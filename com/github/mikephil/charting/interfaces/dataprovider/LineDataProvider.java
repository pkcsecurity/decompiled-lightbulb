package com.github.mikephil.charting.interfaces.dataprovider;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

   YAxis getAxis(YAxis.AxisDependency var1);

   LineData getLineData();
}
