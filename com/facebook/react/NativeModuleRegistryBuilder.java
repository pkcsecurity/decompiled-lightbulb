package com.facebook.react;

import com.facebook.common.logging.FLog;
import com.facebook.react.LazyReactPackage;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactInstancePackage;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.BaseJavaModule;
import com.facebook.react.bridge.ModuleHolder;
import com.facebook.react.bridge.ModuleSpec;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.NativeModuleRegistry;
import com.facebook.react.bridge.OnBatchCompleteListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.ReactMarkerConstants;
import com.facebook.react.module.model.ReactModuleInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class NativeModuleRegistryBuilder {

   private final boolean mLazyNativeModulesEnabled;
   private final Map<Class<? extends NativeModule>, ModuleHolder> mModules = new HashMap();
   private final ReactApplicationContext mReactApplicationContext;
   private final ReactInstanceManager mReactInstanceManager;
   private final Map<String, Class<? extends NativeModule>> namesToType = new HashMap();


   public NativeModuleRegistryBuilder(ReactApplicationContext var1, ReactInstanceManager var2, boolean var3) {
      this.mReactApplicationContext = var1;
      this.mReactInstanceManager = var2;
      this.mLazyNativeModulesEnabled = var3;
   }

   public void addNativeModule(NativeModule var1) {
      String var3 = var1.getName();
      Class var2 = var1.getClass();
      if(this.namesToType.containsKey(var3)) {
         Class var4 = (Class)this.namesToType.get(var3);
         if(!var1.canOverrideExistingModule()) {
            StringBuilder var6 = new StringBuilder();
            var6.append("Native module ");
            var6.append(var2.getSimpleName());
            var6.append(" tried to override ");
            var6.append(var4.getSimpleName());
            var6.append(" for module name ");
            var6.append(var3);
            var6.append(". If this was your intention, set canOverrideExistingModule=true");
            throw new IllegalStateException(var6.toString());
         }

         this.mModules.remove(var4);
      }

      this.namesToType.put(var3, var2);
      ModuleHolder var5 = new ModuleHolder(var1);
      this.mModules.put(var2, var5);
   }

   public NativeModuleRegistry build() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.mModules.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         if(OnBatchCompleteListener.class.isAssignableFrom((Class)var3.getKey())) {
            var1.add(var3.getValue());
         }
      }

      return new NativeModuleRegistry(this.mReactApplicationContext, this.mModules, var1);
   }

   public void processPackage(ReactPackage var1) {
      List var9;
      if(this.mLazyNativeModulesEnabled) {
         if(!(var1 instanceof LazyReactPackage)) {
            throw new IllegalStateException("Lazy native modules requires all ReactPackage to inherit from LazyReactPackage");
         }

         LazyReactPackage var2 = (LazyReactPackage)var1;
         var9 = var2.getNativeModules(this.mReactApplicationContext);
         Map var3 = var2.getReactModuleInfoProvider().getReactModuleInfos();
         Iterator var4 = var9.iterator();

         while(var4.hasNext()) {
            ModuleSpec var10 = (ModuleSpec)var4.next();
            Class var12 = var10.getType();
            ReactModuleInfo var5 = (ReactModuleInfo)var3.get(var12);
            ModuleHolder var13;
            StringBuilder var15;
            if(var5 == null) {
               if(BaseJavaModule.class.isAssignableFrom(var12)) {
                  var15 = new StringBuilder();
                  var15.append("Native Java module ");
                  var15.append(var12.getSimpleName());
                  var15.append(" should be annotated with @ReactModule and added to a @ReactModuleList.");
                  throw new IllegalStateException(var15.toString());
               }

               ReactMarker.logMarker(ReactMarkerConstants.CREATE_MODULE_START, var10.getType().getName());

               NativeModule var11;
               try {
                  var11 = (NativeModule)var10.getProvider().get();
               } finally {
                  ReactMarker.logMarker(ReactMarkerConstants.CREATE_MODULE_END);
               }

               var13 = new ModuleHolder(var11);
            } else {
               var13 = new ModuleHolder(var5, var10.getProvider());
            }

            String var17 = var13.getName();
            if(this.namesToType.containsKey(var17)) {
               Class var6 = (Class)this.namesToType.get(var17);
               if(!var13.getCanOverrideExistingModule()) {
                  var15 = new StringBuilder();
                  var15.append("Native module ");
                  var15.append(var12.getSimpleName());
                  var15.append(" tried to override ");
                  var15.append(var6.getSimpleName());
                  var15.append(" for module name ");
                  var15.append(var17);
                  var15.append(". If this was your intention, set canOverrideExistingModule=true");
                  throw new IllegalStateException(var15.toString());
               }

               this.mModules.remove(var6);
            }

            this.namesToType.put(var17, var12);
            this.mModules.put(var12, var13);
         }
      } else {
         StringBuilder var14 = new StringBuilder();
         var14.append(var1.getClass().getSimpleName());
         var14.append(" is not a LazyReactPackage, falling back to old version.");
         FLog.d("ReactNative", var14.toString());
         if(var1 instanceof ReactInstancePackage) {
            var9 = ((ReactInstancePackage)var1).createNativeModules(this.mReactApplicationContext, this.mReactInstanceManager);
         } else {
            var9 = var1.createNativeModules(this.mReactApplicationContext);
         }

         Iterator var16 = var9.iterator();

         while(var16.hasNext()) {
            this.addNativeModule((NativeModule)var16.next());
         }
      }

   }
}
