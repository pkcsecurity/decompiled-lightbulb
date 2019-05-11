package com.facebook.react.uimanager;

import android.os.SystemClock;
import android.view.View.MeasureSpec;
import com.facebook.common.logging.FLog;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.animation.Animation;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.modules.i18nmanager.I18nUtil;
import com.facebook.react.uimanager.IllegalViewOperationException;
import com.facebook.react.uimanager.MeasureSpecProvider;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.NativeViewHierarchyOptimizer;
import com.facebook.react.uimanager.OnLayoutEvent;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.ShadowNodeRegistry;
import com.facebook.react.uimanager.SizeMonitoringFrameLayout;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIBlock;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.react.uimanager.ViewAtIndex;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.uimanager.ViewManagerRegistry;
import com.facebook.react.uimanager.debug.NotThreadSafeViewHierarchyUpdateDebugListener;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.systrace.Systrace;
import com.facebook.systrace.SystraceMessage;
import com.facebook.yoga.YogaDirection;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

public class UIImplementation {

   protected final EventDispatcher mEventDispatcher;
   private long mLastCalculateLayoutTime;
   @Nullable
   protected UIImplementation.LayoutUpdateListener mLayoutUpdateListener;
   private final int[] mMeasureBuffer;
   private final Set<Integer> mMeasuredRootNodes;
   private final NativeViewHierarchyOptimizer mNativeViewHierarchyOptimizer;
   private final UIViewOperationQueue mOperationsQueue;
   protected final ReactApplicationContext mReactContext;
   protected final ShadowNodeRegistry mShadowNodeRegistry;
   private final ViewManagerRegistry mViewManagers;


   public UIImplementation(ReactApplicationContext var1, UIManagerModule.ViewManagerResolver var2, EventDispatcher var3, int var4) {
      this(var1, new ViewManagerRegistry(var2), var3, var4);
   }

   protected UIImplementation(ReactApplicationContext var1, ViewManagerRegistry var2, UIViewOperationQueue var3, EventDispatcher var4) {
      this.mShadowNodeRegistry = new ShadowNodeRegistry();
      this.mMeasuredRootNodes = new HashSet();
      this.mMeasureBuffer = new int[4];
      this.mLastCalculateLayoutTime = 0L;
      this.mReactContext = var1;
      this.mViewManagers = var2;
      this.mOperationsQueue = var3;
      this.mNativeViewHierarchyOptimizer = new NativeViewHierarchyOptimizer(this.mOperationsQueue, this.mShadowNodeRegistry);
      this.mEventDispatcher = var4;
   }

   private UIImplementation(ReactApplicationContext var1, ViewManagerRegistry var2, EventDispatcher var3, int var4) {
      this(var1, var2, new UIViewOperationQueue(var1, new NativeViewHierarchyManager(var2), var4), var3);
   }

   public UIImplementation(ReactApplicationContext var1, List<ViewManager> var2, EventDispatcher var3, int var4) {
      this(var1, new ViewManagerRegistry(var2), var3, var4);
   }

   private void assertNodeDoesNotNeedCustomLayoutForChildren(ReactShadowNode var1) {
      ViewManager var2 = (ViewManager)Assertions.assertNotNull(this.mViewManagers.get(var1.getViewClass()));
      StringBuilder var3;
      if(var2 instanceof ViewGroupManager) {
         ViewGroupManager var4 = (ViewGroupManager)var2;
         if(var4 != null && var4.needsCustomLayoutForChildren()) {
            var3 = new StringBuilder();
            var3.append("Trying to measure a view using measureLayout/measureLayoutRelativeToParent relative to an ancestor that requires custom layout for it\'s children (");
            var3.append(var1.getViewClass());
            var3.append("). Use measure instead.");
            throw new IllegalViewOperationException(var3.toString());
         }
      } else {
         var3 = new StringBuilder();
         var3.append("Trying to use view ");
         var3.append(var1.getViewClass());
         var3.append(" as a parent, but its Manager doesn\'t extends ViewGroupManager");
         throw new IllegalViewOperationException(var3.toString());
      }
   }

