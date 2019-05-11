package com.facebook.react.uimanager;

import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import com.facebook.common.logging.FLog;
import com.facebook.debug.holder.PrinterHolder;
import com.facebook.debug.tags.ReactDebugOverlayTags;
import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.animation.Animation;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.GuardedRunnable;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.OnBatchCompleteListener;
import com.facebook.react.bridge.PerformanceCounter;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.ReactMarkerConstants;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.DisplayMetricsHolder;
import com.facebook.react.uimanager.MeasureSpecProvider;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ReactRootViewTagGenerator;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.SizeMonitoringFrameLayout;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIBlock;
import com.facebook.react.uimanager.UIImplementation;
import com.facebook.react.uimanager.UIImplementationProvider;
import com.facebook.react.uimanager.UIManagerModuleConstants;
import com.facebook.react.uimanager.UIManagerModuleConstantsHelper;
import com.facebook.react.uimanager.UIManagerModuleListener;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.uimanager.ViewManagerPropertyUpdater;
import com.facebook.react.uimanager.YogaNodePool;
import com.facebook.react.uimanager.debug.NotThreadSafeViewHierarchyUpdateDebugListener;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.systrace.Systrace;
import com.facebook.systrace.SystraceMessage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

@ReactModule(
   name = "UIManager"
)
public class UIManagerModule extends ReactContextBaseJavaModule implements LifecycleEventListener, OnBatchCompleteListener, PerformanceCounter {

   private static final boolean DEBUG = PrinterHolder.getPrinter().shouldDisplayLogMessage(ReactDebugOverlayTags.UI_MANAGER);
   protected static final String NAME = "UIManager";
   private int mBatchId = 0;
   private final Map<String, Object> mCustomDirectEvents;
   private final EventDispatcher mEventDispatcher;
   private boolean mEventsWereSentToJS = false;
   private final List<UIManagerModuleListener> mListeners = new ArrayList();
   private final UIManagerModule.MemoryTrimCallback mMemoryTrimCallback = new UIManagerModule.MemoryTrimCallback(null);
   private final Map<String, Object> mModuleConstants;
   private final UIImplementation mUIImplementation;


   public UIManagerModule(ReactApplicationContext var1, UIManagerModule.ViewManagerResolver var2, UIImplementationProvider var3, int var4) {
      super(var1);
      DisplayMetricsHolder.initDisplayMetricsIfNotInitialized(var1);
      this.mEventDispatcher = new EventDispatcher(var1);
      this.mModuleConstants = createConstants(var2);
      this.mCustomDirectEvents = UIManagerModuleConstants.getDirectEventTypeConstants();
      this.mUIImplementation = var3.createUIImplementation(var1, var2, this.mEventDispatcher, var4);
      var1.addLifecycleEventListener(this);
   }

   public UIManagerModule(ReactApplicationContext var1, List<ViewManager> var2, UIImplementationProvider var3, int var4) {
      super(var1);
      DisplayMetricsHolder.initDisplayMetricsIfNotInitialized(var1);
      this.mEventDispatcher = new EventDispatcher(var1);
      this.mCustomDirectEvents = MapBuilder.newHashMap();
      this.mModuleConstants = createConstants(var2, (Map)null, this.mCustomDirectEvents);
      this.mUIImplementation = var3.createUIImplementation(var1, var2, this.mEventDispatcher, var4);
      var1.addLifecycleEventListener(this);
   }

   private static Map<String, Object> createConstants(UIManagerModule.ViewManagerResolver var0) {
      ReactMarker.logMarker(ReactMarkerConstants.CREATE_UI_MANAGER_MODULE_CONSTANTS_START);
      Systrace.beginSection(0L, "CreateUIManagerConstants");

      Map var3;
      try {
         var3 = UIManagerModuleConstantsHelper.createConstants(var0);
      } finally {
         Systrace.endSection(0L);
         ReactMarker.logMarker(ReactMarkerConstants.CREATE_UI_MANAGER_MODULE_CONSTANTS_END);
      }

      return var3;
   }

