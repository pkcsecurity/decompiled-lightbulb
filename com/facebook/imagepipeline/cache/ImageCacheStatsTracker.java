package com.facebook.imagepipeline.cache;

import com.facebook.cache.common.CacheKey;
import com.facebook.imagepipeline.cache.CountingMemoryCache;

public interface ImageCacheStatsTracker {

   void onBitmapCacheHit(CacheKey var1);

   void onBitmapCacheMiss();

   void onBitmapCachePut();

   void onDiskCacheGetFail();

   void onDiskCacheHit();

   void onDiskCacheMiss();

   void onMemoryCacheHit(CacheKey var1);

   void onMemoryCacheMiss();

   void onMemoryCachePut();

   void onStagingAreaHit(CacheKey var1);

   void onStagingAreaMiss();

   void registerBitmapMemoryCache(CountingMemoryCache<?, ?> var1);

   void registerEncodedMemoryCache(CountingMemoryCache<?, ?> var1);
}
