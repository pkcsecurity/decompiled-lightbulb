package com.facebook.imagepipeline.request;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.nativecode.Bitmaps;
import com.facebook.imagepipeline.request.Postprocessor;
import javax.annotation.Nullable;

public abstract class BasePostprocessor implements Postprocessor {

   public static final Config FALLBACK_BITMAP_CONFIGURATION = Config.ARGB_8888;


   private static void internalCopyBitmap(Bitmap var0, Bitmap var1) {
      if(var0.getConfig() == var1.getConfig()) {
         Bitmaps.copyBitmap(var0, var1);
      } else {
         (new Canvas(var0)).drawBitmap(var1, 0.0F, 0.0F, (Paint)null);
      }
   }

   public String getName() {
      return "Unknown postprocessor";
   }

   @Nullable
   public CacheKey getPostprocessorCacheKey() {
      return null;
   }

   public CloseableReference<Bitmap> process(Bitmap var1, PlatformBitmapFactory var2) {
      Config var5 = var1.getConfig();
      int var3 = var1.getWidth();
      int var4 = var1.getHeight();
      if(var5 == null) {
         var5 = FALLBACK_BITMAP_CONFIGURATION;
      }

      CloseableReference var9 = var2.createBitmapInternal(var3, var4, var5);

      CloseableReference var8;
      try {
         this.process((Bitmap)var9.get(), var1);
         var8 = CloseableReference.cloneOrNull(var9);
      } finally {
         CloseableReference.closeSafely(var9);
      }

      return var8;
   }

   public void process(Bitmap var1) {}

   public void process(Bitmap var1, Bitmap var2) {
      internalCopyBitmap(var1, var2);
      this.process(var1);
   }
}
