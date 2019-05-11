package com.google.android.gms.common;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GoogleSignatureVerifier;
import com.google.android.gms.common.R;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.HideFirstParty;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.zzp;
import com.google.android.gms.common.util.ClientLibraryUtils;
import com.google.android.gms.common.util.DeviceProperties;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.common.util.UidVerifier;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.concurrent.atomic.AtomicBoolean;

@KeepForSdk
@ShowFirstParty
public class GooglePlayServicesUtilLight {

   @KeepForSdk
   static final int GMS_AVAILABILITY_NOTIFICATION_ID = 10436;
   @KeepForSdk
   static final int GMS_GENERAL_ERROR_NOTIFICATION_ID = 39789;
   @KeepForSdk
   public static final String GOOGLE_PLAY_GAMES_PACKAGE = "com.google.android.play.games";
   @Deprecated
   @KeepForSdk
   public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
   @Deprecated
   @KeepForSdk
   public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = 12451000;
   @KeepForSdk
   public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";
   @KeepForSdk
   @VisibleForTesting
   static final AtomicBoolean sCanceledAvailabilityNotification = new AtomicBoolean();
   @VisibleForTesting
   private static boolean zzag;
   @VisibleForTesting
   private static boolean zzah;
   private static boolean zzai;
   @VisibleForTesting
   private static boolean zzaj;
   private static final AtomicBoolean zzak = new AtomicBoolean();


   @Deprecated
   @KeepForSdk
   public static void cancelAvailabilityErrorNotifications(Context param0) {
      // $FF: Couldn't be decompiled
   }

   @KeepForSdk
   @ShowFirstParty
   public static void enableUsingApkIndependentContext() {
      zzak.set(true);
   }

