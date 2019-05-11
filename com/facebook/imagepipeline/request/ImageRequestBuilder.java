package com.facebook.imagepipeline.request;

import android.net.Uri;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.util.UriUtil;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.MediaVariations;
import com.facebook.imagepipeline.request.Postprocessor;
import javax.annotation.Nullable;

public class ImageRequestBuilder {

   private ImageRequest.CacheChoice mCacheChoice;
   private boolean mDiskCacheEnabled;
   private ImageDecodeOptions mImageDecodeOptions;
   private boolean mLocalThumbnailPreviewsEnabled;
   private ImageRequest.RequestLevel mLowestPermittedRequestLevel;
   @Nullable
   private MediaVariations mMediaVariations;
   @Nullable
   private Postprocessor mPostprocessor;
   private boolean mProgressiveRenderingEnabled;
   @Nullable
   private RequestListener mRequestListener;
   private Priority mRequestPriority;
   @Nullable
   private ResizeOptions mResizeOptions;
   @Nullable
   private RotationOptions mRotationOptions;
   private Uri mSourceUri = null;


   private ImageRequestBuilder() {
      this.mLowestPermittedRequestLevel = ImageRequest.RequestLevel.FULL_FETCH;
      this.mResizeOptions = null;
      this.mRotationOptions = null;
      this.mImageDecodeOptions = ImageDecodeOptions.defaults();
      this.mCacheChoice = ImageRequest.CacheChoice.DEFAULT;
      this.mProgressiveRenderingEnabled = ImagePipelineConfig.getDefaultImageRequestConfig().isProgressiveRenderingEnabled();
      this.mLocalThumbnailPreviewsEnabled = false;
      this.mRequestPriority = Priority.HIGH;
      this.mPostprocessor = null;
      this.mDiskCacheEnabled = true;
      this.mMediaVariations = null;
   }

   public static ImageRequestBuilder fromRequest(ImageRequest var0) {
      return newBuilderWithSource(var0.getSourceUri()).setImageDecodeOptions(var0.getImageDecodeOptions()).setCacheChoice(var0.getCacheChoice()).setLocalThumbnailPreviewsEnabled(var0.getLocalThumbnailPreviewsEnabled()).setLowestPermittedRequestLevel(var0.getLowestPermittedRequestLevel()).setMediaVariations(var0.getMediaVariations()).setPostprocessor(var0.getPostprocessor()).setProgressiveRenderingEnabled(var0.getProgressiveRenderingEnabled()).setRequestPriority(var0.getPriority()).setResizeOptions(var0.getResizeOptions()).setRequestListener(var0.getRequestListener()).setRotationOptions(var0.getRotationOptions());
   }

   public static ImageRequestBuilder newBuilderWithResourceId(int var0) {
      return newBuilderWithSource(UriUtil.getUriForResourceId(var0));
   }

   public static ImageRequestBuilder newBuilderWithSource(Uri var0) {
      return (new ImageRequestBuilder()).setSource(var0);
   }

   public ImageRequest build() {
      this.validate();
      return new ImageRequest(this);
   }

   public ImageRequestBuilder disableDiskCache() {
      this.mDiskCacheEnabled = false;
      return this;
   }

   public ImageRequest.CacheChoice getCacheChoice() {
      return this.mCacheChoice;
   }

   public ImageDecodeOptions getImageDecodeOptions() {
      return this.mImageDecodeOptions;
   }

   public ImageRequest.RequestLevel getLowestPermittedRequestLevel() {
      return this.mLowestPermittedRequestLevel;
   }

   @Nullable
   public MediaVariations getMediaVariations() {
      return this.mMediaVariations;
   }

   @Nullable
   public Postprocessor getPostprocessor() {
      return this.mPostprocessor;
   }

   @Nullable
   public RequestListener getRequestListener() {
      return this.mRequestListener;
   }

   public Priority getRequestPriority() {
      return this.mRequestPriority;
   }

   @Nullable
   public ResizeOptions getResizeOptions() {
      return this.mResizeOptions;
   }

