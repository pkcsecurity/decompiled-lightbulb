package com.facebook.litho.dataflow;

import android.support.annotation.VisibleForTesting;
import com.facebook.litho.dataflow.BindingListener;
import com.facebook.litho.dataflow.DataFlowGraph;
import com.facebook.litho.dataflow.ValueNode;
import java.util.ArrayList;

public final class GraphBinding {

   private final ArrayList<ValueNode> mAllNodes = new ArrayList();
   private final GraphBinding.Bindings mBindings = new GraphBinding.Bindings(null);
   private final DataFlowGraph mDataFlowGraph;
   private boolean mHasBeenActivated = false;
   private boolean mIsActive = false;
   private BindingListener mListener;


   private GraphBinding(DataFlowGraph var1) {
      this.mDataFlowGraph = var1;
   }

   public static GraphBinding create() {
      return new GraphBinding(DataFlowGraph.getInstance());
   }

   @VisibleForTesting
   public static GraphBinding create(DataFlowGraph var0) {
      return new GraphBinding(var0);
   }

   public void activate() {
      this.mBindings.applyBindings();
      this.mHasBeenActivated = true;
      this.mIsActive = true;
      this.mDataFlowGraph.register(this);
   }

   public void addBinding(ValueNode var1, ValueNode var2) {
      this.addBinding(var1, var2, "default_input");
   }

   public void addBinding(ValueNode var1, ValueNode var2, String var3) {
      if(this.mHasBeenActivated) {
         throw new RuntimeException("Trying to add binding after DataFlowGraph has already been activated.");
      } else {
         this.mBindings.addBinding(var1, var2, var3);
         this.mAllNodes.add(var1);
         this.mAllNodes.add(var2);
      }
   }

   public void deactivate() {
      if(this.mIsActive) {
         this.mIsActive = false;
         this.mDataFlowGraph.unregister(this);
         this.mBindings.removeBindings();
      }
   }

   ArrayList<ValueNode> getAllNodes() {
      return this.mAllNodes;
   }

   public boolean isActive() {
      return this.mIsActive;
   }

   void notifyNodesHaveFinished() {
      if(this.mListener != null) {
         this.mListener.onAllNodesFinished(this);
      }

      this.deactivate();
   }

   public void setListener(BindingListener var1) {
      if(this.mListener != null && var1 != null) {
         throw new RuntimeException("Overriding existing listener!");
      } else {
         this.mListener = var1;
      }
   }

   static class Bindings {

      private final ArrayList<ValueNode> mFromNodes;
      private final ArrayList<String> mInputNames;
      private final ArrayList<ValueNode> mToNodes;


      private Bindings() {
         this.mFromNodes = new ArrayList();
         this.mToNodes = new ArrayList();
         this.mInputNames = new ArrayList();
      }

      // $FF: synthetic method
      Bindings(Object var1) {
         this();
      }

      private static void unbindNodes(ValueNode var0, ValueNode var1, String var2) {
         var0.removeOutput(var1);
         var1.removeInput(var2);
      }

      public void addBinding(ValueNode var1, ValueNode var2, String var3) {
         this.mFromNodes.add(var1);
         this.mToNodes.add(var2);
         this.mInputNames.add(var3);
      }

      public void applyBindings() {
         for(int var1 = 0; var1 < this.mFromNodes.size(); ++var1) {
            ValueNode var2 = (ValueNode)this.mFromNodes.get(var1);
            ValueNode var3 = (ValueNode)this.mToNodes.get(var1);
            String var4 = (String)this.mInputNames.get(var1);
            ValueNode var5 = var3.getInputUnsafe(var4);
            if(var5 != null) {
               unbindNodes(var5, var3, var4);
            }

            var2.addOutput(var3);
            var3.setInput(var4, var2);
         }

      }

      public void removeBindings() {
         for(int var1 = 0; var1 < this.mFromNodes.size(); ++var1) {
            ValueNode var2 = (ValueNode)this.mFromNodes.get(var1);
            ValueNode var3 = (ValueNode)this.mToNodes.get(var1);
            String var4 = (String)this.mInputNames.get(var1);
            if(var3.getInputUnsafe(var4) == var2) {
               unbindNodes(var2, var3, var4);
            }
         }

      }
   }
}
