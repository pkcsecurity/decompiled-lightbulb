package com.facebook.litho.widget;

import android.content.Context;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.annotations.MountSpec;
import com.facebook.litho.annotations.OnCreateMountContent;
import com.facebook.litho.annotations.OnMount;
import com.facebook.litho.annotations.OnUnmount;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.widget.CardClipDrawable;

@MountSpec(
   isPureRender = true
)
class CardClipSpec {

   @PropDefault
   static final int clippingColor = -1;


   @OnCreateMountContent
   static CardClipDrawable onCreateMountContent(Context var0) {
      return new CardClipDrawable();
   }

   @OnMount
   static void onMount(ComponentContext var0, CardClipDrawable var1, 
      @Prop(
         optional = true,
         resType = ResType.COLOR
      ) int var2, 
      @Prop(
         optional = true,
         resType = ResType.DIMEN_OFFSET
      ) float var3, 
      @Prop(
         optional = true
      ) boolean var4, 
      @Prop(
         optional = true
      ) boolean var5, 
      @Prop(
         optional = true
      ) boolean var6, 
      @Prop(
         optional = true
      ) boolean var7) {
      var1.setClippingColor(var2);
      var1.setCornerRadius(var3);
      byte var9 = 0;
      byte var10;
      if(var5) {
         var10 = 2;
      } else {
         var10 = 0;
      }

      byte var8;
      if(var6) {
         var8 = 4;
      } else {
         var8 = 0;
      }

      if(var7) {
         var9 = 8;
      }

      var1.setDisableClip(var9 | var10 | var4 | var8);
   }

   @OnUnmount
   static void onUnmount(ComponentContext var0, CardClipDrawable var1) {
      var1.setCornerRadius(0.0F);
      var1.setClippingColor(-1);
      var1.setDisableClip(0);
   }
}
