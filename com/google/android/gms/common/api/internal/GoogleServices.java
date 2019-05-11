package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.google.android.gms.common.R;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.StringResourceValueReader;
import com.google.android.gms.common.internal.zzp;
import com.google.android.gms.common.util.VisibleForTesting;
import javax.annotation.concurrent.GuardedBy;

@Deprecated
@KeepForSdk
public final class GoogleServices {

   private static final Object sLock = new Object();
   @GuardedBy
   private static GoogleServices zzax;
   private final String zzay;
   private final Status zzaz;
   private final boolean zzba;
   private final boolean zzbb;


   @KeepForSdk
   @VisibleForTesting
   GoogleServices(Context var1) {
      Resources var5 = var1.getResources();
      int var2 = var5.getIdentifier("google_app_measurement_enable", "integer", var5.getResourcePackageName(R.string.common_google_play_services_unknown_issue));
      boolean var4 = true;
      boolean var3 = true;
      if(var2 != 0) {
         if(var5.getInteger(var2) == 0) {
            var3 = false;
         }

         this.zzbb = var3 ^ true;
      } else {
         this.zzbb = false;
         var3 = var4;
      }

      this.zzba = var3;
      String var6 = zzp.zzc(var1);
      String var7 = var6;
      if(var6 == null) {
         var7 = (new StringResourceValueReader(var1)).getString("google_app_id");
      }

      if(TextUtils.isEmpty(var7)) {
         this.zzaz = new Status(10, "Missing google app id value from from string resources with name google_app_id.");
         this.zzay = null;
      } else {
         this.zzay = var7;
         this.zzaz = Status.RESULT_SUCCESS;
      }
   }

   @KeepForSdk
   @VisibleForTesting
   GoogleServices(String var1, boolean var2) {
      this.zzay = var1;
      this.zzaz = Status.RESULT_SUCCESS;
      this.zzba = var2;
      this.zzbb = var2 ^ true;
   }

   @KeepForSdk
   private static GoogleServices checkInitialized(String param0) {
      // $FF: Couldn't be decompiled
   }

   @KeepForSdk
   @VisibleForTesting
   static void clearInstanceForTest() {
      // $FF: Couldn't be decompiled
   }

   @KeepForSdk
   public static String getGoogleAppId() {
      return checkInitialized("getGoogleAppId").zzay;
   }

   @KeepForSdk
   public static Status initialize(Context param0) {
      // $FF: Couldn't be decompiled
   }

   @KeepForSdk
   public static Status initialize(Context param0, String param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   @KeepForSdk
   public static boolean isMeasurementEnabled() {
      GoogleServices var0 = checkInitialized("isMeasurementEnabled");
      return var0.zzaz.isSuccess() && var0.zzba;
   }

   @KeepForSdk
   public static boolean isMeasurementExplicitlyDisabled() {
      return checkInitialized("isMeasurementExplicitlyDisabled").zzbb;
   }

   @KeepForSdk
   @VisibleForTesting
   final Status checkGoogleAppId(String var1) {
      if(this.zzay != null && !this.zzay.equals(var1)) {
         var1 = this.zzay;
         StringBuilder var2 = new StringBuilder(String.valueOf(var1).length() + 97);
         var2.append("Initialize was called with two different Google App IDs.  Only the first app ID will be used: \'");
         var2.append(var1);
         var2.append("\'.");
         return new Status(10, var2.toString());
      } else {
         return Status.RESULT_SUCCESS;
      }
   }
}
