package com.facebook.litho;

import com.facebook.litho.ParallelTransitionSet;
import com.facebook.litho.Transition;
import com.facebook.litho.animation.AnimationBinding;
import java.util.ArrayList;
import java.util.List;

public abstract class TransitionSet extends Transition {

   private final ArrayList<Transition> mChildren = new ArrayList();


   <T extends Transition> TransitionSet(List<T> var1) {
      for(int var2 = 0; var2 < var1.size(); ++var2) {
         this.addChild((Transition)var1.get(var2));
      }

   }

   <T extends Transition> TransitionSet(T ... var1) {
      for(int var2 = 0; var2 < var1.length; ++var2) {
         this.addChild(var1[var2]);
      }

   }

   private void addChild(Transition var1) {
      if(var1 instanceof Transition.BaseTransitionUnitsBuilder) {
         ArrayList var2 = ((Transition.BaseTransitionUnitsBuilder)var1).getTransitionUnits();
         if(var2.size() > 1) {
            this.mChildren.add(new ParallelTransitionSet(var2));
         } else {
            this.mChildren.add(var2.get(0));
         }
      } else if(var1 != null) {
         this.mChildren.add(var1);
      } else {
         throw new IllegalStateException("Null element is not allowed in transition set");
      }
   }

   abstract AnimationBinding createAnimation(List<AnimationBinding> var1);

   ArrayList<Transition> getChildren() {
      return this.mChildren;
   }
}
