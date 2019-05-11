package com.facebook.drawee.generic;

import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import com.facebook.common.internal.Preconditions;
import com.facebook.drawee.drawable.DrawableParent;
import com.facebook.drawee.drawable.FadeDrawable;
import com.facebook.drawee.drawable.ForwardingDrawable;
import com.facebook.drawee.drawable.MatrixDrawable;
import com.facebook.drawee.drawable.ScaleTypeDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RootDrawable;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.generic.WrappingUtils;
import com.facebook.drawee.interfaces.SettableDraweeHierarchy;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public class GenericDraweeHierarchy implements SettableDraweeHierarchy {

   private static final int ACTUAL_IMAGE_INDEX = 2;
   private static final int BACKGROUND_IMAGE_INDEX = 0;
   private static final int FAILURE_IMAGE_INDEX = 5;
   private static final int OVERLAY_IMAGES_INDEX = 6;
   private static final int PLACEHOLDER_IMAGE_INDEX = 1;
   private static final int PROGRESS_BAR_IMAGE_INDEX = 3;
   private static final int RETRY_IMAGE_INDEX = 4;
   private final ForwardingDrawable mActualImageWrapper;
   private final Drawable mEmptyActualImageDrawable = new ColorDrawable(0);
   private final FadeDrawable mFadeDrawable;
   private final Resources mResources;
   @Nullable
   private RoundingParams mRoundingParams;
   private final RootDrawable mTopLevelDrawable;


   GenericDraweeHierarchy(GenericDraweeHierarchyBuilder var1) {
      this.mResources = var1.getResources();
      this.mRoundingParams = var1.getRoundingParams();
      this.mActualImageWrapper = new ForwardingDrawable(this.mEmptyActualImageDrawable);
      List var5 = var1.getOverlays();
      byte var4 = 1;
      int var2;
      if(var5 != null) {
         var2 = var1.getOverlays().size();
      } else {
         var2 = 1;
      }

      byte var3;
      if(var1.getPressedStateOverlay() != null) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      var2 += var3;
      Drawable[] var8 = new Drawable[var2 + 6];
      var8[0] = this.buildBranch(var1.getBackground(), (ScalingUtils.ScaleType)null);
      var8[1] = this.buildBranch(var1.getPlaceholderImage(), var1.getPlaceholderImageScaleType());
      var8[2] = this.buildActualImageBranch(this.mActualImageWrapper, var1.getActualImageScaleType(), var1.getActualImageFocusPoint(), var1.getActualImageMatrix(), var1.getActualImageColorFilter());
      var8[3] = this.buildBranch(var1.getProgressBarImage(), var1.getProgressBarImageScaleType());
      var8[4] = this.buildBranch(var1.getRetryImage(), var1.getRetryImageScaleType());
      var8[5] = this.buildBranch(var1.getFailureImage(), var1.getFailureImageScaleType());
      if(var2 > 0) {
         int var7 = var4;
         if(var1.getOverlays() != null) {
            Iterator var6 = var1.getOverlays().iterator();
            var2 = 0;

            while(true) {
               var7 = var2;
               if(!var6.hasNext()) {
                  break;
               }

               var8[var2 + 6] = this.buildBranch((Drawable)var6.next(), (ScalingUtils.ScaleType)null);
               ++var2;
            }
         }

         if(var1.getPressedStateOverlay() != null) {
            var8[var7 + 6] = this.buildBranch(var1.getPressedStateOverlay(), (ScalingUtils.ScaleType)null);
         }
      }

      this.mFadeDrawable = new FadeDrawable(var8);
      this.mFadeDrawable.setTransitionDuration(var1.getFadeDuration());
      this.mTopLevelDrawable = new RootDrawable(WrappingUtils.maybeWrapWithRoundedOverlayColor(this.mFadeDrawable, this.mRoundingParams));
      this.mTopLevelDrawable.mutate();
      this.resetFade();
   }

   @Nullable
   private Drawable buildActualImageBranch(Drawable var1, @Nullable ScalingUtils.ScaleType var2, @Nullable PointF var3, @Nullable Matrix var4, @Nullable ColorFilter var5) {
      var1.setColorFilter(var5);
      return WrappingUtils.maybeWrapWithMatrix(WrappingUtils.maybeWrapWithScaleType(var1, var2, var3), var4);
   }

   @Nullable
   private Drawable buildBranch(@Nullable Drawable var1, @Nullable ScalingUtils.ScaleType var2) {
      return WrappingUtils.maybeWrapWithScaleType(WrappingUtils.maybeApplyLeafRounding(var1, this.mRoundingParams, this.mResources), var2);
   }

   private void fadeInLayer(int var1) {
      if(var1 >= 0) {
         this.mFadeDrawable.fadeInLayer(var1);
      }

   }

   private void fadeOutBranches() {
      this.fadeOutLayer(1);
      this.fadeOutLayer(2);
      this.fadeOutLayer(3);
      this.fadeOutLayer(4);
      this.fadeOutLayer(5);
   }

   private void fadeOutLayer(int var1) {
      if(var1 >= 0) {
         this.mFadeDrawable.fadeOutLayer(var1);
      }

   }

   private DrawableParent getParentDrawableAtIndex(int var1) {
      DrawableParent var3 = this.mFadeDrawable.getDrawableParentForIndex(var1);
      Object var2 = var3;
      if(var3.getDrawable() instanceof MatrixDrawable) {
         var2 = (MatrixDrawable)var3.getDrawable();
      }

      Object var4 = var2;
      if(((DrawableParent)var2).getDrawable() instanceof ScaleTypeDrawable) {
         var4 = (ScaleTypeDrawable)((DrawableParent)var2).getDrawable();
      }

      return (DrawableParent)var4;
   }

   private ScaleTypeDrawable getScaleTypeDrawableAtIndex(int var1) {
      DrawableParent var2 = this.getParentDrawableAtIndex(var1);
      return var2 instanceof ScaleTypeDrawable?(ScaleTypeDrawable)var2:WrappingUtils.wrapChildWithScaleType(var2, ScalingUtils.ScaleType.FIT_XY);
   }

   private boolean hasScaleTypeDrawableAtIndex(int var1) {
      return this.getParentDrawableAtIndex(var1) instanceof ScaleTypeDrawable;
   }

   private void resetActualImages() {
      this.mActualImageWrapper.setDrawable(this.mEmptyActualImageDrawable);
   }

   private void resetFade() {
      if(this.mFadeDrawable != null) {
         this.mFadeDrawable.beginBatchMode();
         this.mFadeDrawable.fadeInAllLayers();
         this.fadeOutBranches();
         this.fadeInLayer(1);
         this.mFadeDrawable.finishTransitionImmediately();
         this.mFadeDrawable.endBatchMode();
      }

   }

   private void setChildDrawableAtIndex(int var1, @Nullable Drawable var2) {
      if(var2 == null) {
         this.mFadeDrawable.setDrawable(var1, (Drawable)null);
      } else {
         var2 = WrappingUtils.maybeApplyLeafRounding(var2, this.mRoundingParams, this.mResources);
         this.getParentDrawableAtIndex(var1).setDrawable(var2);
      }
   }

   private void setProgress(float var1) {
      Drawable var2 = this.mFadeDrawable.getDrawable(3);
      if(var2 != null) {
         if(var1 >= 0.999F) {
            if(var2 instanceof Animatable) {
               ((Animatable)var2).stop();
            }

            this.fadeOutLayer(3);
         } else {
            if(var2 instanceof Animatable) {
               ((Animatable)var2).start();
            }

            this.fadeInLayer(3);
         }

         var2.setLevel(Math.round(var1 * 10000.0F));
      }
   }

   public void getActualImageBounds(RectF var1) {
      this.mActualImageWrapper.getTransformedBounds(var1);
   }

   @Nullable
   public ScalingUtils.ScaleType getActualImageScaleType() {
      return !this.hasScaleTypeDrawableAtIndex(2)?null:this.getScaleTypeDrawableAtIndex(2).getScaleType();
   }

   public int getFadeDuration() {
      return this.mFadeDrawable.getTransitionDuration();
   }

   @Nullable
   public RoundingParams getRoundingParams() {
      return this.mRoundingParams;
   }

   public Drawable getTopLevelDrawable() {
      return this.mTopLevelDrawable;
   }

   public boolean hasPlaceholderImage() {
      return this.mFadeDrawable.getDrawable(1) != null;
   }

   public void reset() {
      this.resetActualImages();
      this.resetFade();
   }

   public void setActualImageColorFilter(ColorFilter var1) {
      this.mActualImageWrapper.setColorFilter(var1);
   }

   public void setActualImageFocusPoint(PointF var1) {
      Preconditions.checkNotNull(var1);
      this.getScaleTypeDrawableAtIndex(2).setFocusPoint(var1);
   }

   public void setActualImageScaleType(ScalingUtils.ScaleType var1) {
      Preconditions.checkNotNull(var1);
      this.getScaleTypeDrawableAtIndex(2).setScaleType(var1);
   }

   public void setBackgroundImage(@Nullable Drawable var1) {
      this.setChildDrawableAtIndex(0, var1);
   }

   public void setControllerOverlay(@Nullable Drawable var1) {
      this.mTopLevelDrawable.setControllerOverlay(var1);
   }

   public void setFadeDuration(int var1) {
      this.mFadeDrawable.setTransitionDuration(var1);
   }

   public void setFailure(Throwable var1) {
      this.mFadeDrawable.beginBatchMode();
      this.fadeOutBranches();
      if(this.mFadeDrawable.getDrawable(5) != null) {
         this.fadeInLayer(5);
      } else {
         this.fadeInLayer(1);
      }

      this.mFadeDrawable.endBatchMode();
   }

   public void setFailureImage(int var1) {
      this.setFailureImage(this.mResources.getDrawable(var1));
   }

   public void setFailureImage(int var1, ScalingUtils.ScaleType var2) {
      this.setFailureImage(this.mResources.getDrawable(var1), var2);
   }

   public void setFailureImage(@Nullable Drawable var1) {
      this.setChildDrawableAtIndex(5, var1);
   }

   public void setFailureImage(Drawable var1, ScalingUtils.ScaleType var2) {
      this.setChildDrawableAtIndex(5, var1);
      this.getScaleTypeDrawableAtIndex(5).setScaleType(var2);
   }

   public void setImage(Drawable var1, float var2, boolean var3) {
      var1 = WrappingUtils.maybeApplyLeafRounding(var1, this.mRoundingParams, this.mResources);
      var1.mutate();
      this.mActualImageWrapper.setDrawable(var1);
      this.mFadeDrawable.beginBatchMode();
      this.fadeOutBranches();
      this.fadeInLayer(2);
      this.setProgress(var2);
      if(var3) {
         this.mFadeDrawable.finishTransitionImmediately();
      }

      this.mFadeDrawable.endBatchMode();
   }

   public void setOverlayImage(int var1, @Nullable Drawable var2) {
      boolean var3;
      if(var1 >= 0 && var1 + 6 < this.mFadeDrawable.getNumberOfLayers()) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "The given index does not correspond to an overlay image.");
      this.setChildDrawableAtIndex(var1 + 6, var2);
   }

   public void setOverlayImage(@Nullable Drawable var1) {
      this.setOverlayImage(0, var1);
   }

   public void setPlaceholderImage(int var1) {
      this.setPlaceholderImage(this.mResources.getDrawable(var1));
   }

   public void setPlaceholderImage(int var1, ScalingUtils.ScaleType var2) {
      this.setPlaceholderImage(this.mResources.getDrawable(var1), var2);
   }

   public void setPlaceholderImage(@Nullable Drawable var1) {
      this.setChildDrawableAtIndex(1, var1);
   }

   public void setPlaceholderImage(Drawable var1, ScalingUtils.ScaleType var2) {
      this.setChildDrawableAtIndex(1, var1);
      this.getScaleTypeDrawableAtIndex(1).setScaleType(var2);
   }

   public void setPlaceholderImageFocusPoint(PointF var1) {
      Preconditions.checkNotNull(var1);
      this.getScaleTypeDrawableAtIndex(1).setFocusPoint(var1);
   }

   public void setProgress(float var1, boolean var2) {
      if(this.mFadeDrawable.getDrawable(3) != null) {
         this.mFadeDrawable.beginBatchMode();
         this.setProgress(var1);
         if(var2) {
            this.mFadeDrawable.finishTransitionImmediately();
         }

         this.mFadeDrawable.endBatchMode();
      }
   }

   public void setProgressBarImage(int var1) {
      this.setProgressBarImage(this.mResources.getDrawable(var1));
   }

   public void setProgressBarImage(int var1, ScalingUtils.ScaleType var2) {
      this.setProgressBarImage(this.mResources.getDrawable(var1), var2);
   }

   public void setProgressBarImage(@Nullable Drawable var1) {
      this.setChildDrawableAtIndex(3, var1);
   }

   public void setProgressBarImage(Drawable var1, ScalingUtils.ScaleType var2) {
      this.setChildDrawableAtIndex(3, var1);
      this.getScaleTypeDrawableAtIndex(3).setScaleType(var2);
   }

   public void setRetry(Throwable var1) {
      this.mFadeDrawable.beginBatchMode();
      this.fadeOutBranches();
      if(this.mFadeDrawable.getDrawable(4) != null) {
         this.fadeInLayer(4);
      } else {
         this.fadeInLayer(1);
      }

      this.mFadeDrawable.endBatchMode();
   }

   public void setRetryImage(int var1) {
      this.setRetryImage(this.mResources.getDrawable(var1));
   }

   public void setRetryImage(int var1, ScalingUtils.ScaleType var2) {
      this.setRetryImage(this.mResources.getDrawable(var1), var2);
   }

   public void setRetryImage(@Nullable Drawable var1) {
      this.setChildDrawableAtIndex(4, var1);
   }

   public void setRetryImage(Drawable var1, ScalingUtils.ScaleType var2) {
      this.setChildDrawableAtIndex(4, var1);
      this.getScaleTypeDrawableAtIndex(4).setScaleType(var2);
   }

   public void setRoundingParams(@Nullable RoundingParams var1) {
      this.mRoundingParams = var1;
      WrappingUtils.updateOverlayColorRounding(this.mTopLevelDrawable, this.mRoundingParams);

      for(int var2 = 0; var2 < this.mFadeDrawable.getNumberOfLayers(); ++var2) {
         WrappingUtils.updateLeafRounding(this.getParentDrawableAtIndex(var2), this.mRoundingParams, this.mResources);
      }

   }
}
