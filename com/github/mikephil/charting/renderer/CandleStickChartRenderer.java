package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.CandleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.renderer.LineScatterCandleRadarRenderer;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.Iterator;
import java.util.List;

public class CandleStickChartRenderer extends LineScatterCandleRadarRenderer {

   private float[] mBodyBuffers = new float[4];
   protected CandleDataProvider mChart;
   private float[] mCloseBuffers = new float[4];
   private float[] mOpenBuffers = new float[4];
   private float[] mRangeBuffers = new float[4];
   private float[] mShadowBuffers = new float[8];


   public CandleStickChartRenderer(CandleDataProvider var1, ChartAnimator var2, ViewPortHandler var3) {
      super(var2, var3);
      this.mChart = var1;
   }

   public void drawData(Canvas var1) {
      Iterator var2 = this.mChart.getCandleData().getDataSets().iterator();

      while(var2.hasNext()) {
         ICandleDataSet var3 = (ICandleDataSet)var2.next();
         if(var3.isVisible()) {
            this.drawDataSet(var1, var3);
         }
      }

   }

   protected void drawDataSet(Canvas var1, ICandleDataSet var2) {
      Transformer var13 = this.mChart.getTransformer(var2.getAxisDependency());
      float var3 = this.mAnimator.getPhaseY();
      float var4 = var2.getBarSpace();
      boolean var12 = var2.getShowCandleBar();
      this.mXBounds.set(this.mChart, var2);
      this.mRenderPaint.setStrokeWidth(var2.getShadowWidth());

      for(int var11 = this.mXBounds.min; var11 <= this.mXBounds.range + this.mXBounds.min; ++var11) {
         CandleEntry var14 = (CandleEntry)var2.getEntryForIndex(var11);
         if(var14 != null) {
            float var5 = var14.getX();
            float var6 = var14.getOpen();
            float var7 = var14.getClose();
            float var8 = var14.getHigh();
            float var9 = var14.getLow();
            int var10;
            if(var12) {
               this.mShadowBuffers[0] = var5;
               this.mShadowBuffers[2] = var5;
               this.mShadowBuffers[4] = var5;
               this.mShadowBuffers[6] = var5;
               if(var6 > var7) {
                  this.mShadowBuffers[1] = var8 * var3;
                  this.mShadowBuffers[3] = var6 * var3;
                  this.mShadowBuffers[5] = var9 * var3;
                  this.mShadowBuffers[7] = var7 * var3;
               } else if(var6 < var7) {
                  this.mShadowBuffers[1] = var8 * var3;
                  this.mShadowBuffers[3] = var7 * var3;
                  this.mShadowBuffers[5] = var9 * var3;
                  this.mShadowBuffers[7] = var6 * var3;
               } else {
                  this.mShadowBuffers[1] = var8 * var3;
                  this.mShadowBuffers[3] = var6 * var3;
                  this.mShadowBuffers[5] = var9 * var3;
                  this.mShadowBuffers[7] = this.mShadowBuffers[3];
               }

               var13.pointValuesToPixel(this.mShadowBuffers);
               Paint var15;
               if(var2.getShadowColorSameAsCandle()) {
                  if(var6 > var7) {
                     var15 = this.mRenderPaint;
                     if(var2.getDecreasingColor() == 1122867) {
                        var10 = var2.getColor(var11);
                     } else {
                        var10 = var2.getDecreasingColor();
                     }

                     var15.setColor(var10);
                  } else if(var6 < var7) {
                     var15 = this.mRenderPaint;
                     if(var2.getIncreasingColor() == 1122867) {
                        var10 = var2.getColor(var11);
                     } else {
                        var10 = var2.getIncreasingColor();
                     }

                     var15.setColor(var10);
                  } else {
                     var15 = this.mRenderPaint;
                     if(var2.getNeutralColor() == 1122867) {
                        var10 = var2.getColor(var11);
                     } else {
                        var10 = var2.getNeutralColor();
                     }

                     var15.setColor(var10);
                  }
               } else {
                  var15 = this.mRenderPaint;
                  if(var2.getShadowColor() == 1122867) {
                     var10 = var2.getColor(var11);
                  } else {
                     var10 = var2.getShadowColor();
                  }

                  var15.setColor(var10);
               }

               this.mRenderPaint.setStyle(Style.STROKE);
               var1.drawLines(this.mShadowBuffers, this.mRenderPaint);
               this.mBodyBuffers[0] = var5 - 0.5F + var4;
               this.mBodyBuffers[1] = var7 * var3;
               this.mBodyBuffers[2] = var5 + 0.5F - var4;
               this.mBodyBuffers[3] = var6 * var3;
               var13.pointValuesToPixel(this.mBodyBuffers);
               if(var6 > var7) {
                  if(var2.getDecreasingColor() == 1122867) {
                     this.mRenderPaint.setColor(var2.getColor(var11));
                  } else {
                     this.mRenderPaint.setColor(var2.getDecreasingColor());
                  }

                  this.mRenderPaint.setStyle(var2.getDecreasingPaintStyle());
                  var1.drawRect(this.mBodyBuffers[0], this.mBodyBuffers[3], this.mBodyBuffers[2], this.mBodyBuffers[1], this.mRenderPaint);
               } else if(var6 < var7) {
                  if(var2.getIncreasingColor() == 1122867) {
                     this.mRenderPaint.setColor(var2.getColor(var11));
                  } else {
                     this.mRenderPaint.setColor(var2.getIncreasingColor());
                  }

                  this.mRenderPaint.setStyle(var2.getIncreasingPaintStyle());
                  var1.drawRect(this.mBodyBuffers[0], this.mBodyBuffers[1], this.mBodyBuffers[2], this.mBodyBuffers[3], this.mRenderPaint);
               } else {
                  if(var2.getNeutralColor() == 1122867) {
                     this.mRenderPaint.setColor(var2.getColor(var11));
                  } else {
                     this.mRenderPaint.setColor(var2.getNeutralColor());
                  }

                  var1.drawLine(this.mBodyBuffers[0], this.mBodyBuffers[1], this.mBodyBuffers[2], this.mBodyBuffers[3], this.mRenderPaint);
               }
            } else {
               this.mRangeBuffers[0] = var5;
               this.mRangeBuffers[1] = var8 * var3;
               this.mRangeBuffers[2] = var5;
               this.mRangeBuffers[3] = var9 * var3;
               this.mOpenBuffers[0] = var5 - 0.5F + var4;
               float[] var16 = this.mOpenBuffers;
               var8 = var6 * var3;
               var16[1] = var8;
               this.mOpenBuffers[2] = var5;
               this.mOpenBuffers[3] = var8;
               this.mCloseBuffers[0] = 0.5F + var5 - var4;
               var16 = this.mCloseBuffers;
               var8 = var7 * var3;
               var16[1] = var8;
               this.mCloseBuffers[2] = var5;
               this.mCloseBuffers[3] = var8;
               var13.pointValuesToPixel(this.mRangeBuffers);
               var13.pointValuesToPixel(this.mOpenBuffers);
               var13.pointValuesToPixel(this.mCloseBuffers);
               if(var6 > var7) {
                  if(var2.getDecreasingColor() == 1122867) {
                     var10 = var2.getColor(var11);
                  } else {
                     var10 = var2.getDecreasingColor();
                  }
               } else if(var6 < var7) {
                  if(var2.getIncreasingColor() == 1122867) {
                     var10 = var2.getColor(var11);
                  } else {
                     var10 = var2.getIncreasingColor();
                  }
               } else if(var2.getNeutralColor() == 1122867) {
                  var10 = var2.getColor(var11);
               } else {
                  var10 = var2.getNeutralColor();
               }

               this.mRenderPaint.setColor(var10);
               var1.drawLine(this.mRangeBuffers[0], this.mRangeBuffers[1], this.mRangeBuffers[2], this.mRangeBuffers[3], this.mRenderPaint);
               var1.drawLine(this.mOpenBuffers[0], this.mOpenBuffers[1], this.mOpenBuffers[2], this.mOpenBuffers[3], this.mRenderPaint);
               var1.drawLine(this.mCloseBuffers[0], this.mCloseBuffers[1], this.mCloseBuffers[2], this.mCloseBuffers[3], this.mRenderPaint);
            }
         }
      }

   }

