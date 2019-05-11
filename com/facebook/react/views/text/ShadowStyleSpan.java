package com.facebook.react.views.text;

import android.text.TextPaint;
import android.text.style.CharacterStyle;

public class ShadowStyleSpan extends CharacterStyle {

   private final int mColor;
   private final float mDx;
   private final float mDy;
   private final float mRadius;


   public ShadowStyleSpan(float var1, float var2, float var3, int var4) {
      this.mDx = var1;
      this.mDy = var2;
      this.mRadius = var3;
      this.mColor = var4;
   }

   public void updateDrawState(TextPaint var1) {
      var1.setShadowLayer(this.mRadius, this.mDx, this.mDy, this.mColor);
   }
}
