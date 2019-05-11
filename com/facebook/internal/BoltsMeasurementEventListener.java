package com.facebook.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import com.facebook.appevents.AppEventsLogger;
import java.util.Iterator;

public class BoltsMeasurementEventListener extends BroadcastReceiver {

   private static final String BOLTS_MEASUREMENT_EVENT_PREFIX = "bf_";
   private static final String MEASUREMENT_EVENT_ARGS_KEY = "event_args";
   private static final String MEASUREMENT_EVENT_NAME_KEY = "event_name";
   private static final String MEASUREMENT_EVENT_NOTIFICATION_NAME = "com.parse.bolts.measurement_event";
   private static BoltsMeasurementEventListener _instance;
   private Context applicationContext;


   private BoltsMeasurementEventListener(Context var1) {
      this.applicationContext = var1.getApplicationContext();
   }

   private void close() {
      LocalBroadcastManager.getInstance(this.applicationContext).unregisterReceiver(this);
   }

   public static BoltsMeasurementEventListener getInstance(Context var0) {
      if(_instance != null) {
         return _instance;
      } else {
         _instance = new BoltsMeasurementEventListener(var0);
         _instance.open();
         return _instance;
      }
   }

   private void open() {
      LocalBroadcastManager.getInstance(this.applicationContext).registerReceiver(this, new IntentFilter("com.parse.bolts.measurement_event"));
   }

   protected void finalize() throws Throwable {
      try {
         this.close();
      } finally {
         super.finalize();
      }

   }

   public void onReceive(Context var1, Intent var2) {
      AppEventsLogger var7 = AppEventsLogger.newLogger(var1);
      StringBuilder var3 = new StringBuilder();
      var3.append("bf_");
      var3.append(var2.getStringExtra("event_name"));
      String var9 = var3.toString();
      Bundle var8 = var2.getBundleExtra("event_args");
      Bundle var4 = new Bundle();
      Iterator var5 = var8.keySet().iterator();

      while(var5.hasNext()) {
         String var6 = (String)var5.next();
         var4.putString(var6.replaceAll("[^0-9a-zA-Z _-]", "-").replaceAll("^[ -]*", "").replaceAll("[ -]*$", ""), (String)var8.get(var6));
      }

      var7.logEvent(var9, var4);
   }
}
