package com.facebook.react.flat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.flat.AbstractDrawCommand;
import com.facebook.react.flat.DrawImage;
import com.facebook.react.flat.DraweeRequestHelper;
import com.facebook.react.flat.FlatViewGroup;
import com.facebook.react.views.image.GlobalImageLoadListener;
import com.facebook.react.views.image.ImageResizeMode;
import com.facebook.react.views.imagehelper.ImageSource;
import com.facebook.react.views.imagehelper.MultiSourceHelper;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nullable;

final class DrawImageWithDrawee extends AbstractDrawCommand implements ControllerListener, DrawImage {

   private static final String LOCAL_CONTENT_SCHEME = "content";
   private static final String LOCAL_FILE_SCHEME = "file";
   private int mBorderColor;
   private float mBorderRadius;
   private float mBorderWidth;
   @Nullable
   private FlatViewGroup.InvalidateCallback mCallback;
   @Nullable
   private PorterDuffColorFilter mColorFilter;
   private int mFadeDuration = 300;
   @Nullable
   private final GlobalImageLoadListener mGlobalImageLoadListener;
   private boolean mProgressiveRenderingEnabled;
   private int mReactTag;
   @Nullable
   private DraweeRequestHelper mRequestHelper;
   private ScalingUtils.ScaleType mScaleType = ImageResizeMode.defaultValue();
   private final List<ImageSource> mSources = new LinkedList();


   public DrawImageWithDrawee(@Nullable GlobalImageLoadListener var1) {
      this.mGlobalImageLoadListener = var1;
   }

   private void computeRequestHelper() {
      MultiSourceHelper.MultiSourceResult var1 = MultiSourceHelper.getBestSourceForSize(Math.round(this.getRight() - this.getLeft()), Math.round(this.getBottom() - this.getTop()), this.mSources);
      ImageSource var3 = var1.getBestResult();
      ImageSource var4 = var1.getBestResultInCache();
      ImageRequest var2 = null;
      if(var3 == null) {
         this.mRequestHelper = null;
      } else {
         ResizeOptions var6;
         if(shouldResize(var3)) {
            var6 = new ResizeOptions((int)(this.getRight() - this.getLeft()), (int)(this.getBottom() - this.getTop()));
         } else {
            var6 = null;
         }

         ImageRequest var5 = ImageRequestBuilder.newBuilderWithSource(var3.getUri()).setResizeOptions(var6).setProgressiveRenderingEnabled(this.mProgressiveRenderingEnabled).build();
         if(this.mGlobalImageLoadListener != null) {
            this.mGlobalImageLoadListener.onLoadAttempt(var3.getUri());
         }

         if(var4 != null) {
            var2 = ImageRequestBuilder.newBuilderWithSource(var4.getUri()).setResizeOptions(var6).setProgressiveRenderingEnabled(this.mProgressiveRenderingEnabled).build();
         }

         this.mRequestHelper = new DraweeRequestHelper((ImageRequest)Assertions.assertNotNull(var5), var2, this);
      }
   }

   private boolean shouldDisplayBorder() {
      return this.mBorderColor != 0 || this.mBorderRadius >= 0.5F;
   }

   private static boolean shouldResize(ImageSource var0) {
      Uri var1 = var0.getUri();
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = var1.getScheme();
      }

