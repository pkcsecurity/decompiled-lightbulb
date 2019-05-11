package com.facebook.litho.widget;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.graphics.ColorUtils;
import android.widget.ImageView.ScaleType;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.widget.Image;

@LayoutSpec
class SolidColorSpec {

   @PropDefault
   static final float alpha = -1.0F;


   @OnCreateLayout
   static Component onCreateLayout(ComponentContext var0, 
      @Prop(
         resType = ResType.COLOR
      ) int var1, 
      @Prop(
         isCommonProp = true,
         optional = true,
         overrideCommonPropBehavior = true
      ) float var2) {
      int var3 = var1;
      if(var2 >= 0.0F) {
         var3 = ColorUtils.setAlphaComponent(var1, (int)(Math.min(1.0F, var2) * 255.0F));
      }

      return Image.create(var0).scaleType(ScaleType.FIT_XY).drawable(new ColorDrawable(var3)).build();
   }
}
