package com.facebook.appevents.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import com.facebook.FacebookSdk;
import com.facebook.appevents.internal.SubscriptionType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

class InAppPurchaseEventManager {

   private static final String AS_INTERFACE = "asInterface";
   private static final int CACHE_CLEAR_TIME_LIMIT_SEC = 604800;
   private static final String DETAILS_LIST = "DETAILS_LIST";
   private static final String GET_PURCHASES = "getPurchases";
   private static final String GET_PURCHASE_HISTORY = "getPurchaseHistory";
   private static final String GET_SKU_DETAILS = "getSkuDetails";
   private static final String INAPP = "inapp";
   private static final String INAPP_CONTINUATION_TOKEN = "INAPP_CONTINUATION_TOKEN";
   private static final String INAPP_PURCHASE_DATA_LIST = "INAPP_PURCHASE_DATA_LIST";
   private static final String IN_APP_BILLING_SERVICE = "com.android.vending.billing.IInAppBillingService";
   private static final String IN_APP_BILLING_SERVICE_STUB = "com.android.vending.billing.IInAppBillingService$Stub";
   private static final String IS_BILLING_SUPPORTED = "isBillingSupported";
   private static final String ITEM_ID_LIST = "ITEM_ID_LIST";
   private static final String LAST_CLEARED_TIME = "LAST_CLEARED_TIME";
   private static final String LAST_LOGGED_TIME_SEC = "LAST_LOGGED_TIME_SEC";
   private static final int MAX_QUERY_PURCHASE_NUM = 30;
   private static final String PACKAGE_NAME = FacebookSdk.getApplicationContext().getPackageName();
   private static final int PURCHASE_EXPIRE_TIME_SEC = 43200;
   private static final String PURCHASE_INAPP_STORE = "com.facebook.internal.PURCHASE";
   private static final int PURCHASE_STOP_QUERY_TIME_SEC = 1200;
   private static final String PURCHASE_SUBS_STORE = "com.facebook.internal.PURCHASE_SUBS";
   private static final String RESPONSE_CODE = "RESPONSE_CODE";
   private static final String SKU_DETAILS_STORE = "com.facebook.internal.SKU_DETAILS";
   private static final int SKU_DETAIL_EXPIRE_TIME_SEC = 43200;
   private static final String SUBSCRIPTION = "subs";
   private static final long SUBSCRIPTION_HARTBEAT_INTERVAL = 86400L;
   private static final String TAG = InAppPurchaseEventManager.class.getCanonicalName();
   private static final HashMap<String, Class<?>> classMap = new HashMap();
   private static final HashMap<String, Method> methodMap = new HashMap();
   private static final SharedPreferences purchaseInappSharedPrefs = FacebookSdk.getApplicationContext().getSharedPreferences("com.facebook.internal.PURCHASE", 0);
   private static final SharedPreferences purchaseSubsSharedPrefs = FacebookSdk.getApplicationContext().getSharedPreferences("com.facebook.internal.PURCHASE_SUBS", 0);
   private static final SharedPreferences skuDetailSharedPrefs = FacebookSdk.getApplicationContext().getSharedPreferences("com.facebook.internal.SKU_DETAILS", 0);


   @Nullable
   public static Object asInterface(Context var0, IBinder var1) {
      return invokeMethod(var0, "com.android.vending.billing.IInAppBillingService$Stub", "asInterface", (Object)null, new Object[]{var1});
   }

   public static void clearSkuDetailsCache() {
      long var0 = System.currentTimeMillis() / 1000L;
      long var2 = skuDetailSharedPrefs.getLong("LAST_CLEARED_TIME", 0L);
      if(var2 == 0L) {
         skuDetailSharedPrefs.edit().putLong("LAST_CLEARED_TIME", var0).apply();
      } else {
         if(var0 - var2 > 604800L) {
            skuDetailSharedPrefs.edit().clear().putLong("LAST_CLEARED_TIME", var0).apply();
         }

      }
   }

   private static ArrayList<String> filterPurchasesInapp(ArrayList<String> var0) {
      ArrayList var5 = new ArrayList();
      Editor var6 = purchaseInappSharedPrefs.edit();
      long var1 = System.currentTimeMillis() / 1000L;
      Iterator var11 = var0.iterator();

      while(var11.hasNext()) {
         String var7 = (String)var11.next();

         try {
            JSONObject var9 = new JSONObject(var7);
            String var8 = var9.getString("productId");
            long var3 = var9.getLong("purchaseTime");
            String var12 = var9.getString("purchaseToken");
            if(var1 - var3 / 1000L <= 43200L && !purchaseInappSharedPrefs.getString(var8, "").equals(var12)) {
               var6.putString(var8, var12);
               var5.add(var7);
            }
         } catch (JSONException var10) {
            Log.e(TAG, "parsing purchase failure: ", var10);
         }
      }

      var6.apply();
      return var5;
   }