      return "file".equals(var2) || "content".equals(var2);
   }

   public int getBorderColor() {
      return this.mBorderColor;
   }

   public float getBorderRadius() {
      return this.mBorderRadius;
   }

   public float getBorderWidth() {
      return this.mBorderWidth;
   }

   public ScalingUtils.ScaleType getScaleType() {
      return this.mScaleType;
   }

   public boolean hasImageRequest() {
      return this.mSources.isEmpty() ^ true;
   }

   public void onAttached(FlatViewGroup.InvalidateCallback var1) {
      this.mCallback = var1;
      if(this.mRequestHelper == null) {
         StringBuilder var5 = new StringBuilder();
         var5.append("No DraweeRequestHelper - width: ");
         var5.append(this.getRight() - this.getLeft());
         var5.append(" - height: ");
         var5.append(this.getBottom() - this.getTop());
         var5.append(" - number of sources: ");
         var5.append(this.mSources.size());
         throw new RuntimeException(var5.toString());
      } else {
         GenericDraweeHierarchy var4 = this.mRequestHelper.getHierarchy();
         RoundingParams var3 = var4.getRoundingParams();
         if(this.shouldDisplayBorder()) {
            RoundingParams var2 = var3;
            if(var3 == null) {
               var2 = new RoundingParams();
            }

            var2.setBorder(this.mBorderColor, this.mBorderWidth);
            var2.setCornersRadius(this.mBorderRadius);
            var4.setRoundingParams(var2);
         } else if(var3 != null) {
            var4.setRoundingParams((RoundingParams)null);
         }

         var4.setActualImageScaleType(this.mScaleType);
         var4.setActualImageColorFilter(this.mColorFilter);
         var4.setFadeDuration(this.mFadeDuration);
         var4.getTopLevelDrawable().setBounds(Math.round(this.getLeft()), Math.round(this.getTop()), Math.round(this.getRight()), Math.round(this.getBottom()));
         this.mRequestHelper.attach(var1);
      }
   }

   protected void onBoundsChanged() {
      super.onBoundsChanged();
      this.computeRequestHelper();
   }

   protected void onDebugDrawHighlight(Canvas var1) {
      if(this.mCallback != null) {
         this.debugDrawCautionHighlight(var1, "Invalidate Drawee");
      }

   }

   public void onDetached() {
      if(this.mRequestHelper != null) {
         this.mRequestHelper.detach();
      }

   }

   public void onDraw(Canvas var1) {
      if(this.mRequestHelper != null) {
         this.mRequestHelper.getDrawable().draw(var1);
      }

   }

   public void onFailure(String var1, Throwable var2) {
      if(this.mCallback != null && this.mReactTag != 0) {
         this.mCallback.dispatchImageLoadEvent(this.mReactTag, 1);
         this.mCallback.dispatchImageLoadEvent(this.mReactTag, 3);
      }

   }

   public void onFinalImageSet(String var1, @Nullable Object var2, @Nullable Animatable var3) {
      if(this.mCallback != null && this.mReactTag != 0) {
         this.mCallback.dispatchImageLoadEvent(this.mReactTag, 2);
         this.mCallback.dispatchImageLoadEvent(this.mReactTag, 3);
      }

   }

   public void onIntermediateImageFailed(String var1, Throwable var2) {}

   public void onIntermediateImageSet(String var1, @Nullable Object var2) {}

   public void onRelease(String var1) {}

   public void onSubmit(String var1, Object var2) {
      if(this.mCallback != null && this.mReactTag != 0) {
         this.mCallback.dispatchImageLoadEvent(this.mReactTag, 4);
      }

   }

   public void setBorderColor(int var1) {
      this.mBorderColor = var1;
   }

   public void setBorderRadius(float var1) {
      this.mBorderRadius = var1;
   }

   public void setBorderWidth(float var1) {
      this.mBorderWidth = var1;
   }

   public void setFadeDuration(int var1) {
      this.mFadeDuration = var1;
   }

   public void setProgressiveRenderingEnabled(boolean var1) {
      this.mProgressiveRenderingEnabled = var1;
   }

   public void setReactTag(int var1) {
      this.mReactTag = var1;
   }

   public void setScaleType(ScalingUtils.ScaleType var1) {
      this.mScaleType = var1;
   }

   public void setSource(Context var1, @Nullable ReadableArray var2) {
      this.mSources.clear();
      if(var2 != null && var2.size() != 0) {
         int var4 = var2.size();
         int var3 = 0;
         if(var4 == 1) {
            ReadableMap var6 = var2.getMap(0);
            this.mSources.add(new ImageSource(var1, var6.getString("uri")));
            return;
         }

         while(var3 < var2.size()) {
            ReadableMap var5 = var2.getMap(var3);
            this.mSources.add(new ImageSource(var1, var5.getString("uri"), var5.getDouble("width"), var5.getDouble("height")));
            ++var3;
         }
      }

   }

   public void setTintColor(int var1) {
      if(var1 == 0) {
         this.mColorFilter = null;
      } else {
         this.mColorFilter = new PorterDuffColorFilter(var1, Mode.SRC_ATOP);
      }
   }
}