   @Deprecated
   @KeepForSdk
   public static void ensurePlayServicesAvailable(Context var0, int var1) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
      var1 = GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable(var0, var1);
      if(var1 != 0) {
         Intent var3 = GoogleApiAvailabilityLight.getInstance().getErrorResolutionIntent(var0, var1, "e");
         StringBuilder var2 = new StringBuilder(57);
         var2.append("GooglePlayServices not available due to error ");
         var2.append(var1);
         Log.e("GooglePlayServicesUtil", var2.toString());
         if(var3 == null) {
            throw new GooglePlayServicesNotAvailableException(var1);
         } else {
            throw new GooglePlayServicesRepairableException(var1, "Google Play Services not available", var3);
         }
      }
   }

   @Deprecated
   @KeepForSdk
   @ShowFirstParty
   public static int getApkVersion(Context var0) {
      PackageInfo var2;
      try {
         var2 = var0.getPackageManager().getPackageInfo("com.google.android.gms", 0);
      } catch (NameNotFoundException var1) {
         Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
         return 0;
      }

      return var2.versionCode;
   }

   @Deprecated
   @KeepForSdk
   @ShowFirstParty
   public static int getClientVersion(Context var0) {
      Preconditions.checkState(true);
      return ClientLibraryUtils.getClientVersion(var0, var0.getPackageName());
   }

   @Deprecated
   @KeepForSdk
   public static PendingIntent getErrorPendingIntent(int var0, Context var1, int var2) {
      return GoogleApiAvailabilityLight.getInstance().getErrorResolutionPendingIntent(var1, var0, var2);
   }

   @Deprecated
   @KeepForSdk
   @VisibleForTesting
   public static String getErrorString(int var0) {
      return ConnectionResult.zza(var0);
   }

   @Deprecated
   @KeepForSdk
   @ShowFirstParty
   public static Intent getGooglePlayServicesAvailabilityRecoveryIntent(int var0) {
      return GoogleApiAvailabilityLight.getInstance().getErrorResolutionIntent((Context)null, var0, (String)null);
   }

   @KeepForSdk
   public static Context getRemoteContext(Context var0) {
      try {
         var0 = var0.createPackageContext("com.google.android.gms", 3);
         return var0;
      } catch (NameNotFoundException var1) {
         return null;
      }
   }

   @KeepForSdk
   public static Resources getRemoteResource(Context var0) {
      try {
         Resources var2 = var0.getPackageManager().getResourcesForApplication("com.google.android.gms");
         return var2;
      } catch (NameNotFoundException var1) {
         return null;
      }
   }

   @KeepForSdk
   @ShowFirstParty
   public static boolean honorsDebugCertificates(Context param0) {
      // $FF: Couldn't be decompiled
   }

   @Deprecated
   @KeepForSdk
   @HideFirstParty
   public static int isGooglePlayServicesAvailable(Context var0) {
      return isGooglePlayServicesAvailable(var0, GOOGLE_PLAY_SERVICES_VERSION_CODE);
   }

   @Deprecated
   @KeepForSdk
   public static int isGooglePlayServicesAvailable(Context var0, int var1) {
      try {
         var0.getResources().getString(R.string.common_google_play_services_unknown_issue);
      } catch (Throwable var5) {
         Log.e("GooglePlayServicesUtil", "The Google Play services resources were not found. Check your project configuration to ensure that the resources are included.");
      }

      if(!"com.google.android.gms".equals(var0.getPackageName()) && !zzak.get()) {
         int var2 = zzp.zzd(var0);
         if(var2 == 0) {
            throw new IllegalStateException("A required meta-data tag in your app\'s AndroidManifest.xml does not exist.  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />");
         }

         if(var2 != GOOGLE_PLAY_SERVICES_VERSION_CODE) {
            var1 = GOOGLE_PLAY_SERVICES_VERSION_CODE;
            StringBuilder var6 = new StringBuilder(320);
            var6.append("The meta-data tag in your app\'s AndroidManifest.xml does not have the right value.  Expected ");
            var6.append(var1);
            var6.append(" but found ");
            var6.append(var2);
            var6.append(".  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />");
            throw new IllegalStateException(var6.toString());
         }
      }

      boolean var3;
      if(!DeviceProperties.isWearableWithoutPlayStore(var0) && !DeviceProperties.zzf(var0)) {
         var3 = true;
      } else {
         var3 = false;
      }

      return zza(var0, var3, var1);
   }

   @Deprecated
   @KeepForSdk
   public static boolean isGooglePlayServicesUid(Context var0, int var1) {
      return UidVerifier.isGooglePlayServicesUid(var0, var1);
   }

   @Deprecated
   @KeepForSdk
   @ShowFirstParty
   public static boolean isPlayServicesPossiblyUpdating(Context var0, int var1) {
      return var1 == 18?true:(var1 == 1?isUninstalledAppPossiblyUpdating(var0, "com.google.android.gms"):false);
   }

   @Deprecated
   @KeepForSdk
   @ShowFirstParty
   public static boolean isPlayStorePossiblyUpdating(Context var0, int var1) {
      return var1 == 9?isUninstalledAppPossiblyUpdating(var0, "com.android.vending"):false;
   }

   @TargetApi(18)
   @KeepForSdk
   public static boolean isRestrictedUserProfile(Context var0) {
      if(PlatformVersion.isAtLeastJellyBeanMR2()) {
         Bundle var1 = ((UserManager)var0.getSystemService("user")).getApplicationRestrictions(var0.getPackageName());
         if(var1 != null && "true".equals(var1.getString("restricted_profile"))) {
            return true;
         }
      }

      return false;
   }

   @Deprecated
   @KeepForSdk
   @ShowFirstParty
   @VisibleForTesting
   public static boolean isSidewinderDevice(Context var0) {
      return DeviceProperties.isSidewinder(var0);
   }

   @TargetApi(21)
   static boolean isUninstalledAppPossiblyUpdating(Context param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   @Deprecated
   @KeepForSdk
   public static boolean isUserRecoverableError(int var0) {
      if(var0 != 9) {
         switch(var0) {
         case 1:
         case 2:
         case 3:
            break;
         default:
            return false;
         }
      }

      return true;
   }

   @Deprecated
   @TargetApi(19)
   @KeepForSdk
   public static boolean uidHasPackageName(Context var0, int var1, String var2) {
      return UidVerifier.uidHasPackageName(var0, var1, var2);
   }

   @VisibleForTesting
   private static int zza(Context var0, boolean var1, int var2) {
      boolean var4;
      if(var2 >= 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4);
      PackageManager var6 = var0.getPackageManager();
      PackageInfo var5 = null;
      if(var1) {
         try {
            var5 = var6.getPackageInfo("com.android.vending", 8256);
         } catch (NameNotFoundException var10) {
            Log.w("GooglePlayServicesUtil", "Google Play Store is missing.");
            return 9;
         }
      }

      PackageInfo var7;
      try {
         var7 = var6.getPackageInfo("com.google.android.gms", 64);
      } catch (NameNotFoundException var9) {
         Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
         return 1;
      }

      GoogleSignatureVerifier.getInstance(var0);
      if(!GoogleSignatureVerifier.zza(var7, true)) {
         Log.w("GooglePlayServicesUtil", "Google Play services signature invalid.");
         return 9;
      } else if(var1 && (!GoogleSignatureVerifier.zza(var5, true) || !var5.signatures[0].equals(var7.signatures[0]))) {
         Log.w("GooglePlayServicesUtil", "Google Play Store signature invalid.");
         return 9;
      } else if(com.google.android.gms.common.util.zzb.zzc(var7.versionCode) < com.google.android.gms.common.util.zzb.zzc(var2)) {
         int var3 = var7.versionCode;
         StringBuilder var12 = new StringBuilder(77);
         var12.append("Google Play services out of date.  Requires ");
         var12.append(var2);
         var12.append(" but found ");
         var12.append(var3);
         Log.w("GooglePlayServicesUtil", var12.toString());
         return 2;
      } else {
         ApplicationInfo var13 = var7.applicationInfo;
         ApplicationInfo var11 = var13;
         if(var13 == null) {
            try {
               var11 = var6.getApplicationInfo("com.google.android.gms", 0);
            } catch (NameNotFoundException var8) {
               Log.wtf("GooglePlayServicesUtil", "Google Play services missing when getting application info.", var8);
               return 1;
            }
         }

         return !var11.enabled?3:0;
      }
   }
}
