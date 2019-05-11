package com.facebook.react.views.text;

import android.graphics.Paint.FontMetricsInt;
import android.text.style.LineHeightSpan;

public class CustomLineHeightSpan implements LineHeightSpan {

   private final int mHeight;


   CustomLineHeightSpan(float var1) {
      this.mHeight = (int)Math.ceil((double)var1);
   }

   public void chooseHeight(CharSequence var1, int var2, int var3, int var4, int var5, FontMetricsInt var6) {
      if(-var6.ascent > this.mHeight) {
         var2 = -this.mHeight;
         var6.ascent = var2;
         var6.top = var2;
         var6.descent = 0;
         var6.bottom = 0;
      } else if(-var6.ascent + var6.descent > this.mHeight) {
         var6.top = var6.ascent;
         var2 = this.mHeight + var6.ascent;
         var6.descent = var2;
         var6.bottom = var2;
      } else if(-var6.ascent + var6.bottom > this.mHeight) {
         var6.top = var6.ascent;
         var6.bottom = var6.ascent + this.mHeight;
      } else if(-var6.top + var6.bottom > this.mHeight) {
         var6.top = var6.bottom - this.mHeight;
      } else {
         var2 = this.mHeight - (-var6.top + var6.bottom);
         var6.top -= var2;
         var6.ascent -= var2;
      }
   }
}
