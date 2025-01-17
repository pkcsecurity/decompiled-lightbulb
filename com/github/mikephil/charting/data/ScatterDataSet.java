package com.github.mikephil.charting.data;

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineScatterCandleRadarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.renderer.scatter.ChevronDownShapeRenderer;
import com.github.mikephil.charting.renderer.scatter.ChevronUpShapeRenderer;
import com.github.mikephil.charting.renderer.scatter.CircleShapeRenderer;
import com.github.mikephil.charting.renderer.scatter.CrossShapeRenderer;
import com.github.mikephil.charting.renderer.scatter.IShapeRenderer;
import com.github.mikephil.charting.renderer.scatter.SquareShapeRenderer;
import com.github.mikephil.charting.renderer.scatter.TriangleShapeRenderer;
import com.github.mikephil.charting.renderer.scatter.XShapeRenderer;
import java.util.ArrayList;
import java.util.List;

public class ScatterDataSet extends LineScatterCandleRadarDataSet<Entry> implements IScatterDataSet {

   private int mScatterShapeHoleColor = 1122867;
   private float mScatterShapeHoleRadius = 0.0F;
   protected IShapeRenderer mShapeRenderer = new SquareShapeRenderer();
   private float mShapeSize = 15.0F;


   public ScatterDataSet(List<Entry> var1, String var2) {
      super(var1, var2);
   }

   public static IShapeRenderer getRendererForShape(ScatterChart.ScatterShape var0) {
      switch(null.$SwitchMap$com$github$mikephil$charting$charts$ScatterChart$ScatterShape[var0.ordinal()]) {
      case 1:
         return new SquareShapeRenderer();
      case 2:
         return new CircleShapeRenderer();
      case 3:
         return new TriangleShapeRenderer();
      case 4:
         return new CrossShapeRenderer();
      case 5:
         return new XShapeRenderer();
      case 6:
         return new ChevronUpShapeRenderer();
      case 7:
         return new ChevronDownShapeRenderer();
      default:
         return null;
      }
   }

   public DataSet<Entry> copy() {
      ArrayList var2 = new ArrayList();

      for(int var1 = 0; var1 < this.mValues.size(); ++var1) {
         var2.add(((Entry)this.mValues.get(var1)).copy());
      }

      ScatterDataSet var3 = new ScatterDataSet(var2, this.getLabel());
      var3.mDrawValues = this.mDrawValues;
      var3.mValueColors = this.mValueColors;
      var3.mColors = this.mColors;
      var3.mShapeSize = this.mShapeSize;
      var3.mShapeRenderer = this.mShapeRenderer;
      var3.mScatterShapeHoleRadius = this.mScatterShapeHoleRadius;
      var3.mScatterShapeHoleColor = this.mScatterShapeHoleColor;
      var3.mHighlightLineWidth = this.mHighlightLineWidth;
      var3.mHighLightColor = this.mHighLightColor;
      var3.mHighlightDashPathEffect = this.mHighlightDashPathEffect;
      return var3;
   }

   public int getScatterShapeHoleColor() {
      return this.mScatterShapeHoleColor;
   }

   public float getScatterShapeHoleRadius() {
      return this.mScatterShapeHoleRadius;
   }

   public float getScatterShapeSize() {
      return this.mShapeSize;
   }

   public IShapeRenderer getShapeRenderer() {
      return this.mShapeRenderer;
   }

   public void setScatterShape(ScatterChart.ScatterShape var1) {
      this.mShapeRenderer = getRendererForShape(var1);
   }

   public void setScatterShapeHoleColor(int var1) {
      this.mScatterShapeHoleColor = var1;
   }

   public void setScatterShapeHoleRadius(float var1) {
      this.mScatterShapeHoleRadius = var1;
   }

   public void setScatterShapeSize(float var1) {
      this.mShapeSize = var1;
   }

   public void setShapeRenderer(IShapeRenderer var1) {
      this.mShapeRenderer = var1;
   }
}
