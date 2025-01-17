package com.facebook.react.modules.core;

import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.WritableArray;

public interface JSTimers extends JavaScriptModule {

   void callIdleCallbacks(double var1);

   void callTimers(WritableArray var1);

   void emitTimeDriftWarning(String var1);
}
