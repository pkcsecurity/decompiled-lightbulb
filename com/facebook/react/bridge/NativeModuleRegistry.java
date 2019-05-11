package com.facebook.react.bridge;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.CxxModuleWrapperBase;
import com.facebook.react.bridge.JSInstance;
import com.facebook.react.bridge.JavaModuleWrapper;
import com.facebook.react.bridge.ModuleHolder;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.OnBatchCompleteListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.ReactMarkerConstants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class NativeModuleRegistry {

   private final ArrayList<ModuleHolder> mBatchCompleteListenerModules;
   private final Map<Class<? extends NativeModule>, ModuleHolder> mModules;
   private final ReactApplicationContext mReactApplicationContext;


   public NativeModuleRegistry(ReactApplicationContext var1, Map<Class<? extends NativeModule>, ModuleHolder> var2, ArrayList<ModuleHolder> var3) {
      this.mReactApplicationContext = var1;
      this.mModules = var2;
      this.mBatchCompleteListenerModules = var3;
   }

   private ArrayList<ModuleHolder> getBatchCompleteListenerModules() {
      return this.mBatchCompleteListenerModules;
   }

   private Map<Class<? extends NativeModule>, ModuleHolder> getModuleMap() {
      return this.mModules;
   }

   private ReactApplicationContext getReactApplicationContext() {
      return this.mReactApplicationContext;
   }

   public List<NativeModule> getAllModules() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.mModules.values().iterator();

      while(var2.hasNext()) {
         var1.add(((ModuleHolder)var2.next()).getModule());
      }

      return var1;
   }

   Collection<ModuleHolder> getCxxModules() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.mModules.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         if(CxxModuleWrapperBase.class.isAssignableFrom((Class)var3.getKey())) {
            var1.add(var3.getValue());
         }
      }

      return var1;
   }

   Collection<JavaModuleWrapper> getJavaModules(JSInstance var1) {
      ArrayList var2 = new ArrayList();
      Iterator var3 = this.mModules.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         Class var5 = (Class)var4.getKey();
         if(!CxxModuleWrapperBase.class.isAssignableFrom(var5)) {
            var2.add(new JavaModuleWrapper(var1, var5, (ModuleHolder)var4.getValue()));
         }
      }

      return var2;
   }

   public <T extends Object & NativeModule> T getModule(Class<T> var1) {
      return ((ModuleHolder)Assertions.assertNotNull(this.mModules.get(var1))).getModule();
   }

   public <T extends Object & NativeModule> boolean hasModule(Class<T> var1) {
      return this.mModules.containsKey(var1);
   }

   void notifyJSInstanceDestroy() {
      this.mReactApplicationContext.assertOnNativeModulesQueueThread();
      com.facebook.systrace.Systrace.beginSection(0L, "NativeModuleRegistry_notifyJSInstanceDestroy");

      try {
         Iterator var1 = this.mModules.values().iterator();

         while(var1.hasNext()) {
            ((ModuleHolder)var1.next()).destroy();
         }
      } finally {
         com.facebook.systrace.Systrace.endSection(0L);
      }

   }

   void notifyJSInstanceInitialized() {
      this.mReactApplicationContext.assertOnNativeModulesQueueThread("From version React Native v0.44, native modules are explicitly not initialized on the UI thread. See https://github.com/facebook/react-native/wiki/Breaking-Changes#d4611211-reactnativeandroidbreaking-move-nativemodule-initialization-off-ui-thread---aaachiuuu  for more details.");
      ReactMarker.logMarker(ReactMarkerConstants.NATIVE_MODULE_INITIALIZE_START);
      com.facebook.systrace.Systrace.beginSection(0L, "NativeModuleRegistry_notifyJSInstanceInitialized");

      try {
         Iterator var1 = this.mModules.values().iterator();

         while(var1.hasNext()) {
            ((ModuleHolder)var1.next()).markInitializable();
         }
      } finally {
         com.facebook.systrace.Systrace.endSection(0L);
         ReactMarker.logMarker(ReactMarkerConstants.NATIVE_MODULE_INITIALIZE_END);
      }

   }

   public void onBatchComplete() {
      Iterator var1 = this.mBatchCompleteListenerModules.iterator();

      while(var1.hasNext()) {
         ModuleHolder var2 = (ModuleHolder)var1.next();
         if(var2.hasInstance()) {
            ((OnBatchCompleteListener)var2.getModule()).onBatchComplete();
         }
      }

   }

   void registerModules(NativeModuleRegistry var1) {
      Assertions.assertCondition(this.mReactApplicationContext.equals(var1.getReactApplicationContext()), "Extending native modules with non-matching application contexts.");
      Map var2 = var1.getModuleMap();
      ArrayList var5 = var1.getBatchCompleteListenerModules();
      Iterator var6 = var2.entrySet().iterator();

      while(var6.hasNext()) {
         Entry var4 = (Entry)var6.next();
         Class var3 = (Class)var4.getKey();
         if(!this.mModules.containsKey(var3)) {
            ModuleHolder var7 = (ModuleHolder)var4.getValue();
            if(var5.contains(var7)) {
               this.mBatchCompleteListenerModules.add(var7);
            }

            this.mModules.put(var3, var7);
         }
      }

   }
}
