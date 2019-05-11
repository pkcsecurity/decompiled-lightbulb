package com.facebook.appevents;

import com.facebook.appevents.AccessTokenAppIdPair;
import com.facebook.appevents.AppEvent;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

class PersistedEvents implements Serializable {

   private static final long serialVersionUID = 20160629001L;
   private HashMap<AccessTokenAppIdPair, List<AppEvent>> events = new HashMap();


   public PersistedEvents() {}

   public PersistedEvents(HashMap<AccessTokenAppIdPair, List<AppEvent>> var1) {
      this.events.putAll(var1);
   }

   private Object writeReplace() {
      return new PersistedEvents.SerializationProxyV1(this.events, null);
   }

   public void addEvents(AccessTokenAppIdPair var1, List<AppEvent> var2) {
      if(!this.events.containsKey(var1)) {
         this.events.put(var1, var2);
      } else {
         ((List)this.events.get(var1)).addAll(var2);
      }
   }

   public boolean containsKey(AccessTokenAppIdPair var1) {
      return this.events.containsKey(var1);
   }

   public List<AppEvent> get(AccessTokenAppIdPair var1) {
      return (List)this.events.get(var1);
   }

   public Set<AccessTokenAppIdPair> keySet() {
      return this.events.keySet();
   }

   static class SerializationProxyV1 implements Serializable {

      private static final long serialVersionUID = 20160629001L;
      private final HashMap<AccessTokenAppIdPair, List<AppEvent>> proxyEvents;


      private SerializationProxyV1(HashMap<AccessTokenAppIdPair, List<AppEvent>> var1) {
         this.proxyEvents = var1;
      }

      // $FF: synthetic method
      SerializationProxyV1(HashMap var1, Object var2) {
         this(var1);
      }

      private Object readResolve() {
         return new PersistedEvents(this.proxyEvents);
      }
   }
}
