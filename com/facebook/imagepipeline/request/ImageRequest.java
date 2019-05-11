package com.facebook.imagepipeline.request;

import android.net.Uri;
import com.facebook.common.internal.Objects;
import com.facebook.common.media.MediaUtils;
import com.facebook.common.util.UriUtil;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.MediaVariations;
import com.facebook.imagepipeline.request.Postprocessor;
import java.io.File;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
public class ImageRequest {

   private final ImageRequest.CacheChoice mCacheChoice;
   private final ImageDecodeOptions mImageDecodeOptions;
   private final boolean mIsDiskCacheEnabled;
   private final boolean mLocalThumbnailPreviewsEnabled;
   private final ImageRequest.RequestLevel mLowestPermittedRequestLevel;
   @Nullable
   private final MediaVariations mMediaVariations;
   private final Postprocessor mPostprocessor;
   private final boolean mProgressiveRenderingEnabled;
   @Nullable
   private final RequestListener mRequestListener;
   private final Priority mRequestPriority;
   @Nullable
   private final ResizeOptions mResizeOptions;
   private final RotationOptions mRotationOptions;
   private File mSourceFile;
   private final Uri mSourceUri;
   private final int mSourceUriType;


   protected ImageRequest(ImageRequestBuilder var1) {
      this.mCacheChoice = var1.getCacheChoice();
      this.mSourceUri = var1.getSourceUri();
      this.mSourceUriType = getSourceUriType(this.mSourceUri);
      this.mMediaVariations = var1.getMediaVariations();
      this.mProgressiveRenderingEnabled = var1.isProgressiveRenderingEnabled();
      this.mLocalThumbnailPreviewsEnabled = var1.isLocalThumbnailPreviewsEnabled();
      this.mImageDecodeOptions = var1.getImageDecodeOptions();
      this.mResizeOptions = var1.getResizeOptions();
      RotationOptions var2;
      if(var1.getRotationOptions() == null) {
         var2 = RotationOptions.autoRotate();
      } else {
         var2 = var1.getRotationOptions();
      }

      this.mRotationOptions = var2;
      this.mRequestPriority = var1.getRequestPriority();
      this.mLowestPermittedRequestLevel = var1.getLowestPermittedRequestLevel();
      this.mIsDiskCacheEnabled = var1.isDiskCacheEnabled();
      this.mPostprocessor = var1.getPostprocessor();
      this.mRequestListener = var1.getRequestListener();
   }

   public static ImageRequest fromFile(@Nullable File var0) {
      return var0 == null?null:fromUri(UriUtil.getUriForFile(var0));
   }

   public static ImageRequest fromUri(@Nullable Uri var0) {
      return var0 == null?null:ImageRequestBuilder.newBuilderWithSource(var0).build();
   }

   public static ImageRequest fromUri(@Nullable String var0) {
      return var0 != null && var0.length() != 0?fromUri(Uri.parse(var0)):null;
   }

   private static int getSourceUriType(Uri var0) {
      return var0 == null?-1:(UriUtil.isNetworkUri(var0)?0:(UriUtil.isLocalFileUri(var0)?(MediaUtils.isVideo(MediaUtils.extractMime(var0.getPath()))?2:3):(UriUtil.isLocalContentUri(var0)?4:(UriUtil.isLocalAssetUri(var0)?5:(UriUtil.isLocalResourceUri(var0)?6:(UriUtil.isDataUri(var0)?7:(UriUtil.isQualifiedResourceUri(var0)?8:-1)))))));
   }

   public boolean equals(Object var1) {
      boolean var2 = var1 instanceof ImageRequest;
      boolean var3 = false;
      if(!var2) {
         return false;
      } else {
         ImageRequest var4 = (ImageRequest)var1;
         var2 = var3;
         if(Objects.equal(this.mSourceUri, var4.mSourceUri)) {
            var2 = var3;
            if(Objects.equal(this.mCacheChoice, var4.mCacheChoice)) {
               var2 = var3;
               if(Objects.equal(this.mMediaVariations, var4.mMediaVariations)) {
                  var2 = var3;
                  if(Objects.equal(this.mSourceFile, var4.mSourceFile)) {
                     var2 = true;
                  }
               }
            }
         }

         return var2;
      }
   }

