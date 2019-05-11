package com.facebook.imagepipeline.cache;

import bolts.Continuation;
import bolts.Task;
import com.facebook.cache.common.CacheKey;
import com.facebook.imagepipeline.cache.BufferedDiskCache;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.cache.DiskCachePolicy;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicBoolean;

public class SplitCachesByImageSizeDiskCachePolicy implements DiskCachePolicy {

   private final CacheKeyFactory mCacheKeyFactory;
   private final BufferedDiskCache mDefaultBufferedDiskCache;
   private final int mForceSmallCacheThresholdBytes;
   private final BufferedDiskCache mSmallImageBufferedDiskCache;


   public SplitCachesByImageSizeDiskCachePolicy(BufferedDiskCache var1, BufferedDiskCache var2, CacheKeyFactory var3, int var4) {
      this.mDefaultBufferedDiskCache = var1;
      this.mSmallImageBufferedDiskCache = var2;
      this.mCacheKeyFactory = var3;
      this.mForceSmallCacheThresholdBytes = var4;
   }

   private static boolean isTaskCancelled(Task<?> var0) {
      return var0.c() || var0.d() && var0.f() instanceof CancellationException;
   }

   public Task<EncodedImage> createAndStartCacheReadTask(ImageRequest var1, Object var2, final AtomicBoolean var3) {
      final CacheKey var6 = this.mCacheKeyFactory.getEncodedCacheKey(var1, var2);
      boolean var4 = this.mSmallImageBufferedDiskCache.containsSync(var6);
      boolean var5 = this.mDefaultBufferedDiskCache.containsSync(var6);
      final BufferedDiskCache var7;
      BufferedDiskCache var8;
      if(!var4 && var5) {
         var8 = this.mDefaultBufferedDiskCache;
         var7 = this.mSmallImageBufferedDiskCache;
      } else {
         var8 = this.mSmallImageBufferedDiskCache;
         var7 = this.mDefaultBufferedDiskCache;
      }

      return var8.get(var6, var3).b(new Continuation() {
         public Task<EncodedImage> then(Task<EncodedImage> var1) throws Exception {
            return !SplitCachesByImageSizeDiskCachePolicy.isTaskCancelled(var1)?(!var1.d() && var1.e() != null?var1:var7.get(var6, var3)):var1;
         }
      });
   }

   public ImageRequest.CacheChoice getCacheChoiceForResult(ImageRequest var1, EncodedImage var2) {
      int var3 = var2.getSize();
      return var3 >= 0 && var3 < this.mForceSmallCacheThresholdBytes?ImageRequest.CacheChoice.SMALL:ImageRequest.CacheChoice.DEFAULT;
   }

   public void writeToCache(EncodedImage var1, ImageRequest var2, Object var3) {
      CacheKey var4 = this.mCacheKeyFactory.getEncodedCacheKey(var2, var3);
      switch(null.$SwitchMap$com$facebook$imagepipeline$request$ImageRequest$CacheChoice[this.getCacheChoiceForResult(var2, var1).ordinal()]) {
      case 1:
         this.mDefaultBufferedDiskCache.put(var4, var1);
         return;
      case 2:
         this.mSmallImageBufferedDiskCache.put(var4, var1);
         return;
      default:
      }
   }
}
