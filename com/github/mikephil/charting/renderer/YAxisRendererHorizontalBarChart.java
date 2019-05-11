package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class YAxisRendererHorizontalBarChart extends YAxisRenderer {

   protected Path mDrawZeroLinePathBuffer = new Path();
   protected float[] mRenderLimitLinesBuffer = new float[4];
   protected Path mRenderLimitLinesPathBuffer = new Path();


   public YAxisRendererHorizontalBarChart(ViewPortHandler var1, YAxis var2, Transformer var3) {
      super(var1, var2, var3);
      this.mLimitLinePaint.setTextAlign(Align.LEFT);
   }

   public void computeAxis(float var1, float var2, boolean var3) {
      float var5 = var1;
      float var4 = var2;
      if(this.mViewPortHandler.contentHeight() > 10.0F) {
         var5 = var1;
         var4 = var2;
         if(!this.mViewPortHandler.isFullyZoomedOutX()) {
            MPPointD var6 = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop());
            MPPointD var7 = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentTop());
            if(!var3) {
               var2 = (float)var6.x;
               var1 = (float)var7.x;
            } else {
               var2 = (float)var7.x;
               var1 = (float)var6.x;
            }

            MPPointD.recycleInstance(var6);
            MPPointD.recycleInstance(var7);
            var4 = var1;
            var5 = var2;
         }
      }

      this.computeAxisValues(var5, var4);
   }

   protected void drawYLabels(Canvas var1, float var2, float[] var3, float var4) {
      this.mAxisLabelPaint.setTypeface(this.mYAxis.getTypeface());
      this.mAxisLabelPaint.setTextSize(this.mYAxis.getTextSize());
      this.mAxisLabelPaint.setColor(this.mYAxis.getTextColor());
      int var6 = this.mYAxis.isDrawBottomYLabelEntryEnabled() ^ 1;
      int var5;
      if(this.mYAxis.isDrawTopYLabelEntryEnabled()) {
         var5 = this.mYAxis.mEntryCount;
      } else {
         var5 = this.mYAxis.mEntryCount - 1;
      }

      while(var6 < var5) {
         var1.drawText(this.mYAxis.getFormattedLabel(var6), var3[var6 * 2], var2 - var4, this.mAxisLabelPaint);
         ++var6;
      }

   }

   protected void drawZeroLine(Canvas var1) {
      int var2 = var1.save();
      this.mZeroLineClippingRect.set(this.mViewPortHandler.getContentRect());
      this.mZeroLineClippingRect.inset(-this.mYAxis.getZeroLineWidth(), 0.0F);
      var1.clipRect(this.mLimitLineClippingRect);
      MPPointD var3 = this.mTrans.getPixelForValues(0.0F, 0.0F);
      this.mZeroLinePaint.setColor(this.mYAxis.getZeroLineColor());
      this.mZeroLinePaint.setStrokeWidth(this.mYAxis.getZeroLineWidth());
      Path var4 = this.mDrawZeroLinePathBuffer;
      var4.reset();
      var4.moveTo((float)var3.x - 1.0F, this.mViewPortHandler.contentTop());
      var4.lineTo((float)var3.x - 1.0F, this.mViewPortHandler.contentBottom());
      var1.drawPath(var4, this.mZeroLinePaint);
      var1.restoreToCount(var2);
   }

   public RectF getGridClippingRect() {
      this.mGridClippingRect.set(this.mViewPortHandler.getContentRect());
      this.mGridClippingRect.inset(-this.mAxis.getGridLineWidth(), 0.0F);
      return this.mGridClippingRect;
   }

   protected float[] getTransformedPositions() {
      if(this.mGetTransformedPositionsBuffer.length != this.mYAxis.mEntryCount * 2) {
         this.mGetTransformedPositionsBuffer = new float[this.mYAxis.mEntryCount * 2];
      }

      float[] var2 = this.mGetTransformedPositionsBuffer;

      for(int var1 = 0; var1 < var2.length; var1 += 2) {
         var2[var1] = this.mYAxis.mEntries[var1 / 2];
      }

      this.mTrans.pointValuesToPixel(var2);
      return var2;
   }

   protected Path linePath(Path var1, int var2, float[] var3) {
      var1.moveTo(var3[var2], this.mViewPortHandler.contentTop());
      var1.lineTo(var3[var2], this.mViewPortHandler.contentBottom());
      return var1;
   }

   public void renderAxisLabels(Canvas var1) {
      if(this.mYAxis.isEnabled()) {
         if(this.mYAxis.isDrawLabelsEnabled()) {
            float[] var4 = this.getTransformedPositions();
            this.mAxisLabelPaint.setTypeface(this.mYAxis.getTypeface());
            this.mAxisLabelPaint.setTextSize(this.mYAxis.getTextSize());
            this.mAxisLabelPaint.setColor(this.mYAxis.getTextColor());
            this.mAxisLabelPaint.setTextAlign(Align.CENTER);
            float var2 = Utils.convertDpToPixel(2.5F);
            float var3 = (float)Utils.calcTextHeight(this.mAxisLabelPaint, "Q");
            YAxis.AxisDependency var5 = this.mYAxis.getAxisDependency();
            YAxis.YAxisLabelPosition var6 = this.mYAxis.getLabelPosition();
            if(var5 == YAxis.AxisDependency.LEFT) {
               if(var6 == YAxis.YAxisLabelPosition.OUTSIDE_CHART) {
                  var2 = this.mViewPortHandler.contentTop() - var2;
               } else {
                  var2 = this.mViewPortHandler.contentTop() - var2;
               }
            } else if(var6 == YAxis.YAxisLabelPosition.OUTSIDE_CHART) {
               var2 += this.mViewPortHandler.contentBottom() + var3;
            } else {
               var2 += this.mViewPortHandler.contentBottom() + var3;
            }

            this.drawYLabels(var1, var2, var4, this.mYAxis.getYOffset());
         }
      }
   }

   public void renderAxisLine(Canvas var1) {
      if(this.mYAxis.isEnabled()) {
         if(this.mYAxis.isDrawAxisLineEnabled()) {
            this.mAxisLinePaint.setColor(this.mYAxis.getAxisLineColor());
            this.mAxisLinePaint.setStrokeWidth(this.mYAxis.getAxisLineWidth());
            if(this.mYAxis.getAxisDependency() == YAxis.AxisDependency.LEFT) {
               var1.drawLine(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop(), this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentTop(), this.mAxisLinePaint);
            } else {
               var1.drawLine(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom(), this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentBottom(), this.mAxisLinePaint);
            }
         }
      }
   }

   public void renderLimitLines(Canvas var1) {
      List var7 = this.mYAxis.getLimitLines();
      if(var7 != null) {
         if(var7.size() > 0) {
            float[] var8 = this.mRenderLimitLinesBuffer;
            var8[0] = 0.0F;
            var8[1] = 0.0F;
            var8[2] = 0.0F;
            var8[3] = 0.0F;
            Path var9 = this.mRenderLimitLinesPathBuffer;
            var9.reset();

            for(int var5 = 0; var5 < var7.size(); ++var5) {
               LimitLine var11 = (LimitLine)var7.get(var5);
               if(var11.isEnabled()) {
                  int var6 = var1.save();
                  this.mLimitLineClippingRect.set(this.mViewPortHandler.getContentRect());
                  this.mLimitLineClippingRect.inset(-var11.getLineWidth(), 0.0F);
                  var1.clipRect(this.mLimitLineClippingRect);
                  var8[0] = var11.getLimit();
                  var8[2] = var11.getLimit();
                  this.mTrans.pointValuesToPixel(var8);
                  var8[1] = this.mViewPortHandler.contentTop();
                  var8[3] = this.mViewPortHandler.contentBottom();
                  var9.moveTo(var8[0], var8[1]);
                  var9.lineTo(var8[2], var8[3]);
                  this.mLimitLinePaint.setStyle(Style.STROKE);
                  this.mLimitLinePaint.setColor(var11.getLineColor());
                  this.mLimitLinePaint.setPathEffect(var11.getDashPathEffect());
                  this.mLimitLinePaint.setStrokeWidth(var11.getLineWidth());
                  var1.drawPath(var9, this.mLimitLinePaint);
                  var9.reset();
                  String var10 = var11.getLabel();
                  if(var10 != null && !var10.equals("")) {
                     this.mLimitLinePaint.setStyle(var11.getTextStyle());
                     this.mLimitLinePaint.setPathEffect((PathEffect)null);
                     this.mLimitLinePaint.setColor(var11.getTextColor());
                     this.mLimitLinePaint.setTypeface(var11.getTypeface());
                     this.mLimitLinePaint.setStrokeWidth(0.5F);
                     this.mLimitLinePaint.setTextSize(var11.getTextSize());
                     float var2 = var11.getLineWidth() + var11.getXOffset();
                     float var3 = Utils.convertDpToPixel(2.0F) + var11.getYOffset();
                     LimitLine.LimitLabelPosition var12 = var11.getLabelPosition();
                     float var4;
                     if(var12 == LimitLine.LimitLabelPosition.RIGHT_TOP) {
                        var4 = (float)Utils.calcTextHeight(this.mLimitLinePaint, var10);
                        this.mLimitLinePaint.setTextAlign(Align.LEFT);
                        var1.drawText(var10, var8[0] + var2, this.mViewPortHandler.contentTop() + var3 + var4, this.mLimitLinePaint);
                     } else if(var12 == LimitLine.LimitLabelPosition.RIGHT_BOTTOM) {
                        this.mLimitLinePaint.setTextAlign(Align.LEFT);
                        var1.drawText(var10, var8[0] + var2, this.mViewPortHandler.contentBottom() - var3, this.mLimitLinePaint);
                     } else if(var12 == LimitLine.LimitLabelPosition.LEFT_TOP) {
                        this.mLimitLinePaint.setTextAlign(Align.RIGHT);
                        var4 = (float)Utils.calcTextHeight(this.mLimitLinePaint, var10);
                        var1.drawText(var10, var8[0] - var2, this.mViewPortHandler.contentTop() + var3 + var4, this.mLimitLinePaint);
                     } else {
                        this.mLimitLinePaint.setTextAlign(Align.RIGHT);
                        var1.drawText(var10, var8[0] - var2, this.mViewPortHandler.contentBottom() - var3, this.mLimitLinePaint);
                     }
                  }

                  var1.restoreToCount(var6);
               }
            }

         }
      }
   }
}
