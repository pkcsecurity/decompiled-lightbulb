package com.facebook.react.animation;

import android.view.View;
import com.facebook.react.animation.AbstractSingleFloatProperyUpdater;

public class ScaleXAnimationPropertyUpdater extends AbstractSingleFloatProperyUpdater {

   public ScaleXAnimationPropertyUpdater(float var1) {
      super(var1);
   }

   public ScaleXAnimationPropertyUpdater(float var1, float var2) {
      super(var1, var2);
   }

   protected float getProperty(View var1) {
      return var1.getScaleX();
   }

   protected void setProperty(View var1, float var2) {
      var1.setScaleX(var2);
   }
}
