package com.facebook.react.uimanager;

import android.util.TypedValue;
import com.facebook.react.uimanager.DisplayMetricsHolder;

public class PixelUtil {

   public static float toDIPFromPixel(float var0) {
      return var0 / DisplayMetricsHolder.getWindowDisplayMetrics().density;
   }

   public static float toPixelFromDIP(double var0) {
      return toPixelFromDIP((float)var0);
   }

   public static float toPixelFromDIP(float var0) {
      return TypedValue.applyDimension(1, var0, DisplayMetricsHolder.getWindowDisplayMetrics());
   }

   public static float toPixelFromSP(double var0) {
      return toPixelFromSP((float)var0);
   }

   public static float toPixelFromSP(float var0) {
      return TypedValue.applyDimension(2, var0, DisplayMetricsHolder.getWindowDisplayMetrics());
   }
}
