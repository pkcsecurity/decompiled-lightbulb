package com.facebook.react.bridge;

import com.facebook.react.bridge.JSCJavaScriptExecutor;
import com.facebook.react.bridge.JavaScriptExecutor;
import com.facebook.react.bridge.JavaScriptExecutorFactory;
import com.facebook.react.bridge.WritableNativeMap;

public class JSCJavaScriptExecutorFactory implements JavaScriptExecutorFactory {

   private final String mAppName;
   private final String mDeviceName;


   public JSCJavaScriptExecutorFactory(String var1, String var2) {
      this.mAppName = var1;
      this.mDeviceName = var2;
   }

   public JavaScriptExecutor create() throws Exception {
      WritableNativeMap var1 = new WritableNativeMap();
      var1.putString("OwnerIdentity", "ReactNative");
      var1.putString("AppIdentity", this.mAppName);
      var1.putString("DeviceIdentity", this.mDeviceName);
      return new JSCJavaScriptExecutor(var1);
   }
}
