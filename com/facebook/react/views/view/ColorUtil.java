package com.facebook.react.views.view;


public class ColorUtil {

   public static int getOpacityFromColor(int var0) {
      var0 >>>= 24;
      return var0 == 255?-1:(var0 == 0?-2:-3);
   }

   public static int multiplyColorAlpha(int var0, int var1) {
      return var1 == 255?var0:(var1 == 0?var0 & 16777215:var0 & 16777215 | (var0 >>> 24) * (var1 + (var1 >> 7)) >> 8 << 24);
   }
}
