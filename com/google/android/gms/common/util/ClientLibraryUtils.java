package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.wrappers.Wrappers;

@KeepForSdk
public class ClientLibraryUtils {

   @KeepForSdk
   public static int getClientVersion(Context var0, String var1) {
      PackageInfo var2 = zzb(var0, var1);
      if(var2 != null) {
         if(var2.applicationInfo == null) {
            return -1;
         } else {
            Bundle var3 = var2.applicationInfo.metaData;
            return var3 == null?-1:var3.getInt("com.google.android.gms.version", -1);
         }
      } else {
         return -1;
      }
   }

   @KeepForSdk
   public static boolean isPackageSide() {
      return false;
   }

   @Nullable
   private static PackageInfo zzb(Context var0, String var1) {
      try {
         PackageInfo var3 = Wrappers.packageManager(var0).getPackageInfo(var1, 128);
         return var3;
      } catch (NameNotFoundException var2) {
         return null;
      }
   }

   public static boolean zzc(Context var0, String var1) {
      "com.google.android.gms".equals(var1);

      int var2;
      try {
         var2 = Wrappers.packageManager(var0).getApplicationInfo(var1, 0).flags;
      } catch (NameNotFoundException var3) {
         return false;
      }

      return (var2 & 2097152) != 0;
   }
}
