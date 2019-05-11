package com.facebook.litho.widget;

import android.content.res.Resources;
import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.widget.CardClip;
import com.facebook.litho.widget.CardShadow;
import com.facebook.litho.widget.CardShadowDrawable;
import com.facebook.yoga.YogaEdge;
import com.facebook.yoga.YogaPositionType;

@LayoutSpec(
   isPureRender = true
)
class CardSpec {

   private static final int DEFAULT_CORNER_RADIUS_DP = 2;
   private static final int DEFAULT_SHADOW_SIZE_DP = 2;
   @PropDefault
   static final int cardBackgroundColor = -1;
   @PropDefault
   static final int clippingColor = -1;
   @PropDefault
   static final float cornerRadius = -1.0F;
   @PropDefault
   static final float elevation = -1.0F;
   @PropDefault
   static final int shadowBottomOverride = -1;
   @PropDefault
   static final int shadowEndColor = 50331648;
   @PropDefault
   static final int shadowStartColor = 922746880;


   @OnCreateLayout
   static Component onCreateLayout(ComponentContext var0, @Prop Component var1, 
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
         resType = ResType.COLOR
      ) int var4, 
      @Prop(
         optional = true,
         resType = ResType.COLOR
      ) int var5, 
      @Prop(
         optional = true,
         resType = ResType.DIMEN_OFFSET
      ) float var6, 
      @Prop(
         optional = true,
         resType = ResType.DIMEN_OFFSET
      ) float var7, 
      @Prop(
         optional = true,
         resType = ResType.DIMEN_OFFSET
      ) int var8, 
      @Prop(
         optional = true
      ) boolean var9, 
      @Prop(
         optional = true
      ) boolean var10, 
      @Prop(
         optional = true
      ) boolean var11, 
      @Prop(
         optional = true
      ) boolean var12) {
      Resources var16 = var0.getAndroidContext().getResources();
      if(var6 == -1.0F) {
         var6 = pixels(var16, 2);
      }

      if(var7 == -1.0F) {
         var7 = pixels(var16, 2);
      }

      int var14 = CardShadowDrawable.getShadowTop(var7);
      if(var8 == -1) {
         var8 = CardShadowDrawable.getShadowBottom(var7);
      }

      int var13 = CardShadowDrawable.getShadowHorizontal(var7);
      Column.Builder var21 = Column.create(var0);
      Column.Builder var17 = (Column.Builder)Column.create(var0).marginPx(YogaEdge.HORIZONTAL, var13);
      YogaEdge var18 = YogaEdge.TOP;
      var13 = var14;
      if(var9) {
         var13 = var14;
         if(var10) {
            var13 = 0;
         }
      }

      var17 = (Column.Builder)var17.marginPx(var18, var13);
      var18 = YogaEdge.BOTTOM;
      var13 = var8;
      if(var11) {
         var13 = var8;
         if(var12) {
            var13 = 0;
         }
      }

      Column.Builder var20 = var21.child((Component.Builder)((Column.Builder)((Column.Builder)var17.marginPx(var18, var13)).backgroundColor(var2)).child(var1).child((Component.Builder)((CardClip.Builder)((CardClip.Builder)CardClip.create(var0).clippingColor(var3).cornerRadiusPx(var6).positionType(YogaPositionType.ABSOLUTE)).positionPx(YogaEdge.ALL, 0)).disableClipTopLeft(var9).disableClipTopRight(var10).disableClipBottomLeft(var11).disableClipBottomRight(var12)));
      CardShadow.Builder var19;
      if(var7 > 0.0F) {
         var19 = CardShadow.create(var0).shadowStartColor(var4).shadowEndColor(var5).cornerRadiusPx(var6).shadowSizePx(var7);
         boolean var15 = true;
         if(var9 && var10) {
            var9 = true;
         } else {
            var9 = false;
         }

         var19 = var19.hideTopShadow(var9);
         if(var11 && var12) {
            var9 = var15;
         } else {
            var9 = false;
         }

         var19 = (CardShadow.Builder)((CardShadow.Builder)var19.hideBottomShadow(var9).positionType(YogaPositionType.ABSOLUTE)).positionPx(YogaEdge.ALL, 0);
      } else {
         var19 = null;
      }

      return var20.child((Component.Builder)var19).build();
   }

   private static float pixels(Resources var0, int var1) {
      float var2 = var0.getDisplayMetrics().density;
      return (float)var1 * var2 + 0.5F;
   }
}
