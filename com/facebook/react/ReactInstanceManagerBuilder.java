package com.facebook.react;

import android.app.Activity;
import android.app.Application;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JSBundleLoader;
import com.facebook.react.bridge.JSCJavaScriptExecutorFactory;
import com.facebook.react.bridge.JavaScriptExecutorFactory;
import com.facebook.react.bridge.NativeModuleCallExceptionHandler;
import com.facebook.react.bridge.NotThreadSafeBridgeIdleDebugListener;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.devsupport.RedBoxHandler;
import com.facebook.react.devsupport.interfaces.DevBundleDownloadListener;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.systeminfo.AndroidInfoHelpers;
import com.facebook.react.uimanager.UIImplementationProvider;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

public class ReactInstanceManagerBuilder {

   @Nullable
   private Application mApplication;
   @Nullable
   private NotThreadSafeBridgeIdleDebugListener mBridgeIdleDebugListener;
   @Nullable
   private Activity mCurrentActivity;
   @Nullable
   private DefaultHardwareBackBtnHandler mDefaultHardwareBackBtnHandler;
   private boolean mDelayViewManagerClassLoadsEnabled;
   @Nullable
   private DevBundleDownloadListener mDevBundleDownloadListener;
   private boolean mEnableSplitPackage;
   @Nullable
   private LifecycleState mInitialLifecycleState;
   @Nullable
   private String mJSBundleAssetUrl;
   @Nullable
   private JSBundleLoader mJSBundleLoader;
   @Nullable
   private String mJSMainModulePath;
   @Nullable
   private JavaScriptExecutorFactory mJavaScriptExecutorFactory;
   private boolean mLazyNativeModulesEnabled;
   private boolean mLazyViewManagersEnabled;
   private int mMinNumShakes = 1;
   private int mMinTimeLeftInFrameForNonBatchedOperationMs = -1;
   @Nullable
   private NativeModuleCallExceptionHandler mNativeModuleCallExceptionHandler;
   private final List<ReactPackage> mPackages = new ArrayList();
   @Nullable
   private RedBoxHandler mRedBoxHandler;
   @Nullable
   private UIImplementationProvider mUIImplementationProvider;
   private boolean mUseDeveloperSupport;
   private boolean mUseOnlyDefaultPackages;
   private boolean mUseSeparateUIBackgroundThread;


   public ReactInstanceManagerBuilder addPackage(ReactPackage var1) {
      this.mPackages.add(var1);
      return this;
   }

   public ReactInstanceManagerBuilder addPackages(List<ReactPackage> var1) {
      this.mPackages.addAll(var1);
      return this;
   }

   public ReactInstanceManager build() {
      Assertions.assertNotNull(this.mApplication, "Application property has not been set with this builder");
      boolean var1 = this.mUseDeveloperSupport;
      boolean var2 = true;
      if(!var1 && this.mJSBundleAssetUrl == null && this.mJSBundleLoader == null) {
         var1 = false;
      } else {
         var1 = true;
      }

      Assertions.assertCondition(var1, "JS Bundle File or Asset URL has to be provided when dev support is disabled");
      var1 = var2;
      if(this.mJSMainModulePath == null) {
         var1 = var2;
         if(this.mJSBundleAssetUrl == null) {
            if(this.mJSBundleLoader != null) {
               var1 = var2;
            } else {
               var1 = false;
            }
         }
      }

      Assertions.assertCondition(var1, "Either MainModulePath or JS Bundle File needs to be provided");
      if(this.mUIImplementationProvider == null) {
         this.mUIImplementationProvider = new UIImplementationProvider();
      }

      String var3 = this.mApplication.getPackageName();
      String var4 = AndroidInfoHelpers.getFriendlyDeviceName();
      Application var5 = this.mApplication;
      Activity var6 = this.mCurrentActivity;
      DefaultHardwareBackBtnHandler var7 = this.mDefaultHardwareBackBtnHandler;
      Object var8;
      if(this.mJavaScriptExecutorFactory == null) {
         var8 = new JSCJavaScriptExecutorFactory(var3, var4);
      } else {
         var8 = this.mJavaScriptExecutorFactory;
      }

      JSBundleLoader var9;
      if(this.mJSBundleLoader == null && this.mJSBundleAssetUrl != null) {
         var9 = JSBundleLoader.createAssetLoader(this.mApplication, this.mJSBundleAssetUrl, false);
      } else {
         var9 = this.mJSBundleLoader;
      }

      return new ReactInstanceManager(var5, var6, var7, (JavaScriptExecutorFactory)var8, var9, this.mJSMainModulePath, this.mPackages, this.mUseDeveloperSupport, this.mBridgeIdleDebugListener, (LifecycleState)Assertions.assertNotNull(this.mInitialLifecycleState, "Initial lifecycle state was not set"), this.mUIImplementationProvider, this.mNativeModuleCallExceptionHandler, this.mRedBoxHandler, this.mLazyNativeModulesEnabled, this.mLazyViewManagersEnabled, this.mDelayViewManagerClassLoadsEnabled, this.mDevBundleDownloadListener, this.mUseSeparateUIBackgroundThread, this.mMinNumShakes, this.mEnableSplitPackage, this.mUseOnlyDefaultPackages, this.mMinTimeLeftInFrameForNonBatchedOperationMs);
   }

