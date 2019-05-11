package com.tuyasmart.stencil.app;

import android.app.Application;
import android.content.Context;
import com.tuya.smart.android.base.provider.ApiUrlProvider;
import com.tuya.smart.android.common.utils.L;
import com.tuya.smart.android.network.TuyaSmartNetWork;
import com.tuya.smart.sdk.TuyaSdk;
import com.tuyasmart.stencil.app.ApiConfig;
import com.tuyasmart.stencil.app.GlobalConfig;
import com.tuyasmart.stencil.app.Wgine.1;
import com.tuyasmart.stencil.debug.AbstractDebugConfigService;

@Deprecated
public class Wgine {

   public static final String TAG = "Wgine";
   public static Application appContext;
   public static boolean appDebug;
   public static String appId;
   public static String appIdSecret;
   public static String appTtid;
   public static String appVersion;
   private static ApiConfig mApiConfig;


   private Wgine() {
      throw new AssertionError();
   }

   public static Context getAppContext() {
      return appContext.getApplicationContext();
   }

   public static ApiConfig.EnvConfig getEnv() {
      return mApiConfig.getEnv();
   }

   private static void init(String var0) {
      TuyaSdk.setOnNeedLoginListener(new 1());
      AbstractDebugConfigService var1 = (AbstractDebugConfigService)aiv.a().a(AbstractDebugConfigService.class.getName());
      ApiUrlProvider var2;
      if(var1 != null) {
         var2 = var1.getApiUrlProvider(appContext);
      } else {
         var2 = null;
      }

      TuyaSdk.init(appContext, appId, appIdSecret, appTtid, var0, var2);
   }

   public static void initForStencil(Application var0, String var1, String var2, String var3, String var4, ApiConfig var5) {
      appContext = var0;
      appVersion = bpc.a((Context)var0);
      appTtid = var3;
      mApiConfig = var5;
      appId = var1;
      appIdSecret = var2;
      appDebug = GlobalConfig.DEBUG;
      writeLogs(appDebug);
      TuyaSdk.setDebugMode(appDebug);
      init(var4);
   }

   public static void switchServer(String var0) {
      TuyaSmartNetWork.switchServer(var0);
   }

   public static void writeLogs(boolean var0) {
      appDebug = var0;
      L.setLogSwitcher(var0);
   }
}
