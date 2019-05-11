package com.facebook.react.animation;

import android.view.View;
import com.facebook.react.animation.AbstractFloatPairPropertyUpdater;

public class ScaleXYAnimationPairPropertyUpdater extends AbstractFloatPairPropertyUpdater {

   public ScaleXYAnimationPairPropertyUpdater(float var1, float var2) {
      super(var1, var2);
   }

   public ScaleXYAnimationPairPropertyUpdater(float var1, float var2, float var3, float var4) {
      super(var1, var2, var3, var4);
   }

   protected void getProperty(View var1, float[] var2) {
      var2[0] = var1.getScaleX();
      var2[1] = var1.getScaleY();
   }

   protected void setProperty(View var1, float[] var2) {
      var1.setScaleX(var2[0]);
      var1.setScaleY(var2[1]);
   }
}
