package com.github.mikephil.charting.interfaces.dataprovider;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;

public interface BarDataProvider extends BarLineScatterCandleBubbleDataProvider {

   BarData getBarData();

   boolean isDrawBarShadowEnabled();

   boolean isDrawValueAboveBarEnabled();

   boolean isHighlightFullBarEnabled();
}
