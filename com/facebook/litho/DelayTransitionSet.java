package com.facebook.litho;

import com.facebook.litho.Transition;
import com.facebook.litho.TransitionSet;
import com.facebook.litho.animation.AnimationBinding;
import com.facebook.litho.animation.DelayBinding;
import java.util.List;

public class DelayTransitionSet extends TransitionSet {

   private final int mDelayMs;


   public <T extends Transition> DelayTransitionSet(int var1, T var2) {
      super(new Transition[]{var2});
      this.mDelayMs = var1;
   }

   AnimationBinding createAnimation(List<AnimationBinding> var1) {
      if(var1.size() != 1) {
         StringBuilder var2 = new StringBuilder();
         var2.append("DelayTransitionSet is expected to have exactly one child, provided=");
         var2.append(var1);
         throw new IllegalArgumentException(var2.toString());
      } else {
         return new DelayBinding(this.mDelayMs, (AnimationBinding)var1.get(0));
      }
   }
}
