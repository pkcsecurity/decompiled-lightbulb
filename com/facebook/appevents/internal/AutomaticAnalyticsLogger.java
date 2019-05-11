package com.facebook.appevents.internal;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.appevents.internal.InternalAppEventsLogger;
import com.facebook.appevents.internal.SubscriptionType;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.Validate;
import java.math.BigDecimal;
import java.util.Currency;
import org.json.JSONException;
import org.json.JSONObject;

public class AutomaticAnalyticsLogger {

   private static final String TAG = AutomaticAnalyticsLogger.class.getCanonicalName();
   private static final InternalAppEventsLogger internalAppEventsLogger = new InternalAppEventsLogger(FacebookSdk.getApplicationContext());


   @Nullable
   private static AutomaticAnalyticsLogger.PurchaseLoggingParameters getPurchaseLoggingParameters(String var0, String var1) {
      try {
         JSONObject var5 = new JSONObject(var0);
         JSONObject var7 = new JSONObject(var1);
         Bundle var2 = new Bundle(1);
         var2.putCharSequence("fb_iap_product_id", var5.getString("productId"));
         var2.putCharSequence("fb_iap_purchase_time", var5.getString("purchaseTime"));
         var2.putCharSequence("fb_iap_purchase_token", var5.getString("purchaseToken"));
         var2.putCharSequence("fb_iap_package_name", var5.optString("packageName"));
         var2.putCharSequence("fb_iap_product_title", var7.optString("title"));
         var2.putCharSequence("fb_iap_product_description", var7.optString("description"));
         String var3 = var7.optString("type");
         var2.putCharSequence("fb_iap_product_type", var3);
         if(var3.equals("subs")) {
            var2.putCharSequence("fb_iap_subs_auto_renewing", Boolean.toString(var5.optBoolean("autoRenewing", false)));
            var2.putCharSequence("fb_iap_subs_period", var7.optString("subscriptionPeriod"));
            var2.putCharSequence("fb_free_trial_period", var7.optString("freeTrialPeriod"));
            var0 = var7.optString("introductoryPriceCycles");
            if(!var0.isEmpty()) {
               var2.putCharSequence("fb_intro_price_amount_micros", var7.optString("introductoryPriceAmountMicros"));
               var2.putCharSequence("fb_intro_price_cycles", var0);
            }
         }

         AutomaticAnalyticsLogger.PurchaseLoggingParameters var6 = new AutomaticAnalyticsLogger.PurchaseLoggingParameters(new BigDecimal((double)var7.getLong("price_amount_micros") / 1000000.0D), Currency.getInstance(var7.getString("price_currency_code")), var2);
         return var6;
      } catch (JSONException var4) {
         Log.e(TAG, "Error parsing in-app subscription data.", var4);
         return null;
      }
   }

   public static boolean isImplicitPurchaseLoggingEnabled() {
      FetchedAppSettings var0 = FetchedAppSettingsManager.getAppSettingsWithoutQuery(FacebookSdk.getApplicationId());
      return var0 != null && FacebookSdk.getAutoLogAppEventsEnabled() && var0.getIAPAutomaticLoggingEnabled();
   }

   public static void logActivateAppEvent() {
      Context var1 = FacebookSdk.getApplicationContext();
      String var2 = FacebookSdk.getApplicationId();
      boolean var0 = FacebookSdk.getAutoLogAppEventsEnabled();
      Validate.notNull(var1, "context");
      if(var0) {
         if(var1 instanceof Application) {
            AppEventsLogger.activateApp((Application)var1, var2);
            return;
         }

         Log.w(TAG, "Automatic logging of basic events will not happen, because FacebookSdk.getApplicationContext() returns object that is not instance of android.app.Application. Make sure you call FacebookSdk.sdkInitialize() from Application class and pass application context.");
      }

   }

   public static void logActivityTimeSpentEvent(String var0, long var1) {
      Context var3 = FacebookSdk.getApplicationContext();
      String var4 = FacebookSdk.getApplicationId();
      Validate.notNull(var3, "context");
      FetchedAppSettings var6 = FetchedAppSettingsManager.queryAppSettings(var4, false);
      if(var6 != null && var6.getAutomaticLoggingEnabled() && var1 > 0L) {
         AppEventsLogger var5 = AppEventsLogger.newLogger(var3);
         Bundle var7 = new Bundle(1);
         var7.putCharSequence("fb_aa_time_spent_view_name", var0);
         var5.logEvent("fb_aa_time_spent_on_view", (double)var1, var7);
      }

   }

   public static void logPurchaseInapp(String var0, String var1) {
      if(isImplicitPurchaseLoggingEnabled()) {
         AutomaticAnalyticsLogger.PurchaseLoggingParameters var2 = getPurchaseLoggingParameters(var0, var1);
         if(var2 != null) {
            internalAppEventsLogger.logPurchaseImplicitlyInternal(var2.purchaseAmount, var2.currency, var2.param);
         }

      }
   }

   public static void logPurchaseSubs(SubscriptionType var0, String var1, String var2) {
      if(isImplicitPurchaseLoggingEnabled()) {
         FacebookSdk.getApplicationId();
         String var3;
         switch(null.$SwitchMap$com$facebook$appevents$internal$SubscriptionType[var0.ordinal()]) {
         case 1:
            var3 = "SubscriptionRestore";
            break;
         case 2:
            var3 = "SubscriptionCancel";
            break;
         case 3:
            var3 = "SubscriptionHeartbeat";
            break;
         case 4:
            var3 = "SubscriptionExpire";
            break;
         case 5:
            logPurchaseInapp(var1, var2);
            return;
         default:
            return;
         }

         AutomaticAnalyticsLogger.PurchaseLoggingParameters var4 = getPurchaseLoggingParameters(var1, var2);
         if(var4 != null) {
            internalAppEventsLogger.logEventImplicitly(var3, var4.purchaseAmount, var4.currency, var4.param);
         }

      }
   }

   static class PurchaseLoggingParameters {

      Currency currency;
      Bundle param;
      BigDecimal purchaseAmount;


      PurchaseLoggingParameters(BigDecimal var1, Currency var2, Bundle var3) {
         this.purchaseAmount = var1;
         this.currency = var2;
         this.param = var3;
      }
   }
}
