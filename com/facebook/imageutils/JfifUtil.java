package com.facebook.imageutils;

import com.facebook.common.internal.Preconditions;
import com.facebook.imageutils.StreamProcessor;
import com.facebook.imageutils.TiffUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class JfifUtil {

   public static final int APP1_EXIF_MAGIC = 1165519206;
   public static final int MARKER_APP1 = 225;
   public static final int MARKER_EOI = 217;
   public static final int MARKER_ESCAPE_BYTE = 0;
   public static final int MARKER_FIRST_BYTE = 255;
   public static final int MARKER_RST0 = 208;
   public static final int MARKER_RST7 = 215;
   public static final int MARKER_SOFn = 192;
   public static final int MARKER_SOI = 216;
   public static final int MARKER_SOS = 218;
   public static final int MARKER_TEM = 1;


   public static int getAutoRotateAngleFromOrientation(int var0) {
      return TiffUtil.getAutoRotateAngleFromOrientation(var0);
   }

   public static int getOrientation(InputStream param0) {
      // $FF: Couldn't be decompiled
   }

   public static int getOrientation(byte[] var0) {
      return getOrientation((InputStream)(new ByteArrayInputStream(var0)));
   }

   private static boolean isSOFn(int var0) {
      switch(var0) {
      case 192:
      case 193:
      case 194:
      case 195:
      case 197:
      case 198:
      case 199:
      case 201:
      case 202:
      case 203:
      case 205:
      case 206:
      case 207:
         return true;
      case 196:
      case 200:
      case 204:
      default:
         return false;
      }
   }

   private static int moveToAPP1EXIF(InputStream var0) throws IOException {
      if(moveToMarker(var0, 225)) {
         int var1 = StreamProcessor.readPackedInt(var0, 2, false) - 2;
         if(var1 > 6) {
            int var2 = StreamProcessor.readPackedInt(var0, 4, false);
            int var3 = StreamProcessor.readPackedInt(var0, 2, false);
            if(var2 == 1165519206 && var3 == 0) {
               return var1 - 4 - 2;
            }
         }
      }

      return 0;
   }

   public static boolean moveToMarker(InputStream var0, int var1) throws IOException {
      Preconditions.checkNotNull(var0);

      while(StreamProcessor.readPackedInt(var0, 1, false) == 255) {
         int var2;
         for(var2 = 255; var2 == 255; var2 = StreamProcessor.readPackedInt(var0, 1, false)) {
            ;
         }

         if(var1 == 192 && isSOFn(var2)) {
            return true;
         }

         if(var2 == var1) {
            return true;
         }

         if(var2 != 216 && var2 != 1) {
            if(var2 == 217) {
               return false;
            }

            if(var2 == 218) {
               return false;
            }

            var0.skip((long)(StreamProcessor.readPackedInt(var0, 2, false) - 2));
         }
      }

      return false;
   }
}
