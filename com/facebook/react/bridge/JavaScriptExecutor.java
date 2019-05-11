package com.facebook.react.bridge;

import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;

@DoNotStrip
public abstract class JavaScriptExecutor {

   private final HybridData mHybridData;


   protected JavaScriptExecutor(HybridData var1) {
      this.mHybridData = var1;
   }

   public void close() {
      this.mHybridData.resetNative();
   }
}
