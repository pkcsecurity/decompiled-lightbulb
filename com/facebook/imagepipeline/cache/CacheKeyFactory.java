package com.facebook.imagepipeline.cache;

import android.net.Uri;
import com.facebook.cache.common.CacheKey;
import com.facebook.imagepipeline.request.ImageRequest;
import javax.annotation.Nullable;

public interface CacheKeyFactory {

   CacheKey getBitmapCacheKey(ImageRequest var1, Object var2);

   CacheKey getEncodedCacheKey(ImageRequest var1, Uri var2, @Nullable Object var3);

   CacheKey getEncodedCacheKey(ImageRequest var1, @Nullable Object var2);

   CacheKey getPostprocessedBitmapCacheKey(ImageRequest var1, Object var2);
}
