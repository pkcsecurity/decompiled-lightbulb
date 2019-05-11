package com.facebook.imagepipeline.cache;

import com.facebook.cache.common.CacheKey;
import com.facebook.imagepipeline.cache.CountingMemoryCache;
import com.facebook.imagepipeline.cache.ImageCacheStatsTracker;

public class NoOpImageCacheStatsTracker implements ImageCacheStatsTracker {

   private static NoOpImageCacheStatsTracker sInstance;


   public static NoOpImageCacheStatsTracker getInstance() {
      synchronized(NoOpImageCacheStatsTracker.class){}

      NoOpImageCacheStatsTracker var0;
      try {
         if(sInstance == null) {
            sInstance = new NoOpImageCacheStatsTracker();
         }

         var0 = sInstance;
      } finally {
         ;
      }

      return var0;
   }

   public void onBitmapCacheHit(CacheKey var1) {}

   public void onBitmapCacheMiss() {}

   public void onBitmapCachePut() {}

   public void onDiskCacheGetFail() {}

   public void onDiskCacheHit() {}

   public void onDiskCacheMiss() {}

   public void onMemoryCacheHit(CacheKey var1) {}

   public void onMemoryCacheMiss() {}

   public void onMemoryCachePut() {}

   public void onStagingAreaHit(CacheKey var1) {}

   public void onStagingAreaMiss() {}

   public void registerBitmapMemoryCache(CountingMemoryCache<?, ?> var1) {}

   public void registerEncodedMemoryCache(CountingMemoryCache<?, ?> var1) {}
}
