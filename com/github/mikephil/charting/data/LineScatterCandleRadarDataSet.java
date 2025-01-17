package com.github.mikephil.charting.data;

import android.graphics.DashPathEffect;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.ILineScatterCandleRadarDataSet;
import com.github.mikephil.charting.utils.Utils;
import java.util.List;

public abstract class LineScatterCandleRadarDataSet<T extends Entry> extends BarLineScatterCandleBubbleDataSet<T> implements ILineScatterCandleRadarDataSet<T> {

   protected boolean mDrawHorizontalHighlightIndicator = true;
   protected boolean mDrawVerticalHighlightIndicator = true;
   protected DashPathEffect mHighlightDashPathEffect = null;
   protected float mHighlightLineWidth = 0.5F;


   public LineScatterCandleRadarDataSet(List<T> var1, String var2) {
      super(var1, var2);
      this.mHighlightLineWidth = Utils.convertDpToPixel(0.5F);
   }

   public void disableDashedHighlightLine() {
      this.mHighlightDashPathEffect = null;
   }

   public void enableDashedHighlightLine(float var1, float var2, float var3) {
      this.mHighlightDashPathEffect = new DashPathEffect(new float[]{var1, var2}, var3);
   }

   public DashPathEffect getDashPathEffectHighlight() {
      return this.mHighlightDashPathEffect;
   }

   public float getHighlightLineWidth() {
      return this.mHighlightLineWidth;
   }

   public boolean isDashedHighlightLineEnabled() {
      return this.mHighlightDashPathEffect != null;
   }

   public boolean isHorizontalHighlightIndicatorEnabled() {
      return this.mDrawHorizontalHighlightIndicator;
   }

   public boolean isVerticalHighlightIndicatorEnabled() {
      return this.mDrawVerticalHighlightIndicator;
   }

   public void setDrawHighlightIndicators(boolean var1) {
      this.setDrawVerticalHighlightIndicator(var1);
      this.setDrawHorizontalHighlightIndicator(var1);
   }

   public void setDrawHorizontalHighlightIndicator(boolean var1) {
      this.mDrawHorizontalHighlightIndicator = var1;
   }

   public void setDrawVerticalHighlightIndicator(boolean var1) {
      this.mDrawVerticalHighlightIndicator = var1;
   }

   public void setHighlightLineWidth(float var1) {
      this.mHighlightLineWidth = Utils.convertDpToPixel(var1);
   }
}
