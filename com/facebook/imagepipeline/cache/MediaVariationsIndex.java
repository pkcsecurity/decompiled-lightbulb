package com.facebook.imagepipeline.cache;

import bolts.Task;
import com.facebook.cache.common.CacheKey;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.MediaVariations;

public interface MediaVariationsIndex {

   Task<MediaVariations> getCachedVariants(String var1, MediaVariations.Builder var2);

   void saveCachedVariant(String var1, ImageRequest.CacheChoice var2, CacheKey var3, EncodedImage var4);
}
