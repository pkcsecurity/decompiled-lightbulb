package com.facebook.litho.dataflow;

import android.support.annotation.VisibleForTesting;
import android.support.v4.util.Pools;
import android.support.v4.util.SimpleArrayMap;
import com.facebook.litho.ComponentsPools;
import com.facebook.litho.dataflow.ChoreographerTimingSource;
import com.facebook.litho.dataflow.DetectedCycleException;
import com.facebook.litho.dataflow.GraphBinding;
import com.facebook.litho.dataflow.NodeCanFinish;
import com.facebook.litho.dataflow.TimingSource;
import com.facebook.litho.dataflow.ValueNode;
import com.facebook.litho.internal.ArraySet;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.concurrent.GuardedBy;

public class DataFlowGraph {

   private static DataFlowGraph sInstance;
   private static final Pools.SynchronizedPool<DataFlowGraph.NodeState> sNodeStatePool = new Pools.SynchronizedPool(20);
   @GuardedBy
   private final ArrayList<GraphBinding> mBindings = new ArrayList();
   private boolean mIsDirty = false;
   @GuardedBy
   private final Map<ValueNode, DataFlowGraph.NodeState> mNodeStates = new HashMap();
   @GuardedBy
   private final ArrayList<ValueNode> mSortedNodes = new ArrayList();
   @GuardedBy
   private final TimingSource mTimingSource;


   private DataFlowGraph(TimingSource var1) {
      this.mTimingSource = var1;
   }

   private static DataFlowGraph.NodeState acquireNodeState() {
      DataFlowGraph.NodeState var0 = (DataFlowGraph.NodeState)sNodeStatePool.acquire();
      return var0 != null?var0:new DataFlowGraph.NodeState(null);
   }

   @GuardedBy
   private boolean areInputsFinished(ValueNode var1) {
      Iterator var3 = var1.getAllInputs().iterator();

      ValueNode var2;
      do {
         if(!var3.hasNext()) {
            return true;
         }

         var2 = (ValueNode)var3.next();
      } while(((DataFlowGraph.NodeState)this.mNodeStates.get(var2)).isFinished);

      return false;
   }

   @VisibleForTesting
   public static DataFlowGraph create(TimingSource var0) {
      DataFlowGraph var1 = new DataFlowGraph(var0);
      var0.setDataFlowGraph(var1);
      return var1;
   }

   public static DataFlowGraph getInstance() {
      if(sInstance == null) {
         ChoreographerTimingSource var0 = new ChoreographerTimingSource();
         sInstance = new DataFlowGraph(var0);
         var0.setDataFlowGraph(sInstance);
      }

      return sInstance;
   }

   @GuardedBy
   private void notifyFinishedBindings() {
      int var1 = this.mBindings.size() - 1;

      while(var1 >= 0) {
         GraphBinding var5 = (GraphBinding)this.mBindings.get(var1);
         ArrayList var6 = var5.getAllNodes();
         int var4 = var6.size();
         boolean var3 = false;
         int var2 = 0;

         while(true) {
            boolean var7;
            if(var2 < var4) {
               if(((DataFlowGraph.NodeState)this.mNodeStates.get(var6.get(var2))).isFinished) {
                  ++var2;
                  continue;
               }

               var7 = var3;
            } else {
               var7 = true;
            }

            if(var7) {
               var5.notifyNodesHaveFinished();
            }

            --var1;
            break;
         }
      }

   }

   @GuardedBy
   private void propagate(long var1) {
      int var4 = this.mSortedNodes.size();

      for(int var3 = 0; var3 < var4; ++var3) {
         ((ValueNode)this.mSortedNodes.get(var3)).doCalculateValue(var1);
      }

   }

   @GuardedBy
   private void regenerateSortedNodes() {
      this.mSortedNodes.clear();
      if(this.mBindings.size() != 0) {
         ArraySet var6 = ComponentsPools.acquireArraySet();
         SimpleArrayMap var7 = new SimpleArrayMap();
         int var3 = this.mBindings.size();

         int var1;
         int var2;
         ValueNode var9;
         for(var1 = 0; var1 < var3; ++var1) {
            ArrayList var8 = ((GraphBinding)this.mBindings.get(var1)).getAllNodes();
            int var4 = var8.size();

            for(var2 = 0; var2 < var4; ++var2) {
               var9 = (ValueNode)var8.get(var2);
               int var5 = var9.getOutputCount();
               if(var5 == 0) {
                  var6.add(var9);
               } else {
                  var7.put(var9, Integer.valueOf(var5));
               }
            }
         }

         if(!var7.isEmpty() && var6.isEmpty()) {
            throw new DetectedCycleException("Graph has nodes, but they represent a cycle with no leaf nodes!");
         } else {
            ArrayDeque var11 = ComponentsPools.acquireArrayDeque();
            var11.addAll(var6);

            while(!var11.isEmpty()) {
               var9 = (ValueNode)var11.pollFirst();
               this.mSortedNodes.add(var9);
               Iterator var12 = var9.getAllInputs().iterator();

               while(var12.hasNext()) {
                  ValueNode var10 = (ValueNode)var12.next();
                  var1 = ((Integer)var7.get(var10)).intValue() - 1;
                  var7.put(var10, Integer.valueOf(var1));
                  if(var1 == 0) {
                     var11.addLast(var10);
                  } else if(var1 < 0) {
                     throw new DetectedCycleException("Detected cycle.");
                  }
               }
            }

            var1 = var7.size();
            var2 = var6.size();
            if(this.mSortedNodes.size() != var1 + var2) {
               throw new DetectedCycleException("Had unreachable nodes in graph -- this likely means there was a cycle");
            } else {
               Collections.reverse(this.mSortedNodes);
               this.mIsDirty = false;
               ComponentsPools.release(var11);
               ComponentsPools.release(var6);
            }
         }
      }
   }

