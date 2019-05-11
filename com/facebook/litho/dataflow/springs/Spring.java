package com.facebook.litho.dataflow.springs;

import com.facebook.litho.dataflow.springs.SpringConfig;
import com.facebook.litho.dataflow.springs.SpringListener;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

public class Spring {

   private static int ID;
   private static final double MAX_DELTA_TIME_SEC = 0.064D;
   private static final double SOLVER_TIMESTEP_SEC = 0.001D;
   private final Spring.PhysicsState mCurrentState = new Spring.PhysicsState(null);
   private double mDisplacementFromRestThreshold = 0.005D;
   private double mEndValue;
   private final String mId;
   private final CopyOnWriteArraySet<SpringListener> mListeners = new CopyOnWriteArraySet();
   private boolean mOvershootClampingEnabled;
   private final Spring.PhysicsState mPreviousState = new Spring.PhysicsState(null);
   private double mRestSpeedThreshold = 0.005D;
   private SpringConfig mSpringConfig;
   private double mStartValue;
   private final Spring.PhysicsState mTempState = new Spring.PhysicsState(null);
   private double mTimeAccumulator = 0.0D;
   private boolean mWasAtRest = true;


   public Spring() {
      StringBuilder var2 = new StringBuilder();
      var2.append("spring:");
      int var1 = ID;
      ID = var1 + 1;
      var2.append(var1);
      this.mId = var2.toString();
      this.setSpringConfig(SpringConfig.defaultConfig);
   }

   private double getDisplacementDistanceForState(Spring.PhysicsState var1) {
      return Math.abs(this.mEndValue - var1.position);
   }

   private void interpolate(double var1) {
      Spring.PhysicsState var9 = this.mCurrentState;
      double var3 = this.mCurrentState.position;
      double var5 = this.mPreviousState.position;
      double var7 = 1.0D - var1;
      var9.position = var3 * var1 + var5 * var7;
      this.mCurrentState.velocity = this.mCurrentState.velocity * var1 + this.mPreviousState.velocity * var7;
   }

