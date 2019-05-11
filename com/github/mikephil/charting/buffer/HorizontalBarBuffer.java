package com.github.mikephil.charting.buffer;

import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

public class HorizontalBarBuffer extends BarBuffer {

   public HorizontalBarBuffer(int var1, int var2, boolean var3) {
      super(var1, var2, var3);
   }

   public void feed(IBarDataSet var1) {
      float var9 = (float)var1.getEntryCount();
      float var10 = this.phaseX;
      float var11 = this.mBarWidth / 2.0F;

      for(int var13 = 0; (float)var13 < var9 * var10; ++var13) {
         BarEntry var15 = (BarEntry)var1.getEntryForIndex(var13);
         if(var15 != null) {
            float var12 = var15.getX();
            float var2 = var15.getY();
            float[] var16 = var15.getYVals();
            float var3;
            float var4;
            if(this.mContainsStacks && var16 != null) {
               var2 = -var15.getNegativeSum();
               int var14 = 0;

               for(var3 = 0.0F; var14 < var16.length; var2 = var4) {
                  var4 = var16[var14];
                  float var5;
                  float var6;
                  float var7;
                  if(var4 >= 0.0F) {
                     var4 += var3;
                     var6 = var2;
                     var5 = var4;
                     var2 = var3;
                     var3 = var4;
                     var4 = var6;
                  } else {
                     var7 = Math.abs(var4);
                     var4 = Math.abs(var4);
                     var5 = var3;
                     var4 += var2;
                     var3 = var7 + var2;
                     var2 = var2;
                  }

                  float var8;
                  if(this.mInverted) {
                     if(var2 >= var3) {
                        var7 = var2;
                     } else {
                        var7 = var3;
                     }

                     var8 = var7;
                     var6 = var3;
                     if(var2 <= var3) {
                        var8 = var7;
                        var6 = var2;
                     }
                  } else {
                     if(var2 >= var3) {
                        var6 = var2;
                     } else {
                        var6 = var3;
                     }

                     var7 = var3;
                     if(var2 <= var3) {
                        var7 = var2;
                     }

                     var8 = var7;
                  }

                  var2 = this.phaseY;
                  this.addBar(var8 * this.phaseY, var12 + var11, var6 * var2, var12 - var11);
                  ++var14;
                  var3 = var5;
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

               this.addBar(var2, var12 + var11, var3, var12 - var11);
            }
         }
      }

      this.reset();
   }
}
