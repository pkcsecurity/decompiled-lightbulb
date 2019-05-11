package com.github.mikephil.charting.buffer;

import com.github.mikephil.charting.buffer.AbstractBuffer;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

public class BarBuffer extends AbstractBuffer<IBarDataSet> {

   protected float mBarWidth = 1.0F;
   protected boolean mContainsStacks = false;
   protected int mDataSetCount = 1;
   protected int mDataSetIndex = 0;
   protected boolean mInverted = false;


   public BarBuffer(int var1, int var2, boolean var3) {
      super(var1);
      this.mDataSetCount = var2;
      this.mContainsStacks = var3;
   }

   protected void addBar(float var1, float var2, float var3, float var4) {
      float[] var6 = this.buffer;
      int var5 = this.index;
      this.index = var5 + 1;
      var6[var5] = var1;
      var6 = this.buffer;
      var5 = this.index;
      this.index = var5 + 1;
      var6[var5] = var2;
      var6 = this.buffer;
      var5 = this.index;
      this.index = var5 + 1;
      var6[var5] = var3;
      var6 = this.buffer;
      var5 = this.index;
      this.index = var5 + 1;
      var6[var5] = var4;
   }

   public void feed(IBarDataSet var1) {
      float var8 = (float)var1.getEntryCount();
      float var9 = this.phaseX;
      float var10 = this.mBarWidth / 2.0F;

      for(int var12 = 0; (float)var12 < var8 * var9; ++var12) {
         BarEntry var14 = (BarEntry)var1.getEntryForIndex(var12);
         if(var14 != null) {
            float var11 = var14.getX();
            float var2 = var14.getY();
            float[] var15 = var14.getYVals();
            float var3;
            float var4;
            if(this.mContainsStacks && var15 != null) {
               var2 = -var14.getNegativeSum();
               int var13 = 0;

               float var6;
               for(var4 = 0.0F; var13 < var15.length; var2 = var6) {
                  var3 = var15[var13];
                  float var5;
                  if(var3 == 0.0F && (var4 == 0.0F || var2 == 0.0F)) {
                     var5 = var4;
                     var6 = var2;
                     var2 = var3;
                     var3 = var3;
                  } else if(var3 >= 0.0F) {
                     var3 += var4;
                     var5 = var3;
                     var6 = var2;
                     var2 = var4;
                  } else {
                     var6 = Math.abs(var3) + var2;
                     var3 = Math.abs(var3);
                     float var7 = var3 + var2;
                     var3 = var6;
                     var2 = var2;
                     var6 = var7;
                     var5 = var4;
                  }

                  if(this.mInverted) {
                     if(var2 >= var3) {
                        var4 = var2;
                     } else {
                        var4 = var3;
                     }

                     if(var2 > var3) {
                        var2 = var3;
                     }
                  } else {
                     if(var2 >= var3) {
                        var4 = var2;
                     } else {
                        var4 = var3;
                     }

                     if(var2 > var3) {
                        var2 = var3;
                     }

                     var3 = var2;
                     var2 = var4;
                     var4 = var3;
                  }

                  this.addBar(var11 - var10, var2 * this.phaseY, var11 + var10, var4 * this.phaseY);
                  ++var13;
                  var4 = var5;
               }
            } else {
               if(this.mInverted) {
                  if(var2 >= 0.0F) {
                     var3 = var2;
                  } else {
                     var3 = 0.0F;
                  }

                  if(var2 > 0.0F) {
                     var2 = 0.0F;
                  }

                  var4 = var3;
                  var3 = var2;
                  var2 = var4;
               } else {
                  if(var2 >= 0.0F) {
                     var3 = var2;
                  } else {
                     var3 = 0.0F;
                  }

                  if(var2 > 0.0F) {
                     var2 = 0.0F;
                  }
               }

               if(var3 > 0.0F) {
                  var3 *= this.phaseY;
               } else {
                  var2 *= this.phaseY;
               }

               this.addBar(var11 - var10, var3, var11 + var10, var2);
            }
         }
      }

      this.reset();
   }

   public void setBarWidth(float var1) {
      this.mBarWidth = var1;
   }

   public void setDataSet(int var1) {
      this.mDataSetIndex = var1;
   }

   public void setInverted(boolean var1) {
      this.mInverted = var1;
   }
}
