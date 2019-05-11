package com.facebook.imageformat;

import com.facebook.common.internal.Ints;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.webp.WebpSupportStatus;
import com.facebook.imageformat.DefaultImageFormats;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imageformat.ImageFormatCheckerUtils;
import javax.annotation.Nullable;

public class DefaultImageFormatChecker implements ImageFormat.FormatChecker {

   private static final byte[] BMP_HEADER = ImageFormatCheckerUtils.asciiBytes("BM");
   private static final int BMP_HEADER_LENGTH = BMP_HEADER.length;
   private static final int EXTENDED_WEBP_HEADER_LENGTH = 21;
   private static final byte[] GIF_HEADER_87A = ImageFormatCheckerUtils.asciiBytes("GIF87a");
   private static final byte[] GIF_HEADER_89A = ImageFormatCheckerUtils.asciiBytes("GIF89a");
   private static final int GIF_HEADER_LENGTH = 6;
   private static final byte[] JPEG_HEADER = new byte[]{(byte)-1, (byte)-40, (byte)-1};
   private static final int JPEG_HEADER_LENGTH = JPEG_HEADER.length;
   private static final byte[] PNG_HEADER = new byte[]{(byte)-119, (byte)80, (byte)78, (byte)71, (byte)13, (byte)10, (byte)26, (byte)10};
   private static final int PNG_HEADER_LENGTH = PNG_HEADER.length;
   private static final int SIMPLE_WEBP_HEADER_LENGTH = 20;
   final int MAX_HEADER_LENGTH;


   public DefaultImageFormatChecker() {
      this.MAX_HEADER_LENGTH = Ints.max(new int[]{21, 20, JPEG_HEADER_LENGTH, PNG_HEADER_LENGTH, 6, BMP_HEADER_LENGTH});
   }

   private static ImageFormat getWebpFormat(byte[] var0, int var1) {
      Preconditions.checkArgument(WebpSupportStatus.isWebpHeader(var0, 0, var1));
      return WebpSupportStatus.isSimpleWebpHeader(var0, 0)?DefaultImageFormats.WEBP_SIMPLE:(WebpSupportStatus.isLosslessWebpHeader(var0, 0)?DefaultImageFormats.WEBP_LOSSLESS:(WebpSupportStatus.isExtendedWebpHeader(var0, 0, var1)?(WebpSupportStatus.isAnimatedWebpHeader(var0, 0)?DefaultImageFormats.WEBP_ANIMATED:(WebpSupportStatus.isExtendedWebpHeaderWithAlpha(var0, 0)?DefaultImageFormats.WEBP_EXTENDED_WITH_ALPHA:DefaultImageFormats.WEBP_EXTENDED)):ImageFormat.UNKNOWN));
   }

   private static boolean isBmpHeader(byte[] var0, int var1) {
      return var1 < BMP_HEADER.length?false:ImageFormatCheckerUtils.startsWithPattern(var0, BMP_HEADER);
   }

   private static boolean isGifHeader(byte[] var0, int var1) {
      boolean var2 = false;
      if(var1 < 6) {
         return false;
      } else {
         if(ImageFormatCheckerUtils.startsWithPattern(var0, GIF_HEADER_87A) || ImageFormatCheckerUtils.startsWithPattern(var0, GIF_HEADER_89A)) {
            var2 = true;
         }

         return var2;
      }
   }

   private static boolean isJpegHeader(byte[] var0, int var1) {
      return var1 >= JPEG_HEADER.length && ImageFormatCheckerUtils.startsWithPattern(var0, JPEG_HEADER);
   }

   private static boolean isPngHeader(byte[] var0, int var1) {
      return var1 >= PNG_HEADER.length && ImageFormatCheckerUtils.startsWithPattern(var0, PNG_HEADER);
   }

   @Nullable
   public final ImageFormat determineFormat(byte[] var1, int var2) {
      Preconditions.checkNotNull(var1);
      return WebpSupportStatus.isWebpHeader(var1, 0, var2)?getWebpFormat(var1, var2):(isJpegHeader(var1, var2)?DefaultImageFormats.JPEG:(isPngHeader(var1, var2)?DefaultImageFormats.PNG:(isGifHeader(var1, var2)?DefaultImageFormats.GIF:(isBmpHeader(var1, var2)?DefaultImageFormats.BMP:ImageFormat.UNKNOWN))));
   }

   public int getHeaderSize() {
      return this.MAX_HEADER_LENGTH;
   }
}
