package com.facebook.react.modules.share;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(
   name = "ShareModule"
)
public class ShareModule extends ReactContextBaseJavaModule {

   static final String ACTION_SHARED = "sharedAction";
   static final String ERROR_INVALID_CONTENT = "E_INVALID_CONTENT";
   static final String ERROR_UNABLE_TO_OPEN_DIALOG = "E_UNABLE_TO_OPEN_DIALOG";


   public ShareModule(ReactApplicationContext var1) {
      super(var1);
   }

   public String getName() {
      return "ShareModule";
   }

   @ReactMethod
   public void share(ReadableMap param1, String param2, Promise param3) {
      // $FF: Couldn't be decompiled
   }
}
