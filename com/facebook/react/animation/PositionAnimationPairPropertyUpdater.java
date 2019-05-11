package com.facebook.react.animation;

import android.view.View;
import com.facebook.react.animation.AbstractFloatPairPropertyUpdater;

public class PositionAnimationPairPropertyUpdater extends AbstractFloatPairPropertyUpdater {

   public PositionAnimationPairPropertyUpdater(float var1, float var2) {
      super(var1, var2);
   }

   public PositionAnimationPairPropertyUpdater(float var1, float var2, float var3, float var4) {
      super(var1, var2, var3, var4);
   }

   protected void getProperty(View var1, float[] var2) {
      var2[0] = var1.getX() + (float)var1.getWidth() * 0.5F;
      var2[1] = var1.getY() + (float)var1.getHeight() * 0.5F;
   }

   protected void setProperty(View var1, float[] var2) {
      var1.setX(var2[0] - (float)var1.getWidth() * 0.5F);
      var1.setY(var2[1] - (float)var1.getHeight() * 0.5F);
   }
}