   @Nullable
   private static Class<?> getClass(Context var0, String var1) {
      Class var3 = (Class)classMap.get(var1);
      if(var3 != null) {
         return var3;
      } else {
         ClassNotFoundException var2;
         Class var7;
         label20: {
            try {
               var7 = var0.getClassLoader().loadClass(var1);
            } catch (ClassNotFoundException var6) {
               var2 = var6;
               var7 = var3;
               break label20;
            }

            try {
               classMap.put(var1, var7);
               return var7;
            } catch (ClassNotFoundException var5) {
               var2 = var5;
            }
         }

         String var8 = TAG;
         StringBuilder var4 = new StringBuilder();
         var4.append(var1);
         var4.append(" is not available, please add ");
         var4.append(var1);
         var4.append(" to the project.");
         Log.e(var8, var4.toString(), var2);
         return var7;
      }
   }

   @Nullable
   private static Method getMethod(Class<?> param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   private static ArrayList<String> getPurchaseHistory(Context param0, Object param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   public static ArrayList<String> getPurchaseHistoryInapp(Context var0, Object var1) {
      ArrayList var2 = new ArrayList();
      if(var1 == null) {
         return var2;
      } else {
         Class var3 = getClass(var0, "com.android.vending.billing.IInAppBillingService");
         return var3 == null?var2:(getMethod(var3, "getPurchaseHistory") == null?var2:filterPurchasesInapp(getPurchaseHistory(var0, var1, "inapp")));
      }
   }

   private static ArrayList<String> getPurchases(Context var0, Object var1, String var2) {
      ArrayList var6 = new ArrayList();
      if(var1 == null) {
         return var6;
      } else {
         if(isBillingSupported(var0, var1, var2).booleanValue()) {
            String var5 = null;
            int var3 = 0;

            String var8;
            do {
               label26: {
                  Object var4 = invokeMethod(var0, "com.android.vending.billing.IInAppBillingService", "getPurchases", var1, new Object[]{Integer.valueOf(3), PACKAGE_NAME, var2, var5});
                  if(var4 != null) {
                     Bundle var7 = (Bundle)var4;
                     if(var7.getInt("RESPONSE_CODE") == 0) {
                        ArrayList var9 = var7.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
                        if(var9 == null) {
                           break;
                        }

                        var3 += var9.size();
                        var6.addAll(var9);
                        var8 = var7.getString("INAPP_CONTINUATION_TOKEN");
                        break label26;
                     }
                  }

                  var8 = null;
               }

               if(var3 >= 30) {
                  break;
               }

               var5 = var8;
            } while(var8 != null);
         }

         return var6;
      }
   }

   public static ArrayList<String> getPurchasesInapp(Context var0, Object var1) {
      return filterPurchasesInapp(getPurchases(var0, var1, "inapp"));
   }

   public static Map<String, SubscriptionType> getPurchasesSubs(Context var0, Object var1) {
      HashMap var2 = new HashMap();
      Iterator var4 = getPurchases(var0, var1, "subs").iterator();

      while(var4.hasNext()) {
         String var5 = (String)var4.next();
         SubscriptionType var3 = getSubsType(var5);
         if(var3 != SubscriptionType.DUPLICATED && var3 != SubscriptionType.UNKNOWN) {
            var2.put(var5, var3);
         }
      }

      return var2;
   }

   public static ArrayList<String> getPurchasesSubsExpire(Context var0, Object var1) {
      ArrayList var2 = new ArrayList();
      Map var3 = purchaseSubsSharedPrefs.getAll();
      if(var3.isEmpty()) {
         return var2;
      } else {
         ArrayList var8 = getPurchases(var0, var1, "subs");
         HashSet var6 = new HashSet();
         Iterator var9 = var8.iterator();

         String var4;
         while(var9.hasNext()) {
            var4 = (String)var9.next();

            try {
               var6.add((new JSONObject(var4)).getString("productId"));
            } catch (JSONException var5) {
               Log.e(TAG, "Error parsing purchase json", var5);
            }
         }

         HashSet var10 = new HashSet();
         Iterator var11 = var3.entrySet().iterator();

         while(var11.hasNext()) {
            var4 = (String)((Entry)var11.next()).getKey();
            if(!var6.contains(var4)) {
               var10.add(var4);
            }
         }

         Editor var7 = purchaseSubsSharedPrefs.edit();
         var9 = var10.iterator();

         while(var9.hasNext()) {
            String var12 = (String)var9.next();
            var4 = purchaseSubsSharedPrefs.getString(var12, "");
            var7.remove(var12);
            if(!var4.isEmpty()) {
               var2.add(purchaseSubsSharedPrefs.getString(var12, ""));
            }
         }

         var7.apply();
         return var2;
      }
   }

   public static Map<String, String> getSkuDetails(Context var0, ArrayList<String> var1, Object var2, boolean var3) {
      Map var4 = readSkuDetailsFromCache(var1);
      ArrayList var5 = new ArrayList();
      Iterator var7 = var1.iterator();

      while(var7.hasNext()) {
         String var6 = (String)var7.next();
         if(!var4.containsKey(var6)) {
            var5.add(var6);
         }
      }

      var4.putAll(getSkuDetailsFromGoogle(var0, var5, var2, var3));
      return var4;
   }

   private static Map<String, String> getSkuDetailsFromGoogle(Context var0, ArrayList<String> var1, Object var2, boolean var3) {
      HashMap var6 = new HashMap();
      if(var2 == null) {
         return var6;
      } else if(var1.isEmpty()) {
         return var6;
      } else {
         Bundle var7 = new Bundle();
         var7.putStringArrayList("ITEM_ID_LIST", var1);
         int var4 = 0;
         String var8 = PACKAGE_NAME;
         String var5;
         if(var3) {
            var5 = "subs";
         } else {
            var5 = "inapp";
         }

         Object var9 = invokeMethod(var0, "com.android.vending.billing.IInAppBillingService", "getSkuDetails", var2, new Object[]{Integer.valueOf(3), var8, var5, var7});
         if(var9 != null) {
            Bundle var10 = (Bundle)var9;
            if(var10.getInt("RESPONSE_CODE") == 0) {
               ArrayList var11 = var10.getStringArrayList("DETAILS_LIST");
               if(var11 != null && var1.size() == var11.size()) {
                  while(var4 < var1.size()) {
                     var6.put(var1.get(var4), var11.get(var4));
                     ++var4;
                  }
               }

               writeSkuDetailsToCache(var6);
            }
         }

         return var6;
      }
   }

   private static SubscriptionType getSubsType(String param0) {
      // $FF: Couldn't be decompiled
   }

   @Nullable
   private static Object invokeMethod(Context var0, String var1, String var2, Object var3, Object[] var4) {
      Class var8 = getClass(var0, var1);
      if(var8 == null) {
         return null;
      } else {
         Method var9 = getMethod(var8, var2);
         if(var9 == null) {
            return null;
         } else {
            Object var7 = var3;
            if(var3 != null) {
               var7 = var8.cast(var3);
            }

            String var10;
            StringBuilder var11;
            try {
               var7 = var9.invoke(var7, var4);
               return var7;
            } catch (IllegalAccessException var5) {
               var10 = TAG;
               var11 = new StringBuilder();
               var11.append("Illegal access to method ");
               var11.append(var8.getName());
               var11.append(".");
               var11.append(var9.getName());
               Log.e(var10, var11.toString(), var5);
               return null;
            } catch (InvocationTargetException var6) {
               var10 = TAG;
               var11 = new StringBuilder();
               var11.append("Invocation target exception in ");
               var11.append(var8.getName());
               var11.append(".");
               var11.append(var9.getName());
               Log.e(var10, var11.toString(), var6);
               return null;
            }
         }
      }
   }

   private static Boolean isBillingSupported(Context var0, Object var1, String var2) {
      boolean var4 = false;
      if(var1 == null) {
         return Boolean.valueOf(false);
      } else {
         Object var5 = invokeMethod(var0, "com.android.vending.billing.IInAppBillingService", "isBillingSupported", var1, new Object[]{Integer.valueOf(3), PACKAGE_NAME, var2});
         boolean var3 = var4;
         if(var5 != null) {
            var3 = var4;
            if(((Integer)var5).intValue() == 0) {
               var3 = true;
            }
         }

         return Boolean.valueOf(var3);
      }
   }

   private static Map<String, String> readSkuDetailsFromCache(ArrayList<String> var0) {
      HashMap var3 = new HashMap();
      long var1 = System.currentTimeMillis() / 1000L;
      Iterator var6 = var0.iterator();

      while(var6.hasNext()) {
         String var4 = (String)var6.next();
         String var5 = skuDetailSharedPrefs.getString(var4, (String)null);
         if(var5 != null) {
            String[] var7 = var5.split(";", 2);
            if(var1 - Long.parseLong(var7[0]) < 43200L) {
               var3.put(var4, var7[1]);
            }
         }
      }

      return var3;
   }

   private static void writeSkuDetailsToCache(Map<String, String> var0) {
      long var1 = System.currentTimeMillis() / 1000L;
      Editor var3 = skuDetailSharedPrefs.edit();
      Iterator var7 = var0.entrySet().iterator();

      while(var7.hasNext()) {
         Entry var4 = (Entry)var7.next();
         String var5 = (String)var4.getKey();
         StringBuilder var6 = new StringBuilder();
         var6.append(var1);
         var6.append(";");
         var6.append((String)var4.getValue());
         var3.putString(var5, var6.toString());
      }

      var3.apply();
   }
}
