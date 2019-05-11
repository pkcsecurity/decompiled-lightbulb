package com.facebook.internal;

import android.net.Uri;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.SmartLoginOption;
import com.facebook.internal.Utility;
import java.util.EnumSet;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public final class FetchedAppSettings {

   private boolean IAPAutomaticLoggingEnabled;
   private boolean automaticLoggingEnabled;
   private boolean codelessEventsEnabled;
   private boolean codelessSetupEnabled;
   private boolean customTabsEnabled;
   private Map<String, Map<String, FetchedAppSettings.DialogFeatureConfig>> dialogConfigMap;
   private FacebookRequestErrorClassification errorClassification;
   private JSONArray eventBindings;
   private String nuxContent;
   private boolean nuxEnabled;
   private String sdkUpdateMessage;
   private int sessionTimeoutInSeconds;
   private String smartLoginBookmarkIconURL;
   private String smartLoginMenuIconURL;
   private EnumSet<SmartLoginOption> smartLoginOptions;
   private boolean supportsImplicitLogging;
   private boolean trackUninstallEnabled;


   public FetchedAppSettings(boolean var1, String var2, boolean var3, boolean var4, int var5, EnumSet<SmartLoginOption> var6, Map<String, Map<String, FetchedAppSettings.DialogFeatureConfig>> var7, boolean var8, FacebookRequestErrorClassification var9, String var10, String var11, boolean var12, boolean var13, JSONArray var14, String var15, boolean var16, boolean var17) {
      this.supportsImplicitLogging = var1;
      this.nuxContent = var2;
      this.nuxEnabled = var3;
      this.customTabsEnabled = var4;
      this.dialogConfigMap = var7;
      this.errorClassification = var9;
      this.sessionTimeoutInSeconds = var5;
      this.automaticLoggingEnabled = var8;
      this.smartLoginOptions = var6;
      this.smartLoginBookmarkIconURL = var10;
      this.smartLoginMenuIconURL = var11;
      this.IAPAutomaticLoggingEnabled = var12;
      this.codelessEventsEnabled = var13;
      this.eventBindings = var14;
      this.sdkUpdateMessage = var15;
      this.trackUninstallEnabled = var16;
      this.codelessSetupEnabled = var17;
   }

   public static FetchedAppSettings.DialogFeatureConfig getDialogFeatureConfig(String var0, String var1, String var2) {
      if(!Utility.isNullOrEmpty(var1)) {
         if(Utility.isNullOrEmpty(var2)) {
            return null;
         } else {
            FetchedAppSettings var3 = FetchedAppSettingsManager.getAppSettingsWithoutQuery(var0);
            if(var3 != null) {
               Map var4 = (Map)var3.getDialogConfigurations().get(var1);
               if(var4 != null) {
                  return (FetchedAppSettings.DialogFeatureConfig)var4.get(var2);
               }
            }

            return null;
         }
      } else {
         return null;
      }
   }

   public boolean getAutomaticLoggingEnabled() {
      return this.automaticLoggingEnabled;
   }

   public boolean getCodelessEventsEnabled() {
      return this.codelessEventsEnabled;
   }

   public boolean getCodelessSetupEnabled() {
      return this.codelessSetupEnabled;
   }

   public boolean getCustomTabsEnabled() {
      return this.customTabsEnabled;
   }

   public Map<String, Map<String, FetchedAppSettings.DialogFeatureConfig>> getDialogConfigurations() {
      return this.dialogConfigMap;
   }

   public FacebookRequestErrorClassification getErrorClassification() {
      return this.errorClassification;
   }

   public JSONArray getEventBindings() {
      return this.eventBindings;
   }

   public boolean getIAPAutomaticLoggingEnabled() {
      return this.IAPAutomaticLoggingEnabled;
   }

   public String getNuxContent() {
      return this.nuxContent;
   }

   public boolean getNuxEnabled() {
      return this.nuxEnabled;
   }

   public String getSdkUpdateMessage() {
      return this.sdkUpdateMessage;
   }

   public int getSessionTimeoutInSeconds() {
      return this.sessionTimeoutInSeconds;
   }

   public String getSmartLoginBookmarkIconURL() {
      return this.smartLoginBookmarkIconURL;
   }

   public String getSmartLoginMenuIconURL() {
      return this.smartLoginMenuIconURL;
   }

   public EnumSet<SmartLoginOption> getSmartLoginOptions() {
      return this.smartLoginOptions;
   }

   public boolean getTrackUninstallEnabled() {
      return this.trackUninstallEnabled;
   }

   public boolean supportsImplicitLogging() {
      return this.supportsImplicitLogging;
   }

   public static class DialogFeatureConfig {

      private static final String DIALOG_CONFIG_DIALOG_NAME_FEATURE_NAME_SEPARATOR = "\\|";
      private static final String DIALOG_CONFIG_NAME_KEY = "name";
      private static final String DIALOG_CONFIG_URL_KEY = "url";
      private static final String DIALOG_CONFIG_VERSIONS_KEY = "versions";
      private String dialogName;
      private Uri fallbackUrl;
      private String featureName;
      private int[] featureVersionSpec;


      private DialogFeatureConfig(String var1, String var2, Uri var3, int[] var4) {
         this.dialogName = var1;
         this.featureName = var2;
         this.fallbackUrl = var3;
         this.featureVersionSpec = var4;
      }

      public static FetchedAppSettings.DialogFeatureConfig parseDialogConfig(JSONObject var0) {
         String var3 = var0.optString("name");
         boolean var1 = Utility.isNullOrEmpty(var3);
         Uri var2 = null;
         if(var1) {
            return null;
         } else {
            String[] var4 = var3.split("\\|");
            if(var4.length != 2) {
               return null;
            } else {
               var3 = var4[0];
               String var6 = var4[1];
               if(!Utility.isNullOrEmpty(var3)) {
                  if(Utility.isNullOrEmpty(var6)) {
                     return null;
                  } else {
                     String var5 = var0.optString("url");
                     if(!Utility.isNullOrEmpty(var5)) {
                        var2 = Uri.parse(var5);
                     }

                     return new FetchedAppSettings.DialogFeatureConfig(var3, var6, var2, parseVersionSpec(var0.optJSONArray("versions")));
                  }
               } else {
                  return null;
               }
            }
         }
      }

      private static int[] parseVersionSpec(JSONArray var0) {
         int[] var5;
         if(var0 != null) {
            int var4 = var0.length();
            int[] var6 = new int[var4];
            int var2 = 0;

            while(true) {
               var5 = var6;
               if(var2 >= var4) {
                  break;
               }

               int var3 = var0.optInt(var2, -1);
               int var1 = var3;
               if(var3 == -1) {
                  String var8 = var0.optString(var2);
                  var1 = var3;
                  if(!Utility.isNullOrEmpty(var8)) {
                     try {
                        var1 = Integer.parseInt(var8);
                     } catch (NumberFormatException var7) {
                        Utility.logd("FacebookSDK", (Exception)var7);
                        var1 = -1;
                     }
                  }
               }

               var6[var2] = var1;
               ++var2;
            }
         } else {
            var5 = null;
         }

         return var5;
      }

      public String getDialogName() {
         return this.dialogName;
      }

      public Uri getFallbackUrl() {
         return this.fallbackUrl;
      }

      public String getFeatureName() {
         return this.featureName;
      }

      public int[] getVersionSpec() {
         return this.featureVersionSpec;
      }
   }
}
