package com.facebook.react;

import com.facebook.react.LazyReactPackage;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.ModuleSpec;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.modules.core.ExceptionsManagerModule;
import com.facebook.react.modules.core.HeadlessJsTaskSupportModule;
import com.facebook.react.modules.core.Timing;
import com.facebook.react.modules.debug.SourceCodeModule;
import com.facebook.react.modules.deviceinfo.DeviceInfoModule;
import com.facebook.react.modules.systeminfo.AndroidInfoModule;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Provider;

class BridgeCorePackage extends LazyReactPackage {

   private final DefaultHardwareBackBtnHandler mHardwareBackBtnHandler;
   private final ReactInstanceManager mReactInstanceManager;


   BridgeCorePackage(ReactInstanceManager var1, DefaultHardwareBackBtnHandler var2) {
      this.mReactInstanceManager = var1;
      this.mHardwareBackBtnHandler = var2;
   }

   public List<ModuleSpec> getNativeModules(final ReactApplicationContext var1) {
      ArrayList var2 = new ArrayList();
      var2.add(ModuleSpec.nativeModuleSpec(AndroidInfoModule.class, new Provider() {
         public NativeModule get() {
            return new AndroidInfoModule();
         }
      }));
      var2.add(ModuleSpec.nativeModuleSpec(DeviceEventManagerModule.class, new Provider() {
         public NativeModule get() {
            return new DeviceEventManagerModule(var1, BridgeCorePackage.this.mHardwareBackBtnHandler);
         }
      }));
      var2.add(ModuleSpec.nativeModuleSpec(ExceptionsManagerModule.class, new Provider() {
         public NativeModule get() {
            return new ExceptionsManagerModule(BridgeCorePackage.this.mReactInstanceManager.getDevSupportManager());
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
            return new Timing(var1, BridgeCorePackage.this.mReactInstanceManager.getDevSupportManager());
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
}
