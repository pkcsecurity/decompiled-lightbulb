package com.github.mikephil.charting.interfaces.datasets;

import android.graphics.DashPathEffect;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineRadarDataSet;

public interface ILineDataSet extends ILineRadarDataSet<Entry> {

   int getCircleColor(int var1);

   int getCircleColorCount();

   int getCircleHoleColor();

   float getCircleHoleRadius();

   float getCircleRadius();

   float getCubicIntensity();

   DashPathEffect getDashPathEffect();

   IFillFormatter getFillFormatter();

   LineDataSet.Mode getMode();

   boolean isDashedLineEnabled();

   boolean isDrawCircleHoleEnabled();

   boolean isDrawCirclesEnabled();

   @Deprecated
   boolean isDrawCubicEnabled();

   @Deprecated
   boolean isDrawSteppedEnabled();
}
