package com.facebook.react.flat;

import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import com.facebook.react.flat.TypefaceCache;
import javax.annotation.Nullable;

final class FontStylingSpan extends MetricAffectingSpan {

   static final FontStylingSpan INSTANCE = new FontStylingSpan(-1.6777216E7D, 0, -1, -1, -1, false, false, (String)null, true);
   private int mBackgroundColor;
   @Nullable
   private String mFontFamily;
   private int mFontSize;
   private int mFontStyle;
   private int mFontWeight;
   private boolean mFrozen;
   private boolean mHasStrikeThrough;
   private boolean mHasUnderline;
   private double mTextColor;


   FontStylingSpan() {}

   private FontStylingSpan(double var1, int var3, int var4, int var5, int var6, boolean var7, boolean var8, @Nullable String var9, boolean var10) {
      this.mTextColor = var1;
      this.mBackgroundColor = var3;
      this.mFontSize = var4;
      this.mFontStyle = var5;
      this.mFontWeight = var6;
      this.mHasUnderline = var7;
      this.mHasStrikeThrough = var8;
      this.mFontFamily = var9;
      this.mFrozen = var10;
   }

   private int getNewStyle(int var1) {
      int var2 = var1;
      if(this.mFontStyle != -1) {
         var2 = var1 & -3 | this.mFontStyle;
      }

      var1 = var2;
      if(this.mFontWeight != -1) {
         var1 = var2 & -2 | this.mFontWeight;
      }

      return var1;
   }

   private void updateTypeface(TextPaint var1) {
      Typeface var4 = var1.getTypeface();
      int var2;
      if(var4 == null) {
         var2 = 0;
      } else {
         var2 = var4.getStyle();
      }

      int var3 = this.getNewStyle(var2);
      if(var2 != var3 || this.mFontFamily != null) {
         if(this.mFontFamily != null) {
            var4 = TypefaceCache.getTypeface(this.mFontFamily, var3);
         } else {
            var4 = TypefaceCache.getTypeface(var4, var3);
         }

         var1.setTypeface(var4);
      }
   }

   void freeze() {
      this.mFrozen = true;
   }

   int getBackgroundColor() {
      return this.mBackgroundColor;
   }

   @Nullable
   String getFontFamily() {
      return this.mFontFamily;
   }

   int getFontSize() {
      return this.mFontSize;
   }

   int getFontStyle() {
      return this.mFontStyle;
   }

   int getFontWeight() {
      return this.mFontWeight;
   }

   double getTextColor() {
      return this.mTextColor;
   }

   boolean hasStrikeThrough() {
      return this.mHasStrikeThrough;
   }

   boolean hasUnderline() {
      return this.mHasUnderline;
   }

   boolean isFrozen() {
      return this.mFrozen;
   }

   FontStylingSpan mutableCopy() {
      return new FontStylingSpan(this.mTextColor, this.mBackgroundColor, this.mFontSize, this.mFontStyle, this.mFontWeight, this.mHasUnderline, this.mHasStrikeThrough, this.mFontFamily, false);
   }

   void setBackgroundColor(int var1) {
      this.mBackgroundColor = var1;
   }

   void setFontFamily(@Nullable String var1) {
      this.mFontFamily = var1;
   }

   void setFontSize(int var1) {
      this.mFontSize = var1;
   }

   void setFontStyle(int var1) {
      this.mFontStyle = var1;
   }

   void setFontWeight(int var1) {
      this.mFontWeight = var1;
   }

   void setHasStrikeThrough(boolean var1) {
      this.mHasStrikeThrough = var1;
   }

   void setHasUnderline(boolean var1) {
      this.mHasUnderline = var1;
   }

   void setTextColor(double var1) {
      this.mTextColor = var1;
   }

   public void updateDrawState(TextPaint var1) {
      if(!Double.isNaN(this.mTextColor)) {
         var1.setColor((int)this.mTextColor);
      }

      var1.bgColor = this.mBackgroundColor;
      var1.setUnderlineText(this.mHasUnderline);
      var1.setStrikeThruText(this.mHasStrikeThrough);
      this.updateMeasureState(var1);
   }

   public void updateMeasureState(TextPaint var1) {
      if(this.mFontSize != -1) {
         var1.setTextSize((float)this.mFontSize);
      }

      this.updateTypeface(var1);
   }
}
