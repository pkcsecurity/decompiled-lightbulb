package com.facebook.litho.widget;

import android.content.Context;
import android.support.v7.widget.LinearSmoothScroller;
import android.util.DisplayMetrics;

public class CenterSnappingSmoothScroller extends LinearSmoothScroller {

   private static final float MILLISECONDS_PER_INCH = 100.0F;
   private final int mOffset;


   public CenterSnappingSmoothScroller(Context var1, int var2) {
      super(var1);
      this.mOffset = var2;
   }

   public int calculateDtToFit(int var1, int var2, int var3, int var4, int var5) {
      return var3 + (var4 - var3) / 2 - (var1 + (var2 - var1) / 2) + this.mOffset;
   }

   protected float calculateSpeedPerPixel(DisplayMetrics var1) {
      return 100.0F / (float)var1.densityDpi;
   }
}
