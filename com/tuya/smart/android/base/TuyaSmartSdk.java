package com.tuya.smart.android.base;

import android.app.Application;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import com.tuya.smart.android.base.event.TuyaEventBus;
import com.tuya.smart.android.common.task.TuyaExecutor;
import com.tuya.smart.android.common.utils.L;
import com.tuya.smart.common.OOo0000;
import com.tuya.smart.common.oOO0O0O0;
import com.tuya.smart.common.oo0oo0ooo;

public class TuyaSmartSdk {

   public static boolean DEBUG;
   public static String appSecret;
   public static String appkey;
   private static volatile TuyaEventBus eventBus;
   private static Application mContext;
   private static boolean mInit;
   public static String ttid;


   public static void destroy() {}

   public static String getAppSecret() {
      synchronized(TuyaSmartSdk.class){}

      try {
         if(TextUtils.isEmpty(appSecret)) {
            appSecret = mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(), 128).metaData.getString("TUYA_SMART_SECRET");
         }

         String var0 = appSecret;
         return var0;
      } catch (NameNotFoundException var3) {
         var3.printStackTrace();
      } finally {
         ;
      }

      return null;
   }

   public static String getAppkey() {
      synchronized(TuyaSmartSdk.class){}

      try {
         if(TextUtils.isEmpty(appkey)) {
            appkey = mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(), 128).metaData.getString("TUYA_SMART_APPKEY");
         }

         String var0 = appkey;
         return var0;
      } catch (NameNotFoundException var3) {
         var3.printStackTrace();
      } finally {
         ;
      }

      return null;
   }

   public static Application getApplication() {
      return mContext;
   }

   public static TuyaEventBus getEventBus() {
      return eventBus;
   }

   public static String getLatitude() {
      return null;
   }

   public static String getLongitude() {
      return null;
   }

   public static String getTtid() {
      return ttid;
   }

   public static void init(Application var0) {
      if(!mInit) {
         mInit = true;
         mContext = var0;
         initEventbus();
         L.setSendLogOn(false);
         oo0oo0ooo.O000000o(var0);
         OOo0000.O000000o(var0);
      }

   }

   private static void initEventbus() {
      if(eventBus == null) {
         oOO0O0O0.O00000o0();
         eventBus = new TuyaEventBus(DEBUG, TuyaExecutor.getInstance().getTuyaExecutorService());
      }
   }

   public static void setAppSecret(String var0) {
      appSecret = var0;
   }

   public static void setAppkey(String var0) {
      appkey = var0;
   }

   public static void setDebugMode(boolean var0) {
      DEBUG = var0;
      L.setLogSwitcher(DEBUG);
   }

   public static void setTtid(String var0) {
      ttid = var0;
   }
}
