package com.facebook.litho.widget;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.ComponentCreator;
import java.util.List;
import javax.annotation.Nullable;

@LayoutSpec
public class LazySelectorComponentSpec {

   @Nullable
   @OnCreateLayout
   static Component onCreateLayout(ComponentContext var0, 
      @Prop(
         optional = true,
         varArg = "component"
      ) List<ComponentCreator> var1) {
      if(var1 == null) {
         return null;
      } else {
         for(int var2 = 0; var2 < var1.size(); ++var2) {
            Component var3 = ((ComponentCreator)var1.get(var2)).create();
            if(Component.willRender(var0, var3)) {
               return var3;
            }
         }

         return null;
      }
   }
}
