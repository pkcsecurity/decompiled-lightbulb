package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.highlight.ChartHighlighter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.Range;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.MPPointD;

public class BarHighlighter extends ChartHighlighter<BarDataProvider> {

   public BarHighlighter(BarDataProvider var1) {
      super(var1);
   }

   protected int getClosestStackIndex(Range[] var1, float var2) {
      byte var5 = 0;
      if(var1 != null) {
         if(var1.length == 0) {
            return 0;
         } else {
            int var6 = var1.length;
            int var3 = 0;

            int var4;
            for(var4 = 0; var3 < var6; ++var3) {
               if(var1[var3].contains(var2)) {
                  return var4;
               }

               ++var4;
            }

            var4 = Math.max(var1.length - 1, 0);
            var3 = var5;
            if(var2 > var1[var4].to) {
               var3 = var4;
            }

            return var3;
         }
      } else {
         return 0;
      }
   }

   protected BarLineScatterCandleBubbleData getData() {
      return ((BarDataProvider)this.mChart).getBarData();
   }

   protected float getDistance(float var1, float var2, float var3, float var4) {
      return Math.abs(var1 - var3);
   }

   public Highlight getHighlight(float var1, float var2) {
      Highlight var3 = super.getHighlight(var1, var2);
      if(var3 == null) {
         return null;
      } else {
         MPPointD var4 = this.getValsForTouch(var1, var2);
         IBarDataSet var5 = (IBarDataSet)((BarDataProvider)this.mChart).getBarData().getDataSetByIndex(var3.getDataSetIndex());
         if(var5.isStacked()) {
            return this.getStackedHighlight(var3, var5, (float)var4.x, (float)var4.y);
         } else {
            MPPointD.recycleInstance(var4);
            return var3;
         }
      }
   }

   public Highlight getStackedHighlight(Highlight var1, IBarDataSet var2, float var3, float var4) {
      BarEntry var6 = (BarEntry)var2.getEntryForXValue(var3, var4);
      if(var6 == null) {
         return null;
      } else if(var6.getYVals() == null) {
         return var1;
      } else {
         Range[] var7 = var6.getRanges();
         if(var7.length > 0) {
            int var5 = this.getClosestStackIndex(var7, var4);
            MPPointD var8 = ((BarDataProvider)this.mChart).getTransformer(var2.getAxisDependency()).getPixelForValues(var1.getX(), var7[var5].to);
            var1 = new Highlight(var6.getX(), var6.getY(), (float)var8.x, (float)var8.y, var1.getDataSetIndex(), var5, var1.getAxis());
            MPPointD.recycleInstance(var8);
            return var1;
         } else {
            return null;
         }
      }
   }
}
