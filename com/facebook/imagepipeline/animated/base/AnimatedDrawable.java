package com.facebook.imagepipeline.animated.base;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.view.animation.LinearInterpolator;
import com.facebook.common.time.MonotonicClock;
import com.facebook.imagepipeline.animated.base.AbstractAnimatedDrawable;
import com.facebook.imagepipeline.animated.base.AnimatableDrawable;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableCachingBackend;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableDiagnostics;
import java.util.concurrent.ScheduledExecutorService;

@TargetApi(11)
public class AnimatedDrawable extends AbstractAnimatedDrawable implements AnimatableDrawable {

   public AnimatedDrawable(ScheduledExecutorService var1, AnimatedDrawableCachingBackend var2, AnimatedDrawableDiagnostics var3, MonotonicClock var4) {
      super(var1, var2, var3, var4);
   }

   public AnimatorUpdateListener createAnimatorUpdateListener() {
      return new AnimatorUpdateListener() {
         public void onAnimationUpdate(ValueAnimator var1) {
            AnimatedDrawable.this.setLevel(((Integer)var1.getAnimatedValue()).intValue());
         }
      };
   }

   public ValueAnimator createValueAnimator() {
      int var1 = this.getAnimatedDrawableBackend().getLoopCount();
      ValueAnimator var2 = new ValueAnimator();
      var2.setIntValues(new int[]{0, this.getDuration()});
      var2.setDuration((long)this.getDuration());
      if(var1 == 0) {
         var1 = -1;
      }

      var2.setRepeatCount(var1);
      var2.setRepeatMode(1);
      var2.setInterpolator(new LinearInterpolator());
      var2.addUpdateListener(this.createAnimatorUpdateListener());
      return var2;
   }

   public ValueAnimator createValueAnimator(int var1) {
      ValueAnimator var2 = this.createValueAnimator();
      var2.setRepeatCount(Math.max(var1 / this.getAnimatedDrawableBackend().getDurationMs(), 1));
      return var2;
   }
}
