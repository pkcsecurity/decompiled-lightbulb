package com.facebook.react.bridge;

import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.NativeArray;

@DoNotStrip
public class CxxCallbackImpl implements Callback {

   @DoNotStrip
   private final HybridData mHybridData;


   @DoNotStrip
   private CxxCallbackImpl(HybridData var1) {
      this.mHybridData = var1;
   }

   private native void nativeInvoke(NativeArray var1);

   public void invoke(Object ... var1) {
      this.nativeInvoke(Arguments.fromJavaArgs(var1));
   }
}
