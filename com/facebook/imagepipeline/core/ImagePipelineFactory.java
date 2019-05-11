package com.facebook.imagepipeline.core;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.v4.util.Pools;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.cache.disk.FileCache;
import com.facebook.common.internal.AndroidPredicates;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Suppliers;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.imageformat.ImageFormatChecker;
import com.facebook.imagepipeline.animated.factory.AnimatedFactory;
import com.facebook.imagepipeline.animated.factory.AnimatedFactoryProvider;
import com.facebook.imagepipeline.animated.factory.AnimatedImageFactory;
import com.facebook.imagepipeline.bitmaps.ArtBitmapFactory;
import com.facebook.imagepipeline.bitmaps.EmptyJpegGenerator;
import com.facebook.imagepipeline.bitmaps.GingerbreadBitmapFactory;
import com.facebook.imagepipeline.bitmaps.HoneycombBitmapFactory;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.cache.BitmapCountingMemoryCacheFactory;
import com.facebook.imagepipeline.cache.BitmapMemoryCacheFactory;
import com.facebook.imagepipeline.cache.BufferedDiskCache;
import com.facebook.imagepipeline.cache.CountingMemoryCache;
import com.facebook.imagepipeline.cache.EncodedCountingMemoryCacheFactory;
import com.facebook.imagepipeline.cache.EncodedMemoryCacheFactory;
import com.facebook.imagepipeline.cache.MediaVariationsIndex;
import com.facebook.imagepipeline.cache.MediaVariationsIndexDatabase;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.cache.NoOpMediaVariationsIndex;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ProducerFactory;
import com.facebook.imagepipeline.core.ProducerSequenceFactory;
import com.facebook.imagepipeline.decoder.DefaultImageDecoder;
import com.facebook.imagepipeline.decoder.ImageDecoder;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.memory.PoolFactory;
import com.facebook.imagepipeline.platform.ArtDecoder;
import com.facebook.imagepipeline.platform.GingerbreadPurgeableDecoder;
import com.facebook.imagepipeline.platform.KitKatPurgeableDecoder;
import com.facebook.imagepipeline.platform.PlatformDecoder;
import com.facebook.imagepipeline.producers.ThreadHandoffProducerQueue;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class ImagePipelineFactory {

   private static ImagePipelineFactory sInstance;
   private AnimatedFactory mAnimatedFactory;
   private CountingMemoryCache<CacheKey, CloseableImage> mBitmapCountingMemoryCache;
   private MemoryCache<CacheKey, CloseableImage> mBitmapMemoryCache;
   private final ImagePipelineConfig mConfig;
   private CountingMemoryCache<CacheKey, PooledByteBuffer> mEncodedCountingMemoryCache;
   private MemoryCache<CacheKey, PooledByteBuffer> mEncodedMemoryCache;
   private ImageDecoder mImageDecoder;
   private ImagePipeline mImagePipeline;
   private BufferedDiskCache mMainBufferedDiskCache;
   private FileCache mMainFileCache;
   private MediaVariationsIndex mMediaVariationsIndex;
   private PlatformBitmapFactory mPlatformBitmapFactory;
   private PlatformDecoder mPlatformDecoder;
   private ProducerFactory mProducerFactory;
   private ProducerSequenceFactory mProducerSequenceFactory;
   private BufferedDiskCache mSmallImageBufferedDiskCache;
   private FileCache mSmallImageFileCache;
   private final ThreadHandoffProducerQueue mThreadHandoffProducerQueue;


   public ImagePipelineFactory(ImagePipelineConfig var1) {
      this.mConfig = (ImagePipelineConfig)Preconditions.checkNotNull(var1);
      this.mThreadHandoffProducerQueue = new ThreadHandoffProducerQueue(var1.getExecutorSupplier().forLightweightBackgroundTasks());
   }

   public static PlatformBitmapFactory buildPlatformBitmapFactory(PoolFactory var0, PlatformDecoder var1) {
      return (PlatformBitmapFactory)(VERSION.SDK_INT >= 21?new ArtBitmapFactory(var0.getBitmapPool()):(VERSION.SDK_INT >= 11?new HoneycombBitmapFactory(new EmptyJpegGenerator(var0.getPooledByteBufferFactory()), var1):new GingerbreadBitmapFactory()));
   }

   public static PlatformDecoder buildPlatformDecoder(PoolFactory var0, boolean var1) {
      if(VERSION.SDK_INT >= 21) {
         int var2 = var0.getFlexByteArrayPoolMaxNumThreads();
         return new ArtDecoder(var0.getBitmapPool(), var2, new Pools.SynchronizedPool(var2));
      } else {
         return (PlatformDecoder)(var1 && VERSION.SDK_INT < 19?new GingerbreadPurgeableDecoder():new KitKatPurgeableDecoder(var0.getFlexByteArrayPool()));
      }
   }

   private ImageDecoder getImageDecoder() {
      if(this.mImageDecoder == null) {
         if(this.mConfig.getImageDecoder() != null) {
            this.mImageDecoder = this.mConfig.getImageDecoder();
         } else {
            AnimatedImageFactory var1;
            if(this.getAnimatedFactory() != null) {
               var1 = this.getAnimatedFactory().getAnimatedImageFactory();
            } else {
               var1 = null;
            }

            if(this.mConfig.getImageDecoderConfig() == null) {
               this.mImageDecoder = new DefaultImageDecoder(var1, this.getPlatformDecoder(), this.mConfig.getBitmapConfig());
            } else {
               this.mImageDecoder = new DefaultImageDecoder(var1, this.getPlatformDecoder(), this.mConfig.getBitmapConfig(), this.mConfig.getImageDecoderConfig().getCustomImageDecoders());
               ImageFormatChecker.getInstance().setCustomImageFormatCheckers(this.mConfig.getImageDecoderConfig().getCustomImageFormats());
            }
         }
      }

      return this.mImageDecoder;
   }

   public static ImagePipelineFactory getInstance() {
      return (ImagePipelineFactory)Preconditions.checkNotNull(sInstance, "ImagePipelineFactory was not initialized!");
   }

   private ProducerFactory getProducerFactory() {
      if(this.mProducerFactory == null) {
         this.mProducerFactory = new ProducerFactory(this.mConfig.getContext(), this.mConfig.getPoolFactory().getSmallByteArrayPool(), this.getImageDecoder(), this.mConfig.getProgressiveJpegConfig(), this.mConfig.isDownsampleEnabled(), this.mConfig.isResizeAndRotateEnabledForNetwork(), this.mConfig.getExperiments().isDecodeCancellationEnabled(), this.mConfig.getExecutorSupplier(), this.mConfig.getPoolFactory().getPooledByteBufferFactory(), this.getBitmapMemoryCache(), this.getEncodedMemoryCache(), this.getMainBufferedDiskCache(), this.getSmallImageBufferedDiskCache(), this.getMediaVariationsIndex(), this.mConfig.getExperiments().getMediaIdExtractor(), this.mConfig.getCacheKeyFactory(), this.getPlatformBitmapFactory(), this.mConfig.getExperiments().getForceSmallCacheThresholdBytes());
      }

      return this.mProducerFactory;
   }

   private ProducerSequenceFactory getProducerSequenceFactory() {
      if(this.mProducerSequenceFactory == null) {
         this.mProducerSequenceFactory = new ProducerSequenceFactory(this.getProducerFactory(), this.mConfig.getNetworkFetcher(), this.mConfig.isResizeAndRotateEnabledForNetwork(), this.mConfig.getExperiments().isWebpSupportEnabled(), this.mThreadHandoffProducerQueue, this.mConfig.getExperiments().getUseDownsamplingRatioForResizing());
      }

      return this.mProducerSequenceFactory;
   }

   private BufferedDiskCache getSmallImageBufferedDiskCache() {
      if(this.mSmallImageBufferedDiskCache == null) {
         this.mSmallImageBufferedDiskCache = new BufferedDiskCache(this.getSmallImageFileCache(), this.mConfig.getPoolFactory().getPooledByteBufferFactory(), this.mConfig.getPoolFactory().getPooledByteStreams(), this.mConfig.getExecutorSupplier().forLocalStorageRead(), this.mConfig.getExecutorSupplier().forLocalStorageWrite(), this.mConfig.getImageCacheStatsTracker());
      }

      return this.mSmallImageBufferedDiskCache;
   }

   public static void initialize(Context var0) {
      initialize(ImagePipelineConfig.newBuilder(var0).build());
   }

   public static void initialize(ImagePipelineConfig var0) {
      sInstance = new ImagePipelineFactory(var0);
   }

   public static void shutDown() {
      if(sInstance != null) {
         sInstance.getBitmapMemoryCache().removeAll(AndroidPredicates.True());
         sInstance.getEncodedMemoryCache().removeAll(AndroidPredicates.True());
         sInstance = null;
      }

   }

   public AnimatedFactory getAnimatedFactory() {
      if(this.mAnimatedFactory == null) {
         this.mAnimatedFactory = AnimatedFactoryProvider.getAnimatedFactory(this.getPlatformBitmapFactory(), this.mConfig.getExecutorSupplier());
      }

      return this.mAnimatedFactory;
   }

   public CountingMemoryCache<CacheKey, CloseableImage> getBitmapCountingMemoryCache() {
      if(this.mBitmapCountingMemoryCache == null) {
         this.mBitmapCountingMemoryCache = BitmapCountingMemoryCacheFactory.get(this.mConfig.getBitmapMemoryCacheParamsSupplier(), this.mConfig.getMemoryTrimmableRegistry(), this.getPlatformBitmapFactory(), this.mConfig.getExperiments().isExternalCreatedBitmapLogEnabled());
      }

      return this.mBitmapCountingMemoryCache;
   }

   public MemoryCache<CacheKey, CloseableImage> getBitmapMemoryCache() {
      if(this.mBitmapMemoryCache == null) {
         this.mBitmapMemoryCache = BitmapMemoryCacheFactory.get(this.getBitmapCountingMemoryCache(), this.mConfig.getImageCacheStatsTracker());
      }

      return this.mBitmapMemoryCache;
   }

   public CountingMemoryCache<CacheKey, PooledByteBuffer> getEncodedCountingMemoryCache() {
      if(this.mEncodedCountingMemoryCache == null) {
         this.mEncodedCountingMemoryCache = EncodedCountingMemoryCacheFactory.get(this.mConfig.getEncodedMemoryCacheParamsSupplier(), this.mConfig.getMemoryTrimmableRegistry(), this.getPlatformBitmapFactory());
      }

      return this.mEncodedCountingMemoryCache;
   }

   public MemoryCache<CacheKey, PooledByteBuffer> getEncodedMemoryCache() {
      if(this.mEncodedMemoryCache == null) {
         this.mEncodedMemoryCache = EncodedMemoryCacheFactory.get(this.getEncodedCountingMemoryCache(), this.mConfig.getImageCacheStatsTracker());
      }

      return this.mEncodedMemoryCache;
   }

   public ImagePipeline getImagePipeline() {
      if(this.mImagePipeline == null) {
         this.mImagePipeline = new ImagePipeline(this.getProducerSequenceFactory(), this.mConfig.getRequestListeners(), this.mConfig.getIsPrefetchEnabledSupplier(), this.getBitmapMemoryCache(), this.getEncodedMemoryCache(), this.getMainBufferedDiskCache(), this.getSmallImageBufferedDiskCache(), this.mConfig.getCacheKeyFactory(), this.mThreadHandoffProducerQueue, Suppliers.of(Boolean.valueOf(false)));
      }

      return this.mImagePipeline;
   }

   public BufferedDiskCache getMainBufferedDiskCache() {
      if(this.mMainBufferedDiskCache == null) {
         this.mMainBufferedDiskCache = new BufferedDiskCache(this.getMainFileCache(), this.mConfig.getPoolFactory().getPooledByteBufferFactory(), this.mConfig.getPoolFactory().getPooledByteStreams(), this.mConfig.getExecutorSupplier().forLocalStorageRead(), this.mConfig.getExecutorSupplier().forLocalStorageWrite(), this.mConfig.getImageCacheStatsTracker());
      }

      return this.mMainBufferedDiskCache;
   }

   public FileCache getMainFileCache() {
      if(this.mMainFileCache == null) {
         DiskCacheConfig var1 = this.mConfig.getMainDiskCacheConfig();
         this.mMainFileCache = this.mConfig.getFileCacheFactory().get(var1);
      }

      return this.mMainFileCache;
   }

   public MediaVariationsIndex getMediaVariationsIndex() {
      if(this.mMediaVariationsIndex == null) {
         Object var1;
         if(this.mConfig.getExperiments().getMediaVariationsIndexEnabled()) {
            var1 = new MediaVariationsIndexDatabase(this.mConfig.getContext(), this.mConfig.getExecutorSupplier().forLocalStorageRead(), this.mConfig.getExecutorSupplier().forLocalStorageWrite());
         } else {
            var1 = new NoOpMediaVariationsIndex();
         }

         this.mMediaVariationsIndex = (MediaVariationsIndex)var1;
      }

      return this.mMediaVariationsIndex;
   }

   public PlatformBitmapFactory getPlatformBitmapFactory() {
      if(this.mPlatformBitmapFactory == null) {
         this.mPlatformBitmapFactory = buildPlatformBitmapFactory(this.mConfig.getPoolFactory(), this.getPlatformDecoder());
      }

      return this.mPlatformBitmapFactory;
   }

   public PlatformDecoder getPlatformDecoder() {
      if(this.mPlatformDecoder == null) {
         this.mPlatformDecoder = buildPlatformDecoder(this.mConfig.getPoolFactory(), this.mConfig.getExperiments().isWebpSupportEnabled());
      }

      return this.mPlatformDecoder;
   }

   public FileCache getSmallImageFileCache() {
      if(this.mSmallImageFileCache == null) {
         DiskCacheConfig var1 = this.mConfig.getSmallImageDiskCacheConfig();
         this.mSmallImageFileCache = this.mConfig.getFileCacheFactory().get(var1);
      }

      return this.mSmallImageFileCache;
   }
}
