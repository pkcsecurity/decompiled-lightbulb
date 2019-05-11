package com.github.mikephil.charting.interfaces.datasets;

import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineRadarDataSet;

public interface IRadarDataSet extends ILineRadarDataSet<RadarEntry> {

   int getHighlightCircleFillColor();

   float getHighlightCircleInnerRadius();

   float getHighlightCircleOuterRadius();

   int getHighlightCircleStrokeAlpha();

   int getHighlightCircleStrokeColor();

   float getHighlightCircleStrokeWidth();

   boolean isDrawHighlightCircleEnabled();

   void setDrawHighlightCircleEnabled(boolean var1);
}
