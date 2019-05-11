package com.facebook.imagepipeline.core;

import android.net.Uri;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.webp.WebpSupportStatus;
import com.facebook.imagepipeline.core.ProducerFactory;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.AddImageTransformMetaDataProducer;
import com.facebook.imagepipeline.producers.BitmapMemoryCacheKeyMultiplexProducer;
import com.facebook.imagepipeline.producers.BitmapMemoryCacheProducer;
import com.facebook.imagepipeline.producers.DiskCacheWriteProducer;
import com.facebook.imagepipeline.producers.EncodedMemoryCacheProducer;
import com.facebook.imagepipeline.producers.MediaVariationsFallbackProducer;
import com.facebook.imagepipeline.producers.NetworkFetcher;
import com.facebook.imagepipeline.producers.PostprocessedBitmapMemoryCacheProducer;
import com.facebook.imagepipeline.producers.PostprocessorProducer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ResizeAndRotateProducer;
import com.facebook.imagepipeline.producers.SwallowResultProducer;
import com.facebook.imagepipeline.producers.ThreadHandoffProducer;
import com.facebook.imagepipeline.producers.ThreadHandoffProducerQueue;
import com.facebook.imagepipeline.producers.ThrottlingProducer;
import com.facebook.imagepipeline.producers.ThumbnailBranchProducer;
import com.facebook.imagepipeline.producers.ThumbnailProducer;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.HashMap;
import java.util.Map;

public class ProducerSequenceFactory {

   @VisibleForTesting
   Producer<EncodedImage> mBackgroundLocalFileFetchToEncodedMemorySequence;
   @VisibleForTesting
   Producer<EncodedImage> mBackgroundNetworkFetchToEncodedMemorySequence;
   @VisibleForTesting
   Map<Producer<CloseableReference<CloseableImage>>, Producer<Void>> mCloseableImagePrefetchSequences;
   private Producer<EncodedImage> mCommonNetworkFetchToEncodedMemorySequence;
   @VisibleForTesting
   Producer<CloseableReference<CloseableImage>> mDataFetchSequence;
   @VisibleForTesting
   Producer<CloseableReference<CloseableImage>> mLocalAssetFetchSequence;
   @VisibleForTesting
   Producer<CloseableReference<CloseableImage>> mLocalContentUriFetchSequence;
   @VisibleForTesting
   Producer<CloseableReference<PooledByteBuffer>> mLocalFileEncodedImageProducerSequence;
   @VisibleForTesting
   Producer<Void> mLocalFileFetchToEncodedMemoryPrefetchSequence;
   @VisibleForTesting
   Producer<CloseableReference<CloseableImage>> mLocalImageFileFetchSequence;
   @VisibleForTesting
   Producer<CloseableReference<CloseableImage>> mLocalResourceFetchSequence;
   @VisibleForTesting
   Producer<CloseableReference<CloseableImage>> mLocalVideoFileFetchSequence;
   @VisibleForTesting
   Producer<CloseableReference<PooledByteBuffer>> mNetworkEncodedImageProducerSequence;
   @VisibleForTesting
   Producer<CloseableReference<CloseableImage>> mNetworkFetchSequence;
   @VisibleForTesting
   Producer<Void> mNetworkFetchToEncodedMemoryPrefetchSequence;
   private final NetworkFetcher mNetworkFetcher;
   @VisibleForTesting
   Map<Producer<CloseableReference<CloseableImage>>, Producer<CloseableReference<CloseableImage>>> mPostprocessorSequences;
   private final ProducerFactory mProducerFactory;
   @VisibleForTesting
   Producer<CloseableReference<CloseableImage>> mQualifiedResourceFetchSequence;
   private final boolean mResizeAndRotateEnabledForNetwork;
   private final ThreadHandoffProducerQueue mThreadHandoffProducerQueue;
   private final boolean mUseDownsamplingRatio;
   private final boolean mWebpSupportEnabled;


   public ProducerSequenceFactory(ProducerFactory var1, NetworkFetcher var2, boolean var3, boolean var4, ThreadHandoffProducerQueue var5, boolean var6) {
      this.mProducerFactory = var1;
      this.mNetworkFetcher = var2;
      this.mResizeAndRotateEnabledForNetwork = var3;
      this.mWebpSupportEnabled = var4;
      this.mPostprocessorSequences = new HashMap();
      this.mCloseableImagePrefetchSequences = new HashMap();
      this.mThreadHandoffProducerQueue = var5;
      this.mUseDownsamplingRatio = var6;
   }

