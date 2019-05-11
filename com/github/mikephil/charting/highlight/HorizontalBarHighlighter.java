package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.BarHighlighter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.MPPointD;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HorizontalBarHighlighter extends BarHighlighter {

   public HorizontalBarHighlighter(BarDataProvider var1) {
      super(var1);
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
            MPPointD var11 = ((BarDataProvider)this.mChart).getTransformer(var1.getAxisDependency()).getPixelForValues(var10.getY(), var10.getX());
            var7.add(new Highlight(var10.getX(), var10.getY(), (float)var11.x, (float)var11.y, var2, var1.getAxisDependency()));
         }

         return var7;
      }
   }

   protected float getDistance(float var1, float var2, float var3, float var4) {
      return Math.abs(var2 - var4);
   }

   public Highlight getHighlight(float var1, float var2) {
      BarData var5 = ((BarDataProvider)this.mChart).getBarData();
      MPPointD var3 = this.getValsForTouch(var2, var1);
      Highlight var4 = this.getHighlightForX((float)var3.y, var2, var1);
      if(var4 == null) {
         return null;
      } else {
         IBarDataSet var6 = (IBarDataSet)var5.getDataSetByIndex(var4.getDataSetIndex());
         if(var6.isStacked()) {
            return this.getStackedHighlight(var4, var6, (float)var3.y, (float)var3.x);
         } else {
            MPPointD.recycleInstance(var3);
            return var4;
         }
      }
   }
}
