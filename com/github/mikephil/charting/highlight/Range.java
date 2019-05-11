package com.github.mikephil.charting.highlight;


public final class Range {

   public float from;
   public float to;


   public Range(float var1, float var2) {
      this.from = var1;
      this.to = var2;
   }

   public boolean contains(float var1) {
      return var1 > this.from && var1 <= this.to;
   }

   public boolean isLarger(float var1) {
      return var1 > this.to;
   }

   public boolean isSmaller(float var1) {
      return var1 < this.from;
   }
}
