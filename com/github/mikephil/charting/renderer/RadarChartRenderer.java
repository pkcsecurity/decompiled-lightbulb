package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;
import android.graphics.Path.Direction;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.renderer.LineRadarRenderer;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.Iterator;

public class RadarChartRenderer extends LineRadarRenderer {

   protected RadarChart mChart;
   protected Path mDrawDataSetSurfacePathBuffer = new Path();
   protected Path mDrawHighlightCirclePathBuffer = new Path();
   protected Paint mHighlightCirclePaint;
   protected Paint mWebPaint;


   public RadarChartRenderer(RadarChart var1, ChartAnimator var2, ViewPortHandler var3) {
      super(var2, var3);
      this.mChart = var1;
      this.mHighlightPaint = new Paint(1);
      this.mHighlightPaint.setStyle(Style.STROKE);
      this.mHighlightPaint.setStrokeWidth(2.0F);
      this.mHighlightPaint.setColor(Color.rgb(255, 187, 115));
      this.mWebPaint = new Paint(1);
      this.mWebPaint.setStyle(Style.STROKE);
      this.mHighlightCirclePaint = new Paint(1);
   }

   public void drawData(Canvas var1) {
      RadarData var3 = (RadarData)this.mChart.getData();
      int var2 = ((IRadarDataSet)var3.getMaxEntryCountSet()).getEntryCount();
      Iterator var5 = var3.getDataSets().iterator();

      while(var5.hasNext()) {
         IRadarDataSet var4 = (IRadarDataSet)var5.next();
         if(var4.isVisible()) {
            this.drawDataSet(var1, var4, var2);
         }
      }

   }

   protected void drawDataSet(Canvas var1, IRadarDataSet var2, int var3) {
      float var4 = this.mAnimator.getPhaseX();
      float var5 = this.mAnimator.getPhaseY();
      float var6 = this.mChart.getSliceAngle();
      float var7 = this.mChart.getFactor();
      MPPointF var10 = this.mChart.getCenterOffsets();
      MPPointF var11 = MPPointF.getInstance(0.0F, 0.0F);
      Path var12 = this.mDrawDataSetSurfacePathBuffer;
      var12.reset();
      int var8 = 0;

      for(boolean var9 = false; var8 < var2.getEntryCount(); ++var8) {
         this.mRenderPaint.setColor(var2.getColor(var8));
         Utils.getPosition(var10, (((RadarEntry)var2.getEntryForIndex(var8)).getY() - this.mChart.getYChartMin()) * var7 * var5, (float)var8 * var6 * var4 + this.mChart.getRotationAngle(), var11);
         if(!Float.isNaN(var11.x)) {
            if(!var9) {
               var12.moveTo(var11.x, var11.y);
               var9 = true;
            } else {
               var12.lineTo(var11.x, var11.y);
            }
         }
      }

      if(var2.getEntryCount() > var3) {
         var12.lineTo(var10.x, var10.y);
      }

      var12.close();
      if(var2.isDrawFilledEnabled()) {
         Drawable var13 = var2.getFillDrawable();
         if(var13 != null) {
            this.drawFilledPath(var1, var12, var13);
         } else {
            this.drawFilledPath(var1, var12, var2.getFillColor(), var2.getFillAlpha());
         }
      }

      this.mRenderPaint.setStrokeWidth(var2.getLineWidth());
      this.mRenderPaint.setStyle(Style.STROKE);
      if(!var2.isDrawFilledEnabled() || var2.getFillAlpha() < 255) {
         var1.drawPath(var12, this.mRenderPaint);
      }

      MPPointF.recycleInstance(var10);
      MPPointF.recycleInstance(var11);
   }

   public void drawExtras(Canvas var1) {
      this.drawWeb(var1);
   }

