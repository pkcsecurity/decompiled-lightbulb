package com.facebook.react.views.view;

import android.view.View.MeasureSpec;
import com.facebook.yoga.YogaMeasureMode;

public class MeasureUtil {

   public static int getMeasureSpec(float var0, YogaMeasureMode var1) {
      return var1 == YogaMeasureMode.EXACTLY?MeasureSpec.makeMeasureSpec((int)var0, 1073741824):(var1 == YogaMeasureMode.AT_MOST?MeasureSpec.makeMeasureSpec((int)var0, Integer.MIN_VALUE):MeasureSpec.makeMeasureSpec(0, 0));
   }
}
