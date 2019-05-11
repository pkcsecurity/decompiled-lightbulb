package com.facebook.react.bridge;

import com.facebook.infer.annotation.Assertions;
import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.ReactBridge;
import com.facebook.react.bridge.ReadableNativeArray;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;

@DoNotStrip
public class WritableNativeArray extends ReadableNativeArray implements WritableArray {

   static {
      ReactBridge.staticInit();
   }

   public WritableNativeArray() {
      super(initHybrid());
   }

   private static native HybridData initHybrid();

   private native void pushNativeArray(WritableNativeArray var1);

   private native void pushNativeMap(WritableNativeMap var1);

   public void pushArray(WritableArray var1) {
      boolean var2;
      if(var1 != null && !(var1 instanceof WritableNativeArray)) {
         var2 = false;
      } else {
         var2 = true;
      }

      Assertions.assertCondition(var2, "Illegal type provided");
      this.pushNativeArray((WritableNativeArray)var1);
   }

   public native void pushBoolean(boolean var1);

   public native void pushDouble(double var1);

   public native void pushInt(int var1);

   public void pushMap(WritableMap var1) {
      boolean var2;
      if(var1 != null && !(var1 instanceof WritableNativeMap)) {
         var2 = false;
      } else {
         var2 = true;
      }

      Assertions.assertCondition(var2, "Illegal type provided");
      this.pushNativeMap((WritableNativeMap)var1);
   }

   public native void pushNull();

   public native void pushString(String var1);
}
