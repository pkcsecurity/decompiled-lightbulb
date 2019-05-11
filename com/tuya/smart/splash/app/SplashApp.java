package com.tuya.smart.splash.app;

import android.content.Context;
import com.tuyasmart.stencil.app.GlobalConfig;
import com.tuyasmart.stencil.app.StencilApp;

public abstract class SplashApp extends StencilApp {

   protected void initCrashHandler() {
      String var1 = GlobalConfig.getFlavor();
      if(var1.contains("Online") || var1.contains("Preview") || var1.contains("Daily")) {
         bif.a().a((Context)this);
      }

   }

   public void onCreate() {
      super.onCreate();
      if(!GlobalConfig.DEBUG) {
         this.initCrashHandler();
      }

   }
}
