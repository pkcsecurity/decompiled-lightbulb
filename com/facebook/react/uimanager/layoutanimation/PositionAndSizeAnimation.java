package com.facebook.react.uimanager.layoutanimation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import com.facebook.react.uimanager.layoutanimation.HandleLayout;

class PositionAndSizeAnimation extends Animation implements HandleLayout {

   private final int mDeltaHeight;
   private final int mDeltaWidth;
   private final float mDeltaX;
   private final float mDeltaY;
   private final int mStartHeight;
   private final int mStartWidth;
   private final float mStartX;
   private final float mStartY;
   private final View mView;


   public PositionAndSizeAnimation(View var1, int var2, int var3, int var4, int var5) {
      this.mView = var1;
      this.mStartX = var1.getX() - var1.getTranslationX();
      this.mStartY = var1.getY() - var1.getTranslationY();
      this.mStartWidth = var1.getWidth();
      this.mStartHeight = var1.getHeight();
      this.mDeltaX = (float)var2 - this.mStartX;
      this.mDeltaY = (float)var3 - this.mStartY;
      this.mDeltaWidth = var4 - this.mStartWidth;
      this.mDeltaHeight = var5 - this.mStartHeight;
   }

   protected void applyTransformation(float var1, Transformation var2) {
      float var3 = this.mStartX + this.mDeltaX * var1;
      float var4 = this.mStartY + this.mDeltaY * var1;
      float var5 = (float)this.mStartWidth;
      float var6 = (float)this.mDeltaWidth;
      float var7 = (float)this.mStartHeight;
      float var8 = (float)this.mDeltaHeight;
      this.mView.layout(Math.round(var3), Math.round(var4), Math.round(var3 + var5 + var6 * var1), Math.round(var4 + var7 + var8 * var1));
   }

   public boolean willChangeBounds() {
      return true;
   }
}
