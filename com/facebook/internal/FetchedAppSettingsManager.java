package com.facebook.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.appevents.codeless.internal.UnityReflection;
import com.facebook.appevents.internal.AutomaticAnalyticsLogger;
import com.facebook.appevents.internal.Constants;
import com.facebook.appevents.internal.InAppPurchaseActivityLifecycleTracker;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.facebook.internal.FetchedAppGateKeepersManager;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.InternalSettings;
import com.facebook.internal.SmartLoginOption;
import com.facebook.internal.Utility;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class FetchedAppSettingsManager {

   private static final String ADVERTISER_ID_KEY = "advertiser_id";
   private static final String APPLICATION_FIELDS = "fields";
   private static final String APP_SETTINGS_PREFS_KEY_FORMAT = "com.facebook.internal.APP_SETTINGS.%s";
   private static final String APP_SETTINGS_PREFS_STORE = "com.facebook.internal.preferences.APP_SETTINGS";
   private static final String APP_SETTING_ANDROID_SDK_ERROR_CATEGORIES = "android_sdk_error_categories";
   private static final String APP_SETTING_APP_EVENTS_CODELESS_SETUP_ENABLED = "auto_event_setup_enabled";
   private static final String APP_SETTING_APP_EVENTS_EVENT_BINDINGS = "auto_event_mapping_android";
   private static final String APP_SETTING_APP_EVENTS_FEATURE_BITMASK = "app_events_feature_bitmask";
   private static final String APP_SETTING_APP_EVENTS_SESSION_TIMEOUT = "app_events_session_timeout";
   private static final String APP_SETTING_CUSTOM_TABS_ENABLED = "gdpv4_chrome_custom_tabs_enabled";
   private static final String APP_SETTING_DIALOG_CONFIGS = "android_dialog_configs";
   private static final String[] APP_SETTING_FIELDS = new String[]{"supports_implicit_sdk_logging", "gdpv4_nux_content", "gdpv4_nux_enabled", "gdpv4_chrome_custom_tabs_enabled", "android_dialog_configs", "android_sdk_error_categories", "app_events_session_timeout", "app_events_feature_bitmask", "auto_event_mapping_android", "auto_event_setup_enabled", "seamless_login", "smart_login_bookmark_icon_url", "smart_login_menu_icon_url"};
   private static final String APP_SETTING_NUX_CONTENT = "gdpv4_nux_content";
   private static final String APP_SETTING_NUX_ENABLED = "gdpv4_nux_enabled";
   private static final String APP_SETTING_SMART_LOGIN_OPTIONS = "seamless_login";
   private static final String APP_SETTING_SUPPORTS_IMPLICIT_SDK_LOGGING = "supports_implicit_sdk_logging";
   private static final int AUTOMATIC_LOGGING_ENABLED_BITMASK_FIELD = 8;
   private static final int CODELESS_EVENTS_ENABLED_BITMASK_FIELD = 32;
   private static final int IAP_AUTOMATIC_LOGGING_ENABLED_BITMASK_FIELD = 16;
   private static final String SDK_UPDATE_MESSAGE = "sdk_update_message";
   private static final String SMART_LOGIN_BOOKMARK_ICON_URL = "smart_login_bookmark_icon_url";
   private static final String SMART_LOGIN_MENU_ICON_URL = "smart_login_menu_icon_url";
   private static final String TAG = "FetchedAppSettingsManager";
   private static final int TRACK_UNINSTALL_ENABLED_BITMASK_FIELD = 256;
   private static final Map<String, FetchedAppSettings> fetchedAppSettings = new ConcurrentHashMap();
   private static final ConcurrentLinkedQueue<FetchedAppSettingsManager.FetchedAppSettingsCallback> fetchedAppSettingsCallbacks = new ConcurrentLinkedQueue();
   private static boolean isUnityInit;
   private static final AtomicReference<FetchedAppSettingsManager.FetchAppSettingState> loadingState = new AtomicReference(FetchedAppSettingsManager.FetchAppSettingState.NOT_LOADED);
   private static boolean printedSDKUpdatedMessage;
   @Nullable
   private static JSONArray unityEventBindings;


   public static void getAppSettingsAsync(FetchedAppSettingsManager.FetchedAppSettingsCallback var0) {
      fetchedAppSettingsCallbacks.add(var0);
      loadAppSettingsAsync();
   }

   private static JSONObject getAppSettingsQueryResponse(String var0) {
      Bundle var1 = new Bundle();
      var1.putString("fields", TextUtils.join(",", new ArrayList(Arrays.asList(APP_SETTING_FIELDS))));
      AttributionIdentifiers var2 = AttributionIdentifiers.getAttributionIdentifiers(FacebookSdk.getApplicationContext());
      if(var2 != null && var2.getAndroidAdvertiserId() != null) {
         var1.putString("advertiser_id", var2.getAndroidAdvertiserId());
      }

      GraphRequest var3 = GraphRequest.newGraphPathRequest((AccessToken)null, var0, (GraphRequest.Callback)null);
      var3.setSkipClientToken(true);
      var3.setParameters(var1);
      return var3.executeAndWait().getJSONObject();
   }

   public static FetchedAppSettings getAppSettingsWithoutQuery(String var0) {
      return var0 != null?(FetchedAppSettings)fetchedAppSettings.get(var0):null;
   }

   public static void loadAppSettingsAsync() {
      final Context var1 = FacebookSdk.getApplicationContext();
      final String var2 = FacebookSdk.getApplicationId();
      if(Utility.isNullOrEmpty(var2)) {
         loadingState.set(FetchedAppSettingsManager.FetchAppSettingState.ERROR);
         pollCallbacks();
      } else if(fetchedAppSettings.containsKey(var2)) {
         loadingState.set(FetchedAppSettingsManager.FetchAppSettingState.SUCCESS);
         pollCallbacks();
      } else {
         boolean var0;
         if(!loadingState.compareAndSet(FetchedAppSettingsManager.FetchAppSettingState.NOT_LOADED, FetchedAppSettingsManager.FetchAppSettingState.LOADING) && !loadingState.compareAndSet(FetchedAppSettingsManager.FetchAppSettingState.ERROR, FetchedAppSettingsManager.FetchAppSettingState.LOADING)) {
            var0 = false;
         } else {
            var0 = true;
         }

         if(!var0) {
            pollCallbacks();
         } else {
            final String var3 = String.format("com.facebook.internal.APP_SETTINGS.%s", new Object[]{var2});
            FacebookSdk.getExecutor().execute(new Runnable() {
               public void run() {
                  SharedPreferences var4 = var1.getSharedPreferences("com.facebook.internal.preferences.APP_SETTINGS", 0);
                  String var1x = var3;
                  Object var3x = null;
                  String var2x = var4.getString(var1x, (String)null);
                  FetchedAppSettings var6 = (FetchedAppSettings)var3x;
                  JSONObject var7;
                  if(!Utility.isNullOrEmpty(var2x)) {
                     try {
                        var7 = new JSONObject(var2x);
                     } catch (JSONException var5) {
                        Utility.logd("FacebookSDK", (Exception)var5);
                        var7 = null;
                     }

                     var6 = (FetchedAppSettings)var3x;
                     if(var7 != null) {
                        var6 = FetchedAppSettingsManager.parseAppSettingsFromJSON(var2, var7);
                     }
                  }

                  var7 = FetchedAppSettingsManager.getAppSettingsQueryResponse(var2);
                  if(var7 != null) {
                     FetchedAppSettingsManager.parseAppSettingsFromJSON(var2, var7);
                     var4.edit().putString(var3, var7.toString()).apply();
                  }

                  if(var6 != null) {
                     var1x = var6.getSdkUpdateMessage();
                     if(!FetchedAppSettingsManager.printedSDKUpdatedMessage && var1x != null && var1x.length() > 0) {
                        FetchedAppSettingsManager.printedSDKUpdatedMessage = true;
                        Log.w(FetchedAppSettingsManager.TAG, var1x);
                     }
                  }

                  FetchedAppGateKeepersManager.queryAppGateKeepers(var2, true);
                  AutomaticAnalyticsLogger.logActivateAppEvent();
                  InAppPurchaseActivityLifecycleTracker.update();
                  AtomicReference var9 = FetchedAppSettingsManager.loadingState;
                  FetchedAppSettingsManager.FetchAppSettingState var8;
                  if(FetchedAppSettingsManager.fetchedAppSettings.containsKey(var2)) {
                     var8 = FetchedAppSettingsManager.FetchAppSettingState.SUCCESS;
                  } else {
                     var8 = FetchedAppSettingsManager.FetchAppSettingState.ERROR;
                  }

                  var9.set(var8);
                  FetchedAppSettingsManager.pollCallbacks();
               }
            });
         }
      }
   }

   private static FetchedAppSettings parseAppSettingsFromJSON(String var0, JSONObject var1) {
      JSONArray var8 = var1.optJSONArray("android_sdk_error_categories");
      FacebookRequestErrorClassification var11;
      if(var8 == null) {
         var11 = FacebookRequestErrorClassification.getDefaultErrorClassification();
      } else {
         var11 = FacebookRequestErrorClassification.createFromJSON(var8);
      }

      int var2 = var1.optInt("app_events_feature_bitmask", 0);
      boolean var3;
      if((var2 & 8) != 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      boolean var4;
      if((var2 & 16) != 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      boolean var5;
      if((var2 & 32) != 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      boolean var6;
      if((var2 & 256) != 0) {
         var6 = true;
      } else {
         var6 = false;
      }

      boolean var7 = var1.optBoolean("auto_event_setup_enabled", false);
      JSONArray var9 = var1.optJSONArray("auto_event_mapping_android");
      unityEventBindings = var9;
      if(unityEventBindings != null && InternalSettings.isUnityApp()) {
         UnityReflection.sendEventMapping(var9.toString());
      }

      FetchedAppSettings var10 = new FetchedAppSettings(var1.optBoolean("supports_implicit_sdk_logging", false), var1.optString("gdpv4_nux_content", ""), var1.optBoolean("gdpv4_nux_enabled", false), var1.optBoolean("gdpv4_chrome_custom_tabs_enabled", false), var1.optInt("app_events_session_timeout", Constants.getDefaultAppEventsSessionTimeoutInSeconds()), SmartLoginOption.parseOptions(var1.optLong("seamless_login")), parseDialogConfigurations(var1.optJSONObject("android_dialog_configs")), var3, var11, var1.optString("smart_login_bookmark_icon_url"), var1.optString("smart_login_menu_icon_url"), var4, var5, var9, var1.optString("sdk_update_message"), var6, var7);
      fetchedAppSettings.put(var0, var10);
      return var10;
   }

   private static Map<String, Map<String, FetchedAppSettings.DialogFeatureConfig>> parseDialogConfigurations(JSONObject var0) {
      HashMap var3 = new HashMap();
      if(var0 != null) {
         JSONArray var4 = var0.optJSONArray("data");
         if(var4 != null) {
            for(int var1 = 0; var1 < var4.length(); ++var1) {
               FetchedAppSettings.DialogFeatureConfig var5 = FetchedAppSettings.DialogFeatureConfig.parseDialogConfig(var4.optJSONObject(var1));
               if(var5 != null) {
                  String var6 = var5.getDialogName();
                  Map var2 = (Map)var3.get(var6);
                  Object var7 = var2;
                  if(var2 == null) {
                     var7 = new HashMap();
                     var3.put(var6, var7);
                  }

                  ((Map)var7).put(var5.getFeatureName(), var5);
               }
            }
         }
      }

      return var3;
   }

   private static void pollCallbacks() {
      synchronized(FetchedAppSettingsManager.class){}

      try {
         FetchedAppSettingsManager.FetchAppSettingState var0 = (FetchedAppSettingsManager.FetchAppSettingState)loadingState.get();
         if(!FetchedAppSettingsManager.FetchAppSettingState.NOT_LOADED.equals(var0) && !FetchedAppSettingsManager.FetchAppSettingState.LOADING.equals(var0)) {
            String var1 = FacebookSdk.getApplicationId();
            final FetchedAppSettings var5 = (FetchedAppSettings)fetchedAppSettings.get(var1);
            Handler var2 = new Handler(Looper.getMainLooper());
            if(!FetchedAppSettingsManager.FetchAppSettingState.ERROR.equals(var0)) {
               while(!fetchedAppSettingsCallbacks.isEmpty()) {
                  var2.post(new Runnable() {

                     // $FF: synthetic field
                     final FetchedAppSettingsManager.FetchedAppSettingsCallback val$callback;

                     {
                        this.val$callback = var1;
                     }
                     public void run() {
                        this.val$callback.onSuccess(var5);
                     }
                  });
               }

               return;
            }

            while(!fetchedAppSettingsCallbacks.isEmpty()) {
               var2.post(new Runnable() {

                  // $FF: synthetic field
                  final FetchedAppSettingsManager.FetchedAppSettingsCallback val$callback;

                  {
                     this.val$callback = var1;
                  }
                  public void run() {
                     this.val$callback.onError();
                  }
               });
            }

            return;
         }
      } finally {
         ;
      }

   }

   public static FetchedAppSettings queryAppSettings(String var0, boolean var1) {
      if(!var1 && fetchedAppSettings.containsKey(var0)) {
         return (FetchedAppSettings)fetchedAppSettings.get(var0);
      } else {
         JSONObject var2 = getAppSettingsQueryResponse(var0);
         if(var2 == null) {
            return null;
         } else {
            FetchedAppSettings var3 = parseAppSettingsFromJSON(var0, var2);
            if(var0.equals(FacebookSdk.getApplicationId())) {
               loadingState.set(FetchedAppSettingsManager.FetchAppSettingState.SUCCESS);
               pollCallbacks();
            }

            return var3;
         }
      }
   }

   public static void setIsUnityInit(boolean var0) {
      isUnityInit = var0;
      if(unityEventBindings != null && isUnityInit) {
         UnityReflection.sendEventMapping(unityEventBindings.toString());
      }

   }

   public interface FetchedAppSettingsCallback {

      void onError();

      void onSuccess(FetchedAppSettings var1);
   }

   static enum FetchAppSettingState {

      // $FF: synthetic field
      private static final FetchedAppSettingsManager.FetchAppSettingState[] $VALUES = new FetchedAppSettingsManager.FetchAppSettingState[]{NOT_LOADED, LOADING, SUCCESS, ERROR};
      ERROR("ERROR", 3),
      LOADING("LOADING", 1),
      NOT_LOADED("NOT_LOADED", 0),
      SUCCESS("SUCCESS", 2);


      private FetchAppSettingState(String var1, int var2) {}
   }
}
