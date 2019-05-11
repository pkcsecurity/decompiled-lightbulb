package com.facebook.litho;

import com.facebook.litho.Transition;
import com.facebook.litho.TransitionSet;
import com.facebook.litho.animation.AnimationBinding;
import com.facebook.litho.animation.SequenceBinding;
import java.util.List;

public class SequenceTransitionSet extends TransitionSet {

   public <T extends Transition> SequenceTransitionSet(List<T> var1) {
      super(var1);
   }

   public <T extends Transition> SequenceTransitionSet(T ... var1) {
      super(var1);
   }

   AnimationBinding createAnimation(List<AnimationBinding> var1) {
      return new SequenceBinding(var1);
   }
}
