package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.yoga.YogaLogLevel;
import com.facebook.yoga.YogaNode;

@DoNotStrip
public interface YogaLogger {

   @DoNotStrip
   void log(YogaNode var1, YogaLogLevel var2, String var3);
}
