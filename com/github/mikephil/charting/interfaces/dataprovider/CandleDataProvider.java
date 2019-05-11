package com.github.mikephil.charting.interfaces.dataprovider;

import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

   CandleData getCandleData();
}