   public void drawHighlightCircle(Canvas var1, MPPointF var2, float var3, float var4, int var5, int var6, float var7) {
      var1.save();
      var4 = Utils.convertDpToPixel(var4);
      var3 = Utils.convertDpToPixel(var3);
      if(var5 != 1122867) {
         Path var8 = this.mDrawHighlightCirclePathBuffer;
         var8.reset();
         var8.addCircle(var2.x, var2.y, var4, Direction.CW);
         if(var3 > 0.0F) {
            var8.addCircle(var2.x, var2.y, var3, Direction.CCW);
         }

         this.mHighlightCirclePaint.setColor(var5);
         this.mHighlightCirclePaint.setStyle(Style.FILL);
         var1.drawPath(var8, this.mHighlightCirclePaint);
      }

      if(var6 != 1122867) {
         this.mHighlightCirclePaint.setColor(var6);
         this.mHighlightCirclePaint.setStyle(Style.STROKE);
         this.mHighlightCirclePaint.setStrokeWidth(Utils.convertDpToPixel(var7));
         var1.drawCircle(var2.x, var2.y, var4, this.mHighlightCirclePaint);
      }

      var1.restore();
   }

   public void drawHighlighted(Canvas var1, Highlight[] var2) {
      float var3 = this.mChart.getSliceAngle();
      float var4 = this.mChart.getFactor();
      MPPointF var9 = this.mChart.getCenterOffsets();
      MPPointF var10 = MPPointF.getInstance(0.0F, 0.0F);
      RadarData var11 = (RadarData)this.mChart.getData();
      int var8 = var2.length;

      for(int var6 = 0; var6 < var8; ++var6) {
         Highlight var12 = var2[var6];
         IRadarDataSet var13 = (IRadarDataSet)var11.getDataSetByIndex(var12.getDataSetIndex());
         if(var13 != null && var13.isHighlightEnabled()) {
            RadarEntry var14 = (RadarEntry)var13.getEntryForIndex((int)var12.getX());
            if(this.isInBoundsX(var14, var13)) {
               Utils.getPosition(var9, (var14.getY() - this.mChart.getYChartMin()) * var4 * this.mAnimator.getPhaseY(), var12.getX() * var3 * this.mAnimator.getPhaseX() + this.mChart.getRotationAngle(), var10);
               var12.setDraw(var10.x, var10.y);
               this.drawHighlightLines(var1, var10.x, var10.y, var13);
               if(var13.isDrawHighlightCircleEnabled() && !Float.isNaN(var10.x) && !Float.isNaN(var10.y)) {
                  int var7 = var13.getHighlightCircleStrokeColor();
                  int var5 = var7;
                  if(var7 == 1122867) {
                     var5 = var13.getColor(0);
                  }

                  var7 = var5;
                  if(var13.getHighlightCircleStrokeAlpha() < 255) {
                     var7 = ColorTemplate.colorWithAlpha(var5, var13.getHighlightCircleStrokeAlpha());
                  }

                  this.drawHighlightCircle(var1, var10, var13.getHighlightCircleInnerRadius(), var13.getHighlightCircleOuterRadius(), var13.getHighlightCircleFillColor(), var7, var13.getHighlightCircleStrokeWidth());
               }
            }
         }
      }

      MPPointF.recycleInstance(var9);
      MPPointF.recycleInstance(var10);
   }

