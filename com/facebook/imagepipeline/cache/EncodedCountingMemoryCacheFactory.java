package com.facebook.imagepipeline.cache;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.Supplier;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.cache.CountingMemoryCache;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.cache.NativeMemoryCacheTrimStrategy;
import com.facebook.imagepipeline.cache.ValueDescriptor;

public class EncodedCountingMemoryCacheFactory {

   public static CountingMemoryCache<CacheKey, PooledByteBuffer> get(Supplier<MemoryCacheParams> var0, MemoryTrimmableRegistry var1, PlatformBitmapFactory var2) {
      CountingMemoryCache var3 = new CountingMemoryCache(new ValueDescriptor() {
         public int getSizeInBytes(PooledByteBuffer var1) {
            return var1.size();
         }
      }, new NativeMemoryCacheTrimStrategy(), var0, var2, false);
      var1.registerMemoryTrimmable(var3);
      return var3;
   }
}
