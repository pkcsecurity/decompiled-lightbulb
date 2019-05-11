package com.facebook.litho;

import android.content.res.Configuration;
import com.facebook.litho.LruResourceCache;
import javax.annotation.Nullable;

public abstract class ResourceCache {

   private static ResourceCache latest;
   private final Configuration mConfiguration;


   protected ResourceCache(Configuration var1) {
      this.mConfiguration = var1;
   }

   static ResourceCache getLatest(Configuration var0) {
      synchronized(ResourceCache.class){}

      ResourceCache var3;
      try {
         if(latest == null || !latest.mConfiguration.equals(var0)) {
            latest = new LruResourceCache(new Configuration(var0));
         }

         var3 = latest;
      } finally {
         ;
      }

      return var3;
   }

   @Nullable
   abstract <T extends Object> T get(int var1);

   abstract void put(int var1, Object var2);
}
