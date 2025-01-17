package com.github.mikephil.charting.interfaces.dataprovider;

import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;

public interface ScatterDataProvider extends BarLineScatterCandleBubbleDataProvider {

   ScatterData getScatterData();
}
