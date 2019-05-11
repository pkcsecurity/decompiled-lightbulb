package com.facebook.react;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import java.util.List;
import javax.annotation.Nullable;

public interface ViewManagerOnDemandReactPackage {

   @Nullable
   ViewManager createViewManager(ReactApplicationContext var1, String var2, boolean var3);

   List<String> getViewManagerNames(ReactApplicationContext var1, boolean var2);
}
