package com.google.android.gms.common.wrappers;

import android.content.Context;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.PlatformVersion;

@KeepForSdk
public class InstantApps {

   private static Context zzht;
   private static Boolean zzhu;


   @KeepForSdk
   public static boolean isInstantApp(Context var0) {
      synchronized(InstantApps.class){}

      boolean var1;
      try {
         Context var2 = var0.getApplicationContext();
         if(zzht == null || zzhu == null || zzht != var2) {
            zzhu = null;
            if(PlatformVersion.isAtLeastO()) {
               zzhu = Boolean.valueOf(var2.getPackageManager().isInstantApp());
            } else {
               try {
                  var0.getClassLoader().loadClass("com.google.android.instantapps.supervisor.InstantAppsRuntime");
                  zzhu = Boolean.valueOf(true);
               } catch (ClassNotFoundException var5) {
                  zzhu = Boolean.valueOf(false);
               }
            }

            zzht = var2;
            var1 = zzhu.booleanValue();
            return var1;
         }

         var1 = zzhu.booleanValue();
      } finally {
         ;
      }

      return var1;
   }
}
