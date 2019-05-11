package com.facebook.litho;

import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewParent;
import com.facebook.litho.AnimatableItem;
import com.facebook.litho.ComponentHost;
import com.facebook.litho.ComponentsSystrace;
import com.facebook.litho.LayoutOutput;
import com.facebook.litho.LayoutState;
import com.facebook.litho.MountState;
import com.facebook.litho.OutputUnitsAffinityGroup;
import com.facebook.litho.ParallelTransitionSet;
import com.facebook.litho.Transition;
import com.facebook.litho.TransitionSet;
import com.facebook.litho.animation.AnimatedProperties;
import com.facebook.litho.animation.AnimatedProperty;
import com.facebook.litho.animation.AnimatedPropertyNode;
import com.facebook.litho.animation.AnimationBinding;
import com.facebook.litho.animation.AnimationBindingListener;
import com.facebook.litho.animation.ParallelBinding;
import com.facebook.litho.animation.PropertyAnimation;
import com.facebook.litho.animation.PropertyHandle;
import com.facebook.litho.animation.Resolver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TransitionManager {

   private final TransitionManager.TransitionsAnimationBindingListener mAnimationBindingListener = new TransitionManager.TransitionsAnimationBindingListener(null);
   private final Map<String, TransitionManager.AnimationState> mAnimationStates = new HashMap();
   private final Map<AnimationBinding, List<PropertyHandle>> mAnimationsToPropertyHandles = new HashMap();
   private final Map<PropertyHandle, Float> mInitialStatesToRestore = new HashMap();
   private final MountState mMountState;
   private final TransitionManager.OnAnimationCompleteListener mOnAnimationCompleteListener;
   private final TransitionManager.TransitionsResolver mResolver = new TransitionManager.TransitionsResolver(null);
   private final TransitionManager.RootAnimationListener mRootAnimationListener = new TransitionManager.RootAnimationListener(null);
   private AnimationBinding mRootAnimationToRun;
   private final ArrayList<AnimationBinding> mRunningRootAnimations = new ArrayList();
   private final SparseArrayCompat<String> mTraceNames = new SparseArrayCompat();


   public TransitionManager(TransitionManager.OnAnimationCompleteListener var1, MountState var2) {
      this.mOnAnimationCompleteListener = var1;
      this.mMountState = var2;
   }

   private static void acquireRef(OutputUnitsAffinityGroup<LayoutOutput> var0) {
      int var2 = var0.size();

      for(int var1 = 0; var1 < var2; ++var1) {
         ((LayoutOutput)var0.getAt(var1)).acquireRef();
      }

   }

   private static String changeTypeToString(int var0) {
      switch(var0) {
      case -1:
         return "UNSET";
      case 0:
         return "APPEARED";
      case 1:
         return "CHANGED";
      case 2:
         return "DISAPPEARED";
      default:
         StringBuilder var1 = new StringBuilder();
         var1.append("Unknown changeType: ");
         var1.append(var0);
         throw new RuntimeException(var1.toString());
      }
   }

   private void cleanupNonAnimatingAnimationStates() {
      Iterator var1 = this.mAnimationStates.keySet().iterator();

      while(var1.hasNext()) {
         String var2 = (String)var1.next();
         TransitionManager.AnimationState var3 = (TransitionManager.AnimationState)this.mAnimationStates.get(var2);
         if(var3.propertyStates.isEmpty()) {
            this.setMountContentInner(var2, var3, (OutputUnitsAffinityGroup)null);
            var1.remove();
            clearLayoutOutputs(var3);
         }
      }

   }

   private static void clearLayoutOutputs(TransitionManager.AnimationState var0) {
      if(var0.currentLayoutOutputsGroup != null) {
         release(var0.currentLayoutOutputsGroup);
         var0.currentLayoutOutputsGroup = null;
      }

      if(var0.nextLayoutOutputsGroup != null) {
         release(var0.nextLayoutOutputsGroup);
         var0.nextLayoutOutputsGroup = null;
      }

   }

   private AnimationBinding createAnimationsForTransition(Transition var1) {
      if(var1 instanceof Transition.TransitionUnit) {
         return this.createAnimationsForTransitionUnit((Transition.TransitionUnit)var1);
      } else if(var1 instanceof TransitionSet) {
         return this.createAnimationsForTransitionSet((TransitionSet)var1);
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("Unhandled Transition type: ");
         var2.append(var1);
         throw new RuntimeException(var2.toString());
      }
   }

   @Nullable
   private AnimationBinding createAnimationsForTransitionSet(TransitionSet var1) {
      ArrayList var4 = var1.getChildren();
      ArrayList var5 = new ArrayList();
      int var3 = var4.size();

      for(int var2 = 0; var2 < var3; ++var2) {
         AnimationBinding var6 = this.createAnimationsForTransition((Transition)var4.get(var2));
         if(var6 != null) {
            var5.add(var6);
         }
      }

      if(var5.isEmpty()) {
         return null;
      } else {
         return var1.createAnimation(var5);
      }
   }

   @Nullable
   private AnimationBinding createAnimationsForTransitionUnit(Transition.TransitionUnit var1) {
      Transition.AnimationTarget var4 = var1.getAnimationTarget();
      ArrayList var3 = new ArrayList();
      switch(null.$SwitchMap$com$facebook$litho$Transition$ComponentTargetType[var4.componentTarget.componentTargetType.ordinal()]) {
      case 1:
         this.createAnimationsForTransitionUnitAllKeys(var1, var3);
         break;
      case 2:
         this.createAnimationsForTransitionUnitAllKeys(var1, var3);
         break;
      case 3:
         String[] var5 = (String[])var4.componentTarget.componentTargetExtraData;

         for(int var2 = 0; var2 < var5.length; ++var2) {
            this.createAnimationsForTransitionUnit(var1, var5[var2], var3);
         }

         return (AnimationBinding)(var3.isEmpty()?null:(var3.size() == 1?(AnimationBinding)var3.get(0):new ParallelBinding(0, var3)));
      case 4:
         this.createAnimationsForTransitionUnit(var1, (String)var4.componentTarget.componentTargetExtraData, var3);
      }

      return (AnimationBinding)(var3.isEmpty()?null:(var3.size() == 1?(AnimationBinding)var3.get(0):new ParallelBinding(0, var3)));
   }

   private void createAnimationsForTransitionUnit(Transition.TransitionUnit var1, String var2, ArrayList<AnimationBinding> var3) {
      Transition.AnimationTarget var7 = var1.getAnimationTarget();
      int var6 = null.$SwitchMap$com$facebook$litho$Transition$PropertyTargetType[var7.propertyTarget.propertyTargetType.ordinal()];
      int var4 = 0;
      byte var5 = 0;
      switch(var6) {
      case 1:
         for(; var4 < AnimatedProperties.AUTO_LAYOUT_PROPERTIES.length; ++var4) {
            AnimationBinding var11 = this.maybeCreateAnimation(var1, var2, AnimatedProperties.AUTO_LAYOUT_PROPERTIES[var4]);
            if(var11 != null) {
               var3.add(var11);
            }
         }

         return;
      case 2:
         AnimatedProperty[] var10 = (AnimatedProperty[])var7.propertyTarget.propertyTargetExtraData;

         for(var4 = var5; var4 < var10.length; ++var4) {
            AnimationBinding var8 = this.maybeCreateAnimation(var1, var2, var10[var4]);
            if(var8 != null) {
               var3.add(var8);
            }
         }

         return;
      case 3:
         AnimationBinding var9 = this.maybeCreateAnimation(var1, var2, (AnimatedProperty)var7.propertyTarget.propertyTargetExtraData);
         if(var9 != null) {
            var3.add(var9);
            return;
         }

         return;
      default:
      }
   }

   private void createAnimationsForTransitionUnitAllKeys(Transition.TransitionUnit var1, ArrayList<AnimationBinding> var2) {
      Iterator var3 = this.mAnimationStates.keySet().iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         if(((TransitionManager.AnimationState)this.mAnimationStates.get(var4)).seenInLastTransition) {
            this.createAnimationsForTransitionUnit(var1, var4, var2);
         }
      }

   }

   private void createTransitionAnimations(Transition var1) {
      this.mRootAnimationToRun = this.createAnimationsForTransition(var1);
   }

   private void debugLogStartingAnimations() {
      throw new RuntimeException("Trying to debug log animations without debug flag set!");
   }

   private static float getPropertyValue(AnimatedProperty var0, OutputUnitsAffinityGroup<LayoutOutput> var1) {
      return var0.get((AnimatableItem)var1.getMostSignificantUnit());
   }

   @Nullable
   static Transition getRootTransition(List<Transition> var0) {
      return (Transition)(var0.isEmpty()?null:(var0.size() == 1?(Transition)var0.get(0):new ParallelTransitionSet(var0)));
   }

   @Nullable
   private AnimationBinding maybeCreateAnimation(Transition.TransitionUnit var1, String var2, AnimatedProperty var3) {
      TransitionManager.AnimationState var8 = (TransitionManager.AnimationState)this.mAnimationStates.get(var2);
      if(var8 != null) {
         if(var8.currentLayoutOutputsGroup == null && var8.nextLayoutOutputsGroup == null) {
            return null;
         } else {
            int var6 = var8.changeType;
            changeTypeToString(var8.changeType);
            if((var6 != 0 || var1.hasAppearAnimation()) && (var6 != 2 || var1.hasDisappearAnimation())) {
               TransitionManager.PropertyState var7 = (TransitionManager.PropertyState)var8.propertyStates.get(var3);
               PropertyHandle var9 = new PropertyHandle(var2, var3);
               float var4;
               if(var7 != null) {
                  var4 = var7.animatedPropertyNode.getValue();
               } else if(var8.changeType != 0) {
                  var4 = var3.get((AnimatableItem)var8.currentLayoutOutputsGroup.getMostSignificantUnit());
               } else {
                  var4 = var1.getAppearFrom().resolve(this.mResolver, var9);
               }

               float var5;
               if(var8.changeType != 2) {
                  var5 = var3.get((AnimatableItem)var8.nextLayoutOutputsGroup.getMostSignificantUnit());
               } else {
                  var5 = var1.getDisappearTo().resolve(this.mResolver, var9);
               }

               if(var7 != null && var7.targetValue != null) {
                  if(var5 == var7.targetValue.floatValue()) {
                     return null;
                  }
               } else if(var4 == var5) {
                  return null;
               }

               AnimationBinding var10 = var1.createAnimation(var9, var5);
               var10.addListener(this.mAnimationBindingListener);
               TransitionManager.PropertyState var11 = var7;
               if(var7 == null) {
                  var11 = new TransitionManager.PropertyState(null);
                  var11.animatedPropertyNode = new AnimatedPropertyNode(var8.mountContentGroup, var3);
                  var8.propertyStates.put(var3, var11);
               }

               var11.animatedPropertyNode.setValue(var4);
               ++var11.numPendingAnimations;
               ArrayList var12 = new ArrayList();
               var12.add(var9);
               this.mAnimationsToPropertyHandles.put(var10, var12);
               this.mInitialStatesToRestore.put(var9, Float.valueOf(var4));
               if(!TextUtils.isEmpty(var1.getTraceName())) {
                  this.mTraceNames.put(var10.hashCode(), var1.getTraceName());
               }

               return var10;
            } else {
               return null;
            }
         }
      } else {
         return null;
      }
   }

   private void recordLastMountedValues(TransitionManager.AnimationState var1) {
      LayoutOutput var2;
      if(var1.nextLayoutOutputsGroup != null) {
         var2 = (LayoutOutput)var1.nextLayoutOutputsGroup.getMostSignificantUnit();
      } else {
         var2 = null;
      }

      Iterator var3 = var1.propertyStates.keySet().iterator();

      while(var3.hasNext()) {
         AnimatedProperty var4 = (AnimatedProperty)var3.next();
         TransitionManager.PropertyState var5 = (TransitionManager.PropertyState)var1.propertyStates.get(var4);
         if(var2 == null) {
            var5.lastMountedValue = null;
         } else {
            var5.lastMountedValue = Float.valueOf(var4.get((AnimatableItem)var2));
         }
      }

   }

   private void recordLayoutOutputsGroupDiff(String var1, OutputUnitsAffinityGroup<LayoutOutput> var2, OutputUnitsAffinityGroup<LayoutOutput> var3) {
      TransitionManager.AnimationState var5 = (TransitionManager.AnimationState)this.mAnimationStates.get(var1);
      TransitionManager.AnimationState var4 = var5;
      if(var5 == null) {
         var4 = new TransitionManager.AnimationState(null);
         this.mAnimationStates.put(var1, var4);
      }

      if(var2 == null && var3 == null) {
         throw new RuntimeException("Both current and next LayoutOutput groups were null!");
      } else {
         if(var2 == null && var3 != null) {
            var4.changeType = 0;
         } else if(var2 != null && var3 != null) {
            var4.changeType = 1;
         } else {
            var4.changeType = 2;
         }

         if(var2 != null) {
            acquireRef(var2);
         }

         var4.currentLayoutOutputsGroup = var2;
         if(var3 != null) {
            acquireRef(var3);
         }

         var4.nextLayoutOutputsGroup = var3;
         this.recordLastMountedValues(var4);
         var4.seenInLastTransition = true;
      }
   }

   private void recursivelySetChildClipping(Object var1, boolean var2) {
      if(var1 instanceof View) {
         this.recursivelySetChildClippingForView((View)var1, var2);
      }
   }

   private void recursivelySetChildClippingForGroup(OutputUnitsAffinityGroup<Object> var1, boolean var2) {
      this.recursivelySetChildClipping(var1.get(3), var2);
   }

   private void recursivelySetChildClippingForView(View var1, boolean var2) {
      if(var1 instanceof ComponentHost) {
         if(var2) {
            ((ComponentHost)var1).restoreChildClipping();
         } else {
            ((ComponentHost)var1).temporaryDisableChildClipping();
         }
      }

      ViewParent var3 = var1.getParent();
      if(var3 instanceof ComponentHost) {
         this.recursivelySetChildClippingForView((View)var3, var2);
      }

   }

   private static void release(OutputUnitsAffinityGroup<LayoutOutput> var0) {
      int var2 = var0.size();

      for(int var1 = 0; var1 < var2; ++var1) {
         ((LayoutOutput)var0.getAt(var1)).release();
      }

   }

   private static void resetProperty(AnimatedProperty var0, OutputUnitsAffinityGroup<Object> var1) {
      int var3 = var1.size();

      for(int var2 = 0; var2 < var3; ++var2) {
         var0.reset(var1.getAt(var2));
      }

   }

   private void restoreInitialStates() {
      Iterator var2 = this.mInitialStatesToRestore.keySet().iterator();

      while(var2.hasNext()) {
         PropertyHandle var3 = (PropertyHandle)var2.next();
         float var1 = ((Float)this.mInitialStatesToRestore.get(var3)).floatValue();
         TransitionManager.AnimationState var4 = (TransitionManager.AnimationState)this.mAnimationStates.get(var3.getTransitionKey());
         if(var4.mountContentGroup != null) {
            setPropertyValue(var3.getProperty(), var1, var4.mountContentGroup);
         }
      }

      this.mInitialStatesToRestore.clear();
   }

   private void setMountContentInner(String var1, TransitionManager.AnimationState var2, OutputUnitsAffinityGroup<Object> var3) {
      OutputUnitsAffinityGroup var5 = var2.mountContentGroup;
      if((var5 != null || var3 != null) && (var5 == null || !var5.equals(var3))) {
         Map var6 = var2.propertyStates;
         if(var2.mountContentGroup != null) {
            Iterator var4 = var6.keySet().iterator();

            while(var4.hasNext()) {
               resetProperty((AnimatedProperty)var4.next(), var2.mountContentGroup);
            }

            this.recursivelySetChildClippingForGroup(var2.mountContentGroup, true);
         }

         Iterator var7 = var6.values().iterator();

         while(var7.hasNext()) {
            ((TransitionManager.PropertyState)var7.next()).animatedPropertyNode.setMountContentGroup(var3);
         }

         if(var3 != null) {
            this.recursivelySetChildClippingForGroup(var3, false);
         }

         var2.mountContentGroup = var3;
      }
   }

   private static void setPropertyValue(AnimatedProperty var0, float var1, OutputUnitsAffinityGroup<Object> var2) {
      int var4 = var2.size();

      for(int var3 = 0; var3 < var4; ++var3) {
         var0.set(var2.getAt(var3), var1);
      }

   }

   boolean isKeyAnimating(String var1) {
      return this.mAnimationStates.containsKey(var1);
   }

   boolean isKeyDisappearing(String var1) {
      TransitionManager.AnimationState var3 = (TransitionManager.AnimationState)this.mAnimationStates.get(var1);
      boolean var2 = false;
      if(var3 == null) {
         return false;
      } else {
         if(var3.changeType == 2) {
            var2 = true;
         }

         return var2;
      }
   }

   void removeMountContent(String var1, int var2) {
      TransitionManager.AnimationState var4 = (TransitionManager.AnimationState)this.mAnimationStates.get(var1);
      if(var4 != null) {
         OutputUnitsAffinityGroup var3 = var4.mountContentGroup;
         if(var3 != null) {
            if(var3.get(var2) != null) {
               if(var3.size() > 1) {
                  var3 = new OutputUnitsAffinityGroup(var3);
                  var3.replace(var2, (Object)null);
               } else {
                  var3 = null;
               }

               this.setMountContentInner(var1, var4, var3);
            }
         }
      }
   }

   void reset() {
      Iterator var2 = this.mAnimationStates.keySet().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         TransitionManager.AnimationState var4 = (TransitionManager.AnimationState)this.mAnimationStates.get(var3);
         this.setMountContentInner(var3, var4, (OutputUnitsAffinityGroup)null);
         clearLayoutOutputs(var4);
      }

      this.mAnimationStates.clear();
      this.mTraceNames.clear();
      this.mAnimationsToPropertyHandles.clear();

      for(int var1 = this.mRunningRootAnimations.size() - 1; var1 >= 0; --var1) {
         ((AnimationBinding)this.mRunningRootAnimations.get(var1)).stop();
      }

      this.mRunningRootAnimations.clear();
      this.mRootAnimationToRun = null;
   }

   void runTransitions() {
      this.restoreInitialStates();
      if(this.mRootAnimationToRun != null) {
         this.mRootAnimationToRun.addListener(this.mRootAnimationListener);
         this.mRootAnimationToRun.start(this.mResolver);
         this.mRootAnimationToRun = null;
      }

   }

   void setMountContent(String var1, OutputUnitsAffinityGroup<Object> var2) {
      TransitionManager.AnimationState var3 = (TransitionManager.AnimationState)this.mAnimationStates.get(var1);
      if(var3 != null) {
         this.setMountContentInner(var1, var3, var2);
      }

   }

   void setupTransitions(LayoutState var1, LayoutState var2, Transition var3) {
      boolean var4 = ComponentsSystrace.isTracing();
      if(var4) {
         ComponentsSystrace.beginSection("TransitionManager.setupTransition");
      }

      for(Iterator var5 = this.mAnimationStates.values().iterator(); var5.hasNext(); ((TransitionManager.AnimationState)var5.next()).seenInLastTransition = false) {
         ;
      }

      Map var12 = var2.getTransitionKeyMapping();
      if(var1 == null) {
         Iterator var10 = var12.keySet().iterator();

         while(var10.hasNext()) {
            String var14 = (String)var10.next();
            this.recordLayoutOutputsGroupDiff(var14, (OutputUnitsAffinityGroup)null, (OutputUnitsAffinityGroup)var12.get(var14));
         }
      } else {
         Map var11 = var1.getTransitionKeyMapping();
         HashSet var15 = new HashSet();

         String var7;
         OutputUnitsAffinityGroup var8;
         OutputUnitsAffinityGroup var9;
         for(Iterator var6 = var12.keySet().iterator(); var6.hasNext(); this.recordLayoutOutputsGroupDiff(var7, var9, var8)) {
            var7 = (String)var6.next();
            var8 = (OutputUnitsAffinityGroup)var12.get(var7);
            var9 = (OutputUnitsAffinityGroup)var11.get(var7);
            if(var9 != null) {
               var15.add(var7);
            }
         }

         Iterator var13 = var11.keySet().iterator();

         while(var13.hasNext()) {
            String var16 = (String)var13.next();
            if(!var15.contains(var16)) {
               this.recordLayoutOutputsGroupDiff(var16, (OutputUnitsAffinityGroup)var11.get(var16), (OutputUnitsAffinityGroup)null);
            }
         }
      }

      this.createTransitionAnimations(var3);
      this.cleanupNonAnimatingAnimationStates();
      if(var4) {
         ComponentsSystrace.endSection();
      }

   }

   class RootAnimationListener implements AnimationBindingListener {

      private RootAnimationListener() {}

      // $FF: synthetic method
      RootAnimationListener(Object var2) {
         this();
      }

      public void onCanceledBeforeStart(AnimationBinding var1) {
         TransitionManager.this.mRunningRootAnimations.remove(var1);
      }

      public void onFinish(AnimationBinding var1) {
         TransitionManager.this.mRunningRootAnimations.remove(var1);
      }

      public void onScheduledToStartLater(AnimationBinding var1) {}

      public void onWillStart(AnimationBinding var1) {
         TransitionManager.this.mRunningRootAnimations.add(var1);
      }

      public boolean shouldStart(AnimationBinding var1) {
         return true;
      }
   }

   public interface OnAnimationCompleteListener {

      void onAnimationComplete(String var1);
   }

   class TransitionsResolver implements Resolver {

      private TransitionsResolver() {}

      // $FF: synthetic method
      TransitionsResolver(Object var2) {
         this();
      }

      public AnimatedPropertyNode getAnimatedPropertyNode(PropertyHandle var1) {
         return ((TransitionManager.PropertyState)((TransitionManager.AnimationState)TransitionManager.this.mAnimationStates.get(var1.getTransitionKey())).propertyStates.get(var1.getProperty())).animatedPropertyNode;
      }

      public float getCurrentState(PropertyHandle var1) {
         AnimatedProperty var2 = var1.getProperty();
         TransitionManager.AnimationState var4 = (TransitionManager.AnimationState)TransitionManager.this.mAnimationStates.get(var1.getTransitionKey());
         TransitionManager.PropertyState var3 = (TransitionManager.PropertyState)var4.propertyStates.get(var2);
         if(var3 != null) {
            return var3.animatedPropertyNode.getValue();
         } else {
            OutputUnitsAffinityGroup var5;
            if(var4.changeType == 0) {
               var5 = var4.nextLayoutOutputsGroup;
            } else {
               var5 = var4.currentLayoutOutputsGroup;
            }

            if(var5 == null) {
               throw new RuntimeException("Both LayoutOutputs were null!");
            } else {
               return var2.get((AnimatableItem)var5.getMostSignificantUnit());
            }
         }
      }
   }

   class TransitionsAnimationBindingListener implements AnimationBindingListener {

      private final ArrayList<PropertyAnimation> mTempPropertyAnimations;


      private TransitionsAnimationBindingListener() {
         this.mTempPropertyAnimations = new ArrayList();
      }

      // $FF: synthetic method
      TransitionsAnimationBindingListener(Object var2) {
         this();
      }

      private boolean areAllDisappearingAnimationsFinished(TransitionManager.AnimationState var1) {
         if(var1.changeType != 2) {
            throw new RuntimeException("This should only be checked for disappearing animations");
         } else {
            Iterator var2 = var1.propertyStates.values().iterator();

            do {
               if(!var2.hasNext()) {
                  return true;
               }
            } while(((TransitionManager.PropertyState)var2.next()).numPendingAnimations <= 0);

            return false;
         }
      }

      private void finishAnimation(AnimationBinding var1) {
         List var7 = (List)TransitionManager.this.mAnimationsToPropertyHandles.remove(var1);
         if(var7 != null) {
            int var4 = var7.size();

            for(int var2 = 0; var2 < var4; ++var2) {
               PropertyHandle var9 = (PropertyHandle)var7.get(var2);
               String var8 = var9.getTransitionKey();
               AnimatedProperty var10 = var9.getProperty();
               TransitionManager.AnimationState var13 = (TransitionManager.AnimationState)TransitionManager.this.mAnimationStates.get(var8);
               boolean var3;
               if(var13.changeType == 2) {
                  var3 = true;
               } else {
                  var3 = false;
               }

               boolean var5;
               if(var3) {
                  TransitionManager.PropertyState var14 = (TransitionManager.PropertyState)var13.propertyStates.get(var10);
                  if(var14 == null) {
                     throw new RuntimeException("Some animation bookkeeping is wrong: tried to remove an animation from the list of active animations, but it wasn\'t there.");
                  }

                  --var14.numPendingAnimations;
                  boolean var6 = this.areAllDisappearingAnimationsFinished(var13);
                  var5 = var6;
                  if(var6) {
                     var5 = var6;
                     if(var13.mountContentGroup != null) {
                        Iterator var15 = var13.propertyStates.keySet().iterator();

                        while(true) {
                           var5 = var6;
                           if(!var15.hasNext()) {
                              break;
                           }

                           TransitionManager.resetProperty((AnimatedProperty)var15.next(), var13.mountContentGroup);
                        }
                     }
                  }
               } else {
                  TransitionManager.PropertyState var11 = (TransitionManager.PropertyState)var13.propertyStates.get(var10);
                  if(var11 == null) {
                     throw new RuntimeException("Some animation bookkeeping is wrong: tried to remove an animation from the list of active animations, but it wasn\'t there.");
                  }

                  --var11.numPendingAnimations;
                  if(var11.numPendingAnimations > 0) {
                     var5 = false;
                  } else {
                     var13.propertyStates.remove(var10);
                     var5 = var13.propertyStates.isEmpty();
                     if(var13.mountContentGroup != null) {
                        TransitionManager.setPropertyValue(var10, TransitionManager.getPropertyValue(var10, var13.nextLayoutOutputsGroup), var13.mountContentGroup);
                     }
                  }
               }

               if(var5) {
                  if(var13.mountContentGroup != null) {
                     TransitionManager.this.recursivelySetChildClippingForGroup(var13.mountContentGroup, true);
                  }

                  if(TransitionManager.this.mOnAnimationCompleteListener != null) {
                     TransitionManager.this.mOnAnimationCompleteListener.onAnimationComplete(var8);
                  }

                  TransitionManager.this.mAnimationStates.remove(var8);
                  TransitionManager.clearLayoutOutputs(var13);
               }
            }

            String var12 = (String)TransitionManager.this.mTraceNames.get(var1.hashCode());
            if(!TextUtils.isEmpty(var12)) {
               ComponentsSystrace.endSectionAsync(var12, var1.hashCode());
               TransitionManager.this.mTraceNames.delete(var1.hashCode());
            }

         }
      }

      private void updateAnimationStates(AnimationBinding var1) {
         var1.collectTransitioningProperties(this.mTempPropertyAnimations);
         int var3 = this.mTempPropertyAnimations.size();

         for(int var2 = 0; var2 < var3; ++var2) {
            PropertyAnimation var4 = (PropertyAnimation)this.mTempPropertyAnimations.get(var2);
            String var5 = var4.getTransitionKey();
            TransitionManager.PropertyState var6 = (TransitionManager.PropertyState)((TransitionManager.AnimationState)TransitionManager.this.mAnimationStates.get(var5)).propertyStates.get(var4.getProperty());
            var6.targetValue = Float.valueOf(var4.getTargetValue());
            var6.animation = var1;
         }

         this.mTempPropertyAnimations.clear();
      }

      public void onCanceledBeforeStart(AnimationBinding var1) {
         this.finishAnimation(var1);
      }

      public void onFinish(AnimationBinding var1) {
         this.finishAnimation(var1);
      }

      public void onScheduledToStartLater(AnimationBinding var1) {
         this.updateAnimationStates(var1);
      }

      public void onWillStart(AnimationBinding var1) {
         this.updateAnimationStates(var1);
         String var2 = (String)TransitionManager.this.mTraceNames.get(var1.hashCode());
         if(!TextUtils.isEmpty(var2)) {
            ComponentsSystrace.beginSectionAsync(var2, var1.hashCode());
         }

      }

      public boolean shouldStart(AnimationBinding var1) {
         var1.collectTransitioningProperties(this.mTempPropertyAnimations);
         int var3 = this.mTempPropertyAnimations.size();
         int var2 = 0;

         boolean var4;
         boolean var5;
         for(var4 = true; var2 < var3; var4 = var5) {
            PropertyAnimation var7 = (PropertyAnimation)this.mTempPropertyAnimations.get(var2);
            String var6 = var7.getTransitionKey();
            TransitionManager.PropertyState var8 = (TransitionManager.PropertyState)((TransitionManager.AnimationState)TransitionManager.this.mAnimationStates.get(var6)).propertyStates.get(var7.getProperty());
            var5 = var4;
            if(var8.lastMountedValue != null) {
               var5 = var4;
               if(var8.lastMountedValue.floatValue() != var7.getTargetValue()) {
                  var5 = false;
               }
            }

            ++var2;
         }

         this.mTempPropertyAnimations.clear();
         return var4;
      }
   }

   static class PropertyState {

      public AnimatedPropertyNode animatedPropertyNode;
      public AnimationBinding animation;
      public Float lastMountedValue;
      public int numPendingAnimations;
      public Float targetValue;


      private PropertyState() {}

      // $FF: synthetic method
      PropertyState(Object var1) {
         this();
      }
   }

   static class AnimationState {

      public int changeType;
      @Nullable
      public OutputUnitsAffinityGroup<LayoutOutput> currentLayoutOutputsGroup;
      @Nullable
      public OutputUnitsAffinityGroup<Object> mountContentGroup;
      @Nullable
      public OutputUnitsAffinityGroup<LayoutOutput> nextLayoutOutputsGroup;
      public final Map<AnimatedProperty, TransitionManager.PropertyState> propertyStates;
      public boolean seenInLastTransition;


      private AnimationState() {
         this.propertyStates = new HashMap();
         this.changeType = -1;
         this.seenInLastTransition = false;
      }

      // $FF: synthetic method
      AnimationState(Object var1) {
         this();
      }
   }
}
