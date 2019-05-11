package com.facebook.litho.animation;

import android.support.annotation.Nullable;
import android.view.animation.Interpolator;
import com.facebook.litho.animation.PropertyAnimation;
import com.facebook.litho.animation.Resolver;
import com.facebook.litho.animation.TransitionAnimationBinding;
import com.facebook.litho.dataflow.ConstantNode;
import com.facebook.litho.dataflow.InterpolatorNode;
import com.facebook.litho.dataflow.MappingNode;
import com.facebook.litho.dataflow.TimingNode;
import java.util.ArrayList;

public class TimingTransition extends TransitionAnimationBinding {

   private final int mDurationMs;
   @Nullable
   private final Interpolator mInterpolator;
   private final PropertyAnimation mPropertyAnimation;


   public TimingTransition(int var1, PropertyAnimation var2) {
      this(var1, var2, (Interpolator)null);
   }

   public TimingTransition(int var1, PropertyAnimation var2, Interpolator var3) {
      this.mDurationMs = var1;
      this.mPropertyAnimation = var2;
      this.mInterpolator = var3;
   }

   public void collectTransitioningProperties(ArrayList<PropertyAnimation> var1) {
      var1.add(this.mPropertyAnimation);
   }

   protected void setupBinding(Resolver var1) {
      TimingNode var2 = new TimingNode(this.mDurationMs);
      ConstantNode var3 = new ConstantNode(var1.getCurrentState(this.mPropertyAnimation.getPropertyHandle()));
      ConstantNode var4 = new ConstantNode(this.mPropertyAnimation.getTargetValue());
      MappingNode var5 = new MappingNode();
      if(this.mInterpolator != null) {
         InterpolatorNode var6 = new InterpolatorNode(this.mInterpolator);
         this.addBinding(var2, var6);
         this.addBinding(var6, var5);
      } else {
         this.addBinding(var2, var5);
      }

      this.addBinding(var3, var5, "initial");
      this.addBinding(var4, var5, "end");
      this.addBinding(var5, var1.getAnimatedPropertyNode(this.mPropertyAnimation.getPropertyHandle()));
   }
}
