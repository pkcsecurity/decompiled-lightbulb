package com.facebook.litho.sections;

import android.support.annotation.VisibleForTesting;
import com.facebook.litho.StateContainer;
import com.facebook.litho.sections.Children;
import com.facebook.litho.sections.Section;
import com.facebook.litho.sections.SectionContext;
import com.facebook.litho.sections.SectionLifecycle;

@VisibleForTesting
public final class SectionLifecycleTestUtil {

   public static Children createChildren(SectionLifecycle var0, SectionContext var1, Section var2) {
      return var0.createChildren(var1);
   }

   public static void createInitialState(SectionLifecycle var0, SectionContext var1, Section var2) {
      var0.createInitialState(var1);
   }

   public static StateContainer getStateContainer(Section var0) {
      return var0.getStateContainer();
   }

   public static boolean isDiffSectionSpec(SectionLifecycle var0) {
      return var0.isDiffSectionSpec();
   }

   public static void setScopedContext(Section var0, SectionContext var1) {
      var0.setScopedContext(var1);
   }
}
