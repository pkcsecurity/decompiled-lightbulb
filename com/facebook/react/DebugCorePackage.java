package com.facebook.react;

import com.facebook.react.LazyReactPackage;
import com.facebook.react.bridge.ModuleSpec;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.devsupport.JSCHeapCapture;
import com.facebook.react.devsupport.JSCSamplingProfiler;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Provider;

class DebugCorePackage extends LazyReactPackage {

   public List<ModuleSpec> getNativeModules(final ReactApplicationContext var1) {
      ArrayList var2 = new ArrayList();
      var2.add(ModuleSpec.nativeModuleSpec(JSCHeapCapture.class, new Provider() {
         public NativeModule get() {
            return new JSCHeapCapture(var1);
         }
      }));
      var2.add(ModuleSpec.nativeModuleSpec(JSCSamplingProfiler.class, new Provider() {
         public NativeModule get() {
            return new JSCSamplingProfiler(var1);
         }
      }));
      return var2;
   }

   public ReactModuleInfoProvider getReactModuleInfoProvider() {
      return LazyReactPackage.getReactModuleInfoProviderViaReflection(this);
   }
}
