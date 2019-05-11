package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.ScatterDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.renderer.LineScatterCandleRadarRenderer;
import com.github.mikephil.charting.renderer.scatter.IShapeRenderer;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.Iterator;
import java.util.List;

public class ScatterChartRenderer extends LineScatterCandleRadarRenderer {

   protected ScatterDataProvider mChart;
   float[] mPixelBuffer = new float[2];


   public ScatterChartRenderer(ScatterDataProvider var1, ChartAnimator var2, ViewPortHandler var3) {
      super(var2, var3);
      this.mChart = var1;
   }

   public void drawData(Canvas var1) {
      Iterator var2 = this.mChart.getScatterData().getDataSets().iterator();

      while(var2.hasNext()) {
         IScatterDataSet var3 = (IScatterDataSet)var2.next();
         if(var3.isVisible()) {
            this.drawDataSet(var1, var3);
         }
      }

   }

   protected void drawDataSet(Canvas var1, IScatterDataSet var2) {
      ViewPortHandler var6 = this.mViewPortHandler;
      Transformer var7 = this.mChart.getTransformer(var2.getAxisDependency());
      float var3 = this.mAnimator.getPhaseY();
      IShapeRenderer var8 = var2.getShapeRenderer();
      if(var8 == null) {
         Log.i("MISSING", "There\'s no IShapeRenderer specified for ScatterDataSet");
      } else {
         int var5 = (int)Math.min(Math.ceil((double)((float)var2.getEntryCount() * this.mAnimator.getPhaseX())), (double)((float)var2.getEntryCount()));

         for(int var4 = 0; var4 < var5; ++var4) {
            Entry var9 = var2.getEntryForIndex(var4);
            this.mPixelBuffer[0] = var9.getX();
            this.mPixelBuffer[1] = var9.getY() * var3;
            var7.pointValuesToPixel(this.mPixelBuffer);
            if(!var6.isInBoundsRight(this.mPixelBuffer[0])) {
               return;
            }

            if(var6.isInBoundsLeft(this.mPixelBuffer[0]) && var6.isInBoundsY(this.mPixelBuffer[1])) {
               this.mRenderPaint.setColor(var2.getColor(var4 / 2));
               var8.renderShape(var1, var2, this.mViewPortHandler, this.mPixelBuffer[0], this.mPixelBuffer[1], this.mRenderPaint);
            }
         }

      }
   }

   public void drawExtras(Canvas var1) {}

   public void drawHighlighted(Canvas var1, Highlight[] var2) {
      ScatterData var5 = this.mChart.getScatterData();
      int var4 = var2.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         Highlight var6 = var2[var3];
         IScatterDataSet var7 = (IScatterDataSet)var5.getDataSetByIndex(var6.getDataSetIndex());
         if(var7 != null && var7.isHighlightEnabled()) {
            Entry var8 = var7.getEntryForXValue(var6.getX(), var6.getY());
            if(this.isInBoundsX(var8, var7)) {
               MPPointD var9 = this.mChart.getTransformer(var7.getAxisDependency()).getPixelForValues(var8.getX(), var8.getY() * this.mAnimator.getPhaseY());
               var6.setDraw((float)var9.x, (float)var9.y);
               this.drawHighlightLines(var1, (float)var9.x, (float)var9.y, var7);
            }
         }
      }

   }

   public void drawValues(Canvas var1) {
      if(this.isDrawingValuesAllowed(this.mChart)) {
         List var10 = this.mChart.getScatterData().getDataSets();

         for(int var3 = 0; var3 < this.mChart.getScatterData().getDataSetCount(); ++var3) {
            IScatterDataSet var11 = (IScatterDataSet)var10.get(var3);
            if(this.shouldDrawValues(var11)) {
               this.applyValueTextStyle(var11);
               this.mXBounds.set(this.mChart, var11);
               float[] var12 = this.mChart.getTransformer(var11.getAxisDependency()).generateTransformedValuesScatter(var11, this.mAnimator.getPhaseX(), this.mAnimator.getPhaseY(), this.mXBounds.min, this.mXBounds.max);
               float var2 = Utils.convertDpToPixel(var11.getScatterShapeSize());
               MPPointF var7 = MPPointF.getInstance(var11.getIconsOffset());
               var7.x = Utils.convertDpToPixel(var7.x);
               var7.y = Utils.convertDpToPixel(var7.y);

               for(int var4 = 0; var4 < var12.length && this.mViewPortHandler.isInBoundsRight(var12[var4]); var4 += 2) {
                  if(this.mViewPortHandler.isInBoundsLeft(var12[var4])) {
                     ViewPortHandler var9 = this.mViewPortHandler;
                     int var5 = var4 + 1;
                     if(var9.isInBoundsY(var12[var5])) {
                        int var6 = var4 / 2;
                        Entry var13 = var11.getEntryForIndex(this.mXBounds.min + var6);
                        if(var11.isDrawValuesEnabled()) {
                           this.drawValue(var1, var11.getValueFormatter(), var13.getY(), var13, var3, var12[var4], var12[var5] - var2, var11.getValueTextColor(var6 + this.mXBounds.min));
                        }

                        if(var13.getIcon() != null && var11.isDrawIconsEnabled()) {
                           Drawable var14 = var13.getIcon();
                           Utils.drawImage(var1, var14, (int)(var12[var4] + var7.x), (int)(var12[var5] + var7.y), var14.getIntrinsicWidth(), var14.getIntrinsicHeight());
                        }
                     }
                  }
               }

               MPPointF.recycleInstance(var7);
            }
         }
      }

   }

   public void initBuffers() {}
}
