package com.facebook.litho;

import android.animation.TimeInterpolator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import com.facebook.infer.annotation.ThreadSafe;
import com.facebook.litho.AnimatableItem;
import com.facebook.litho.DelayTransitionSet;
import com.facebook.litho.LayoutOutput;
import com.facebook.litho.LayoutState;
import com.facebook.litho.ParallelTransitionSet;
import com.facebook.litho.SequenceTransitionSet;
import com.facebook.litho.TransitionSet;
import com.facebook.litho.animation.AnimatedProperties;
import com.facebook.litho.animation.AnimatedProperty;
import com.facebook.litho.animation.AnimatedPropertyNode;
import com.facebook.litho.animation.AnimationBinding;
import com.facebook.litho.animation.FloatValue;
import com.facebook.litho.animation.PropertyAnimation;
import com.facebook.litho.animation.PropertyHandle;
import com.facebook.litho.animation.RenderThreadTransition;
import com.facebook.litho.animation.Resolver;
import com.facebook.litho.animation.RuntimeValue;
import com.facebook.litho.animation.SpringTransition;
import com.facebook.litho.animation.TimingTransition;
import com.facebook.litho.animation.TransitionAnimationBinding;
import com.facebook.litho.dataflow.springs.SpringConfig;
import java.util.ArrayList;
import javax.annotation.Nullable;

public abstract class Transition {

   private static final Transition.TransitionAnimator DEFAULT_ANIMATOR = SPRING_WITH_OVERSHOOT;
   private static final int DEFAULT_DURATION = 300;
   private static final Interpolator DEFAULT_INTERPOLATOR = new AccelerateDecelerateInterpolator();
   public static final Transition.TransitionAnimator SPRING_WITHOUT_OVERSHOOT = new Transition.SpringTransitionAnimator(SpringConfig.noOvershootConfig);
   public static final Transition.TransitionAnimator SPRING_WITH_OVERSHOOT = new Transition.SpringTransitionAnimator(SpringConfig.defaultConfig);


   public static Transition.AutoBoundsTransitionBuilder allLayout() {
      return new Transition.AutoBoundsTransitionBuilder();
   }

   private static <T extends Object> boolean arrayContains(T[] var0, T var1) {
      int var3 = var0.length;

      for(int var2 = 0; var2 < var3; ++var2) {
         if(var0[var2] == var1) {
            return true;
         }
      }

      return false;
   }

   public static Transition.TransitionUnitsBuilder create(Transition.ComponentTarget var0) {
      return new Transition.TransitionUnitsBuilder(var0.componentTargetType, var0.componentTargetExtraData);
   }

   public static Transition.TransitionUnitsBuilder create(String var0) {
      return new Transition.TransitionUnitsBuilder(Transition.ComponentTargetType.SINGLE, var0);
   }

   public static Transition.TransitionUnitsBuilder create(String ... var0) {
      return new Transition.TransitionUnitsBuilder(Transition.ComponentTargetType.SET, var0);
   }

   public static <T extends Transition> Transition delay(int var0, T var1) {
      return new DelayTransitionSet(var0, var1);
   }

   static float getRootAppearFromValue(Transition.TransitionUnit var0, LayoutState var1, AnimatedProperty var2) {
      Transition.RootItemResolver var3 = new Transition.RootItemResolver(var1, var2, null);
      String var4 = var1.getRootTransitionKey();
      return var0.getAppearFrom().resolve(var3, new PropertyHandle(var4, var2));
   }

   @ThreadSafe(
      enableChecks = false
   )
   public static <T extends Transition> TransitionSet parallel(T ... var0) {
      return new ParallelTransitionSet(var0);
   }

   public static Transition.TransitionAnimator renderThread() {
      return new Transition.RenderThreadAnimator(0, 300, DEFAULT_INTERPOLATOR);
   }

   public static Transition.TransitionAnimator renderThread(int var0) {
      return new Transition.RenderThreadAnimator(0, var0, DEFAULT_INTERPOLATOR);
   }

   public static Transition.TransitionAnimator renderThread(int var0, int var1) {
      return new Transition.RenderThreadAnimator(var0, var1, DEFAULT_INTERPOLATOR);
   }

   public static Transition.TransitionAnimator renderThread(int var0, int var1, Interpolator var2) {
      return new Transition.RenderThreadAnimator(var0, var1, var2);
   }

