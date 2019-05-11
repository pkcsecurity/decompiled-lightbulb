package com.facebook.litho;

import com.facebook.litho.Transition;
import com.facebook.litho.TransitionSet;
import com.facebook.litho.animation.AnimationBinding;
import com.facebook.litho.animation.ParallelBinding;
import java.util.List;

public class ParallelTransitionSet extends TransitionSet {

   private final int mStaggerMs;


   public <T extends Transition> ParallelTransitionSet(int var1, List<T> var2) {
      super(var2);
      this.mStaggerMs = var1;
   }

   public <T extends Transition> ParallelTransitionSet(int var1, T ... var2) {
      super(var2);
      this.mStaggerMs = var1;
   }

   public <T extends Transition> ParallelTransitionSet(List<T> var1) {
      super(var1);
      this.mStaggerMs = 0;
   }

   public <T extends Transition> ParallelTransitionSet(T ... var1) {
      super(var1);
      this.mStaggerMs = 0;
   }

   AnimationBinding createAnimation(List<AnimationBinding> var1) {
      return new ParallelBinding(this.mStaggerMs, var1);
   }
}
