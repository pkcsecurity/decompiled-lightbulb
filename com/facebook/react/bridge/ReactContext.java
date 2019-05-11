package com.facebook.react.bridge;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.JavaScriptContextHolder;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.NativeModuleCallExceptionHandler;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.ReactMarkerConstants;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.queue.MessageQueueThread;
import com.facebook.react.bridge.queue.ReactQueueConfiguration;
import com.facebook.react.common.LifecycleState;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.annotation.Nullable;

public class ReactContext extends ContextWrapper {

   private static final String EARLY_JS_ACCESS_EXCEPTION_MESSAGE = "Tried to access a JS module before the React instance was fully set up. Calls to ReactContext#getJSModule should only happen once initialize() has been called on your native module.";
   private final CopyOnWriteArraySet<ActivityEventListener> mActivityEventListeners = new CopyOnWriteArraySet();
   @Nullable
   private CatalystInstance mCatalystInstance;
   @Nullable
   private WeakReference<Activity> mCurrentActivity;
   @Nullable
   private LayoutInflater mInflater;
   @Nullable
   private MessageQueueThread mJSMessageQueueThread;
   private final CopyOnWriteArraySet<LifecycleEventListener> mLifecycleEventListeners = new CopyOnWriteArraySet();
   private LifecycleState mLifecycleState;
   @Nullable
   private NativeModuleCallExceptionHandler mNativeModuleCallExceptionHandler;
   @Nullable
   private MessageQueueThread mNativeModulesMessageQueueThread;
   @Nullable
   private MessageQueueThread mUiBackgroundMessageQueueThread;
   @Nullable
   private MessageQueueThread mUiMessageQueueThread;


   public ReactContext(Context var1) {
      super(var1);
      this.mLifecycleState = LifecycleState.BEFORE_CREATE;
   }

   public void addActivityEventListener(ActivityEventListener var1) {
      this.mActivityEventListeners.add(var1);
   }

   public void addLifecycleEventListener(final LifecycleEventListener var1) {
      this.mLifecycleEventListeners.add(var1);
      if(this.hasActiveCatalystInstance()) {
         switch(null.$SwitchMap$com$facebook$react$common$LifecycleState[this.mLifecycleState.ordinal()]) {
         case 1:
         case 2:
            break;
         case 3:
            this.runOnUiQueueThread(new Runnable() {
               public void run() {
                  try {
                     var1.onHostResume();
                  } catch (RuntimeException var2) {
                     ReactContext.this.handleException(var2);
                  }
               }
            });
            break;
         default:
            throw new RuntimeException("Unhandled lifecycle state.");
         }
      }

   }

   public void assertOnJSQueueThread() {
      ((MessageQueueThread)Assertions.assertNotNull(this.mJSMessageQueueThread)).assertIsOnThread();
   }

   public void assertOnNativeModulesQueueThread() {
      ((MessageQueueThread)Assertions.assertNotNull(this.mNativeModulesMessageQueueThread)).assertIsOnThread();
   }

   public void assertOnNativeModulesQueueThread(String var1) {
      ((MessageQueueThread)Assertions.assertNotNull(this.mNativeModulesMessageQueueThread)).assertIsOnThread(var1);
   }

   public void assertOnUIBackgroundOrNativeModulesThread() {
      if(this.mUiBackgroundMessageQueueThread == null) {
         this.assertOnNativeModulesQueueThread();
      } else {
         this.assertOnUiBackgroundQueueThread();
      }
   }

   public void assertOnUiBackgroundQueueThread() {
      ((MessageQueueThread)Assertions.assertNotNull(this.mUiBackgroundMessageQueueThread)).assertIsOnThread();
   }

   public void assertOnUiQueueThread() {
      ((MessageQueueThread)Assertions.assertNotNull(this.mUiMessageQueueThread)).assertIsOnThread();
   }

   public void destroy() {
      UiThreadUtil.assertOnUiThread();
      if(this.mCatalystInstance != null) {
         this.mCatalystInstance.destroy();
      }

   }

   public CatalystInstance getCatalystInstance() {
      return (CatalystInstance)Assertions.assertNotNull(this.mCatalystInstance);
   }

