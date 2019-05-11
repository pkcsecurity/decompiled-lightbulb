package com.facebook.react.flat;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.flat.FlatUIImplementation;
import com.facebook.react.uimanager.UIImplementationProvider;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.uimanager.events.EventDispatcher;
import java.util.List;

public final class FlatUIImplementationProvider extends UIImplementationProvider {

   private final boolean mMemoryImprovementEnabled;


   public FlatUIImplementationProvider() {
      this.mMemoryImprovementEnabled = true;
   }

   public FlatUIImplementationProvider(boolean var1) {
      this.mMemoryImprovementEnabled = var1;
   }

   public FlatUIImplementation createUIImplementation(ReactApplicationContext var1, UIManagerModule.ViewManagerResolver var2, EventDispatcher var3, int var4) {
      throw new UnsupportedOperationException("Lazy version of FlatUIImplementations are not supported");
   }

   public FlatUIImplementation createUIImplementation(ReactApplicationContext var1, List<ViewManager> var2, EventDispatcher var3, int var4) {
      return FlatUIImplementation.createInstance(var1, var2, var3, this.mMemoryImprovementEnabled, var4);
   }
}