   private Producer<EncodedImage> getBackgroundLocalFileFetchToEncodeMemorySequence() {
      synchronized(this){}

      Producer var1;
      try {
         if(this.mBackgroundLocalFileFetchToEncodedMemorySequence == null) {
            var1 = this.newEncodedCacheMultiplexToTranscodeSequence(this.mProducerFactory.newLocalFileFetchProducer());
            this.mBackgroundLocalFileFetchToEncodedMemorySequence = this.mProducerFactory.newBackgroundThreadHandoffProducer(var1, this.mThreadHandoffProducerQueue);
         }

         var1 = this.mBackgroundLocalFileFetchToEncodedMemorySequence;
      } finally {
         ;
      }

      return var1;
   }

   private Producer<EncodedImage> getBackgroundNetworkFetchToEncodedMemorySequence() {
      synchronized(this){}

      Producer var1;
      try {
         if(this.mBackgroundNetworkFetchToEncodedMemorySequence == null) {
            this.mBackgroundNetworkFetchToEncodedMemorySequence = this.mProducerFactory.newBackgroundThreadHandoffProducer(this.getCommonNetworkFetchToEncodedMemorySequence(), this.mThreadHandoffProducerQueue);
         }

         var1 = this.mBackgroundNetworkFetchToEncodedMemorySequence;
      } finally {
         ;
      }

      return var1;
   }

   private Producer<CloseableReference<CloseableImage>> getBasicDecodedImageSequence(ImageRequest var1) {
      Preconditions.checkNotNull(var1);
      Uri var3 = var1.getSourceUri();
      Preconditions.checkNotNull(var3, "Uri is null.");
      int var2 = var1.getSourceUriType();
      if(var2 != 0) {
         switch(var2) {
         case 2:
            return this.getLocalVideoFileFetchSequence();
         case 3:
            return this.getLocalImageFileFetchSequence();
         case 4:
            return this.getLocalContentUriFetchSequence();
         case 5:
            return this.getLocalAssetFetchSequence();
         case 6:
            return this.getLocalResourceFetchSequence();
         case 7:
            return this.getDataFetchSequence();
         case 8:
            return this.getQualifiedResourceFetchSequence();
         default:
            StringBuilder var4 = new StringBuilder();
            var4.append("Unsupported uri scheme! Uri is: ");
            var4.append(getShortenedUriString(var3));
            throw new IllegalArgumentException(var4.toString());
         }
      } else {
         return this.getNetworkFetchSequence();
      }
   }

   private Producer<EncodedImage> getCommonNetworkFetchToEncodedMemorySequence() {
      synchronized(this){}

      Producer var1;
      try {
         if(this.mCommonNetworkFetchToEncodedMemorySequence == null) {
            this.mCommonNetworkFetchToEncodedMemorySequence = ProducerFactory.newAddImageTransformMetaDataProducer(this.newEncodedCacheMultiplexToTranscodeSequence(this.mProducerFactory.newNetworkFetchProducer(this.mNetworkFetcher)));
            this.mCommonNetworkFetchToEncodedMemorySequence = this.mProducerFactory.newResizeAndRotateProducer(this.mCommonNetworkFetchToEncodedMemorySequence, this.mResizeAndRotateEnabledForNetwork, this.mUseDownsamplingRatio);
         }

         var1 = this.mCommonNetworkFetchToEncodedMemorySequence;
      } finally {
         ;
      }

      return var1;
   }

   private Producer<CloseableReference<CloseableImage>> getDataFetchSequence() {
      // $FF: Couldn't be decompiled
   }

   private Producer<Void> getDecodedImagePrefetchSequence(Producer<CloseableReference<CloseableImage>> var1) {
      synchronized(this){}

      try {
         if(!this.mCloseableImagePrefetchSequences.containsKey(var1)) {
            ProducerFactory var2 = this.mProducerFactory;
            SwallowResultProducer var5 = ProducerFactory.newSwallowResultProducer(var1);
            this.mCloseableImagePrefetchSequences.put(var1, var5);
         }

         var1 = (Producer)this.mCloseableImagePrefetchSequences.get(var1);
      } finally {
         ;
      }

      return var1;
   }

