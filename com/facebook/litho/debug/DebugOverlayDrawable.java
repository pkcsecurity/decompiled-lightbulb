package com.facebook.litho.debug;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import java.util.List;

class DebugOverlayDrawable extends Drawable {

   private static final int BOX_HEIGHT = 100;
   private static final int COLOR_GREEN_OPAQUE = Color.parseColor("#CC00FF00");
   private static final int COLOR_GREEN_TRANSPARENT = Color.parseColor("#2200FF00");
   private static final int COLOR_RED_OPAQUE = Color.parseColor("#CCFF0000");
   private static final int COLOR_RED_TRANSPARENT = Color.parseColor("#22FF0000");
   private static final float TEXT_SIZE = 80.0F;
   private static final int TEXT_THRESHOLD = 3;
   private final Paint colorPaint = new Paint();
   private final List<Boolean> mainThreadCalculations;
   private final int overlayColor;
   private final String text;
   private final Paint textPaint = new Paint();


   DebugOverlayDrawable(List<Boolean> var1) {
      this.textPaint.setColor(-16777216);
      this.textPaint.setAntiAlias(true);
      this.textPaint.setStyle(Style.FILL);
      this.textPaint.setTextSize(80.0F);
      this.textPaint.setTextAlign(Align.LEFT);
      this.mainThreadCalculations = var1;
      int var2 = var1.size();
      StringBuilder var3 = new StringBuilder();
      var3.append(var2);
      var3.append("x");
      this.text = var3.toString();
      if(((Boolean)var1.get(var2 - 1)).booleanValue()) {
         var2 = COLOR_RED_TRANSPARENT;
      } else {
         var2 = COLOR_GREEN_TRANSPARENT;
      }

      this.overlayColor = var2;
   }

   public void draw(Canvas var1) {
      this.colorPaint.setColor(this.overlayColor);
      var1.drawRect(this.getBounds(), this.colorPaint);
      int var3 = this.mainThreadCalculations.size();
      int var4 = var1.getWidth();

      for(int var2 = 0; var2 < var3; ++var2) {
         int var5 = var2 * 20;
         int var6 = var5 + 16;
         if(var6 >= var4) {
            break;
         }

         if(((Boolean)this.mainThreadCalculations.get(var2)).booleanValue()) {
            this.colorPaint.setColor(COLOR_RED_OPAQUE);
         } else {
            this.colorPaint.setColor(COLOR_GREEN_OPAQUE);
         }

         var1.drawRect((float)var5, 0.0F, (float)var6, 100.0F, this.colorPaint);
      }

      if(var3 > 3) {
         var1.drawText(this.text, 0.0F, 80.0F, this.textPaint);
      }

   }

   public int getOpacity() {
      return -3;
   }

   public void setAlpha(int var1) {}

   public void setColorFilter(ColorFilter var1) {}
}
