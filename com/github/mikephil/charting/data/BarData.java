package com.github.mikephil.charting.data;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import java.util.Iterator;
import java.util.List;

public class BarData extends BarLineScatterCandleBubbleData<IBarDataSet> {

   private float mBarWidth = 0.85F;


   public BarData() {}

   public BarData(List<IBarDataSet> var1) {
      super(var1);
   }

   public BarData(IBarDataSet ... var1) {
      super((IBarLineScatterCandleBubbleDataSet[])var1);
   }

   public float getBarWidth() {
      return this.mBarWidth;
   }

   public float getGroupWidth(float var1, float var2) {
      return (float)this.mDataSets.size() * (this.mBarWidth + var2) + var1;
   }

   public void groupBars(float var1, float var2, float var3) {
      if(this.mDataSets.size() <= 1) {
         throw new RuntimeException("BarData needs to hold at least 2 BarDataSets to allow grouping.");
      } else {
         int var9 = ((IBarDataSet)this.getMaxEntryCountSet()).getEntryCount();
         float var4 = var2 / 2.0F;
         float var5 = var3 / 2.0F;
         float var6 = this.mBarWidth / 2.0F;
         var3 = this.getGroupWidth(var2, var3);

         for(int var8 = 0; var8 < var9; ++var8) {
            var2 = var1 + var4;

            for(Iterator var10 = this.mDataSets.iterator(); var10.hasNext(); var2 = var2 + var6 + var5) {
               IBarDataSet var11 = (IBarDataSet)var10.next();
               var2 = var2 + var5 + var6;
               if(var8 < var11.getEntryCount()) {
                  BarEntry var12 = (BarEntry)var11.getEntryForIndex(var8);
                  if(var12 != null) {
                     var12.setX(var2);
                  }
               }
            }

            var2 += var4;
            float var7 = var3 - (var2 - var1);
            if(var7 <= 0.0F) {
               var1 = var2;
               if(var7 >= 0.0F) {
                  continue;
               }
            }

            var1 = var2 + var7;
         }

         this.notifyDataChanged();
      }
   }

   public void setBarWidth(float var1) {
      this.mBarWidth = var1;
   }
}
