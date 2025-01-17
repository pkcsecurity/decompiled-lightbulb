package com.github.mikephil.charting.data;

import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.LineRadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import java.util.ArrayList;
import java.util.List;

public class RadarDataSet extends LineRadarDataSet<RadarEntry> implements IRadarDataSet {

   protected boolean mDrawHighlightCircleEnabled = false;
   protected int mHighlightCircleFillColor = -1;
   protected float mHighlightCircleInnerRadius = 3.0F;
   protected float mHighlightCircleOuterRadius = 4.0F;
   protected int mHighlightCircleStrokeAlpha = 76;
   protected int mHighlightCircleStrokeColor = 1122867;
   protected float mHighlightCircleStrokeWidth = 2.0F;


   public RadarDataSet(List<RadarEntry> var1, String var2) {
      super(var1, var2);
   }

   public DataSet<RadarEntry> copy() {
      ArrayList var2 = new ArrayList();

      for(int var1 = 0; var1 < this.mValues.size(); ++var1) {
         var2.add(((RadarEntry)this.mValues.get(var1)).copy());
      }

      RadarDataSet var3 = new RadarDataSet(var2, this.getLabel());
      var3.mColors = this.mColors;
      var3.mHighLightColor = this.mHighLightColor;
      return var3;
   }

   public int getHighlightCircleFillColor() {
      return this.mHighlightCircleFillColor;
   }

   public float getHighlightCircleInnerRadius() {
      return this.mHighlightCircleInnerRadius;
   }

   public float getHighlightCircleOuterRadius() {
      return this.mHighlightCircleOuterRadius;
   }

   public int getHighlightCircleStrokeAlpha() {
      return this.mHighlightCircleStrokeAlpha;
   }

   public int getHighlightCircleStrokeColor() {
      return this.mHighlightCircleStrokeColor;
   }

   public float getHighlightCircleStrokeWidth() {
      return this.mHighlightCircleStrokeWidth;
   }

   public boolean isDrawHighlightCircleEnabled() {
      return this.mDrawHighlightCircleEnabled;
   }

   public void setDrawHighlightCircleEnabled(boolean var1) {
      this.mDrawHighlightCircleEnabled = var1;
   }

   public void setHighlightCircleFillColor(int var1) {
      this.mHighlightCircleFillColor = var1;
   }

   public void setHighlightCircleInnerRadius(float var1) {
      this.mHighlightCircleInnerRadius = var1;
   }

   public void setHighlightCircleOuterRadius(float var1) {
      this.mHighlightCircleOuterRadius = var1;
   }

   public void setHighlightCircleStrokeAlpha(int var1) {
      this.mHighlightCircleStrokeAlpha = var1;
   }

   public void setHighlightCircleStrokeColor(int var1) {
      this.mHighlightCircleStrokeColor = var1;
   }

   public void setHighlightCircleStrokeWidth(float var1) {
      this.mHighlightCircleStrokeWidth = var1;
   }
}
