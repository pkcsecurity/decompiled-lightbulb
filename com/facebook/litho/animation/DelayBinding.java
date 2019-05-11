package com.facebook.litho.animation;

import com.facebook.litho.animation.AnimationBinding;
import com.facebook.litho.animation.AnimationBindingListener;
import com.facebook.litho.animation.BaseAnimationBinding;
import com.facebook.litho.animation.PropertyAnimation;
import com.facebook.litho.animation.Resolver;
import com.facebook.litho.dataflow.ChoreographerCompat;
import com.facebook.litho.dataflow.ChoreographerCompatImpl;
import java.util.ArrayList;

public class DelayBinding extends BaseAnimationBinding {

   private final AnimationBinding mBinding;
   private final int mDelayMs;
   private boolean mHasStarted = false;
   private boolean mIsActive = false;
   private Resolver mResolver;


   public DelayBinding(int var1, AnimationBinding var2) {
      this.mDelayMs = var1;
      this.mBinding = var2;
   }

   private void finish() {
      this.mIsActive = false;
      this.notifyFinished();
   }

   public void collectTransitioningProperties(ArrayList<PropertyAnimation> var1) {
      this.mBinding.collectTransitioningProperties(var1);
   }

   public boolean isActive() {
      return this.mIsActive;
   }

   public void prepareToStartLater() {
      this.notifyScheduledToStartLater();
      this.mBinding.prepareToStartLater();
   }

   public void start(Resolver var1) {
      if(this.mHasStarted) {
         throw new RuntimeException("Starting binding multiple times");
      } else {
         this.mHasStarted = true;
         this.mResolver = var1;
         if(!this.shouldStart()) {
            this.notifyCanceledBeforeStart();
         } else {
            this.notifyWillStart();
            this.mIsActive = true;
            this.mBinding.prepareToStartLater();
            AnimationBindingListener var2 = new AnimationBindingListener() {
               public void onCanceledBeforeStart(AnimationBinding var1) {
                  this.onFinish(var1);
               }
               public void onFinish(AnimationBinding var1) {
                  var1.removeListener(this);
                  DelayBinding.this.finish();
               }
               public void onScheduledToStartLater(AnimationBinding var1) {}
               public void onWillStart(AnimationBinding var1) {}
               public boolean shouldStart(AnimationBinding var1) {
                  return true;
               }
            };
            this.mBinding.addListener(var2);
            ChoreographerCompat.FrameCallback var3 = new ChoreographerCompat.FrameCallback() {
               public void doFrame(long var1) {
                  if(DelayBinding.this.mIsActive) {
                     DelayBinding.this.mBinding.start(DelayBinding.this.mResolver);
                  }
               }
            };
            ChoreographerCompatImpl.getInstance().postFrameCallbackDelayed(var3, (long)this.mDelayMs);
         }
      }
   }

   public void stop() {
      if(this.mIsActive) {
         this.mIsActive = false;
         this.mResolver = null;
         if(this.mBinding.isActive()) {
            this.mBinding.stop();
         }

      }
   }
}
