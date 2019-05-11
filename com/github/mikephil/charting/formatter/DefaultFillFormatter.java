package com.github.mikephil.charting.formatter;

import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

public class DefaultFillFormatter implements IFillFormatter {

   public float getFillLinePosition(ILineDataSet var1, LineDataProvider var2) {
      float var3 = var2.getYChartMax();
      float var4 = var2.getYChartMin();
      LineData var5 = var2.getLineData();
      if(var1.getYMax() > 0.0F && var1.getYMin() < 0.0F) {
         return 0.0F;
      } else {
         if(var5.getYMax() > 0.0F) {
            var3 = 0.0F;
         }

         if(var5.getYMin() < 0.0F) {
            var4 = 0.0F;
         }

         return var1.getYMin() >= 0.0F?var4:var3;
      }
   }
}
