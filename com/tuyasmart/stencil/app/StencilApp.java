package com.tuyasmart.stencil.app;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import com.tuya.smart.android.base.TuyaSmartSdk;
import com.tuya.smart.android.base.database.StorageHelper;
import com.tuya.smart.android.common.task.TuyaExecutor;
import com.tuya.smart.android.common.utils.L;
import com.tuya.smart.api.start.LauncherApplicationAgent;
import com.tuyasmart.stencil.app.ApiConfig;
import com.tuyasmart.stencil.app.GlobalConfig;
import com.tuyasmart.stencil.app.SmartLogger;
import com.tuyasmart.stencil.app.TuyaSmartInitializer;
import com.tuyasmart.stencil.debug.AbstractDebugConfigService;
import com.tuyasmart.stencil.global.lifecycle.CrossActivityLifecycleObserver;
import com.tuyasmart.stencil.global.lifecycle.TuyaActivityLifecycleObserver;

public abstract class StencilApp extends MultiDexApplication {

   protected static final String TAG = "StencilApp";
   private TuyaSmartInitializer mTuyaSmartInitializer;


   private void initCore() {
      aiv.c().a((LauncherApplicationAgent.CrossActivityLifecycleCallback)(new CrossActivityLifecycleObserver()));
      aiv.b().registerActivityLifecycleCallbacks(new TuyaActivityLifecycleObserver());
      this.mTuyaSmartInitializer.start(aiv.c().d(), aiv.c().c());
   }

   private void initTY() {
      bpc.a((Context)aiv.b(), StorageHelper.getIntValue("TY_LANG", 0));
   }

   private void initWgine() {
      AbstractDebugConfigService var1 = (AbstractDebugConfigService)aiv.a().a(AbstractDebugConfigService.class.getName());
      if(var1 != null) {
         this.initKey(var1.getEnv());
      } else {
         this.initKey(ApiConfig.EnvConfig.ONLINE);
         StringBuilder var2 = new StringBuilder();
         var2.append("envName:");
         var2.append(ApiConfig.EnvConfig.ONLINE.name());
         L.d("StencilApp", var2.toString());
      }
   }

   public abstract String getChannel();

   public abstract void initKey(ApiConfig.EnvConfig var1);

   public boolean isBuildConfigDebug() {
      return false;
   }

   public void onCreate() {
      super.onCreate();
      TuyaSmartSdk.init(this);
      aiw.a(this.isBuildConfigDebug());
      aiw.a(this, new SmartLogger(), TuyaExecutor.getInstance().getExecutorService());
      this.mTuyaSmartInitializer = new TuyaSmartInitializer();
      if(aiv.c().b()) {
         this.initCore();
         this.initTY();
         GlobalConfig.init(this, this.getChannel(), this.isBuildConfigDebug());
      } else {
         this.mTuyaSmartInitializer.start(aiv.c().d(), aiv.c().c());
      }

      this.initWgine();
      aiw.a();
      StringBuilder var1 = new StringBuilder();
      var1.append("PACKAGE_NAME:");
      var1.append(aiv.c().c());
      L.d("StencilApp", var1.toString());
   }
}
