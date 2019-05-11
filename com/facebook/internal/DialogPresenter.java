package com.facebook.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.facebook.FacebookActivity;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.AppCall;
import com.facebook.internal.DialogFeature;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.FragmentWrapper;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;

public class DialogPresenter {

   public static boolean canPresentNativeDialogWithFeature(DialogFeature var0) {
      return getProtocolVersionForNativeDialog(var0).getProtocolVersion() != -1;
   }

   public static boolean canPresentWebFallbackDialogWithFeature(DialogFeature var0) {
      return getDialogWebFallbackUri(var0) != null;
   }

   private static Uri getDialogWebFallbackUri(DialogFeature var0) {
      String var1 = var0.name();
      String var2 = var0.getAction();
      FetchedAppSettings.DialogFeatureConfig var3 = FetchedAppSettings.getDialogFeatureConfig(FacebookSdk.getApplicationId(), var2, var1);
      return var3 != null?var3.getFallbackUrl():null;
   }

   public static NativeProtocol.ProtocolVersionQueryResult getProtocolVersionForNativeDialog(DialogFeature var0) {
      String var1 = FacebookSdk.getApplicationId();
      String var2 = var0.getAction();
      return NativeProtocol.getLatestAvailableProtocolVersionForAction(var2, getVersionSpecForFeature(var1, var2, var0));
   }

   private static int[] getVersionSpecForFeature(String var0, String var1, DialogFeature var2) {
      FetchedAppSettings.DialogFeatureConfig var3 = FetchedAppSettings.getDialogFeatureConfig(var0, var1, var2.name());
      return var3 != null?var3.getVersionSpec():new int[]{var2.getMinVersion()};
   }

   public static void logDialogActivity(Context var0, String var1, String var2) {
      AppEventsLogger var4 = AppEventsLogger.newLogger(var0);
      Bundle var3 = new Bundle();
      var3.putString("fb_dialog_outcome", var2);
      var4.logSdkEvent(var1, (Double)null, var3);
   }

   public static void present(AppCall var0, Activity var1) {
      var1.startActivityForResult(var0.getRequestIntent(), var0.getRequestCode());
      var0.setPending();
   }

   public static void present(AppCall var0, FragmentWrapper var1) {
      var1.startActivityForResult(var0.getRequestIntent(), var0.getRequestCode());
      var0.setPending();
   }

   public static void setupAppCallForCannotShowError(AppCall var0) {
      setupAppCallForValidationError(var0, new FacebookException("Unable to show the provided content via the web or the installed version of the Facebook app. Some dialogs are only supported starting API 14."));
   }

   public static void setupAppCallForErrorResult(AppCall var0, FacebookException var1) {
      if(var1 != null) {
         Validate.hasFacebookActivity(FacebookSdk.getApplicationContext());
         Intent var2 = new Intent();
         var2.setClass(FacebookSdk.getApplicationContext(), FacebookActivity.class);
         var2.setAction(FacebookActivity.PASS_THROUGH_CANCEL_ACTION);
         NativeProtocol.setupProtocolRequestIntent(var2, var0.getCallId().toString(), (String)null, NativeProtocol.getLatestKnownVersion(), NativeProtocol.createBundleForException(var1));
         var0.setRequestIntent(var2);
      }
   }

   public static void setupAppCallForNativeDialog(AppCall var0, DialogPresenter.ParameterProvider var1, DialogFeature var2) {
      Context var4 = FacebookSdk.getApplicationContext();
      String var5 = var2.getAction();
      NativeProtocol.ProtocolVersionQueryResult var6 = getProtocolVersionForNativeDialog(var2);
      int var3 = var6.getProtocolVersion();
      if(var3 == -1) {
         throw new FacebookException("Cannot present this dialog. This likely means that the Facebook app is not installed.");
      } else {
         Bundle var7;
         if(NativeProtocol.isVersionCompatibleWithBucketedIntent(var3)) {
            var7 = var1.getParameters();
         } else {
            var7 = var1.getLegacyParameters();
         }

         Bundle var8 = var7;
         if(var7 == null) {
            var8 = new Bundle();
         }

         Intent var9 = NativeProtocol.createPlatformActivityIntent(var4, var0.getCallId().toString(), var5, var6, var8);
         if(var9 == null) {
            throw new FacebookException("Unable to create Intent; this likely means theFacebook app is not installed.");
         } else {
            var0.setRequestIntent(var9);
         }
      }
   }

   public static void setupAppCallForValidationError(AppCall var0, FacebookException var1) {
      setupAppCallForErrorResult(var0, var1);
   }

   public static void setupAppCallForWebDialog(AppCall var0, String var1, Bundle var2) {
      Validate.hasFacebookActivity(FacebookSdk.getApplicationContext());
      Validate.hasInternetPermissions(FacebookSdk.getApplicationContext());
      Bundle var3 = new Bundle();
      var3.putString("action", var1);
      var3.putBundle("params", var2);
      Intent var4 = new Intent();
      NativeProtocol.setupProtocolRequestIntent(var4, var0.getCallId().toString(), var1, NativeProtocol.getLatestKnownVersion(), var3);
      var4.setClass(FacebookSdk.getApplicationContext(), FacebookActivity.class);
      var4.setAction("FacebookDialogFragment");
      var0.setRequestIntent(var4);
   }

   public static void setupAppCallForWebFallbackDialog(AppCall var0, Bundle var1, DialogFeature var2) {
      Validate.hasFacebookActivity(FacebookSdk.getApplicationContext());
      Validate.hasInternetPermissions(FacebookSdk.getApplicationContext());
      String var4 = var2.name();
      Uri var5 = getDialogWebFallbackUri(var2);
      if(var5 == null) {
         StringBuilder var6 = new StringBuilder();
         var6.append("Unable to fetch the Url for the DialogFeature : \'");
         var6.append(var4);
         var6.append("\'");
         throw new FacebookException(var6.toString());
      } else {
         int var3 = NativeProtocol.getLatestKnownVersion();
         var1 = ServerProtocol.getQueryParamsForPlatformActivityIntentWebFallback(var0.getCallId().toString(), var3, var1);
         if(var1 == null) {
            throw new FacebookException("Unable to fetch the app\'s key-hash");
         } else {
            Uri var7;
            if(var5.isRelative()) {
               var7 = Utility.buildUri(ServerProtocol.getDialogAuthority(), var5.toString(), var1);
            } else {
               var7 = Utility.buildUri(var5.getAuthority(), var5.getPath(), var1);
            }

            Bundle var9 = new Bundle();
            var9.putString("url", var7.toString());
            var9.putBoolean("is_fallback", true);
            Intent var8 = new Intent();
            NativeProtocol.setupProtocolRequestIntent(var8, var0.getCallId().toString(), var2.getAction(), NativeProtocol.getLatestKnownVersion(), var9);
            var8.setClass(FacebookSdk.getApplicationContext(), FacebookActivity.class);
            var8.setAction("FacebookDialogFragment");
            var0.setRequestIntent(var8);
         }
      }
   }

   public interface ParameterProvider {

      Bundle getLegacyParameters();

      Bundle getParameters();
   }
}
