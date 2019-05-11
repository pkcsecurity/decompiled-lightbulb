package com.facebook.jni;

import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;

@DoNotStrip
public class NativeRunnable implements Runnable {

   private final HybridData mHybridData;


   private NativeRunnable(HybridData var1) {
      this.mHybridData = var1;
   }

   public native void run();
}
