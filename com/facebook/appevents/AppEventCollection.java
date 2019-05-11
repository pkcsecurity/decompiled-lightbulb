package com.facebook.appevents;

import com.facebook.appevents.AccessTokenAppIdPair;
import com.facebook.appevents.AppEvent;
import com.facebook.appevents.PersistedEvents;
import com.facebook.appevents.SessionEventsState;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

class AppEventCollection {

   private final HashMap<AccessTokenAppIdPair, SessionEventsState> stateMap = new HashMap();


   private SessionEventsState getSessionEventsState(AccessTokenAppIdPair param1) {
      // $FF: Couldn't be decompiled
   }

   public void addEvent(AccessTokenAppIdPair var1, AppEvent var2) {
      synchronized(this){}

      try {
         this.getSessionEventsState(var1).addEvent(var2);
      } finally {
         ;
      }

   }

   public void addPersistedEvents(PersistedEvents var1) {
      synchronized(this){}
      if(var1 != null) {
         try {
            Iterator var2 = var1.keySet().iterator();

            while(var2.hasNext()) {
               AccessTokenAppIdPair var4 = (AccessTokenAppIdPair)var2.next();
               SessionEventsState var3 = this.getSessionEventsState(var4);
               Iterator var7 = var1.get(var4).iterator();

               while(var7.hasNext()) {
                  var3.addEvent((AppEvent)var7.next());
               }
            }
         } finally {
            ;
         }

      }
   }

   public SessionEventsState get(AccessTokenAppIdPair var1) {
      synchronized(this){}

      SessionEventsState var4;
      try {
         var4 = (SessionEventsState)this.stateMap.get(var1);
      } finally {
         ;
      }

      return var4;
   }

   public int getEventCount() {
      // $FF: Couldn't be decompiled
   }

   public Set<AccessTokenAppIdPair> keySet() {
      synchronized(this){}

      Set var1;
      try {
         var1 = this.stateMap.keySet();
      } finally {
         ;
      }

      return var1;
   }
}
