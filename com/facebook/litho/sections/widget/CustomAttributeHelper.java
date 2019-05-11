package com.facebook.litho.sections.widget;

import android.support.annotation.Nullable;
import com.facebook.litho.widget.ComponentRenderInfo;
import com.facebook.litho.widget.ComponentTreeHolder;
import com.facebook.litho.widget.RenderInfo;

public abstract class CustomAttributeHelper<T extends Object> {

   public final ComponentRenderInfo.Builder addAttribute(T var1, ComponentRenderInfo.Builder var2) {
      return (ComponentRenderInfo.Builder)var2.customAttribute(this.getTag(), var1);
   }

   @Nullable
   public final T getAttribute(@Nullable ComponentTreeHolder var1) {
      if(var1 == null) {
         return null;
      } else {
         RenderInfo var2 = var1.getRenderInfo();
         return var2 == null?null:var2.getCustomAttribute(this.getTag());
      }
   }

   protected abstract String getTag();
}