   public Spring addListener(SpringListener var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("newListener is required");
      } else {
         this.mListeners.add(var1);
         return this;
      }
   }

   public void advance(double var1) {
      boolean var28 = this.isAtRest();
      if(!var28 || !this.mWasAtRest) {
         double var3 = 0.064D;
         if(var1 > 0.064D) {
            var1 = var3;
         }

         this.mTimeAccumulator += var1;
         double var9 = this.mSpringConfig.tension;
         double var11 = this.mSpringConfig.friction;
         var3 = this.mCurrentState.position;
         var1 = this.mCurrentState.velocity;
         double var5 = this.mTempState.position;

         double var7;
         double var13;
         double var15;
         double var17;
         double var19;
         for(var7 = this.mTempState.velocity; this.mTimeAccumulator >= 0.001D; var1 += (var13 + (var15 + var17) * 2.0D + ((var19 - var5) * var9 - var11 * var7)) * 0.16666666666666666D * 0.001D) {
            this.mTimeAccumulator -= 0.001D;
            if(this.mTimeAccumulator < 0.001D) {
               this.mPreviousState.position = var3;
               this.mPreviousState.velocity = var1;
            }

            var13 = (this.mEndValue - var5) * var9 - var11 * var1;
            double var21 = var1 + var13 * 0.001D * 0.5D;
            var15 = (this.mEndValue - (var1 * 0.001D * 0.5D + var3)) * var9 - var11 * var21;
            double var23 = var1 + var15 * 0.001D * 0.5D;
            var17 = (this.mEndValue - (var21 * 0.001D * 0.5D + var3)) * var9 - var11 * var23;
            var5 = var23 * 0.001D + var3;
            var7 = var1 + var17 * 0.001D;
            var19 = this.mEndValue;
            var3 += (var1 + (var21 + var23) * 2.0D + var7) * 0.16666666666666666D * 0.001D;
         }

         this.mTempState.position = var5;
         this.mTempState.velocity = var7;
         this.mCurrentState.position = var3;
         this.mCurrentState.velocity = var1;
         if(this.mTimeAccumulator > 0.0D) {
            this.interpolate(this.mTimeAccumulator / 0.001D);
         }

         boolean var27;
         label72: {
            if(!this.isAtRest()) {
               var27 = var28;
               if(!this.mOvershootClampingEnabled) {
                  break label72;
               }

               var27 = var28;
               if(!this.isOvershooting()) {
                  break label72;
               }
            }

            if(var9 > 0.0D) {
               this.mStartValue = this.mEndValue;
               this.mCurrentState.position = this.mEndValue;
            } else {
               this.mEndValue = this.mCurrentState.position;
               this.mStartValue = this.mEndValue;
            }

            this.setVelocity(0.0D);
            var27 = true;
         }

         var28 = this.mWasAtRest;
         boolean var26 = false;
         boolean var25;
         if(var28) {
            this.mWasAtRest = false;
            var25 = true;
         } else {
            var25 = false;
         }

         if(var27) {
            this.mWasAtRest = true;
            var26 = true;
         }

         Iterator var29 = this.mListeners.iterator();

         while(var29.hasNext()) {
            SpringListener var30 = (SpringListener)var29.next();
            if(var25) {
               var30.onSpringActivate(this);
            }

            var30.onSpringUpdate(this);
            if(var26) {
               var30.onSpringAtRest(this);
            }
         }

      }
   }

   public boolean currentValueIsApproximately(double var1) {
      return Math.abs(this.getCurrentValue() - var1) <= this.getRestDisplacementThreshold();
   }

   public void destroy() {
      this.mListeners.clear();
   }

   public double getCurrentDisplacementDistance() {
      return this.getDisplacementDistanceForState(this.mCurrentState);
   }

   public double getCurrentValue() {
      return this.mCurrentState.position;
   }

   public double getEndValue() {
      return this.mEndValue;
   }

   public String getId() {
      return this.mId;
   }

   public double getRestDisplacementThreshold() {
      return this.mDisplacementFromRestThreshold;
   }

   public double getRestSpeedThreshold() {
      return this.mRestSpeedThreshold;
   }

   public SpringConfig getSpringConfig() {
      return this.mSpringConfig;
   }

   public double getStartValue() {
      return this.mStartValue;
   }

   public double getVelocity() {
      return this.mCurrentState.velocity;
   }

   public boolean isAtRest() {
      return Math.abs(this.mCurrentState.velocity) <= this.mRestSpeedThreshold && (this.getDisplacementDistanceForState(this.mCurrentState) <= this.mDisplacementFromRestThreshold || this.mSpringConfig.tension == 0.0D);
   }

   public boolean isOvershootClampingEnabled() {
      return this.mOvershootClampingEnabled;
   }

   public boolean isOvershooting() {
      return this.mSpringConfig.tension > 0.0D && (this.mStartValue < this.mEndValue && this.getCurrentValue() > this.mEndValue || this.mStartValue > this.mEndValue && this.getCurrentValue() < this.mEndValue);
   }

   public Spring removeAllListeners() {
      this.mListeners.clear();
      return this;
   }

   public Spring removeListener(SpringListener var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("listenerToRemove is required");
      } else {
         this.mListeners.remove(var1);
         return this;
      }
   }

   public Spring setAtRest() {
      this.mEndValue = this.mCurrentState.position;
      this.mTempState.position = this.mCurrentState.position;
      this.mCurrentState.velocity = 0.0D;
      return this;
   }

   public Spring setCurrentValue(double var1) {
      return this.setCurrentValue(var1, true);
   }

   public Spring setCurrentValue(double var1, boolean var3) {
      this.mStartValue = var1;
      this.mCurrentState.position = var1;
      Iterator var4 = this.mListeners.iterator();

      while(var4.hasNext()) {
         ((SpringListener)var4.next()).onSpringUpdate(this);
      }

      if(var3) {
         this.setAtRest();
      }

      return this;
   }

   public Spring setEndValue(double var1) {
      if(this.mEndValue == var1 && this.isAtRest()) {
         return this;
      } else {
         this.mStartValue = this.getCurrentValue();
         this.mEndValue = var1;
         Iterator var3 = this.mListeners.iterator();

         while(var3.hasNext()) {
            ((SpringListener)var3.next()).onSpringEndStateChange(this);
         }

         return this;
      }
   }

   public Spring setOvershootClampingEnabled(boolean var1) {
      this.mOvershootClampingEnabled = var1;
      return this;
   }

   public Spring setRestDisplacementThreshold(double var1) {
      this.mDisplacementFromRestThreshold = var1;
      return this;
   }

   public Spring setRestSpeedThreshold(double var1) {
      this.mRestSpeedThreshold = var1;
      return this;
   }

   public Spring setSpringConfig(SpringConfig var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("springConfig is required");
      } else {
         this.mSpringConfig = var1;
         return this;
      }
   }

   public Spring setVelocity(double var1) {
      if(var1 == this.mCurrentState.velocity) {
         return this;
      } else {
         this.mCurrentState.velocity = var1;
         return this;
      }
   }

   public boolean systemShouldAdvance() {
      return !this.isAtRest() || !this.wasAtRest();
   }

   public boolean wasAtRest() {
      return this.mWasAtRest;
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
