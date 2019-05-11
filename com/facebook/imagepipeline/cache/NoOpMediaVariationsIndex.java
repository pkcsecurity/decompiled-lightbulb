package com.facebook.imagepipeline.cache;

import bolts.Task;
import com.facebook.cache.common.CacheKey;
import com.facebook.imagepipeline.cache.MediaVariationsIndex;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.MediaVariations;

public class NoOpMediaVariationsIndex implements MediaVariationsIndex {

   public Task<MediaVariations> getCachedVariants(String var1, MediaVariations.Builder var2) {
      return Task.a((Object)null);
   }

   public void saveCachedVariant(String var1, ImageRequest.CacheChoice var2, CacheKey var3, EncodedImage var4) {}
}
