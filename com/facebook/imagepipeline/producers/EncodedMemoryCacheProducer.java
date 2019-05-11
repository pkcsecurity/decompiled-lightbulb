package com.facebook.imagepipeline.producers;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.DelegatingConsumer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;

public class EncodedMemoryCacheProducer implements Producer<EncodedImage> {

   public static final String EXTRA_CACHED_VALUE_FOUND = "cached_value_found";
   public static final String PRODUCER_NAME = "EncodedMemoryCacheProducer";
   private final CacheKeyFactory mCacheKeyFactory;
   private final Producer<EncodedImage> mInputProducer;
   private final MemoryCache<CacheKey, PooledByteBuffer> mMemoryCache;


   public EncodedMemoryCacheProducer(MemoryCache<CacheKey, PooledByteBuffer> var1, CacheKeyFactory var2, Producer<EncodedImage> var3) {
      this.mMemoryCache = var1;
      this.mCacheKeyFactory = var2;
      this.mInputProducer = var3;
   }

   public void produceResults(Consumer<EncodedImage> param1, ProducerContext param2) {
      // $FF: Couldn't be decompiled
   }

   static class EncodedMemoryCacheConsumer extends DelegatingConsumer<EncodedImage, EncodedImage> {

      private final MemoryCache<CacheKey, PooledByteBuffer> mMemoryCache;
      private final CacheKey mRequestedCacheKey;


      public EncodedMemoryCacheConsumer(Consumer<EncodedImage> var1, MemoryCache<CacheKey, PooledByteBuffer> var2, CacheKey var3) {
         super(var1);
         this.mMemoryCache = var2;
         this.mRequestedCacheKey = var3;
      }

      public void onNewResultImpl(EncodedImage var1, boolean var2) {
         if(var2 && var1 != null) {
            CloseableReference var4 = var1.getByteBufferRef();
            if(var4 != null) {
               CloseableReference var17;
               try {
                  CacheKey var3;
                  if(var1.getEncodedCacheKey() != null) {
                     var3 = var1.getEncodedCacheKey();
                  } else {
                     var3 = this.mRequestedCacheKey;
                  }

                  var17 = this.mMemoryCache.cache(var3, var4);
               } finally {
                  CloseableReference.closeSafely(var4);
               }

               if(var17 != null) {
                  EncodedImage var18;
                  try {
                     var18 = new EncodedImage(var17);
                     var18.copyMetaDataFrom(var1);
                  } finally {
                     CloseableReference.closeSafely(var17);
                  }

                  try {
                     this.getConsumer().onProgressUpdate(1.0F);
                     this.getConsumer().onNewResult(var18, true);
                  } finally {
                     EncodedImage.closeSafely(var18);
                  }

                  return;
               }
            }

            this.getConsumer().onNewResult(var1, true);
         } else {
            this.getConsumer().onNewResult(var1, var2);
         }
      }
   }
}
