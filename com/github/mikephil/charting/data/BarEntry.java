package com.github.mikephil.charting.data;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Range;

@SuppressLint({"ParcelCreator"})
public class BarEntry extends Entry {

   private float mNegativeSum;
   private float mPositiveSum;
   private Range[] mRanges;
   private float[] mYVals;


   public BarEntry(float var1, float var2) {
      super(var1, var2);
   }

   public BarEntry(float var1, float var2, Drawable var3) {
      super(var1, var2, var3);
   }

   public BarEntry(float var1, float var2, Drawable var3, Object var4) {
      super(var1, var2, var3, var4);
   }

   public BarEntry(float var1, float var2, Object var3) {
      super(var1, var2, var3);
   }

   public BarEntry(float var1, float[] var2) {
      super(var1, calcSum(var2));
      this.mYVals = var2;
      this.calcPosNegSum();
      this.calcRanges();
   }

   public BarEntry(float var1, float[] var2, Drawable var3) {
      super(var1, calcSum(var2), var3);
      this.mYVals = var2;
      this.calcPosNegSum();
      this.calcRanges();
   }

   public BarEntry(float var1, float[] var2, Drawable var3, Object var4) {
      super(var1, calcSum(var2), var3, var4);
      this.mYVals = var2;
      this.calcPosNegSum();
      this.calcRanges();
   }

   public BarEntry(float var1, float[] var2, Object var3) {
      super(var1, calcSum(var2), var3);
      this.mYVals = var2;
      this.calcPosNegSum();
      this.calcRanges();
   }

   private void calcPosNegSum() {
      if(this.mYVals == null) {
         this.mNegativeSum = 0.0F;
         this.mPositiveSum = 0.0F;
      } else {
         float[] var6 = this.mYVals;
         int var5 = var6.length;
         int var4 = 0;
         float var2 = 0.0F;

         float var1;
         for(var1 = 0.0F; var4 < var5; ++var4) {
            float var3 = var6[var4];
            if(var3 <= 0.0F) {
               var2 += Math.abs(var3);
            } else {
               var1 += var3;
            }
         }

         this.mNegativeSum = var2;
         this.mPositiveSum = var1;
      }
   }

   private static float calcSum(float[] var0) {
      float var1 = 0.0F;
      if(var0 == null) {
         return 0.0F;
      } else {
         int var3 = var0.length;

         for(int var2 = 0; var2 < var3; ++var2) {
            var1 += var0[var2];
         }

         return var1;
      }
   }

   protected void calcRanges() {
      float[] var5 = this.getYVals();
      if(var5 != null) {
         if(var5.length != 0) {
            this.mRanges = new Range[var5.length];
            float var2 = -this.getNegativeSum();
            int var4 = 0;

            for(float var1 = 0.0F; var4 < this.mRanges.length; ++var4) {
               float var3 = var5[var4];
               Range[] var6;
               if(var3 < 0.0F) {
                  var6 = this.mRanges;
                  var3 = var2 - var3;
                  var6[var4] = new Range(var2, var3);
                  var2 = var3;
               } else {
                  var6 = this.mRanges;
                  var3 += var1;
                  var6[var4] = new Range(var1, var3);
                  var1 = var3;
               }
            }

         }
      }
   }

   public BarEntry copy() {
      BarEntry var1 = new BarEntry(this.getX(), this.getY(), this.getData());
      var1.setVals(this.mYVals);
      return var1;
   }

   @Deprecated
   public float getBelowSum(int var1) {
      return this.getSumBelow(var1);
   }

   public float getNegativeSum() {
      return this.mNegativeSum;
   }

   public float getPositiveSum() {
      return this.mPositiveSum;
   }

   public Range[] getRanges() {
      return this.mRanges;
   }

   public float getSumBelow(int var1) {
      float[] var4 = this.mYVals;
      float var2 = 0.0F;
      if(var4 == null) {
         return 0.0F;
      } else {
         for(int var3 = this.mYVals.length - 1; var3 > var1 && var3 >= 0; --var3) {
            var2 += this.mYVals[var3];
         }

         return var2;
      }
   }

   public float getY() {
      return super.getY();
   }

   public float[] getYVals() {
      return this.mYVals;
   }

   public boolean isStacked() {
      return this.mYVals != null;
   }

   public void setVals(float[] var1) {
      this.setY(calcSum(var1));
      this.mYVals = var1;
      this.calcPosNegSum();
      this.calcRanges();
   }
}
