package com.facebook.react.bridge;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;
import com.facebook.common.logging.FLog;
import com.facebook.infer.annotation.Assertions;
import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.JSBundleLoader;
import com.facebook.react.bridge.JavaModuleWrapper;
import com.facebook.react.bridge.JavaScriptContextHolder;
import com.facebook.react.bridge.JavaScriptExecutor;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.JavaScriptModuleRegistry;
import com.facebook.react.bridge.ModuleHolder;
import com.facebook.react.bridge.NativeArray;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.NativeModuleCallExceptionHandler;
import com.facebook.react.bridge.NativeModuleRegistry;
import com.facebook.react.bridge.NotThreadSafeBridgeIdleDebugListener;
import com.facebook.react.bridge.ReactBridge;
import com.facebook.react.bridge.ReactCallback;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.ReactMarkerConstants;
import com.facebook.react.bridge.Systrace;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.queue.MessageQueueThread;
import com.facebook.react.bridge.queue.QueueThreadExceptionHandler;
import com.facebook.react.bridge.queue.ReactQueueConfiguration;
import com.facebook.react.bridge.queue.ReactQueueConfigurationImpl;
import com.facebook.react.bridge.queue.ReactQueueConfigurationSpec;
import com.facebook.react.common.annotations.VisibleForTesting;
import com.facebook.systrace.TraceListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;

@DoNotStrip
public class CatalystInstanceImpl implements CatalystInstance {

   private static final AtomicInteger sNextInstanceIdForTrace;
   private volatile boolean mAcceptCalls;
   private final CopyOnWriteArrayList<NotThreadSafeBridgeIdleDebugListener> mBridgeIdleListeners;
   private volatile boolean mDestroyed;
   private final HybridData mHybridData;
   private boolean mInitialized;
   private boolean mJSBundleHasLoaded;
   private final JSBundleLoader mJSBundleLoader;
   private final ArrayList<CatalystInstanceImpl.PendingJSCall> mJSCallsPendingInit;
   private final Object mJSCallsPendingInitLock;
   private final JavaScriptModuleRegistry mJSModuleRegistry;
   private JavaScriptContextHolder mJavaScriptContextHolder;
   private final String mJsPendingCallsTitleForTrace;
   private final NativeModuleCallExceptionHandler mNativeModuleCallExceptionHandler;
   private final NativeModuleRegistry mNativeModuleRegistry;
   private final MessageQueueThread mNativeModulesQueueThread;
   private final AtomicInteger mPendingJSCalls;
   private final ReactQueueConfigurationImpl mReactQueueConfiguration;
   @Nullable
   private String mSourceURL;
   private final TraceListener mTraceListener;
   @Nullable
   private final MessageQueueThread mUIBackgroundQueueThread;


   static {
      ReactBridge.staticInit();
      sNextInstanceIdForTrace = new AtomicInteger(1);
   }

   private CatalystInstanceImpl(ReactQueueConfigurationSpec var1, JavaScriptExecutor var2, NativeModuleRegistry var3, JSBundleLoader var4, NativeModuleCallExceptionHandler var5) {
      this.mPendingJSCalls = new AtomicInteger(0);
      StringBuilder var6 = new StringBuilder();
      var6.append("pending_js_calls_instance");
      var6.append(sNextInstanceIdForTrace.getAndIncrement());
      this.mJsPendingCallsTitleForTrace = var6.toString();
      this.mDestroyed = false;
      this.mJSCallsPendingInit = new ArrayList();
      this.mJSCallsPendingInitLock = new Object();
      this.mInitialized = false;
      this.mAcceptCalls = false;
      Log.d("ReactNative", "Initializing React Xplat Bridge.");
      this.mHybridData = initHybrid();
      this.mReactQueueConfiguration = ReactQueueConfigurationImpl.create(var1, new CatalystInstanceImpl.NativeExceptionHandler(null));
      this.mBridgeIdleListeners = new CopyOnWriteArrayList();
      this.mNativeModuleRegistry = var3;
      this.mJSModuleRegistry = new JavaScriptModuleRegistry();
      this.mJSBundleLoader = var4;
      this.mNativeModuleCallExceptionHandler = var5;
      this.mNativeModulesQueueThread = this.mReactQueueConfiguration.getNativeModulesQueueThread();
      this.mUIBackgroundQueueThread = this.mReactQueueConfiguration.getUIBackgroundQueueThread();
      this.mTraceListener = new CatalystInstanceImpl.JSProfilerTraceListener(this);
      Log.d("ReactNative", "Initializing React Xplat Bridge before initializeBridge");
      this.initializeBridge(new CatalystInstanceImpl.BridgeCallback(this), var2, this.mReactQueueConfiguration.getJSQueueThread(), this.mNativeModulesQueueThread, this.mUIBackgroundQueueThread, this.mNativeModuleRegistry.getJavaModules(this), this.mNativeModuleRegistry.getCxxModules());
      Log.d("ReactNative", "Initializing React Xplat Bridge after initializeBridge");
      this.mJavaScriptContextHolder = new JavaScriptContextHolder(this.getJavaScriptContext());
   }

