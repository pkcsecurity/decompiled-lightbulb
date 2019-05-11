package com.facebook.react.modules.appregistry;

import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.WritableMap;

public interface AppRegistry extends JavaScriptModule {

   void runApplication(String var1, WritableMap var2);

   void startHeadlessTask(int var1, String var2, WritableMap var3);

   void unmountApplicationComponentAtRootTag(int var1);
}
