package com.facebook.react.devsupport;

import com.facebook.react.bridge.JavaJSExecutor;

public interface ReactInstanceDevCommandsHandler {

   void onJSBundleLoadedFromServer();

   void onReloadWithJSDebugger(JavaJSExecutor.Factory var1);

   void toggleElementInspector();
}