   private static Map<String, Object> createConstants(List<ViewManager> var0, @Nullable Map<String, Object> var1, @Nullable Map<String, Object> var2) {
      ReactMarker.logMarker(ReactMarkerConstants.CREATE_UI_MANAGER_MODULE_CONSTANTS_START);
      Systrace.beginSection(0L, "CreateUIManagerConstants");

      Map var5;
      try {
         var5 = UIManagerModuleConstantsHelper.createConstants(var0, var1, var2);
      } finally {
         Systrace.endSection(0L);
         ReactMarker.logMarker(ReactMarkerConstants.CREATE_UI_MANAGER_MODULE_CONSTANTS_END);
      }

      return var5;
   }

   public void addAnimation(int var1, int var2, Callback var3) {
      this.mUIImplementation.addAnimation(var1, var2, var3);
   }

   public <T extends SizeMonitoringFrameLayout & MeasureSpecProvider> int addRootView(T var1) {
      Systrace.beginSection(0L, "UIManagerModule.addRootView");
      final int var2 = ReactRootViewTagGenerator.getNextRootViewTag();
      final ReactApplicationContext var3 = this.getReactApplicationContext();
      ThemedReactContext var4 = new ThemedReactContext(var3, var1.getContext());
      this.mUIImplementation.registerRootView(var1, var2, var4);
      var1.setOnSizeChangedListener(new SizeMonitoringFrameLayout.OnSizeChangedListener() {
         public void onSizeChanged(final int var1, final int var2x, int var3x, int var4) {
            var3.runUIBackgroundRunnable(new GuardedRunnable(var3) {
               public void runGuarded() {
                  UIManagerModule.this.updateNodeSize(var2, var1, var2x);
               }
            });
         }
      });
      Systrace.endSection(0L);
      return var2;
   }

   public void addUIBlock(UIBlock var1) {
      this.mUIImplementation.addUIBlock(var1);
   }

   public void addUIManagerListener(UIManagerModuleListener var1) {
      this.mListeners.add(var1);
   }

   @ReactMethod
   public void clearJSResponder() {
      this.mUIImplementation.clearJSResponder();
   }

   @ReactMethod
   public void configureNextLayoutAnimation(ReadableMap var1, Callback var2, Callback var3) {
      this.mUIImplementation.configureNextLayoutAnimation(var1, var2, var3);
   }

   @ReactMethod
   public void createView(int var1, String var2, int var3, ReadableMap var4) {
      if(DEBUG) {
         StringBuilder var5 = new StringBuilder();
         var5.append("(UIManager.createView) tag: ");
         var5.append(var1);
         var5.append(", class: ");
         var5.append(var2);
         var5.append(", props: ");
         var5.append(var4);
         String var6 = var5.toString();
         FLog.d("ReactNative", var6);
         PrinterHolder.getPrinter().logMessage(ReactDebugOverlayTags.UI_MANAGER, var6);
      }

      this.mUIImplementation.createView(var1, var2, var3, var4);
   }

   @ReactMethod
   public void dispatchViewManagerCommand(int var1, int var2, ReadableArray var3) {
      this.mUIImplementation.dispatchViewManagerCommand(var1, var2, var3);
   }

   @ReactMethod
   public void findSubviewIn(int var1, ReadableArray var2, Callback var3) {
      this.mUIImplementation.findSubviewIn(var1, (float)Math.round(PixelUtil.toPixelFromDIP(var2.getDouble(0))), (float)Math.round(PixelUtil.toPixelFromDIP(var2.getDouble(1))), var3);
   }

   public Map<String, Object> getConstants() {
      return this.mModuleConstants;
   }

   @ReactMethod(
      isBlockingSynchronousMethod = true
   )
   @Nullable
   @DoNotStrip
   public WritableMap getConstantsForViewManager(String param1) {
      // $FF: Couldn't be decompiled
   }

   public UIManagerModule.CustomEventNamesResolver getDirectEventNamesResolver() {
      return new UIManagerModule.CustomEventNamesResolver() {
         @Nullable
         public String resolveCustomEventName(String var1) {
            Map var2 = (Map)UIManagerModule.this.mCustomDirectEvents.get(var1);
            return var2 != null?(String)var2.get("registrationName"):var1;
         }
      };
   }

   public EventDispatcher getEventDispatcher() {
      return this.mEventDispatcher;
   }

   public String getName() {
      return "UIManager";
   }

