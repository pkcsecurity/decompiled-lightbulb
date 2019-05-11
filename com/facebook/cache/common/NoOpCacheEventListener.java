package com.facebook.cache.common;

import com.facebook.cache.common.CacheEvent;
import com.facebook.cache.common.CacheEventListener;

public class NoOpCacheEventListener implements CacheEventListener {

   private static NoOpCacheEventListener sInstance;


   public static NoOpCacheEventListener getInstance() {
      synchronized(NoOpCacheEventListener.class){}

      NoOpCacheEventListener var0;
      try {
         if(sInstance == null) {
            sInstance = new NoOpCacheEventListener();
         }

         var0 = sInstance;
      } finally {
         ;
      }

      return var0;
   }

   public void onCleared() {}

   public void onEviction(CacheEvent var1) {}

   public void onHit(CacheEvent var1) {}

   public void onMiss(CacheEvent var1) {}

   public void onReadException(CacheEvent var1) {}

   public void onWriteAttempt(CacheEvent var1) {}

   public void onWriteException(CacheEvent var1) {}

   public void onWriteSuccess(CacheEvent var1) {}
}
