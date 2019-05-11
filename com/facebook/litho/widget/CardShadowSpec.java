package com.facebook.litho.widget;

import android.content.Context;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.annotations.MountSpec;
import com.facebook.litho.annotations.OnCreateMountContent;
import com.facebook.litho.annotations.OnMount;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.widget.CardShadowDrawable;

@MountSpec(
   isPureRender = true
)
class CardShadowSpec {

   @OnCreateMountContent
   static CardShadowDrawable onCreateMountContent(Context var0) {
      return new CardShadowDrawable();
   }

   @OnMount
   static void onMount(ComponentContext var0, CardShadowDrawable var1, 
      @Prop(
         optional = true,
         resType = ResType.COLOR
      ) int var2, 
      @Prop(
         optional = true,
         resType = ResType.COLOR
      ) int var3, 
      @Prop(
         optional = true,
         resType = ResType.DIMEN_OFFSET
      ) float var4, 
      @Prop(
         optional = true,
         resType = ResType.DIMEN_SIZE
      ) float var5, 
      @Prop(
         optional = true
      ) boolean var6, 
      @Prop(
         optional = true
      ) boolean var7) {
      var1.setShadowStartColor(var2);
      var1.setShadowEndColor(var3);
      var1.setCornerRadius(var4);
      var1.setShadowSize(var5);
      var1.setHideTopShadow(var6);
      var1.setHideBottomShadow(var7);
   }
}
