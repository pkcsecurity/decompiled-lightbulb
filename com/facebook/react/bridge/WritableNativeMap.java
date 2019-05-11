package com.facebook.react.bridge;

import com.facebook.infer.annotation.Assertions;
import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.ReactBridge;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;

@DoNotStrip
public class WritableNativeMap extends ReadableNativeMap implements WritableMap {

   static {
      ReactBridge.staticInit();
   }

   public WritableNativeMap() {
      super(initHybrid());
   }

   private static native HybridData initHybrid();

   private native void mergeNativeMap(ReadableNativeMap var1);

   private native void putNativeArray(String var1, WritableNativeArray var2);

   private native void putNativeMap(String var1, WritableNativeMap var2);

   public void merge(ReadableMap var1) {
      Assertions.assertCondition(var1 instanceof ReadableNativeMap, "Illegal type provided");
      this.mergeNativeMap((ReadableNativeMap)var1);
   }

   public void putArray(String var1, WritableArray var2) {
      boolean var3;
      if(var2 != null && !(var2 instanceof WritableNativeArray)) {
         var3 = false;
      } else {
         var3 = true;
      }

      Assertions.assertCondition(var3, "Illegal type provided");
      this.putNativeArray(var1, (WritableNativeArray)var2);
   }

   public native void putBoolean(String var1, boolean var2);

   public native void putDouble(String var1, double var2);

   public native void putInt(String var1, int var2);

   public void putMap(String var1, WritableMap var2) {
      boolean var3;
      if(var2 != null && !(var2 instanceof WritableNativeMap)) {
         var3 = false;
      } else {
         var3 = true;
      }

      Assertions.assertCondition(var3, "Illegal type provided");
      this.putNativeMap(var1, (WritableNativeMap)var2);
   }

   public native void putNull(String var1);

   public native void putString(String var1, String var2);
}
