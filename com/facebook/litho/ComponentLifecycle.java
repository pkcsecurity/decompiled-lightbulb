package com.facebook.litho;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.util.Pools;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.View;
import com.facebook.infer.annotation.ThreadSafe;
import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.ComponentTree;
import com.facebook.litho.ComponentsPools;
import com.facebook.litho.ComponentsSystrace;
import com.facebook.litho.DefaultMountContentPool;
import com.facebook.litho.Diff;
import com.facebook.litho.DiffNode;
import com.facebook.litho.ErrorEvent;
import com.facebook.litho.EventDispatcher;
import com.facebook.litho.EventHandler;
import com.facebook.litho.EventTrigger;
import com.facebook.litho.EventTriggerTarget;
import com.facebook.litho.InternalNode;
import com.facebook.litho.LayoutState;
import com.facebook.litho.MountContentPool;
import com.facebook.litho.Output;
import com.facebook.litho.PlaceholderComponent;
import com.facebook.litho.Size;
import com.facebook.litho.SizeSpec;
import com.facebook.litho.StateContainer;
import com.facebook.litho.Transition;
import com.facebook.litho.TreeProps;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.yoga.YogaBaselineFunction;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaMeasureOutput;
import com.facebook.yoga.YogaNode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.concurrent.GuardedBy;

public abstract class ComponentLifecycle implements EventDispatcher, EventTriggerTarget {

   private static final int DEFAULT_MAX_PREALLOCATION = 3;
   static final int ERROR_EVENT_HANDLER_ID = "__internalOnErrorHandler".hashCode();
   private static final YogaBaselineFunction sBaselineFunction = new YogaBaselineFunction() {
      public float baseline(YogaNode var1, float var2, float var3) {
         InternalNode var4 = (InternalNode)var1.getData();
         return (float)var4.getRootComponent().onMeasureBaseline(var4.getContext(), (int)var2, (int)var3);
      }
   };
   private static final AtomicInteger sComponentTypeId = new AtomicInteger();
   private static final YogaMeasureFunction sMeasureFunction = new YogaMeasureFunction() {

      private final Pools.SynchronizedPool<Size> mSizePool = new Pools.SynchronizedPool(2);

      private Size acquireSize(int var1) {
         Size var3 = (Size)this.mSizePool.acquire();
         Size var2 = var3;
         if(var3 == null) {
            var2 = new Size();
         }

         var2.width = var1;
         var2.height = var1;
         return var2;
      }
      private void releaseSize(Size var1) {
         this.mSizePool.release(var1);
      }
      @SuppressLint({"WrongCall"})
      public long measure(YogaNode var1, float var2, YogaMeasureMode var3, float var4, YogaMeasureMode var5) {
         InternalNode var11 = (InternalNode)var1.getData();
         DiffNode var15;
         if(var11.areCachedMeasuresValid()) {
            var15 = var11.getDiffNode();
         } else {
            var15 = null;
         }

         Component var12 = var11.getRootComponent();
         boolean var10 = ComponentsSystrace.isTracing();
         int var8 = SizeSpec.makeSizeSpecFromCssSpec(var2, var3);
         int var9 = SizeSpec.makeSizeSpecFromCssSpec(var4, var5);
         StringBuilder var18;
         if(var10) {
            var18 = new StringBuilder();
            var18.append("measure:");
            var18.append(var12.getSimpleName());
            ComponentsSystrace.beginSectionWithArgs(var18.toString()).arg("widthSpec", SizeSpec.toString(var8)).arg("heightSpec", SizeSpec.toString(var9)).arg("componentId", var12.getId()).flush();
         }

         var11.setLastWidthSpec(var8);
         var11.setLastHeightSpec(var9);
         int var6;
         int var7;
         if(!Component.isNestedTree(var12) && !var11.hasNestedTree()) {
            if(var15 != null && var15.getLastWidthSpec() == var8 && var15.getLastHeightSpec() == var9 && !var12.shouldAlwaysRemeasure()) {
               var6 = (int)var15.getLastMeasuredWidth();
               var7 = (int)var15.getLastMeasuredHeight();
            } else {
               Size var17 = this.acquireSize(Integer.MIN_VALUE);

               try {
                  var12.onMeasure(var12.getScopedContext(), var11, var8, var9, var17);
                  if(var17.width < 0 || var17.height < 0) {
                     var18 = new StringBuilder();
                     var18.append("MeasureOutput not set, ComponentLifecycle is: ");
                     var18.append(var12);
                     throw new IllegalStateException(var18.toString());
                  }

                  var6 = var17.width;
                  var7 = var17.height;
                  if(var11.getDiffNode() != null) {
                     var11.getDiffNode().setLastWidthSpec(var8);
                     var11.getDiffNode().setLastHeightSpec(var9);
                     var11.getDiffNode().setLastMeasuredWidth((float)var6);
                     var11.getDiffNode().setLastMeasuredHeight((float)var7);
                  }
               } finally {
                  this.releaseSize(var17);
               }
            }
         } else {
            InternalNode var16 = LayoutState.resolveNestedTree(var11, var8, var9);
            var6 = var16.getWidth();
            var7 = var16.getHeight();
         }

         var11.setLastMeasuredWidth((float)var6);
         var11.setLastMeasuredHeight((float)var7);
         if(var10) {
            ComponentsSystrace.endSection();
         }

         return YogaMeasureOutput.make(var6, var7);
      }
   };
   @GuardedBy
   private static final Map<Object, Integer> sTypeIdByComponentType = new HashMap();
   private final int mTypeId;


