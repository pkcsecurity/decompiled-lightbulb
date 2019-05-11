package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.PieRadarChartBase;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.IHighlighter;
import java.util.ArrayList;
import java.util.List;

public abstract class PieRadarHighlighter<T extends PieRadarChartBase> implements IHighlighter {

   protected T mChart;
   protected List<Highlight> mHighlightBuffer = new ArrayList();


   public PieRadarHighlighter(T var1) {
      this.mChart = var1;
   }

   protected abstract Highlight getClosestHighlight(int var1, float var2, float var3);

   public Highlight getHighlight(float var1, float var2) {
      if(this.mChart.distanceToCenter(var1, var2) > this.mChart.getRadius()) {
         return null;
      } else {
         float var4 = this.mChart.getAngleForPoint(var1, var2);
         float var3 = var4;
         if(this.mChart instanceof PieChart) {
            var3 = var4 / this.mChart.getAnimator().getPhaseY();
         }

         int var5 = this.mChart.getIndexForAngle(var3);
         return var5 >= 0?(var5 >= this.mChart.getData().getMaxEntryCountSet().getEntryCount()?null:this.getClosestHighlight(var5, var1, var2)):null;
      }
   }
}
