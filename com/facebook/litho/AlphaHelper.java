package com.facebook.litho;

import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;

public class AlphaHelper {

   public static float getAlpha(Drawable var0) {
      return VERSION.SDK_INT >= 19?(float)var0.getAlpha() / 255.0F:1.0F;
   }

   public static void setAlpha(Drawable var0, float var1) {
      var0.setAlpha((int)(var1 * 255.0F));
   }
}
