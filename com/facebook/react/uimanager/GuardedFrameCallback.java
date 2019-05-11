package com.facebook.react.uimanager;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.ChoreographerCompat;

public abstract class GuardedFrameCallback extends ChoreographerCompat.FrameCallback {

   private final ReactContext mReactContext;


   protected GuardedFrameCallback(ReactContext var1) {
      this.mReactContext = var1;
   }

   public final void doFrame(long var1) {
      try {
         this.doFrameGuarded(var1);
      } catch (RuntimeException var4) {
         this.mReactContext.handleException(var4);
      }
   }

   public abstract void doFrameGuarded(long var1);
}
