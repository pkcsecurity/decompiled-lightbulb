package com.facebook.litho.animation;

import com.facebook.litho.animation.PropertyAnimation;
import com.facebook.litho.animation.Resolver;
import com.facebook.litho.animation.TransitionAnimationBinding;
import com.facebook.litho.dataflow.ConstantNode;
import com.facebook.litho.dataflow.SpringNode;
import com.facebook.litho.dataflow.ValueNode;
import java.util.ArrayList;

public class BezierTransition extends TransitionAnimationBinding {

   private final float mControlX;
   private final float mControlY;
   private final PropertyAnimation mXPropertyAnimation;
   private final PropertyAnimation mYPropertyAnimation;


   public BezierTransition(PropertyAnimation var1, PropertyAnimation var2, float var3, float var4) {
      this.mXPropertyAnimation = var1;
      this.mYPropertyAnimation = var2;
      this.mControlX = var3;
      this.mControlY = var4;
   }

   public void collectTransitioningProperties(ArrayList<PropertyAnimation> var1) {
      var1.add(this.mXPropertyAnimation);
      var1.add(this.mYPropertyAnimation);
   }

   protected void setupBinding(Resolver var1) {
      float var2 = var1.getCurrentState(this.mXPropertyAnimation.getPropertyHandle());
      float var3 = this.mXPropertyAnimation.getTargetValue();
      float var4 = var1.getCurrentState(this.mYPropertyAnimation.getPropertyHandle());
      float var5 = this.mYPropertyAnimation.getTargetValue();
      float var6 = this.mControlX;
      float var7 = this.mControlY;
      SpringNode var8 = new SpringNode();
      BezierTransition.BezierNode var9 = new BezierTransition.BezierNode(var2, var3, (var3 - var2) * var6 + var2);
      BezierTransition.BezierNode var10 = new BezierTransition.BezierNode(var4, var5, (var5 - var4) * var7 + var4);
      this.addBinding(new ConstantNode(0.0F), var8, "initial");
      this.addBinding(new ConstantNode(1.0F), var8, "end");
      this.addBinding(var8, var9);
      this.addBinding(var8, var10);
      this.addBinding(var9, var1.getAnimatedPropertyNode(this.mXPropertyAnimation.getPropertyHandle()));
      this.addBinding(var10, var1.getAnimatedPropertyNode(this.mYPropertyAnimation.getPropertyHandle()));
   }

   static class BezierNode extends ValueNode {

      private final float mControlPoint;
      private final float mEnd;
      private final float mInitial;


      public BezierNode(float var1, float var2, float var3) {
         this.mInitial = var1;
         this.mEnd = var2;
         this.mControlPoint = var3;
      }

      protected float calculateValue(long var1) {
         float var3 = this.getInput().getValue();
         float var4 = 1.0F - var3;
         return var4 * var4 * this.mInitial + 2.0F * var3 * var4 * this.mControlPoint + var3 * var3 * this.mEnd;
      }
   }
}
