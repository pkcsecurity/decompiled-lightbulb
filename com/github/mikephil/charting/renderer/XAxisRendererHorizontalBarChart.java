package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class XAxisRendererHorizontalBarChart extends XAxisRenderer {

   protected BarChart mChart;
   protected Path mRenderLimitLinesPathBuffer = new Path();


   public XAxisRendererHorizontalBarChart(ViewPortHandler var1, XAxis var2, Transformer var3, BarChart var4) {
      super(var1, var2, var3);
      this.mChart = var4;
   }

   public void computeAxis(float var1, float var2, boolean var3) {
      float var5 = var1;
      float var4 = var2;
      if(this.mViewPortHandler.contentWidth() > 10.0F) {
         var5 = var1;
         var4 = var2;
         if(!this.mViewPortHandler.isFullyZoomedOutY()) {
            MPPointD var6 = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom());
            MPPointD var7 = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop());
            if(var3) {
               var2 = (float)var7.y;
               var1 = (float)var6.y;
            } else {
               var2 = (float)var6.y;
               var1 = (float)var7.y;
            }

            MPPointD.recycleInstance(var6);
            MPPointD.recycleInstance(var7);
            var4 = var1;
            var5 = var2;
         }
      }

      this.computeAxisValues(var5, var4);
   }

   protected void computeSize() {
      this.mAxisLabelPaint.setTypeface(this.mXAxis.getTypeface());
      this.mAxisLabelPaint.setTextSize(this.mXAxis.getTextSize());
      String var3 = this.mXAxis.getLongestLabel();
      FSize var4 = Utils.calcTextSize(this.mAxisLabelPaint, var3);
      float var1 = (float)((int)(var4.width + this.mXAxis.getXOffset() * 3.5F));
      float var2 = var4.height;
      var4 = Utils.getSizeOfRotatedRectangleByDegrees(var4.width, var2, this.mXAxis.getLabelRotationAngle());
      this.mXAxis.mLabelWidth = Math.round(var1);
      this.mXAxis.mLabelHeight = Math.round(var2);
      this.mXAxis.mLabelRotatedWidth = (int)(var4.width + this.mXAxis.getXOffset() * 3.5F);
      this.mXAxis.mLabelRotatedHeight = Math.round(var4.height);
      FSize.recycleInstance(var4);
   }

   protected void drawGridLine(Canvas var1, float var2, float var3, Path var4) {
      var4.moveTo(this.mViewPortHandler.contentRight(), var3);
      var4.lineTo(this.mViewPortHandler.contentLeft(), var3);
      var1.drawPath(var4, this.mGridPaint);
      var4.reset();
   }

   protected void drawLabels(Canvas var1, float var2, MPPointF var3) {
      float var4 = this.mXAxis.getLabelRotationAngle();
      boolean var7 = this.mXAxis.isCenterAxisLabelsEnabled();
      float[] var8 = new float[this.mXAxis.mEntryCount * 2];

      int var6;
      for(var6 = 0; var6 < var8.length; var6 += 2) {
         if(var7) {
            var8[var6 + 1] = this.mXAxis.mCenteredEntries[var6 / 2];
         } else {
            var8[var6 + 1] = this.mXAxis.mEntries[var6 / 2];
         }
      }

      this.mTrans.pointValuesToPixel(var8);

      for(var6 = 0; var6 < var8.length; var6 += 2) {
         float var5 = var8[var6 + 1];
         if(this.mViewPortHandler.isInBoundsY(var5)) {
            this.drawLabel(var1, this.mXAxis.getValueFormatter().getFormattedValue(this.mXAxis.mEntries[var6 / 2], this.mXAxis), var2, var5, var3, var4);
         }
      }

   }

   public RectF getGridClippingRect() {
      this.mGridClippingRect.set(this.mViewPortHandler.getContentRect());
      this.mGridClippingRect.inset(0.0F, -this.mAxis.getGridLineWidth());
      return this.mGridClippingRect;
   }

   public void renderAxisLabels(Canvas var1) {
      if(this.mXAxis.isEnabled()) {
         if(this.mXAxis.isDrawLabelsEnabled()) {
            float var2 = this.mXAxis.getXOffset();
            this.mAxisLabelPaint.setTypeface(this.mXAxis.getTypeface());
            this.mAxisLabelPaint.setTextSize(this.mXAxis.getTextSize());
            this.mAxisLabelPaint.setColor(this.mXAxis.getTextColor());
            MPPointF var3 = MPPointF.getInstance(0.0F, 0.0F);
            if(this.mXAxis.getPosition() == XAxis.XAxisPosition.TOP) {
               var3.x = 0.0F;
               var3.y = 0.5F;
               this.drawLabels(var1, this.mViewPortHandler.contentRight() + var2, var3);
            } else if(this.mXAxis.getPosition() == XAxis.XAxisPosition.TOP_INSIDE) {
               var3.x = 1.0F;
               var3.y = 0.5F;
               this.drawLabels(var1, this.mViewPortHandler.contentRight() - var2, var3);
            } else if(this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM) {
               var3.x = 1.0F;
               var3.y = 0.5F;
               this.drawLabels(var1, this.mViewPortHandler.contentLeft() - var2, var3);
            } else if(this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM_INSIDE) {
               var3.x = 1.0F;
               var3.y = 0.5F;
               this.drawLabels(var1, this.mViewPortHandler.contentLeft() + var2, var3);
            } else {
               var3.x = 0.0F;
               var3.y = 0.5F;
               this.drawLabels(var1, this.mViewPortHandler.contentRight() + var2, var3);
               var3.x = 1.0F;
               var3.y = 0.5F;
               this.drawLabels(var1, this.mViewPortHandler.contentLeft() - var2, var3);
            }

            MPPointF.recycleInstance(var3);
         }
      }
   }

   public void renderAxisLine(Canvas var1) {
      if(this.mXAxis.isDrawAxisLineEnabled()) {
         if(this.mXAxis.isEnabled()) {
            this.mAxisLinePaint.setColor(this.mXAxis.getAxisLineColor());
            this.mAxisLinePaint.setStrokeWidth(this.mXAxis.getAxisLineWidth());
            if(this.mXAxis.getPosition() == XAxis.XAxisPosition.TOP || this.mXAxis.getPosition() == XAxis.XAxisPosition.TOP_INSIDE || this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTH_SIDED) {
               var1.drawLine(this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentTop(), this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentBottom(), this.mAxisLinePaint);
            }

            if(this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM || this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM_INSIDE || this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTH_SIDED) {
               var1.drawLine(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop(), this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom(), this.mAxisLinePaint);
            }

         }
      }
   }

   public void renderLimitLines(Canvas var1) {
      List var7 = this.mXAxis.getLimitLines();
      if(var7 != null) {
         if(var7.size() > 0) {
            float[] var8 = this.mRenderLimitLinesBuffer;
            int var5 = 0;
            var8[0] = 0.0F;
            var8[1] = 0.0F;
            Path var9 = this.mRenderLimitLinesPathBuffer;
            var9.reset();

            for(; var5 < var7.size(); ++var5) {
               LimitLine var11 = (LimitLine)var7.get(var5);
               if(var11.isEnabled()) {
                  int var6 = var1.save();
                  this.mLimitLineClippingRect.set(this.mViewPortHandler.getContentRect());
                  this.mLimitLineClippingRect.inset(0.0F, -var11.getLineWidth());
                  var1.clipRect(this.mLimitLineClippingRect);
                  this.mLimitLinePaint.setStyle(Style.STROKE);
                  this.mLimitLinePaint.setColor(var11.getLineColor());
                  this.mLimitLinePaint.setStrokeWidth(var11.getLineWidth());
                  this.mLimitLinePaint.setPathEffect(var11.getDashPathEffect());
                  var8[1] = var11.getLimit();
                  this.mTrans.pointValuesToPixel(var8);
                  var9.moveTo(this.mViewPortHandler.contentLeft(), var8[1]);
                  var9.lineTo(this.mViewPortHandler.contentRight(), var8[1]);
                  var1.drawPath(var9, this.mLimitLinePaint);
                  var9.reset();
                  String var10 = var11.getLabel();
                  if(var10 != null && !var10.equals("")) {
                     this.mLimitLinePaint.setStyle(var11.getTextStyle());
                     this.mLimitLinePaint.setPathEffect((PathEffect)null);
                     this.mLimitLinePaint.setColor(var11.getTextColor());
                     this.mLimitLinePaint.setStrokeWidth(0.5F);
                     this.mLimitLinePaint.setTextSize(var11.getTextSize());
                     float var2 = (float)Utils.calcTextHeight(this.mLimitLinePaint, var10);
                     float var3 = Utils.convertDpToPixel(4.0F) + var11.getXOffset();
                     float var4 = var11.getLineWidth() + var2 + var11.getYOffset();
                     LimitLine.LimitLabelPosition var12 = var11.getLabelPosition();
                     if(var12 == LimitLine.LimitLabelPosition.RIGHT_TOP) {
                        this.mLimitLinePaint.setTextAlign(Align.RIGHT);
                        var1.drawText(var10, this.mViewPortHandler.contentRight() - var3, var8[1] - var4 + var2, this.mLimitLinePaint);
                     } else if(var12 == LimitLine.LimitLabelPosition.RIGHT_BOTTOM) {
                        this.mLimitLinePaint.setTextAlign(Align.RIGHT);
                        var1.drawText(var10, this.mViewPortHandler.contentRight() - var3, var8[1] + var4, this.mLimitLinePaint);
                     } else if(var12 == LimitLine.LimitLabelPosition.LEFT_TOP) {
                        this.mLimitLinePaint.setTextAlign(Align.LEFT);
                        var1.drawText(var10, this.mViewPortHandler.contentLeft() + var3, var8[1] - var4 + var2, this.mLimitLinePaint);
                     } else {
                        this.mLimitLinePaint.setTextAlign(Align.LEFT);
                        var1.drawText(var10, this.mViewPortHandler.offsetLeft() + var3, var8[1] + var4, this.mLimitLinePaint);
                     }
                  }

                  var1.restoreToCount(var6);
               }
            }

         }
      }
   }
}
