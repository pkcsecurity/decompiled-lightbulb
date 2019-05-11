package com.facebook.react.flat;

import android.text.TextPaint;
import android.text.style.CharacterStyle;

final class ShadowStyleSpan extends CharacterStyle {

   static final ShadowStyleSpan INSTANCE = new ShadowStyleSpan(0.0F, 0.0F, 0.0F, 0, true);
   private int mColor;
   private float mDx;
   private float mDy;
   private boolean mFrozen;
   private float mRadius;


   private ShadowStyleSpan(float var1, float var2, float var3, int var4, boolean var5) {
      this.mDx = var1;
      this.mDy = var2;
      this.mRadius = var3;
      this.mColor = var4;
      this.mFrozen = var5;
   }

   void freeze() {
      this.mFrozen = true;
   }

   public int getColor() {
      return this.mColor;
   }

   public float getRadius() {
      return this.mRadius;
   }

   boolean isFrozen() {
      return this.mFrozen;
   }

   ShadowStyleSpan mutableCopy() {
      return new ShadowStyleSpan(this.mDx, this.mDy, this.mRadius, this.mColor, false);
   }

   public boolean offsetMatches(float var1, float var2) {
      return this.mDx == var1 && this.mDy == var2;
   }

   public void setColor(int var1) {
      this.mColor = var1;
   }

   public void setOffset(float var1, float var2) {
      this.mDx = var1;
      this.mDy = var2;
   }

   public void setRadius(float var1) {
      this.mRadius = var1;
   }

   public void updateDrawState(TextPaint var1) {
      var1.setShadowLayer(this.mRadius, this.mDx, this.mDy, this.mColor);
   }
}
