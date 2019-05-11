package com.github.mikephil.charting.data;

import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import java.util.Iterator;
import java.util.List;

public class ScatterData extends BarLineScatterCandleBubbleData<IScatterDataSet> {

   public ScatterData() {}

   public ScatterData(List<IScatterDataSet> var1) {
      super(var1);
   }

   public ScatterData(IScatterDataSet ... var1) {
      super((IBarLineScatterCandleBubbleDataSet[])var1);
   }

   public float getGreatestShapeSize() {
      Iterator var3 = this.mDataSets.iterator();
      float var1 = 0.0F;

      while(var3.hasNext()) {
         float var2 = ((IScatterDataSet)var3.next()).getScatterShapeSize();
         if(var2 > var1) {
            var1 = var2;
         }
      }

      return var1;
   }
}
