package com.facebook.react.bridge;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.JSInstance;

public final class CallbackImpl implements Callback {

   private final int mCallbackId;
   private boolean mInvoked;
   private final JSInstance mJSInstance;


   public CallbackImpl(JSInstance var1, int var2) {
      this.mJSInstance = var1;
      this.mCallbackId = var2;
      this.mInvoked = false;
   }

   public void invoke(Object ... var1) {
      if(this.mInvoked) {
         throw new RuntimeException("Illegal callback invocation from native module. This callback type only permits a single invocation from native code.");
      } else {
         this.mJSInstance.invokeCallback(this.mCallbackId, Arguments.fromJavaArgs(var1));
         this.mInvoked = true;
      }
   }
}
