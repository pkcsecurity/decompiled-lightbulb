package com.tuyasmart.stencil.app;

import android.app.Application;
import android.content.Context;
import com.tuya.smart.android.base.TuyaSmartSdk;
import com.tuya.smart.android.common.utils.L;
import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuyasmart.stencil.global.model.TuyaUIDownloadManager;

public class GlobalConfig {

   public static final String APP_TAG = "TUYA";
   public static boolean DEBUG;
   private static final String TAG = "GlobalConfig";
   private static Application mApplication;
   private static ClassLoader mClassLoader;
   private static Context mContext;
   private static String mFlavor;
   private static String mFlavorString;
   private static String mVersion;


   public static void exitApplation() {
      TuyaHomeSdk.onDestroy();
      TuyaUIDownloadManager.getInstance().onDestroy();
      boz.b();
      L.d("GlobalConfig", "exitApplation");
   }

   public static void exitApplationDelayed(int var0) {
      exitApplation();
   }

   public static Application getApplication() {
      synchronized(GlobalConfig.class){}

      Application var0;
      try {
         if(mApplication == null) {
            mApplication = aiv.b();
         }

         var0 = mApplication;
      } finally {
         ;
      }

      return var0;
   }

   public static ClassLoader getClassLoader() {
      synchronized(GlobalConfig.class){}

      ClassLoader var0;
      try {
         if(mClassLoader == null) {
            mClassLoader = getApplication().getClassLoader();
         }

         var0 = mClassLoader;
      } finally {
         ;
      }

      return var0;
   }

   public static Context getContext() {
      return mContext;
   }

   public static String getFlavor() {
      return mFlavor;
   }

   public static String getFlavorString() {
      return mFlavorString;
   }

   public static String getVersionName() {
      synchronized(GlobalConfig.class){}

      String var0;
      try {
         if(mVersion == null) {
            try {
               mVersion = TuyaSmartSdk.getApplication().getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
            } catch (Exception var4) {
               StringBuilder var1 = new StringBuilder();
               var1.append("getVersionName exception, msg: ");
               var1.append(var4.getMessage());
               bpj.b("GlobalConfig", var1.toString());
               mVersion = "1.0.0";
            }

            var0 = mVersion;
            return var0;
         }

         var0 = mVersion;
      } finally {
         ;
      }

      return var0;
   }

   public static void init(Context var0, String var1, boolean var2) {
      DEBUG = var2;
      mContext = var0;
      mFlavor = var1;
      StringBuilder var3 = new StringBuilder();
      var3.append(mFlavor);
      var3.append("@tuya_android_");
      var3.append(getVersionName());
      mFlavorString = var3.toString();
      bpj.a(DEBUG);
   }
}
