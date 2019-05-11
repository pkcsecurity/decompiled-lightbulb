package com.facebook.react;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactInstancePackage;
import com.facebook.react.ReactPackage;
import com.facebook.react.ViewManagerOnDemandReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.annotation.Nullable;

public class CompositeReactPackage extends ReactInstancePackage implements ViewManagerOnDemandReactPackage {

   private final List<ReactPackage> mChildReactPackages = new ArrayList();


   public CompositeReactPackage(ReactPackage var1, ReactPackage var2, ReactPackage ... var3) {
      this.mChildReactPackages.add(var1);
      this.mChildReactPackages.add(var2);
      Collections.addAll(this.mChildReactPackages, var3);
   }

   public List<NativeModule> createNativeModules(ReactApplicationContext var1) {
      HashMap var2 = new HashMap();
      Iterator var3 = this.mChildReactPackages.iterator();

      while(var3.hasNext()) {
         Iterator var4 = ((ReactPackage)var3.next()).createNativeModules(var1).iterator();

         while(var4.hasNext()) {
            NativeModule var5 = (NativeModule)var4.next();
            var2.put(var5.getName(), var5);
         }
      }

      return new ArrayList(var2.values());
   }

   public List<NativeModule> createNativeModules(ReactApplicationContext var1, ReactInstanceManager var2) {
      HashMap var4 = new HashMap();
      Iterator var5 = this.mChildReactPackages.iterator();

      while(var5.hasNext()) {
         ReactPackage var3 = (ReactPackage)var5.next();
         List var7;
         if(var3 instanceof ReactInstancePackage) {
            var7 = ((ReactInstancePackage)var3).createNativeModules(var1, var2);
         } else {
            var7 = var3.createNativeModules(var1);
         }

         Iterator var8 = var7.iterator();

         while(var8.hasNext()) {
            NativeModule var6 = (NativeModule)var8.next();
            var4.put(var6.getName(), var6);
         }
      }

      return new ArrayList(var4.values());
   }

   @Nullable
   public ViewManager createViewManager(ReactApplicationContext var1, String var2, boolean var3) {
      ListIterator var4 = this.mChildReactPackages.listIterator(this.mChildReactPackages.size());

      while(var4.hasPrevious()) {
         ReactPackage var5 = (ReactPackage)var4.previous();
         if(var5 instanceof ViewManagerOnDemandReactPackage) {
            ViewManager var6 = ((ViewManagerOnDemandReactPackage)var5).createViewManager(var1, var2, var3);
            if(var6 != null) {
               return var6;
            }
         }
      }

      return null;
   }

   public List<ViewManager> createViewManagers(ReactApplicationContext var1) {
      HashMap var2 = new HashMap();
      Iterator var3 = this.mChildReactPackages.iterator();

      while(var3.hasNext()) {
         Iterator var4 = ((ReactPackage)var3.next()).createViewManagers(var1).iterator();

         while(var4.hasNext()) {
            ViewManager var5 = (ViewManager)var4.next();
            var2.put(var5.getName(), var5);
         }
      }

      return new ArrayList(var2.values());
   }

   public List<String> getViewManagerNames(ReactApplicationContext var1, boolean var2) {
      HashSet var3 = new HashSet();
      Iterator var4 = this.mChildReactPackages.iterator();

      while(var4.hasNext()) {
         ReactPackage var5 = (ReactPackage)var4.next();
         if(var5 instanceof ViewManagerOnDemandReactPackage) {
            List var6 = ((ViewManagerOnDemandReactPackage)var5).getViewManagerNames(var1, var2);
            if(var6 != null) {
               var3.addAll(var6);
            }
         }
      }

      return new ArrayList(var3);
   }
}
