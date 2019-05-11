package com.facebook.react;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import java.util.List;

public interface ReactPackage {

   List<NativeModule> createNativeModules(ReactApplicationContext var1);

   List<ViewManager> createViewManagers(ReactApplicationContext var1);
}
