package com.facebook.imagepipeline.producers;

import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.EncodedImage;

public final class ThumbnailSizeChecker {

   public static final float ACCEPTABLE_REQUESTED_TO_ACTUAL_SIZE_RATIO = 1.3333334F;
   private static final int ROTATED_90_DEGREES_CLOCKWISE = 90;
   private static final int ROTATED_90_DEGREES_COUNTER_CLOCKWISE = 270;


   public static int getAcceptableSize(int var0) {
      return (int)((float)var0 * 1.3333334F);
   }

   public static boolean isImageBigEnough(int var0, int var1, ResizeOptions var2) {
      boolean var5 = false;
      boolean var4 = false;
      boolean var3;
      if(var2 == null) {
         var3 = var4;
         if((float)getAcceptableSize(var0) >= 2048.0F) {
            var3 = var4;
            if(getAcceptableSize(var1) >= 2048) {
               var3 = true;
            }
         }

         return var3;
      } else {
         var3 = var5;
         if(getAcceptableSize(var0) >= var2.width) {
            var3 = var5;
            if(getAcceptableSize(var1) >= var2.height) {
               var3 = true;
            }
         }

         return var3;
      }
   }

   public static boolean isImageBigEnough(EncodedImage var0, ResizeOptions var1) {
      if(var0 == null) {
         return false;
      } else {
         int var2 = var0.getRotationAngle();
         return var2 != 90 && var2 != 270?isImageBigEnough(var0.getWidth(), var0.getHeight(), var1):isImageBigEnough(var0.getHeight(), var0.getWidth(), var1);
      }
   }
}
