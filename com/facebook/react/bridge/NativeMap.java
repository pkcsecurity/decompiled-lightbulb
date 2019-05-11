package com.facebook.react.bridge;

import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.ReactBridge;

@DoNotStrip
public abstract class NativeMap {

   @DoNotStrip
   private HybridData mHybridData;


   static {
      ReactBridge.staticInit();
   }

   public NativeMap(HybridData var1) {
      this.mHybridData = var1;
   }

   public native String toString();
}