   private Producer<CloseableReference<CloseableImage>> getLocalAssetFetchSequence() {
      synchronized(this){}

      Producer var1;
      try {
         if(this.mLocalAssetFetchSequence == null) {
            this.mLocalAssetFetchSequence = this.newBitmapCacheGetToLocalTransformSequence(this.mProducerFactory.newLocalAssetFetchProducer());
         }

         var1 = this.mLocalAssetFetchSequence;
      } finally {
         ;
      }

      return var1;
   }

   private Producer<CloseableReference<CloseableImage>> getLocalContentUriFetchSequence() {
      synchronized(this){}

      Producer var1;
      try {
         if(this.mLocalContentUriFetchSequence == null) {
            this.mLocalContentUriFetchSequence = this.newBitmapCacheGetToLocalTransformSequence(this.mProducerFactory.newLocalContentUriFetchProducer(), new ThumbnailProducer[]{this.mProducerFactory.newLocalContentUriThumbnailFetchProducer(), this.mProducerFactory.newLocalExifThumbnailProducer()});
         }

         var1 = this.mLocalContentUriFetchSequence;
      } finally {
         ;
      }

      return var1;
   }

   private Producer<Void> getLocalFileFetchToEncodedMemoryPrefetchSequence() {
      synchronized(this){}

      Producer var1;
      try {
         if(this.mLocalFileFetchToEncodedMemoryPrefetchSequence == null) {
            this.mLocalFileFetchToEncodedMemoryPrefetchSequence = ProducerFactory.newSwallowResultProducer(this.getBackgroundLocalFileFetchToEncodeMemorySequence());
         }

         var1 = this.mLocalFileFetchToEncodedMemoryPrefetchSequence;
      } finally {
         ;
      }

      return var1;
   }

   private Producer<CloseableReference<CloseableImage>> getLocalImageFileFetchSequence() {
      synchronized(this){}

      Producer var1;
      try {
         if(this.mLocalImageFileFetchSequence == null) {
            this.mLocalImageFileFetchSequence = this.newBitmapCacheGetToLocalTransformSequence(this.mProducerFactory.newLocalFileFetchProducer());
         }

         var1 = this.mLocalImageFileFetchSequence;
      } finally {
         ;
      }

      return var1;
   }

   private Producer<CloseableReference<CloseableImage>> getLocalResourceFetchSequence() {
      synchronized(this){}

      Producer var1;
      try {
         if(this.mLocalResourceFetchSequence == null) {
            this.mLocalResourceFetchSequence = this.newBitmapCacheGetToLocalTransformSequence(this.mProducerFactory.newLocalResourceFetchProducer());
         }

         var1 = this.mLocalResourceFetchSequence;
      } finally {
         ;
      }

      return var1;
   }

   private Producer<CloseableReference<CloseableImage>> getLocalVideoFileFetchSequence() {
      synchronized(this){}

      Producer var1;
      try {
         if(this.mLocalVideoFileFetchSequence == null) {
            this.mLocalVideoFileFetchSequence = this.newBitmapCacheGetToBitmapCacheSequence(this.mProducerFactory.newLocalVideoThumbnailProducer());
         }

         var1 = this.mLocalVideoFileFetchSequence;
      } finally {
         ;
      }

      return var1;
   }

   private Producer<CloseableReference<CloseableImage>> getNetworkFetchSequence() {
      synchronized(this){}

      Producer var1;
      try {
         if(this.mNetworkFetchSequence == null) {
            this.mNetworkFetchSequence = this.newBitmapCacheGetToDecodeSequence(this.getCommonNetworkFetchToEncodedMemorySequence());
         }

         var1 = this.mNetworkFetchSequence;
      } finally {
         ;
      }

      return var1;
   }

   private Producer<Void> getNetworkFetchToEncodedMemoryPrefetchSequence() {
      synchronized(this){}

      Producer var1;
      try {
         if(this.mNetworkFetchToEncodedMemoryPrefetchSequence == null) {
            this.mNetworkFetchToEncodedMemoryPrefetchSequence = ProducerFactory.newSwallowResultProducer(this.getBackgroundNetworkFetchToEncodedMemorySequence());
         }

         var1 = this.mNetworkFetchToEncodedMemoryPrefetchSequence;
      } finally {
         ;
      }

      return var1;
   }

