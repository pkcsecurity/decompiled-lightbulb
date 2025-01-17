package com.facebook.imagepipeline.cache;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.Supplier;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.cache.BitmapMemoryCacheTrimStrategy;
import com.facebook.imagepipeline.cache.CountingMemoryCache;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.cache.ValueDescriptor;
import com.facebook.imagepipeline.image.CloseableImage;

public class BitmapCountingMemoryCacheFactory {

   public static CountingMemoryCache<CacheKey, CloseableImage> get(Supplier<MemoryCacheParams> var0, MemoryTrimmableRegistry var1, PlatformBitmapFactory var2, boolean var3) {
      CountingMemoryCache var4 = new CountingMemoryCache(new ValueDescriptor() {
         public int getSizeInBytes(CloseableImage var1) {
            return var1.getSizeInBytes();
         }
      }, new BitmapMemoryCacheTrimStrategy(), var0, var2, var3);
      var1.registerMemoryTrimmable(var4);
      return var4;
   }
}
