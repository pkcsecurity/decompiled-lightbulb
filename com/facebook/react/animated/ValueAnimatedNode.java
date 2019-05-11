package com.facebook.react.animated;

import com.facebook.react.animated.AnimatedNode;
import com.facebook.react.animated.AnimatedNodeValueListener;
import com.facebook.react.bridge.ReadableMap;
import javax.annotation.Nullable;

class ValueAnimatedNode extends AnimatedNode {

   double mOffset = 0.0D;
   double mValue = Double.NaN;
   @Nullable
   private AnimatedNodeValueListener mValueListener;


   public ValueAnimatedNode() {}

   public ValueAnimatedNode(ReadableMap var1) {
      this.mValue = var1.getDouble("value");
      this.mOffset = var1.getDouble("offset");
   }

   public void extractOffset() {
      this.mOffset += this.mValue;
      this.mValue = 0.0D;
   }

   public void flattenOffset() {
      this.mValue += this.mOffset;
      this.mOffset = 0.0D;
   }

   public double getValue() {
      return this.mOffset + this.mValue;
   }

   public void onValueUpdate() {
      if(this.mValueListener != null) {
         this.mValueListener.onValueUpdate(this.getValue());
      }
   }

   public void setValueListener(@Nullable AnimatedNodeValueListener var1) {
      this.mValueListener = var1;
   }
}
