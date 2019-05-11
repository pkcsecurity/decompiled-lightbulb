package com.facebook.react.uimanager;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.UIImplementation;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.uimanager.events.EventDispatcher;
import java.util.List;

public class UIImplementationProvider {

   public UIImplementation createUIImplementation(ReactApplicationContext var1, UIManagerModule.ViewManagerResolver var2, EventDispatcher var3, int var4) {
      return new UIImplementation(var1, var2, var3, var4);
   }

   public UIImplementation createUIImplementation(ReactApplicationContext var1, List<ViewManager> var2, EventDispatcher var3, int var4) {
      return new UIImplementation(var1, var2, var3, var4);
   }
}
