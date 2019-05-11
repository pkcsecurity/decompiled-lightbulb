package com.facebook.react.uimanager.layoutanimation;

import android.graphics.Paint;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.Animation.AnimationListener;

class OpacityAnimation extends Animation {

   private final float mDeltaOpacity;
   private final float mStartOpacity;
   private final View mView;


   public OpacityAnimation(View var1, float var2, float var3) {
      this.mView = var1;
      this.mStartOpacity = var2;
      this.mDeltaOpacity = var3 - var2;
      this.setAnimationListener(new OpacityAnimation.OpacityAnimationListener(var1));
   }

   protected void applyTransformation(float var1, Transformation var2) {
      this.mView.setAlpha(this.mStartOpacity + this.mDeltaOpacity * var1);
   }

   public boolean willChangeBounds() {
      return false;
   }

   static class OpacityAnimationListener implements AnimationListener {

      private boolean mLayerTypeChanged = false;
      private final View mView;


      public OpacityAnimationListener(View var1) {
         this.mView = var1;
      }

      public void onAnimationEnd(Animation var1) {
         if(this.mLayerTypeChanged) {
            this.mView.setLayerType(0, (Paint)null);
         }

      }

      public void onAnimationRepeat(Animation var1) {}

      public void onAnimationStart(Animation var1) {
         if(this.mView.hasOverlappingRendering() && this.mView.getLayerType() == 0) {
            this.mLayerTypeChanged = true;
            this.mView.setLayerType(2, (Paint)null);
         }

      }
   }
}
