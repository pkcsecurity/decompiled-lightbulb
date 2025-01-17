package com.github.mikephil.charting.interfaces.datasets;

import android.graphics.DashPathEffect;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;

public interface ILineScatterCandleRadarDataSet<T extends Entry> extends IBarLineScatterCandleBubbleDataSet<T> {

   DashPathEffect getDashPathEffectHighlight();

   float getHighlightLineWidth();

   boolean isHorizontalHighlightIndicatorEnabled();

   boolean isVerticalHighlightIndicatorEnabled();
}
