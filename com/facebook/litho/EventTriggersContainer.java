package com.facebook.litho;

import android.support.annotation.GuardedBy;
import android.support.annotation.Nullable;
import com.facebook.litho.EventTrigger;
import java.util.Map;

public class EventTriggersContainer {

   @GuardedBy("this")
   @Nullable
   private Map<String, EventTrigger> mEventTriggers;


   public void clear() {
      synchronized(this){}

      try {
         if(this.mEventTriggers != null) {
            this.mEventTriggers.clear();
         }
      } finally {
         ;
      }

   }

   @Nullable
   public EventTrigger getEventTrigger(String var1) {
      synchronized(this){}

      EventTrigger var4;
      try {
         if(this.mEventTriggers == null || !this.mEventTriggers.containsKey(var1)) {
            return null;
         }

         var4 = (EventTrigger)this.mEventTriggers.get(var1);
      } finally {
         ;
      }

      return var4;
   }

   public void recordEventTrigger(@Nullable EventTrigger param1) {
      // $FF: Couldn't be decompiled
   }
}
