package com.facebook.litho;

import android.content.Context;
import android.provider.Settings.Global;
import android.support.annotation.Nullable;
import com.facebook.litho.Transition;
import com.facebook.litho.TransitionSet;
import com.facebook.litho.animation.AnimatedProperty;
import com.facebook.litho.config.ComponentsConfiguration;
import java.util.ArrayList;
import java.util.List;

class TransitionUtils {

   static void addTransitions(Transition var0, List<Transition> var1, @Nullable String var2) {
      if(var0 instanceof Transition.BaseTransitionUnitsBuilder) {
         var1.addAll(((Transition.BaseTransitionUnitsBuilder)var0).getTransitionUnits());
      } else if(var0 != null) {
         var1.add(var0);
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("[");
         var3.append(var2);
         var3.append("] Adding null to transition list is not allowed.");
         throw new IllegalStateException(var3.toString());
      }
   }

   static boolean areTransitionsEnabled(Context var0) {
      boolean var3 = ComponentsConfiguration.ARE_TRANSITIONS_SUPPORTED;
      boolean var2 = false;
      if(!var3) {
         return false;
      } else if(!ComponentsConfiguration.isEndToEndTestRun) {
         return true;
      } else if(!ComponentsConfiguration.CAN_CHECK_GLOBAL_ANIMATOR_SETTINGS) {
         return false;
      } else {
         float var1 = Global.getFloat(var0.getContentResolver(), "animator_duration_scale", 1.0F);
         if(ComponentsConfiguration.forceEnableTransitionsForInstrumentationTests || var1 != 0.0F) {
            var2 = true;
         }

         return var2;
      }
   }

   static void collectRootBoundsTransitions(String var0, Transition var1, AnimatedProperty var2, Transition.RootBoundsTransition var3) {
      boolean var7 = var1 instanceof TransitionSet;
      byte var5 = 0;
      int var4 = 0;
      ArrayList var9;
      if(var7) {
         var9 = ((TransitionSet)var1).getChildren();

         for(int var11 = var9.size(); var4 < var11; ++var4) {
            collectRootBoundsTransitions(var0, (Transition)var9.get(var4), var2, var3);
         }
      } else if(var1 instanceof Transition.TransitionUnit) {
         Transition.TransitionUnit var10 = (Transition.TransitionUnit)var1;
         if(var10.targetsKey(var0) && var10.targetsProperty(var2)) {
            var3.hasTransition = true;
            if(var10.hasAppearAnimation()) {
               var3.appearTransition = var10;
               return;
            }
         }
      } else {
         if(!(var1 instanceof Transition.BaseTransitionUnitsBuilder)) {
            StringBuilder var8 = new StringBuilder();
            var8.append("Unhandled transition type: ");
            var8.append(var1);
            throw new RuntimeException(var8.toString());
         }

         var9 = ((Transition.BaseTransitionUnitsBuilder)var1).getTransitionUnits();
         int var6 = var9.size();

         for(var4 = var5; var4 < var6; ++var4) {
            collectRootBoundsTransitions(var0, (Transition)var9.get(var4), var2, var3);
         }
      }

   }
}
