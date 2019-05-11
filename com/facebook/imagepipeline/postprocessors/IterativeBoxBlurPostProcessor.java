package com.facebook.imagepipeline.postprocessors;

import android.graphics.Bitmap;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.common.internal.Preconditions;
import com.facebook.imagepipeline.nativecode.NativeBlurFilter;
import com.facebook.imagepipeline.request.BasePostprocessor;
import java.util.Locale;
import javax.annotation.Nullable;

public class IterativeBoxBlurPostProcessor extends BasePostprocessor {

   private static final int DEFAULT_ITERATIONS = 3;
   private final int mBlurRadius;
   private CacheKey mCacheKey;
   private final int mIterations;


   public IterativeBoxBlurPostProcessor(int var1) {
      this(3, var1);
   }

   public IterativeBoxBlurPostProcessor(int var1, int var2) {
      boolean var4 = false;
      boolean var3;
      if(var1 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);
      var3 = var4;
      if(var2 > 0) {
         var3 = true;
      }

      Preconditions.checkArgument(var3);
      this.mIterations = var1;
      this.mBlurRadius = var2;
   }

   @Nullable
   public CacheKey getPostprocessorCacheKey() {
      if(this.mCacheKey == null) {
         this.mCacheKey = new SimpleCacheKey(String.format((Locale)null, "i%dr%d", new Object[]{Integer.valueOf(this.mIterations), Integer.valueOf(this.mBlurRadius)}));
      }

      return this.mCacheKey;
   }

   public void process(Bitmap var1) {
      NativeBlurFilter.iterativeBoxBlur(var1, this.mIterations, this.mBlurRadius);
   }
}
