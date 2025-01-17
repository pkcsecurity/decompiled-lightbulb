package com.github.mikephil.charting.data;

import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import java.util.Arrays;
import java.util.List;

public class RadarData extends ChartData<IRadarDataSet> {

   private List<String> mLabels;


   public RadarData() {}

   public RadarData(List<IRadarDataSet> var1) {
      super(var1);
   }

   public RadarData(IRadarDataSet ... var1) {
      super((IDataSet[])var1);
   }

   public Entry getEntryForHighlight(Highlight var1) {
      return ((IRadarDataSet)this.getDataSetByIndex(var1.getDataSetIndex())).getEntryForIndex((int)var1.getX());
   }

   public List<String> getLabels() {
      return this.mLabels;
   }

   public void setLabels(List<String> var1) {
      this.mLabels = var1;
   }

   public void setLabels(String ... var1) {
      this.mLabels = Arrays.asList(var1);
   }
}
