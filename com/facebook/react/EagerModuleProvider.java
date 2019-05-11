package com.facebook.react;

import com.facebook.react.bridge.NativeModule;
import javax.inject.Provider;

public class EagerModuleProvider implements Provider<NativeModule> {

   private final NativeModule mModule;


   public EagerModuleProvider(NativeModule var1) {
      this.mModule = var1;
   }

   public NativeModule get() {
      return this.mModule;
   }
}
