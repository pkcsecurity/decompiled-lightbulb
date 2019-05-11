package com.github.mikephil.charting.charts;

import android.content.Context;
import android.util.AttributeSet;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.interfaces.dataprovider.ScatterDataProvider;
import com.github.mikephil.charting.renderer.ScatterChartRenderer;

public class ScatterChart extends BarLineChartBase<ScatterData> implements ScatterDataProvider {

   public ScatterChart(Context var1) {
      super(var1);
   }

   public ScatterChart(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public ScatterChart(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   public ScatterData getScatterData() {
      return (ScatterData)this.mData;
   }

   protected void init() {
      super.init();
      this.mRenderer = new ScatterChartRenderer(this, this.mAnimator, this.mViewPortHandler);
      this.getXAxis().setSpaceMin(0.5F);
      this.getXAxis().setSpaceMax(0.5F);
   }

   public static enum ScatterShape {

      // $FF: synthetic field
      private static final ScatterChart.ScatterShape[] $VALUES = new ScatterChart.ScatterShape[]{SQUARE, CIRCLE, TRIANGLE, CROSS, X, CHEVRON_UP, CHEVRON_DOWN};
      CHEVRON_DOWN("CHEVRON_DOWN", 6, "CHEVRON_DOWN"),
      CHEVRON_UP("CHEVRON_UP", 5, "CHEVRON_UP"),
      CIRCLE("CIRCLE", 1, "CIRCLE"),
      CROSS("CROSS", 3, "CROSS"),
      SQUARE("SQUARE", 0, "SQUARE"),
      TRIANGLE("TRIANGLE", 2, "TRIANGLE"),
      X("X", 4, "X");
      private final String shapeIdentifier;


      private ScatterShape(String var1, int var2, String var3) {
         this.shapeIdentifier = var3;
      }

      public static ScatterChart.ScatterShape[] getAllDefaultShapes() {
         return new ScatterChart.ScatterShape[]{SQUARE, CIRCLE, TRIANGLE, CROSS, X, CHEVRON_UP, CHEVRON_DOWN};
      }

      public String toString() {
         return this.shapeIdentifier;
      }
   }
}