   public static Transition.TransitionAnimator renderThread(int var0, TimeInterpolator var1) {
      return new Transition.RenderThreadAnimator(0, var0, var1);
   }

   public static <T extends Transition> TransitionSet sequence(T ... var0) {
      return new SequenceTransitionSet(var0);
   }

   public static Transition.TransitionAnimator springWithConfig(double var0, double var2) {
      return new Transition.SpringTransitionAnimator(var0, var2);
   }

   public static <T extends Transition> TransitionSet stagger(int var0, T ... var1) {
      return new ParallelTransitionSet(var0, var1);
   }

   public static Transition.TransitionAnimator timing(int var0) {
      return new Transition.TimingTransitionAnimator(var0);
   }

   public static Transition.TransitionAnimator timing(int var0, Interpolator var1) {
      return new Transition.TimingTransitionAnimator(var0, var1);
   }

   public static class SpringTransitionAnimator implements Transition.TransitionAnimator {

      final SpringConfig mSpringConfig;


      public SpringTransitionAnimator(double var1, double var3) {
         this.mSpringConfig = new SpringConfig(var1, var3);
      }

      public SpringTransitionAnimator(SpringConfig var1) {
         this.mSpringConfig = var1;
      }

      public TransitionAnimationBinding createAnimation(PropertyAnimation var1) {
         return new SpringTransition(var1, this.mSpringConfig);
      }
   }

   static class RenderThreadAnimator implements Transition.TransitionAnimator {

      final int mDelayMs;
      final int mDurationMs;
      final TimeInterpolator mInterpolator;


      RenderThreadAnimator(int var1, int var2, TimeInterpolator var3) {
         this.mDelayMs = var1;
         this.mDurationMs = var2;
         this.mInterpolator = var3;
      }

      public TransitionAnimationBinding createAnimation(PropertyAnimation var1) {
         return new RenderThreadTransition(var1, this.mDelayMs, this.mDurationMs, this.mInterpolator);
      }
   }

   static class RootItemResolver implements Resolver {

      private final AnimatedProperty mAnimatedProperty;
      private final LayoutState mLayoutState;


      private RootItemResolver(LayoutState var1, AnimatedProperty var2) {
         this.mLayoutState = var1;
         this.mAnimatedProperty = var2;
      }

      // $FF: synthetic method
      RootItemResolver(LayoutState var1, AnimatedProperty var2, Object var3) {
         this(var1, var2);
      }

      public AnimatedPropertyNode getAnimatedPropertyNode(PropertyHandle var1) {
         throw new UnsupportedOperationException();
      }

      public float getCurrentState(PropertyHandle var1) {
         LayoutOutput var2 = this.mLayoutState.getMountableOutputAt(0);
         return this.mAnimatedProperty.get((AnimatableItem)var2);
      }
   }

   public abstract static class BaseTransitionUnitsBuilder extends Transition {

      RuntimeValue mAppearFrom;
      ArrayList<Transition.TransitionUnit> mBuiltTransitions = new ArrayList();
      Transition.ComponentTarget mComponentTarget;
      RuntimeValue mDisappearTo;
      Transition.PropertyTarget mPropertyTarget;
      String mTraceName;
      Transition.TransitionAnimator mTransitionAnimator;


      public BaseTransitionUnitsBuilder() {
         this.mTransitionAnimator = Transition.DEFAULT_ANIMATOR;
      }

      ArrayList<Transition.TransitionUnit> getTransitionUnits() {
         this.maybeCommitCurrentBuilder();
         return this.mBuiltTransitions;
      }

      void maybeCommitCurrentBuilder() {
         if(this.mPropertyTarget != null) {
            this.mBuiltTransitions.add(new Transition.TransitionUnit(new Transition.AnimationTarget(this.mComponentTarget, this.mPropertyTarget), this.mTransitionAnimator, this.mAppearFrom, this.mDisappearTo, this.mTraceName));
            this.mPropertyTarget = null;
            this.mTransitionAnimator = Transition.DEFAULT_ANIMATOR;
            this.mAppearFrom = null;
            this.mDisappearTo = null;
            this.mTraceName = null;
         }
      }
   }

   public static class ComponentTarget {

      public final Object componentTargetExtraData;
      public final Transition.ComponentTargetType componentTargetType;


