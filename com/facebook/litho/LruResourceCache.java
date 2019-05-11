package com.facebook.litho;

import android.content.res.Configuration;
import android.support.v4.util.LruCache;
import com.facebook.litho.ResourceCache;
import javax.annotation.Nullable;

class LruResourceCache extends ResourceCache {

   private final LruCache<Integer, Object> mCache = new LruCache(500) {
      protected int sizeOf(Integer var1, Object var2) {
         return var2 instanceof String?((String)var2).length():1;
      }
   };


   LruResourceCache(Configuration var1) {
      super(var1);
   }

   @Nullable
   <T extends Object> T get(int var1) {
      return this.mCache.get(Integer.valueOf(var1));
   }

   void put(int var1, Object var2) {
      this.mCache.put(Integer.valueOf(var1), var2);
   }
}