   // $FF: synthetic method
   CatalystInstanceImpl(ReactQueueConfigurationSpec var1, JavaScriptExecutor var2, NativeModuleRegistry var3, JSBundleLoader var4, NativeModuleCallExceptionHandler var5, Object var6) {
      this(var1, var2, var3, var4, var5);
   }

   private void decrementPendingJSCalls() {
      int var2 = this.mPendingJSCalls.decrementAndGet();
      boolean var1;
      if(var2 == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      com.facebook.systrace.Systrace.traceCounter(0L, this.mJsPendingCallsTitleForTrace, var2);
      if(var1 && !this.mBridgeIdleListeners.isEmpty()) {
         this.mNativeModulesQueueThread.runOnQueue(new Runnable() {
            public void run() {
               Iterator var1 = CatalystInstanceImpl.this.mBridgeIdleListeners.iterator();

               while(var1.hasNext()) {
                  ((NotThreadSafeBridgeIdleDebugListener)var1.next()).onTransitionToBridgeIdle();
               }

            }
         });
      }

   }

   private native long getJavaScriptContext();

   private void incrementPendingJSCalls() {
      int var2 = this.mPendingJSCalls.getAndIncrement();
      boolean var1;
      if(var2 == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      com.facebook.systrace.Systrace.traceCounter(0L, this.mJsPendingCallsTitleForTrace, var2 + 1);
      if(var1 && !this.mBridgeIdleListeners.isEmpty()) {
         this.mNativeModulesQueueThread.runOnQueue(new Runnable() {
            public void run() {
               Iterator var1 = CatalystInstanceImpl.this.mBridgeIdleListeners.iterator();

               while(var1.hasNext()) {
                  ((NotThreadSafeBridgeIdleDebugListener)var1.next()).onTransitionToBridgeBusy();
               }

            }
         });
      }

   }

   private static native HybridData initHybrid();

   private native void initializeBridge(ReactCallback var1, JavaScriptExecutor var2, MessageQueueThread var3, MessageQueueThread var4, MessageQueueThread var5, Collection<JavaModuleWrapper> var6, Collection<ModuleHolder> var7);

   private native void jniCallJSCallback(int var1, NativeArray var2);

   private native void jniCallJSFunction(String var1, String var2, NativeArray var3);

   private native void jniExtendNativeModules(Collection<JavaModuleWrapper> var1, Collection<ModuleHolder> var2);

   private native void jniHandleMemoryPressure(int var1);

   private native void jniLoadScriptFromAssets(AssetManager var1, String var2, boolean var3);

   private native void jniSetSourceURL(String var1);

   private void onNativeException(Exception var1) {
      this.mNativeModuleCallExceptionHandler.handleException(var1);
      this.mReactQueueConfiguration.getUIQueueThread().runOnQueue(new Runnable() {
         public void run() {
            CatalystInstanceImpl.this.destroy();
         }
      });
   }

   public void addBridgeIdleDebugListener(NotThreadSafeBridgeIdleDebugListener var1) {
      this.mBridgeIdleListeners.add(var1);
   }

   public void callFunction(CatalystInstanceImpl.PendingJSCall param1) {
      // $FF: Couldn't be decompiled
   }

   public void callFunction(String var1, String var2, NativeArray var3) {
      this.callFunction(new CatalystInstanceImpl.PendingJSCall(var1, var2, var3));
   }

   public void destroy() {
      Log.d("ReactNative", "CatalystInstanceImpl.destroy() start");
      UiThreadUtil.assertOnUiThread();
      if(!this.mDestroyed) {
         ReactMarker.logMarker(ReactMarkerConstants.DESTROY_CATALYST_INSTANCE_START);
         this.mDestroyed = true;
         this.mNativeModulesQueueThread.runOnQueue(new Runnable() {
            public void run() {
               CatalystInstanceImpl.this.mNativeModuleRegistry.notifyJSInstanceDestroy();
               AtomicInteger var2 = CatalystInstanceImpl.this.mPendingJSCalls;
               boolean var1 = false;
               if(var2.getAndSet(0) == 0) {
                  var1 = true;
               }

               if(!var1 && !CatalystInstanceImpl.this.mBridgeIdleListeners.isEmpty()) {
                  Iterator var3 = CatalystInstanceImpl.this.mBridgeIdleListeners.iterator();

                  while(var3.hasNext()) {
                     ((NotThreadSafeBridgeIdleDebugListener)var3.next()).onTransitionToBridgeIdle();
                  }
               }

               AsyncTask.execute(new Runnable() {
                  public void run() {
                     CatalystInstanceImpl.this.mJavaScriptContextHolder.clear();
                     CatalystInstanceImpl.this.mHybridData.resetNative();
                     CatalystInstanceImpl.this.getReactQueueConfiguration().destroy();
                     Log.d("ReactNative", "CatalystInstanceImpl.destroy() end");
                     ReactMarker.logMarker(ReactMarkerConstants.DESTROY_CATALYST_INSTANCE_END);
                  }
               });
            }
         });
         com.facebook.systrace.Systrace.unregisterListener(this.mTraceListener);
      }
   }

   public void extendNativeModules(NativeModuleRegistry var1) {
      this.mNativeModuleRegistry.registerModules(var1);
      this.jniExtendNativeModules(var1.getJavaModules(this), var1.getCxxModules());
   }

   public <T extends Object & JavaScriptModule> T getJSModule(Class<T> var1) {
      return this.mJSModuleRegistry.getJavaScriptModule(this, var1);
   }

   public JavaScriptContextHolder getJavaScriptContextHolder() {
      return this.mJavaScriptContextHolder;
   }

   public <T extends Object & NativeModule> T getNativeModule(Class<T> var1) {
      return this.mNativeModuleRegistry.getModule(var1);
   }

   public Collection<NativeModule> getNativeModules() {
      return this.mNativeModuleRegistry.getAllModules();
   }

   public ReactQueueConfiguration getReactQueueConfiguration() {
      return this.mReactQueueConfiguration;
   }

   @Nullable
   public String getSourceURL() {
      return this.mSourceURL;
   }

   public void handleMemoryPressure(int var1) {
      if(!this.mDestroyed) {
         this.jniHandleMemoryPressure(var1);
      }
   }

   public <T extends Object & NativeModule> boolean hasNativeModule(Class<T> var1) {
      return this.mNativeModuleRegistry.hasModule(var1);
   }

   public boolean hasRunJSBundle() {
      // $FF: Couldn't be decompiled
   }

   @VisibleForTesting
   public void initialize() {
      Log.d("ReactNative", "CatalystInstanceImpl.initialize()");
      Assertions.assertCondition(this.mInitialized ^ true, "This catalyst instance has already been initialized");
      Assertions.assertCondition(this.mAcceptCalls, "RunJSBundle hasn\'t completed.");
      this.mInitialized = true;
      this.mNativeModulesQueueThread.runOnQueue(new Runnable() {
         public void run() {
            CatalystInstanceImpl.this.mNativeModuleRegistry.notifyJSInstanceInitialized();
         }
      });
   }

   public void invokeCallback(int var1, NativeArray var2) {
      if(this.mDestroyed) {
         FLog.w("ReactNative", "Invoking JS callback after bridge has been destroyed.");
      } else {
         this.jniCallJSCallback(var1, var2);
      }
   }

   public boolean isDestroyed() {
      return this.mDestroyed;
   }

   public native void jniLoadScriptFromFile(String var1, String var2, boolean var3);

   void loadScriptFromAssets(AssetManager var1, String var2, boolean var3) {
      this.mSourceURL = var2;
      this.jniLoadScriptFromAssets(var1, var2, var3);
   }

   void loadScriptFromFile(String var1, String var2, boolean var3) {
      this.mSourceURL = var2;
      this.jniLoadScriptFromFile(var1, var2, var3);
   }

   public void removeBridgeIdleDebugListener(NotThreadSafeBridgeIdleDebugListener var1) {
      this.mBridgeIdleListeners.remove(var1);
   }

   public void runJSBundle() {
      // $FF: Couldn't be decompiled
   }

   public native void setGlobalVariable(String var1, String var2);

   void setSourceURLs(String var1, String var2) {
      this.mSourceURL = var1;
      this.jniSetSourceURL(var2);
   }

   class NativeExceptionHandler implements QueueThreadExceptionHandler {

      private NativeExceptionHandler() {}

      // $FF: synthetic method
      NativeExceptionHandler(Object var2) {
         this();
      }

      public void handleException(Exception var1) {
         CatalystInstanceImpl.this.onNativeException(var1);
      }
   }

   static class JSProfilerTraceListener implements TraceListener {

      private final WeakReference<CatalystInstanceImpl> mOuter;


      public JSProfilerTraceListener(CatalystInstanceImpl var1) {
         this.mOuter = new WeakReference(var1);
      }

      public void onTraceStarted() {
         CatalystInstanceImpl var1 = (CatalystInstanceImpl)this.mOuter.get();
         if(var1 != null) {
            ((Systrace)var1.getJSModule(Systrace.class)).setEnabled(true);
         }

      }

      public void onTraceStopped() {
         CatalystInstanceImpl var1 = (CatalystInstanceImpl)this.mOuter.get();
         if(var1 != null) {
            ((Systrace)var1.getJSModule(Systrace.class)).setEnabled(false);
         }

      }
   }

   public static class PendingJSCall {

      @Nullable
      public NativeArray mArguments;
      public String mMethod;
      public String mModule;


      public PendingJSCall(String var1, String var2, @Nullable NativeArray var3) {
         this.mModule = var1;
         this.mMethod = var2;
         this.mArguments = var3;
      }

      void call(CatalystInstanceImpl var1) {
         Object var2;
         if(this.mArguments != null) {
            var2 = this.mArguments;
         } else {
            var2 = new WritableNativeArray();
         }

         var1.jniCallJSFunction(this.mModule, this.mMethod, (NativeArray)var2);
      }

      public String toString() {
         StringBuilder var2 = new StringBuilder();
         var2.append(this.mModule);
         var2.append(".");
         var2.append(this.mMethod);
         var2.append("(");
         String var1;
         if(this.mArguments == null) {
            var1 = "";
         } else {
            var1 = this.mArguments.toString();
         }

         var2.append(var1);
         var2.append(")");
         return var2.toString();
      }
   }

   static class BridgeCallback implements ReactCallback {

      private final WeakReference<CatalystInstanceImpl> mOuter;


      public BridgeCallback(CatalystInstanceImpl var1) {
         this.mOuter = new WeakReference(var1);
      }

      public void decrementPendingJSCalls() {
         CatalystInstanceImpl var1 = (CatalystInstanceImpl)this.mOuter.get();
         if(var1 != null) {
            var1.decrementPendingJSCalls();
         }

      }

      public void incrementPendingJSCalls() {
         CatalystInstanceImpl var1 = (CatalystInstanceImpl)this.mOuter.get();
         if(var1 != null) {
            var1.incrementPendingJSCalls();
         }

      }

      public void onBatchComplete() {
         CatalystInstanceImpl var1 = (CatalystInstanceImpl)this.mOuter.get();
         if(var1 != null) {
            var1.mNativeModuleRegistry.onBatchComplete();
         }

      }
   }

   public static class Builder {

      @Nullable
      private JSBundleLoader mJSBundleLoader;
      @Nullable
      private JavaScriptExecutor mJSExecutor;
      @Nullable
      private NativeModuleCallExceptionHandler mNativeModuleCallExceptionHandler;
      @Nullable
      private ReactQueueConfigurationSpec mReactQueueConfigurationSpec;
      @Nullable
      private NativeModuleRegistry mRegistry;


      public CatalystInstanceImpl build() {
         return new CatalystInstanceImpl((ReactQueueConfigurationSpec)Assertions.assertNotNull(this.mReactQueueConfigurationSpec), (JavaScriptExecutor)Assertions.assertNotNull(this.mJSExecutor), (NativeModuleRegistry)Assertions.assertNotNull(this.mRegistry), (JSBundleLoader)Assertions.assertNotNull(this.mJSBundleLoader), (NativeModuleCallExceptionHandler)Assertions.assertNotNull(this.mNativeModuleCallExceptionHandler), null);
      }

      public CatalystInstanceImpl.Builder setJSBundleLoader(JSBundleLoader var1) {
         this.mJSBundleLoader = var1;
         return this;
      }

      public CatalystInstanceImpl.Builder setJSExecutor(JavaScriptExecutor var1) {
         this.mJSExecutor = var1;
         return this;
      }

      public CatalystInstanceImpl.Builder setNativeModuleCallExceptionHandler(NativeModuleCallExceptionHandler var1) {
         this.mNativeModuleCallExceptionHandler = var1;
         return this;
      }

      public CatalystInstanceImpl.Builder setReactQueueConfigurationSpec(ReactQueueConfigurationSpec var1) {
         this.mReactQueueConfigurationSpec = var1;
         return this;
      }

      public CatalystInstanceImpl.Builder setRegistry(NativeModuleRegistry var1) {
         this.mRegistry = var1;
         return this;
      }
   }
}