      ComponentTarget(Transition.ComponentTargetType var1, Object var2) {
         this.componentTargetType = var1;
         this.componentTargetExtraData = var2;
      }
   }

   public static class PropertyTarget {

      public final Object propertyTargetExtraData;
      public final Transition.PropertyTargetType propertyTargetType;


      PropertyTarget(Transition.PropertyTargetType var1, Object var2) {
         this.propertyTargetType = var1;
         this.propertyTargetExtraData = var2;
      }
   }

   static enum PropertyTargetType {

      // $FF: synthetic field
      private static final Transition.PropertyTargetType[] $VALUES = new Transition.PropertyTargetType[]{SET, SINGLE, AUTO_LAYOUT};
      AUTO_LAYOUT("AUTO_LAYOUT", 2),
      SET("SET", 0),
      SINGLE("SINGLE", 1);


      private PropertyTargetType(String var1, int var2) {}
   }

   public interface TransitionAnimator {

      TransitionAnimationBinding createAnimation(PropertyAnimation var1);
   }

   public static class TransitionUnit extends Transition {

      private final Transition.AnimationTarget mAnimationTarget;
      private final RuntimeValue mAppearFrom;
      private final RuntimeValue mDisappearTo;
      @Nullable
      private final String mTraceName;
      private final Transition.TransitionAnimator mTransitionAnimator;


      TransitionUnit(Transition.AnimationTarget var1, Transition.TransitionAnimator var2, RuntimeValue var3, RuntimeValue var4, @Nullable String var5) {
         this.mAnimationTarget = var1;
         this.mTransitionAnimator = var2;
         this.mAppearFrom = var3;
         this.mDisappearTo = var4;
         this.mTraceName = var5;
      }

      AnimationBinding createAnimation(PropertyHandle var1, float var2) {
         PropertyAnimation var3 = new PropertyAnimation(var1, var2);
         return this.mTransitionAnimator.createAnimation(var3);
      }

      Transition.AnimationTarget getAnimationTarget() {
         return this.mAnimationTarget;
      }

      RuntimeValue getAppearFrom() {
         return this.mAppearFrom;
      }

      RuntimeValue getDisappearTo() {
         return this.mDisappearTo;
      }

      @Nullable
      String getTraceName() {
         return this.mTraceName;
      }

      boolean hasAppearAnimation() {
         return this.mAppearFrom != null;
      }

      boolean hasDisappearAnimation() {
         return this.mDisappearTo != null;
      }

      boolean targetsKey(String var1) {
         switch(null.$SwitchMap$com$facebook$litho$Transition$ComponentTargetType[this.mAnimationTarget.componentTarget.componentTargetType.ordinal()]) {
         case 1:
         case 2:
            return true;
         case 3:
            return Transition.arrayContains((String[])this.mAnimationTarget.componentTarget.componentTargetExtraData, var1);
         case 4:
            return var1.equals(this.mAnimationTarget.componentTarget.componentTargetExtraData);
         default:
            StringBuilder var2 = new StringBuilder();
            var2.append("Didn\'t handle type: ");
            var2.append(this.mAnimationTarget.componentTarget.componentTargetType);
            throw new RuntimeException(var2.toString());
         }
      }

      boolean targetsProperty(AnimatedProperty var1) {
         switch(null.$SwitchMap$com$facebook$litho$Transition$PropertyTargetType[this.mAnimationTarget.propertyTarget.propertyTargetType.ordinal()]) {
         case 1:
            return Transition.arrayContains(AnimatedProperties.AUTO_LAYOUT_PROPERTIES, var1);
         case 2:
            return Transition.arrayContains((AnimatedProperty[])this.mAnimationTarget.propertyTarget.propertyTargetExtraData, var1);
         case 3:
            return var1.equals(this.mAnimationTarget.propertyTarget.propertyTargetExtraData);
         default:
            StringBuilder var2 = new StringBuilder();
            var2.append("Didn\'t handle type: ");
            var2.append(this.mAnimationTarget.propertyTarget.propertyTargetExtraData);
            throw new RuntimeException(var2.toString());
         }
      }
   }

   public static class AnimationTarget {

      public final Transition.ComponentTarget componentTarget;
      public final Transition.PropertyTarget propertyTarget;


      AnimationTarget(Transition.ComponentTarget var1, Transition.PropertyTarget var2) {
         this.componentTarget = var1;
         this.propertyTarget = var2;
      }
   }

