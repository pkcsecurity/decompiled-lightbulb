package com.facebook.litho.animation;

import com.facebook.litho.animation.AnimationBinding;
import com.facebook.litho.animation.AnimationBindingListener;
import com.facebook.litho.animation.BaseAnimationBinding;
import com.facebook.litho.animation.PropertyAnimation;
import com.facebook.litho.animation.Resolver;
import com.facebook.litho.dataflow.ChoreographerCompat;
import com.facebook.litho.dataflow.ChoreographerCompatImpl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class ParallelBinding extends BaseAnimationBinding {

   private final List<AnimationBinding> mBindings;
   private final HashSet<AnimationBinding> mBindingsFinished = new HashSet();
   private final AnimationBindingListener mChildListener;
   private int mChildrenFinished = 0;
   private boolean mHasStarted = false;
   private boolean mIsActive = false;
   private int mNextIndexToStart = 0;
   private Resolver mResolver;
   private final ChoreographerCompat.FrameCallback mStaggerCallback;
   private final int mStaggerMs;


   public ParallelBinding(int var1, List<AnimationBinding> var2) {
      this.mStaggerMs = var1;
      this.mBindings = var2;
      if(this.mBindings.isEmpty()) {
         throw new IllegalArgumentException("Empty binding parallel");
      } else {
         this.mChildListener = new AnimationBindingListener() {
            public void onCanceledBeforeStart(AnimationBinding var1) {
               ParallelBinding.this.onBindingFinished(var1);
            }
            public void onFinish(AnimationBinding var1) {
               ParallelBinding.this.onBindingFinished(var1);
            }
            public void onScheduledToStartLater(AnimationBinding var1) {}
            public void onWillStart(AnimationBinding var1) {}
            public boolean shouldStart(AnimationBinding var1) {
               return true;
            }
         };
         if(this.mStaggerMs == 0) {
            this.mStaggerCallback = null;
         } else {
            this.mStaggerCallback = new ChoreographerCompat.FrameCallback() {
               public void doFrame(long var1) {
                  if(ParallelBinding.this.mIsActive) {
                     ParallelBinding.this.startNextBindingForStagger();
                  }
               }
            };
         }
      }
   }

   private void finish() {
      this.mIsActive = false;
      this.notifyFinished();
   }

   private void onBindingFinished(AnimationBinding var1) {
      if(this.mBindingsFinished.contains(var1)) {
         throw new RuntimeException("Binding unexpectedly completed twice");
      } else {
         this.mBindingsFinished.add(var1);
         ++this.mChildrenFinished;
         var1.removeListener(this.mChildListener);
         if(this.mChildrenFinished >= this.mBindings.size()) {
            this.finish();
         }

      }
   }

   private void startNextBindingForStagger() {
      ((AnimationBinding)this.mBindings.get(this.mNextIndexToStart)).start(this.mResolver);
      ++this.mNextIndexToStart;
      if(this.mNextIndexToStart < this.mBindings.size()) {
         ChoreographerCompatImpl.getInstance().postFrameCallbackDelayed(this.mStaggerCallback, (long)this.mStaggerMs);
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
      if(this.mHasStarted) {
         throw new RuntimeException("Starting binding multiple times");
      } else {
         int var2 = 1;
         this.mHasStarted = true;
         this.mResolver = var1;
         if(!this.shouldStart()) {
            this.notifyCanceledBeforeStart();
         } else {
            this.notifyWillStart();
            this.mIsActive = true;
            Iterator var4 = this.mBindings.iterator();

            while(var4.hasNext()) {
               ((AnimationBinding)var4.next()).addListener(this.mChildListener);
            }

            int var3;
            if(this.mStaggerMs == 0) {
               var2 = 0;

               for(var3 = this.mBindings.size(); var2 < var3; ++var2) {
                  ((AnimationBinding)this.mBindings.get(var2)).start(this.mResolver);
               }

               this.mNextIndexToStart = this.mBindings.size();
            } else {
               for(var3 = this.mBindings.size(); var2 < var3; ++var2) {
                  ((AnimationBinding)this.mBindings.get(var2)).prepareToStartLater();
               }

               this.startNextBindingForStagger();
            }
         }
      }
   }

   public void stop() {
      if(this.mIsActive) {
         int var1 = 0;
         this.mIsActive = false;
         this.mResolver = null;

         for(int var2 = this.mBindings.size(); var1 < var2; ++var1) {
            AnimationBinding var3 = (AnimationBinding)this.mBindings.get(var1);
            if(var3.isActive()) {
               var3.stop();
            }
         }

      }
   }
}
