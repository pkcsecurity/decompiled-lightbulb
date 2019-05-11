package com.facebook.litho.fresco;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

public class NoOpDrawable extends Drawable {

   public void draw(Canvas var1) {}

   public int getOpacity() {
      return 0;
   }

   public void setAlpha(int var1) {}

   public void setColorFilter(ColorFilter var1) {}
}
