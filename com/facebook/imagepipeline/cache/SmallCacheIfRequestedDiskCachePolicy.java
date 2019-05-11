package com.facebook.imagepipeline.cache;

import bolts.Task;
import com.facebook.cache.common.CacheKey;
import com.facebook.imagepipeline.cache.BufferedDiskCache;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.cache.DiskCachePolicy;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.concurrent.atomic.AtomicBoolean;

public class SmallCacheIfRequestedDiskCachePolicy implements DiskCachePolicy {

   private final CacheKeyFactory mCacheKeyFactory;
   private final BufferedDiskCache mDefaultBufferedDiskCache;
   private final BufferedDiskCache mSmallImageBufferedDiskCache;


   public SmallCacheIfRequestedDiskCachePolicy(BufferedDiskCache var1, BufferedDiskCache var2, CacheKeyFactory var3) {
      this.mDefaultBufferedDiskCache = var1;
      this.mSmallImageBufferedDiskCache = var2;
      this.mCacheKeyFactory = var3;
   }

   public Task<EncodedImage> createAndStartCacheReadTask(ImageRequest var1, Object var2, AtomicBoolean var3) {
      CacheKey var4 = this.mCacheKeyFactory.getEncodedCacheKey(var1, var2);
      return var1.getCacheChoice() == ImageRequest.CacheChoice.SMALL?this.mSmallImageBufferedDiskCache.get(var4, var3):this.mDefaultBufferedDiskCache.get(var4, var3);
   }

   public ImageRequest.CacheChoice getCacheChoiceForResult(ImageRequest var1, EncodedImage var2) {
      return var1.getCacheChoice() == null?ImageRequest.CacheChoice.DEFAULT:var1.getCacheChoice();
   }

   public void writeToCache(EncodedImage var1, ImageRequest var2, Object var3) {
      CacheKey var4 = this.mCacheKeyFactory.getEncodedCacheKey(var2, var3);
      if(this.getCacheChoiceForResult(var2, var1) == ImageRequest.CacheChoice.SMALL) {
         this.mSmallImageBufferedDiskCache.put(var4, var1);
      } else {
         this.mDefaultBufferedDiskCache.put(var4, var1);
      }
   }
}
