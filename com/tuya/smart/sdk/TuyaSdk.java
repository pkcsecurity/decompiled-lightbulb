package com.tuya.smart.sdk;

import android.app.ActivityManager;
import android.app.Application;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
import com.tuya.smart.android.base.TuyaSmartSdk;
import com.tuya.smart.android.base.broadcast.NetworkBroadcastReceiver;
import com.tuya.smart.android.base.event.TuyaEventBus;
import com.tuya.smart.android.base.provider.ApiUrlProvider;
import com.tuya.smart.android.common.utils.L;
import com.tuya.smart.android.common.utils.StorageUtil;
import com.tuya.smart.android.device.utils.TuyaActivityLifecycleCallback;
import com.tuya.smart.android.network.IApiUrlProvider;
import com.tuya.smart.android.network.TuyaSmartNetWork;
import com.tuya.smart.common.OOo0000;
import com.tuya.smart.common.o0o00000oo;
import com.tuya.smart.common.o0o000o0oo;
import com.tuya.smart.sdk.TuyaSdk.1;
import com.tuya.smart.sdk.api.INeedLoginListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

public class TuyaSdk {

   private static final String TAG = "TuyaSdk";
   private static String latitude;
   private static String longitude;
   private static Application mApplication;


   // $FF: synthetic method
   static void access$000() {
      logout();
   }

   public static Application getApplication() {
      synchronized(TuyaSdk.class){}

      Application var0;
      try {
         if(mApplication == null) {
            mApplication = getSystemApp();
         }

         var0 = mApplication;
      } finally {
         ;
      }

      return var0;
   }

   public static TuyaEventBus getEventBus() {
      return TuyaSmartSdk.getEventBus();
   }

   public static String getLatitude() {
      return latitude;
   }

   public static String getLongitude() {
      return longitude;
   }

   private static String getProcessName(Context var0) {
      int var1 = Process.myPid();
      ActivityManager var3 = (ActivityManager)var0.getSystemService("activity");
      if(var3 != null) {
         List var4 = var3.getRunningAppProcesses();
         if(var4 != null) {
            Iterator var5 = var4.iterator();

            while(var5.hasNext()) {
               RunningAppProcessInfo var2 = (RunningAppProcessInfo)var5.next();
               if(var2.pid == var1) {
                  return var2.processName;
               }
            }
         }
      }

      return null;
   }

   private static Application getSystemApp() {
      try {
         Class var1 = Class.forName("android.app.ActivityThread");
         Method var0 = var1.getDeclaredMethod("currentActivityThread", new Class[0]);
         Field var4 = var1.getDeclaredField("mInitialApplication");
         var4.setAccessible(true);
         Application var3 = (Application)var4.get(var0.invoke((Object)null, new Object[0]));
         return var3;
      } catch (Exception var2) {
         return TuyaSmartSdk.getApplication();
      }
   }

   public static void init(Application var0) {
      TuyaSmartSdk.init(var0);
      init(var0, TuyaSmartSdk.getAppkey(), TuyaSmartSdk.getAppSecret());
   }

   public static void init(Application var0, String var1, String var2) {
      TuyaSmartSdk.init(var0);
      init(var0, var1, var2, "android", "sdk", (String)null, (IApiUrlProvider)null);
   }

   public static void init(Application var0, String var1, String var2, String var3, String var4, IApiUrlProvider var5) {
      TuyaSmartSdk.init(var0);
      if(!TextUtils.isEmpty(var1) && !TextUtils.isEmpty(var2)) {
         mApplication = var0;
         initTuyaData(var0, var1, var2, var3, "oem", var4, var5);
         TuyaSmartNetWork.mSdkVersion = "3.9.3";
      } else {
         throw new RuntimeException("appkey and appSecret cannot be null ");
      }
   }

   public static void init(Application var0, String var1, String var2, String var3, String var4, String var5, IApiUrlProvider var6) {
      TuyaSmartSdk.init(var0);
      if(!TextUtils.isEmpty(var1) && !TextUtils.isEmpty(var2)) {
         mApplication = var0;
         initTuyaData(var0, var1, var2, var3, var5, var4, var6);
         TuyaSmartNetWork.mSdkVersion = "3.9.3";
      } else {
         throw new RuntimeException("appkey and appSecret cannot be null ");
      }
   }

   private static void initTuyaData(Application var0, String var1, String var2, String var3, String var4, String var5, IApiUrlProvider var6) {
      String var7 = getProcessName(getApplication());
      String var8 = getApplication().getPackageName();
      if(var6 == null) {
         var6 = new ApiUrlProvider(var0);
      }

      TuyaSmartNetWork.initialize(var0, var1, var2, var3, var4, (String)null, var5, (IApiUrlProvider)var6);
      StringBuilder var9 = new StringBuilder();
      var9.append("processName: ");
      var9.append(var7);
      L.d("TuyaSdk", var9.toString());
      if(TextUtils.isEmpty(var7) || TextUtils.equals(var7, var8)) {
         o0o00000oo var10 = (o0o00000oo)OOo0000.O000000o(o0o00000oo.class);
         if(var10 != null) {
            var10.O0000Ooo();
         }
      }

      TuyaActivityLifecycleCallback.getInstance(var0);
      var9 = new StringBuilder();
      var9.append(StorageUtil.getCacheDirectory(getApplication()));
      var9.append("/t/l/t2.rar");
      L.setsLogPath(var9.toString());
      L.init();
      NetworkBroadcastReceiver.registerReceiver(var0);
   }

   public static boolean isForeginAccount() {
      byte var2;
      label21: {
         String var1 = TuyaSmartNetWork.getRegion();
         int var0 = var1.hashCode();
         if(var0 != 2104) {
            if(var0 == 2645 && var1.equals("SH")) {
               var2 = 1;
               break label21;
            }
         } else if(var1.equals("AY")) {
            var2 = 0;
            break label21;
         }

         var2 = -1;
      }

      switch(var2) {
      case 0:
      case 1:
         return false;
      default:
         return true;
      }
   }

   private static void logout() {
      o0o00000oo var0 = (o0o00000oo)OOo0000.O000000o(o0o00000oo.class);
      if(var0 != null) {
         var0.O0000Ooo().onDestroy();
      }

      o0o000o0oo var1 = (o0o000o0oo)OOo0000.O000000o(o0o000o0oo.class);
      if(var1 != null) {
         var1.O000000o().removeUser();
      }

   }

   public static void setDebugMode(boolean var0) {
      TuyaSmartSdk.setDebugMode(var0);
   }

   public static void setLatAndLong(String var0, String var1) {
      latitude = var0;
      longitude = var1;
   }

   public static void setOnNeedLoginListener(INeedLoginListener var0) {
      TuyaSmartNetWork.setOnNeedLoginListener(new 1(var0));
   }
}
