package com.facebook.litho;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.VisibleForTesting;
import android.support.v4.view.accessibility.AccessibilityManagerCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.accessibility.AccessibilityManager;
import com.facebook.litho.AccessibilityUtils;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentHost;
import com.facebook.litho.ComponentLogParams;
import com.facebook.litho.ComponentTree;
import com.facebook.litho.ComponentsLogger;
import com.facebook.litho.ComponentsPools;
import com.facebook.litho.DoubleMeasureFixUtil;
import com.facebook.litho.LayoutState;
import com.facebook.litho.LithoViewTestHelper;
import com.facebook.litho.LogTreePopulator;
import com.facebook.litho.MountState;
import com.facebook.litho.PerfEvent;
import com.facebook.litho.SizeSpec;
import com.facebook.litho.TestItem;
import com.facebook.litho.ThreadUtils;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.proguard.annotations.DoNotStrip;
import java.lang.ref.WeakReference;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public class LithoView extends ComponentHost {

   public static final String SET_ALREADY_ATTACHED_COMPONENT_TREE = "LithoView:SetAlreadyAttachedComponentTree";
   public static final String ZERO_HEIGHT_LOG = "LithoView:0-height";
   private static final int[] sLayoutSize = new int[2];
   private final AccessibilityManager mAccessibilityManager;
   private final LithoView.AccessibilityStateChangeListener mAccessibilityStateChangeListener;
   private int mAnimatedHeight;
   private int mAnimatedWidth;
   private final ComponentContext mComponentContext;
   @Nullable
   private ComponentTree mComponentTree;
   private boolean mDoMeasureInLayout;
   private boolean mForceLayout;
   private boolean mHasNewComponentTree;
   @Nullable
   private Map<String, ComponentLogParams> mInvalidStateLogParams;
   private boolean mIsAttached;
   private boolean mIsMeasuring;
   private final MountState mMountState;
   @Nullable
   private String mNullComponentCause;
   private LithoView.OnDirtyMountListener mOnDirtyMountListener;
   @Nullable
   private LithoView.OnPostDrawListener mOnPostDrawListener;
   @Nullable
   private String mPreviousComponentSimpleName;
   private final Rect mPreviousMountVisibleRectBounds;
   private boolean mSuppressMeasureComponentTree;
   private ComponentTree mTemporaryDetachedComponent;
   private int mTransientStateCount;


   public LithoView(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public LithoView(Context var1, AttributeSet var2) {
      this(new ComponentContext(var1), var2);
   }

   public LithoView(ComponentContext var1) {
      this(var1, (AttributeSet)null);
   }

   public LithoView(ComponentContext var1, AttributeSet var2) {
      super(var1, var2);
      this.mPreviousMountVisibleRectBounds = new Rect();
      this.mIsMeasuring = false;
      this.mHasNewComponentTree = false;
      this.mAnimatedWidth = -1;
      this.mAnimatedHeight = -1;
      this.mOnDirtyMountListener = null;
      this.mOnPostDrawListener = null;
      this.mAccessibilityStateChangeListener = new LithoView.AccessibilityStateChangeListener(this, null);
      this.mComponentContext = var1;
      this.mMountState = new MountState(this);
      this.mAccessibilityManager = (AccessibilityManager)var1.getAndroidContext().getSystemService("accessibility");
   }

   private boolean checkMainThreadLayoutStateForIncrementalMount() {
      if(this.mComponentTree.getMainThreadLayoutState() != null) {
         return true;
      } else if(!this.isLayoutRequested()) {
         throw new RuntimeException("Trying to incrementally mount a component with a null main thread LayoutState on a LithoView that hasn\'t requested layout!");
      } else {
         return false;
      }
   }

   public static LithoView create(Context var0, Component var1) {
      return create(new ComponentContext(var0), var1);
   }

   public static LithoView create(ComponentContext var0, Component var1) {
      LithoView var2 = new LithoView(var0);
      var2.setComponentTree(ComponentTree.create(var0, var1).build());
      return var2;
   }

   private static void logError(ComponentsLogger var0, String var1, ComponentLogParams var2) {
      ComponentsLogger.LogLevel var3;
      if(var2.failHarder) {
         var3 = ComponentsLogger.LogLevel.FATAL;
      } else {
         var3 = ComponentsLogger.LogLevel.ERROR;
      }

      var0.emitMessage(var3, var1, var2.samplingFrequency);
   }

   private void logSetAlreadyAttachedComponentTree(ComponentTree var1, ComponentTree var2, ComponentLogParams var3) {
      ComponentsLogger var4 = this.getComponentContext().getLogger();
      if(var4 != null) {
         StringBuilder var5 = new StringBuilder();
         var5.append(var3.logProductId);
         var5.append("-");
         var5.append("LithoView:SetAlreadyAttachedComponentTree");
         var5.append(", currentView=");
         var5.append(LithoViewTestHelper.toDebugString(var1.getLithoView()));
         var5.append(", newComponent.LV=");
         var5.append(LithoViewTestHelper.toDebugString(var2.getLithoView()));
         var5.append(", currentComponent=");
         var5.append(var1.getSimpleName());
         var5.append(", newComponent=");
         var5.append(var2.getSimpleName());
         logError(var4, var5.toString(), var3);
      }
   }

   private void maybeLogInvalidZeroHeight() {
      ComponentsLogger var4 = this.getComponentContext().getLogger();
      if(var4 != null) {
         if(this.mComponentTree == null || this.mComponentTree.getMainThreadLayoutState() == null || this.mComponentTree.getMainThreadLayoutState().mLayoutRoot != null) {
            ComponentLogParams var2;
            if(this.mInvalidStateLogParams == null) {
               var2 = null;
            } else {
               var2 = (ComponentLogParams)this.mInvalidStateLogParams.get("LithoView:0-height");
            }

            if(var2 != null) {
               LayoutParams var3 = this.getLayoutParams();
               boolean var1;
               if(var3 instanceof LithoView.LayoutManagerOverrideParams && ((LithoView.LayoutManagerOverrideParams)var3).hasValidAdapterPosition()) {
                  var1 = true;
               } else {
                  var1 = false;
               }

               if(!var1) {
                  StringBuilder var5 = new StringBuilder();
                  var5.append(var2.logProductId);
                  var5.append("-");
                  var5.append("LithoView:0-height");
                  var5.append(", current=");
                  String var7;
                  if(this.mComponentTree == null) {
                     StringBuilder var6 = new StringBuilder();
                     var6.append("null_");
                     var6.append(this.mNullComponentCause);
                     var7 = var6.toString();
                  } else {
                     var7 = this.mComponentTree.getSimpleName();
                  }

                  var5.append(var7);
                  var5.append(", previous=");
                  var5.append(this.mPreviousComponentSimpleName);
                  var5.append(", view=");
                  var5.append(LithoViewTestHelper.toDebugString(this));
                  logError(var4, var5.toString(), var2);
               }
            }
         }
      }
   }

   private void maybePerformIncrementalMountOnView() {
      if(this.mComponentTree != null && this.mComponentTree.isIncrementalMountEnabled()) {
         if(this.getParent() instanceof View) {
            int var1 = ((View)this.getParent()).getWidth();
            int var2 = ((View)this.getParent()).getHeight();
            int var3 = (int)this.getTranslationX();
            int var4 = (int)this.getTranslationY();
            int var5 = this.getTop();
            int var6 = this.getBottom();
            int var7 = this.getLeft();
            int var8 = this.getRight();
            if(var7 + var3 < 0 || var5 + var4 < 0 || var8 + var3 > var1 || var6 + var4 > var2 || this.mPreviousMountVisibleRectBounds.width() != this.getWidth() || this.mPreviousMountVisibleRectBounds.height() != this.getHeight()) {
               Rect var9 = ComponentsPools.acquireRect();
               if(!this.getLocalVisibleRect(var9)) {
                  ComponentsPools.release(var9);
               } else {
                  this.performIncrementalMount(var9, true);
                  ComponentsPools.release(var9);
               }
            }
         }
      }
   }

   private void onAttach() {
      if(!this.mIsAttached) {
         this.mIsAttached = true;
         if(this.mComponentTree != null) {
            this.mComponentTree.attach();
         }

         this.refreshAccessibilityDelegatesIfNeeded(AccessibilityUtils.isAccessibilityEnabled(this.getContext()));
         AccessibilityManagerCompat.addAccessibilityStateChangeListener(this.mAccessibilityManager, this.mAccessibilityStateChangeListener);
      }

   }

   private void onDetach() {
      if(this.mIsAttached) {
         this.mIsAttached = false;
         this.mMountState.detach();
         if(this.mComponentTree != null) {
            this.mComponentTree.detach();
         }

         AccessibilityManagerCompat.removeAccessibilityStateChangeListener(this.mAccessibilityManager, this.mAccessibilityStateChangeListener);
         this.mSuppressMeasureComponentTree = false;
      }

   }

   private static void performLayoutOnChildrenIfNecessary(ComponentHost var0) {
      int var2 = var0.getChildCount();

      for(int var1 = 0; var1 < var2; ++var1) {
         View var3 = var0.getChildAt(var1);
         if(var3.isLayoutRequested()) {
            var3.measure(MeasureSpec.makeMeasureSpec(var3.getWidth(), 1073741824), MeasureSpec.makeMeasureSpec(var3.getHeight(), 1073741824));
            var3.layout(var3.getLeft(), var3.getTop(), var3.getRight(), var3.getBottom());
         }

         if(var3 instanceof ComponentHost) {
            performLayoutOnChildrenIfNecessary((ComponentHost)var3);
         }
      }

   }

   void assertNotInMeasure() {
      if(this.mIsMeasuring) {
         throw new RuntimeException("Cannot update ComponentTree while in the middle of measure");
      }
   }

   void clearComponentTree() {
      ThreadUtils.assertMainThread();
      if(this.mIsAttached) {
         throw new IllegalStateException("Trying to clear the ComponentTree while attached.");
      } else {
         this.mComponentTree = null;
         this.mNullComponentCause = "clear_CT";
      }
   }

   public void draw(Canvas var1) {
      ComponentTree var2 = this.getComponentTree();
      PerfEvent var3 = null;
      ComponentsLogger var4;
      if(var2 == null) {
         var4 = null;
      } else {
         var4 = this.getComponentTree().getContext().getLogger();
      }

      if(var4 != null) {
         var3 = LogTreePopulator.populatePerfEventFromLogger(this.getComponentContext(), var4, var4.newPerformanceEvent(17));
      }

      if(var3 != null) {
         this.setPerfEvent(var3);
      }

      super.draw(var1);
      if(this.mOnPostDrawListener != null) {
         if(var3 != null) {
            var3.markerPoint("POST_DRAW_START");
         }

         this.mOnPostDrawListener.onPostDraw();
         if(var3 != null) {
            var3.markerPoint("POST_DRAW_END");
         }
      }

      if(var3 != null) {
         var3.markerAnnotate("root_component", this.getComponentTree().getRoot().getSimpleName());
         var4.logPerfEvent(var3);
      }

   }

   @VisibleForTesting(
      otherwise = 2
   )
   @DoNotStrip
   Deque<TestItem> findTestItems(String var1) {
      return this.mMountState.findTestItems(var1);
   }

   protected void forceRelayout() {
      this.mForceLayout = true;
      this.requestLayout();
   }

   public ComponentContext getComponentContext() {
      return this.mComponentContext;
   }

   @Nullable
   public ComponentTree getComponentTree() {
      return this.mComponentTree;
   }

   MountState getMountState() {
      return this.mMountState;
   }

   public Rect getPreviousMountBounds() {
      return this.mPreviousMountVisibleRectBounds;
   }

   public boolean isIncrementalMountEnabled() {
      return this.mComponentTree != null && this.mComponentTree.isIncrementalMountEnabled();
   }

   boolean isMountStateDirty() {
      return this.mMountState.isDirty();
   }

   void mount(LayoutState var1, Rect var2, boolean var3) {
      int var6 = this.mTransientStateCount;
      boolean var5 = false;
      boolean var4 = var5;
      Rect var8 = var2;
      boolean var7 = var3;
      if(var6 > 0) {
         var4 = var5;
         var8 = var2;
         var7 = var3;
         if(this.mComponentTree != null) {
            var4 = var5;
            var8 = var2;
            var7 = var3;
            if(this.mComponentTree.isIncrementalMountEnabled()) {
               if(!this.mMountState.isDirty()) {
                  return;
               }

               var8 = ComponentsPools.acquireRect();
               var8.set(0, 0, this.getWidth(), this.getHeight());
               var7 = false;
               var4 = true;
            }
         }
      }

      if(var8 == null) {
         this.mPreviousMountVisibleRectBounds.setEmpty();
      } else {
         this.mPreviousMountVisibleRectBounds.set(var8);
      }

      this.mMountState.mount(var1, var8, var7);
      if(var4) {
         ComponentsPools.release(var8);
      }

   }

   boolean mountStateNeedsRemount() {
      return this.mMountState.needsRemount();
   }

   public void offsetLeftAndRight(int var1) {
      super.offsetLeftAndRight(var1);
      this.maybePerformIncrementalMountOnView();
   }

   public void offsetTopAndBottom(int var1) {
      super.offsetTopAndBottom(var1);
      this.maybePerformIncrementalMountOnView();
   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      this.onAttach();
   }

   protected void onDetachedFromWindow() {
      super.onDetachedFromWindow();
      this.onDetach();
   }

   void onDirtyMountComplete() {
      if(this.mOnDirtyMountListener != null) {
         this.mOnDirtyMountListener.onDirtyMount(this);
      }

   }

   public void onFinishTemporaryDetach() {
      super.onFinishTemporaryDetach();
      this.onAttach();
   }

   protected void onMeasure(int var1, int var2) {
      var1 = DoubleMeasureFixUtil.correctWidthSpecForAndroidDoubleMeasureBug(this.getResources(), var1);
      int var3 = this.mAnimatedWidth;
      boolean var8 = true;
      boolean var5;
      if(var3 == -1 && this.mAnimatedHeight == -1) {
         var5 = false;
      } else {
         var5 = true;
      }

      int var4;
      if(this.mAnimatedWidth != -1) {
         var4 = this.mAnimatedWidth;
      } else {
         var4 = this.getWidth();
      }

      if(this.mAnimatedHeight != -1) {
         var3 = this.mAnimatedHeight;
      } else {
         var3 = this.getHeight();
      }

      this.mAnimatedWidth = -1;
      this.mAnimatedHeight = -1;
      if(var5 && !this.isMountStateDirty()) {
         this.setMeasuredDimension(var4, var3);
      } else {
         LayoutParams var11 = this.getLayoutParams();
         int var12 = var1;
         int var6 = var2;
         int var7;
         if(var11 instanceof LithoView.LayoutManagerOverrideParams) {
            LithoView.LayoutManagerOverrideParams var14 = (LithoView.LayoutManagerOverrideParams)var11;
            var12 = var14.getWidthMeasureSpec();
            if(var12 != -1) {
               var1 = var12;
            }

            var7 = var14.getHeightMeasureSpec();
            var12 = var1;
            var6 = var2;
            if(var7 != -1) {
               var6 = var7;
               var12 = var1;
            }
         }

         var7 = MeasureSpec.getSize(var12);
         int var9 = MeasureSpec.getSize(var6);
         if(this.mTemporaryDetachedComponent != null && this.mComponentTree == null) {
            this.setComponentTree(this.mTemporaryDetachedComponent);
            this.mTemporaryDetachedComponent = null;
         }

         if(!this.mForceLayout && SizeSpec.getMode(var12) == 1073741824 && SizeSpec.getMode(var6) == 1073741824) {
            this.mDoMeasureInLayout = true;
            this.setMeasuredDimension(var7, var9);
         } else {
            this.mIsMeasuring = true;
            var1 = var7;
            var2 = var9;
            if(this.mComponentTree != null) {
               var1 = var7;
               var2 = var9;
               if(!this.mSuppressMeasureComponentTree) {
                  boolean var10 = this.mForceLayout;
                  this.mForceLayout = false;
                  this.mComponentTree.measure(var12, var6, sLayoutSize, var10);
                  var1 = sLayoutSize[0];
                  var2 = sLayoutSize[1];
                  this.mDoMeasureInLayout = false;
               }
            }

            if(var2 == 0) {
               this.maybeLogInvalidZeroHeight();
            }

            boolean var13;
            label72: {
               if(!this.mSuppressMeasureComponentTree && this.mComponentTree != null) {
                  var13 = var8;
                  if(!this.mHasNewComponentTree) {
                     break label72;
                  }

                  if(!this.mComponentTree.hasMounted()) {
                     var13 = var8;
                     break label72;
                  }
               }

               var13 = false;
            }

            var12 = var1;
            var6 = var2;
            if(var13) {
               this.mComponentTree.maybeCollectTransitions();
               var4 = this.mComponentTree.getInitialAnimatedLithoViewWidth(var4, this.mHasNewComponentTree);
               if(var4 != -1) {
                  var1 = var4;
               }

               var3 = this.mComponentTree.getInitialAnimatedLithoViewHeight(var3, this.mHasNewComponentTree);
               var12 = var1;
               var6 = var2;
               if(var3 != -1) {
                  var6 = var3;
                  var12 = var1;
               }
            }

            this.setMeasuredDimension(var12, var6);
            this.mHasNewComponentTree = false;
            this.mIsMeasuring = false;
         }
      }
   }

   public void onStartTemporaryDetach() {
      super.onStartTemporaryDetach();
      this.onDetach();
   }

   public void performIncrementalMount() {
      if(this.mComponentTree != null) {
         if(this.mComponentTree.getMainThreadLayoutState() != null) {
            if(this.mComponentTree.isIncrementalMountEnabled()) {
               this.mComponentTree.incrementalMountComponent();
            } else {
               throw new IllegalStateException("To perform incremental mounting, you need first to enable it when creating the ComponentTree.");
            }
         }
      }
   }

   public void performIncrementalMount(Rect var1, boolean var2) {
      if(this.mComponentTree != null) {
         if(this.checkMainThreadLayoutStateForIncrementalMount()) {
            if(this.mComponentTree.isIncrementalMountEnabled()) {
               this.mComponentTree.mountComponent(var1, var2);
            } else {
               throw new IllegalStateException("To perform incremental mounting, you need first to enable it when creating the ComponentTree.");
            }
         }
      }
   }

   protected void performLayout(boolean var1, int var2, int var3, int var4, int var5) {
      if(this.mComponentTree != null) {
         if(this.mComponentTree.isReleased()) {
            throw new IllegalStateException("Trying to layout a LithoView holding onto a released ComponentTree");
         }

         if(this.mDoMeasureInLayout || this.mComponentTree.getMainThreadLayoutState() == null) {
            this.mComponentTree.measure(MeasureSpec.makeMeasureSpec(var4 - var2, 1073741824), MeasureSpec.makeMeasureSpec(var5 - var3, 1073741824), sLayoutSize, false);
            this.mHasNewComponentTree = false;
            this.mDoMeasureInLayout = false;
         }

         var1 = this.mComponentTree.layout();
         if(!var1 && this.isIncrementalMountEnabled()) {
            this.performIncrementalMount();
         }

         if(!var1 || this.shouldAlwaysLayoutChildren()) {
            performLayoutOnChildrenIfNecessary(this);
         }
      }

   }

   void processVisibilityOutputs(LayoutState var1, Rect var2) {
      this.mMountState.processVisibilityOutputs(var1, var2, (PerfEvent)null);
   }

   public void rebind() {
      this.mMountState.rebind();
   }

   public void release() {
      ThreadUtils.assertMainThread();
      if(this.mComponentTree != null) {
         this.mComponentTree.release();
         this.mComponentTree = null;
         this.mNullComponentCause = "release_CT";
      }

   }

   public void rerenderForAccessibility(boolean var1) {
      this.refreshAccessibilityDelegatesIfNeeded(var1);
      this.forceRelayout();
   }

   public void setAnimatedHeight(int var1) {
      this.mAnimatedHeight = var1;
      this.requestLayout();
   }

   public void setAnimatedWidth(int var1) {
      this.mAnimatedWidth = var1;
      this.requestLayout();
   }

   public void setComponent(Component var1) {
      if(this.mComponentTree == null) {
         this.setComponentTree(ComponentTree.create(this.getComponentContext(), var1).build());
      } else {
         this.mComponentTree.setRoot(var1);
      }
   }

   public void setComponentAsync(Component var1) {
      if(this.mComponentTree == null) {
         this.setComponentTree(ComponentTree.create(this.getComponentContext(), var1).build());
      } else {
         this.mComponentTree.setRootAsync(var1);
      }
   }

   public void setComponentTree(@Nullable ComponentTree var1) {
      ThreadUtils.assertMainThread();
      this.assertNotInMeasure();
      Object var3 = null;
      this.mTemporaryDetachedComponent = null;
      if(this.mComponentTree == var1) {
         if(this.mIsAttached) {
            this.rebind();
         }

      } else {
         boolean var2;
         if(this.mComponentTree != null && var1 != null && this.mComponentTree.mId == var1.mId) {
            var2 = false;
         } else {
            var2 = true;
         }

         this.mHasNewComponentTree = var2;
         this.setMountStateDirty();
         if(this.mComponentTree != null) {
            if(ComponentsConfiguration.unmountAllWhenComponentTreeSetToNull && var1 == null) {
               this.unmountAllItems();
            }

            if(this.mInvalidStateLogParams != null) {
               this.mPreviousComponentSimpleName = this.mComponentTree.getSimpleName();
            }

            if(var1 != null && var1.getLithoView() != null && this.mInvalidStateLogParams != null && this.mInvalidStateLogParams.containsKey("LithoView:SetAlreadyAttachedComponentTree")) {
               this.logSetAlreadyAttachedComponentTree(this.mComponentTree, var1, (ComponentLogParams)this.mInvalidStateLogParams.get("LithoView:SetAlreadyAttachedComponentTree"));
            }

            if(this.mIsAttached) {
               this.mComponentTree.detach();
            }

            this.mComponentTree.clearLithoView();
         }

         this.mComponentTree = var1;
         if(this.mComponentTree != null) {
            if(this.mComponentTree.isReleased()) {
               StringBuilder var5 = new StringBuilder();
               var5.append("Setting a released ComponentTree to a LithoView, released component was: ");
               var5.append(this.mComponentTree.getReleasedComponent());
               throw new IllegalStateException(var5.toString());
            }

            this.mComponentTree.setLithoView(this);
            if(this.mIsAttached) {
               this.mComponentTree.attach();
            } else {
               this.requestLayout();
            }
         }

         String var4 = (String)var3;
         if(this.mComponentTree == null) {
            var4 = "set_CT";
         }

         this.mNullComponentCause = var4;
      }
   }

   public void setHasTransientState(boolean var1) {
      if(var1) {
         if(this.mTransientStateCount == 0 && this.mComponentTree != null && this.mComponentTree.isIncrementalMountEnabled()) {
            Rect var2 = ComponentsPools.acquireRect();
            var2.set(0, 0, this.getWidth(), this.getHeight());
            this.performIncrementalMount(var2, false);
            ComponentsPools.release(var2);
         }

         ++this.mTransientStateCount;
      } else {
         --this.mTransientStateCount;
         if(this.mTransientStateCount == 0 && this.mComponentTree != null && this.mComponentTree.isIncrementalMountEnabled()) {
            this.performIncrementalMount();
         }

         if(this.mTransientStateCount < 0) {
            this.mTransientStateCount = 0;
         }
      }

      super.setHasTransientState(var1);
   }

   public void setInvalidStateLogParamsList(@Nullable List<ComponentLogParams> var1) {
      if(var1 == null) {
         this.mInvalidStateLogParams = null;
      } else {
         this.mInvalidStateLogParams = new HashMap();
         int var2 = 0;

         for(int var3 = var1.size(); var2 < var3; ++var2) {
            ComponentLogParams var4 = (ComponentLogParams)var1.get(var2);
            this.mInvalidStateLogParams.put(var4.logType, var4);
         }

      }
   }

   void setMountStateDirty() {
      this.mMountState.setDirty();
      this.mPreviousMountVisibleRectBounds.setEmpty();
   }

   public void setOnDirtyMountListener(LithoView.OnDirtyMountListener var1) {
      this.mOnDirtyMountListener = var1;
   }

   public void setOnPostDrawListener(@Nullable LithoView.OnPostDrawListener var1) {
      this.mOnPostDrawListener = var1;
   }

   public void setTranslationX(float var1) {
      if(var1 != this.getTranslationX()) {
         super.setTranslationX(var1);
         this.maybePerformIncrementalMountOnView();
      }
   }

   public void setTranslationY(float var1) {
      if(var1 != this.getTranslationY()) {
         super.setTranslationY(var1);
         this.maybePerformIncrementalMountOnView();
      }
   }

   public void setVisibilityHint(boolean var1) {
      ThreadUtils.assertMainThread();
      if(this.mComponentTree != null) {
         if(this.mComponentTree.isIncrementalMountEnabled()) {
            if(var1) {
               Rect var2 = ComponentsPools.acquireRect();
               if(this.getLocalVisibleRect(var2)) {
                  this.mComponentTree.processVisibilityOutputs();
               }

               ComponentsPools.release(var2);
            } else {
               this.mMountState.clearVisibilityItems();
            }
         }
      }
   }

   protected boolean shouldAlwaysLayoutChildren() {
      return false;
   }

   protected boolean shouldRequestLayout() {
      return this.mComponentTree != null && this.mComponentTree.isMounting()?false:super.shouldRequestLayout();
   }

   public void startTemporaryDetach() {
      this.mTemporaryDetachedComponent = this.mComponentTree;
   }

   public void suppressMeasureComponentTree(boolean var1) {
      this.mSuppressMeasureComponentTree = var1;
   }

   public String toString() {
      return LithoViewTestHelper.viewToString(this, true);
   }

   public void unbind() {
      this.mMountState.unbind();
   }

   public void unmountAllItems() {
      this.mMountState.unmountAllItems();
      this.mPreviousMountVisibleRectBounds.setEmpty();
   }

   public interface OnDirtyMountListener {

      void onDirtyMount(LithoView var1);
   }

   public interface LayoutManagerOverrideParams {

      int UNINITIALIZED = -1;


      int getHeightMeasureSpec();

      int getWidthMeasureSpec();

      boolean hasValidAdapterPosition();
   }

   public interface OnPostDrawListener {

      void onPostDraw();
   }

   static class AccessibilityStateChangeListener extends AccessibilityManagerCompat.AccessibilityStateChangeListenerCompat {

      private final WeakReference<LithoView> mLithoView;


      private AccessibilityStateChangeListener(LithoView var1) {
         this.mLithoView = new WeakReference(var1);
      }

      // $FF: synthetic method
      AccessibilityStateChangeListener(LithoView var1, Object var2) {
         this(var1);
      }

      public void onAccessibilityStateChanged(boolean var1) {
         AccessibilityUtils.invalidateCachedIsAccessibilityEnabled();
         LithoView var2 = (LithoView)this.mLithoView.get();
         if(var2 != null) {
            var2.rerenderForAccessibility(var1);
         }
      }
   }
}
