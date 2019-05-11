package com.facebook.imagepipeline.producers;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.ImmutableMap;
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
import java.util.Map;

public class BitmapMemoryCacheProducer implements Producer<CloseableReference<CloseableImage>> {

   public static final String EXTRA_CACHED_VALUE_FOUND = "cached_value_found";
   public static final String PRODUCER_NAME = "BitmapMemoryCacheProducer";
   private final CacheKeyFactory mCacheKeyFactory;
   private final Producer<CloseableReference<CloseableImage>> mInputProducer;
   private final MemoryCache<CacheKey, CloseableImage> mMemoryCache;


   public BitmapMemoryCacheProducer(MemoryCache<CacheKey, CloseableImage> var1, CacheKeyFactory var2, Producer<CloseableReference<CloseableImage>> var3) {
      this.mMemoryCache = var1;
      this.mCacheKeyFactory = var2;
      this.mInputProducer = var3;
   }

   // $FF: synthetic method
   static MemoryCache access$000(BitmapMemoryCacheProducer var0) {
      return var0.mMemoryCache;
   }

   protected String getProducerName() {
      return "BitmapMemoryCacheProducer";
   }

   public void produceResults(Consumer<CloseableReference<CloseableImage>> var1, ProducerContext var2) {
      ProducerListener var6 = var2.getListener();
      String var7 = var2.getId();
      var6.onProducerStart(var7, this.getProducerName());
      ImageRequest var4 = var2.getImageRequest();
      Object var5 = var2.getCallerContext();
      CacheKey var8 = this.mCacheKeyFactory.getBitmapCacheKey(var4, var5);
      CloseableReference var9 = this.mMemoryCache.get(var8);
      var5 = null;
      if(var9 != null) {
         boolean var3 = ((CloseableImage)var9.get()).getQualityInfo().isOfFullQuality();
         if(var3) {
            String var10 = this.getProducerName();
            Map var13;
            if(var6.requiresExtraMap(var7)) {
               var13 = ImmutableMap.of("cached_value_found", "true");
            } else {
               var13 = null;
            }

            var6.onProducerFinishWithSuccess(var7, var10, var13);
            var6.onUltimateProducerReached(var7, this.getProducerName(), true);
            var1.onProgressUpdate(1.0F);
         }

         var1.onNewResult(var9, var3);
         var9.close();
         if(var3) {
            return;
         }
      }

      if(var2.getLowestPermittedRequestLevel().getValue() >= ImageRequest.RequestLevel.BITMAP_MEMORY_CACHE.getValue()) {
         String var15 = this.getProducerName();
         Map var12;
         if(var6.requiresExtraMap(var7)) {
            var12 = ImmutableMap.of("cached_value_found", "false");
         } else {
            var12 = null;
         }

         var6.onProducerFinishWithSuccess(var7, var15, var12);
         var6.onUltimateProducerReached(var7, this.getProducerName(), false);
         var1.onNewResult((Object)null, true);
      } else {
         Consumer var14 = this.wrapConsumer(var1, var8);
         String var16 = this.getProducerName();
         Map var11 = (Map)var5;
         if(var6.requiresExtraMap(var7)) {
            var11 = ImmutableMap.of("cached_value_found", "false");
         }

         var6.onProducerFinishWithSuccess(var7, var16, var11);
         this.mInputProducer.produceResults(var14, var2);
      }
   }

   protected Consumer<CloseableReference<CloseableImage>> wrapConsumer(final Consumer<CloseableReference<CloseableImage>> var1, final CacheKey var2) {
      return new DelegatingConsumer(var1) {
         public void onNewResultImpl(CloseableReference<CloseableImage> param1, boolean param2) {
            // $FF: Couldn't be decompiled
         }
      };
   }
}
