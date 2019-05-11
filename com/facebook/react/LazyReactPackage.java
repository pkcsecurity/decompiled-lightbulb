package com.facebook.react;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.ModuleSpec;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.ReactMarkerConstants;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.systrace.SystraceMessage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class LazyReactPackage implements ReactPackage {

   public static ReactModuleInfoProvider getReactModuleInfoProviderViaReflection(LazyReactPackage var0) {
      StringBuilder var1;
      Class var6;
      try {
         var1 = new StringBuilder();
         var1.append(var0.getClass().getCanonicalName());
         var1.append("$$ReactModuleInfoProvider");
         var6 = Class.forName(var1.toString());
      } catch (ClassNotFoundException var5) {
         throw new RuntimeException(var5);
      }

      if(var6 == null) {
         var1 = new StringBuilder();
         var1.append("ReactModuleInfoProvider class for ");
         var1.append(var0.getClass().getCanonicalName());
         var1.append(" not found.");
         throw new RuntimeException(var1.toString());
      } else {
         StringBuilder var2;
         try {
            ReactModuleInfoProvider var7 = (ReactModuleInfoProvider)var6.newInstance();
            return var7;
         } catch (InstantiationException var3) {
            var2 = new StringBuilder();
            var2.append("Unable to instantiate ReactModuleInfoProvider for ");
            var2.append(var0.getClass());
            throw new RuntimeException(var2.toString(), var3);
         } catch (IllegalAccessException var4) {
            var2 = new StringBuilder();
            var2.append("Unable to instantiate ReactModuleInfoProvider for ");
            var2.append(var0.getClass());
            throw new RuntimeException(var2.toString(), var4);
         }
      }
   }

   public final List<NativeModule> createNativeModules(ReactApplicationContext var1) {
      ArrayList var2 = new ArrayList();

      NativeModule var7;
      for(Iterator var6 = this.getNativeModules(var1).iterator(); var6.hasNext(); var2.add(var7)) {
         ModuleSpec var3 = (ModuleSpec)var6.next();
         SystraceMessage.beginSection(0L, "createNativeModule").arg("module", var3.getType()).flush();
         ReactMarker.logMarker(ReactMarkerConstants.CREATE_MODULE_START, var3.getType().getSimpleName());

         try {
            var7 = (NativeModule)var3.getProvider().get();
         } finally {
            ReactMarker.logMarker(ReactMarkerConstants.CREATE_MODULE_END);
            SystraceMessage.endSection(0L).flush();
         }
      }

      return var2;
   }

   public List<ViewManager> createViewManagers(ReactApplicationContext var1) {
      List var2 = this.getViewManagers(var1);
      if(var2 != null && !var2.isEmpty()) {
         ArrayList var3 = new ArrayList();
         Iterator var4 = var2.iterator();

         while(var4.hasNext()) {
            var3.add((ViewManager)((ModuleSpec)var4.next()).getProvider().get());
         }

         return var3;
      } else {
         return Collections.emptyList();
      }
   }

   public abstract List<ModuleSpec> getNativeModules(ReactApplicationContext var1);

   public abstract ReactModuleInfoProvider getReactModuleInfoProvider();

   public List<ModuleSpec> getViewManagers(ReactApplicationContext var1) {
      return Collections.emptyList();
   }
}
