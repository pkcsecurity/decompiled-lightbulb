package com.facebook.appevents.internal;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.facebook.FacebookSdk;
import com.facebook.appevents.internal.AutomaticAnalyticsLogger;
import com.facebook.appevents.internal.InAppPurchaseEventManager;
import com.facebook.appevents.internal.SubscriptionType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

public class InAppPurchaseActivityLifecycleTracker {

   private static final String BILLING_ACTIVITY_NAME = "com.android.billingclient.api.ProxyBillingActivity";
   private static final String SERVICE_INTERFACE_NAME = "com.android.vending.billing.IInAppBillingService$Stub";
   private static final String TAG = InAppPurchaseActivityLifecycleTracker.class.getCanonicalName();
   private static ActivityLifecycleCallbacks callbacks;
   private static Boolean hasBiillingActivity;
   private static Boolean hasBillingService;
   private static Object inAppBillingObj;
   private static Intent intent;
   private static final AtomicBoolean isTracking = new AtomicBoolean(false);
   private static ServiceConnection serviceConnection;


   private static void initializeIfNotInitialized() {
      if(hasBillingService == null) {
         try {
            Class.forName("com.android.vending.billing.IInAppBillingService$Stub");
            hasBillingService = Boolean.valueOf(true);
         } catch (ClassNotFoundException var2) {
            hasBillingService = Boolean.valueOf(false);
            return;
         }

         try {
            Class.forName("com.android.billingclient.api.ProxyBillingActivity");
            hasBiillingActivity = Boolean.valueOf(true);
         } catch (ClassNotFoundException var1) {
            hasBiillingActivity = Boolean.valueOf(false);
         }

         InAppPurchaseEventManager.clearSkuDetailsCache();
         intent = (new Intent("com.android.vending.billing.InAppBillingService.BIND")).setPackage("com.android.vending");
         serviceConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName var1, IBinder var2) {
               InAppPurchaseActivityLifecycleTracker.inAppBillingObj = InAppPurchaseEventManager.asInterface(FacebookSdk.getApplicationContext(), var2);
            }
            public void onServiceDisconnected(ComponentName var1) {}
         };
         callbacks = new ActivityLifecycleCallbacks() {
            public void onActivityCreated(Activity var1, Bundle var2) {}
            public void onActivityDestroyed(Activity var1) {}
            public void onActivityPaused(Activity var1) {}
            public void onActivityResumed(Activity var1) {
               FacebookSdk.getExecutor().execute(new Runnable() {
                  public void run() {
                     Context var1 = FacebookSdk.getApplicationContext();
                     InAppPurchaseActivityLifecycleTracker.logPurchaseInapp(var1, InAppPurchaseEventManager.getPurchasesInapp(var1, InAppPurchaseActivityLifecycleTracker.inAppBillingObj));
                     Map var2 = InAppPurchaseEventManager.getPurchasesSubs(var1, InAppPurchaseActivityLifecycleTracker.inAppBillingObj);
                     Iterator var3 = InAppPurchaseEventManager.getPurchasesSubsExpire(var1, InAppPurchaseActivityLifecycleTracker.inAppBillingObj).iterator();

                     while(var3.hasNext()) {
                        var2.put((String)var3.next(), SubscriptionType.EXPIRE);
                     }

                     InAppPurchaseActivityLifecycleTracker.logPurchaseSubs(var1, var2);
                  }
               });
            }
            public void onActivitySaveInstanceState(Activity var1, Bundle var2) {}
            public void onActivityStarted(Activity var1) {}
            public void onActivityStopped(Activity var1) {
               if(InAppPurchaseActivityLifecycleTracker.hasBiillingActivity.booleanValue() && var1.getLocalClassName().equals("com.android.billingclient.api.ProxyBillingActivity")) {
                  FacebookSdk.getExecutor().execute(new Runnable() {
                     public void run() {
                        Context var3 = FacebookSdk.getApplicationContext();
                        ArrayList var2 = InAppPurchaseEventManager.getPurchasesInapp(var3, InAppPurchaseActivityLifecycleTracker.inAppBillingObj);
                        ArrayList var1 = var2;
                        if(var2.isEmpty()) {
                           var1 = InAppPurchaseEventManager.getPurchaseHistoryInapp(var3, InAppPurchaseActivityLifecycleTracker.inAppBillingObj);
                        }

                        InAppPurchaseActivityLifecycleTracker.logPurchaseInapp(var3, var1);
                     }
                  });
               }

            }
         };
      }
   }

   private static void logPurchaseInapp(Context var0, ArrayList<String> var1) {
      if(!var1.isEmpty()) {
         HashMap var2 = new HashMap();
         ArrayList var3 = new ArrayList();
         Iterator var8 = var1.iterator();

         while(var8.hasNext()) {
            String var4 = (String)var8.next();

            try {
               String var5 = (new JSONObject(var4)).getString("productId");
               var2.put(var5, var4);
               var3.add(var5);
            } catch (JSONException var6) {
               Log.e(TAG, "Error parsing in-app purchase data.", var6);
            }
         }

         Iterator var7 = InAppPurchaseEventManager.getSkuDetails(var0, var3, inAppBillingObj, false).entrySet().iterator();

         while(var7.hasNext()) {
            Entry var9 = (Entry)var7.next();
            AutomaticAnalyticsLogger.logPurchaseInapp((String)var2.get(var9.getKey()), (String)var9.getValue());
         }

      }
   }

   private static void logPurchaseSubs(Context var0, Map<String, SubscriptionType> var1) {
      if(!var1.isEmpty()) {
         HashMap var2 = new HashMap();
         ArrayList var3 = new ArrayList();
         Iterator var4 = var1.keySet().iterator();

         String var5;
         while(var4.hasNext()) {
            var5 = (String)var4.next();

            try {
               String var6 = (new JSONObject(var5)).getString("productId");
               var3.add(var6);
               var2.put(var6, var5);
            } catch (JSONException var7) {
               Log.e(TAG, "Error parsing in-app purchase data.", var7);
            }
         }

         Map var8 = InAppPurchaseEventManager.getSkuDetails(var0, var3, inAppBillingObj, true);
         Iterator var9 = var8.keySet().iterator();

         while(var9.hasNext()) {
            var5 = (String)var9.next();
            String var10 = (String)var2.get(var5);
            var5 = (String)var8.get(var5);
            AutomaticAnalyticsLogger.logPurchaseSubs((SubscriptionType)var1.get(var10), var10, var5);
         }

      }
   }

   private static void startTracking() {
      if(isTracking.compareAndSet(false, true)) {
         Context var0 = FacebookSdk.getApplicationContext();
         if(var0 instanceof Application) {
            ((Application)var0).registerActivityLifecycleCallbacks(callbacks);
            var0.bindService(intent, serviceConnection, 1);
         }

      }
   }

   public static void update() {
      initializeIfNotInitialized();
      if(hasBillingService.booleanValue()) {
         if(AutomaticAnalyticsLogger.isImplicitPurchaseLoggingEnabled()) {
            startTracking();
         }

      }
   }
}
