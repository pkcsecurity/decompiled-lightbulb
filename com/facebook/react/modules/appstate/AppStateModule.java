package com.facebook.react.modules.appstate;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;

@ReactModule(
   name = "AppState"
)
public class AppStateModule extends ReactContextBaseJavaModule implements LifecycleEventListener {

   public static final String APP_STATE_ACTIVE = "active";
   public static final String APP_STATE_BACKGROUND = "background";
   protected static final String NAME = "AppState";
   private String mAppState = "uninitialized";


   public AppStateModule(ReactApplicationContext var1) {
      super(var1);
   }

   private WritableMap createAppStateEventMap() {
      WritableMap var1 = Arguments.createMap();
      var1.putString("app_state", this.mAppState);
      return var1;
   }

   private void sendAppStateChangeEvent() {
      ((DeviceEventManagerModule.RCTDeviceEventEmitter)this.getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("appStateDidChange", this.createAppStateEventMap());
   }

   @ReactMethod
   public void getCurrentAppState(Callback var1, Callback var2) {
      var1.invoke(new Object[]{this.createAppStateEventMap()});
   }

   public String getName() {
      return "AppState";
   }

   public void initialize() {
      this.getReactApplicationContext().addLifecycleEventListener(this);
   }

   public void onHostDestroy() {}

   public void onHostPause() {
      this.mAppState = "background";
      this.sendAppStateChangeEvent();
   }

   public void onHostResume() {
      this.mAppState = "active";
      this.sendAppStateChangeEvent();
   }
}
