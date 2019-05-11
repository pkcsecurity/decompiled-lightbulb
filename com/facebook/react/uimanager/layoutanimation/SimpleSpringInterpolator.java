package com.facebook.react.uimanager.layoutanimation;

import android.view.animation.Interpolator;

class SimpleSpringInterpolator implements Interpolator {

   private static final float FACTOR = 0.5F;


   public float getInterpolation(float var1) {
      return (float)(Math.pow(2.0D, (double)(-10.0F * var1)) * Math.sin((double)(var1 - 0.125F) * 3.141592653589793D * 2.0D / 0.5D) + 1.0D);
   }
}