   static class RootBoundsTransition {

      Transition.TransitionUnit appearTransition;
      boolean hasTransition;


   }

   static enum ComponentTargetType {

      // $FF: synthetic field
      private static final Transition.ComponentTargetType[] $VALUES = new Transition.ComponentTargetType[]{ALL, SET, SINGLE, AUTO_LAYOUT};
      ALL("ALL", 0),
      AUTO_LAYOUT("AUTO_LAYOUT", 3),
      SET("SET", 1),
      SINGLE("SINGLE", 2);


      private ComponentTargetType(String var1, int var2) {}
   }

   public static class TimingTransitionAnimator implements Transition.TransitionAnimator {

      final int mDurationMs;
      final Interpolator mInterpolator;


      public TimingTransitionAnimator(int var1) {
         this(var1, Transition.DEFAULT_INTERPOLATOR);
      }

      public TimingTransitionAnimator(int var1, Interpolator var2) {
         this.mDurationMs = var1;
         this.mInterpolator = var2;
      }

      public TransitionAnimationBinding createAnimation(PropertyAnimation var1) {
         return new TimingTransition(this.mDurationMs, var1, this.mInterpolator);
      }
   }

   public static class TransitionUnitsBuilder extends Transition.BaseTransitionUnitsBuilder {

      TransitionUnitsBuilder(Transition.ComponentTarget var1) {
         this.mComponentTarget = var1;
      }

      TransitionUnitsBuilder(Transition.ComponentTargetType var1, Object var2) {
         this.mComponentTarget = new Transition.ComponentTarget(var1, var2);
      }

      public Transition.TransitionUnitsBuilder animate(Transition.PropertyTarget var1) {
         this.maybeCommitCurrentBuilder();
         this.mPropertyTarget = var1;
         return this;
      }

      public Transition.TransitionUnitsBuilder animate(AnimatedProperty var1) {
         this.maybeCommitCurrentBuilder();
         this.mPropertyTarget = new Transition.PropertyTarget(Transition.PropertyTargetType.SINGLE, var1);
         return this;
      }

      public Transition.TransitionUnitsBuilder animate(AnimatedProperty ... var1) {
         this.maybeCommitCurrentBuilder();
         this.mPropertyTarget = new Transition.PropertyTarget(Transition.PropertyTargetType.SET, var1);
         return this;
      }

      public Transition.TransitionUnitsBuilder animator(Transition.TransitionAnimator var1) {
         this.mTransitionAnimator = var1;
         return this;
      }

      public Transition.TransitionUnitsBuilder appearFrom(float var1) {
         return this.appearFrom(new FloatValue(var1));
      }

      public Transition.TransitionUnitsBuilder appearFrom(RuntimeValue var1) {
         if(this.mPropertyTarget != null && this.mPropertyTarget.propertyTargetType == Transition.PropertyTargetType.SINGLE) {
            this.mAppearFrom = var1;
            return this;
         } else {
            throw new RuntimeException("Must specify a single property using #animate() before specifying an appearFrom value!");
         }
      }

      public Transition.TransitionUnitsBuilder disappearTo(float var1) {
         return this.disappearTo(new FloatValue(var1));
      }

      public Transition.TransitionUnitsBuilder disappearTo(RuntimeValue var1) {
         if(this.mPropertyTarget != null && this.mPropertyTarget.propertyTargetType == Transition.PropertyTargetType.SINGLE) {
            this.mDisappearTo = var1;
            return this;
         } else {
            throw new RuntimeException("Must specify a single property using #animate() before specifying an disappearTo value!");
         }
      }

      public Transition.TransitionUnitsBuilder traceName(String var1) {
         this.mTraceName = var1;
         return this;
      }
   }

   public static class AutoBoundsTransitionBuilder extends Transition.BaseTransitionUnitsBuilder {

      AutoBoundsTransitionBuilder() {
         this.mComponentTarget = new Transition.ComponentTarget(Transition.ComponentTargetType.AUTO_LAYOUT, (Object)null);
         this.mPropertyTarget = new Transition.PropertyTarget(Transition.PropertyTargetType.AUTO_LAYOUT, (Object)null);
      }

      public Transition.AutoBoundsTransitionBuilder animator(Transition.TransitionAnimator var1) {
         this.mTransitionAnimator = var1;
         return this;
      }
   }
}
