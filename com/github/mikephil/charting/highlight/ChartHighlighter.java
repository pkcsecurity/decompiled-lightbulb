package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.IHighlighter;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.MPPointD;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChartHighlighter<T extends Object & BarLineScatterCandleBubbleDataProvider> implements IHighlighter {

   protected T mChart;
   protected List<Highlight> mHighlightBuffer = new ArrayList();


   public ChartHighlighter(T var1) {
      this.mChart = var1;
   }

   protected List<Highlight> buildHighlights(IDataSet var1, int var2, float var3, DataSet.Rounding var4) {
      ArrayList var7 = new ArrayList();
      List var6 = var1.getEntriesForXValue(var3);
      List var5 = var6;
      if(var6.size() == 0) {
         Entry var8 = var1.getEntryForXValue(var3, Float.NaN, var4);
         var5 = var6;
         if(var8 != null) {
            var5 = var1.getEntriesForXValue(var8.getX());
         }
      }

      if(var5.size() == 0) {
         return var7;
      } else {
         Iterator var9 = var5.iterator();

         while(var9.hasNext()) {
            Entry var10 = (Entry)var9.next();
            MPPointD var11 = this.mChart.getTransformer(var1.getAxisDependency()).getPixelForValues(var10.getX(), var10.getY());
            var7.add(new Highlight(var10.getX(), var10.getY(), (float)var11.x, (float)var11.y, var2, var1.getAxisDependency()));
         }

         return var7;
      }
   }

   public Highlight getClosestHighlightByPixel(List<Highlight> var1, float var2, float var3, YAxis.AxisDependency var4, float var5) {
      Highlight var9 = null;

      float var6;
      for(int var8 = 0; var8 < var1.size(); var5 = var6) {
         Highlight var10;
         label18: {
            Highlight var11 = (Highlight)var1.get(var8);
            if(var4 != null) {
               var10 = var9;
               var6 = var5;
               if(var11.getAxis() != var4) {
                  break label18;
               }
            }

            float var7 = this.getDistance(var2, var3, var11.getXPx(), var11.getYPx());
            var10 = var9;
            var6 = var5;
            if(var7 < var5) {
               var10 = var11;
               var6 = var7;
            }
         }

         ++var8;
         var9 = var10;
      }

      return var9;
   }

   protected BarLineScatterCandleBubbleData getData() {
      return this.mChart.getData();
   }

   protected float getDistance(float var1, float var2, float var3, float var4) {
      return (float)Math.hypot((double)(var1 - var3), (double)(var2 - var4));
   }

   public Highlight getHighlight(float var1, float var2) {
      MPPointD var4 = this.getValsForTouch(var1, var2);
      float var3 = (float)var4.x;
      MPPointD.recycleInstance(var4);
      return this.getHighlightForX(var3, var1, var2);
   }

   protected Highlight getHighlightForX(float var1, float var2, float var3) {
      List var5 = this.getHighlightsAtXValue(var1, var2, var3);
      if(var5.isEmpty()) {
         return null;
      } else {
         YAxis.AxisDependency var4;
         if(this.getMinimumDistance(var5, var3, YAxis.AxisDependency.LEFT) < this.getMinimumDistance(var5, var3, YAxis.AxisDependency.RIGHT)) {
            var4 = YAxis.AxisDependency.LEFT;
         } else {
            var4 = YAxis.AxisDependency.RIGHT;
         }

         return this.getClosestHighlightByPixel(var5, var2, var3, var4, this.mChart.getMaxHighlightDistance());
      }
   }

   protected float getHighlightPos(Highlight var1) {
      return var1.getYPx();
   }

   protected List<Highlight> getHighlightsAtXValue(float var1, float var2, float var3) {
      this.mHighlightBuffer.clear();
      BarLineScatterCandleBubbleData var6 = this.getData();
      if(var6 == null) {
         return this.mHighlightBuffer;
      } else {
         int var4 = 0;

         for(int var5 = var6.getDataSetCount(); var4 < var5; ++var4) {
            IDataSet var7 = var6.getDataSetByIndex(var4);
            if(var7.isHighlightEnabled()) {
               this.mHighlightBuffer.addAll(this.buildHighlights(var7, var4, var1, DataSet.Rounding.CLOSEST));
            }
         }

         return this.mHighlightBuffer;
      }
   }

   protected float getMinimumDistance(List<Highlight> var1, float var2, YAxis.AxisDependency var3) {
      float var4 = Float.MAX_VALUE;

      float var5;
      for(int var7 = 0; var7 < var1.size(); var4 = var5) {
         Highlight var8 = (Highlight)var1.get(var7);
         var5 = var4;
         if(var8.getAxis() == var3) {
            float var6 = Math.abs(this.getHighlightPos(var8) - var2);
            var5 = var4;
            if(var6 < var4) {
               var5 = var6;
            }
         }

         ++var7;
      }

      return var4;
   }

   protected MPPointD getValsForTouch(float var1, float var2) {
      return this.mChart.getTransformer(YAxis.AxisDependency.LEFT).getValuesByTouchPoint(var1, var2);
   }
}
