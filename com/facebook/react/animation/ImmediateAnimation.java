package com.facebook.react.animation;

import com.facebook.react.animation.Animation;
import com.facebook.react.animation.AnimationPropertyUpdater;

public class ImmediateAnimation extends Animation {

   public ImmediateAnimation(int var1, AnimationPropertyUpdater var2) {
      super(var1, var2);
   }

   public void run() {
      this.onUpdate(1.0F);
      this.finish();
   }
}