   public Map<String, Long> getPerformanceCounters() {
      return this.mUIImplementation.getProfiledBatchPerfCounters();
   }

   public UIImplementation getUIImplementation() {
      return this.mUIImplementation;
   }

   public void initialize() {
      this.getReactApplicationContext().registerComponentCallbacks(this.mMemoryTrimCallback);
   }

   public void invalidateNodeLayout(int var1) {
      ReactShadowNode var2 = this.mUIImplementation.resolveShadowNode(var1);
      if(var2 == null) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Warning : attempted to dirty a non-existent react shadow node. reactTag=");
         var3.append(var1);
         FLog.w("ReactNative", var3.toString());
      } else {
         var2.dirty();
      }
   }

   @ReactMethod
   public void manageChildren(int var1, @Nullable ReadableArray var2, @Nullable ReadableArray var3, @Nullable ReadableArray var4, @Nullable ReadableArray var5, @Nullable ReadableArray var6) {
      if(DEBUG) {
         StringBuilder var7 = new StringBuilder();
         var7.append("(UIManager.manageChildren) tag: ");
         var7.append(var1);
         var7.append(", moveFrom: ");
         var7.append(var2);
         var7.append(", moveTo: ");
         var7.append(var3);
         var7.append(", addTags: ");
         var7.append(var4);
         var7.append(", atIndices: ");
         var7.append(var5);
         var7.append(", removeFrom: ");
         var7.append(var6);
         String var8 = var7.toString();
         FLog.d("ReactNative", var8);
         PrinterHolder.getPrinter().logMessage(ReactDebugOverlayTags.UI_MANAGER, var8);
      }

      this.mUIImplementation.manageChildren(var1, var2, var3, var4, var5, var6);
   }

   @ReactMethod
   public void measure(int var1, Callback var2) {
      this.mUIImplementation.measure(var1, var2);
   }

   @ReactMethod
   public void measureInWindow(int var1, Callback var2) {
      this.mUIImplementation.measureInWindow(var1, var2);
   }

   @ReactMethod
   public void measureLayout(int var1, int var2, Callback var3, Callback var4) {
      this.mUIImplementation.measureLayout(var1, var2, var3, var4);
   }

   @ReactMethod
   public void measureLayoutRelativeToParent(int var1, Callback var2, Callback var3) {
      this.mUIImplementation.measureLayoutRelativeToParent(var1, var2, var3);
   }

   public void onBatchComplete() {
      int var1 = this.mBatchId++;
      SystraceMessage.beginSection(0L, "onBatchCompleteUI").arg("BatchId", var1).flush();
      Iterator var2 = this.mListeners.iterator();

      while(var2.hasNext()) {
         ((UIManagerModuleListener)var2.next()).willDispatchViewUpdates(this);
      }

      try {
         this.mUIImplementation.dispatchViewUpdates(var1);
      } finally {
         Systrace.endSection(0L);
      }

   }

   public void onCatalystInstanceDestroy() {
      super.onCatalystInstanceDestroy();
      this.mEventDispatcher.onCatalystInstanceDestroyed();
      this.getReactApplicationContext().unregisterComponentCallbacks(this.mMemoryTrimCallback);
      YogaNodePool.get().clear();
      ViewManagerPropertyUpdater.clear();
   }

   public void onHostDestroy() {
      this.mUIImplementation.onHostDestroy();
   }

   public void onHostPause() {
      this.mUIImplementation.onHostPause();
   }

   public void onHostResume() {
      this.mUIImplementation.onHostResume();
   }

   public void prependUIBlock(UIBlock var1) {
      this.mUIImplementation.prependUIBlock(var1);
   }

   public void registerAnimation(Animation var1) {
      this.mUIImplementation.registerAnimation(var1);
   }

   public void removeAnimation(int var1, int var2) {
      this.mUIImplementation.removeAnimation(var1, var2);
   }

   @ReactMethod
   public void removeRootView(int var1) {
      this.mUIImplementation.removeRootView(var1);
   }

   @ReactMethod
   public void removeSubviewsFromContainerWithID(int var1) {
      this.mUIImplementation.removeSubviewsFromContainerWithID(var1);
   }

   public void removeUIManagerListener(UIManagerModuleListener var1) {
      this.mListeners.remove(var1);
   }

   @ReactMethod
   public void replaceExistingNonRootView(int var1, int var2) {
      this.mUIImplementation.replaceExistingNonRootView(var1, var2);
   }

   public int resolveRootTagFromReactTag(int var1) {
      return this.mUIImplementation.resolveRootTagFromReactTag(var1);
   }

   @ReactMethod
   public void sendAccessibilityEvent(int var1, int var2) {
      this.mUIImplementation.sendAccessibilityEvent(var1, var2);
   }

   @ReactMethod
   public void setChildren(int var1, ReadableArray var2) {
      if(DEBUG) {
         StringBuilder var3 = new StringBuilder();
         var3.append("(UIManager.setChildren) tag: ");
         var3.append(var1);
         var3.append(", children: ");
         var3.append(var2);
         String var4 = var3.toString();
         FLog.d("ReactNative", var4);
         PrinterHolder.getPrinter().logMessage(ReactDebugOverlayTags.UI_MANAGER, var4);
      }

      this.mUIImplementation.setChildren(var1, var2);
   }

   @ReactMethod
   public void setJSResponder(int var1, boolean var2) {
      this.mUIImplementation.setJSResponder(var1, var2);
   }

   @ReactMethod
   public void setLayoutAnimationEnabledExperimental(boolean var1) {
      this.mUIImplementation.setLayoutAnimationEnabledExperimental(var1);
   }

   public void setViewHierarchyUpdateDebugListener(@Nullable NotThreadSafeViewHierarchyUpdateDebugListener var1) {
      this.mUIImplementation.setViewHierarchyUpdateDebugListener(var1);
   }

   public void setViewLocalData(final int var1, final Object var2) {
      final ReactApplicationContext var3 = this.getReactApplicationContext();
      var3.assertOnUiQueueThread();
      var3.runUIBackgroundRunnable(new GuardedRunnable(var3) {
         public void runGuarded() {
            UIManagerModule.this.mUIImplementation.setViewLocalData(var1, var2);
         }
      });
   }

   @ReactMethod
   public void showPopupMenu(int var1, ReadableArray var2, Callback var3, Callback var4) {
      this.mUIImplementation.showPopupMenu(var1, var2, var3, var4);
   }

   public void updateNodeSize(int var1, int var2, int var3) {
      this.getReactApplicationContext().assertOnUIBackgroundOrNativeModulesThread();
      this.mUIImplementation.updateNodeSize(var1, var2, var3);
   }

   public void updateRootLayoutSpecs(int var1, int var2, int var3) {
      this.mUIImplementation.updateRootView(var1, var2, var3);
      this.mUIImplementation.dispatchViewUpdates(-1);
   }

   @ReactMethod
   public void updateView(int var1, String var2, ReadableMap var3) {
      if(DEBUG) {
         StringBuilder var4 = new StringBuilder();
         var4.append("(UIManager.updateView) tag: ");
         var4.append(var1);
         var4.append(", class: ");
         var4.append(var2);
         var4.append(", props: ");
         var4.append(var3);
         String var5 = var4.toString();
         FLog.d("ReactNative", var5);
         PrinterHolder.getPrinter().logMessage(ReactDebugOverlayTags.UI_MANAGER, var5);
      }

      this.mUIImplementation.updateView(var1, var2, var3);
   }

   @ReactMethod
   public void viewIsDescendantOf(int var1, int var2, Callback var3) {
      this.mUIImplementation.viewIsDescendantOf(var1, var2, var3);
   }

   public interface ViewManagerResolver {

      @Nullable
      ViewManager getViewManager(String var1);

      List<String> getViewManagerNames();
   }

   public interface CustomEventNamesResolver {

      @Nullable
      String resolveCustomEventName(String var1);
   }

   class MemoryTrimCallback implements ComponentCallbacks2 {

      private MemoryTrimCallback() {}

      // $FF: synthetic method
      MemoryTrimCallback(Object var2) {
         this();
      }

      public void onConfigurationChanged(Configuration var1) {}

      public void onLowMemory() {}

      public void onTrimMemory(int var1) {
         if(var1 >= 60) {
            YogaNodePool.get().clear();
         }

      }
   }
}
