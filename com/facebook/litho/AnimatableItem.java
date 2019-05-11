package com.facebook.litho;

import android.graphics.Rect;

public interface AnimatableItem {

   float getAlpha();

   Rect getBounds();

   float getRotation();

   float getScale();

   boolean isAlphaSet();

   boolean isRotationSet();

   boolean isScaleSet();
}
