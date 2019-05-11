package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaNode;

@DoNotStrip
public interface YogaMeasureFunction {

   @DoNotStrip
   long measure(YogaNode var1, float var2, YogaMeasureMode var3, float var4, YogaMeasureMode var5);
}
