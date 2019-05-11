package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Path;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class YAxisRendererRadarChart extends YAxisRenderer {

   private RadarChart mChart;
   private Path mRenderLimitLinesPathBuffer = new Path();


   public YAxisRendererRadarChart(ViewPortHandler var1, YAxis var2, RadarChart var3) {
      super(var1, var2, (Transformer)null);
      this.mChart = var3;
   }

   protected void computeAxisValues(float var1, float var2) {
      throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\n");
   }

   public void renderAxisLabels(Canvas var1) {
      if(this.mYAxis.isEnabled()) {
         if(this.mYAxis.isDrawLabelsEnabled()) {
            this.mAxisLabelPaint.setTypeface(this.mYAxis.getTypeface());
            this.mAxisLabelPaint.setTextSize(this.mYAxis.getTextSize());
            this.mAxisLabelPaint.setColor(this.mYAxis.getTextColor());
            MPPointF var5 = this.mChart.getCenterOffsets();
            MPPointF var6 = MPPointF.getInstance(0.0F, 0.0F);
            float var2 = this.mChart.getFactor();
            int var4 = this.mYAxis.isDrawBottomYLabelEntryEnabled() ^ 1;
            int var3;
            if(this.mYAxis.isDrawTopYLabelEntryEnabled()) {
               var3 = this.mYAxis.mEntryCount;
            } else {
               var3 = this.mYAxis.mEntryCount - 1;
            }

            while(var4 < var3) {
               Utils.getPosition(var5, (this.mYAxis.mEntries[var4] - this.mYAxis.mAxisMinimum) * var2, this.mChart.getRotationAngle(), var6);
               var1.drawText(this.mYAxis.getFormattedLabel(var4), var6.x + 10.0F, var6.y, this.mAxisLabelPaint);
               ++var4;
            }

            MPPointF.recycleInstance(var5);
            MPPointF.recycleInstance(var6);
         }
      }
   }

   public void renderLimitLines(Canvas var1) {
      List var8 = this.mYAxis.getLimitLines();
      if(var8 != null) {
         float var2 = this.mChart.getSliceAngle();
         float var3 = this.mChart.getFactor();
         MPPointF var9 = this.mChart.getCenterOffsets();
         MPPointF var10 = MPPointF.getInstance(0.0F, 0.0F);

         for(int var6 = 0; var6 < var8.size(); ++var6) {
            LimitLine var11 = (LimitLine)var8.get(var6);
            if(var11.isEnabled()) {
               this.mLimitLinePaint.setColor(var11.getLineColor());
               this.mLimitLinePaint.setPathEffect(var11.getDashPathEffect());
               this.mLimitLinePaint.setStrokeWidth(var11.getLineWidth());
               float var4 = var11.getLimit();
               float var5 = this.mChart.getYChartMin();
               Path var12 = this.mRenderLimitLinesPathBuffer;
               var12.reset();

               for(int var7 = 0; var7 < ((IRadarDataSet)((RadarData)this.mChart.getData()).getMaxEntryCountSet()).getEntryCount(); ++var7) {
                  Utils.getPosition(var9, (var4 - var5) * var3, (float)var7 * var2 + this.mChart.getRotationAngle(), var10);
                  if(var7 == 0) {
                     var12.moveTo(var10.x, var10.y);
                  } else {
                     var12.lineTo(var10.x, var10.y);
                  }
               }

               var12.close();
               var1.drawPath(var12, this.mLimitLinePaint);
            }
         }

         MPPointF.recycleInstance(var9);
         MPPointF.recycleInstance(var10);
      }
   }
}
