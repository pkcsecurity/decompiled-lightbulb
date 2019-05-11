package com.facebook.react;

import android.app.Application;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactInstanceManagerBuilder;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptExecutorFactory;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.devsupport.RedBoxHandler;
import com.facebook.react.uimanager.UIImplementationProvider;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public abstract class ReactNativeHost {

   private final Application mApplication;
   @Nullable
   private ReactInstanceManager mReactInstanceManager;


   protected ReactNativeHost(Application var1) {
      this.mApplication = var1;
   }

   public void clear() {
      if(this.mReactInstanceManager != null) {
         this.mReactInstanceManager.destroy();
         this.mReactInstanceManager = null;
      }

   }

   public ReactInstanceManager createReactInstanceManager() {
      ReactInstanceManagerBuilder var1 = ReactInstanceManager.builder().setApplication(this.mApplication).setJSMainModulePath(this.getJSMainModuleName()).setUseDeveloperSupport(this.getUseDeveloperSupport()).setRedBoxHandler(this.getRedBoxHandler()).setJavaScriptExecutorFactory(this.getJavaScriptExecutorFactory()).setUIImplementationProvider(this.getUIImplementationProvider()).setInitialLifecycleState(LifecycleState.BEFORE_CREATE);
      Iterator var2 = this.getPackages().iterator();

      while(var2.hasNext()) {
         var1.addPackage((ReactPackage)var2.next());
      }

      String var3 = this.getJSBundleFile();
      if(var3 != null) {
         var1.setJSBundleFile(var3);
      } else {
         var1.setBundleAssetName((String)Assertions.assertNotNull(this.getBundleAssetName()));
      }

      return var1.build();
   }

   protected final Application getApplication() {
      return this.mApplication;
   }

   @Nullable
   protected String getBundleAssetName() {
      return "index.android.bundle";
   }

   @Nullable
   public String getJSBundleFile() {
      return null;
   }

   public String getJSMainModuleName() {
      return "index.android";
   }

   @Nullable
   protected JavaScriptExecutorFactory getJavaScriptExecutorFactory() {
      return null;
   }

   public abstract List<ReactPackage> getPackages();

   public ReactInstanceManager getReactInstanceManager() {
      if(this.mReactInstanceManager == null) {
         this.mReactInstanceManager = this.createReactInstanceManager();
      }

      return this.mReactInstanceManager;
   }

   @Nullable
   protected RedBoxHandler getRedBoxHandler() {
      return null;
   }

   protected UIImplementationProvider getUIImplementationProvider() {
      return new UIImplementationProvider();
   }

   public abstract boolean getUseDeveloperSupport();

   public boolean hasInstance() {
      return this.mReactInstanceManager != null;
   }
}
