package com.facebook.imagepipeline.cache;

import android.net.Uri;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.imagepipeline.cache.BitmapMemoryCacheKey;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.Postprocessor;
import javax.annotation.Nullable;

public class DefaultCacheKeyFactory implements CacheKeyFactory {

   private static DefaultCacheKeyFactory sInstance;


   public static DefaultCacheKeyFactory getInstance() {
      synchronized(DefaultCacheKeyFactory.class){}

      DefaultCacheKeyFactory var0;
      try {
         if(sInstance == null) {
            sInstance = new DefaultCacheKeyFactory();
         }

         var0 = sInstance;
      } finally {
         ;
      }

      return var0;
   }

   public CacheKey getBitmapCacheKey(ImageRequest var1, Object var2) {
      return new BitmapMemoryCacheKey(this.getCacheKeySourceUri(var1.getSourceUri()).toString(), var1.getResizeOptions(), var1.getRotationOptions(), var1.getImageDecodeOptions(), (CacheKey)null, (String)null, var2);
   }

   protected Uri getCacheKeySourceUri(Uri var1) {
      return var1;
   }

   public CacheKey getEncodedCacheKey(ImageRequest var1, Uri var2, @Nullable Object var3) {
      return new SimpleCacheKey(this.getCacheKeySourceUri(var2).toString());
   }

   public CacheKey getEncodedCacheKey(ImageRequest var1, @Nullable Object var2) {
      return this.getEncodedCacheKey(var1, var1.getSourceUri(), var2);
   }

   public CacheKey getPostprocessedBitmapCacheKey(ImageRequest var1, Object var2) {
      Postprocessor var4 = var1.getPostprocessor();
      CacheKey var3;
      Object var5;
      if(var4 != null) {
         var3 = var4.getPostprocessorCacheKey();
         var5 = var4.getClass().getName();
      } else {
         var3 = null;
         var5 = var3;
      }

      return new BitmapMemoryCacheKey(this.getCacheKeySourceUri(var1.getSourceUri()).toString(), var1.getResizeOptions(), var1.getRotationOptions(), var1.getImageDecodeOptions(), var3, (String)var5, var2);
   }
}
