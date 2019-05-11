package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.PieRadarHighlighter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import java.util.List;

public class RadarHighlighter extends PieRadarHighlighter<RadarChart> {

   public RadarHighlighter(RadarChart var1) {
      super(var1);
   }

   protected Highlight getClosestHighlight(int var1, float var2, float var3) {
      List var8 = this.getHighlightsAtIndex(var1);
      float var5 = ((RadarChart)this.mChart).distanceToCenter(var2, var3) / ((RadarChart)this.mChart).getFactor();
      Highlight var6 = null;
      var2 = Float.MAX_VALUE;

      for(var1 = 0; var1 < var8.size(); var2 = var3) {
         Highlight var7 = (Highlight)var8.get(var1);
         float var4 = Math.abs(var7.getY() - var5);
         var3 = var2;
         if(var4 < var2) {
            var6 = var7;
            var3 = var4;
         }

         ++var1;
      }

      return var6;
   }

   protected List<Highlight> getHighlightsAtIndex(int var1) {
      this.mHighlightBuffer.clear();
      float var2 = ((RadarChart)this.mChart).getAnimator().getPhaseX();
      float var3 = ((RadarChart)this.mChart).getAnimator().getPhaseY();
      float var4 = ((RadarChart)this.mChart).getSliceAngle();
      float var5 = ((RadarChart)this.mChart).getFactor();
      MPPointF var11 = MPPointF.getInstance(0.0F, 0.0F);

      for(int var9 = 0; var9 < ((RadarData)((RadarChart)this.mChart).getData()).getDataSetCount(); ++var9) {
         IDataSet var12 = ((RadarData)((RadarChart)this.mChart).getData()).getDataSetByIndex(var9);
         Entry var13 = var12.getEntryForIndex(var1);
         float var6 = var13.getY();
         float var7 = ((RadarChart)this.mChart).getYChartMin();
         MPPointF var14 = ((RadarChart)this.mChart).getCenterOffsets();
         float var8 = (float)var1;
         Utils.getPosition(var14, (var6 - var7) * var5 * var3, var4 * var8 * var2 + ((RadarChart)this.mChart).getRotationAngle(), var11);
         this.mHighlightBuffer.add(new Highlight(var8, var13.getY(), var11.x, var11.y, var9, var12.getAxisDependency()));
      }

      return this.mHighlightBuffer;
   }
}
