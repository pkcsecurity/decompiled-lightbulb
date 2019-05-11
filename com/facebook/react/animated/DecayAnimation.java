package com.facebook.react.animated;

import com.facebook.react.animated.AnimationDriver;
import com.facebook.react.bridge.ReadableMap;

public class DecayAnimation extends AnimationDriver {

   private int mCurrentLoop;
   private final double mDeceleration;
   private double mFromValue = 0.0D;
   private int mIterations;
   private double mLastValue = 0.0D;
   private long mStartFrameTimeMillis = -1L;
   private final double mVelocity;


   public DecayAnimation(ReadableMap var1) {
      this.mVelocity = var1.getDouble("velocity");
      this.mDeceleration = var1.getDouble("deceleration");
      boolean var4 = var1.hasKey("iterations");
      boolean var3 = true;
      int var2;
      if(var4) {
         var2 = var1.getInt("iterations");
      } else {
         var2 = 1;
      }

      this.mIterations = var2;
      this.mCurrentLoop = 1;
      if(this.mIterations != 0) {
         var3 = false;
      }

      this.mHasFinished = var3;
   }

   public void runAnimationStep(long var1) {
      var1 /= 1000000L;
      if(this.mStartFrameTimeMillis == -1L) {
         this.mStartFrameTimeMillis = var1 - 16L;
         if(this.mFromValue == this.mLastValue) {
            this.mFromValue = this.mAnimatedValue.mValue;
         } else {
            this.mAnimatedValue.mValue = this.mFromValue;
         }

         this.mLastValue = this.mAnimatedValue.mValue;
      }

      double var3 = this.mFromValue + this.mVelocity / (1.0D - this.mDeceleration) * (1.0D - Math.exp(-(1.0D - this.mDeceleration) * (double)(var1 - this.mStartFrameTimeMillis)));
      if(Math.abs(this.mLastValue - var3) < 0.1D) {
         if(this.mIterations != -1 && this.mCurrentLoop >= this.mIterations) {
            this.mHasFinished = true;
            return;
         }

         this.mStartFrameTimeMillis = -1L;
         ++this.mCurrentLoop;
      }

      this.mLastValue = var3;
      this.mAnimatedValue.mValue = var3;
   }
}
