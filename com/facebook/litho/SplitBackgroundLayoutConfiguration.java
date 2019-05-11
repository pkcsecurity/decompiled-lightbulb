package com.facebook.litho;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.SplitLayoutResolver;

public class SplitBackgroundLayoutConfiguration {

   static boolean canSplitChildrenLayouts(ComponentContext var0, Component var1) {
      return SplitLayoutResolver.isComponentEnabledForSplitting(var0, var1);
   }

   static boolean isSplitLayoutEnabled(Component var0) {
      return var0.mSplitChildrenLayoutInThreadPool;
   }
}
