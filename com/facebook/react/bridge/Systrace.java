package com.facebook.react.bridge;

import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.JavaScriptModule;

@DoNotStrip
public interface Systrace extends JavaScriptModule {

   @DoNotStrip
   void setEnabled(boolean var1);
}
