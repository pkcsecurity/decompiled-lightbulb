package com.facebook.react;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import java.util.List;

public abstract class ReactInstancePackage implements ReactPackage {

   public List<NativeModule> createNativeModules(ReactApplicationContext var1) {
      throw new RuntimeException("ReactInstancePackage must be passed in the ReactInstanceManager.");
   }

   public abstract List<NativeModule> createNativeModules(ReactApplicationContext var1, ReactInstanceManager var2);
}
