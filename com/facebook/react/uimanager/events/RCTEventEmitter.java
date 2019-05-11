package com.facebook.react.uimanager.events;

import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import javax.annotation.Nullable;

public interface RCTEventEmitter extends JavaScriptModule {

   void receiveEvent(int var1, String var2, @Nullable WritableMap var3);

   void receiveTouches(String var1, WritableArray var2, WritableArray var3);
}
