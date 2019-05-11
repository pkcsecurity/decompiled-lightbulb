package com.facebook.imagepipeline.producers;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.DelegatingConsumer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ProducerListener;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.Postprocessor;
import com.facebook.imagepipeline.request.RepeatedPostprocessor;
import java.util.Map;

public class PostprocessedBitmapMemoryCacheProducer implements Producer<CloseableReference<CloseableImage>> {

   public static final String PRODUCER_NAME = "PostprocessedBitmapMemoryCacheProducer";
   @VisibleForTesting
   static final String VALUE_FOUND = "cached_value_found";
   private final CacheKeyFactory mCacheKeyFactory;
   private final Producer<CloseableReference<CloseableImage>> mInputProducer;
   private final MemoryCache<CacheKey, CloseableImage> mMemoryCache;


   public PostprocessedBitmapMemoryCacheProducer(MemoryCache<CacheKey, CloseableImage> var1, CacheKeyFactory var2, Producer<CloseableReference<CloseableImage>> var3) {
      this.mMemoryCache = var1;
      this.mCacheKeyFactory = var2;
      this.mInputProducer = var3;
   }

   protected String getProducerName() {
      return "PostprocessedBitmapMemoryCacheProducer";
   }

   public void produceResults(Consumer<CloseableReference<CloseableImage>> var1, ProducerContext var2) {
      ProducerListener var5 = var2.getListener();
      String var6 = var2.getId();
      ImageRequest var3 = var2.getImageRequest();
      Object var4 = var2.getCallerContext();
      Postprocessor var8 = var3.getPostprocessor();
      if(var8 != null && var8.getPostprocessorCacheKey() != null) {
         var5.onProducerStart(var6, this.getProducerName());
         CacheKey var9 = this.mCacheKeyFactory.getPostprocessedBitmapCacheKey(var3, var4);
         CloseableReference var7 = this.mMemoryCache.get(var9);
         var3 = null;
         var4 = null;
         if(var7 != null) {
            String var12 = this.getProducerName();
            Map var11 = (Map)var4;
            if(var5.requiresExtraMap(var6)) {
               var11 = ImmutableMap.of("cached_value_found", "true");
            }

            var5.onProducerFinishWithSuccess(var6, var12, var11);
            var5.onUltimateProducerReached(var6, "PostprocessedBitmapMemoryCacheProducer", true);
            var1.onProgressUpdate(1.0F);
            var1.onNewResult(var7, true);
            var7.close();
         } else {
            PostprocessedBitmapMemoryCacheProducer.CachedPostprocessorConsumer var13 = new PostprocessedBitmapMemoryCacheProducer.CachedPostprocessorConsumer(var1, var9, var8 instanceof RepeatedPostprocessor, this.mMemoryCache);
            String var14 = this.getProducerName();
            Map var10 = var3;
            if(var5.requiresExtraMap(var6)) {
               var10 = ImmutableMap.of("cached_value_found", "false");
            }

            var5.onProducerFinishWithSuccess(var6, var14, var10);
            this.mInputProducer.produceResults(var13, var2);
         }
      } else {
         this.mInputProducer.produceResults(var1, var2);
      }
   }

   public static class CachedPostprocessorConsumer extends DelegatingConsumer<CloseableReference<CloseableImage>, CloseableReference<CloseableImage>> {

      private final CacheKey mCacheKey;
      private final boolean mIsRepeatedProcessor;
      private final MemoryCache<CacheKey, CloseableImage> mMemoryCache;


      public CachedPostprocessorConsumer(Consumer<CloseableReference<CloseableImage>> var1, CacheKey var2, boolean var3, MemoryCache<CacheKey, CloseableImage> var4) {
         super(var1);
         this.mCacheKey = var2;
         this.mIsRepeatedProcessor = var3;
         this.mMemoryCache = var4;
      }

      protected void onNewResultImpl(CloseableReference<CloseableImage> param1, boolean param2) {
         // $FF: Couldn't be decompiled
      }
   }
}
