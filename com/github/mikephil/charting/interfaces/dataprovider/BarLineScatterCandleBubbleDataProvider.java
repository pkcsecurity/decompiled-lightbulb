package com.github.mikephil.charting.interfaces.dataprovider;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.interfaces.dataprovider.ChartInterface;
import com.github.mikephil.charting.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

   BarLineScatterCandleBubbleData getData();

   float getHighestVisibleX();

   float getLowestVisibleX();

   Transformer getTransformer(YAxis.AxisDependency var1);

   boolean isInverted(YAxis.AxisDependency var1);
}
