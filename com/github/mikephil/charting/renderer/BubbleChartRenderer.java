package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BubbleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet;
import com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.Iterator;
import java.util.List;

public class BubbleChartRenderer extends BarLineScatterCandleBubbleRenderer {

   private float[] _hsvBuffer = new float[3];
   protected BubbleDataProvider mChart;
   private float[] pointBuffer = new float[2];
   private float[] sizeBuffer = new float[4];


   public BubbleChartRenderer(BubbleDataProvider var1, ChartAnimator var2, ViewPortHandler var3) {
      super(var2, var3);
      this.mChart = var1;
      this.mRenderPaint.setStyle(Style.FILL);
      this.mHighlightPaint.setStyle(Style.STROKE);
      this.mHighlightPaint.setStrokeWidth(Utils.convertDpToPixel(1.5F));
   }

   public void drawData(Canvas var1) {
      Iterator var2 = this.mChart.getBubbleData().getDataSets().iterator();

      while(var2.hasNext()) {
         IBubbleDataSet var3 = (IBubbleDataSet)var2.next();
         if(var3.isVisible()) {
            this.drawDataSet(var1, var3);
         }
      }

   }

   protected void drawDataSet(Canvas var1, IBubbleDataSet var2) {
      Transformer var9 = this.mChart.getTransformer(var2.getAxisDependency());
      float var3 = this.mAnimator.getPhaseY();
      this.mXBounds.set(this.mChart, var2);
      this.sizeBuffer[0] = 0.0F;
      this.sizeBuffer[2] = 1.0F;
      var9.pointValuesToPixel(this.sizeBuffer);
      boolean var8 = var2.isNormalizeSizeEnabled();
      float var4 = Math.abs(this.sizeBuffer[2] - this.sizeBuffer[0]);
      var4 = Math.min(Math.abs(this.mViewPortHandler.contentBottom() - this.mViewPortHandler.contentTop()), var4);

      for(int var6 = this.mXBounds.min; var6 <= this.mXBounds.range + this.mXBounds.min; ++var6) {
         BubbleEntry var10 = (BubbleEntry)var2.getEntryForIndex(var6);
         this.pointBuffer[0] = var10.getX();
         this.pointBuffer[1] = var10.getY() * var3;
         var9.pointValuesToPixel(this.pointBuffer);
         float var5 = this.getShapeSize(var10.getSize(), var2.getMaxSize(), var4, var8) / 2.0F;
         if(this.mViewPortHandler.isInBoundsTop(this.pointBuffer[1] + var5) && this.mViewPortHandler.isInBoundsBottom(this.pointBuffer[1] - var5) && this.mViewPortHandler.isInBoundsLeft(this.pointBuffer[0] + var5)) {
            if(!this.mViewPortHandler.isInBoundsRight(this.pointBuffer[0] - var5)) {
               return;
            }

            int var7 = var2.getColor((int)var10.getX());
            this.mRenderPaint.setColor(var7);
            var1.drawCircle(this.pointBuffer[0], this.pointBuffer[1], var5, this.mRenderPaint);
         }
      }

   }

   public void drawExtras(Canvas var1) {}