   private Producer<CloseableReference<CloseableImage>> getPostprocessorSequence(Producer<CloseableReference<CloseableImage>> var1) {
      synchronized(this){}

      try {
         if(!this.mPostprocessorSequences.containsKey(var1)) {
            PostprocessorProducer var2 = this.mProducerFactory.newPostprocessorProducer(var1);
            PostprocessedBitmapMemoryCacheProducer var5 = this.mProducerFactory.newPostprocessorBitmapMemoryCacheProducer(var2);
            this.mPostprocessorSequences.put(var1, var5);
         }

         var1 = (Producer)this.mPostprocessorSequences.get(var1);
      } finally {
         ;
      }

      return var1;
   }

   private Producer<CloseableReference<CloseableImage>> getQualifiedResourceFetchSequence() {
      synchronized(this){}

      Producer var1;
      try {
         if(this.mQualifiedResourceFetchSequence == null) {
            this.mQualifiedResourceFetchSequence = this.newBitmapCacheGetToLocalTransformSequence(this.mProducerFactory.newQualifiedResourceFetchProducer());
         }

         var1 = this.mQualifiedResourceFetchSequence;
      } finally {
         ;
      }

      return var1;
   }

   private static String getShortenedUriString(Uri var0) {
      String var1 = String.valueOf(var0);
      String var2 = var1;
      if(var1.length() > 30) {
         StringBuilder var3 = new StringBuilder();
         var3.append(var1.substring(0, 30));
         var3.append("...");
         var2 = var3.toString();
      }

      return var2;
   }

   private Producer<CloseableReference<CloseableImage>> newBitmapCacheGetToBitmapCacheSequence(Producer<CloseableReference<CloseableImage>> var1) {
      BitmapMemoryCacheProducer var2 = this.mProducerFactory.newBitmapMemoryCacheProducer(var1);
      BitmapMemoryCacheKeyMultiplexProducer var3 = this.mProducerFactory.newBitmapMemoryCacheKeyMultiplexProducer(var2);
      ThreadHandoffProducer var4 = this.mProducerFactory.newBackgroundThreadHandoffProducer(var3, this.mThreadHandoffProducerQueue);
      return this.mProducerFactory.newBitmapMemoryCacheGetProducer(var4);
   }

   private Producer<CloseableReference<CloseableImage>> newBitmapCacheGetToDecodeSequence(Producer<EncodedImage> var1) {
      return this.newBitmapCacheGetToBitmapCacheSequence(this.mProducerFactory.newDecodeProducer(var1));
   }

   private Producer<CloseableReference<CloseableImage>> newBitmapCacheGetToLocalTransformSequence(Producer<EncodedImage> var1) {
      return this.newBitmapCacheGetToLocalTransformSequence(var1, new ThumbnailProducer[]{this.mProducerFactory.newLocalExifThumbnailProducer()});
   }

   private Producer<CloseableReference<CloseableImage>> newBitmapCacheGetToLocalTransformSequence(Producer<EncodedImage> var1, ThumbnailProducer<EncodedImage>[] var2) {
      return this.newBitmapCacheGetToDecodeSequence(this.newLocalTransformationsSequence(this.newEncodedCacheMultiplexToTranscodeSequence(var1), var2));
   }

   private Producer<EncodedImage> newDiskCacheSequence(Producer<EncodedImage> var1) {
      DiskCacheWriteProducer var2 = this.mProducerFactory.newDiskCacheWriteProducer(var1);
      MediaVariationsFallbackProducer var3 = this.mProducerFactory.newMediaVariationsProducer(var2);
      return this.mProducerFactory.newDiskCacheReadProducer(var3);
   }

