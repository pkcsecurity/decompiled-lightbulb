package com.facebook.imagepipeline.producers;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.logging.FLog;
import com.facebook.imageformat.DefaultImageFormats;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.request.ImageRequest;

public class DownsampleUtil {

   private static final int DEFAULT_SAMPLE_SIZE = 1;
   private static final float INTERVAL_ROUNDING = 0.33333334F;


   @VisibleForTesting
   static float determineDownsampleRatio(ImageRequest var0, EncodedImage var1) {
      Preconditions.checkArgument(EncodedImage.isMetaDataAvailable(var1));
      ResizeOptions var7 = var0.getResizeOptions();
      if(var7 != null && var7.height > 0 && var7.width > 0 && var1.getWidth() != 0 && var1.getHeight() != 0) {
         int var5 = getRotationAngle(var0, var1);
         boolean var6;
         if(var5 != 90 && var5 != 270) {
            var6 = false;
         } else {
            var6 = true;
         }

         if(var6) {
            var5 = var1.getHeight();
         } else {
            var5 = var1.getWidth();
         }

         int var8;
         if(var6) {
            var8 = var1.getWidth();
         } else {
            var8 = var1.getHeight();
         }

         float var2 = (float)var7.width / (float)var5;
         float var3 = (float)var7.height / (float)var8;
         float var4 = Math.max(var2, var3);
         FLog.v("DownsampleUtil", "Downsample - Specified size: %dx%d, image size: %dx%d ratio: %.1f x %.1f, ratio: %.3f for %s", new Object[]{Integer.valueOf(var7.width), Integer.valueOf(var7.height), Integer.valueOf(var5), Integer.valueOf(var8), Float.valueOf(var2), Float.valueOf(var3), Float.valueOf(var4), var0.getSourceUri().toString()});
         return var4;
      } else {
         return 1.0F;
      }
   }

   public static int determineSampleSize(ImageRequest var0, EncodedImage var1) {
      if(!EncodedImage.isMetaDataAvailable(var1)) {
         return 1;
      } else {
         float var2 = determineDownsampleRatio(var0, var1);
         int var3;
         if(var1.getImageFormat() == DefaultImageFormats.JPEG) {
            var3 = ratioToSampleSizeJPEG(var2);
         } else {
            var3 = ratioToSampleSize(var2);
         }

         int var4 = Math.max(var1.getHeight(), var1.getWidth());
         ResizeOptions var5 = var0.getResizeOptions();
         if(var5 != null) {
            var2 = var5.maxBitmapSize;
         } else {
            var2 = 2048.0F;
         }

         while((float)(var4 / var3) > var2) {
            if(var1.getImageFormat() == DefaultImageFormats.JPEG) {
               var3 *= 2;
            } else {
               ++var3;
            }
         }

         return var3;
      }
   }

   private static int getRotationAngle(ImageRequest var0, EncodedImage var1) {
      boolean var4 = var0.getRotationOptions().useImageMetadata();
      boolean var3 = false;
      if(!var4) {
         return 0;
      } else {
         int var2 = var1.getRotationAngle();
         if(var2 == 0 || var2 == 90 || var2 == 180 || var2 == 270) {
            var3 = true;
         }

         Preconditions.checkArgument(var3);
         return var2;
      }
   }

   @VisibleForTesting
   static int ratioToSampleSize(float var0) {
      if(var0 > 0.6666667F) {
         return 1;
      } else {
         int var5 = 2;

         while(true) {
            double var1 = (double)var5;
            double var3 = 1.0D / (Math.pow(var1, 2.0D) - var1);
            if(1.0D / var1 + var3 * 0.3333333432674408D <= (double)var0) {
               return var5 - 1;
            }

            ++var5;
         }
      }
   }

   @VisibleForTesting
   static int ratioToSampleSizeJPEG(float var0) {
      if(var0 > 0.6666667F) {
         return 1;
      } else {
         int var3 = 2;

         while(true) {
            int var4 = var3 * 2;
            double var1 = 1.0D / (double)var4;
            if(var1 + 0.3333333432674408D * var1 <= (double)var0) {
               return var3;
            }

            var3 = var4;
         }
      }
   }

   @VisibleForTesting
   static int roundToPowerOfTwo(int var0) {
      int var1;
      for(var1 = 1; var1 < var0; var1 *= 2) {
         ;
      }

      return var1;
   }
}
