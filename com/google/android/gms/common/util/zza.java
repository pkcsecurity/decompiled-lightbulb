package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import com.google.android.gms.common.util.PlatformVersion;

public final class zza {

   private static final IntentFilter filter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
   private static long zzgt;
   private static float zzgu;


   @TargetApi(20)
   public static int zzg(Context var0) {
      if(var0 != null) {
         if(var0.getApplicationContext() == null) {
            return -1;
         } else {
            Intent var4 = var0.getApplicationContext().registerReceiver((BroadcastReceiver)null, filter);
            byte var2 = 0;
            int var1;
            if(var4 == null) {
               var1 = 0;
            } else {
               var1 = var4.getIntExtra("plugged", 0);
            }

            byte var6;
            if((var1 & 7) != 0) {
               var6 = 1;
            } else {
               var6 = 0;
            }

            PowerManager var5 = (PowerManager)var0.getSystemService("power");
            if(var5 == null) {
               return -1;
            } else {
               boolean var3;
               if(PlatformVersion.isAtLeastKitKatWatch()) {
                  var3 = var5.isInteractive();
               } else {
                  var3 = var5.isScreenOn();
               }

               if(var3) {
                  var2 = 1;
               }

               return var2 << 1 | var6;
            }
         }
      } else {
         return -1;
      }
   }

   public static float zzh(Context param0) {
      // $FF: Couldn't be decompiled
   }
}