   @Nullable
   public Activity getCurrentActivity() {
      return this.mCurrentActivity == null?null:(Activity)this.mCurrentActivity.get();
   }

   public <T extends Object & JavaScriptModule> T getJSModule(Class<T> var1) {
      if(this.mCatalystInstance == null) {
         throw new RuntimeException("Tried to access a JS module before the React instance was fully set up. Calls to ReactContext#getJSModule should only happen once initialize() has been called on your native module.");
      } else {
         return this.mCatalystInstance.getJSModule(var1);
      }
   }

   public JavaScriptContextHolder getJavaScriptContextHolder() {
      return this.mCatalystInstance.getJavaScriptContextHolder();
   }

   public LifecycleState getLifecycleState() {
      return this.mLifecycleState;
   }

   public <T extends Object & NativeModule> T getNativeModule(Class<T> var1) {
      if(this.mCatalystInstance == null) {
         throw new RuntimeException("Trying to call native module before CatalystInstance has been set!");
      } else {
         return this.mCatalystInstance.getNativeModule(var1);
      }
   }

   public Object getSystemService(String var1) {
      if("layout_inflater".equals(var1)) {
         if(this.mInflater == null) {
            this.mInflater = LayoutInflater.from(this.getBaseContext()).cloneInContext(this);
         }

         return this.mInflater;
      } else {
         return this.getBaseContext().getSystemService(var1);
      }
   }

   public void handleException(RuntimeException var1) {
      if(this.mCatalystInstance != null && !this.mCatalystInstance.isDestroyed() && this.mNativeModuleCallExceptionHandler != null) {
         this.mNativeModuleCallExceptionHandler.handleException(var1);
      } else {
         throw var1;
      }
   }

   public boolean hasActiveCatalystInstance() {
      return this.mCatalystInstance != null && !this.mCatalystInstance.isDestroyed();
   }

   public boolean hasCurrentActivity() {
      return this.mCurrentActivity != null && this.mCurrentActivity.get() != null;
   }

   public <T extends Object & NativeModule> boolean hasNativeModule(Class<T> var1) {
      if(this.mCatalystInstance == null) {
         throw new RuntimeException("Trying to call native module before CatalystInstance has been set!");
      } else {
         return this.mCatalystInstance.hasNativeModule(var1);
      }
   }

   public boolean hasUIBackgroundRunnableThread() {
      return this.mUiBackgroundMessageQueueThread != null;
   }

