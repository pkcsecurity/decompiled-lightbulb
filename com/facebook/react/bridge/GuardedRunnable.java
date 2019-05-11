package com.facebook.react.bridge;

import com.facebook.react.bridge.ReactContext;

public abstract class GuardedRunnable implements Runnable {

   private final ReactContext mReactContext;


   public GuardedRunnable(ReactContext var1) {
      this.mReactContext = var1;
   }

   public final void run() {
      try {
         this.runGuarded();
      } catch (RuntimeException var2) {
         this.mReactContext.handleException(var2);
      }
   }

   public abstract void runGuarded();
}
