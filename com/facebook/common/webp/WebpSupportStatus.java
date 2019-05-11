package com.facebook.common.webp;

import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Build.VERSION;
import android.util.Base64;
import com.facebook.common.webp.WebpBitmapFactory;
import java.io.UnsupportedEncodingException;

public class WebpSupportStatus {

   private static final int EXTENDED_WEBP_HEADER_LENGTH = 21;
   private static final int SIMPLE_WEBP_HEADER_LENGTH = 20;
   private static final String VP8X_WEBP_BASE64 = "UklGRkoAAABXRUJQVlA4WAoAAAAQAAAAAAAAAAAAQUxQSAwAAAARBxAR/Q9ERP8DAABWUDggGAAAABQBAJ0BKgEAAQAAAP4AAA3AAP7mtQAAAA==";
   private static final byte[] WEBP_NAME_BYTES;
   private static final byte[] WEBP_RIFF_BYTES;
   private static final byte[] WEBP_VP8L_BYTES;
   private static final byte[] WEBP_VP8X_BYTES;
   private static final byte[] WEBP_VP8_BYTES;
   public static final boolean sIsExtendedWebpSupported;
   public static final boolean sIsSimpleWebpSupported;
   public static final boolean sIsWebpSupportRequired;
   public static WebpBitmapFactory sWebpBitmapFactory;
   private static boolean sWebpLibraryChecked;


   static {
      int var0 = VERSION.SDK_INT;
      boolean var2 = false;
      boolean var1;
      if(var0 <= 17) {
         var1 = true;
      } else {
         var1 = false;
      }

      sIsWebpSupportRequired = var1;
      var1 = var2;
      if(VERSION.SDK_INT >= 14) {
         var1 = true;
      }

      sIsSimpleWebpSupported = var1;
      sIsExtendedWebpSupported = isExtendedWebpSupported();
      WEBP_RIFF_BYTES = asciiBytes("RIFF");
      WEBP_NAME_BYTES = asciiBytes("WEBP");
      WEBP_VP8_BYTES = asciiBytes("VP8 ");
      WEBP_VP8L_BYTES = asciiBytes("VP8L");
      WEBP_VP8X_BYTES = asciiBytes("VP8X");
   }

   private static byte[] asciiBytes(String var0) {
      try {
         byte[] var2 = var0.getBytes("ASCII");
         return var2;
      } catch (UnsupportedEncodingException var1) {
         throw new RuntimeException("ASCII not found!", var1);
      }
   }

   public static boolean isAnimatedWebpHeader(byte[] var0, int var1) {
      boolean var4 = matchBytePattern(var0, var1 + 12, WEBP_VP8X_BYTES);
      byte var5 = var0[var1 + 20];
      boolean var3 = false;
      boolean var6;
      if((var5 & 2) == 2) {
         var6 = true;
      } else {
         var6 = false;
      }

      boolean var2 = var3;
      if(var4) {
         var2 = var3;
         if(var6) {
            var2 = true;
         }
      }

      return var2;
   }

   public static boolean isExtendedWebpHeader(byte[] var0, int var1, int var2) {
      return var2 >= 21 && matchBytePattern(var0, var1 + 12, WEBP_VP8X_BYTES);
   }

   public static boolean isExtendedWebpHeaderWithAlpha(byte[] var0, int var1) {
      boolean var4 = matchBytePattern(var0, var1 + 12, WEBP_VP8X_BYTES);
      byte var5 = var0[var1 + 20];
      boolean var3 = false;
      boolean var6;
      if((var5 & 16) == 16) {
         var6 = true;
      } else {
         var6 = false;
      }

      boolean var2 = var3;
      if(var4) {
         var2 = var3;
         if(var6) {
            var2 = true;
         }
      }

      return var2;
   }

   private static boolean isExtendedWebpSupported() {
      if(VERSION.SDK_INT < 17) {
         return false;
      } else {
         if(VERSION.SDK_INT == 17) {
            byte[] var0 = Base64.decode("UklGRkoAAABXRUJQVlA4WAoAAAAQAAAAAAAAAAAAQUxQSAwAAAARBxAR/Q9ERP8DAABWUDggGAAAABQBAJ0BKgEAAQAAAP4AAA3AAP7mtQAAAA==", 0);
            Options var1 = new Options();
            var1.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(var0, 0, var0.length, var1);
            if(var1.outHeight != 1 || var1.outWidth != 1) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean isLosslessWebpHeader(byte[] var0, int var1) {
      return matchBytePattern(var0, var1 + 12, WEBP_VP8L_BYTES);
   }

   public static boolean isSimpleWebpHeader(byte[] var0, int var1) {
      return matchBytePattern(var0, var1 + 12, WEBP_VP8_BYTES);
   }

   public static boolean isWebpHeader(byte[] var0, int var1, int var2) {
      return var2 >= 20 && matchBytePattern(var0, var1, WEBP_RIFF_BYTES) && matchBytePattern(var0, var1 + 8, WEBP_NAME_BYTES);
   }

   public static boolean isWebpSupportedByPlatform(byte[] var0, int var1, int var2) {
      return isSimpleWebpHeader(var0, var1)?sIsSimpleWebpSupported:(isLosslessWebpHeader(var0, var1)?sIsExtendedWebpSupported:(isExtendedWebpHeader(var0, var1, var2)?(isAnimatedWebpHeader(var0, var1)?false:sIsExtendedWebpSupported):false));
   }

   public static WebpBitmapFactory loadWebpBitmapFactoryIfExists() {
      if(sWebpLibraryChecked) {
         return sWebpBitmapFactory;
      } else {
         WebpBitmapFactory var0 = null;

         label15: {
            WebpBitmapFactory var1;
            try {
               var1 = (WebpBitmapFactory)Class.forName("com.facebook.webpsupport.WebpBitmapFactoryImpl").newInstance();
            } catch (Throwable var2) {
               break label15;
            }

            var0 = var1;
         }

         sWebpLibraryChecked = true;
         return var0;
      }
   }

   private static boolean matchBytePattern(byte[] var0, int var1, byte[] var2) {
      if(var2 != null) {
         if(var0 == null) {
            return false;
         } else if(var2.length + var1 > var0.length) {
            return false;
         } else {
            for(int var3 = 0; var3 < var2.length; ++var3) {
               if(var0[var3 + var1] != var2[var3]) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }
}
