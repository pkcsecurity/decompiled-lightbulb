package com.github.mikephil.charting.interfaces.datasets;

import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;

public interface IBubbleDataSet extends IBarLineScatterCandleBubbleDataSet<BubbleEntry> {

   float getHighlightCircleWidth();

   float getMaxSize();

   boolean isNormalizeSizeEnabled();

   void setHighlightCircleWidth(float var1);
}
