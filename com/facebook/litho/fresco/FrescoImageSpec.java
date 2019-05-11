package com.facebook.litho.fresco;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.Size;
import com.facebook.litho.annotations.MountSpec;
import com.facebook.litho.annotations.OnBind;
import com.facebook.litho.annotations.OnCreateMountContent;
import com.facebook.litho.annotations.OnMeasure;
import com.facebook.litho.annotations.OnMount;
import com.facebook.litho.annotations.OnUnbind;
import com.facebook.litho.annotations.OnUnmount;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.fresco.DraweeDrawable;
import com.facebook.litho.fresco.FrescoImageDefaults;
import com.facebook.litho.fresco.FrescoImageHierarchyTools;
import com.facebook.litho.utils.MeasureUtils;

@MountSpec
public class FrescoImageSpec {

   @PropDefault
   protected static final ScalingUtils.ScaleType actualImageScaleType = FrescoImageDefaults.DEFAULT_ACTUAL_IMAGE_SCALE_TYPE;
   @PropDefault
   protected static final int fadeDuration = 300;
   @PropDefault
   protected static final ScalingUtils.ScaleType failureImageScaleType = FrescoImageDefaults.DEFAULT_SCALE_TYPE;
   @PropDefault
   protected static final float imageAspectRatio = 1.0F;
   @PropDefault
   protected static final PointF placeholderImageFocusPoint = FrescoImageDefaults.DEFAULT_PLACEHOLDER_IMAGE_FOCUS_POINT;
   @PropDefault
   protected static final ScalingUtils.ScaleType placeholderImageScaleType = FrescoImageDefaults.DEFAULT_SCALE_TYPE;
   @PropDefault
   protected static final ScalingUtils.ScaleType progressBarImageScaleType = FrescoImageDefaults.DEFAULT_SCALE_TYPE;
   @PropDefault
   protected static final ScalingUtils.ScaleType retryImageScaleType = FrescoImageDefaults.DEFAULT_SCALE_TYPE;


   @OnBind
   protected static void onBind(ComponentContext var0, DraweeDrawable<GenericDraweeHierarchy> var1, @Prop DraweeController var2) {
      var1.setController(var2);
      if(var2 != null) {
         var2.onViewportVisibilityHint(true);
      }

   }

   @OnCreateMountContent
   protected static DraweeDrawable<GenericDraweeHierarchy> onCreateMountContent(Context var0) {
      return new DraweeDrawable(var0, GenericDraweeHierarchyBuilder.newInstance(var0.getResources()).build());
   }

   @OnMeasure
   protected static void onMeasure(ComponentContext var0, ComponentLayout var1, int var2, int var3, Size var4, 
      @Prop(
         optional = true,
         resType = ResType.FLOAT
      ) float var5) {
      MeasureUtils.measureWithAspectRatio(var2, var3, var5, var4);
   }

   @OnMount
   protected static void onMount(ComponentContext var0, DraweeDrawable<GenericDraweeHierarchy> var1, 
      @Prop(
         optional = true
      ) ScalingUtils.ScaleType var2, 
      @Prop(
         optional = true
      ) int var3, 
      @Prop(
         optional = true,
         resType = ResType.DRAWABLE
      ) Drawable var4, 
      @Prop(
         optional = true
      ) ScalingUtils.ScaleType var5, 
      @Prop(
         optional = true,
         resType = ResType.DRAWABLE
      ) Drawable var6, 
      @Prop(
         optional = true
      ) PointF var7, 
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
         optional = true,
         resType = ResType.DRAWABLE
      ) Drawable var11, 
      @Prop(
         optional = true
      ) ScalingUtils.ScaleType var12, 
      @Prop(
         optional = true
      ) RoundingParams var13, 
      @Prop(
         optional = true
      ) ColorFilter var14) {
      FrescoImageHierarchyTools.setupHierarchy(var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, (GenericDraweeHierarchy)var1.getDraweeHierarchy());
      var1.mount();
   }

   @OnUnbind
   protected static void onUnbind(ComponentContext var0, DraweeDrawable<GenericDraweeHierarchy> var1, @Prop DraweeController var2) {
      var1.setController((DraweeController)null);
      if(var2 != null) {
         var2.onViewportVisibilityHint(false);
      }

   }

   @OnUnmount
   protected static void onUnmount(ComponentContext var0, DraweeDrawable<GenericDraweeHierarchy> var1) {
      var1.unmount();
   }
}