   @Deprecated
   public boolean getAutoRotateEnabled() {
      return this.mRotationOptions.useImageMetadata();
   }

   public ImageRequest.CacheChoice getCacheChoice() {
      return this.mCacheChoice;
   }

   public ImageDecodeOptions getImageDecodeOptions() {
      return this.mImageDecodeOptions;
   }

   public boolean getLocalThumbnailPreviewsEnabled() {
      return this.mLocalThumbnailPreviewsEnabled;
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

   public int getPreferredHeight() {
      return this.mResizeOptions != null?this.mResizeOptions.height:2048;
   }

   public int getPreferredWidth() {
      return this.mResizeOptions != null?this.mResizeOptions.width:2048;
   }

   public Priority getPriority() {
      return this.mRequestPriority;
   }

   public boolean getProgressiveRenderingEnabled() {
      return this.mProgressiveRenderingEnabled;
   }

   @Nullable
   public RequestListener getRequestListener() {
      return this.mRequestListener;
   }

   @Nullable
   public ResizeOptions getResizeOptions() {
      return this.mResizeOptions;
   }

   public RotationOptions getRotationOptions() {
      return this.mRotationOptions;
   }

   public File getSourceFile() {
      synchronized(this){}

      File var1;
      try {
         if(this.mSourceFile == null) {
            this.mSourceFile = new File(this.mSourceUri.getPath());
         }

         var1 = this.mSourceFile;
      } finally {
         ;
      }

      return var1;
   }

   public Uri getSourceUri() {
      return this.mSourceUri;
   }

   public int getSourceUriType() {
      return this.mSourceUriType;
   }

   public int hashCode() {
      return Objects.hashCode(new Object[]{this.mCacheChoice, this.mSourceUri, this.mMediaVariations, this.mSourceFile});
   }

   public boolean isDiskCacheEnabled() {
      return this.mIsDiskCacheEnabled;
   }

   public String toString() {
      return Objects.toStringHelper((Object)this).add("uri", this.mSourceUri).add("cacheChoice", this.mCacheChoice).add("decodeOptions", this.mImageDecodeOptions).add("postprocessor", this.mPostprocessor).add("priority", this.mRequestPriority).add("resizeOptions", this.mResizeOptions).add("rotationOptions", this.mRotationOptions).add("mediaVariations", this.mMediaVariations).toString();
   }

   public static enum RequestLevel {

      // $FF: synthetic field
      private static final ImageRequest.RequestLevel[] $VALUES = new ImageRequest.RequestLevel[]{FULL_FETCH, DISK_CACHE, ENCODED_MEMORY_CACHE, BITMAP_MEMORY_CACHE};
      BITMAP_MEMORY_CACHE("BITMAP_MEMORY_CACHE", 3, 4),
      DISK_CACHE("DISK_CACHE", 1, 2),
      ENCODED_MEMORY_CACHE("ENCODED_MEMORY_CACHE", 2, 3),
      FULL_FETCH("FULL_FETCH", 0, 1);
      private int mValue;


      private RequestLevel(String var1, int var2, int var3) {
         this.mValue = var3;
      }

      public static ImageRequest.RequestLevel getMax(ImageRequest.RequestLevel var0, ImageRequest.RequestLevel var1) {
         return var0.getValue() > var1.getValue()?var0:var1;
      }

      public int getValue() {
         return this.mValue;
      }
   }

   public static enum CacheChoice {

      // $FF: synthetic field
      private static final ImageRequest.CacheChoice[] $VALUES = new ImageRequest.CacheChoice[]{SMALL, DEFAULT};
      DEFAULT("DEFAULT", 1),
      SMALL("SMALL", 0);


      private CacheChoice(String var1, int var2) {}
   }
}
