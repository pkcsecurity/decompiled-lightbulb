package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.renderer.AxisRenderer;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class YAxisRenderer extends AxisRenderer {

   protected Path mDrawZeroLinePath = new Path();
   protected float[] mGetTransformedPositionsBuffer = new float[2];
   protected RectF mGridClippingRect = new RectF();
   protected RectF mLimitLineClippingRect = new RectF();
   protected Path mRenderGridLinesPath = new Path();
   protected Path mRenderLimitLines = new Path();
   protected float[] mRenderLimitLinesBuffer = new float[2];
   protected YAxis mYAxis;
   protected RectF mZeroLineClippingRect = new RectF();
   protected Paint mZeroLinePaint;


   public YAxisRenderer(ViewPortHandler var1, YAxis var2, Transformer var3) {
      super(var1, var3, var2);
      this.mYAxis = var2;
      if(this.mViewPortHandler != null) {
         this.mAxisLabelPaint.setColor(-16777216);
         this.mAxisLabelPaint.setTextSize(Utils.convertDpToPixel(10.0F));
         this.mZeroLinePaint = new Paint(1);
         this.mZeroLinePaint.setColor(-7829368);
         this.mZeroLinePaint.setStrokeWidth(1.0F);
         this.mZeroLinePaint.setStyle(Style.STROKE);
      }

   }

   protected void drawYLabels(Canvas var1, float var2, float[] var3, float var4) {
      int var6 = this.mYAxis.isDrawBottomYLabelEntryEnabled() ^ 1;
      int var5;
      if(this.mYAxis.isDrawTopYLabelEntryEnabled()) {
         var5 = this.mYAxis.mEntryCount;
      } else {
         var5 = this.mYAxis.mEntryCount - 1;
      }

      while(var6 < var5) {
         var1.drawText(this.mYAxis.getFormattedLabel(var6), var2, var3[var6 * 2 + 1] + var4, this.mAxisLabelPaint);
         ++var6;
      }

   }

   protected void drawZeroLine(Canvas var1) {
      int var2 = var1.save();
      this.mZeroLineClippingRect.set(this.mViewPortHandler.getContentRect());
      this.mZeroLineClippingRect.inset(0.0F, -this.mYAxis.getZeroLineWidth());
      var1.clipRect(this.mZeroLineClippingRect);
      MPPointD var3 = this.mTrans.getPixelForValues(0.0F, 0.0F);
      this.mZeroLinePaint.setColor(this.mYAxis.getZeroLineColor());
      this.mZeroLinePaint.setStrokeWidth(this.mYAxis.getZeroLineWidth());
      Path var4 = this.mDrawZeroLinePath;
      var4.reset();
      var4.moveTo(this.mViewPortHandler.contentLeft(), (float)var3.y);
      var4.lineTo(this.mViewPortHandler.contentRight(), (float)var3.y);
      var1.drawPath(var4, this.mZeroLinePaint);
      var1.restoreToCount(var2);
   }

   public RectF getGridClippingRect() {
      this.mGridClippingRect.set(this.mViewPortHandler.getContentRect());
      this.mGridClippingRect.inset(0.0F, -this.mAxis.getGridLineWidth());
      return this.mGridClippingRect;
   }

   protected float[] getTransformedPositions() {
      if(this.mGetTransformedPositionsBuffer.length != this.mYAxis.mEntryCount * 2) {
         this.mGetTransformedPositionsBuffer = new float[this.mYAxis.mEntryCount * 2];
      }

      float[] var2 = this.mGetTransformedPositionsBuffer;

      for(int var1 = 0; var1 < var2.length; var1 += 2) {
         var2[var1 + 1] = this.mYAxis.mEntries[var1 / 2];
      }

      this.mTrans.pointValuesToPixel(var2);
      return var2;
   }

   protected Path linePath(Path var1, int var2, float[] var3) {
      float var4 = this.mViewPortHandler.offsetLeft();
      ++var2;
      var1.moveTo(var4, var3[var2]);
      var1.lineTo(this.mViewPortHandler.contentRight(), var3[var2]);
      return var1;
   }

   public void renderAxisLabels(Canvas var1) {
      if(this.mYAxis.isEnabled()) {
         if(this.mYAxis.isDrawLabelsEnabled()) {
            float[] var5 = this.getTransformedPositions();
            this.mAxisLabelPaint.setTypeface(this.mYAxis.getTypeface());
            this.mAxisLabelPaint.setTextSize(this.mYAxis.getTextSize());
            this.mAxisLabelPaint.setColor(this.mYAxis.getTextColor());
            float var2 = this.mYAxis.getXOffset();
            float var3 = (float)Utils.calcTextHeight(this.mAxisLabelPaint, "A") / 2.5F;
            float var4 = this.mYAxis.getYOffset();
            YAxis.AxisDependency var6 = this.mYAxis.getAxisDependency();
            YAxis.YAxisLabelPosition var7 = this.mYAxis.getLabelPosition();
            if(var6 == YAxis.AxisDependency.LEFT) {
               if(var7 == YAxis.YAxisLabelPosition.OUTSIDE_CHART) {
                  this.mAxisLabelPaint.setTextAlign(Align.RIGHT);
                  var2 = this.mViewPortHandler.offsetLeft() - var2;
               } else {
                  this.mAxisLabelPaint.setTextAlign(Align.LEFT);
                  var2 += this.mViewPortHandler.offsetLeft();
               }
            } else if(var7 == YAxis.YAxisLabelPosition.OUTSIDE_CHART) {
               this.mAxisLabelPaint.setTextAlign(Align.LEFT);
               var2 += this.mViewPortHandler.contentRight();
            } else {
               this.mAxisLabelPaint.setTextAlign(Align.RIGHT);
               var2 = this.mViewPortHandler.contentRight() - var2;
            }

            this.drawYLabels(var1, var2, var5, var3 + var4);
         }
      }
   }

   public void renderAxisLine(Canvas var1) {
      if(this.mYAxis.isEnabled()) {
         if(this.mYAxis.isDrawAxisLineEnabled()) {
            this.mAxisLinePaint.setColor(this.mYAxis.getAxisLineColor());
            this.mAxisLinePaint.setStrokeWidth(this.mYAxis.getAxisLineWidth());
            if(this.mYAxis.getAxisDependency() == YAxis.AxisDependency.LEFT) {
               var1.drawLine(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop(), this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom(), this.mAxisLinePaint);
            } else {
               var1.drawLine(this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentTop(), this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentBottom(), this.mAxisLinePaint);
            }
         }
      }
   }

   public void renderGridLines(Canvas var1) {
      if(this.mYAxis.isEnabled()) {
         if(this.mYAxis.isDrawGridLinesEnabled()) {
            int var3 = var1.save();
            var1.clipRect(this.getGridClippingRect());
            float[] var4 = this.getTransformedPositions();
            this.mGridPaint.setColor(this.mYAxis.getGridColor());
            this.mGridPaint.setStrokeWidth(this.mYAxis.getGridLineWidth());
            this.mGridPaint.setPathEffect(this.mYAxis.getGridDashPathEffect());
            Path var5 = this.mRenderGridLinesPath;
            var5.reset();

            for(int var2 = 0; var2 < var4.length; var2 += 2) {
               var1.drawPath(this.linePath(var5, var2, var4), this.mGridPaint);
               var5.reset();
            }

            var1.restoreToCount(var3);
         }

         if(this.mYAxis.isDrawZeroLineEnabled()) {
            this.drawZeroLine(var1);
         }

      }
   }

   public void renderLimitLines(Canvas var1) {
      List var7 = this.mYAxis.getLimitLines();
      if(var7 != null) {
         if(var7.size() > 0) {
            float[] var8 = this.mRenderLimitLinesBuffer;
            int var5 = 0;
            var8[0] = 0.0F;
            var8[1] = 0.0F;
            Path var9 = this.mRenderLimitLines;
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
                     this.mLimitLinePaint.setTypeface(var11.getTypeface());
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