   public void drawValues(Canvas var1) {
      float var2 = this.mAnimator.getPhaseX();
      float var5 = this.mAnimator.getPhaseY();
      float var3 = this.mChart.getSliceAngle();
      float var6 = this.mChart.getFactor();
      MPPointF var17 = this.mChart.getCenterOffsets();
      MPPointF var13 = MPPointF.getInstance(0.0F, 0.0F);
      MPPointF var12 = MPPointF.getInstance(0.0F, 0.0F);
      float var7 = Utils.convertDpToPixel(5.0F);

      MPPointF var14;
      for(int var10 = 0; var10 < ((RadarData)this.mChart.getData()).getDataSetCount(); var13 = var14) {
         IRadarDataSet var15 = (IRadarDataSet)((RadarData)this.mChart.getData()).getDataSetByIndex(var10);
         float var4;
         if(!this.shouldDrawValues(var15)) {
            var4 = var3;
            var14 = var13;
            var13 = var12;
            var3 = var2;
            var2 = var4;
            var12 = var14;
         } else {
            this.applyValueTextStyle(var15);
            var14 = MPPointF.getInstance(var15.getIconsOffset());
            var14.x = Utils.convertDpToPixel(var14.x);
            var14.y = Utils.convertDpToPixel(var14.y);

            for(int var11 = 0; var11 < var15.getEntryCount(); ++var11) {
               RadarEntry var16 = (RadarEntry)var15.getEntryForIndex(var11);
               var4 = var16.getY();
               float var8 = this.mChart.getYChartMin();
               float var9 = (float)var11 * var3 * var2;
               Utils.getPosition(var17, (var4 - var8) * var6 * var5, var9 + this.mChart.getRotationAngle(), var13);
               if(var15.isDrawValuesEnabled()) {
                  this.drawValue(var1, var15.getValueFormatter(), var16.getY(), var16, var10, var13.x, var13.y - var7, var15.getValueTextColor(var11));
               }

               if(var16.getIcon() != null && var15.isDrawIconsEnabled()) {
                  Drawable var18 = var16.getIcon();
                  Utils.getPosition(var17, var16.getY() * var6 * var5 + var14.y, var9 + this.mChart.getRotationAngle(), var12);
                  var12.y += var14.x;
                  Utils.drawImage(var1, var18, (int)var12.x, (int)var12.y, var18.getIntrinsicWidth(), var18.getIntrinsicHeight());
               }
            }

            var4 = var2;
            var2 = var3;
            MPPointF var19 = var12;
            MPPointF.recycleInstance(var14);
            var12 = var13;
            var3 = var4;
            var13 = var19;
         }

         ++var10;
         var4 = var2;
         var14 = var12;
         var12 = var13;
         var2 = var3;
         var3 = var4;
      }

      MPPointF.recycleInstance(var17);
      MPPointF.recycleInstance(var13);
      MPPointF.recycleInstance(var12);
   }

   protected void drawWeb(Canvas var1) {
      float var2 = this.mChart.getSliceAngle();
      float var3 = this.mChart.getFactor();
      float var4 = this.mChart.getRotationAngle();
      MPPointF var9 = this.mChart.getCenterOffsets();
      this.mWebPaint.setStrokeWidth(this.mChart.getWebLineWidth());
      this.mWebPaint.setColor(this.mChart.getWebColor());
      this.mWebPaint.setAlpha(this.mChart.getWebAlpha());
      int var7 = this.mChart.getSkipWebLineCount();
      int var8 = ((IRadarDataSet)((RadarData)this.mChart.getData()).getMaxEntryCountSet()).getEntryCount();
      MPPointF var10 = MPPointF.getInstance(0.0F, 0.0F);

      int var6;
      for(var6 = 0; var6 < var8; var6 += var7 + 1) {
         Utils.getPosition(var9, this.mChart.getYRange() * var3, (float)var6 * var2 + var4, var10);
         var1.drawLine(var9.x, var9.y, var10.x, var10.y, this.mWebPaint);
      }

      MPPointF.recycleInstance(var10);
      this.mWebPaint.setStrokeWidth(this.mChart.getWebLineWidthInner());
      this.mWebPaint.setColor(this.mChart.getWebColorInner());
      this.mWebPaint.setAlpha(this.mChart.getWebAlpha());
      var8 = this.mChart.getYAxis().mEntryCount;
      var10 = MPPointF.getInstance(0.0F, 0.0F);
      MPPointF var11 = MPPointF.getInstance(0.0F, 0.0F);

      for(var6 = 0; var6 < var8; ++var6) {
         var7 = 0;

         while(var7 < ((RadarData)this.mChart.getData()).getEntryCount()) {
            float var5 = (this.mChart.getYAxis().mEntries[var6] - this.mChart.getYChartMin()) * var3;
            Utils.getPosition(var9, var5, (float)var7 * var2 + var4, var10);
            ++var7;
            Utils.getPosition(var9, var5, (float)var7 * var2 + var4, var11);
            var1.drawLine(var10.x, var10.y, var11.x, var11.y, this.mWebPaint);
         }
      }

      MPPointF.recycleInstance(var10);
      MPPointF.recycleInstance(var11);
   }

   public Paint getWebPaint() {
      return this.mWebPaint;
   }

   public void initBuffers() {}
}