   public void drawHighlighted(Canvas var1, Highlight[] var2) {
      BubbleData var9 = this.mChart.getBubbleData();
      float var3 = this.mAnimator.getPhaseY();
      int var6 = var2.length;

      for(int var5 = 0; var5 < var6; ++var5) {
         Highlight var11 = var2[var5];
         IBubbleDataSet var10 = (IBubbleDataSet)var9.getDataSetByIndex(var11.getDataSetIndex());
         if(var10 != null && var10.isHighlightEnabled()) {
            BubbleEntry var12 = (BubbleEntry)var10.getEntryForXValue(var11.getX(), var11.getY());
            if(var12.getY() == var11.getY() && this.isInBoundsX(var12, var10)) {
               Transformer var13 = this.mChart.getTransformer(var10.getAxisDependency());
               this.sizeBuffer[0] = 0.0F;
               this.sizeBuffer[2] = 1.0F;
               var13.pointValuesToPixel(this.sizeBuffer);
               boolean var8 = var10.isNormalizeSizeEnabled();
               float var4 = Math.abs(this.sizeBuffer[2] - this.sizeBuffer[0]);
               var4 = Math.min(Math.abs(this.mViewPortHandler.contentBottom() - this.mViewPortHandler.contentTop()), var4);
               this.pointBuffer[0] = var12.getX();
               this.pointBuffer[1] = var12.getY() * var3;
               var13.pointValuesToPixel(this.pointBuffer);
               var11.setDraw(this.pointBuffer[0], this.pointBuffer[1]);
               var4 = this.getShapeSize(var12.getSize(), var10.getMaxSize(), var4, var8) / 2.0F;
               if(this.mViewPortHandler.isInBoundsTop(this.pointBuffer[1] + var4) && this.mViewPortHandler.isInBoundsBottom(this.pointBuffer[1] - var4) && this.mViewPortHandler.isInBoundsLeft(this.pointBuffer[0] + var4)) {
                  if(!this.mViewPortHandler.isInBoundsRight(this.pointBuffer[0] - var4)) {
                     return;
                  }

                  int var7 = var10.getColor((int)var12.getX());
                  Color.RGBToHSV(Color.red(var7), Color.green(var7), Color.blue(var7), this._hsvBuffer);
                  float[] var14 = this._hsvBuffer;
                  var14[2] *= 0.5F;
                  var7 = Color.HSVToColor(Color.alpha(var7), this._hsvBuffer);
                  this.mHighlightPaint.setColor(var7);
                  this.mHighlightPaint.setStrokeWidth(var10.getHighlightCircleWidth());
                  var1.drawCircle(this.pointBuffer[0], this.pointBuffer[1], var4, this.mHighlightPaint);
               }
            }
         }
      }

   }

   public void drawValues(Canvas var1) {
      BubbleData var10 = this.mChart.getBubbleData();
      if(var10 != null) {
         if(this.isDrawingValuesAllowed(this.mChart)) {
            List var13 = var10.getDataSets();
            float var4 = (float)Utils.calcTextHeight(this.mValuePaint, "1");

            for(int var6 = 0; var6 < var13.size(); ++var6) {
               IBubbleDataSet var14 = (IBubbleDataSet)var13.get(var6);
               if(this.shouldDrawValues(var14)) {
                  this.applyValueTextStyle(var14);
                  float var2 = Math.max(0.0F, Math.min(1.0F, this.mAnimator.getPhaseX()));
                  float var3 = this.mAnimator.getPhaseY();
                  this.mXBounds.set(this.mChart, var14);
                  float[] var15 = this.mChart.getTransformer(var14.getAxisDependency()).generateTransformedValuesBubble(var14, var3, this.mXBounds.min, this.mXBounds.max);
                  if(var2 == 1.0F) {
                     var2 = var3;
                  }

                  MPPointF var16 = MPPointF.getInstance(var14.getIconsOffset());
                  var16.x = Utils.convertDpToPixel(var16.x);
                  var16.y = Utils.convertDpToPixel(var16.y);

                  for(int var7 = 0; var7 < var15.length; var7 += 2) {
                     int var8 = var7 / 2;
                     int var9 = var14.getValueTextColor(this.mXBounds.min + var8);
                     var9 = Color.argb(Math.round(255.0F * var2), Color.red(var9), Color.green(var9), Color.blue(var9));
                     var3 = var15[var7];
                     float var5 = var15[var7 + 1];
                     if(!this.mViewPortHandler.isInBoundsRight(var3)) {
                        break;
                     }

                     if(this.mViewPortHandler.isInBoundsLeft(var3) && this.mViewPortHandler.isInBoundsY(var5)) {
                        BubbleEntry var12 = (BubbleEntry)var14.getEntryForIndex(var8 + this.mXBounds.min);
                        if(var14.isDrawValuesEnabled()) {
                           this.drawValue(var1, var14.getValueFormatter(), var12.getSize(), var12, var6, var3, var5 + 0.5F * var4, var9);
                        }

                        if(var12.getIcon() != null && var14.isDrawIconsEnabled()) {
                           Drawable var17 = var12.getIcon();
                           Utils.drawImage(var1, var17, (int)(var3 + var16.x), (int)(var5 + var16.y), var17.getIntrinsicWidth(), var17.getIntrinsicHeight());
                        }
                     }
                  }

                  MPPointF.recycleInstance(var16);
               }
            }
         }

      }
   }

   protected float getShapeSize(float var1, float var2, float var3, boolean var4) {
      float var5 = var1;
      if(var4) {
         if(var2 == 0.0F) {
            var5 = 1.0F;
         } else {
            var5 = (float)Math.sqrt((double)(var1 / var2));
         }
      }

      return var3 * var5;
   }

   public void initBuffers() {}
}
