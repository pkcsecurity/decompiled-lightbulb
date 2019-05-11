package com.facebook.react.modules.deviceinfo;

import android.content.Context;
import android.util.DisplayMetrics;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.BaseJavaModule;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.DisplayMetricsHolder;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

@ReactModule(
   name = "DeviceInfo"
)
public class DeviceInfoModule extends BaseJavaModule implements LifecycleEventListener {

   private float mFontScale;
   @Nullable
   private ReactApplicationContext mReactApplicationContext;


   public DeviceInfoModule(Context var1) {
      this.mReactApplicationContext = null;
      DisplayMetricsHolder.initDisplayMetricsIfNotInitialized(var1);
      this.mFontScale = var1.getResources().getConfiguration().fontScale;
   }

   public DeviceInfoModule(ReactApplicationContext var1) {
      this((Context)var1);
      this.mReactApplicationContext = var1;
   }

   private WritableMap getDimensionsConstants() {
      DisplayMetrics var3 = DisplayMetricsHolder.getWindowDisplayMetrics();
      DisplayMetrics var2 = DisplayMetricsHolder.getScreenDisplayMetrics();
      WritableMap var1 = Arguments.createMap();
      var1.putInt("width", var3.widthPixels);
      var1.putInt("height", var3.heightPixels);
      var1.putDouble("scale", (double)var3.density);
      var1.putDouble("fontScale", (double)this.mFontScale);
      var1.putDouble("densityDpi", (double)var3.densityDpi);
      WritableMap var5 = Arguments.createMap();
      var5.putInt("width", var2.widthPixels);
      var5.putInt("height", var2.heightPixels);
      var5.putDouble("scale", (double)var2.density);
      var5.putDouble("fontScale", (double)this.mFontScale);
      var5.putDouble("densityDpi", (double)var2.densityDpi);
      WritableMap var4 = Arguments.createMap();
      var4.putMap("windowPhysicalPixels", var1);
      var4.putMap("screenPhysicalPixels", var5);
      return var4;
   }

   public void emitUpdateDimensionsEvent() {
      if(this.mReactApplicationContext != null) {
         ((DeviceEventManagerModule.RCTDeviceEventEmitter)this.mReactApplicationContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("didUpdateDimensions", this.getDimensionsConstants());
      }
   }

   @Nullable
   public Map<String, Object> getConstants() {
      HashMap var1 = new HashMap();
      var1.put("Dimensions", this.getDimensionsConstants());
      return var1;
   }

   public String getName() {
      return "DeviceInfo";
   }

   public void onHostDestroy() {}

   public void onHostPause() {}

   public void onHostResume() {
      if(this.mReactApplicationContext != null) {
         float var1 = this.mReactApplicationContext.getResources().getConfiguration().fontScale;
         if(this.mFontScale != var1) {
            this.mFontScale = var1;
            this.emitUpdateDimensionsEvent();
         }

      }
   }
}
