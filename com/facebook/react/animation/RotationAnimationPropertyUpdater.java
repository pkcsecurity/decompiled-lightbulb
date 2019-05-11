package com.facebook.react.animation;

import android.view.View;
import com.facebook.react.animation.AbstractSingleFloatProperyUpdater;

public class RotationAnimationPropertyUpdater extends AbstractSingleFloatProperyUpdater {

   public RotationAnimationPropertyUpdater(float var1) {
      super(var1);
   }

   protected float getProperty(View var1) {
      return var1.getRotation();
   }

   protected void setProperty(View var1, float var2) {
      var1.setRotation((float)Math.toDegrees((double)var2));
   }
}
