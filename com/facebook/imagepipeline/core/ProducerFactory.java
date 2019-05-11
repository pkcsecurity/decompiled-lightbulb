package com.facebook.imagepipeline.core;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.memory.ByteArrayPool;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.cache.BufferedDiskCache;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.cache.DiskCachePolicy;
import com.facebook.imagepipeline.cache.MediaIdExtractor;
import com.facebook.imagepipeline.cache.MediaVariationsIndex;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.cache.SmallCacheIfRequestedDiskCachePolicy;
import com.facebook.imagepipeline.cache.SplitCachesByImageSizeDiskCachePolicy;
import com.facebook.imagepipeline.core.ExecutorSupplier;
import com.facebook.imagepipeline.decoder.ImageDecoder;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.AddImageTransformMetaDataProducer;
import com.facebook.imagepipeline.producers.BitmapMemoryCacheGetProducer;
import com.facebook.imagepipeline.producers.BitmapMemoryCacheKeyMultiplexProducer;
import com.facebook.imagepipeline.producers.BitmapMemoryCacheProducer;
import com.facebook.imagepipeline.producers.BranchOnSeparateImagesProducer;
import com.facebook.imagepipeline.producers.DataFetchProducer;
import com.facebook.imagepipeline.producers.DecodeProducer;
import com.facebook.imagepipeline.producers.DiskCacheReadProducer;
import com.facebook.imagepipeline.producers.DiskCacheWriteProducer;
import com.facebook.imagepipeline.producers.EncodedCacheKeyMultiplexProducer;
import com.facebook.imagepipeline.producers.EncodedMemoryCacheProducer;
import com.facebook.imagepipeline.producers.LocalAssetFetchProducer;
import com.facebook.imagepipeline.producers.LocalContentUriFetchProducer;
import com.facebook.imagepipeline.producers.LocalContentUriThumbnailFetchProducer;
import com.facebook.imagepipeline.producers.LocalExifThumbnailProducer;
import com.facebook.imagepipeline.producers.LocalFileFetchProducer;
import com.facebook.imagepipeline.producers.LocalResourceFetchProducer;
import com.facebook.imagepipeline.producers.LocalVideoThumbnailProducer;
import com.facebook.imagepipeline.producers.MediaVariationsFallbackProducer;
import com.facebook.imagepipeline.producers.NetworkFetchProducer;
import com.facebook.imagepipeline.producers.NetworkFetcher;
import com.facebook.imagepipeline.producers.NullProducer;
import com.facebook.imagepipeline.producers.PostprocessedBitmapMemoryCacheProducer;
import com.facebook.imagepipeline.producers.PostprocessorProducer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.QualifiedResourceFetchProducer;
import com.facebook.imagepipeline.producers.ResizeAndRotateProducer;
import com.facebook.imagepipeline.producers.SwallowResultProducer;
import com.facebook.imagepipeline.producers.ThreadHandoffProducer;
import com.facebook.imagepipeline.producers.ThreadHandoffProducerQueue;
import com.facebook.imagepipeline.producers.ThrottlingProducer;
import com.facebook.imagepipeline.producers.ThumbnailBranchProducer;
import com.facebook.imagepipeline.producers.ThumbnailProducer;
import com.facebook.imagepipeline.producers.WebpTranscodeProducer;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public class ProducerFactory {

   private static final int MAX_SIMULTANEOUS_REQUESTS = 5;
   private AssetManager mAssetManager;
   private final MemoryCache<CacheKey, CloseableImage> mBitmapMemoryCache;
   private final ByteArrayPool mByteArrayPool;
   private final CacheKeyFactory mCacheKeyFactory;
   private ContentResolver mContentResolver;
   private final boolean mDecodeCancellationEnabled;
   private final BufferedDiskCache mDefaultBufferedDiskCache;
   private final boolean mDownsampleEnabled;
   private final MemoryCache<CacheKey, PooledByteBuffer> mEncodedMemoryCache;
   private final ExecutorSupplier mExecutorSupplier;
   private final ImageDecoder mImageDecoder;
   private final DiskCachePolicy mMainDiskCachePolicy;
   @Nullable
   private final MediaIdExtractor mMediaIdExtractor;
   private final MediaVariationsIndex mMediaVariationsIndex;
   private final PlatformBitmapFactory mPlatformBitmapFactory;
   private final PooledByteBufferFactory mPooledByteBufferFactory;
   private final ProgressiveJpegConfig mProgressiveJpegConfig;
   private final boolean mResizeAndRotateEnabledForNetwork;
   private Resources mResources;
   private final BufferedDiskCache mSmallImageBufferedDiskCache;


   public ProducerFactory(Context var1, ByteArrayPool var2, ImageDecoder var3, ProgressiveJpegConfig var4, boolean var5, boolean var6, boolean var7, ExecutorSupplier var8, PooledByteBufferFactory var9, MemoryCache<CacheKey, CloseableImage> var10, MemoryCache<CacheKey, PooledByteBuffer> var11, BufferedDiskCache var12, BufferedDiskCache var13, MediaVariationsIndex var14, @Nullable MediaIdExtractor var15, CacheKeyFactory var16, PlatformBitmapFactory var17, int var18) {
      this.mContentResolver = var1.getApplicationContext().getContentResolver();
      this.mResources = var1.getApplicationContext().getResources();
      this.mAssetManager = var1.getApplicationContext().getAssets();
      this.mByteArrayPool = var2;
      this.mImageDecoder = var3;
      this.mProgressiveJpegConfig = var4;
      this.mDownsampleEnabled = var5;
      this.mResizeAndRotateEnabledForNetwork = var6;
      this.mDecodeCancellationEnabled = var7;
      this.mExecutorSupplier = var8;
      this.mPooledByteBufferFactory = var9;
      this.mBitmapMemoryCache = var10;
      this.mEncodedMemoryCache = var11;
      this.mDefaultBufferedDiskCache = var12;
      this.mSmallImageBufferedDiskCache = var13;
      this.mMediaVariationsIndex = var14;
      this.mMediaIdExtractor = var15;
      this.mCacheKeyFactory = var16;
      this.mPlatformBitmapFactory = var17;
      if(var18 > 0) {
         this.mMainDiskCachePolicy = new SplitCachesByImageSizeDiskCachePolicy(var12, var13, var16, var18);
      } else {
         this.mMainDiskCachePolicy = new SmallCacheIfRequestedDiskCachePolicy(var12, var13, var16);
      }
   }

   public static AddImageTransformMetaDataProducer newAddImageTransformMetaDataProducer(Producer<EncodedImage> var0) {
      return new AddImageTransformMetaDataProducer(var0);
   }

   public static BranchOnSeparateImagesProducer newBranchOnSeparateImagesProducer(Producer<EncodedImage> var0, Producer<EncodedImage> var1) {
      return new BranchOnSeparateImagesProducer(var0, var1);
   }

   public static <T extends Object> NullProducer<T> newNullProducer() {
      return new NullProducer();
   }

   public static <T extends Object> SwallowResultProducer<T> newSwallowResultProducer(Producer<T> var0) {
      return new SwallowResultProducer(var0);
   }

   public <T extends Object> ThreadHandoffProducer<T> newBackgroundThreadHandoffProducer(Producer<T> var1, ThreadHandoffProducerQueue var2) {
      return new ThreadHandoffProducer(var1, var2);
   }

   public BitmapMemoryCacheGetProducer newBitmapMemoryCacheGetProducer(Producer<CloseableReference<CloseableImage>> var1) {
      return new BitmapMemoryCacheGetProducer(this.mBitmapMemoryCache, this.mCacheKeyFactory, var1);
   }

   public BitmapMemoryCacheKeyMultiplexProducer newBitmapMemoryCacheKeyMultiplexProducer(Producer<CloseableReference<CloseableImage>> var1) {
      return new BitmapMemoryCacheKeyMultiplexProducer(this.mCacheKeyFactory, var1);
   }

   public BitmapMemoryCacheProducer newBitmapMemoryCacheProducer(Producer<CloseableReference<CloseableImage>> var1) {
      return new BitmapMemoryCacheProducer(this.mBitmapMemoryCache, this.mCacheKeyFactory, var1);
   }

   public DataFetchProducer newDataFetchProducer() {
      return new DataFetchProducer(this.mPooledByteBufferFactory);
   }

   public DecodeProducer newDecodeProducer(Producer<EncodedImage> var1) {
      return new DecodeProducer(this.mByteArrayPool, this.mExecutorSupplier.forDecode(), this.mImageDecoder, this.mProgressiveJpegConfig, this.mDownsampleEnabled, this.mResizeAndRotateEnabledForNetwork, this.mDecodeCancellationEnabled, var1);
   }

   public DiskCacheReadProducer newDiskCacheReadProducer(Producer<EncodedImage> var1) {
      return new DiskCacheReadProducer(var1, this.mMainDiskCachePolicy);
   }

   public DiskCacheWriteProducer newDiskCacheWriteProducer(Producer<EncodedImage> var1) {
      return new DiskCacheWriteProducer(var1, this.mMainDiskCachePolicy);
   }

   public EncodedCacheKeyMultiplexProducer newEncodedCacheKeyMultiplexProducer(Producer<EncodedImage> var1) {
      return new EncodedCacheKeyMultiplexProducer(this.mCacheKeyFactory, var1);
   }

   public EncodedMemoryCacheProducer newEncodedMemoryCacheProducer(Producer<EncodedImage> var1) {
      return new EncodedMemoryCacheProducer(this.mEncodedMemoryCache, this.mCacheKeyFactory, var1);
   }

   public LocalAssetFetchProducer newLocalAssetFetchProducer() {
      return new LocalAssetFetchProducer(this.mExecutorSupplier.forLocalStorageRead(), this.mPooledByteBufferFactory, this.mAssetManager);
   }

   public LocalContentUriFetchProducer newLocalContentUriFetchProducer() {
      return new LocalContentUriFetchProducer(this.mExecutorSupplier.forLocalStorageRead(), this.mPooledByteBufferFactory, this.mContentResolver);
   }

   public LocalContentUriThumbnailFetchProducer newLocalContentUriThumbnailFetchProducer() {
      return new LocalContentUriThumbnailFetchProducer(this.mExecutorSupplier.forLocalStorageRead(), this.mPooledByteBufferFactory, this.mContentResolver);
   }

   public LocalExifThumbnailProducer newLocalExifThumbnailProducer() {
      return new LocalExifThumbnailProducer(this.mExecutorSupplier.forLocalStorageRead(), this.mPooledByteBufferFactory, this.mContentResolver);
   }

   public LocalFileFetchProducer newLocalFileFetchProducer() {
      return new LocalFileFetchProducer(this.mExecutorSupplier.forLocalStorageRead(), this.mPooledByteBufferFactory);
   }

   public LocalResourceFetchProducer newLocalResourceFetchProducer() {
      return new LocalResourceFetchProducer(this.mExecutorSupplier.forLocalStorageRead(), this.mPooledByteBufferFactory, this.mResources);
   }

   public LocalVideoThumbnailProducer newLocalVideoThumbnailProducer() {
      return new LocalVideoThumbnailProducer(this.mExecutorSupplier.forLocalStorageRead());
   }

   public MediaVariationsFallbackProducer newMediaVariationsProducer(Producer<EncodedImage> var1) {
      return new MediaVariationsFallbackProducer(this.mDefaultBufferedDiskCache, this.mSmallImageBufferedDiskCache, this.mCacheKeyFactory, this.mMediaVariationsIndex, this.mMediaIdExtractor, this.mMainDiskCachePolicy, var1);
   }

   public NetworkFetchProducer newNetworkFetchProducer(NetworkFetcher var1) {
      return new NetworkFetchProducer(this.mPooledByteBufferFactory, this.mByteArrayPool, var1);
   }

   public PostprocessedBitmapMemoryCacheProducer newPostprocessorBitmapMemoryCacheProducer(Producer<CloseableReference<CloseableImage>> var1) {
      return new PostprocessedBitmapMemoryCacheProducer(this.mBitmapMemoryCache, this.mCacheKeyFactory, var1);
   }

   public PostprocessorProducer newPostprocessorProducer(Producer<CloseableReference<CloseableImage>> var1) {
      return new PostprocessorProducer(var1, this.mPlatformBitmapFactory, this.mExecutorSupplier.forBackgroundTasks());
   }

   public QualifiedResourceFetchProducer newQualifiedResourceFetchProducer() {
      return new QualifiedResourceFetchProducer(this.mExecutorSupplier.forLocalStorageRead(), this.mPooledByteBufferFactory, this.mContentResolver);
   }

   public ResizeAndRotateProducer newResizeAndRotateProducer(Producer<EncodedImage> var1, boolean var2, boolean var3) {
      Executor var4 = this.mExecutorSupplier.forBackgroundTasks();
      PooledByteBufferFactory var5 = this.mPooledByteBufferFactory;
      if(var2 && !this.mDownsampleEnabled) {
         var2 = true;
      } else {
         var2 = false;
      }

      return new ResizeAndRotateProducer(var4, var5, var2, var1, var3);
   }

   public <T extends Object> ThrottlingProducer<T> newThrottlingProducer(Producer<T> var1) {
      return new ThrottlingProducer(5, this.mExecutorSupplier.forLightweightBackgroundTasks(), var1);
   }

   public ThumbnailBranchProducer newThumbnailBranchProducer(ThumbnailProducer<EncodedImage>[] var1) {
      return new ThumbnailBranchProducer(var1);
   }

   public WebpTranscodeProducer newWebpTranscodeProducer(Producer<EncodedImage> var1) {
      return new WebpTranscodeProducer(this.mExecutorSupplier.forBackgroundTasks(), this.mPooledByteBufferFactory, var1);
   }
}
