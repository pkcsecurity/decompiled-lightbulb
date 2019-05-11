package com.facebook.imagepipeline.core;

import android.net.Uri;
import bolts.Continuation;
import bolts.Task;
import com.android.internal.util.Predicate;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Supplier;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSources;
import com.facebook.datasource.SimpleDataSource;
import com.facebook.imagepipeline.cache.BufferedDiskCache;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.core.ProducerSequenceFactory;
import com.facebook.imagepipeline.datasource.ProducerToDataSourceAdapter;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.listener.ForwardingRequestListener;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.SettableProducerContext;
import com.facebook.imagepipeline.producers.ThreadHandoffProducerQueue;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class ImagePipeline {

   private static final CancellationException PREFETCH_EXCEPTION = new CancellationException("Prefetching is not enabled");
   private final MemoryCache<CacheKey, CloseableImage> mBitmapMemoryCache;
   private final CacheKeyFactory mCacheKeyFactory;
   private final MemoryCache<CacheKey, PooledByteBuffer> mEncodedMemoryCache;
   private AtomicLong mIdCounter = new AtomicLong();
   private final Supplier<Boolean> mIsPrefetchEnabledSupplier;
   private final BufferedDiskCache mMainBufferedDiskCache;
   private final ProducerSequenceFactory mProducerSequenceFactory;
   private final RequestListener mRequestListener;
   private final BufferedDiskCache mSmallImageBufferedDiskCache;
   private final Supplier<Boolean> mSuppressBitmapPrefetchingSupplier;
   private final ThreadHandoffProducerQueue mThreadHandoffProducerQueue;


   public ImagePipeline(ProducerSequenceFactory var1, Set<RequestListener> var2, Supplier<Boolean> var3, MemoryCache<CacheKey, CloseableImage> var4, MemoryCache<CacheKey, PooledByteBuffer> var5, BufferedDiskCache var6, BufferedDiskCache var7, CacheKeyFactory var8, ThreadHandoffProducerQueue var9, Supplier<Boolean> var10) {
      this.mProducerSequenceFactory = var1;
      this.mRequestListener = new ForwardingRequestListener(var2);
      this.mIsPrefetchEnabledSupplier = var3;
      this.mBitmapMemoryCache = var4;
      this.mEncodedMemoryCache = var5;
      this.mMainBufferedDiskCache = var6;
      this.mSmallImageBufferedDiskCache = var7;
      this.mCacheKeyFactory = var8;
      this.mThreadHandoffProducerQueue = var9;
      this.mSuppressBitmapPrefetchingSupplier = var10;
   }

   private String generateUniqueFutureId() {
      return String.valueOf(this.mIdCounter.getAndIncrement());
   }

   private RequestListener getRequestListenerForRequest(ImageRequest var1) {
      return (RequestListener)(var1.getRequestListener() == null?this.mRequestListener:new ForwardingRequestListener(new RequestListener[]{this.mRequestListener, var1.getRequestListener()}));
   }

   private Predicate<CacheKey> predicateForUri(final Uri var1) {
      return new Predicate() {
         public boolean apply(CacheKey var1x) {
            return var1x.containsUri(var1);
         }
      };
   }

   private <T extends Object> DataSource<CloseableReference<T>> submitFetchRequest(Producer<CloseableReference<T>> param1, ImageRequest param2, ImageRequest.RequestLevel param3, Object param4) {
      // $FF: Couldn't be decompiled
   }

   private DataSource<Void> submitPrefetchRequest(Producer<Void> var1, ImageRequest var2, ImageRequest.RequestLevel var3, Object var4, Priority var5) {
      RequestListener var6 = this.getRequestListenerForRequest(var2);

      try {
         var3 = ImageRequest.RequestLevel.getMax(var2.getLowestPermittedRequestLevel(), var3);
         DataSource var8 = ProducerToDataSourceAdapter.create(var1, new SettableProducerContext(var2, this.generateUniqueFutureId(), var6, var4, var3, true, false, var5), var6);
         return var8;
      } catch (Exception var7) {
         return DataSources.immediateFailedDataSource(var7);
      }
   }

   public void clearCaches() {
      this.clearMemoryCaches();
      this.clearDiskCaches();
   }

   public void clearDiskCaches() {
      this.mMainBufferedDiskCache.clearAll();
      this.mSmallImageBufferedDiskCache.clearAll();
   }

   public void clearMemoryCaches() {
      Predicate var1 = new Predicate() {
         public boolean apply(CacheKey var1) {
            return true;
         }
      };
      this.mBitmapMemoryCache.removeAll(var1);
      this.mEncodedMemoryCache.removeAll(var1);
   }

   public void evictFromCache(Uri var1) {
      this.evictFromMemoryCache(var1);
      this.evictFromDiskCache(var1);
   }

   public void evictFromDiskCache(Uri var1) {
      this.evictFromDiskCache(ImageRequest.fromUri(var1));
   }

   public void evictFromDiskCache(ImageRequest var1) {
      CacheKey var2 = this.mCacheKeyFactory.getEncodedCacheKey(var1, (Object)null);
      this.mMainBufferedDiskCache.remove(var2);
      this.mSmallImageBufferedDiskCache.remove(var2);
   }

   public void evictFromMemoryCache(Uri var1) {
      Predicate var2 = this.predicateForUri(var1);
      this.mBitmapMemoryCache.removeAll(var2);
      this.mEncodedMemoryCache.removeAll(var2);
   }

   public DataSource<CloseableReference<CloseableImage>> fetchDecodedImage(ImageRequest var1, Object var2) {
      return this.fetchDecodedImage(var1, var2, ImageRequest.RequestLevel.FULL_FETCH);
   }

   public DataSource<CloseableReference<CloseableImage>> fetchDecodedImage(ImageRequest var1, Object var2, ImageRequest.RequestLevel var3) {
      try {
         DataSource var5 = this.submitFetchRequest(this.mProducerSequenceFactory.getDecodedImageProducerSequence(var1), var1, var3, var2);
         return var5;
      } catch (Exception var4) {
         return DataSources.immediateFailedDataSource(var4);
      }
   }

   public DataSource<CloseableReference<PooledByteBuffer>> fetchEncodedImage(ImageRequest param1, Object param2) {
      // $FF: Couldn't be decompiled
   }

   public DataSource<CloseableReference<CloseableImage>> fetchImageFromBitmapCache(ImageRequest var1, Object var2) {
      return this.fetchDecodedImage(var1, var2, ImageRequest.RequestLevel.BITMAP_MEMORY_CACHE);
   }

   public MemoryCache<CacheKey, CloseableImage> getBitmapMemoryCache() {
      return this.mBitmapMemoryCache;
   }

   public CacheKeyFactory getCacheKeyFactory() {
      return this.mCacheKeyFactory;
   }

   public Supplier<DataSource<CloseableReference<CloseableImage>>> getDataSourceSupplier(final ImageRequest var1, final Object var2, final ImageRequest.RequestLevel var3) {
      return new Supplier() {
         public DataSource<CloseableReference<CloseableImage>> get() {
            return ImagePipeline.this.fetchDecodedImage(var1, var2, var3);
         }
         public String toString() {
            return Objects.toStringHelper((Object)this).add("uri", var1.getSourceUri()).toString();
         }
      };
   }

   @Deprecated
   public Supplier<DataSource<CloseableReference<CloseableImage>>> getDataSourceSupplier(ImageRequest var1, Object var2, boolean var3) {
      ImageRequest.RequestLevel var4;
      if(var3) {
         var4 = ImageRequest.RequestLevel.BITMAP_MEMORY_CACHE;
      } else {
         var4 = ImageRequest.RequestLevel.FULL_FETCH;
      }

      return this.getDataSourceSupplier(var1, var2, var4);
   }

   public Supplier<DataSource<CloseableReference<PooledByteBuffer>>> getEncodedImageDataSourceSupplier(final ImageRequest var1, final Object var2) {
      return new Supplier() {
         public DataSource<CloseableReference<PooledByteBuffer>> get() {
            return ImagePipeline.this.fetchEncodedImage(var1, var2);
         }
         public String toString() {
            return Objects.toStringHelper((Object)this).add("uri", var1.getSourceUri()).toString();
         }
      };
   }

   public boolean isInBitmapMemoryCache(Uri var1) {
      if(var1 == null) {
         return false;
      } else {
         Predicate var2 = this.predicateForUri(var1);
         return this.mBitmapMemoryCache.contains(var2);
      }
   }

   public boolean isInBitmapMemoryCache(ImageRequest var1) {
      if(var1 == null) {
         return false;
      } else {
         CacheKey var6 = this.mCacheKeyFactory.getBitmapCacheKey(var1, (Object)null);
         CloseableReference var7 = this.mBitmapMemoryCache.get(var6);

         boolean var2;
         try {
            var2 = CloseableReference.isValid(var7);
         } finally {
            CloseableReference.closeSafely(var7);
         }

         return var2;
      }
   }

   public DataSource<Boolean> isInDiskCache(Uri var1) {
      return this.isInDiskCache(ImageRequest.fromUri(var1));
   }

   public DataSource<Boolean> isInDiskCache(ImageRequest var1) {
      final CacheKey var3 = this.mCacheKeyFactory.getEncodedCacheKey(var1, (Object)null);
      final SimpleDataSource var2 = SimpleDataSource.create();
      this.mMainBufferedDiskCache.contains(var3).b(new Continuation() {
         public Task<Boolean> then(Task<Boolean> var1) throws Exception {
            return !var1.c() && !var1.d() && ((Boolean)var1.e()).booleanValue()?Task.a((Object)Boolean.valueOf(true)):ImagePipeline.this.mSmallImageBufferedDiskCache.contains(var3);
         }
      }).a(new Continuation() {
         public Void then(Task<Boolean> var1) throws Exception {
            SimpleDataSource var3 = var2;
            boolean var2x;
            if(!var1.c() && !var1.d() && ((Boolean)var1.e()).booleanValue()) {
               var2x = true;
            } else {
               var2x = false;
            }

            var3.setResult(Boolean.valueOf(var2x));
            return null;
         }
      });
      return var2;
   }

   public boolean isInDiskCacheSync(Uri var1) {
      return this.isInDiskCacheSync(var1, ImageRequest.CacheChoice.SMALL) || this.isInDiskCacheSync(var1, ImageRequest.CacheChoice.DEFAULT);
   }

   public boolean isInDiskCacheSync(Uri var1, ImageRequest.CacheChoice var2) {
      return this.isInDiskCacheSync(ImageRequestBuilder.newBuilderWithSource(var1).setCacheChoice(var2).build());
   }

   public boolean isInDiskCacheSync(ImageRequest var1) {
      CacheKey var2 = this.mCacheKeyFactory.getEncodedCacheKey(var1, (Object)null);
      ImageRequest.CacheChoice var3 = var1.getCacheChoice();
      switch(null.$SwitchMap$com$facebook$imagepipeline$request$ImageRequest$CacheChoice[var3.ordinal()]) {
      case 1:
         return this.mMainBufferedDiskCache.diskCheckSync(var2);
      case 2:
         return this.mSmallImageBufferedDiskCache.diskCheckSync(var2);
      default:
         return false;
      }
   }

   public boolean isPaused() {
      return this.mThreadHandoffProducerQueue.isQueueing();
   }

   public void pause() {
      this.mThreadHandoffProducerQueue.startQueueing();
   }

   public DataSource<Void> prefetchToBitmapCache(ImageRequest var1, Object var2) {
      if(!((Boolean)this.mIsPrefetchEnabledSupplier.get()).booleanValue()) {
         return DataSources.immediateFailedDataSource(PREFETCH_EXCEPTION);
      } else {
         try {
            Producer var3;
            if(((Boolean)this.mSuppressBitmapPrefetchingSupplier.get()).booleanValue()) {
               var3 = this.mProducerSequenceFactory.getEncodedImagePrefetchProducerSequence(var1);
            } else {
               var3 = this.mProducerSequenceFactory.getDecodedImagePrefetchProducerSequence(var1);
            }

            DataSource var5 = this.submitPrefetchRequest(var3, var1, ImageRequest.RequestLevel.FULL_FETCH, var2, Priority.MEDIUM);
            return var5;
         } catch (Exception var4) {
            return DataSources.immediateFailedDataSource(var4);
         }
      }
   }

   public DataSource<Void> prefetchToDiskCache(ImageRequest var1, Object var2) {
      return this.prefetchToDiskCache(var1, var2, Priority.MEDIUM);
   }

   public DataSource<Void> prefetchToDiskCache(ImageRequest var1, Object var2, Priority var3) {
      if(!((Boolean)this.mIsPrefetchEnabledSupplier.get()).booleanValue()) {
         return DataSources.immediateFailedDataSource(PREFETCH_EXCEPTION);
      } else {
         try {
            DataSource var5 = this.submitPrefetchRequest(this.mProducerSequenceFactory.getEncodedImagePrefetchProducerSequence(var1), var1, ImageRequest.RequestLevel.FULL_FETCH, var2, var3);
            return var5;
         } catch (Exception var4) {
            return DataSources.immediateFailedDataSource(var4);
         }
      }
   }

   public void resume() {
      this.mThreadHandoffProducerQueue.stopQueuing();
   }
}