   public ReactInstanceManagerBuilder setApplication(Application var1) {
      this.mApplication = var1;
      return this;
   }

   public ReactInstanceManagerBuilder setBridgeIdleDebugListener(NotThreadSafeBridgeIdleDebugListener var1) {
      this.mBridgeIdleDebugListener = var1;
      return this;
   }

   public ReactInstanceManagerBuilder setBundleAssetName(String var1) {
      if(var1 == null) {
         var1 = null;
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("assets://");
         var2.append(var1);
         var1 = var2.toString();
      }

      this.mJSBundleAssetUrl = var1;
      this.mJSBundleLoader = null;
      return this;
   }

   public ReactInstanceManagerBuilder setCurrentActivity(Activity var1) {
      this.mCurrentActivity = var1;
      return this;
   }

   public ReactInstanceManagerBuilder setDefaultHardwareBackBtnHandler(DefaultHardwareBackBtnHandler var1) {
      this.mDefaultHardwareBackBtnHandler = var1;
      return this;
   }

   public ReactInstanceManagerBuilder setDelayViewManagerClassLoadsEnabled(boolean var1) {
      this.mDelayViewManagerClassLoadsEnabled = var1;
      return this;
   }

   public ReactInstanceManagerBuilder setDevBundleDownloadListener(@Nullable DevBundleDownloadListener var1) {
      this.mDevBundleDownloadListener = var1;
      return this;
   }

   public ReactInstanceManagerBuilder setEnableSplitPackage(boolean var1) {
      this.mEnableSplitPackage = var1;
      return this;
   }

   public ReactInstanceManagerBuilder setInitialLifecycleState(LifecycleState var1) {
      this.mInitialLifecycleState = var1;
      return this;
   }

   public ReactInstanceManagerBuilder setJSBundleFile(String var1) {
      if(var1.startsWith("assets://")) {
         this.mJSBundleAssetUrl = var1;
         this.mJSBundleLoader = null;
         return this;
      } else {
         return this.setJSBundleLoader(JSBundleLoader.createFileLoader(var1));
      }
   }

   public ReactInstanceManagerBuilder setJSBundleLoader(JSBundleLoader var1) {
      this.mJSBundleLoader = var1;
      this.mJSBundleAssetUrl = null;
      return this;
   }

   public ReactInstanceManagerBuilder setJSMainModulePath(String var1) {
      this.mJSMainModulePath = var1;
      return this;
   }

   public ReactInstanceManagerBuilder setJavaScriptExecutorFactory(@Nullable JavaScriptExecutorFactory var1) {
      this.mJavaScriptExecutorFactory = var1;
      return this;
   }

   public ReactInstanceManagerBuilder setLazyNativeModulesEnabled(boolean var1) {
      this.mLazyNativeModulesEnabled = var1;
      return this;
   }

   public ReactInstanceManagerBuilder setLazyViewManagersEnabled(boolean var1) {
      this.mLazyViewManagersEnabled = var1;
      return this;
   }

   public ReactInstanceManagerBuilder setMinNumShakes(int var1) {
      this.mMinNumShakes = var1;
      return this;
   }

   public ReactInstanceManagerBuilder setMinTimeLeftInFrameForNonBatchedOperationMs(int var1) {
      this.mMinTimeLeftInFrameForNonBatchedOperationMs = var1;
      return this;
   }

   public ReactInstanceManagerBuilder setNativeModuleCallExceptionHandler(NativeModuleCallExceptionHandler var1) {
      this.mNativeModuleCallExceptionHandler = var1;
      return this;
   }

   public ReactInstanceManagerBuilder setRedBoxHandler(@Nullable RedBoxHandler var1) {
      this.mRedBoxHandler = var1;
      return this;
   }

   public ReactInstanceManagerBuilder setUIImplementationProvider(@Nullable UIImplementationProvider var1) {
      this.mUIImplementationProvider = var1;
      return this;
   }

   public ReactInstanceManagerBuilder setUseDeveloperSupport(boolean var1) {
      this.mUseDeveloperSupport = var1;
      return this;
   }

   public ReactInstanceManagerBuilder setUseOnlyDefaultPackages(boolean var1) {
      this.mUseOnlyDefaultPackages = var1;
      return this;
   }

   public ReactInstanceManagerBuilder setUseSeparateUIBackgroundThread(boolean var1) {
      this.mUseSeparateUIBackgroundThread = var1;
      return this;
   }
}
