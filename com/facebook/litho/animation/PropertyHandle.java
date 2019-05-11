package com.facebook.litho.animation;

import com.facebook.litho.animation.AnimatedProperty;

public final class PropertyHandle {

   private final AnimatedProperty mProperty;
   private final String mTransitionKey;


   public PropertyHandle(String var1, AnimatedProperty var2) {
      this.mTransitionKey = var1;
      this.mProperty = var2;
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(var1 != null) {
         if(this.getClass() != var1.getClass()) {
            return false;
         } else {
            PropertyHandle var2 = (PropertyHandle)var1;
            return this.mTransitionKey.equals(var2.mTransitionKey) && this.mProperty.equals(var2.mProperty);
         }
      } else {
         return false;
      }
   }

   public AnimatedProperty getProperty() {
      return this.mProperty;
   }

   public String getTransitionKey() {
      return this.mTransitionKey;
   }

   public int hashCode() {
      return this.mTransitionKey.hashCode() * 31 + this.mProperty.hashCode();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("PropertyHandle{ mTransitionKey=\'");
      var1.append(this.mTransitionKey);
      var1.append("\', mProperty=");
      var1.append(this.mProperty);
      var1.append("}");
      return var1.toString();
   }
}
