package com.google.android.gms.common.stats;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.stats.LoggingConstants;
import com.google.android.gms.common.stats.WakeLockEvent;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.Arrays;
import java.util.List;

@KeepForSdk
public class WakeLockTracker {

   @VisibleForTesting
   private static boolean zzfb;
   private static WakeLockTracker zzgb = new WakeLockTracker();
   private static Boolean zzgc;


   @KeepForSdk
   public static WakeLockTracker getInstance() {
      return zzgb;
   }

   @KeepForSdk
   public void registerAcquireEvent(Context var1, Intent var2, String var3, String var4, String var5, int var6, String var7) {
      List var8 = Arrays.asList(new String[]{var7});
      this.registerEvent(var1, var2.getStringExtra("WAKE_LOCK_KEY"), 7, var3, var4, var5, var6, var8);
   }

   @KeepForSdk
   public void registerEvent(Context var1, String var2, int var3, String var4, String var5, String var6, int var7, List<String> var8) {
      this.registerEvent(var1, var2, var3, var4, var5, var6, var7, var8, 0L);
   }

   @KeepForSdk
   public void registerEvent(Context var1, String var2, int var3, String var4, String var5, String var6, int var7, List<String> var8, long var9) {
      if(zzgc == null) {
         zzgc = Boolean.valueOf(false);
      }

      if(zzgc.booleanValue()) {
         if(TextUtils.isEmpty(var2)) {
            String var19 = String.valueOf(var2);
            if(var19.length() != 0) {
               var19 = "missing wakeLock key. ".concat(var19);
            } else {
               var19 = new String("missing wakeLock key. ");
            }

            Log.e("WakeLockTracker", var19);
         } else {
            long var12 = System.currentTimeMillis();
            if(7 == var3 || 8 == var3 || 10 == var3 || 11 == var3) {
               List var16 = var8;
               if(var8 != null) {
                  var16 = var8;
                  if(var8.size() == 1) {
                     var16 = var8;
                     if("com.google.android.gms".equals(var8.get(0))) {
                        var16 = null;
                     }
                  }
               }

               long var14 = SystemClock.elapsedRealtime();
               int var11 = com.google.android.gms.common.util.zza.zzg(var1);
               String var21 = var1.getPackageName();
               if("com.google.android.gms".equals(var21)) {
                  var21 = null;
               }

               WakeLockEvent var20 = new WakeLockEvent(var12, var3, var4, var7, var16, var2, var14, var11, var5, var21, com.google.android.gms.common.util.zza.zzh(var1), var9, var6);

               try {
                  var1.startService((new Intent()).setComponent(LoggingConstants.zzfg).putExtra("com.google.android.gms.common.stats.EXTRA_LOG_EVENT", var20));
                  return;
               } catch (Exception var18) {
                  Log.wtf("WakeLockTracker", var18);
               }
            }

         }
      }
   }

   @KeepForSdk
   public void registerReleaseEvent(Context var1, Intent var2) {
      this.registerEvent(var1, var2.getStringExtra("WAKE_LOCK_KEY"), 8, (String)null, (String)null, (String)null, 0, (List)null);
   }
}
