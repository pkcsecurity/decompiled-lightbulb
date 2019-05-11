package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.renderer.BubbleChartRenderer;
import com.github.mikephil.charting.renderer.CandleStickChartRenderer;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.renderer.LineChartRenderer;
import com.github.mikephil.charting.renderer.ScatterChartRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CombinedChartRenderer extends DataRenderer {

   protected WeakReference<Chart> mChart;
   protected List<Highlight> mHighlightBuffer = new ArrayList();
   protected List<DataRenderer> mRenderers = new ArrayList(5);


   public CombinedChartRenderer(CombinedChart var1, ChartAnimator var2, ViewPortHandler var3) {
      super(var2, var3);
      this.mChart = new WeakReference(var1);
      this.createRenderers();
   }

   public void createRenderers() {
      this.mRenderers.clear();
      CombinedChart var3 = (CombinedChart)this.mChart.get();
      if(var3 != null) {
         CombinedChart.DrawOrder[] var4 = var3.getDrawOrder();
         int var2 = var4.length;

         for(int var1 = 0; var1 < var2; ++var1) {
            CombinedChart.DrawOrder var5 = var4[var1];
            switch(null.$SwitchMap$com$github$mikephil$charting$charts$CombinedChart$DrawOrder[var5.ordinal()]) {
            case 1:
               if(var3.getBarData() != null) {
                  this.mRenderers.add(new BarChartRenderer(var3, this.mAnimator, this.mViewPortHandler));
               }
               break;
            case 2:
               if(var3.getBubbleData() != null) {
                  this.mRenderers.add(new BubbleChartRenderer(var3, this.mAnimator, this.mViewPortHandler));
               }
               break;
            case 3:
               if(var3.getLineData() != null) {
                  this.mRenderers.add(new LineChartRenderer(var3, this.mAnimator, this.mViewPortHandler));
               }
               break;
            case 4:
               if(var3.getCandleData() != null) {
                  this.mRenderers.add(new CandleStickChartRenderer(var3, this.mAnimator, this.mViewPortHandler));
               }
               break;
            case 5:
               if(var3.getScatterData() != null) {
                  this.mRenderers.add(new ScatterChartRenderer(var3, this.mAnimator, this.mViewPortHandler));
               }
            }
         }

      }
   }

   public void drawData(Canvas var1) {
      Iterator var2 = this.mRenderers.iterator();

      while(var2.hasNext()) {
         ((DataRenderer)var2.next()).drawData(var1);
      }

   }

   public void drawExtras(Canvas var1) {
      Iterator var2 = this.mRenderers.iterator();

      while(var2.hasNext()) {
         ((DataRenderer)var2.next()).drawExtras(var1);
      }

   }

   public void drawHighlighted(Canvas var1, Highlight[] var2) {
      Chart var7 = (Chart)this.mChart.get();
      if(var7 != null) {
         Iterator var8 = this.mRenderers.iterator();

         while(var8.hasNext()) {
            DataRenderer var9 = (DataRenderer)var8.next();
            Object var6 = null;
            if(var9 instanceof BarChartRenderer) {
               var6 = ((BarChartRenderer)var9).mChart.getBarData();
            } else if(var9 instanceof LineChartRenderer) {
               var6 = ((LineChartRenderer)var9).mChart.getLineData();
            } else if(var9 instanceof CandleStickChartRenderer) {
               var6 = ((CandleStickChartRenderer)var9).mChart.getCandleData();
            } else if(var9 instanceof ScatterChartRenderer) {
               var6 = ((ScatterChartRenderer)var9).mChart.getScatterData();
            } else if(var9 instanceof BubbleChartRenderer) {
               var6 = ((BubbleChartRenderer)var9).mChart.getBubbleData();
            }

            int var3;
            if(var6 == null) {
               var3 = -1;
            } else {
               var3 = ((CombinedData)var7.getData()).getAllData().indexOf(var6);
            }

            this.mHighlightBuffer.clear();
            int var5 = var2.length;

            for(int var4 = 0; var4 < var5; ++var4) {
               Highlight var10 = var2[var4];
               if(var10.getDataIndex() == var3 || var10.getDataIndex() == -1) {
                  this.mHighlightBuffer.add(var10);
               }
            }

            var9.drawHighlighted(var1, (Highlight[])this.mHighlightBuffer.toArray(new Highlight[this.mHighlightBuffer.size()]));
         }

      }
   }

   public void drawValues(Canvas var1) {
      Iterator var2 = this.mRenderers.iterator();

      while(var2.hasNext()) {
         ((DataRenderer)var2.next()).drawValues(var1);
      }

   }

   public DataRenderer getSubRenderer(int var1) {
      return var1 < this.mRenderers.size() && var1 >= 0?(DataRenderer)this.mRenderers.get(var1):null;
   }

   public List<DataRenderer> getSubRenderers() {
      return this.mRenderers;
   }

   public void initBuffers() {
      Iterator var1 = this.mRenderers.iterator();

      while(var1.hasNext()) {
         ((DataRenderer)var1.next()).initBuffers();
      }

   }

   public void setSubRenderers(List<DataRenderer> var1) {
      this.mRenderers = var1;
   }
}
