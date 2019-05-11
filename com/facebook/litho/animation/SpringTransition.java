package com.facebook.litho.animation;

import com.facebook.litho.animation.PropertyAnimation;
import com.facebook.litho.animation.Resolver;
import com.facebook.litho.animation.TransitionAnimationBinding;
import com.facebook.litho.dataflow.ConstantNode;
import com.facebook.litho.dataflow.SpringNode;
import com.facebook.litho.dataflow.springs.SpringConfig;
import java.util.ArrayList;
import javax.annotation.Nullable;

public class SpringTransition extends TransitionAnimationBinding {

   private final PropertyAnimation mPropertyAnimation;
   @Nullable
   private final SpringConfig mSpringConfig;


   public SpringTransition(PropertyAnimation var1) {
      this(var1, (SpringConfig)null);
   }

   public SpringTransition(PropertyAnimation var1, SpringConfig var2) {
      this.mPropertyAnimation = var1;
      this.mSpringConfig = var2;
   }

   public void collectTransitioningProperties(ArrayList<PropertyAnimation> var1) {
      var1.add(this.mPropertyAnimation);
   }

   protected void setupBinding(Resolver var1) {
      SpringNode var2 = new SpringNode(this.mSpringConfig);
      ConstantNode var3 = new ConstantNode(var1.getCurrentState(this.mPropertyAnimation.getPropertyHandle()));
      ConstantNode var4 = new ConstantNode(this.mPropertyAnimation.getTargetValue());
      this.addBinding(var3, var2, "initial");
      this.addBinding(var4, var2, "end");
      this.addBinding(var2, var1.getAnimatedPropertyNode(this.mPropertyAnimation.getPropertyHandle()));
   }
}
