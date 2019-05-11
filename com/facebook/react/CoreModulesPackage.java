package com.facebook.react;

import com.facebook.react.LazyReactPackage;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackageLogger;
import com.facebook.react.bridge.ModuleSpec;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.ReactMarkerConstants;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.modules.core.ExceptionsManagerModule;
import com.facebook.react.modules.core.HeadlessJsTaskSupportModule;
import com.facebook.react.modules.core.Timing;
import com.facebook.react.modules.debug.AnimationsDebugModule;
import com.facebook.react.modules.debug.SourceCodeModule;
import com.facebook.react.modules.deviceinfo.DeviceInfoModule;
import com.facebook.react.modules.systeminfo.AndroidInfoModule;
import com.facebook.react.uimanager.UIImplementationProvider;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.systrace.Systrace;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import javax.inject.Provider;

class CoreModulesPackage extends LazyReactPackage implements ReactPackageLogger {

   private final DefaultHardwareBackBtnHandler mHardwareBackBtnHandler;
   private final boolean mLazyViewManagersEnabled;
   private final int mMinTimeLeftInFrameForNonBatchedOperationMs;
   private final ReactInstanceManager mReactInstanceManager;
   private final UIImplementationProvider mUIImplementationProvider;


   CoreModulesPackage(ReactInstanceManager var1, DefaultHardwareBackBtnHandler var2, UIImplementationProvider var3, boolean var4, int var5) {
      this.mReactInstanceManager = var1;
      this.mHardwareBackBtnHandler = var2;
      this.mUIImplementationProvider = var3;
      this.mLazyViewManagersEnabled = var4;
      this.mMinTimeLeftInFrameForNonBatchedOperationMs = var5;
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
                  return CoreModulesPackage.this.mReactInstanceManager.createViewManager(var1);
               }
               public List<String> getViewManagerNames() {
                  return CoreModulesPackage.this.mReactInstanceManager.getViewManagerNames();
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

   public void endProcessPackage() {
      ReactMarker.logMarker(ReactMarkerConstants.PROCESS_CORE_REACT_PACKAGE_END);
   }

   public List<ModuleSpec> getNativeModules(final ReactApplicationContext var1) {
      ArrayList var2 = new ArrayList();
      var2.add(ModuleSpec.nativeModuleSpec(AndroidInfoModule.class, new Provider() {
         public NativeModule get() {
            return new AndroidInfoModule();
         }
      }));
      var2.add(ModuleSpec.nativeModuleSpec(AnimationsDebugModule.class, new Provider() {
         public NativeModule get() {
            return new AnimationsDebugModule(var1, CoreModulesPackage.this.mReactInstanceManager.getDevSupportManager().getDevSettings());
         }
      }));
      var2.add(ModuleSpec.nativeModuleSpec(DeviceEventManagerModule.class, new Provider() {
         public NativeModule get() {
            return new DeviceEventManagerModule(var1, CoreModulesPackage.this.mHardwareBackBtnHandler);
         }
      }));
      var2.add(ModuleSpec.nativeModuleSpec(ExceptionsManagerModule.class, new Provider() {
         public NativeModule get() {
            return new ExceptionsManagerModule(CoreModulesPackage.this.mReactInstanceManager.getDevSupportManager());
         }
      }));
      var2.add(ModuleSpec.nativeModuleSpec(HeadlessJsTaskSupportModule.class, new Provider() {
         public NativeModule get() {
            return new HeadlessJsTaskSupportModule(var1);
         }
      }));
      var2.add(ModuleSpec.nativeModuleSpec(SourceCodeModule.class, new Provider() {
         public NativeModule get() {
            return new SourceCodeModule(var1);
         }
      }));
      var2.add(ModuleSpec.nativeModuleSpec(Timing.class, new Provider() {
         public NativeModule get() {
            return new Timing(var1, CoreModulesPackage.this.mReactInstanceManager.getDevSupportManager());
         }
      }));
      var2.add(ModuleSpec.nativeModuleSpec(UIManagerModule.class, new Provider() {
         public NativeModule get() {
            return CoreModulesPackage.this.createUIManager(var1);
         }
      }));
      var2.add(ModuleSpec.nativeModuleSpec(DeviceInfoModule.class, new Provider() {
         public NativeModule get() {
            return new DeviceInfoModule(var1);
         }
      }));
      return var2;
   }

   public ReactModuleInfoProvider getReactModuleInfoProvider() {
      return LazyReactPackage.getReactModuleInfoProviderViaReflection(this);
   }

   public void startProcessPackage() {
      ReactMarker.logMarker(ReactMarkerConstants.PROCESS_CORE_REACT_PACKAGE_START);
   }
}
