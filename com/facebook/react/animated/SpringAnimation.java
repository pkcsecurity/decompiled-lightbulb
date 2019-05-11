package com.facebook.react.animated;

import com.facebook.react.animated.AnimationDriver;
import com.facebook.react.bridge.ReadableMap;

class SpringAnimation extends AnimationDriver {

   private static final double MAX_DELTA_TIME_SEC = 0.064D;
   private static final double SOLVER_TIMESTEP_SEC = 0.001D;
   private int mCurrentLoop;
   private final SpringAnimation.PhysicsState mCurrentState = new SpringAnimation.PhysicsState(null);
   private double mDisplacementFromRestThreshold;
   private double mEndValue;
   private double mInitialVelocity;
   private int mIterations;
   private long mLastTime;
   private double mOriginalValue;
   private boolean mOvershootClampingEnabled;
   private double mRestSpeedThreshold;
   private double mSpringDamping;
   private double mSpringMass;
   private boolean mSpringStarted;
   private double mSpringStiffness;
   private double mStartValue;
   private double mTimeAccumulator = 0.0D;


   SpringAnimation(ReadableMap var1) {
      boolean var3 = false;
      this.mCurrentLoop = 0;
      this.mSpringStiffness = var1.getDouble("stiffness");
      this.mSpringDamping = var1.getDouble("damping");
      this.mSpringMass = var1.getDouble("mass");
      this.mInitialVelocity = var1.getDouble("initialVelocity");
      this.mCurrentState.velocity = this.mInitialVelocity;
      this.mEndValue = var1.getDouble("toValue");
      this.mRestSpeedThreshold = var1.getDouble("restSpeedThreshold");
      this.mDisplacementFromRestThreshold = var1.getDouble("restDisplacementThreshold");
      this.mOvershootClampingEnabled = var1.getBoolean("overshootClamping");
      int var2;
      if(var1.hasKey("iterations")) {
         var2 = var1.getInt("iterations");
      } else {
         var2 = 1;
      }

      this.mIterations = var2;
      if(this.mIterations == 0) {
         var3 = true;
      }

      this.mHasFinished = var3;
   }

   private void advance(double var1) {
      if(!this.isAtRest()) {
         double var3 = 0.064D;
         if(var1 > 0.064D) {
            var1 = var3;
         }

         this.mTimeAccumulator += var1;
         double var7 = this.mSpringDamping;
         var3 = this.mSpringMass;
         double var5 = this.mSpringStiffness;
         var1 = -this.mInitialVelocity;
         double var13 = var7 / (Math.sqrt(var5 * var3) * 2.0D);
         double var15 = Math.sqrt(var5 / var3);
         double var9 = Math.sqrt(1.0D - var13 * var13) * var15;
         var5 = this.mEndValue - this.mStartValue;
         double var11 = this.mTimeAccumulator;
         if(var13 < 1.0D) {
            var3 = Math.exp(-var13 * var15 * var11);
            var7 = this.mEndValue;
            var13 *= var15;
            var1 += var13 * var5;
            var15 = var1 / var9;
            double var19 = var11 * var9;
            var11 = Math.sin(var19);
            double var17 = Math.cos(var19);
            var1 = var13 * var3 * (Math.sin(var19) * var1 / var9 + Math.cos(var19) * var5) - (Math.cos(var19) * var1 - var9 * var5 * Math.sin(var19)) * var3;
            var3 = var7 - (var15 * var11 + var17 * var5) * var3;
         } else {
            var7 = Math.exp(-var15 * var11);
            var3 = this.mEndValue - ((var15 * var5 + var1) * var11 + var5) * var7;
            var1 = var7 * (var1 * (var11 * var15 - 1.0D) + var11 * var5 * var15 * var15);
         }

         this.mCurrentState.position = var3;
         this.mCurrentState.velocity = var1;
         if(this.isAtRest() || this.mOvershootClampingEnabled && this.isOvershooting()) {
            if(this.mSpringStiffness > 0.0D) {
               this.mStartValue = this.mEndValue;
               this.mCurrentState.position = this.mEndValue;
            } else {
               this.mEndValue = this.mCurrentState.position;
               this.mStartValue = this.mEndValue;
            }

            this.mCurrentState.velocity = 0.0D;
         }

      }
   }

   private double getDisplacementDistanceForState(SpringAnimation.PhysicsState var1) {
      return Math.abs(this.mEndValue - var1.position);
   }

   private boolean isAtRest() {
      return Math.abs(this.mCurrentState.velocity) <= this.mRestSpeedThreshold && (this.getDisplacementDistanceForState(this.mCurrentState) <= this.mDisplacementFromRestThreshold || this.mSpringStiffness == 0.0D);
   }

   private boolean isOvershooting() {
      return this.mSpringStiffness > 0.0D && (this.mStartValue < this.mEndValue && this.mCurrentState.position > this.mEndValue || this.mStartValue > this.mEndValue && this.mCurrentState.position < this.mEndValue);
   }

   public void runAnimationStep(long var1) {
      var1 /= 1000000L;
      if(!this.mSpringStarted) {
         if(this.mCurrentLoop == 0) {
            this.mOriginalValue = this.mAnimatedValue.mValue;
            this.mCurrentLoop = 1;
         }

         SpringAnimation.PhysicsState var5 = this.mCurrentState;
         double var3 = this.mAnimatedValue.mValue;
         var5.position = var3;
         this.mStartValue = var3;
         this.mLastTime = var1;
         this.mTimeAccumulator = 0.0D;
         this.mSpringStarted = true;
      }

      this.advance((double)(var1 - this.mLastTime) / 1000.0D);
      this.mLastTime = var1;
      this.mAnimatedValue.mValue = this.mCurrentState.position;
      if(this.isAtRest()) {
         if(this.mIterations != -1 && this.mCurrentLoop >= this.mIterations) {
            this.mHasFinished = true;
            return;
         }

         this.mSpringStarted = false;
         this.mAnimatedValue.mValue = this.mOriginalValue;
         ++this.mCurrentLoop;
      }

   }

   static class PhysicsState {

      double position;
      double velocity;


      private PhysicsState() {}

      // $FF: synthetic method
      PhysicsState(Object var1) {
         this();
      }
   }
}
