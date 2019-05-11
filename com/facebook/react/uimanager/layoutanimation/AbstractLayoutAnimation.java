package com.facebook.react.uimanager.layoutanimation;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.IllegalViewOperationException;
import com.facebook.react.uimanager.layoutanimation.AnimatedPropertyType;
import com.facebook.react.uimanager.layoutanimation.InterpolatorType;
import com.facebook.react.uimanager.layoutanimation.SimpleSpringInterpolator;
import java.util.Map;
import javax.annotation.Nullable;

abstract class AbstractLayoutAnimation {

   private static final Map<InterpolatorType, Interpolator> INTERPOLATOR = MapBuilder.of(InterpolatorType.LINEAR, new LinearInterpolator(), InterpolatorType.EASE_IN, new AccelerateInterpolator(), InterpolatorType.EASE_OUT, new DecelerateInterpolator(), InterpolatorType.EASE_IN_EASE_OUT, new AccelerateDecelerateInterpolator(), InterpolatorType.SPRING, new SimpleSpringInterpolator());
   private static final boolean SLOWDOWN_ANIMATION_MODE = false;
   @Nullable
   protected AnimatedPropertyType mAnimatedProperty;
   private int mDelayMs;
   protected int mDurationMs;
   @Nullable
   private Interpolator mInterpolator;


   private static Interpolator getInterpolator(InterpolatorType var0) {
      Interpolator var1 = (Interpolator)INTERPOLATOR.get(var0);
      if(var1 == null) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Missing interpolator for type : ");
         var2.append(var0);
         throw new IllegalArgumentException(var2.toString());
      } else {
         return var1;
      }
   }

   @Nullable
   public final Animation createAnimation(View var1, int var2, int var3, int var4, int var5) {
      if(!this.isValid()) {
         return null;
      } else {
         Animation var6 = this.createAnimationImpl(var1, var2, var3, var4, var5);
         if(var6 != null) {
            var6.setDuration((long)(this.mDurationMs * 1));
            var6.setStartOffset((long)(this.mDelayMs * 1));
            var6.setInterpolator(this.mInterpolator);
         }

         return var6;
      }
   }

   @Nullable
   abstract Animation createAnimationImpl(View var1, int var2, int var3, int var4, int var5);

   public void initializeFromConfig(ReadableMap var1, int var2) {
      AnimatedPropertyType var3;
      if(var1.hasKey("property")) {
         var3 = AnimatedPropertyType.fromString(var1.getString("property"));
      } else {
         var3 = null;
      }

      this.mAnimatedProperty = var3;
      if(var1.hasKey("duration")) {
         var2 = var1.getInt("duration");
      }

      this.mDurationMs = var2;
      if(var1.hasKey("delay")) {
         var2 = var1.getInt("delay");
      } else {
         var2 = 0;
      }

      this.mDelayMs = var2;
      if(!var1.hasKey("type")) {
         throw new IllegalArgumentException("Missing interpolation type.");
      } else {
         this.mInterpolator = getInterpolator(InterpolatorType.fromString(var1.getString("type")));
         if(!this.isValid()) {
            StringBuilder var4 = new StringBuilder();
            var4.append("Invalid layout animation : ");
            var4.append(var1);
            throw new IllegalViewOperationException(var4.toString());
         }
      }
   }

   abstract boolean isValid();

   public void reset() {
      this.mAnimatedProperty = null;
      this.mDurationMs = 0;
      this.mDelayMs = 0;
      this.mInterpolator = null;
   }
}
