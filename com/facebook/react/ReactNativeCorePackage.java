package com.facebook.react;

import com.facebook.react.LazyReactPackage;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.ModuleSpec;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.ReactMarkerConstants;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.uimanager.UIImplementationProvider;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.systrace.Systrace;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import javax.inject.Provider;

public class ReactNativeCorePackage extends LazyReactPackage {

   private final boolean mLazyViewManagersEnabled;
   private final int mMinTimeLeftInFrameForNonBatchedOperationMs;
   private final ReactInstanceManager mReactInstanceManager;
   private final UIImplementationProvider mUIImplementationProvider;


   public ReactNativeCorePackage(ReactInstanceManager var1, UIImplementationProvider var2, boolean var3, int var4) {
      this.mReactInstanceManager = var1;
      this.mUIImplementationProvider = var2;
      this.mLazyViewManagersEnabled = var3;
      this.mMinTimeLeftInFrameForNonBatchedOperationMs = var4;
   }

   private UIManagerModule createUIManager(ReactApplicationContext var1) {
      ReactMarker.logMarker(ReactMarkerConstants.CREATE_UI_MANAGER_MODULE_START);
      Systrace.beginSection(0L, "createUIManagerModule");

      UIManagerModule var4;
      try {
         if(this.mLazyViewManagersEnabled) {
            var4 = new UIManagerModule(var1, new UIManagerModule.ViewManagerResolver() {
               @Nullable
               public ViewManager getViewManager(String var1) {
                  return ReactNativeCorePackage.this.mReactInstanceManager.createViewManager(var1);
               }
               public List<String> getViewManagerNames() {
                  return ReactNativeCorePackage.this.mReactInstanceManager.getViewManagerNames();
               }
            }, this.mUIImplementationProvider, this.mMinTimeLeftInFrameForNonBatchedOperationMs);
            return var4;
         }

         var4 = new UIManagerModule(var1, this.mReactInstanceManager.createAllViewManagers(var1), this.mUIImplementationProvider, this.mMinTimeLeftInFrameForNonBatchedOperationMs);
      } finally {
         Systrace.endSection(0L);
         ReactMarker.logMarker(ReactMarkerConstants.CREATE_UI_MANAGER_MODULE_END);
      }

      return var4;
   }

   public List<ModuleSpec> getNativeModules(final ReactApplicationContext var1) {
      return Collections.singletonList(ModuleSpec.nativeModuleSpec(UIManagerModule.class, new Provider() {
         public NativeModule get() {
            return ReactNativeCorePackage.this.createUIManager(var1);
         }
      }));
   }

   public ReactModuleInfoProvider getReactModuleInfoProvider() {
      return LazyReactPackage.getReactModuleInfoProviderViaReflection(this);
   }
}