   ComponentLifecycle() {
      this((Object)null);
   }

   protected ComponentLifecycle(Object param1) {
      // $FF: Couldn't be decompiled
   }

   private Component createComponentLayout(ComponentContext var1) {
      Component var2;
      if(Component.isLayoutSpecWithSizeSpec((Component)this)) {
         try {
            var2 = this.onCreateLayoutWithSizeSpec(var1, var1.getWidthSpec(), var1.getHeightSpec());
            return var2;
         } catch (Exception var3) {
            dispatchErrorEvent(var1, var3);
         }
      } else {
         try {
            var2 = this.onCreateLayout(var1);
            return var2;
         } catch (Exception var4) {
            dispatchErrorEvent(var1, var4);
         }
      }

      return null;
   }

   public static void dispatchErrorEvent(ComponentContext var0, ErrorEvent var1) {
      EventHandler var2 = var0.getComponentScope().getErrorHandler();
      if(var2 != null) {
         var2.dispatchEvent(var1);
      }

   }

   public static void dispatchErrorEvent(ComponentContext var0, Exception var1) {
      if(ComponentsConfiguration.enableOnErrorHandling) {
         ErrorEvent var2 = new ErrorEvent();
         var2.exception = var1;
         dispatchErrorEvent(var0, var2);
      } else if(var1 instanceof RuntimeException) {
         throw (RuntimeException)var1;
      } else {
         throw new RuntimeException(var1);
      }
   }

   @Nullable
   protected static EventTrigger getEventTrigger(ComponentContext var0, int var1, String var2) {
      if(var0.getComponentScope() == null) {
         return null;
      } else {
         ComponentTree var3 = var0.getComponentTree();
         StringBuilder var4 = new StringBuilder();
         var4.append(var0.getComponentScope().getGlobalKey());
         var4.append(var1);
         var4.append(var2);
         EventTrigger var5 = var3.getEventTrigger(var4.toString());
         return var5 == null?null:var5;
      }
   }

   protected static <E extends Object> EventHandler<E> newEventHandler(Component var0, int var1, Object[] var2) {
      EventHandler var3 = new EventHandler(var0, var1, var2);
      if(var0.getScopedContext() != null && var0.getScopedContext().getComponentTree() != null) {
         var0.getScopedContext().getComponentTree().recordEventHandler(var0, var3);
      }

      return var3;
   }

   protected static <E extends Object> EventHandler<E> newEventHandler(ComponentContext var0, int var1, Object[] var2) {
      EventHandler var3 = var0.newEventHandler(var1, var2);
      if(var0.getComponentTree() != null) {
         var0.getComponentTree().recordEventHandler(var0.getComponentScope(), var3);
      }

      return var3;
   }

   protected static <E extends Object> EventTrigger<E> newEventTrigger(ComponentContext var0, String var1, int var2) {
      return var0.newEventTrigger(var1, var2);
   }

