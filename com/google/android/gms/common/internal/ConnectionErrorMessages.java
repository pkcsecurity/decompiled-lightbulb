package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.base.R;
import com.google.android.gms.common.util.DeviceProperties;
import com.google.android.gms.common.wrappers.Wrappers;
import javax.annotation.concurrent.GuardedBy;

public final class ConnectionErrorMessages {

   @GuardedBy
   private static final SimpleArrayMap<String, String> zaof = new SimpleArrayMap();


   public static String getAppName(Context var0) {
      String var1 = var0.getPackageName();

      try {
         String var2 = Wrappers.packageManager(var0).getApplicationLabel(var1).toString();
         return var2;
      } catch (NullPointerException var3) {
         String var4 = var0.getApplicationInfo().name;
         return TextUtils.isEmpty(var4)?var1:var4;
      }
   }

   public static String getDefaultNotificationChannelName(Context var0) {
      return var0.getResources().getString(R.string.common_google_play_services_notification_channel_name);
   }

   @NonNull
   public static String getErrorDialogButtonMessage(Context var0, int var1) {
      Resources var2 = var0.getResources();
      switch(var1) {
      case 1:
         return var2.getString(R.string.common_google_play_services_install_button);
      case 2:
         return var2.getString(R.string.common_google_play_services_update_button);
      case 3:
         return var2.getString(R.string.common_google_play_services_enable_button);
      default:
         return var2.getString(17039370);
      }
   }

   @NonNull
   public static String getErrorMessage(Context var0, int var1) {
      Resources var2 = var0.getResources();
      String var3 = getAppName(var0);
      if(var1 != 5) {
         if(var1 != 7) {
            if(var1 != 9) {
               if(var1 != 20) {
                  switch(var1) {
                  case 1:
                     return var2.getString(R.string.common_google_play_services_install_text, new Object[]{var3});
                  case 2:
                     if(DeviceProperties.isWearableWithoutPlayStore(var0)) {
                        return var2.getString(R.string.common_google_play_services_wear_update_text);
                     }

                     return var2.getString(R.string.common_google_play_services_update_text, new Object[]{var3});
                  case 3:
                     return var2.getString(R.string.common_google_play_services_enable_text, new Object[]{var3});
                  default:
                     switch(var1) {
                     case 16:
                        return zaa(var0, "common_google_play_services_api_unavailable_text", var3);
                     case 17:
                        return zaa(var0, "common_google_play_services_sign_in_failed_text", var3);
                     case 18:
                        return var2.getString(R.string.common_google_play_services_updating_text, new Object[]{var3});
                     default:
                        return var2.getString(com.google.android.gms.common.R.common_google_play_services_unknown_issue, new Object[]{var3});
                     }
                  }
               } else {
                  return zaa(var0, "common_google_play_services_restricted_profile_text", var3);
               }
            } else {
               return var2.getString(R.string.common_google_play_services_unsupported_text, new Object[]{var3});
            }
         } else {
            return zaa(var0, "common_google_play_services_network_error_text", var3);
         }
      } else {
         return zaa(var0, "common_google_play_services_invalid_account_text", var3);
      }
   }

   @NonNull
   public static String getErrorNotificationMessage(Context var0, int var1) {
      return var1 == 6?zaa(var0, "common_google_play_services_resolution_required_text", getAppName(var0)):getErrorMessage(var0, var1);
   }

   @NonNull
   public static String getErrorNotificationTitle(Context var0, int var1) {
      String var2;
      if(var1 == 6) {
         var2 = zaa(var0, "common_google_play_services_resolution_required_title");
      } else {
         var2 = getErrorTitle(var0, var1);
      }

      String var3 = var2;
      if(var2 == null) {
         var3 = var0.getResources().getString(R.string.common_google_play_services_notification_ticker);
      }

      return var3;
   }

   @Nullable
   public static String getErrorTitle(Context var0, int var1) {
      Resources var2 = var0.getResources();
      if(var1 != 20) {
         switch(var1) {
         case 1:
            return var2.getString(R.string.common_google_play_services_install_title);
         case 2:
            return var2.getString(R.string.common_google_play_services_update_title);
         case 3:
            return var2.getString(R.string.common_google_play_services_enable_title);
         case 5:
            Log.e("GoogleApiAvailability", "An invalid account was specified when connecting. Please provide a valid account.");
            return zaa(var0, "common_google_play_services_invalid_account_title");
         case 7:
            Log.e("GoogleApiAvailability", "Network error occurred. Please retry request later.");
            return zaa(var0, "common_google_play_services_network_error_title");
         case 8:
            Log.e("GoogleApiAvailability", "Internal error occurred. Please see logs for detailed information");
            return null;
         case 9:
            Log.e("GoogleApiAvailability", "Google Play services is invalid. Cannot recover.");
            return null;
         case 10:
            Log.e("GoogleApiAvailability", "Developer error occurred. Please see logs for detailed information");
            return null;
         case 11:
            Log.e("GoogleApiAvailability", "The application is not licensed to the user.");
            return null;
         default:
            switch(var1) {
            case 16:
               Log.e("GoogleApiAvailability", "One of the API components you attempted to connect to is not available.");
               return null;
            case 17:
               Log.e("GoogleApiAvailability", "The specified account could not be signed in.");
               return zaa(var0, "common_google_play_services_sign_in_failed_title");
            case 18:
               break;
            default:
               StringBuilder var3 = new StringBuilder(33);
               var3.append("Unexpected error code ");
               var3.append(var1);
               Log.e("GoogleApiAvailability", var3.toString());
               return null;
            }
         case 4:
         case 6:
            return null;
         }
      } else {
         Log.e("GoogleApiAvailability", "The current user profile is restricted and could not use authenticated features.");
         return zaa(var0, "common_google_play_services_restricted_profile_title");
      }
   }

   @Nullable
   private static String zaa(Context param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   private static String zaa(Context var0, String var1, String var2) {
      Resources var3 = var0.getResources();
      var1 = zaa(var0, var1);
      String var4 = var1;
      if(var1 == null) {
         var4 = var3.getString(com.google.android.gms.common.R.common_google_play_services_unknown_issue);
      }

      return String.format(var3.getConfiguration().locale, var4, new Object[]{var2});
   }
}
