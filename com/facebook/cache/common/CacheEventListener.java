package com.facebook.cache.common;

import com.facebook.cache.common.CacheEvent;

public interface CacheEventListener {

   void onCleared();

   void onEviction(CacheEvent var1);

   void onHit(CacheEvent var1);

   void onMiss(CacheEvent var1);

   void onReadException(CacheEvent var1);

   void onWriteAttempt(CacheEvent var1);

   void onWriteException(CacheEvent var1);

   void onWriteSuccess(CacheEvent var1);

   public static enum EvictionReason {

      // $FF: synthetic field
      private static final CacheEventListener.EvictionReason[] $VALUES = new CacheEventListener.EvictionReason[]{CACHE_FULL, CONTENT_STALE, USER_FORCED, CACHE_MANAGER_TRIMMED};
      CACHE_FULL("CACHE_FULL", 0),
      CACHE_MANAGER_TRIMMED("CACHE_MANAGER_TRIMMED", 3),
      CONTENT_STALE("CONTENT_STALE", 1),
      USER_FORCED("USER_FORCED", 2);


      private EvictionReason(String var1, int var2) {}
   }
}
