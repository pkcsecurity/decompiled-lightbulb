package com.facebook.litho.widget;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import javax.annotation.Nullable;

@LayoutSpec
class EmptyComponentSpec {

   @Nullable
   @OnCreateLayout
   static Component onCreateLayout(ComponentContext var0) {
      return null;
   }
}
