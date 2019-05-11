package com.github.mikephil.charting.components;

import android.graphics.Paint;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.utils.Utils;

public class YAxis extends AxisBase {

   private YAxis.AxisDependency mAxisDependency;
   private boolean mDrawBottomYLabelEntry = true;
   private boolean mDrawTopYLabelEntry = true;
   protected boolean mDrawZeroLine = false;
   protected boolean mInverted = false;
   protected float mMaxWidth;
   protected float mMinWidth;
   private YAxis.YAxisLabelPosition mPosition;
   protected float mSpacePercentBottom = 10.0F;
   protected float mSpacePercentTop = 10.0F;
   protected int mZeroLineColor = -7829368;
   protected float mZeroLineWidth = 1.0F;


   public YAxis() {
      this.mPosition = YAxis.YAxisLabelPosition.OUTSIDE_CHART;
      this.mMinWidth = 0.0F;
      this.mMaxWidth = Float.POSITIVE_INFINITY;
      this.mAxisDependency = YAxis.AxisDependency.LEFT;
      this.mYOffset = 0.0F;
   }

   public YAxis(YAxis.AxisDependency var1) {
      this.mPosition = YAxis.YAxisLabelPosition.OUTSIDE_CHART;
      this.mMinWidth = 0.0F;
      this.mMaxWidth = Float.POSITIVE_INFINITY;
      this.mAxisDependency = var1;
      this.mYOffset = 0.0F;
   }

   public void calculate(float var1, float var2) {
      if(this.mCustomAxisMin) {
         var1 = this.mAxisMinimum;
      }

      if(this.mCustomAxisMax) {
         var2 = this.mAxisMaximum;
      }

      float var5 = Math.abs(var2 - var1);
      float var4 = var1;
      float var3 = var2;
      if(var5 == 0.0F) {
         var3 = var2 + 1.0F;
         var4 = var1 - 1.0F;
      }

      if(!this.mCustomAxisMin) {
         this.mAxisMinimum = var4 - var5 / 100.0F * this.getSpaceBottom();
      }

      if(!this.mCustomAxisMax) {
         this.mAxisMaximum = var3 + var5 / 100.0F * this.getSpaceTop();
      }

      this.mAxisRange = Math.abs(this.mAxisMaximum - this.mAxisMinimum);
   }

   public YAxis.AxisDependency getAxisDependency() {
      return this.mAxisDependency;
   }

   public YAxis.YAxisLabelPosition getLabelPosition() {
      return this.mPosition;
   }

   public float getMaxWidth() {
      return this.mMaxWidth;
   }

   public float getMinWidth() {
      return this.mMinWidth;
   }

   public float getRequiredHeightSpace(Paint var1) {
      var1.setTextSize(this.mTextSize);
      return (float)Utils.calcTextHeight(var1, this.getLongestLabel()) + this.getYOffset() * 2.0F;
   }

   public float getRequiredWidthSpace(Paint var1) {
      var1.setTextSize(this.mTextSize);
      float var4 = (float)Utils.calcTextWidth(var1, this.getLongestLabel()) + this.getXOffset() * 2.0F;
      float var2 = this.getMinWidth();
      float var5 = this.getMaxWidth();
      float var3 = var2;
      if(var2 > 0.0F) {
         var3 = Utils.convertDpToPixel(var2);
      }

      var2 = var5;
      if(var5 > 0.0F) {
         var2 = var5;
         if(var5 != Float.POSITIVE_INFINITY) {
            var2 = Utils.convertDpToPixel(var5);
         }
      }

      if((double)var2 <= 0.0D) {
         var2 = var4;
      }

      return Math.max(var3, Math.min(var4, var2));
   }

   public float getSpaceBottom() {
      return this.mSpacePercentBottom;
   }

   public float getSpaceTop() {
      return this.mSpacePercentTop;
   }

   public int getZeroLineColor() {
      return this.mZeroLineColor;
   }

   public float getZeroLineWidth() {
      return this.mZeroLineWidth;
   }

   public boolean isDrawBottomYLabelEntryEnabled() {
      return this.mDrawBottomYLabelEntry;
   }

   public boolean isDrawTopYLabelEntryEnabled() {
      return this.mDrawTopYLabelEntry;
   }

   public boolean isDrawZeroLineEnabled() {
      return this.mDrawZeroLine;
   }

   public boolean isInverted() {
      return this.mInverted;
   }

   public boolean needsOffset() {
      return this.isEnabled() && this.isDrawLabelsEnabled() && this.getLabelPosition() == YAxis.YAxisLabelPosition.OUTSIDE_CHART;
   }

   public void setDrawTopYLabelEntry(boolean var1) {
      this.mDrawTopYLabelEntry = var1;
   }

   public void setDrawZeroLine(boolean var1) {
      this.mDrawZeroLine = var1;
   }

   public void setInverted(boolean var1) {
      this.mInverted = var1;
   }

   public void setMaxWidth(float var1) {
      this.mMaxWidth = var1;
   }

   public void setMinWidth(float var1) {
      this.mMinWidth = var1;
   }

   public void setPosition(YAxis.YAxisLabelPosition var1) {
      this.mPosition = var1;
   }

   public void setSpaceBottom(float var1) {
      this.mSpacePercentBottom = var1;
   }

   public void setSpaceTop(float var1) {
      this.mSpacePercentTop = var1;
   }

   @Deprecated
   public void setStartAtZero(boolean var1) {
      if(var1) {
         this.setAxisMinimum(0.0F);
      } else {
         this.resetAxisMinimum();
      }
   }

   public void setZeroLineColor(int var1) {
      this.mZeroLineColor = var1;
   }

   public void setZeroLineWidth(float var1) {
      this.mZeroLineWidth = Utils.convertDpToPixel(var1);
   }

   public static enum AxisDependency {

      // $FF: synthetic field
      private static final YAxis.AxisDependency[] $VALUES = new YAxis.AxisDependency[]{LEFT, RIGHT};
      LEFT("LEFT", 0),
      RIGHT("RIGHT", 1);


      private AxisDependency(String var1, int var2) {}
   }

   public static enum YAxisLabelPosition {

      // $FF: synthetic field
      private static final YAxis.YAxisLabelPosition[] $VALUES = new YAxis.YAxisLabelPosition[]{OUTSIDE_CHART, INSIDE_CHART};
      INSIDE_CHART("INSIDE_CHART", 1),
      OUTSIDE_CHART("OUTSIDE_CHART", 0);


      private YAxisLabelPosition(String var1, int var2) {}
   }
}