   @GuardedBy
   private void registerNodes(GraphBinding var1) {
      ArrayList var6 = var1.getAllNodes();
      int var3 = var6.size();

      for(int var2 = 0; var2 < var3; ++var2) {
         ValueNode var4 = (ValueNode)var6.get(var2);
         DataFlowGraph.NodeState var5 = (DataFlowGraph.NodeState)this.mNodeStates.get(var4);
         if(var5 != null) {
            DataFlowGraph.NodeState.access$108(var5);
         } else {
            var5 = acquireNodeState();
            var5.refCount = 1;
            this.mNodeStates.put(var4, var5);
         }
      }

   }

   private static void release(DataFlowGraph.NodeState var0) {
      var0.reset();
      sNodeStatePool.release(var0);
   }

   @VisibleForTesting
   public static void setInstance(DataFlowGraph var0) {
      sInstance = var0;
   }

   @GuardedBy
   private void unregisterNodes(GraphBinding var1) {
      ArrayList var6 = var1.getAllNodes();
      int var3 = var6.size();

      for(int var2 = 0; var2 < var3; ++var2) {
         ValueNode var4 = (ValueNode)var6.get(var2);
         DataFlowGraph.NodeState var5 = (DataFlowGraph.NodeState)this.mNodeStates.get(var4);
         DataFlowGraph.NodeState.access$110(var5);
         if(var5.refCount == 0) {
            release((DataFlowGraph.NodeState)this.mNodeStates.remove(var4));
         }
      }

   }

   @GuardedBy
   private void updateFinishedNodes() {
      int var3 = this.mSortedNodes.size();

      for(int var1 = 0; var1 < var3; ++var1) {
         ValueNode var4 = (ValueNode)this.mSortedNodes.get(var1);
         DataFlowGraph.NodeState var5 = (DataFlowGraph.NodeState)this.mNodeStates.get(var4);
         if(!var5.isFinished && this.areInputsFinished(var4)) {
            boolean var2;
            if(var4 instanceof NodeCanFinish && !((NodeCanFinish)var4).isFinished()) {
               var2 = false;
            } else {
               var2 = true;
            }

            if(var2) {
               var5.isFinished = true;
            }
         }
      }

   }

   @GuardedBy
   private void updateFinishedStates() {
      this.updateFinishedNodes();
      this.notifyFinishedBindings();
   }

   void doFrame(long var1) {
      synchronized(this){}

      try {
         if(this.mIsDirty) {
            this.regenerateSortedNodes();
         }

         this.propagate(var1);
         this.updateFinishedStates();
      } finally {
         ;
      }

   }

   @VisibleForTesting
   @GuardedBy
   boolean hasReferencesToNodes() {
      return !this.mBindings.isEmpty() || !this.mSortedNodes.isEmpty() || !this.mNodeStates.isEmpty();
   }

   public void register(GraphBinding var1) {
      synchronized(this){}

      try {
         if(!var1.isActive()) {
            StringBuilder var2 = new StringBuilder();
            var2.append("Expected added GraphBinding to be active: ");
            var2.append(var1);
            throw new RuntimeException(var2.toString());
         }

         this.mBindings.add(var1);
         this.registerNodes(var1);
         if(this.mBindings.size() == 1) {
            this.mTimingSource.start();
         }

         this.mIsDirty = true;
      } finally {
         ;
      }

   }

   public void unregister(GraphBinding var1) {
      synchronized(this){}

      try {
         if(!this.mBindings.remove(var1)) {
            throw new RuntimeException("Tried to unregister non-existent binding");
         }

         this.unregisterNodes(var1);
         if(this.mBindings.isEmpty()) {
            this.mTimingSource.stop();
            this.mSortedNodes.clear();
            if(!this.mNodeStates.isEmpty()) {
               throw new RuntimeException("Failed to clean up all nodes");
            }
         }

         this.mIsDirty = true;
      } finally {
         ;
      }

   }

   static class NodeState {

      private boolean isFinished;
      private int refCount;


      private NodeState() {
         this.isFinished = false;
         this.refCount = 0;
      }

      // $FF: synthetic method
      NodeState(Object var1) {
         this();
      }

      // $FF: synthetic method
      static int access$108(DataFlowGraph.NodeState var0) {
         int var1 = var0.refCount;
         var0.refCount = var1 + 1;
         return var1;
      }

      // $FF: synthetic method
      static int access$110(DataFlowGraph.NodeState var0) {
         int var1 = var0.refCount;
         var0.refCount = var1 - 1;
         return var1;
      }

      void reset() {
         this.isFinished = false;
         this.refCount = 0;
      }
   }
}
