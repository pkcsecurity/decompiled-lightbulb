package com.facebook.fbui.textlayoutbuilder.util;

import android.os.Build.VERSION;
import android.text.Layout;
import android.text.StaticLayout;

public class LayoutMeasureUtil {

   public static int getHeight(Layout var0) {
      byte var3 = 0;
      if(var0 == null) {
         return 0;
      } else {
         int var2 = var3;
         if(VERSION.SDK_INT < 20) {
            var2 = var3;
            if(var0 instanceof StaticLayout) {
               var2 = var0.getLineAscent(var0.getLineCount() - 1);
               float var1 = (float)(var0.getLineDescent(var0.getLineCount() - 1) - var2);
               var1 -= (var1 - var0.getSpacingAdd()) / var0.getSpacingMultiplier();
               if(var1 >= 0.0F) {
                  var2 = (int)((double)var1 + 0.5D);
               } else {
                  var2 = -((int)((double)(-var1) + 0.5D));
               }
            }
         }

         return var0.getHeight() - var2;
      }
   }

   public static int getWidth(Layout var0) {
      int var1 = 0;
      if(var0 == null) {
         return 0;
      } else {
         int var3 = var0.getLineCount();

         int var2;
         for(var2 = 0; var1 < var3; ++var1) {
            var2 = Math.max(var2, (int)var0.getLineRight(var1));
         }

         return var2;
      }
   }
}
