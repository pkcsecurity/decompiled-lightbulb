package com.facebook.react.animation;

import android.view.View;
import com.facebook.react.animation.AbstractSingleFloatProperyUpdater;

public class ScaleYAnimationPropertyUpdater extends AbstractSingleFloatProperyUpdater {

   public ScaleYAnimationPropertyUpdater(float var1) {
      super(var1);
   }

   public ScaleYAnimationPropertyUpdater(float var1, float var2) {
      super(var1, var2);
   }

   protected float getProperty(View var1) {
      return var1.getScaleY();
   }

   protected void setProperty(View var1, float var2) {
      var1.setScaleY(var2);
   }
}
