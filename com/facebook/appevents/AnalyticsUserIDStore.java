package com.facebook.appevents;

import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.appevents.internal.AppEventUtility;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class AnalyticsUserIDStore {

   private static final String ANALYTICS_USER_ID_KEY = "com.facebook.appevents.AnalyticsUserIDStore.userID";
   private static final String TAG = "AnalyticsUserIDStore";
   private static volatile boolean initialized;
   private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
   private static String userID;


   public static String getUserID() {
      if(!initialized) {
         Log.w(TAG, "initStore should have been called before calling setUserID");
         initAndWait();
      }

      lock.readLock().lock();

      String var0;
      try {
         var0 = userID;
      } finally {
         lock.readLock().unlock();
      }

      return var0;
   }

   private static void initAndWait() {
      // $FF: Couldn't be decompiled
   }

   public static void initStore() {
      if(!initialized) {
         AppEventsLogger.getAnalyticsExecutor().execute(new Runnable() {
            public void run() {
               AnalyticsUserIDStore.initAndWait();
            }
         });
      }
   }

   public static void setUserID(final String var0) {
      AppEventUtility.assertIsNotMainThread();
      if(!initialized) {
         Log.w(TAG, "initStore should have been called before calling setUserID");
         initAndWait();
      }

      AppEventsLogger.getAnalyticsExecutor().execute(new Runnable() {
         public void run() {
            AnalyticsUserIDStore.lock.writeLock().lock();

            try {
               AnalyticsUserIDStore.userID = var0;
               Editor var1 = PreferenceManager.getDefaultSharedPreferences(FacebookSdk.getApplicationContext()).edit();
               var1.putString("com.facebook.appevents.AnalyticsUserIDStore.userID", AnalyticsUserIDStore.userID);
               var1.apply();
            } finally {
               AnalyticsUserIDStore.lock.writeLock().unlock();
            }

         }
      });
   }
}
