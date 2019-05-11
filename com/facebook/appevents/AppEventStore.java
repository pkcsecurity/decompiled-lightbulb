package com.facebook.appevents;

import com.facebook.appevents.AccessTokenAppIdPair;
import com.facebook.appevents.AppEvent;
import com.facebook.appevents.AppEventCollection;
import com.facebook.appevents.PersistedEvents;
import com.facebook.appevents.SessionEventsState;
import com.facebook.appevents.internal.AppEventUtility;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.Iterator;

class AppEventStore {

   private static final String PERSISTED_EVENTS_FILENAME = "AppEventsLogger.persistedevents";
   private static final String TAG = "com.facebook.appevents.AppEventStore";


   public static void persistEvents(AccessTokenAppIdPair var0, SessionEventsState var1) {
      synchronized(AppEventStore.class){}

      try {
         AppEventUtility.assertIsNotMainThread();
         PersistedEvents var2 = readAndClearStore();
         if(var2.containsKey(var0)) {
            var2.get(var0).addAll(var1.getEventsToPersist());
         } else {
            var2.addEvents(var0, var1.getEventsToPersist());
         }

         saveEventsToDisk(var2);
      } finally {
         ;
      }

   }

   public static void persistEvents(AppEventCollection var0) {
      synchronized(AppEventStore.class){}

      try {
         AppEventUtility.assertIsNotMainThread();
         PersistedEvents var1 = readAndClearStore();
         Iterator var2 = var0.keySet().iterator();

         while(var2.hasNext()) {
            AccessTokenAppIdPair var3 = (AccessTokenAppIdPair)var2.next();
            var1.addEvents(var3, var0.get(var3).getEventsToPersist());
         }

         saveEventsToDisk(var1);
      } finally {
         ;
      }
   }

   public static PersistedEvents readAndClearStore() {
      // $FF: Couldn't be decompiled
   }

   private static void saveEventsToDisk(PersistedEvents param0) {
      // $FF: Couldn't be decompiled
   }

   static class MovedClassObjectInputStream extends ObjectInputStream {

      private static final String ACCESS_TOKEN_APP_ID_PAIR_SERIALIZATION_PROXY_V1_CLASS_NAME = "com.facebook.appevents.AppEventsLogger$AccessTokenAppIdPair$SerializationProxyV1";
      private static final String APP_EVENT_SERIALIZATION_PROXY_V1_CLASS_NAME = "com.facebook.appevents.AppEventsLogger$AppEvent$SerializationProxyV1";


      public MovedClassObjectInputStream(InputStream var1) throws IOException {
         super(var1);
      }

      protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
         ObjectStreamClass var2 = super.readClassDescriptor();
         if(var2.getName().equals("com.facebook.appevents.AppEventsLogger$AccessTokenAppIdPair$SerializationProxyV1")) {
            return ObjectStreamClass.lookup(AccessTokenAppIdPair.SerializationProxyV1.class);
         } else {
            ObjectStreamClass var1 = var2;
            if(var2.getName().equals("com.facebook.appevents.AppEventsLogger$AppEvent$SerializationProxyV1")) {
               var1 = ObjectStreamClass.lookup(AppEvent.SerializationProxyV1.class);
            }

            return var1;
         }
      }
   }
}
