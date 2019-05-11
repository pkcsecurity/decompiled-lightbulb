package com.facebook.drawee.generic;

import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import com.facebook.common.internal.Preconditions;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public class GenericDraweeHierarchyBuilder {

   public static final ScalingUtils.ScaleType DEFAULT_ACTUAL_IMAGE_SCALE_TYPE = ScalingUtils.ScaleType.CENTER_CROP;
   public static final int DEFAULT_FADE_DURATION = 300;
   public static final ScalingUtils.ScaleType DEFAULT_SCALE_TYPE = ScalingUtils.ScaleType.CENTER_INSIDE;
   private ColorFilter mActualImageColorFilter;
   private PointF mActualImageFocusPoint;
   private Matrix mActualImageMatrix;
   private ScalingUtils.ScaleType mActualImageScaleType;
   private Drawable mBackground;
   private float mDesiredAspectRatio;
   private int mFadeDuration;
   private Drawable mFailureImage;
   private ScalingUtils.ScaleType mFailureImageScaleType;
   private List<Drawable> mOverlays;
   private Drawable mPlaceholderImage;
   @Nullable
   private ScalingUtils.ScaleType mPlaceholderImageScaleType;
   private Drawable mPressedStateOverlay;
   private Drawable mProgressBarImage;
   private ScalingUtils.ScaleType mProgressBarImageScaleType;
   private Resources mResources;
   private Drawable mRetryImage;
   private ScalingUtils.ScaleType mRetryImageScaleType;
   private RoundingParams mRoundingParams;


   public GenericDraweeHierarchyBuilder(Resources var1) {
      this.mResources = var1;
      this.init();
   }

   private void init() {
      this.mFadeDuration = 300;
      this.mDesiredAspectRatio = 0.0F;
      this.mPlaceholderImage = null;
      this.mPlaceholderImageScaleType = DEFAULT_SCALE_TYPE;
      this.mRetryImage = null;
      this.mRetryImageScaleType = DEFAULT_SCALE_TYPE;
      this.mFailureImage = null;
      this.mFailureImageScaleType = DEFAULT_SCALE_TYPE;
      this.mProgressBarImage = null;
      this.mProgressBarImageScaleType = DEFAULT_SCALE_TYPE;
      this.mActualImageScaleType = DEFAULT_ACTUAL_IMAGE_SCALE_TYPE;
      this.mActualImageMatrix = null;
      this.mActualImageFocusPoint = null;
      this.mActualImageColorFilter = null;
      this.mBackground = null;
      this.mOverlays = null;
      this.mPressedStateOverlay = null;
      this.mRoundingParams = null;
   }

   public static GenericDraweeHierarchyBuilder newInstance(Resources var0) {
      return new GenericDraweeHierarchyBuilder(var0);
   }

   private void validate() {
      if(this.mOverlays != null) {
         Iterator var1 = this.mOverlays.iterator();

         while(var1.hasNext()) {
            Preconditions.checkNotNull((Drawable)var1.next());
         }
      }

   }

   public GenericDraweeHierarchy build() {
      this.validate();
      return new GenericDraweeHierarchy(this);
   }

   @Nullable
   public ColorFilter getActualImageColorFilter() {
      return this.mActualImageColorFilter;
   }

   @Nullable
   public PointF getActualImageFocusPoint() {
      return this.mActualImageFocusPoint;
   }

   @Nullable
   public Matrix getActualImageMatrix() {
      return this.mActualImageMatrix;
   }

   @Nullable
   public ScalingUtils.ScaleType getActualImageScaleType() {
      return this.mActualImageScaleType;
   }

   @Nullable
   public Drawable getBackground() {
      return this.mBackground;
   }

   public float getDesiredAspectRatio() {
      return this.mDesiredAspectRatio;
   }

   public int getFadeDuration() {
      return this.mFadeDuration;
   }

   @Nullable
   public Drawable getFailureImage() {
      return this.mFailureImage;
   }

   @Nullable
   public ScalingUtils.ScaleType getFailureImageScaleType() {
      return this.mFailureImageScaleType;
   }

   @Nullable
   public List<Drawable> getOverlays() {
      return this.mOverlays;
   }

   @Nullable
   public Drawable getPlaceholderImage() {
      return this.mPlaceholderImage;
   }

   @Nullable
   public ScalingUtils.ScaleType getPlaceholderImageScaleType() {
      return this.mPlaceholderImageScaleType;
   }

   @Nullable
   public Drawable getPressedStateOverlay() {
      return this.mPressedStateOverlay;
   }

   @Nullable
   public Drawable getProgressBarImage() {
      return this.mProgressBarImage;
   }

   @Nullable
   public ScalingUtils.ScaleType getProgressBarImageScaleType() {
      return this.mProgressBarImageScaleType;
   }

   public Resources getResources() {
      return this.mResources;
   }

   @Nullable
   public Drawable getRetryImage() {
      return this.mRetryImage;
   }

   @Nullable
   public ScalingUtils.ScaleType getRetryImageScaleType() {
      return this.mRetryImageScaleType;
   }

   @Nullable
   public RoundingParams getRoundingParams() {
      return this.mRoundingParams;
   }

   public GenericDraweeHierarchyBuilder reset() {
      this.init();
      return this;
   }

   public GenericDraweeHierarchyBuilder setActualImageColorFilter(@Nullable ColorFilter var1) {
      this.mActualImageColorFilter = var1;
      return this;
   }

   public GenericDraweeHierarchyBuilder setActualImageFocusPoint(@Nullable PointF var1) {
      this.mActualImageFocusPoint = var1;
      return this;
   }

   @Deprecated
   public GenericDraweeHierarchyBuilder setActualImageMatrix(@Nullable Matrix var1) {
      this.mActualImageMatrix = var1;
      this.mActualImageScaleType = null;
      return this;
   }

   public GenericDraweeHierarchyBuilder setActualImageScaleType(@Nullable ScalingUtils.ScaleType var1) {
      this.mActualImageScaleType = var1;
      this.mActualImageMatrix = null;
      return this;
   }

   public GenericDraweeHierarchyBuilder setBackground(@Nullable Drawable var1) {
      this.mBackground = var1;
      return this;
   }

   public GenericDraweeHierarchyBuilder setDesiredAspectRatio(float var1) {
      this.mDesiredAspectRatio = var1;
      return this;
   }

   public GenericDraweeHierarchyBuilder setFadeDuration(int var1) {
      this.mFadeDuration = var1;
      return this;
   }

   public GenericDraweeHierarchyBuilder setFailureImage(int var1) {
      this.mFailureImage = this.mResources.getDrawable(var1);
      return this;
   }

   public GenericDraweeHierarchyBuilder setFailureImage(int var1, @Nullable ScalingUtils.ScaleType var2) {
      this.mFailureImage = this.mResources.getDrawable(var1);
      this.mFailureImageScaleType = var2;
      return this;
   }

   public GenericDraweeHierarchyBuilder setFailureImage(@Nullable Drawable var1) {
      this.mFailureImage = var1;
      return this;
   }

   public GenericDraweeHierarchyBuilder setFailureImage(Drawable var1, @Nullable ScalingUtils.ScaleType var2) {
      this.mFailureImage = var1;
      this.mFailureImageScaleType = var2;
      return this;
   }

   public GenericDraweeHierarchyBuilder setFailureImageScaleType(@Nullable ScalingUtils.ScaleType var1) {
      this.mFailureImageScaleType = var1;
      return this;
   }

   public GenericDraweeHierarchyBuilder setOverlay(@Nullable Drawable var1) {
      if(var1 == null) {
         this.mOverlays = null;
         return this;
      } else {
         this.mOverlays = Arrays.asList(new Drawable[]{var1});
         return this;
      }
   }

   public GenericDraweeHierarchyBuilder setOverlays(@Nullable List<Drawable> var1) {
      this.mOverlays = var1;
      return this;
   }

   public GenericDraweeHierarchyBuilder setPlaceholderImage(int var1) {
      this.mPlaceholderImage = this.mResources.getDrawable(var1);
      return this;
   }

   public GenericDraweeHierarchyBuilder setPlaceholderImage(int var1, @Nullable ScalingUtils.ScaleType var2) {
      this.mPlaceholderImage = this.mResources.getDrawable(var1);
      this.mPlaceholderImageScaleType = var2;
      return this;
   }

   public GenericDraweeHierarchyBuilder setPlaceholderImage(@Nullable Drawable var1) {
      this.mPlaceholderImage = var1;
      return this;
   }

   public GenericDraweeHierarchyBuilder setPlaceholderImage(Drawable var1, @Nullable ScalingUtils.ScaleType var2) {
      this.mPlaceholderImage = var1;
      this.mPlaceholderImageScaleType = var2;
      return this;
   }

   public GenericDraweeHierarchyBuilder setPlaceholderImageScaleType(@Nullable ScalingUtils.ScaleType var1) {
      this.mPlaceholderImageScaleType = var1;
      return this;
   }

   public GenericDraweeHierarchyBuilder setPressedStateOverlay(@Nullable Drawable var1) {
      if(var1 == null) {
         this.mPressedStateOverlay = null;
         return this;
      } else {
         StateListDrawable var2 = new StateListDrawable();
         var2.addState(new int[]{16842919}, var1);
         this.mPressedStateOverlay = var2;
         return this;
      }
   }

   public GenericDraweeHierarchyBuilder setProgressBarImage(int var1) {
      this.mProgressBarImage = this.mResources.getDrawable(var1);
      return this;
   }

   public GenericDraweeHierarchyBuilder setProgressBarImage(int var1, @Nullable ScalingUtils.ScaleType var2) {
      this.mProgressBarImage = this.mResources.getDrawable(var1);
      this.mProgressBarImageScaleType = var2;
      return this;
   }

   public GenericDraweeHierarchyBuilder setProgressBarImage(@Nullable Drawable var1) {
      this.mProgressBarImage = var1;
      return this;
   }

   public GenericDraweeHierarchyBuilder setProgressBarImage(Drawable var1, @Nullable ScalingUtils.ScaleType var2) {
      this.mProgressBarImage = var1;
      this.mProgressBarImageScaleType = var2;
      return this;
   }

   public GenericDraweeHierarchyBuilder setProgressBarImageScaleType(@Nullable ScalingUtils.ScaleType var1) {
      this.mProgressBarImageScaleType = var1;
      return this;
   }

   public GenericDraweeHierarchyBuilder setRetryImage(int var1) {
      this.mRetryImage = this.mResources.getDrawable(var1);
      return this;
   }

   public GenericDraweeHierarchyBuilder setRetryImage(int var1, @Nullable ScalingUtils.ScaleType var2) {
      this.mRetryImage = this.mResources.getDrawable(var1);
      this.mRetryImageScaleType = var2;
      return this;
   }

   public GenericDraweeHierarchyBuilder setRetryImage(@Nullable Drawable var1) {
      this.mRetryImage = var1;
      return this;
   }

   public GenericDraweeHierarchyBuilder setRetryImage(Drawable var1, @Nullable ScalingUtils.ScaleType var2) {
      this.mRetryImage = var1;
      this.mRetryImageScaleType = var2;
      return this;
   }

   public GenericDraweeHierarchyBuilder setRetryImageScaleType(@Nullable ScalingUtils.ScaleType var1) {
      this.mRetryImageScaleType = var1;
      return this;
   }

   public GenericDraweeHierarchyBuilder setRoundingParams(@Nullable RoundingParams var1) {
      this.mRoundingParams = var1;
      return this;
   }
}
