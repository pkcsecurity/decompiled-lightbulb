package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.PlatformVersion;

@KeepForSdk
public final class DeviceProperties {

   private static Boolean zzgl;
   private static Boolean zzgm;
   private static Boolean zzgn;
   private static Boolean zzgo;
   private static Boolean zzgp;
   private static Boolean zzgq;
   private static Boolean zzgr;
   private static Boolean zzgs;


   @KeepForSdk
   public static boolean isAuto(Context var0) {
      if(zzgr == null) {
         boolean var1;
         if(PlatformVersion.isAtLeastO() && var0.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
            var1 = true;
         } else {
            var1 = false;
         }

         zzgr = Boolean.valueOf(var1);
      }

      return zzgr.booleanValue();
   }

   @KeepForSdk
   public static boolean isLatchsky(Context var0) {
      if(zzgp == null) {
         PackageManager var2 = var0.getPackageManager();
         boolean var1;
         if(var2.hasSystemFeature("com.google.android.feature.services_updater") && var2.hasSystemFeature("cn.google.services")) {
            var1 = true;
         } else {
            var1 = false;
         }

         zzgp = Boolean.valueOf(var1);
      }

      return zzgp.booleanValue();
   }

   @TargetApi(21)
   @KeepForSdk
   public static boolean isSidewinder(Context var0) {
      if(zzgo == null) {
         boolean var1;
         if(PlatformVersion.isAtLeastLollipop() && var0.getPackageManager().hasSystemFeature("cn.google")) {
            var1 = true;
         } else {
            var1 = false;
         }

         zzgo = Boolean.valueOf(var1);
      }

      return zzgo.booleanValue();
   }

   @KeepForSdk
   public static boolean isTablet(Resources var0) {
      boolean var3 = false;
      if(var0 == null) {
         return false;
      } else {
         if(zzgl == null) {
            boolean var1;
            if((var0.getConfiguration().screenLayout & 15) > 3) {
               var1 = true;
            } else {
               var1 = false;
            }

            boolean var2;
            label33: {
               if(!var1) {
                  if(zzgm == null) {
                     Configuration var4 = var0.getConfiguration();
                     if((var4.screenLayout & 15) <= 3 && var4.smallestScreenWidthDp >= 600) {
                        var2 = true;
                     } else {
                        var2 = false;
                     }

                     zzgm = Boolean.valueOf(var2);
                  }

                  var2 = var3;
                  if(!zzgm.booleanValue()) {
                     break label33;
                  }
               }

               var2 = true;
            }

            zzgl = Boolean.valueOf(var2);
         }

         return zzgl.booleanValue();
      }
   }

   @KeepForSdk
   public static boolean isTv(Context var0) {
      if(zzgs == null) {
         PackageManager var2 = var0.getPackageManager();
         boolean var1;
         if(!var2.hasSystemFeature("com.google.android.tv") && !var2.hasSystemFeature("android.hardware.type.television") && !var2.hasSystemFeature("android.software.leanback")) {
            var1 = false;
         } else {
            var1 = true;
         }

         zzgs = Boolean.valueOf(var1);
      }

      return zzgs.booleanValue();
   }

   @KeepForSdk
   public static boolean isUserBuild() {
      return "user".equals(Build.TYPE);
   }

   @TargetApi(20)
   @KeepForSdk
   public static boolean isWearable(Context var0) {
      if(zzgn == null) {
         boolean var1;
         if(PlatformVersion.isAtLeastKitKatWatch() && var0.getPackageManager().hasSystemFeature("android.hardware.type.watch")) {
            var1 = true;
         } else {
            var1 = false;
         }

         zzgn = Boolean.valueOf(var1);
      }

      return zzgn.booleanValue();
   }

   @TargetApi(26)
   @KeepForSdk
   public static boolean isWearableWithoutPlayStore(Context var0) {
      return isWearable(var0) && (!PlatformVersion.isAtLeastN() || isSidewinder(var0) && !PlatformVersion.isAtLeastO());
   }

   public static boolean zzf(Context var0) {
      if(zzgq == null) {
         boolean var1;
         if(!var0.getPackageManager().hasSystemFeature("android.hardware.type.iot") && !var0.getPackageManager().hasSystemFeature("android.hardware.type.embedded")) {
            var1 = false;
         } else {
            var1 = true;
         }

         zzgq = Boolean.valueOf(var1);
      }

      return zzgq.booleanValue();
   }
}
