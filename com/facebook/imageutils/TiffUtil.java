package com.facebook.imageutils;

import com.facebook.common.logging.FLog;
import com.facebook.imageutils.StreamProcessor;
import java.io.IOException;
import java.io.InputStream;

class TiffUtil {

   private static final Class<?> TAG = TiffUtil.class;
   public static final int TIFF_BYTE_ORDER_BIG_END = 1296891946;
   public static final int TIFF_BYTE_ORDER_LITTLE_END = 1229531648;
   public static final int TIFF_TAG_ORIENTATION = 274;
   public static final int TIFF_TYPE_SHORT = 3;


   public static int getAutoRotateAngleFromOrientation(int var0) {
      if(var0 != 3) {
         if(var0 != 6) {
            if(var0 != 8) {
               switch(var0) {
               case 0:
               case 1:
                  return 0;
               default:
                  FLog.i(TAG, "Unsupported orientation");
                  return 0;
               }
            } else {
               return 270;
            }
         } else {
            return 90;
         }
      } else {
         return 180;
      }
   }

   private static int getOrientationFromTiffEntry(InputStream var0, int var1, boolean var2) throws IOException {
      if(var1 < 10) {
         return 0;
      } else if(StreamProcessor.readPackedInt(var0, 2, var2) != 3) {
         return 0;
      } else if(StreamProcessor.readPackedInt(var0, 4, var2) != 1) {
         return 0;
      } else {
         var1 = StreamProcessor.readPackedInt(var0, 2, var2);
         StreamProcessor.readPackedInt(var0, 2, var2);
         return var1;
      }
   }

   private static int moveToTiffEntryWithTag(InputStream var0, int var1, boolean var2, int var3) throws IOException {
      if(var1 < 14) {
         return 0;
      } else {
         int var5 = StreamProcessor.readPackedInt(var0, 2, var2);
         int var4 = var1 - 2;

         for(var1 = var5; var1 > 0 && var4 >= 12; --var1) {
            var5 = StreamProcessor.readPackedInt(var0, 2, var2);
            var4 -= 2;
            if(var5 == var3) {
               return var4;
            }

            var0.skip(10L);
            var4 -= 10;
         }

         return 0;
      }
   }

   public static int readOrientationFromTIFF(InputStream var0, int var1) throws IOException {
      TiffUtil.TiffHeader var3 = new TiffUtil.TiffHeader(null);
      var1 = readTiffHeader(var0, var1, var3);
      int var2 = var3.firstIfdOffset - 8;
      if(var1 != 0 && var2 <= var1) {
         var0.skip((long)var2);
         return getOrientationFromTiffEntry(var0, moveToTiffEntryWithTag(var0, var1 - var2, var3.isLittleEndian, 274), var3.isLittleEndian);
      } else {
         return 0;
      }
   }

   private static int readTiffHeader(InputStream var0, int var1, TiffUtil.TiffHeader var2) throws IOException {
      if(var1 <= 8) {
         return 0;
      } else {
         var2.byteOrder = StreamProcessor.readPackedInt(var0, 4, false);
         if(var2.byteOrder != 1229531648 && var2.byteOrder != 1296891946) {
            FLog.e(TAG, "Invalid TIFF header");
            return 0;
         } else {
            boolean var3;
            if(var2.byteOrder == 1229531648) {
               var3 = true;
            } else {
               var3 = false;
            }

            var2.isLittleEndian = var3;
            var2.firstIfdOffset = StreamProcessor.readPackedInt(var0, 4, var2.isLittleEndian);
            var1 = var1 - 4 - 4;
            if(var2.firstIfdOffset >= 8 && var2.firstIfdOffset - 8 <= var1) {
               return var1;
            } else {
               FLog.e(TAG, "Invalid offset");
               return 0;
            }
         }
      }
   }

   static class TiffHeader {

      int byteOrder;
      int firstIfdOffset;
      boolean isLittleEndian;


      private TiffHeader() {}

      // $FF: synthetic method
      TiffHeader(Object var1) {
         this();
      }
   }
}
