package com.facebook.litho.animation;

import com.facebook.litho.animation.PropertyHandle;
import com.facebook.litho.animation.Resolver;
import com.facebook.litho.animation.RuntimeValue;

public class FloatValue implements RuntimeValue {

   private final float mValue;


   public FloatValue(float var1) {
      this.mValue = var1;
   }

   public float resolve(Resolver var1, PropertyHandle var2) {
      return this.mValue;
   }
}
