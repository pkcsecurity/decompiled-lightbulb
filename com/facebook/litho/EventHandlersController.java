package com.facebook.litho;

import android.support.annotation.VisibleForTesting;
import android.support.v4.util.SparseArrayCompat;
import com.facebook.infer.annotation.ThreadSafe;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.EventHandler;
import com.facebook.litho.HasEventDispatcher;
import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class EventHandlersController {

   private final Map<String, EventHandlersController.EventHandlersWrapper> mEventHandlers = new HashMap();


   public void bindEventHandlers(ComponentContext param1, HasEventDispatcher param2, String param3) {
      // $FF: Couldn't be decompiled
   }

   public void clearUnusedEventHandlers() {
      // $FF: Couldn't be decompiled
   }

   @VisibleForTesting
   public Map<String, EventHandlersController.EventHandlersWrapper> getEventHandlers() {
      synchronized(this){}

      Map var1;
      try {
         var1 = this.mEventHandlers;
      } finally {
         ;
      }

      return var1;
   }

   public void recordEventHandler(String param1, EventHandler param2) {
      // $FF: Couldn't be decompiled
   }

   @VisibleForTesting
   public static class EventHandlersWrapper {

      private final SparseArrayCompat<EventHandler> mEventHandlers = new SparseArrayCompat();
      boolean mUsedInCurrentTree;


      void addEventHandler(EventHandler var1) {
         this.mEventHandlers.put(var1.id, var1);
      }

      void bindToDispatcher(ComponentContext var1, HasEventDispatcher var2) {
         int var4 = this.mEventHandlers.size();

         for(int var3 = 0; var3 < var4; ++var3) {
            EventHandler var5 = (EventHandler)this.mEventHandlers.valueAt(var3);
            var5.mHasEventDispatcher = var2;
            if(var5.params != null) {
               var5.params[0] = var1;
            }
         }

      }

      @VisibleForTesting
      public SparseArrayCompat<EventHandler> getEventHandlers() {
         return this.mEventHandlers;
      }
   }
}
