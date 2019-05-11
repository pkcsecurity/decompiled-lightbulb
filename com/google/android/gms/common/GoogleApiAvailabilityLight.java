package com.google.android.gms.common;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.HideFirstParty;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.util.DeviceProperties;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.common.wrappers.Wrappers;

@KeepForSdk
@ShowFirstParty
public class GoogleApiAvailabilityLight {

   @KeepForSdk
   public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
   @KeepForSdk
   public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = GooglePlayServicesUtilLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
   @KeepForSdk
   public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";
   @KeepForSdk
   static final String TRACKING_SOURCE_DIALOG = "d";
   @KeepForSdk
   static final String TRACKING_SOURCE_NOTIFICATION = "n";
   private static final GoogleApiAvailabilityLight zzm = new GoogleApiAvailabilityLight();


   @KeepForSdk
   public static GoogleApiAvailabilityLight getInstance() {
      return zzm;
   }

   @VisibleForTesting
   private static String zza(@Nullable Context var0, @Nullable String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append("gcore_");
      var2.append(GOOGLE_PLAY_SERVICES_VERSION_CODE);
      var2.append("-");
      if(!TextUtils.isEmpty(var1)) {
         var2.append(var1);
      }

      var2.append("-");
      if(var0 != null) {
         var2.append(var0.getPackageName());
      }

      var2.append("-");
      if(var0 != null) {
         try {
            var2.append(Wrappers.packageManager(var0).getPackageInfo(var0.getPackageName(), 0).versionCode);
         } catch (NameNotFoundException var3) {
            ;
         }
      }

      return var2.toString();
   }

   @KeepForSdk
   public void cancelAvailabilityErrorNotifications(Context var1) {
      GooglePlayServicesUtilLight.cancelAvailabilityErrorNotifications(var1);
   }

   @KeepForSdk
   @ShowFirstParty
   public int getApkVersion(Context var1) {
      return GooglePlayServicesUtilLight.getApkVersion(var1);
   }

   @KeepForSdk
   @ShowFirstParty
   public int getClientVersion(Context var1) {
      return GooglePlayServicesUtilLight.getClientVersion(var1);
   }

   @Deprecated
   @Nullable
   @KeepForSdk
   @ShowFirstParty
   public Intent getErrorResolutionIntent(int var1) {
      return this.getErrorResolutionIntent((Context)null, var1, (String)null);
   }

   @Nullable
   @KeepForSdk
   @ShowFirstParty
   public Intent getErrorResolutionIntent(Context var1, int var2, @Nullable String var3) {
      switch(var2) {
      case 1:
      case 2:
         if(var1 != null && DeviceProperties.isWearableWithoutPlayStore(var1)) {
            return com.google.android.gms.common.internal.zzg.zzs();
         }

         return com.google.android.gms.common.internal.zzg.zza("com.google.android.gms", zza(var1, var3));
      case 3:
         return com.google.android.gms.common.internal.zzg.zzg("com.google.android.gms");
      default:
         return null;
      }
   }

   @Nullable
   @KeepForSdk
   public PendingIntent getErrorResolutionPendingIntent(Context var1, int var2, int var3) {
      return this.getErrorResolutionPendingIntent(var1, var2, var3, (String)null);
   }

   @Nullable
   @KeepForSdk
   @ShowFirstParty
   public PendingIntent getErrorResolutionPendingIntent(Context var1, int var2, int var3, @Nullable String var4) {
      Intent var5 = this.getErrorResolutionIntent(var1, var2, var4);
      return var5 == null?null:PendingIntent.getActivity(var1, var3, var5, 134217728);
   }

   @KeepForSdk
   public String getErrorString(int var1) {
      return GooglePlayServicesUtilLight.getErrorString(var1);
   }

   @KeepForSdk
   @HideFirstParty
   public int isGooglePlayServicesAvailable(Context var1) {
      return this.isGooglePlayServicesAvailable(var1, GOOGLE_PLAY_SERVICES_VERSION_CODE);
   }

   @KeepForSdk
   public int isGooglePlayServicesAvailable(Context var1, int var2) {
      int var3 = GooglePlayServicesUtilLight.isGooglePlayServicesAvailable(var1, var2);
      var2 = var3;
      if(GooglePlayServicesUtilLight.isPlayServicesPossiblyUpdating(var1, var3)) {
         var2 = 18;
      }

      return var2;
   }

   @KeepForSdk
   @ShowFirstParty
   public boolean isPlayServicesPossiblyUpdating(Context var1, int var2) {
      return GooglePlayServicesUtilLight.isPlayServicesPossiblyUpdating(var1, var2);
   }

   @KeepForSdk
   @ShowFirstParty
   public boolean isPlayStorePossiblyUpdating(Context var1, int var2) {
      return GooglePlayServicesUtilLight.isPlayStorePossiblyUpdating(var1, var2);
   }

   @KeepForSdk
   public boolean isUninstalledAppPossiblyUpdating(Context var1, String var2) {
      return GooglePlayServicesUtilLight.isUninstalledAppPossiblyUpdating(var1, var2);
   }

   @KeepForSdk
   public boolean isUserResolvableError(int var1) {
      return GooglePlayServicesUtilLight.isUserRecoverableError(var1);
   }

   @KeepForSdk
   public void verifyGooglePlayServicesIsAvailable(Context var1, int var2) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
      GooglePlayServicesUtilLight.ensurePlayServicesAvailable(var1, var2);
   }
}
