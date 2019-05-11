package com.facebook.litho.animation;

import com.facebook.litho.animation.AnimatedProperty;
import com.facebook.litho.animation.PropertyHandle;

public class PropertyAnimation {

   private final PropertyHandle mPropertyHandle;
   private final float mTargetValue;


   public PropertyAnimation(PropertyHandle var1, float var2) {
      this.mPropertyHandle = var1;
      this.mTargetValue = var2;
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(var1 != null) {
         if(this.getClass() != var1.getClass()) {
            return false;
         } else {
            PropertyAnimation var2 = (PropertyAnimation)var1;
            return Float.compare(var2.mTargetValue, this.mTargetValue) == 0 && this.mPropertyHandle.equals(var2.mPropertyHandle);
         }
      } else {
         return false;
      }
   }

   public AnimatedProperty getProperty() {
      return this.mPropertyHandle.getProperty();
   }

   public PropertyHandle getPropertyHandle() {
      return this.mPropertyHandle;
   }

   public float getTargetValue() {
      return this.mTargetValue;
   }

   public String getTransitionKey() {
      return this.mPropertyHandle.getTransitionKey();
   }

   public int hashCode() {
      int var2 = this.mPropertyHandle.hashCode();
      int var1;
      if(this.mTargetValue != 0.0F) {
         var1 = Float.floatToIntBits(this.mTargetValue);
      } else {
         var1 = 0;
      }

      return var2 * 31 + var1;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("PropertyAnimation{ PropertyHandle=");
      var1.append(this.mPropertyHandle);
      var1.append(", TargetValue=");
      var1.append(this.mTargetValue);
      var1.append("}");
      return var1.toString();
   }
}
