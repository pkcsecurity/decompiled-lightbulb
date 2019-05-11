package com.facebook.litho.animation;

import com.facebook.litho.animation.AnimationBinding;
import com.facebook.litho.animation.AnimationBindingListener;
import com.facebook.litho.animation.BaseAnimationBinding;
import com.facebook.litho.animation.PropertyAnimation;
import com.facebook.litho.animation.Resolver;
import java.util.ArrayList;
import java.util.List;

public class SequenceBinding extends BaseAnimationBinding {

   private final List<AnimationBinding> mBindings;
   private final AnimationBindingListener mChildListener;
   private int mCurrentIndex = 0;
   private boolean mIsActive = false;
   private Resolver mResolver;


   public SequenceBinding(List<AnimationBinding> var1) {
      this.mBindings = var1;
      if(this.mBindings.isEmpty()) {
         throw new IllegalArgumentException("Empty binding sequence");
      } else {
         this.mChildListener = new AnimationBindingListener() {
            public void onCanceledBeforeStart(AnimationBinding var1) {
               SequenceBinding.this.onBindingFinished(var1);
            }
            public void onFinish(AnimationBinding var1) {
               SequenceBinding.this.onBindingFinished(var1);
            }
            public void onScheduledToStartLater(AnimationBinding var1) {}
            public void onWillStart(AnimationBinding var1) {}
            public boolean shouldStart(AnimationBinding var1) {
               return true;
            }
         };
      }
   }

   private void finish() {
      this.notifyFinished();
      this.mIsActive = false;
      this.mResolver = null;
   }

   private void onBindingFinished(AnimationBinding var1) {
      if(var1 != this.mBindings.get(this.mCurrentIndex)) {
         throw new RuntimeException("Unexpected Binding completed");
      } else {
         var1.removeListener(this.mChildListener);
         ++this.mCurrentIndex;
         if(this.mCurrentIndex >= this.mBindings.size()) {
            this.finish();
         } else {
            var1 = (AnimationBinding)this.mBindings.get(this.mCurrentIndex);
            var1.addListener(this.mChildListener);
            var1.start(this.mResolver);
         }
      }
   }

   public void collectTransitioningProperties(ArrayList<PropertyAnimation> var1) {
      int var3 = this.mBindings.size();

      for(int var2 = 0; var2 < var3; ++var2) {
         ((AnimationBinding)this.mBindings.get(var2)).collectTransitioningProperties(var1);
      }

   }

   public boolean isActive() {
      return this.mIsActive;
   }

   public void prepareToStartLater() {
      this.notifyScheduledToStartLater();
      int var2 = this.mBindings.size();

      for(int var1 = 0; var1 < var2; ++var1) {
         ((AnimationBinding)this.mBindings.get(var1)).prepareToStartLater();
      }

   }

   public void start(Resolver var1) {
      if(this.mIsActive) {
         throw new RuntimeException("Already started");
      } else if(!this.shouldStart()) {
         this.notifyCanceledBeforeStart();
      } else {
         this.notifyWillStart();
         int var3 = this.mBindings.size();

         for(int var2 = 1; var2 < var3; ++var2) {
            ((AnimationBinding)this.mBindings.get(var2)).prepareToStartLater();
         }

         this.mIsActive = true;
         this.mResolver = var1;
         AnimationBinding var4 = (AnimationBinding)this.mBindings.get(0);
         var4.addListener(this.mChildListener);
         var4.start(this.mResolver);
      }
   }

   public void stop() {
      if(this.mIsActive) {
         this.mIsActive = false;
         ((AnimationBinding)this.mBindings.get(this.mCurrentIndex)).stop();
      }
   }
}
