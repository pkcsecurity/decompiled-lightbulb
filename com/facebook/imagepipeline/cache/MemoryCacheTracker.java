package com.facebook.imagepipeline.cache;


public interface MemoryCacheTracker<K extends Object> {

   void onCacheHit(K var1);

   void onCacheMiss();

   void onCachePut();
}
