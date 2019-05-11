package com.google.android.gms.stats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.stats.WakeLockTracker;

@KeepForSdk
@ShowFirstParty
public abstract class GCoreWakefulBroadcastReceiver extends WakefulBroadcastReceiver {

   private static String TAG;


   @SuppressLint({"UnwrappedWakefulBroadcastReceiver"})
   @KeepForSdk
   public static boolean completeWakefulIntent(Context var0, Intent var1) {
      if(var1 == null) {
         return false;
      } else {
         if(var0 != null) {
            WakeLockTracker.getInstance().registerReleaseEvent(var0, var1);
         } else {
            String var2 = TAG;
            String var3 = String.valueOf(var1.toUri(0));
            if(var3.length() != 0) {
               var3 = "context shouldn\'t be null. intent: ".concat(var3);
            } else {
               var3 = new String("context shouldn\'t be null. intent: ");
            }

            Log.w(var2, var3);
         }

         return WakefulBroadcastReceiver.completeWakefulIntent(var1);
      }
   }
}
