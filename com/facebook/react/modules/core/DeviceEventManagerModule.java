package com.facebook.react.modules.core;

import android.net.Uri;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import javax.annotation.Nullable;

@ReactModule(
   name = "DeviceEventManager"
)
public class DeviceEventManagerModule extends ReactContextBaseJavaModule {

   private final Runnable mInvokeDefaultBackPressRunnable;


   public DeviceEventManagerModule(ReactApplicationContext var1, final DefaultHardwareBackBtnHandler var2) {
      super(var1);
      this.mInvokeDefaultBackPressRunnable = new Runnable() {
         public void run() {
            UiThreadUtil.assertOnUiThread();
            var2.invokeDefaultOnBackPressed();
         }
      };
   }

   public void emitHardwareBackPressed() {
      ((DeviceEventManagerModule.RCTDeviceEventEmitter)this.getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("hardwareBackPress", (Object)null);
   }

   public void emitNewIntentReceived(Uri var1) {
      WritableMap var2 = Arguments.createMap();
      var2.putString("url", var1.toString());
      ((DeviceEventManagerModule.RCTDeviceEventEmitter)this.getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("url", var2);
   }

   public String getName() {
      return "DeviceEventManager";
   }

   @ReactMethod
   public void invokeDefaultBackPressHandler() {
      this.getReactApplicationContext().runOnUiQueueThread(this.mInvokeDefaultBackPressRunnable);
   }

   public interface RCTDeviceEventEmitter extends JavaScriptModule {

      void emit(String var1, @Nullable Object var2);
   }
}
