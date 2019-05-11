package com.tuyasmart.stencil.app;

import com.alibaba.wireless.security.jaq.JAQException;
import com.alibaba.wireless.security.jaq.SecurityInit;
import com.tuya.smart.android.common.task.Coordinator;
import com.tuya.smart.android.common.utils.L;
import com.tuya.smart.api.start.LauncherApplicationAgent;
import com.tuyasmart.stencil.app.GlobalConfig;
import com.tuyasmart.stencil.app.TuyaSmartInitializer.1;
import com.tuyasmart.stencil.app.TuyaSmartInitializer.2;
import com.tuyasmart.stencil.app.TuyaSmartInitializer.3;
import com.tuyasmart.stencil.utils.UmengHelper;

public class TuyaSmartInitializer {

   private static final String TAG = "TaoCouponInitializer";
   private String packageName = null;
   private String processName = null;


   // $FF: synthetic method
   static void access$000(TuyaSmartInitializer var0) {
      var0.initSecurity();
   }

   private void initSecurity() {
      try {
         SecurityInit.Initialize(aiv.b());
      } catch (JAQException var3) {
         StringBuilder var2 = new StringBuilder();
         var2.append("errorCode =");
         var2.append(var3.getErrorCode());
         L.e("TaoCouponInitializer", var2.toString());
         UmengHelper.error(aiv.b(), var3);
      }
   }

   private void startInitializersGlobals() {
      if(this.processName != null && this.processName.equals(GlobalConfig.getApplication().getPackageName())) {
         L.d("TaoCouponInitializer", this.processName);
         ajc.a(ajc.b(aiv.b(), "panelAction").a("action", "initFresco"));
         Coordinator.postTask(new 2(this, "initGlobal"));
      }

      Coordinator.postTask(new 3(this, "initDeviceId"));
   }

   public void start(String var1, String var2) {
      this.processName = var1;
      this.packageName = var2;
      this.startInitializersGlobals();
      aiv.c().a((LauncherApplicationAgent.CrossActivityLifecycleCallback)(new 1(this)));
   }
}
