package com.facebook.react.views.text;

import android.text.Spannable;

public class ReactTextUpdate {

   private final boolean mContainsImages;
   private final int mJsEventCounter;
   private final float mPaddingBottom;
   private final float mPaddingLeft;
   private final float mPaddingRight;
   private final float mPaddingTop;
   private final Spannable mText;
   private final int mTextAlign;
   private final int mTextBreakStrategy;


   @Deprecated
   public ReactTextUpdate(Spannable var1, int var2, boolean var3, float var4, float var5, float var6, float var7, int var8) {
      this(var1, var2, var3, var4, var5, var6, var7, var8, 1);
   }

   public ReactTextUpdate(Spannable var1, int var2, boolean var3, float var4, float var5, float var6, float var7, int var8, int var9) {
      this.mText = var1;
      this.mJsEventCounter = var2;
      this.mContainsImages = var3;
      this.mPaddingLeft = var4;
      this.mPaddingTop = var5;
      this.mPaddingRight = var6;
      this.mPaddingBottom = var7;
      this.mTextAlign = var8;
      this.mTextBreakStrategy = var9;
   }

   public boolean containsImages() {
      return this.mContainsImages;
   }

   public int getJsEventCounter() {
      return this.mJsEventCounter;
   }

   public float getPaddingBottom() {
      return this.mPaddingBottom;
   }

   public float getPaddingLeft() {
      return this.mPaddingLeft;
   }

   public float getPaddingRight() {
      return this.mPaddingRight;
   }

   public float getPaddingTop() {
      return this.mPaddingTop;
   }

   public Spannable getText() {
      return this.mText;
   }

   public int getTextAlign() {
      return this.mTextAlign;
   }

   public int getTextBreakStrategy() {
      return this.mTextBreakStrategy;
   }
}
