package com.facebook.litho;

import android.support.annotation.Nullable;
import android.support.v4.util.Pools;
import com.facebook.infer.annotation.ThreadSafe;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentLifecycle;
import com.facebook.litho.ComponentsPools;
import com.facebook.litho.StateContainer;
import com.facebook.litho.Transition;
import com.facebook.litho.config.ComponentsConfiguration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.concurrent.GuardedBy;

public class StateHandler {

   private static final int INITIAL_MAP_CAPACITY = 4;
   private static final int INITIAL_STATE_UPDATE_LIST_CAPACITY = 4;
   private static final int POOL_CAPACITY = 10;
   @Nullable
   private static final Pools.SynchronizedPool<Map<String, List<ComponentLifecycle.StateUpdate>>> sPendingStateUpdatesMapPool;
   @Nullable
   private static final Pools.SynchronizedPool<Map<String, StateContainer>> sStateContainersMapPool;
   @Nullable
   private static final Pools.SynchronizedPool<List<ComponentLifecycle.StateUpdate>> sStateUpdatesListPool;
   @GuardedBy
   public HashSet<String> mNeededStateContainers;
   @Nullable
   @GuardedBy
   private Map<String, List<Transition>> mPendingStateUpdateTransitions;
   @GuardedBy
   private Map<String, List<ComponentLifecycle.StateUpdate>> mPendingStateUpdates;
   @GuardedBy
   public Map<String, StateContainer> mStateContainers;


   static {
      if(ComponentsConfiguration.useStateHandlers) {
         sStateUpdatesListPool = new Pools.SynchronizedPool(10);
         sPendingStateUpdatesMapPool = new Pools.SynchronizedPool(10);
         sStateContainersMapPool = new Pools.SynchronizedPool(10);
      } else {
         sStateUpdatesListPool = null;
         sPendingStateUpdatesMapPool = null;
         sStateContainersMapPool = null;
      }
   }

   @Nullable
   public static StateHandler acquireNewInstance(@Nullable StateHandler var0) {
      return ComponentsConfiguration.useStateHandlers?ComponentsPools.acquireStateHandler(var0):null;
   }

   private static List<ComponentLifecycle.StateUpdate> acquireStateUpdatesList() {
      return acquireStateUpdatesList((List)null);
   }

   private static List<ComponentLifecycle.StateUpdate> acquireStateUpdatesList(List<ComponentLifecycle.StateUpdate> var0) {
      List var3 = (List)sStateUpdatesListPool.acquire();
      Object var2 = var3;
      if(var3 == null) {
         int var1;
         if(var0 == null) {
            var1 = 4;
         } else {
            var1 = var0.size();
         }

         var2 = new ArrayList(var1);
      }

      if(var0 != null) {
         ((List)var2).addAll(var0);
      }

      return (List)var2;
   }

   private void clearStateUpdates(Map<String, List<ComponentLifecycle.StateUpdate>> param1) {
      // $FF: Couldn't be decompiled
   }

   private static void clearUnusedStateContainers(StateHandler var0) {
      HashSet var1 = var0.mNeededStateContainers;
      ArrayList var2 = new ArrayList();
      if(var1 != null) {
         if(var0.mStateContainers != null) {
            var2.addAll(var0.mStateContainers.keySet());
            Iterator var4 = var2.iterator();

            while(var4.hasNext()) {
               String var3 = (String)var4.next();
               if(!var1.contains(var3)) {
                  var0.mStateContainers.remove(var3);
               }
            }

         }
      }
   }

   private void copyCurrentStateContainers(Map<String, StateContainer> param1) {
      // $FF: Couldn't be decompiled
   }

   private void copyPendingStateTransitions(@Nullable Map<String, List<Transition>> param1) {
      // $FF: Couldn't be decompiled
   }

   private void copyPendingStateUpdatesMap(Map<String, List<ComponentLifecycle.StateUpdate>> param1) {
      // $FF: Couldn't be decompiled
   }

