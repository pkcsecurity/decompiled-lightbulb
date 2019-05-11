package com.facebook.yoga;

import com.facebook.yoga.YogaUnit;
import com.facebook.yoga.YogaValue;

public class YogaConstants {

   public static final float UNDEFINED = Float.NaN;


   public static boolean isUndefined(float var0) {
      return Float.compare(var0, Float.NaN) == 0;
   }

   public static boolean isUndefined(YogaValue var0) {
      return var0.unit == YogaUnit.UNDEFINED;
   }
}
