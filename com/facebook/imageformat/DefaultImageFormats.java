package com.facebook.imageformat;

import com.facebook.common.internal.ImmutableList;
import com.facebook.imageformat.ImageFormat;
import java.util.ArrayList;
import java.util.List;

public final class DefaultImageFormats {

   public static final ImageFormat BMP = new ImageFormat("BMP", "bmp");
   public static final ImageFormat GIF = new ImageFormat("GIF", "gif");
   public static final ImageFormat JPEG = new ImageFormat("JPEG", "jpeg");
   public static final ImageFormat PNG = new ImageFormat("PNG", "png");
   public static final ImageFormat WEBP_ANIMATED = new ImageFormat("WEBP_ANIMATED", "webp");
   public static final ImageFormat WEBP_EXTENDED = new ImageFormat("WEBP_EXTENDED", "webp");
   public static final ImageFormat WEBP_EXTENDED_WITH_ALPHA = new ImageFormat("WEBP_EXTENDED_WITH_ALPHA", "webp");
   public static final ImageFormat WEBP_LOSSLESS = new ImageFormat("WEBP_LOSSLESS", "webp");
   public static final ImageFormat WEBP_SIMPLE = new ImageFormat("WEBP_SIMPLE", "webp");
   private static ImmutableList<ImageFormat> sAllDefaultFormats;


   public static List<ImageFormat> getDefaultFormats() {
      if(sAllDefaultFormats == null) {
         ArrayList var0 = new ArrayList(9);
         var0.add(JPEG);
         var0.add(PNG);
         var0.add(GIF);
         var0.add(BMP);
         var0.add(WEBP_SIMPLE);
         var0.add(WEBP_LOSSLESS);
         var0.add(WEBP_EXTENDED);
         var0.add(WEBP_EXTENDED_WITH_ALPHA);
         var0.add(WEBP_ANIMATED);
         sAllDefaultFormats = ImmutableList.copyOf(var0);
      }

      return sAllDefaultFormats;
   }

   public static boolean isStaticWebpFormat(ImageFormat var0) {
      return var0 == WEBP_SIMPLE || var0 == WEBP_LOSSLESS || var0 == WEBP_EXTENDED || var0 == WEBP_EXTENDED_WITH_ALPHA;
   }

   public static boolean isWebpFormat(ImageFormat var0) {
      return isStaticWebpFormat(var0) || var0 == WEBP_ANIMATED;
   }
}
