package com.facebook.cache.common;

import com.facebook.cache.common.CacheErrorLogger;
import javax.annotation.Nullable;

public class NoOpCacheErrorLogger implements CacheErrorLogger {

   private static NoOpCacheErrorLogger sInstance;


   public static NoOpCacheErrorLogger getInstance() {
      synchronized(NoOpCacheErrorLogger.class){}

      NoOpCacheErrorLogger var0;
      try {
         if(sInstance == null) {
            sInstance = new NoOpCacheErrorLogger();
         }

         var0 = sInstance;
      } finally {
         ;
      }

      return var0;
   }

   public void logError(CacheErrorLogger.CacheErrorCategory var1, Class<?> var2, String var3, @Nullable Throwable var4) {}
}