   @Nullable
   public Object acceptTriggerEvent(EventTrigger var1, Object var2, Object[] var3) {
      return null;
   }

   protected final <T extends Object> Diff<T> acquireDiff(T var1, T var2) {
      return ComponentsPools.acquireDiff(var1, var2);
   }

   protected Output acquireOutput() {
      return ComponentsPools.acquireOutput();
   }

   protected void applyPreviousRenderData(ComponentLifecycle.RenderData var1) {}

   void bind(ComponentContext var1, Object var2) {
      var1.enterNoStateUpdatesMethod("bind");
      boolean var3 = ComponentsSystrace.isTracing();
      if(var3) {
         StringBuilder var4 = new StringBuilder();
         var4.append("onBind:");
         var4.append(((Component)this).getSimpleName());
         ComponentsSystrace.beginSection(var4.toString());
      }

      try {
         this.onBind(var1, var2);
      } finally {
         if(var3) {
            ComponentsSystrace.endSection();
         }

      }

      var1.exitNoStateUpdatesMethod();
   }

   public boolean callsShouldUpdateOnMount() {
      return false;
   }

   public boolean canMeasure() {
      return false;
   }

   public boolean canPreallocate() {
      return false;
   }

   public void createInitialState(ComponentContext var1) {}

   InternalNode createLayout(ComponentContext param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   @ThreadSafe(
      enableChecks = false
   )
   public Object createMountContent(Context var1) {
      boolean var2 = ComponentsSystrace.isTracing();
      if(var2) {
         StringBuilder var3 = new StringBuilder();
         var3.append("createMountContent:");
         var3.append(((Component)this).getSimpleName());
         ComponentsSystrace.beginSection(var3.toString());
      }

      Object var6;
      try {
         var6 = this.onCreateMountContent(var1);
      } finally {
         if(var2) {
            ComponentsSystrace.endSection();
         }

      }

      return var6;
   }

   protected void dispatchOnEnteredRange(String var1) {}

   @Nullable
   public Object dispatchOnEvent(EventHandler var1, Object var2) {
      if(ComponentsConfiguration.enableOnErrorHandling && var1.id == ERROR_EVENT_HANDLER_ID) {
         ((Component)this).getErrorHandler().dispatchEvent((ErrorEvent)var2);
      }

      return null;
   }

   protected void dispatchOnExitedRange(String var1) {}

   public int getExtraAccessibilityNodeAt(int var1, int var2) {
      return Integer.MIN_VALUE;
   }

   public int getExtraAccessibilityNodesCount() {
      return 0;
   }

   public ComponentLifecycle.MountType getMountType() {
      return ComponentLifecycle.MountType.NONE;
   }

   protected TreeProps getTreePropsForChildren(ComponentContext var1, TreeProps var2) {
      return var2;
   }

   int getTypeId() {
      return this.mTypeId;
   }

   public boolean hasChildLithoViews() {
      return false;
   }

   public boolean hasState() {
      return false;
   }

   public boolean implementsAccessibility() {
      return false;
   }

   public boolean implementsExtraAccessibilityNodes() {
      return false;
   }

   public boolean isMountSizeDependent() {
      return false;
   }

   public boolean isPureRender() {
      return false;
   }

   void loadStyle(ComponentContext var1) {
      this.onLoadStyle(var1);
   }

   void loadStyle(ComponentContext var1, @AttrRes int var2, @StyleRes int var3) {
      var1.setDefStyle(var2, var3);
      this.onLoadStyle(var1);
      var1.setDefStyle(0, 0);
   }

   void mount(ComponentContext var1, Object var2) {
      var1.enterNoStateUpdatesMethod("mount");
      boolean var3 = ComponentsSystrace.isTracing();
      if(var3) {
         StringBuilder var4 = new StringBuilder();
         var4.append("onMount:");
         var4.append(((Component)this).getSimpleName());
         ComponentsSystrace.beginSection(var4.toString());
      }

      try {
         this.onMount(var1, var2);
      } catch (Exception var7) {
         var1.exitNoStateUpdatesMethod();
         dispatchErrorEvent(var1, var7);
      } finally {
         if(var3) {
            ComponentsSystrace.endSection();
         }

      }

      var1.exitNoStateUpdatesMethod();
   }

   protected boolean needsPreviousRenderData() {
      return false;
   }

   public void onBind(ComponentContext var1, Object var2) {}

   public void onBoundsDefined(ComponentContext var1, ComponentLayout var2) {}

   public Component onCreateLayout(ComponentContext var1) {
      return (Component)(ComponentsConfiguration.usePlaceholderComponent?PlaceholderComponent.createAndBuild():Column.create(var1).build());
   }

   protected Component onCreateLayoutWithSizeSpec(ComponentContext var1, int var2, int var3) {
      return (Component)(ComponentsConfiguration.usePlaceholderComponent?PlaceholderComponent.createAndBuild():Column.create(var1).build());
   }

   public Object onCreateMountContent(Context var1) {
      throw new RuntimeException("Trying to mount a MountSpec that doesn\'t implement @OnCreateMountContent");
   }

   protected MountContentPool onCreateMountContentPool() {
      return new DefaultMountContentPool(this.getClass().getSimpleName(), this.poolSize(), true);
   }

   @Nullable
   protected Transition onCreateTransition(ComponentContext var1) {
      return null;
   }

   protected void onError(ComponentContext var1, Exception var2) {
      throw new RuntimeException(var2);
   }

   public void onLoadStyle(ComponentContext var1) {}

   public void onMeasure(ComponentContext var1, ComponentLayout var2, int var3, int var4, Size var5) {
      StringBuilder var6 = new StringBuilder();
      var6.append("You must override onMeasure() if you return true in canMeasure(), ComponentLifecycle is: ");
      var6.append(this);
      throw new IllegalStateException(var6.toString());
   }

   protected int onMeasureBaseline(ComponentContext var1, int var2, int var3) {
      return var3;
   }

   public void onMount(ComponentContext var1, Object var2) {}

   public void onPopulateAccessibilityNode(View var1, AccessibilityNodeInfoCompat var2) {}

   public void onPopulateExtraAccessibilityNode(AccessibilityNodeInfoCompat var1, int var2, int var3, int var4) {}

   public void onPrepare(ComponentContext var1) {}

   public void onUnbind(ComponentContext var1, Object var2) {}

   public void onUnmount(ComponentContext var1, Object var2) {}

   @ThreadSafe
   public int poolSize() {
      return 3;
   }

   protected void populateTreeProps(TreeProps var1) {}

   @Nullable
   protected ComponentLifecycle.RenderData recordRenderData(ComponentLifecycle.RenderData var1) {
      return null;
   }

   protected void releaseDiff(Diff var1) {
      ComponentsPools.release(var1);
   }

   protected void releaseOutput(Output var1) {
      ComponentsPools.release(var1);
   }

   protected ComponentLayout resolve(ComponentContext var1) {
      return this.createLayout(var1, false);
   }

   public boolean shouldAlwaysRemeasure() {
      return false;
   }

   final boolean shouldComponentUpdate(Component var1, Component var2) {
      return this.isPureRender()?this.shouldUpdate(var1, var2):true;
   }

   public boolean shouldUpdate(Component var1, Component var2) {
      return var1.isEquivalentTo(var2) ^ true;
   }

   public boolean shouldUseDisplayList() {
      return false;
   }

   public void transferState(ComponentContext var1, StateContainer var2) {}

   void unbind(ComponentContext var1, Object var2) {
      this.onUnbind(var1, var2);
   }

   void unmount(ComponentContext var1, Object var2) {
      this.onUnmount(var1, var2);
   }

   static class CreateLayoutException extends RuntimeException {

      CreateLayoutException(Component var1, Throwable var2) {
         super(var1.getSimpleName());
         this.initCause(var2);
         this.setStackTrace(new StackTraceElement[0]);
      }
   }

   public interface TransitionContainer {

      List<Transition> consumeTransitions();
   }

   public interface StateUpdate {

      void updateState(StateContainer var1, Component var2);
   }

   public interface RenderData {
   }

   public static enum MountType {

      // $FF: synthetic field
      private static final ComponentLifecycle.MountType[] $VALUES = new ComponentLifecycle.MountType[]{NONE, DRAWABLE, VIEW};
      DRAWABLE("DRAWABLE", 1),
      NONE("NONE", 0),
      VIEW("VIEW", 2);


      private MountType(String var1, int var2) {}
   }
}