   private void maybeInitNeededStateContainers() {
      synchronized(this){}

      try {
         if(this.mNeededStateContainers == null) {
            this.mNeededStateContainers = new HashSet();
         }
      } finally {
         ;
      }

   }

   private void maybeInitPendingStateUpdateTransitions() {
      synchronized(this){}

      try {
         if(this.mPendingStateUpdateTransitions == null) {
            this.mPendingStateUpdateTransitions = new HashMap();
         }
      } finally {
         ;
      }

   }

   private void maybeInitPendingUpdates() {
      synchronized(this){}

      try {
         if(this.mPendingStateUpdates == null) {
            this.mPendingStateUpdates = (Map)sPendingStateUpdatesMapPool.acquire();
            if(this.mPendingStateUpdates == null) {
               this.mPendingStateUpdates = new HashMap(4);
            }
         }
      } finally {
         ;
      }

   }

   private void maybeInitStateContainers() {
      synchronized(this){}

      try {
         if(this.mStateContainers == null) {
            this.mStateContainers = (Map)sStateContainersMapPool.acquire();
            if(this.mStateContainers == null) {
               this.mStateContainers = new HashMap(4);
            }
         }
      } finally {
         ;
      }

   }

   private static void releaseStateUpdatesList(List<ComponentLifecycle.StateUpdate> var0) {
      var0.clear();
      sStateUpdatesListPool.release(var0);
   }

   @ThreadSafe(
      enableChecks = false
   )
   void applyStateUpdatesForComponent(Component param1) {
      // $FF: Couldn't be decompiled
   }

   void commit(StateHandler var1) {
      this.clearStateUpdates(var1.getPendingStateUpdates());
      clearUnusedStateContainers(var1);
      this.copyCurrentStateContainers(var1.getStateContainers());
      this.copyPendingStateTransitions(var1.getPendingStateUpdateTransitions());
   }

   @Nullable
   void consumePendingStateUpdateTransitions(List<Transition> param1, @Nullable String param2) {
      // $FF: Couldn't be decompiled
   }

   @Nullable
   Map<String, List<Transition>> getPendingStateUpdateTransitions() {
      synchronized(this){}

      Map var1;
      try {
         var1 = this.mPendingStateUpdateTransitions;
      } finally {
         ;
      }

      return var1;
   }

   Map<String, List<ComponentLifecycle.StateUpdate>> getPendingStateUpdates() {
      synchronized(this){}

      Map var1;
      try {
         var1 = this.mPendingStateUpdates;
      } finally {
         ;
      }

      return var1;
   }

   Map<String, StateContainer> getStateContainers() {
      synchronized(this){}

      Map var1;
      try {
         var1 = this.mStateContainers;
      } finally {
         ;
      }

      return var1;
   }

   void init(@Nullable StateHandler param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean isEmpty() {
      synchronized(this){}
      boolean var4 = false;

      boolean var1;
      label46: {
         try {
            var4 = true;
            if(this.mStateContainers == null) {
               var4 = false;
               break label46;
            }

            var1 = this.mStateContainers.isEmpty();
            var4 = false;
         } finally {
            if(var4) {
               ;
            }
         }

         if(!var1) {
            var1 = false;
            return var1;
         }
      }

      var1 = true;
      return var1;
   }

   void queueStateUpdate(String param1, ComponentLifecycle.StateUpdate param2) {
      // $FF: Couldn't be decompiled
   }

   void release() {
      synchronized(this){}

      try {
         if(this.mPendingStateUpdates != null) {
            this.mPendingStateUpdates.clear();
            sPendingStateUpdatesMapPool.release(this.mPendingStateUpdates);
            this.mPendingStateUpdates = null;
         }

         this.mPendingStateUpdateTransitions = null;
         if(this.mStateContainers != null) {
            this.mStateContainers.clear();
            sStateContainersMapPool.release(this.mStateContainers);
            this.mStateContainers = null;
         }

         this.mNeededStateContainers = null;
      } finally {
         ;
      }

   }
}
