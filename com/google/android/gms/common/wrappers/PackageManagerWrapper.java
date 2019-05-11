package com.google.android.gms.common.wrappers;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Binder;
import android.os.Process;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.common.wrappers.InstantApps;

@KeepForSdk
public class PackageManagerWrapper {

   private final Context zzhv;


   public PackageManagerWrapper(Context var1) {
      this.zzhv = var1;
   }

   @KeepForSdk
   public int checkCallingOrSelfPermission(String var1) {
      return this.zzhv.checkCallingOrSelfPermission(var1);
   }

   @KeepForSdk
   public int checkPermission(String var1, String var2) {
      return this.zzhv.getPackageManager().checkPermission(var1, var2);
   }

   @KeepForSdk
   public ApplicationInfo getApplicationInfo(String var1, int var2) throws NameNotFoundException {
      return this.zzhv.getPackageManager().getApplicationInfo(var1, var2);
   }

   @KeepForSdk
   public CharSequence getApplicationLabel(String var1) throws NameNotFoundException {
      return this.zzhv.getPackageManager().getApplicationLabel(this.zzhv.getPackageManager().getApplicationInfo(var1, 0));
   }

   @KeepForSdk
   public PackageInfo getPackageInfo(String var1, int var2) throws NameNotFoundException {
      return this.zzhv.getPackageManager().getPackageInfo(var1, var2);
   }

   public final String[] getPackagesForUid(int var1) {
      return this.zzhv.getPackageManager().getPackagesForUid(var1);
   }

   @KeepForSdk
   public boolean isCallerInstantApp() {
      if(Binder.getCallingUid() == Process.myUid()) {
         return InstantApps.isInstantApp(this.zzhv);
      } else {
         if(PlatformVersion.isAtLeastO()) {
            String var1 = this.zzhv.getPackageManager().getNameForUid(Binder.getCallingUid());
            if(var1 != null) {
               return this.zzhv.getPackageManager().isInstantApp(var1);
            }
         }

         return false;
      }
   }

   public final PackageInfo zza(String var1, int var2, int var3) throws NameNotFoundException {
      return this.zzhv.getPackageManager().getPackageInfo(var1, 64);
   }

   @TargetApi(19)
   public final boolean zzb(int var1, String var2) {
      if(PlatformVersion.isAtLeastKitKat()) {
         try {
            ((AppOpsManager)this.zzhv.getSystemService("appops")).checkPackage(var1, var2);
            return true;
         } catch (SecurityException var4) {
            return false;
         }
      } else {
         String[] var3 = this.zzhv.getPackageManager().getPackagesForUid(var1);
         if(var2 != null && var3 != null) {
            for(var1 = 0; var1 < var3.length; ++var1) {
               if(var2.equals(var3[var1])) {
                  return true;
               }
            }
         }

         return false;
      }
   }
}
