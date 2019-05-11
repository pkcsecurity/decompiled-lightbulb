package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.common.wrappers.Wrappers;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@KeepForSdk
public class AndroidUtilsLight {

   private static volatile int zzgd;


   @TargetApi(24)
   @KeepForSdk
   public static Context getDeviceProtectedStorageContext(Context var0) {
      return PlatformVersion.isAtLeastN() && !var0.isDeviceProtectedStorage()?var0.createDeviceProtectedStorageContext():var0;
   }

   @KeepForSdk
   public static byte[] getPackageCertificateHashBytes(Context var0, String var1) throws NameNotFoundException {
      PackageInfo var2 = Wrappers.packageManager(var0).getPackageInfo(var1, 64);
      if(var2.signatures != null && var2.signatures.length == 1) {
         MessageDigest var3 = zzi("SHA1");
         if(var3 != null) {
            return var3.digest(var2.signatures[0].toByteArray());
         }
      }

      return null;
   }

   public static MessageDigest zzi(String var0) {
      for(int var1 = 0; var1 < 2; ++var1) {
         MessageDigest var2;
         try {
            var2 = MessageDigest.getInstance(var0);
         } catch (NoSuchAlgorithmException var3) {
            continue;
         }

         if(var2 != null) {
            return var2;
         }
      }

      return null;
   }
}
