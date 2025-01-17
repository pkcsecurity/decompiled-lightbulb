package com.github.mikephil.charting.data;

import android.graphics.Color;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import java.util.List;

public abstract class BarLineScatterCandleBubbleDataSet<T extends Entry> extends DataSet<T> implements IBarLineScatterCandleBubbleDataSet<T> {

   protected int mHighLightColor = Color.rgb(255, 187, 115);


   public BarLineScatterCandleBubbleDataSet(List<T> var1, String var2) {
      super(var1, var2);
   }

   public int getHighLightColor() {
      return this.mHighLightColor;
   }

   public void setHighLightColor(int var1) {
      this.mHighLightColor = var1;
   }
}