   public void drawExtras(Canvas var1) {}

   public void drawHighlighted(Canvas var1, Highlight[] var2) {
      CandleData var6 = this.mChart.getCandleData();
      int var5 = var2.length;

      for(int var4 = 0; var4 < var5; ++var4) {
         Highlight var7 = var2[var4];
         ICandleDataSet var8 = (ICandleDataSet)var6.getDataSetByIndex(var7.getDataSetIndex());
         if(var8 != null && var8.isHighlightEnabled()) {
            CandleEntry var9 = (CandleEntry)var8.getEntryForXValue(var7.getX(), var7.getY());
            if(this.isInBoundsX(var9, var8)) {
               float var3 = (var9.getLow() * this.mAnimator.getPhaseY() + var9.getHigh() * this.mAnimator.getPhaseY()) / 2.0F;
               MPPointD var10 = this.mChart.getTransformer(var8.getAxisDependency()).getPixelForValues(var9.getX(), var3);
               var7.setDraw((float)var10.x, (float)var10.y);
               this.drawHighlightLines(var1, (float)var10.x, (float)var10.y, var8);
            }
         }
      }

   }

   public void drawValues(Canvas var1) {
      if(this.isDrawingValuesAllowed(this.mChart)) {
         List var11 = this.mChart.getCandleData().getDataSets();

         for(int var5 = 0; var5 < var11.size(); ++var5) {
            ICandleDataSet var12 = (ICandleDataSet)var11.get(var5);
            if(this.shouldDrawValues(var12)) {
               this.applyValueTextStyle(var12);
               Transformer var8 = this.mChart.getTransformer(var12.getAxisDependency());
               this.mXBounds.set(this.mChart, var12);
               float[] var13 = var8.generateTransformedValuesCandle(var12, this.mAnimator.getPhaseX(), this.mAnimator.getPhaseY(), this.mXBounds.min, this.mXBounds.max);
               float var2 = Utils.convertDpToPixel(5.0F);
               MPPointF var14 = MPPointF.getInstance(var12.getIconsOffset());
               var14.x = Utils.convertDpToPixel(var14.x);
               var14.y = Utils.convertDpToPixel(var14.y);

               for(int var6 = 0; var6 < var13.length; var6 += 2) {
                  float var3 = var13[var6];
                  float var4 = var13[var6 + 1];
                  if(!this.mViewPortHandler.isInBoundsRight(var3)) {
                     break;
                  }

                  if(this.mViewPortHandler.isInBoundsLeft(var3) && this.mViewPortHandler.isInBoundsY(var4)) {
                     int var7 = var6 / 2;
                     CandleEntry var10 = (CandleEntry)var12.getEntryForIndex(this.mXBounds.min + var7);
                     if(var12.isDrawValuesEnabled()) {
                        this.drawValue(var1, var12.getValueFormatter(), var10.getHigh(), var10, var5, var3, var4 - var2, var12.getValueTextColor(var7));
                     }

                     if(var10.getIcon() != null && var12.isDrawIconsEnabled()) {
                        Drawable var15 = var10.getIcon();
                        Utils.drawImage(var1, var15, (int)(var3 + var14.x), (int)(var4 + var14.y), var15.getIntrinsicWidth(), var15.getIntrinsicHeight());
                     }
                  }
               }

               MPPointF.recycleInstance(var14);
            }
         }
      }

   }

   public void initBuffers() {}
}