   private void assertViewExists(int var1, String var2) {
      if(this.mShadowNodeRegistry.getNode(var1) == null) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Unable to execute operation ");
         var3.append(var2);
         var3.append(" on view with tag: ");
         var3.append(var1);
         var3.append(", since the view does not exists");
         throw new IllegalViewOperationException(var3.toString());
      }
   }

   private void dispatchViewUpdatesIfNeeded() {
      if(this.mOperationsQueue.isEmpty()) {
         this.dispatchViewUpdates(-1);
      }

   }

   private void measureLayout(int var1, int var2, int[] var3) {
      ReactShadowNode var5 = this.mShadowNodeRegistry.getNode(var1);
      ReactShadowNode var6 = this.mShadowNodeRegistry.getNode(var2);
      StringBuilder var7;
      if(var5 != null && var6 != null) {
         if(var5 != var6) {
            for(ReactShadowNode var4 = var5.getParent(); var4 != var6; var4 = var4.getParent()) {
               if(var4 == null) {
                  var7 = new StringBuilder();
                  var7.append("Tag ");
                  var7.append(var2);
                  var7.append(" is not an ancestor of tag ");
                  var7.append(var1);
                  throw new IllegalViewOperationException(var7.toString());
               }
            }
         }

         this.measureLayoutRelativeToVerifiedAncestor(var5, var6, var3);
      } else {
         var7 = new StringBuilder();
         var7.append("Tag ");
         if(var5 != null) {
            var1 = var2;
         }

         var7.append(var1);
         var7.append(" does not exist");
         throw new IllegalViewOperationException(var7.toString());
      }
   }

   private void measureLayoutRelativeToParent(int var1, int[] var2) {
      ReactShadowNode var3 = this.mShadowNodeRegistry.getNode(var1);
      StringBuilder var5;
      if(var3 == null) {
         var5 = new StringBuilder();
         var5.append("No native view for tag ");
         var5.append(var1);
         var5.append(" exists!");
         throw new IllegalViewOperationException(var5.toString());
      } else {
         ReactShadowNode var4 = var3.getParent();
         if(var4 == null) {
            var5 = new StringBuilder();
            var5.append("View with tag ");
            var5.append(var1);
            var5.append(" doesn\'t have a parent!");
            throw new IllegalViewOperationException(var5.toString());
         } else {
            this.measureLayoutRelativeToVerifiedAncestor(var3, var4, var2);
         }
      }
   }

   private void measureLayoutRelativeToVerifiedAncestor(ReactShadowNode var1, ReactShadowNode var2, int[] var3) {
      int var4;
      int var5;
      if(var1 != var2) {
         var5 = Math.round(var1.getLayoutX());
         var4 = Math.round(var1.getLayoutY());

         for(ReactShadowNode var6 = var1.getParent(); var6 != var2; var6 = var6.getParent()) {
            Assertions.assertNotNull(var6);
            this.assertNodeDoesNotNeedCustomLayoutForChildren(var6);
            var5 += Math.round(var6.getLayoutX());
            var4 += Math.round(var6.getLayoutY());
         }

         this.assertNodeDoesNotNeedCustomLayoutForChildren(var2);
      } else {
         var5 = 0;
         var4 = 0;
      }

      var3[0] = var5;
      var3[1] = var4;
      var3[2] = var1.getScreenWidth();
      var3[3] = var1.getScreenHeight();
   }

   private void notifyOnBeforeLayoutRecursive(ReactShadowNode var1) {
      if(var1.hasUpdates()) {
         for(int var2 = 0; var2 < var1.getChildCount(); ++var2) {
            this.notifyOnBeforeLayoutRecursive(var1.getChildAt(var2));
         }

         var1.onBeforeLayout();
      }
   }

   private void removeShadowNodeRecursive(ReactShadowNode var1) {
      NativeViewHierarchyOptimizer.handleRemoveNode(var1);
      this.mShadowNodeRegistry.removeNode(var1.getReactTag());
      this.mMeasuredRootNodes.remove(Integer.valueOf(var1.getReactTag()));

      for(int var2 = var1.getChildCount() - 1; var2 >= 0; --var2) {
         this.removeShadowNodeRecursive(var1.getChildAt(var2));
      }

      var1.removeAndDisposeAllChildren();
   }

   public void addAnimation(int var1, int var2, Callback var3) {
      this.assertViewExists(var1, "addAnimation");
      this.mOperationsQueue.enqueueAddAnimation(var1, var2, var3);
   }

   public void addUIBlock(UIBlock var1) {
      this.mOperationsQueue.enqueueUIBlock(var1);
   }

   public void applyUpdatesRecursive(ReactShadowNode var1, float var2, float var3) {
      if(var1.hasUpdates()) {
         int var4;
         if(!var1.isVirtualAnchor()) {
            for(var4 = 0; var4 < var1.getChildCount(); ++var4) {
               this.applyUpdatesRecursive(var1.getChildAt(var4), var1.getLayoutX() + var2, var1.getLayoutY() + var3);
            }
         }

         var4 = var1.getReactTag();
         if(!this.mShadowNodeRegistry.isRootNode(var4) && var1.dispatchUpdates(var2, var3, this.mOperationsQueue, this.mNativeViewHierarchyOptimizer) && var1.shouldNotifyOnLayout()) {
            this.mEventDispatcher.dispatchEvent(OnLayoutEvent.obtain(var4, var1.getScreenX(), var1.getScreenY(), var1.getScreenWidth(), var1.getScreenHeight()));
         }

         var1.markUpdateSeen();
      }
   }

   protected void calculateRootLayout(ReactShadowNode var1) {
      SystraceMessage.beginSection(0L, "cssRoot.calculateLayout").arg("rootTag", var1.getReactTag()).flush();
      long var2 = SystemClock.uptimeMillis();

      try {
         var1.calculateLayout();
      } finally {
         Systrace.endSection(0L);
         this.mLastCalculateLayoutTime = SystemClock.uptimeMillis() - var2;
      }

   }

   public void clearJSResponder() {
      this.mOperationsQueue.enqueueClearJSResponder();
   }

   public void configureNextLayoutAnimation(ReadableMap var1, Callback var2, Callback var3) {
      this.mOperationsQueue.enqueueConfigureLayoutAnimation(var1, var2, var3);
   }

   public ReactShadowNode createRootShadowNode() {
      ReactShadowNodeImpl var1 = new ReactShadowNodeImpl();
      if(I18nUtil.getInstance().isRTL(this.mReactContext)) {
         var1.setLayoutDirection(YogaDirection.RTL);
      }

      var1.setViewClassName("Root");
      return var1;
   }

   public ReactShadowNode createShadowNode(String var1) {
      return this.mViewManagers.get(var1).createShadowNodeInstance(this.mReactContext);
   }

   public void createView(int var1, String var2, int var3, ReadableMap var4) {
      ReactShadowNode var5 = this.createShadowNode(var2);
      ReactShadowNode var6 = this.mShadowNodeRegistry.getNode(var3);
      var5.setReactTag(var1);
      var5.setViewClassName(var2);
      var5.setRootNode(var6);
      var5.setThemedContext(var6.getThemedContext());
      this.mShadowNodeRegistry.addNode(var5);
      ReactStylesDiffMap var7;
      if(var4 != null) {
         var7 = new ReactStylesDiffMap(var4);
         var5.updateProperties(var7);
      } else {
         var7 = null;
      }

      this.handleCreateView(var5, var3, var7);
   }

   public void dispatchViewManagerCommand(int var1, int var2, ReadableArray var3) {
      this.assertViewExists(var1, "dispatchViewManagerCommand");
      this.mOperationsQueue.enqueueDispatchCommand(var1, var2, var3);
   }

   public void dispatchViewUpdates(int var1) {
      SystraceMessage.beginSection(0L, "UIImplementation.dispatchViewUpdates").arg("batchId", var1).flush();
      long var2 = SystemClock.uptimeMillis();

      try {
         this.updateViewHierarchy();
         this.mNativeViewHierarchyOptimizer.onBatchComplete();
         this.mOperationsQueue.dispatchViewUpdates(var1, var2, this.mLastCalculateLayoutTime);
      } finally {
         Systrace.endSection(0L);
      }

   }

   public void enableLayoutCalculationForRootNode(int var1) {
      this.mMeasuredRootNodes.add(Integer.valueOf(var1));
   }

   public void findSubviewIn(int var1, float var2, float var3, Callback var4) {
      this.mOperationsQueue.enqueueFindTargetForTouch(var1, var2, var3, var4);
   }

   public Map<String, Long> getProfiledBatchPerfCounters() {
      return this.mOperationsQueue.getProfiledBatchPerfCounters();
   }

   UIViewOperationQueue getUIViewOperationQueue() {
      return this.mOperationsQueue;
   }

   public void handleCreateView(ReactShadowNode var1, int var2, @Nullable ReactStylesDiffMap var3) {
      if(!var1.isVirtual()) {
         this.mNativeViewHierarchyOptimizer.handleCreateView(var1, var1.getThemedContext(), var3);
      }

   }

   public void handleUpdateView(ReactShadowNode var1, String var2, ReactStylesDiffMap var3) {
      if(!var1.isVirtual()) {
         this.mNativeViewHierarchyOptimizer.handleUpdateView(var1, var2, var3);
      }

   }

   public void manageChildren(int var1, @Nullable ReadableArray var2, @Nullable ReadableArray var3, @Nullable ReadableArray var4, @Nullable ReadableArray var5, @Nullable ReadableArray var6) {
      ReactShadowNode var15 = this.mShadowNodeRegistry.getNode(var1);
      int var7;
      if(var2 == null) {
         var7 = 0;
      } else {
         var7 = var2.size();
      }

      int var9;
      if(var4 == null) {
         var9 = 0;
      } else {
         var9 = var4.size();
      }

      int var8;
      if(var6 == null) {
         var8 = 0;
      } else {
         var8 = var6.size();
      }

      if(var7 != 0 && (var3 == null || var7 != var3.size())) {
         throw new IllegalViewOperationException("Size of moveFrom != size of moveTo!");
      } else if(var9 != 0 && (var5 == null || var9 != var5.size())) {
         throw new IllegalViewOperationException("Size of addChildTags != size of addAtIndices!");
      } else {
         ViewAtIndex[] var16 = new ViewAtIndex[var7 + var9];
         int[] var17 = new int[var7 + var8];
         int[] var18 = new int[var17.length];
         int[] var13 = new int[var8];
         int[] var14 = var13;
         int var10;
         int var11;
         int var12;
         if(var7 > 0) {
            Assertions.assertNotNull(var2);
            Assertions.assertNotNull(var3);
            var10 = 0;

            while(true) {
               var14 = var13;
               if(var10 >= var7) {
                  break;
               }

               var11 = var2.getInt(var10);
               var12 = var15.getChildAt(var11).getReactTag();
               var16[var10] = new ViewAtIndex(var12, var3.getInt(var10));
               var17[var10] = var11;
               var18[var10] = var12;
               ++var10;
            }
         }

         if(var9 > 0) {
            Assertions.assertNotNull(var4);
            Assertions.assertNotNull(var5);

            for(var10 = 0; var10 < var9; ++var10) {
               var16[var7 + var10] = new ViewAtIndex(var4.getInt(var10), var5.getInt(var10));
            }
         }

         if(var8 > 0) {
            Assertions.assertNotNull(var6);

            for(var9 = 0; var9 < var8; ++var9) {
               var10 = var6.getInt(var9);
               var11 = var15.getChildAt(var10).getReactTag();
               var12 = var7 + var9;
               var17[var12] = var10;
               var18[var12] = var11;
               var14[var9] = var11;
            }
         }

         Arrays.sort(var16, ViewAtIndex.COMPARATOR);
         Arrays.sort(var17);
         var7 = var17.length - 1;

         for(var8 = -1; var7 >= 0; --var7) {
            if(var17[var7] == var8) {
               StringBuilder var19 = new StringBuilder();
               var19.append("Repeated indices in Removal list for view tag: ");
               var19.append(var1);
               throw new IllegalViewOperationException(var19.toString());
            }

            var15.removeChildAt(var17[var7]);
            var8 = var17[var7];
         }

         for(var1 = 0; var1 < var16.length; ++var1) {
            ViewAtIndex var20 = var16[var1];
            ReactShadowNode var21 = this.mShadowNodeRegistry.getNode(var20.mTag);
            if(var21 == null) {
               StringBuilder var22 = new StringBuilder();
               var22.append("Trying to add unknown view tag: ");
               var22.append(var20.mTag);
               throw new IllegalViewOperationException(var22.toString());
            }

            var15.addChildAt(var21, var20.mIndex);
         }

         if(!var15.isVirtual() && !var15.isVirtualAnchor()) {
            this.mNativeViewHierarchyOptimizer.handleManageChildren(var15, var17, var18, var16, var14);
         }

         for(var1 = 0; var1 < var14.length; ++var1) {
            this.removeShadowNode(this.mShadowNodeRegistry.getNode(var14[var1]));
         }

      }
   }

   public void measure(int var1, Callback var2) {
      this.mOperationsQueue.enqueueMeasure(var1, var2);
   }

   public void measureInWindow(int var1, Callback var2) {
      this.mOperationsQueue.enqueueMeasureInWindow(var1, var2);
   }

   public void measureLayout(int var1, int var2, Callback var3, Callback var4) {
      try {
         this.measureLayout(var1, var2, this.mMeasureBuffer);
         var4.invoke(new Object[]{Float.valueOf(PixelUtil.toDIPFromPixel((float)this.mMeasureBuffer[0])), Float.valueOf(PixelUtil.toDIPFromPixel((float)this.mMeasureBuffer[1])), Float.valueOf(PixelUtil.toDIPFromPixel((float)this.mMeasureBuffer[2])), Float.valueOf(PixelUtil.toDIPFromPixel((float)this.mMeasureBuffer[3]))});
      } catch (IllegalViewOperationException var5) {
         var3.invoke(new Object[]{var5.getMessage()});
      }
   }

   public void measureLayoutRelativeToParent(int var1, Callback var2, Callback var3) {
      try {
         this.measureLayoutRelativeToParent(var1, this.mMeasureBuffer);
         var3.invoke(new Object[]{Float.valueOf(PixelUtil.toDIPFromPixel((float)this.mMeasureBuffer[0])), Float.valueOf(PixelUtil.toDIPFromPixel((float)this.mMeasureBuffer[1])), Float.valueOf(PixelUtil.toDIPFromPixel((float)this.mMeasureBuffer[2])), Float.valueOf(PixelUtil.toDIPFromPixel((float)this.mMeasureBuffer[3]))});
      } catch (IllegalViewOperationException var4) {
         var2.invoke(new Object[]{var4.getMessage()});
      }
   }

   public void onHostDestroy() {}

   public void onHostPause() {
      this.mOperationsQueue.pauseFrameCallback();
   }

   public void onHostResume() {
      this.mOperationsQueue.resumeFrameCallback();
   }

   public void prependUIBlock(UIBlock var1) {
      this.mOperationsQueue.prependUIBlock(var1);
   }

   public void profileNextBatch() {
      this.mOperationsQueue.profileNextBatch();
   }

   public void registerAnimation(Animation var1) {
      this.mOperationsQueue.enqueueRegisterAnimation(var1);
   }

   public <T extends SizeMonitoringFrameLayout & MeasureSpecProvider> void registerRootView(T var1, int var2, ThemedReactContext var3) {
      ReactShadowNode var4 = this.createRootShadowNode();
      var4.setReactTag(var2);
      var4.setThemedContext(var3);
      MeasureSpecProvider var5 = (MeasureSpecProvider)var1;
      this.updateRootView(var4, var5.getWidthMeasureSpec(), var5.getHeightMeasureSpec());
      this.mShadowNodeRegistry.addRootNode(var4);
      this.mOperationsQueue.addRootView(var2, var1, var3);
   }

   public void removeAnimation(int var1, int var2) {
      this.assertViewExists(var1, "removeAnimation");
      this.mOperationsQueue.enqueueRemoveAnimation(var2);
   }

   public void removeLayoutUpdateListener() {
      this.mLayoutUpdateListener = null;
   }

   public void removeRootShadowNode(int var1) {
      this.mShadowNodeRegistry.removeRootNode(var1);
   }

   public void removeRootView(int var1) {
      this.removeRootShadowNode(var1);
      this.mOperationsQueue.enqueueRemoveRootView(var1);
   }

   protected final void removeShadowNode(ReactShadowNode var1) {
      this.removeShadowNodeRecursive(var1);
      var1.dispose();
   }

   public void removeSubviewsFromContainerWithID(int var1) {
      ReactShadowNode var3 = this.mShadowNodeRegistry.getNode(var1);
      if(var3 == null) {
         StringBuilder var5 = new StringBuilder();
         var5.append("Trying to remove subviews of an unknown view tag: ");
         var5.append(var1);
         throw new IllegalViewOperationException(var5.toString());
      } else {
         WritableArray var4 = Arguments.createArray();

         for(int var2 = 0; var2 < var3.getChildCount(); ++var2) {
            var4.pushInt(var2);
         }

         this.manageChildren(var1, (ReadableArray)null, (ReadableArray)null, (ReadableArray)null, (ReadableArray)null, var4);
      }
   }

   public void replaceExistingNonRootView(int var1, int var2) {
      if(!this.mShadowNodeRegistry.isRootNode(var1) && !this.mShadowNodeRegistry.isRootNode(var2)) {
         ReactShadowNode var4 = this.mShadowNodeRegistry.getNode(var1);
         StringBuilder var7;
         if(var4 == null) {
            var7 = new StringBuilder();
            var7.append("Trying to replace unknown view tag: ");
            var7.append(var1);
            throw new IllegalViewOperationException(var7.toString());
         } else {
            ReactShadowNode var3 = var4.getParent();
            if(var3 == null) {
               var7 = new StringBuilder();
               var7.append("Node is not attached to a parent: ");
               var7.append(var1);
               throw new IllegalViewOperationException(var7.toString());
            } else {
               var1 = var3.indexOf(var4);
               if(var1 < 0) {
                  throw new IllegalStateException("Didn\'t find child tag in parent");
               } else {
                  WritableArray var8 = Arguments.createArray();
                  var8.pushInt(var2);
                  WritableArray var5 = Arguments.createArray();
                  var5.pushInt(var1);
                  WritableArray var6 = Arguments.createArray();
                  var6.pushInt(var1);
                  this.manageChildren(var3.getReactTag(), (ReadableArray)null, (ReadableArray)null, var8, var5, var6);
               }
            }
         }
      } else {
         throw new IllegalViewOperationException("Trying to add or replace a root tag!");
      }
   }

   public int resolveRootTagFromReactTag(int var1) {
      if(this.mShadowNodeRegistry.isRootNode(var1)) {
         return var1;
      } else {
         ReactShadowNode var2 = this.resolveShadowNode(var1);
         if(var2 != null) {
            return var2.getRootNode().getReactTag();
         } else {
            StringBuilder var3 = new StringBuilder();
            var3.append("Warning : attempted to resolve a non-existent react shadow node. reactTag=");
            var3.append(var1);
            FLog.w("ReactNative", var3.toString());
            return 0;
         }
      }
   }

   public final ReactShadowNode resolveShadowNode(int var1) {
      return this.mShadowNodeRegistry.getNode(var1);
   }

   protected final ViewManager resolveViewManager(String var1) {
      return this.mViewManagers.get(var1);
   }

   public void sendAccessibilityEvent(int var1, int var2) {
      this.mOperationsQueue.enqueueSendAccessibilityEvent(var1, var2);
   }

   public void setChildren(int var1, ReadableArray var2) {
      ReactShadowNode var3 = this.mShadowNodeRegistry.getNode(var1);

      for(var1 = 0; var1 < var2.size(); ++var1) {
         ReactShadowNode var4 = this.mShadowNodeRegistry.getNode(var2.getInt(var1));
         if(var4 == null) {
            StringBuilder var5 = new StringBuilder();
            var5.append("Trying to add unknown view tag: ");
            var5.append(var2.getInt(var1));
            throw new IllegalViewOperationException(var5.toString());
         }

         var3.addChildAt(var4, var1);
      }

      if(!var3.isVirtual() && !var3.isVirtualAnchor()) {
         this.mNativeViewHierarchyOptimizer.handleSetChildren(var3, var2);
      }

   }

   public void setJSResponder(int var1, boolean var2) {
      this.assertViewExists(var1, "setJSResponder");

      ReactShadowNode var3;
      for(var3 = this.mShadowNodeRegistry.getNode(var1); var3.isVirtual() || var3.isLayoutOnly(); var3 = var3.getParent()) {
         ;
      }

      this.mOperationsQueue.enqueueSetJSResponder(var3.getReactTag(), var1, var2);
   }

   public void setLayoutAnimationEnabledExperimental(boolean var1) {
      this.mOperationsQueue.enqueueSetLayoutAnimationEnabled(var1);
   }

   public void setLayoutUpdateListener(UIImplementation.LayoutUpdateListener var1) {
      this.mLayoutUpdateListener = var1;
   }

   public void setViewHierarchyUpdateDebugListener(@Nullable NotThreadSafeViewHierarchyUpdateDebugListener var1) {
      this.mOperationsQueue.setViewHierarchyUpdateDebugListener(var1);
   }

   public void setViewLocalData(int var1, Object var2) {
      ReactShadowNode var3 = this.mShadowNodeRegistry.getNode(var1);
      if(var3 == null) {
         StringBuilder var4 = new StringBuilder();
         var4.append("Attempt to set local data for view with unknown tag: ");
         var4.append(var1);
         FLog.w("ReactNative", var4.toString());
      } else {
         var3.setLocalData(var2);
         this.dispatchViewUpdatesIfNeeded();
      }
   }

   public void showPopupMenu(int var1, ReadableArray var2, Callback var3, Callback var4) {
      this.assertViewExists(var1, "showPopupMenu");
      this.mOperationsQueue.enqueueShowPopupMenu(var1, var2, var3, var4);
   }

   public void synchronouslyUpdateViewOnUIThread(int var1, ReactStylesDiffMap var2) {
      UiThreadUtil.assertOnUiThread();
      this.mOperationsQueue.getNativeViewHierarchyManager().updateProperties(var1, var2);
   }

   public void updateNodeSize(int var1, int var2, int var3) {
      ReactShadowNode var4 = this.mShadowNodeRegistry.getNode(var1);
      if(var4 == null) {
         StringBuilder var5 = new StringBuilder();
         var5.append("Tried to update size of non-existent tag: ");
         var5.append(var1);
         FLog.w("ReactNative", var5.toString());
      } else {
         var4.setStyleWidth((float)var2);
         var4.setStyleHeight((float)var3);
         this.dispatchViewUpdatesIfNeeded();
      }
   }

   public void updateRootView(int var1, int var2, int var3) {
      ReactShadowNode var4 = this.mShadowNodeRegistry.getNode(var1);
      if(var4 == null) {
         StringBuilder var5 = new StringBuilder();
         var5.append("Tried to update non-existent root tag: ");
         var5.append(var1);
         FLog.w("ReactNative", var5.toString());
      } else {
         this.updateRootView(var4, var2, var3);
      }
   }

   public void updateRootView(ReactShadowNode var1, int var2, int var3) {
      int var4 = MeasureSpec.getMode(var2);
      var2 = MeasureSpec.getSize(var2);
      if(var4 != Integer.MIN_VALUE) {
         if(var4 != 0) {
            if(var4 == 1073741824) {
               var1.setStyleWidth((float)var2);
            }
         } else {
            var1.setStyleWidthAuto();
         }
      } else {
         var1.setStyleMaxWidth((float)var2);
      }

      var2 = MeasureSpec.getMode(var3);
      var3 = MeasureSpec.getSize(var3);
      if(var2 != Integer.MIN_VALUE) {
         if(var2 != 0) {
            if(var2 == 1073741824) {
               var1.setStyleHeight((float)var3);
            }
         } else {
            var1.setStyleHeightAuto();
         }
      } else {
         var1.setStyleMaxHeight((float)var3);
      }
   }

   public void updateView(int var1, String var2, ReadableMap var3) {
      if(this.mViewManagers.get(var2) == null) {
         StringBuilder var7 = new StringBuilder();
         var7.append("Got unknown view type: ");
         var7.append(var2);
         throw new IllegalViewOperationException(var7.toString());
      } else {
         ReactShadowNode var4 = this.mShadowNodeRegistry.getNode(var1);
         if(var4 == null) {
            StringBuilder var5 = new StringBuilder();
            var5.append("Trying to update non-existent view with tag ");
            var5.append(var1);
            throw new IllegalViewOperationException(var5.toString());
         } else {
            if(var3 != null) {
               ReactStylesDiffMap var6 = new ReactStylesDiffMap(var3);
               var4.updateProperties(var6);
               this.handleUpdateView(var4, var2, var6);
            }

         }
      }
   }

   public void updateViewHierarchy() {
      // $FF: Couldn't be decompiled
   }

   public void viewIsDescendantOf(int var1, int var2, Callback var3) {
      ReactShadowNode var4 = this.mShadowNodeRegistry.getNode(var1);
      ReactShadowNode var5 = this.mShadowNodeRegistry.getNode(var2);
      if(var4 != null && var5 != null) {
         var3.invoke(new Object[]{Boolean.valueOf(var4.isDescendantOf(var5))});
      } else {
         var3.invoke(new Object[]{Boolean.valueOf(false)});
      }
   }

   public interface LayoutUpdateListener {

      void onLayoutUpdated(ReactShadowNode var1);
   }
}
