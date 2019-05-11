package com.facebook.imagepipeline.cache;

import bolts.Task;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.concurrent.atomic.AtomicBoolean;

public interface DiskCachePolicy {

   Task<EncodedImage> createAndStartCacheReadTask(ImageRequest var1, Object var2, AtomicBoolean var3);

   ImageRequest.CacheChoice getCacheChoiceForResult(ImageRequest var1, EncodedImage var2);

   void writeToCache(EncodedImage var1, ImageRequest var2, Object var3);
}
