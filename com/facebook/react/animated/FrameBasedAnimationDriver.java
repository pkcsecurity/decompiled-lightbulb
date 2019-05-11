package com.facebook.react.animated;

import com.facebook.react.animated.AnimationDriver;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

class FrameBasedAnimationDriver extends AnimationDriver {

   private static final double FRAME_TIME_MILLIS = 16.666666666666668D;
   private int mCurrentLoop;
   private final double[] mFrames;
   private double mFromValue;
   private int mIterations;
   private long mStartFrameTimeNanos = -1L;
   private final double mToValue;


   FrameBasedAnimationDriver(ReadableMap var1) {
      ReadableArray var6 = var1.getArray("frames");
      int var3 = var6.size();
      this.mFrames = new double[var3];

      int var2;
      for(var2 = 0; var2 < var3; ++var2) {
         this.mFrames[var2] = var6.getDouble(var2);
      }

      this.mToValue = var1.getDouble("toValue");
      boolean var5 = var1.hasKey("iterations");
      boolean var4 = true;
      if(var5) {
         var2 = var1.getInt("iterations");
      } else {
         var2 = 1;
      }

      this.mIterations = var2;
      this.mCurrentLoop = 1;
      if(this.mIterations != 0) {
         var4 = false;
      }

      this.mHasFinished = var4;
   }

   public void runAnimationStep(long var1) {
      if(this.mStartFrameTimeNanos < 0L) {
         this.mStartFrameTimeNanos = var1;
         this.mFromValue = this.mAnimatedValue.mValue;
      }

      int var5 = (int)((double)((var1 - this.mStartFrameTimeNanos) / 1000000L) / 16.666666666666668D);
      if(var5 < 0) {
         throw new IllegalStateException("Calculated frame index should never be lower than 0");
      } else if(!this.mHasFinished) {
         double var3;
         if(var5 >= this.mFrames.length - 1) {
            var3 = this.mToValue;
            if(this.mIterations != -1 && this.mCurrentLoop >= this.mIterations) {
               this.mHasFinished = true;
            } else {
               this.mStartFrameTimeNanos = var1;
               ++this.mCurrentLoop;
            }
         } else {
            var3 = this.mFromValue;
            var3 += this.mFrames[var5] * (this.mToValue - this.mFromValue);
         }

         this.mAnimatedValue.mValue = var3;
      }
   }
}
