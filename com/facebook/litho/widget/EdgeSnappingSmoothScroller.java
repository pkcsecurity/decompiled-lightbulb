package com.facebook.litho.widget;

import android.content.Context;
import android.support.v7.widget.LinearSmoothScroller;

public class EdgeSnappingSmoothScroller extends LinearSmoothScroller {

   private final int mOffset;
   private final int mSnapPreference;


   public EdgeSnappingSmoothScroller(Context var1, int var2, int var3) {
      super(var1);
      this.mSnapPreference = var2;
      this.mOffset = var3;
   }

   public int calculateDtToFit(int var1, int var2, int var3, int var4, int var5) {
      return super.calculateDtToFit(var1, var2, var3, var4, var5) + this.mOffset;
   }

   protected int getHorizontalSnapPreference() {
      return this.mSnapPreference;
   }

   protected int getVerticalSnapPreference() {
      return this.mSnapPreference;
   }
}
