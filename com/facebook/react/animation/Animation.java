package com.facebook.react.animation;

import android.view.View;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.animation.AnimationListener;
import com.facebook.react.animation.AnimationPropertyUpdater;
import javax.annotation.Nullable;

public abstract class Animation {

   @Nullable
   private View mAnimatedView;
   private final int mAnimationID;
   @Nullable
   private AnimationListener mAnimationListener;
   private volatile boolean mCancelled = false;
   private volatile boolean mIsFinished = false;
   private final AnimationPropertyUpdater mPropertyUpdater;


   public Animation(int var1, AnimationPropertyUpdater var2) {
      this.mAnimationID = var1;
      this.mPropertyUpdater = var2;
   }

   public final void cancel() {
      if(!this.mIsFinished) {
         if(!this.mCancelled) {
            this.mCancelled = true;
            if(this.mAnimationListener != null) {
               this.mAnimationListener.onCancel();
            }

         }
      }
   }

   protected final void finish() {
      Assertions.assertCondition(this.mIsFinished ^ true, "Animation must not already be finished!");
      this.mIsFinished = true;
      if(!this.mCancelled) {
         if(this.mAnimatedView != null) {
            this.mPropertyUpdater.onFinish(this.mAnimatedView);
         }

         if(this.mAnimationListener != null) {
            this.mAnimationListener.onFinished();
         }
      }

   }

   public int getAnimationID() {
      return this.mAnimationID;
   }

   protected final boolean onUpdate(float var1) {
      Assertions.assertCondition(this.mIsFinished ^ true, "Animation must not already be finished!");
      if(!this.mCancelled) {
         this.mPropertyUpdater.onUpdate((View)Assertions.assertNotNull(this.mAnimatedView), var1);
      }

      return this.mCancelled ^ true;
   }

   public abstract void run();

   public void setAnimationListener(AnimationListener var1) {
      this.mAnimationListener = var1;
   }

   public final void start(View var1) {
      this.mAnimatedView = var1;
      this.mPropertyUpdater.prepare(var1);
      this.run();
   }
}
