package com.smart.app;

import android.app.Notification;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.text.TextUtils;
import com.facebook.soloader.SoLoader;
import com.tuya.android.mist.api.MistCore;
import com.tuya.smart.android.base.TuyaSmartSdk;
import com.tuya.smart.android.common.utils.L;
import com.tuya.smart.android.common.utils.NotificationHelper$Builder;
import com.tuya.smart.android.common.utils.TuyaUtil;
import com.tuya.smart.android.network.TuyaSmartNetWork;
import com.tuya.smart.litho.mist.config.DemoConfig;
import com.tuya.smart.sdk.TuyaSdk;
import com.tuya.smart.sdk.constant.ServiceNotification;
import com.tuyasmart.stencil.app.ApiConfig;
import com.tuyasmart.stencil.app.GlobalConfig;
import com.tuyasmart.stencil.app.StencilApp;
import com.tuyasmart.stencil.app.TuyaResConfig;
import com.tuyasmart.stencil.app.Wgine;
import com.tuyasmart.stencil.utils.PreferencesGlobalUtil;
import java.util.HashMap;

public class SmartApplication extends StencilApp {

   private String getRnVersion() {
      Resources var2 = this.getResources();
      StringBuilder var3 = new StringBuilder();
      var3.append(this.getPackageName());
      var3.append(":string/app_rn_version");
      int var1 = var2.getIdentifier(var3.toString(), (String)null, (String)null);
      return var1 > 0?this.getResources().getString(var1):"";
   }

   private void initMist() {
      avv var1 = new avv();
      var1.a();
      MistCore.getInstance().init(var1);
      avu.a().a(this, "mist_style_config.json");
      L.d("DemoApp", "after initMist()");
   }

   private void initMistLitho() {
      if(aiv.c().b()) {
         SoLoader.init(this, false);
         DemoConfig var1 = new DemoConfig();
         var1.create();
         com.tuya.smart.litho.mist.api.MistCore.getInstance().init(var1, this);
      }

   }

   private void initNotification() {
      NotificationHelper$Builder var1 = (new NotificationHelper$Builder(TuyaSdk.getApplication())).setChannelId("service").setSmallIconRes(2130838179).setLargeIconRes(2130838178).setAutoCancel(false);
      StringBuilder var2 = new StringBuilder();
      var2.append(TuyaUtil.getApplicationName(TuyaSmartSdk.getApplication()));
      var2.append(" ");
      var2.append(this.getString(2131428970));
      Notification var3 = var1.setTitle(var2.toString()).setText(this.getString(2131428969)).build();
      ServiceNotification.getInstance().setNotification(var3);
   }

   private void initResource() {
      HashMap var1 = new HashMap();
      var1.put(TuyaResConfig.THEME_ID, Integer.valueOf(2131558516));
      var1.put(TuyaResConfig.PUSH_ICON_RES_ID, Integer.valueOf(2130838179));
      TuyaResConfig.init(var1);
      ajc.a(this.getString(2131429497));
   }

   protected String getChannel() {
      ApplicationInfo var1;
      try {
         var1 = this.getPackageManager().getApplicationInfo(this.getPackageName(), 128);
      } catch (NameNotFoundException var2) {
         var1 = null;
      }

      return var1 == null?this.getResources().getString(2131429471):var1.metaData.getString("UMENG_CHANNEL");
   }

   protected void initKey(ApiConfig.EnvConfig var1) {
      TuyaSmartNetWork.mSdk = true;
      TuyaSmartNetWork.mNTY = true;
      if(!TuyaSmartNetWork.mSdk) {
         Wgine.initForStencil(this, "ekmnwp9f5pnh3trdtpgy", "r3me7ghmxjevrvnpemwmhw3fxtacphyg", GlobalConfig.getFlavor(), this.getRnVersion(), new ApiConfig(var1));
      } else {
         String var2;
         String var3;
         StringBuilder var5;
         label16: {
            var2 = PreferencesGlobalUtil.getString("appkey");
            String var4 = PreferencesGlobalUtil.getString("appSercet");
            if(!TextUtils.isEmpty(var2)) {
               var3 = var4;
               if(!TextUtils.isEmpty(var4)) {
                  break label16;
               }
            }

            var2 = "ekmnwp9f5pnh3trdtpgy";
            var3 = "r3me7ghmxjevrvnpemwmhw3fxtacphyg";
            var5 = new StringBuilder();
            var5.append("appKey:");
            var5.append("ekmnwp9f5pnh3trdtpgy");
            var5.append("  appSecret:");
            var5.append("r3me7ghmxjevrvnpemwmhw3fxtacphyg");
            L.d("SmartApplication", var5.toString());
         }

         var5 = new StringBuilder();
         var5.append("sdk_");
         var5.append(GlobalConfig.getFlavor());
         var5.append("@");
         var5.append(var2);
         Wgine.initForStencil(this, var2, var3, var5.toString(), this.getRnVersion(), new ApiConfig(var1));
      }
   }

   protected boolean isBuildConfigDebug() {
      return false;
   }

   public void onCreate() {
      super.onCreate();
      L.setLogSwitcher(this.isBuildConfigDebug());
      L.logToServer("StencilApp", "onCreate");
      this.initMist();
      this.initMistLitho();
      this.initNotification();
      ahk.a(this);
      this.initResource();
   }
}
