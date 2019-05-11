package com.facebook.react.modules.intent;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(
   name = "IntentAndroid"
)
public class IntentModule extends ReactContextBaseJavaModule {

   public IntentModule(ReactApplicationContext var1) {
      super(var1);
   }

   @ReactMethod
   public void canOpenURL(String param1, Promise param2) {
      // $FF: Couldn't be decompiled
   }

   @ReactMethod
   public void getInitialURL(Promise param1) {
      // $FF: Couldn't be decompiled
   }

   public String getName() {
      return "IntentAndroid";
   }

   @ReactMethod
   public void openURL(String param1, Promise param2) {
      // $FF: Couldn't be decompiled
   }
}
