package com.facebook.react.bridge;

import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.JSInstance;
import com.facebook.react.bridge.JavaScriptContextHolder;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.MemoryPressureListener;
import com.facebook.react.bridge.NativeArray;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.NativeModuleRegistry;
import com.facebook.react.bridge.NotThreadSafeBridgeIdleDebugListener;
import com.facebook.react.bridge.queue.ReactQueueConfiguration;
import com.facebook.react.common.annotations.VisibleForTesting;
import java.util.Collection;
import javax.annotation.Nullable;

@DoNotStrip
public interface CatalystInstance extends JSInstance, MemoryPressureListener {

   void addBridgeIdleDebugListener(NotThreadSafeBridgeIdleDebugListener var1);

   @DoNotStrip
   void callFunction(String var1, String var2, NativeArray var3);

   void destroy();

   void extendNativeModules(NativeModuleRegistry var1);

   <T extends Object & JavaScriptModule> T getJSModule(Class<T> var1);

   JavaScriptContextHolder getJavaScriptContextHolder();

   <T extends Object & NativeModule> T getNativeModule(Class<T> var1);

   Collection<NativeModule> getNativeModules();

   ReactQueueConfiguration getReactQueueConfiguration();

   @Nullable
   String getSourceURL();

   <T extends Object & NativeModule> boolean hasNativeModule(Class<T> var1);

   boolean hasRunJSBundle();

   @VisibleForTesting
   void initialize();

   @DoNotStrip
   void invokeCallback(int var1, NativeArray var2);

   boolean isDestroyed();

   void removeBridgeIdleDebugListener(NotThreadSafeBridgeIdleDebugListener var1);

   void runJSBundle();

   @VisibleForTesting
   void setGlobalVariable(String var1, String var2);
}