   private Producer<EncodedImage> newEncodedCacheMultiplexToTranscodeSequence(Producer<EncodedImage> var1) {
      Object var2 = var1;
      if(WebpSupportStatus.sIsWebpSupportRequired) {
         label12: {
            if(this.mWebpSupportEnabled) {
               var2 = var1;
               if(WebpSupportStatus.sWebpBitmapFactory != null) {
                  break label12;
               }
            }

            var2 = this.mProducerFactory.newWebpTranscodeProducer(var1);
         }
      }

      var1 = this.newDiskCacheSequence((Producer)var2);
      EncodedMemoryCacheProducer var3 = this.mProducerFactory.newEncodedMemoryCacheProducer(var1);
      return this.mProducerFactory.newEncodedCacheKeyMultiplexProducer(var3);
   }

   private Producer<EncodedImage> newLocalThumbnailProducer(ThumbnailProducer<EncodedImage>[] var1) {
      ThumbnailBranchProducer var2 = this.mProducerFactory.newThumbnailBranchProducer(var1);
      return this.mProducerFactory.newResizeAndRotateProducer(var2, true, this.mUseDownsamplingRatio);
   }

   private Producer<EncodedImage> newLocalTransformationsSequence(Producer<EncodedImage> var1, ThumbnailProducer<EncodedImage>[] var2) {
      AddImageTransformMetaDataProducer var4 = ProducerFactory.newAddImageTransformMetaDataProducer(var1);
      ResizeAndRotateProducer var5 = this.mProducerFactory.newResizeAndRotateProducer(var4, true, this.mUseDownsamplingRatio);
      ThrottlingProducer var6 = this.mProducerFactory.newThrottlingProducer(var5);
      ProducerFactory var3 = this.mProducerFactory;
      return ProducerFactory.newBranchOnSeparateImagesProducer(this.newLocalThumbnailProducer(var2), var6);
   }

   private static void validateEncodedImageRequest(ImageRequest var0) {
      Preconditions.checkNotNull(var0);
      boolean var1;
      if(var0.getLowestPermittedRequestLevel().getValue() <= ImageRequest.RequestLevel.ENCODED_MEMORY_CACHE.getValue()) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkArgument(var1);
   }

   public Producer<Void> getDecodedImagePrefetchProducerSequence(ImageRequest var1) {
      return this.getDecodedImagePrefetchSequence(this.getBasicDecodedImageSequence(var1));
   }

   public Producer<CloseableReference<CloseableImage>> getDecodedImageProducerSequence(ImageRequest var1) {
      Producer var2 = this.getBasicDecodedImageSequence(var1);
      return var1.getPostprocessor() != null?this.getPostprocessorSequence(var2):var2;
   }

   public Producer<Void> getEncodedImagePrefetchProducerSequence(ImageRequest var1) {
      validateEncodedImageRequest(var1);
      int var2 = var1.getSourceUriType();
      if(var2 != 0) {
         switch(var2) {
         case 2:
         case 3:
            return this.getLocalFileFetchToEncodedMemoryPrefetchSequence();
         default:
            Uri var4 = var1.getSourceUri();
            StringBuilder var3 = new StringBuilder();
            var3.append("Unsupported uri scheme for encoded image fetch! Uri is: ");
            var3.append(getShortenedUriString(var4));
            throw new IllegalArgumentException(var3.toString());
         }
      } else {
         return this.getNetworkFetchToEncodedMemoryPrefetchSequence();
      }
   }

   public Producer<CloseableReference<PooledByteBuffer>> getEncodedImageProducerSequence(ImageRequest var1) {
      validateEncodedImageRequest(var1);
      Uri var3 = var1.getSourceUri();
      int var2 = var1.getSourceUriType();
      if(var2 != 0) {
         switch(var2) {
         case 2:
         case 3:
            return this.getLocalFileFetchEncodedImageProducerSequence();
         default:
            StringBuilder var4 = new StringBuilder();
            var4.append("Unsupported uri scheme for encoded image fetch! Uri is: ");
            var4.append(getShortenedUriString(var3));
            throw new IllegalArgumentException(var4.toString());
         }
      } else {
         return this.getNetworkFetchEncodedImageProducerSequence();
      }
   }

   public Producer<CloseableReference<PooledByteBuffer>> getLocalFileFetchEncodedImageProducerSequence() {
      // $FF: Couldn't be decompiled
   }

   public Producer<CloseableReference<PooledByteBuffer>> getNetworkFetchEncodedImageProducerSequence() {
      // $FF: Couldn't be decompiled
   }
}
