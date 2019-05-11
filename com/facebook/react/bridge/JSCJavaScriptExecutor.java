package com.facebook.react.bridge;

import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.JavaScriptExecutor;
import com.facebook.react.bridge.ReactBridge;
import com.facebook.react.bridge.ReadableNativeMap;

@DoNotStrip
class JSCJavaScriptExecutor extends JavaScriptExecutor {

   static {
      ReactBridge.staticInit();
   }

   JSCJavaScriptExecutor(ReadableNativeMap var1) {
      super(initHybrid(var1));
   }

   private static native HybridData initHybrid(ReadableNativeMap var0);
}
