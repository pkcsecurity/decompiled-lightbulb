package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class XAxisRendererRadarChart extends XAxisRenderer {

   private RadarChart mChart;


   public XAxisRendererRadarChart(ViewPortHandler var1, XAxis var2, RadarChart var3) {
      super(var1, var2, (Transformer)null);
      this.mChart = var3;
   }

   public void renderAxisLabels(Canvas var1) {
      if(this.mXAxis.isEnabled()) {
         if(this.mXAxis.isDrawLabelsEnabled()) {
            float var2 = this.mXAxis.getLabelRotationAngle();
            MPPointF var8 = MPPointF.getInstance(0.5F, 0.25F);
            this.mAxisLabelPaint.setTypeface(this.mXAxis.getTypeface());
            this.mAxisLabelPaint.setTextSize(this.mXAxis.getTextSize());
            this.mAxisLabelPaint.setColor(this.mXAxis.getTextColor());
            float var3 = this.mChart.getSliceAngle();
            float var4 = this.mChart.getFactor();
            MPPointF var9 = this.mChart.getCenterOffsets();
            MPPointF var10 = MPPointF.getInstance(0.0F, 0.0F);

            for(int var7 = 0; var7 < ((IRadarDataSet)((RadarData)this.mChart.getData()).getMaxEntryCountSet()).getEntryCount(); ++var7) {
               IAxisValueFormatter var11 = this.mXAxis.getValueFormatter();
               float var5 = (float)var7;
               String var12 = var11.getFormattedValue(var5, this.mXAxis);
               float var6 = this.mChart.getRotationAngle();
               Utils.getPosition(var9, this.mChart.getYRange() * var4 + (float)this.mXAxis.mLabelRotatedWidth / 2.0F, (var5 * var3 + var6) % 360.0F, var10);
               this.drawLabel(var1, var12, var10.x, var10.y - (float)this.mXAxis.mLabelRotatedHeight / 2.0F, var8, var2);
            }

            MPPointF.recycleInstance(var9);
            MPPointF.recycleInstance(var10);
            MPPointF.recycleInstance(var8);
         }
      }
   }

   public void renderLimitLines(Canvas var1) {}
}