   public void initializeWithInstance(CatalystInstance var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("CatalystInstance cannot be null.");
      } else if(this.mCatalystInstance != null) {
         throw new IllegalStateException("ReactContext has been already initialized");
      } else {
         this.mCatalystInstance = var1;
         ReactQueueConfiguration var2 = var1.getReactQueueConfiguration();
         this.mUiMessageQueueThread = var2.getUIQueueThread();
         this.mUiBackgroundMessageQueueThread = var2.getUIBackgroundQueueThread();
         this.mNativeModulesMessageQueueThread = var2.getNativeModulesQueueThread();
         this.mJSMessageQueueThread = var2.getJSQueueThread();
      }
   }

   public boolean isOnJSQueueThread() {
      return ((MessageQueueThread)Assertions.assertNotNull(this.mJSMessageQueueThread)).isOnThread();
   }

   public boolean isOnNativeModulesQueueThread() {
      return ((MessageQueueThread)Assertions.assertNotNull(this.mNativeModulesMessageQueueThread)).isOnThread();
   }

   public boolean isOnUiQueueThread() {
      return ((MessageQueueThread)Assertions.assertNotNull(this.mUiMessageQueueThread)).isOnThread();
   }

   public void onActivityResult(Activity var1, int var2, int var3, Intent var4) {
      Iterator var5 = this.mActivityEventListeners.iterator();

      while(var5.hasNext()) {
         ActivityEventListener var6 = (ActivityEventListener)var5.next();

         try {
            var6.onActivityResult(var1, var2, var3, var4);
         } catch (RuntimeException var7) {
            this.handleException(var7);
         }
      }

   }

   public void onHostCreate(@Nullable Activity var1) {
      this.mCurrentActivity = new WeakReference(var1);
   }

   public void onHostDestroy() {
      UiThreadUtil.assertOnUiThread();
      this.mLifecycleState = LifecycleState.BEFORE_CREATE;
      Iterator var1 = this.mLifecycleEventListeners.iterator();

      while(var1.hasNext()) {
         LifecycleEventListener var2 = (LifecycleEventListener)var1.next();

         try {
            var2.onHostDestroy();
         } catch (RuntimeException var3) {
            this.handleException(var3);
         }
      }

      this.mCurrentActivity = null;
   }

   public void onHostPause() {
      this.mLifecycleState = LifecycleState.BEFORE_RESUME;
      ReactMarker.logMarker(ReactMarkerConstants.ON_HOST_PAUSE_START);
      Iterator var1 = this.mLifecycleEventListeners.iterator();

      while(var1.hasNext()) {
         LifecycleEventListener var2 = (LifecycleEventListener)var1.next();

         try {
            var2.onHostPause();
         } catch (RuntimeException var3) {
            this.handleException(var3);
         }
      }

      ReactMarker.logMarker(ReactMarkerConstants.ON_HOST_PAUSE_END);
   }

   public void onHostResume(@Nullable Activity var1) {
      this.mLifecycleState = LifecycleState.RESUMED;
      this.mCurrentActivity = new WeakReference(var1);
      ReactMarker.logMarker(ReactMarkerConstants.ON_HOST_RESUME_START);
      Iterator var4 = this.mLifecycleEventListeners.iterator();

      while(var4.hasNext()) {
         LifecycleEventListener var2 = (LifecycleEventListener)var4.next();

         try {
            var2.onHostResume();
         } catch (RuntimeException var3) {
            this.handleException(var3);
         }
      }

      ReactMarker.logMarker(ReactMarkerConstants.ON_HOST_RESUME_END);
   }

   public void onNewIntent(@Nullable Activity var1, Intent var2) {
      UiThreadUtil.assertOnUiThread();
      this.mCurrentActivity = new WeakReference(var1);
      Iterator var5 = this.mActivityEventListeners.iterator();

      while(var5.hasNext()) {
         ActivityEventListener var3 = (ActivityEventListener)var5.next();

         try {
            var3.onNewIntent(var2);
         } catch (RuntimeException var4) {
            this.handleException(var4);
         }
      }

   }

   public void removeActivityEventListener(ActivityEventListener var1) {
      this.mActivityEventListeners.remove(var1);
   }

   public void removeLifecycleEventListener(LifecycleEventListener var1) {
      this.mLifecycleEventListeners.remove(var1);
   }

   public void runOnJSQueueThread(Runnable var1) {
      ((MessageQueueThread)Assertions.assertNotNull(this.mJSMessageQueueThread)).runOnQueue(var1);
   }

   public void runOnNativeModulesQueueThread(Runnable var1) {
      ((MessageQueueThread)Assertions.assertNotNull(this.mNativeModulesMessageQueueThread)).runOnQueue(var1);
   }

   public void runOnUiBackgroundQueueThread(Runnable var1) {
      ((MessageQueueThread)Assertions.assertNotNull(this.mUiBackgroundMessageQueueThread)).runOnQueue(var1);
   }

   public void runOnUiQueueThread(Runnable var1) {
      ((MessageQueueThread)Assertions.assertNotNull(this.mUiMessageQueueThread)).runOnQueue(var1);
   }

   public void runUIBackgroundRunnable(Runnable var1) {
      if(this.mUiBackgroundMessageQueueThread == null) {
         this.runOnNativeModulesQueueThread(var1);
      } else {
         this.runOnUiBackgroundQueueThread(var1);
      }
   }

   public void setNativeModuleCallExceptionHandler(@Nullable NativeModuleCallExceptionHandler var1) {
      this.mNativeModuleCallExceptionHandler = var1;
   }

   public boolean startActivityForResult(Intent var1, int var2, Bundle var3) {
      Activity var4 = this.getCurrentActivity();
      Assertions.assertNotNull(var4);
      var4.startActivityForResult(var1, var2, var3);
      return true;
   }
}
