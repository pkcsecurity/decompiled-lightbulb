package com.facebook.litho.fresco;

import android.graphics.ColorFilter;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.ResType;

public class FrescoImageHierarchyTools {

   public static void setupHierarchy(
      @Prop(
         optional = true
      ) ScalingUtils.ScaleType var0, 
      @Prop(
         optional = true
      ) int var1, 
      @Prop(
         optional = true,
         resType = ResType.DRAWABLE
      ) Drawable var2, 
      @Prop(
         optional = true
      ) ScalingUtils.ScaleType var3, 
      @Prop(
         optional = true,
         resType = ResType.DRAWABLE
      ) Drawable var4, 
      @Prop(
         optional = true
      ) PointF var5, 
      @Prop(
         optional = true
      ) ScalingUtils.ScaleType var6, 
      @Prop(
         optional = true,
         resType = ResType.DRAWABLE
      ) Drawable var7, 
      @Prop(
         optional = true
      ) ScalingUtils.ScaleType var8, 
      @Prop(
         optional = true,
         resType = ResType.DRAWABLE
      ) Drawable var9, 
      @Prop(
         optional = true
      ) ScalingUtils.ScaleType var10, 
      @Prop(
         optional = true
      ) RoundingParams var11, 
      @Prop(
         optional = true
      ) ColorFilter var12, GenericDraweeHierarchy var13) {
      if(var4 == null) {
         var13.setPlaceholderImage((Drawable)null);
      } else {
         var13.setPlaceholderImage(var4, var6);
      }

      if(var6 == ScalingUtils.ScaleType.FOCUS_CROP) {
         var13.setPlaceholderImageFocusPoint(var5);
      }

      var13.setActualImageScaleType(var0);
      var13.setFadeDuration(var1);
      if(var2 == null) {
         var13.setFailureImage((Drawable)null);
      } else {
         var13.setFailureImage(var2, var3);
      }

      if(var7 == null) {
         var13.setProgressBarImage((Drawable)null);
      } else {
         var13.setProgressBarImage(var7, var8);
      }

      if(var9 == null) {
         var13.setRetryImage((Drawable)null);
      } else {
         var13.setRetryImage(var9, var10);
      }

      var13.setRoundingParams(var11);
      var13.setActualImageColorFilter(var12);
   }
}