   @Nullable
   public RotationOptions getRotationOptions() {
      return this.mRotationOptions;
   }

   public Uri getSourceUri() {
      return this.mSourceUri;
   }

   public boolean isDiskCacheEnabled() {
      return this.mDiskCacheEnabled && UriUtil.isNetworkUri(this.mSourceUri);
   }

   public boolean isLocalThumbnailPreviewsEnabled() {
      return this.mLocalThumbnailPreviewsEnabled;
   }

   public boolean isProgressiveRenderingEnabled() {
      return this.mProgressiveRenderingEnabled;
   }

   @Deprecated
   public ImageRequestBuilder setAutoRotateEnabled(boolean var1) {
      return var1?this.setRotationOptions(RotationOptions.autoRotate()):this.setRotationOptions(RotationOptions.disableRotation());
   }

   public ImageRequestBuilder setCacheChoice(ImageRequest.CacheChoice var1) {
      this.mCacheChoice = var1;
      return this;
   }

   public ImageRequestBuilder setImageDecodeOptions(ImageDecodeOptions var1) {
      this.mImageDecodeOptions = var1;
      return this;
   }

   public ImageRequestBuilder setLocalThumbnailPreviewsEnabled(boolean var1) {
      this.mLocalThumbnailPreviewsEnabled = var1;
      return this;
   }

   public ImageRequestBuilder setLowestPermittedRequestLevel(ImageRequest.RequestLevel var1) {
      this.mLowestPermittedRequestLevel = var1;
      return this;
   }

   public ImageRequestBuilder setMediaVariations(MediaVariations var1) {
      this.mMediaVariations = var1;
      return this;
   }

   public ImageRequestBuilder setMediaVariationsForMediaId(String var1) {
      return this.setMediaVariations(MediaVariations.forMediaId(var1));
   }

   public ImageRequestBuilder setPostprocessor(Postprocessor var1) {
      this.mPostprocessor = var1;
      return this;
   }

   public ImageRequestBuilder setProgressiveRenderingEnabled(boolean var1) {
      this.mProgressiveRenderingEnabled = var1;
      return this;
   }

   public ImageRequestBuilder setRequestListener(RequestListener var1) {
      this.mRequestListener = var1;
      return this;
   }

   public ImageRequestBuilder setRequestPriority(Priority var1) {
      this.mRequestPriority = var1;
      return this;
   }

   public ImageRequestBuilder setResizeOptions(@Nullable ResizeOptions var1) {
      this.mResizeOptions = var1;
      return this;
   }

   public ImageRequestBuilder setRotationOptions(@Nullable RotationOptions var1) {
      this.mRotationOptions = var1;
      return this;
   }

   public ImageRequestBuilder setSource(Uri var1) {
      Preconditions.checkNotNull(var1);
      this.mSourceUri = var1;
      return this;
   }

   protected void validate() {
      if(this.mSourceUri == null) {
         throw new ImageRequestBuilder.BuilderException("Source must be set!");
      } else {
         if(UriUtil.isLocalResourceUri(this.mSourceUri)) {
            if(!this.mSourceUri.isAbsolute()) {
               throw new ImageRequestBuilder.BuilderException("Resource URI path must be absolute.");
            }

            if(this.mSourceUri.getPath().isEmpty()) {
               throw new ImageRequestBuilder.BuilderException("Resource URI must not be empty");
            }

            try {
               Integer.parseInt(this.mSourceUri.getPath().substring(1));
            } catch (NumberFormatException var2) {
               throw new ImageRequestBuilder.BuilderException("Resource URI path must be a resource id.");
            }
         }

         if(UriUtil.isLocalAssetUri(this.mSourceUri) && !this.mSourceUri.isAbsolute()) {
            throw new ImageRequestBuilder.BuilderException("Asset URI path must be absolute.");
         }
      }
   }

   public static class BuilderException extends RuntimeException {

      public BuilderException(String var1) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Invalid request builder: ");
         var2.append(var1);
         super(var2.toString());
      }
   }
}
