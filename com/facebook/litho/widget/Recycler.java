package com.facebook.litho.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.Dimension;
import android.support.annotation.IdRes;
import android.support.annotation.Px;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.Pools;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.ComponentLifecycle;
import com.facebook.litho.Diff;
import com.facebook.litho.ErrorEvent;
import com.facebook.litho.EventHandler;
import com.facebook.litho.HasEventDispatcher;
import com.facebook.litho.Output;
import com.facebook.litho.Size;
import com.facebook.litho.StateContainer;
import com.facebook.litho.StateValue;
import com.facebook.litho.annotations.Comparable;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.annotations.State;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.widget.Binder;
import com.facebook.litho.widget.LithoRecylerView;
import com.facebook.litho.widget.PTRRefreshEvent;
import com.facebook.litho.widget.ReMeasureEvent;
import com.facebook.litho.widget.RecyclerEventsController;
import com.facebook.litho.widget.RecyclerSpec;
import com.facebook.litho.widget.SectionsRecyclerView;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public final class Recycler extends Component {

   static final Pools.SynchronizedPool<PTRRefreshEvent> sPTRRefreshEventPool = new Pools.SynchronizedPool(2);
   @Comparable(
      type = 13
   )
   @Prop(
      optional = false,
      resType = ResType.NONE
   )
   Binder<RecyclerView> binder;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   int bottomPadding = 0;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean clipChildren = true;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean clipToPadding = true;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   CharSequence contentDescription;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.DIMEN_SIZE
   )
   int fadingEdgeLength;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean hasFixedSize = true;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean horizontalFadingEdgeEnabled;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   RecyclerView.ItemAnimator itemAnimator;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   RecyclerView.ItemDecoration itemDecoration;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   int leftPadding;
   @Comparable(
      type = 14
   )
   private Recycler.RecyclerStateContainer mStateContainer;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean nestedScrollingEnabled;
   RecyclerView.ItemAnimator oldAnimator;
   SwipeRefreshLayout.OnRefreshListener onRefreshListener;
   @Comparable(
      type = 5
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   List<RecyclerView.OnScrollListener> onScrollListeners;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   int overScrollMode;
   EventHandler pTRRefreshEventHandler;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean pullToRefresh;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   RecyclerEventsController recyclerEventsController;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   @IdRes
   int recyclerViewId;
   @Comparable(
      type = 11
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   EventHandler refreshHandler;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.COLOR
   )
   int refreshProgressBarColor;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   int rightPadding;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   int scrollBarStyle;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   SnapHelper snapHelper;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   int topPadding;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   LithoRecylerView.TouchInterceptor touchInterceptor;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean verticalFadingEdgeEnabled;


   private Recycler() {
      super("Recycler");
      this.itemAnimator = RecyclerSpec.itemAnimator;
      this.leftPadding = 0;
      this.nestedScrollingEnabled = true;
      this.overScrollMode = 0;
      this.pullToRefresh = true;
      this.recyclerViewId = -1;
      this.refreshProgressBarColor = -16777216;
      this.rightPadding = 0;
      this.scrollBarStyle = 0;
      this.topPadding = 0;
      this.mStateContainer = new Recycler.RecyclerStateContainer();
   }

   public static Recycler.Builder create(ComponentContext var0) {
      return create(var0, 0, 0);
   }

   public static Recycler.Builder create(ComponentContext var0, int var1, int var2) {
      Recycler.Builder var3 = new Recycler.Builder();
      var3.init(var0, var1, var2, new Recycler());
      return var3;
   }

   private Recycler.OnUpdateMeasureStateUpdate createOnUpdateMeasureStateUpdate(int var1) {
      return new Recycler.OnUpdateMeasureStateUpdate(var1);
   }

   static Boolean dispatchPTRRefreshEvent(EventHandler var0) {
      PTRRefreshEvent var2 = (PTRRefreshEvent)sPTRRefreshEventPool.acquire();
      PTRRefreshEvent var1 = var2;
      if(var2 == null) {
         var1 = new PTRRefreshEvent();
      }

      Boolean var3 = (Boolean)var0.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(var0, var1);
      sPTRRefreshEventPool.release(var1);
      return var3;
   }

   public static EventHandler getPTRRefreshEventHandler(ComponentContext var0) {
      return var0.getComponentScope() == null?null:((Recycler)var0.getComponentScope()).pTRRefreshEventHandler;
   }

   public static EventHandler<ReMeasureEvent> onRemeasure(ComponentContext var0) {
      return newEventHandler(var0, 946341036, new Object[]{var0});
   }

   private void onRemeasure(HasEventDispatcher var1, ComponentContext var2) {
      RecyclerSpec.onRemeasure(var2, ((Recycler)var1).mStateContainer.measureVersion);
   }

   protected static void onUpdateMeasure(ComponentContext var0, int var1) {
      Component var2 = var0.getComponentScope();
      if(var2 != null) {
         var0.updateStateSync(((Recycler)var2).createOnUpdateMeasureStateUpdate(var1), "Recycler.onUpdateMeasure");
      }
   }

   protected static void onUpdateMeasureAsync(ComponentContext var0, int var1) {
      Component var2 = var0.getComponentScope();
      if(var2 != null) {
         var0.updateStateAsync(((Recycler)var2).createOnUpdateMeasureStateUpdate(var1), "Recycler.onUpdateMeasure");
      }
   }

   protected static void onUpdateMeasureSync(ComponentContext var0, int var1) {
      Component var2 = var0.getComponentScope();
      if(var2 != null) {
         var0.updateStateSync(((Recycler)var2).createOnUpdateMeasureStateUpdate(var1), "Recycler.onUpdateMeasure");
      }
   }

   public boolean callsShouldUpdateOnMount() {
      return true;
   }

   protected boolean canMeasure() {
      return true;
   }

   protected boolean canPreallocate() {
      return false;
   }

   protected void copyInterStageImpl(Component var1) {
      Recycler var2 = (Recycler)var1;
      this.oldAnimator = var2.oldAnimator;
      this.onRefreshListener = var2.onRefreshListener;
   }

   protected void createInitialState(ComponentContext var1) {
      StateValue var2 = new StateValue();
      RecyclerSpec.onCreateInitialState(var1, var2);
      this.mStateContainer.measureVersion = ((Integer)var2.get()).intValue();
   }

   public Object dispatchOnEvent(EventHandler var1, Object var2) {
      int var3 = var1.id;
      if(var3 != -1048037474) {
         if(var3 != 946341036) {
            return null;
         } else {
            ReMeasureEvent var4 = (ReMeasureEvent)var2;
            this.onRemeasure(var1.mHasEventDispatcher, (ComponentContext)var1.params[0]);
            return null;
         }
      } else {
         dispatchErrorEvent((ComponentContext)var1.params[0], (ErrorEvent)var2);
         return null;
      }
   }

   public ComponentLifecycle.MountType getMountType() {
      return ComponentLifecycle.MountType.VIEW;
   }

   protected StateContainer getStateContainer() {
      return this.mStateContainer;
   }

   public boolean hasChildLithoViews() {
      return true;
   }

   protected boolean hasState() {
      return true;
   }

   public boolean isEquivalentTo(Component var1) {
      if(ComponentsConfiguration.useNewIsEquivalentTo) {
         return super.isEquivalentTo(var1);
      } else if(this == var1) {
         return true;
      } else if(var1 != null) {
         if(this.getClass() != var1.getClass()) {
            return false;
         } else {
            Recycler var2 = (Recycler)var1;
            if(this.getId() == var2.getId()) {
               return true;
            } else {
               if(this.binder != null) {
                  if(!this.binder.equals(var2.binder)) {
                     return false;
                  }
               } else if(var2.binder != null) {
                  return false;
               }

               if(this.bottomPadding != var2.bottomPadding) {
                  return false;
               } else if(this.clipChildren != var2.clipChildren) {
                  return false;
               } else if(this.clipToPadding != var2.clipToPadding) {
                  return false;
               } else {
                  if(this.contentDescription != null) {
                     if(!this.contentDescription.equals(var2.contentDescription)) {
                        return false;
                     }
                  } else if(var2.contentDescription != null) {
                     return false;
                  }

                  if(this.fadingEdgeLength != var2.fadingEdgeLength) {
                     return false;
                  } else if(this.hasFixedSize != var2.hasFixedSize) {
                     return false;
                  } else if(this.horizontalFadingEdgeEnabled != var2.horizontalFadingEdgeEnabled) {
                     return false;
                  } else {
                     if(this.itemAnimator != null) {
                        if(!this.itemAnimator.equals(var2.itemAnimator)) {
                           return false;
                        }
                     } else if(var2.itemAnimator != null) {
                        return false;
                     }

                     if(this.itemDecoration != null) {
                        if(!this.itemDecoration.equals(var2.itemDecoration)) {
                           return false;
                        }
                     } else if(var2.itemDecoration != null) {
                        return false;
                     }

                     if(this.leftPadding != var2.leftPadding) {
                        return false;
                     } else if(this.nestedScrollingEnabled != var2.nestedScrollingEnabled) {
                        return false;
                     } else {
                        if(this.onScrollListeners != null) {
                           if(!this.onScrollListeners.equals(var2.onScrollListeners)) {
                              return false;
                           }
                        } else if(var2.onScrollListeners != null) {
                           return false;
                        }

                        if(this.overScrollMode != var2.overScrollMode) {
                           return false;
                        } else if(this.pullToRefresh != var2.pullToRefresh) {
                           return false;
                        } else {
                           if(this.recyclerEventsController != null) {
                              if(!this.recyclerEventsController.equals(var2.recyclerEventsController)) {
                                 return false;
                              }
                           } else if(var2.recyclerEventsController != null) {
                              return false;
                           }

                           if(this.recyclerViewId != var2.recyclerViewId) {
                              return false;
                           } else {
                              if(this.refreshHandler != null) {
                                 if(!this.refreshHandler.isEquivalentTo(var2.refreshHandler)) {
                                    return false;
                                 }
                              } else if(var2.refreshHandler != null) {
                                 return false;
                              }

                              if(this.refreshProgressBarColor != var2.refreshProgressBarColor) {
                                 return false;
                              } else if(this.rightPadding != var2.rightPadding) {
                                 return false;
                              } else if(this.scrollBarStyle != var2.scrollBarStyle) {
                                 return false;
                              } else {
                                 if(this.snapHelper != null) {
                                    if(!this.snapHelper.equals(var2.snapHelper)) {
                                       return false;
                                    }
                                 } else if(var2.snapHelper != null) {
                                    return false;
                                 }

                                 if(this.topPadding != var2.topPadding) {
                                    return false;
                                 } else {
                                    if(this.touchInterceptor != null) {
                                       if(!this.touchInterceptor.equals(var2.touchInterceptor)) {
                                          return false;
                                       }
                                    } else if(var2.touchInterceptor != null) {
                                       return false;
                                    }

                                    return this.verticalFadingEdgeEnabled != var2.verticalFadingEdgeEnabled?false:this.mStateContainer.measureVersion == var2.mStateContainer.measureVersion;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      } else {
         return false;
      }
   }

   public boolean isPureRender() {
      return true;
   }

   public Recycler makeShallowCopy() {
      Recycler var1 = (Recycler)super.makeShallowCopy();
      var1.oldAnimator = null;
      var1.onRefreshListener = null;
      var1.mStateContainer = new Recycler.RecyclerStateContainer();
      return var1;
   }

   protected void onBind(ComponentContext var1, Object var2) {
      Output var3 = this.acquireOutput();
      RecyclerSpec.onBind(var1, (SectionsRecyclerView)var2, this.itemAnimator, this.binder, this.recyclerEventsController, this.onScrollListeners, this.snapHelper, this.pullToRefresh, this.touchInterceptor, this.onRefreshListener, var3);
      this.oldAnimator = (RecyclerView.ItemAnimator)var3.get();
      this.releaseOutput(var3);
   }

   protected void onBoundsDefined(ComponentContext var1, ComponentLayout var2) {
      RecyclerSpec.onBoundsDefined(var1, var2, this.binder);
   }

   protected Object onCreateMountContent(Context var1) {
      return RecyclerSpec.onCreateMountContent(var1);
   }

   protected void onMeasure(ComponentContext var1, ComponentLayout var2, int var3, int var4, Size var5) {
      RecyclerSpec.onMeasure(var1, var2, var3, var4, var5, this.binder);
   }

   protected void onMount(ComponentContext var1, Object var2) {
      RecyclerSpec.onMount(var1, (SectionsRecyclerView)var2, this.binder, this.hasFixedSize, this.clipToPadding, this.leftPadding, this.rightPadding, this.topPadding, this.bottomPadding, this.clipChildren, this.nestedScrollingEnabled, this.scrollBarStyle, this.itemDecoration, this.refreshProgressBarColor, this.horizontalFadingEdgeEnabled, this.verticalFadingEdgeEnabled, this.fadingEdgeLength, this.recyclerViewId, this.overScrollMode, this.contentDescription);
   }

   protected void onPrepare(ComponentContext var1) {
      Output var2 = this.acquireOutput();
      RecyclerSpec.onPrepare(var1, this.refreshHandler, var2);
      this.onRefreshListener = (SwipeRefreshLayout.OnRefreshListener)var2.get();
      this.releaseOutput(var2);
   }

   protected void onUnbind(ComponentContext var1, Object var2) {
      RecyclerSpec.onUnbind(var1, (SectionsRecyclerView)var2, this.binder, this.recyclerEventsController, this.onScrollListeners, this.oldAnimator);
   }

   protected void onUnmount(ComponentContext var1, Object var2) {
      RecyclerSpec.onUnmount(var1, (SectionsRecyclerView)var2, this.binder, this.itemDecoration, this.snapHelper);
   }

   protected int poolSize() {
      return 3;
   }

   protected boolean shouldAlwaysRemeasure() {
      return RecyclerSpec.shouldAlwaysRemeasure(this.binder);
   }

   protected boolean shouldUpdate(Component var1, Component var2) {
      Recycler var6 = (Recycler)var1;
      Recycler var5 = (Recycler)var2;
      Binder var19;
      if(var6 == null) {
         var19 = null;
      } else {
         var19 = var6.binder;
      }

      Binder var20;
      if(var5 == null) {
         var20 = null;
      } else {
         var20 = var5.binder;
      }

      Diff var7 = this.acquireDiff(var19, var20);
      Boolean var21;
      if(var6 == null) {
         var21 = null;
      } else {
         var21 = Boolean.valueOf(var6.hasFixedSize);
      }

      Boolean var22;
      if(var5 == null) {
         var22 = null;
      } else {
         var22 = Boolean.valueOf(var5.hasFixedSize);
      }

      Diff var8 = this.acquireDiff(var21, var22);
      if(var6 == null) {
         var21 = null;
      } else {
         var21 = Boolean.valueOf(var6.clipToPadding);
      }

      if(var5 == null) {
         var22 = null;
      } else {
         var22 = Boolean.valueOf(var5.clipToPadding);
      }

      Diff var9 = this.acquireDiff(var21, var22);
      Integer var23;
      if(var6 == null) {
         var23 = null;
      } else {
         var23 = Integer.valueOf(var6.leftPadding);
      }

      Integer var24;
      if(var5 == null) {
         var24 = null;
      } else {
         var24 = Integer.valueOf(var5.leftPadding);
      }

      Diff var10 = this.acquireDiff(var23, var24);
      if(var6 == null) {
         var23 = null;
      } else {
         var23 = Integer.valueOf(var6.rightPadding);
      }

      if(var5 == null) {
         var24 = null;
      } else {
         var24 = Integer.valueOf(var5.rightPadding);
      }

      Diff var11 = this.acquireDiff(var23, var24);
      if(var6 == null) {
         var23 = null;
      } else {
         var23 = Integer.valueOf(var6.topPadding);
      }

      if(var5 == null) {
         var24 = null;
      } else {
         var24 = Integer.valueOf(var5.topPadding);
      }

      Diff var12 = this.acquireDiff(var23, var24);
      if(var6 == null) {
         var23 = null;
      } else {
         var23 = Integer.valueOf(var6.bottomPadding);
      }

      if(var5 == null) {
         var24 = null;
      } else {
         var24 = Integer.valueOf(var5.bottomPadding);
      }

      Diff var13 = this.acquireDiff(var23, var24);
      if(var6 == null) {
         var21 = null;
      } else {
         var21 = Boolean.valueOf(var6.clipChildren);
      }

      if(var5 == null) {
         var22 = null;
      } else {
         var22 = Boolean.valueOf(var5.clipChildren);
      }

      Diff var14 = this.acquireDiff(var21, var22);
      if(var6 == null) {
         var23 = null;
      } else {
         var23 = Integer.valueOf(var6.scrollBarStyle);
      }

      if(var5 == null) {
         var24 = null;
      } else {
         var24 = Integer.valueOf(var5.scrollBarStyle);
      }

      Diff var15 = this.acquireDiff(var23, var24);
      RecyclerView.ItemDecoration var25;
      if(var6 == null) {
         var25 = null;
      } else {
         var25 = var6.itemDecoration;
      }

      RecyclerView.ItemDecoration var26;
      if(var5 == null) {
         var26 = null;
      } else {
         var26 = var5.itemDecoration;
      }

      Diff var16 = this.acquireDiff(var25, var26);
      if(var6 == null) {
         var21 = null;
      } else {
         var21 = Boolean.valueOf(var6.horizontalFadingEdgeEnabled);
      }

      if(var5 == null) {
         var22 = null;
      } else {
         var22 = Boolean.valueOf(var5.horizontalFadingEdgeEnabled);
      }

      Diff var4 = this.acquireDiff(var21, var22);
      if(var6 == null) {
         var21 = null;
      } else {
         var21 = Boolean.valueOf(var6.verticalFadingEdgeEnabled);
      }

      if(var5 == null) {
         var22 = null;
      } else {
         var22 = Boolean.valueOf(var5.verticalFadingEdgeEnabled);
      }

      Diff var17 = this.acquireDiff(var21, var22);
      if(var6 == null) {
         var23 = null;
      } else {
         var23 = Integer.valueOf(var6.fadingEdgeLength);
      }

      if(var5 == null) {
         var24 = null;
      } else {
         var24 = Integer.valueOf(var5.fadingEdgeLength);
      }

      Diff var18 = this.acquireDiff(var23, var24);
      if(var6 == null) {
         var23 = null;
      } else {
         var23 = Integer.valueOf(var6.mStateContainer.measureVersion);
      }

      if(var5 == null) {
         var24 = null;
      } else {
         var24 = Integer.valueOf(var5.mStateContainer.measureVersion);
      }

      Diff var27 = this.acquireDiff(var23, var24);
      boolean var3 = RecyclerSpec.shouldUpdate(var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var4, var17, var18, var27);
      this.releaseDiff(var7);
      this.releaseDiff(var8);
      this.releaseDiff(var9);
      this.releaseDiff(var10);
      this.releaseDiff(var11);
      this.releaseDiff(var12);
      this.releaseDiff(var13);
      this.releaseDiff(var14);
      this.releaseDiff(var15);
      this.releaseDiff(var16);
      this.releaseDiff(var4);
      this.releaseDiff(var17);
      this.releaseDiff(var18);
      this.releaseDiff(var27);
      return var3;
   }

   protected void transferState(ComponentContext var1, StateContainer var2) {
      Recycler.RecyclerStateContainer var3 = (Recycler.RecyclerStateContainer)var2;
      this.mStateContainer.measureVersion = var3.measureVersion;
   }

   public static class Builder extends Component.Builder<Recycler.Builder> {

      private final int REQUIRED_PROPS_COUNT = 1;
      private final String[] REQUIRED_PROPS_NAMES = new String[]{"binder"};
      ComponentContext mContext;
      Recycler mRecycler;
      private final BitSet mRequired = new BitSet(1);


      private void init(ComponentContext var1, int var2, int var3, Recycler var4) {
         super.init(var1, var2, var3, var4);
         this.mRecycler = var4;
         this.mContext = var1;
         this.mRequired.clear();
      }

      public Recycler.Builder binder(Binder<RecyclerView> var1) {
         this.mRecycler.binder = var1;
         this.mRequired.set(0);
         return this;
      }

      public Recycler.Builder bottomPadding(int var1) {
         this.mRecycler.bottomPadding = var1;
         return this;
      }

      public Recycler build() {
         checkArgs(1, this.mRequired, this.REQUIRED_PROPS_NAMES);
         Recycler var1 = this.mRecycler;
         this.release();
         return var1;
      }

      public Recycler.Builder clipChildren(boolean var1) {
         this.mRecycler.clipChildren = var1;
         return this;
      }

      public Recycler.Builder clipToPadding(boolean var1) {
         this.mRecycler.clipToPadding = var1;
         return this;
      }

      public Recycler.Builder contentDescription(CharSequence var1) {
         super.contentDescription(var1);
         this.mRecycler.contentDescription = var1;
         return this;
      }

      public Recycler.Builder fadingEdgeLengthAttr(@AttrRes int var1) {
         this.mRecycler.fadingEdgeLength = this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public Recycler.Builder fadingEdgeLengthAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mRecycler.fadingEdgeLength = this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public Recycler.Builder fadingEdgeLengthDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mRecycler.fadingEdgeLength = this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public Recycler.Builder fadingEdgeLengthPx(@Px int var1) {
         this.mRecycler.fadingEdgeLength = var1;
         return this;
      }

      public Recycler.Builder fadingEdgeLengthRes(@DimenRes int var1) {
         this.mRecycler.fadingEdgeLength = this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public Recycler.Builder getThis() {
         return this;
      }

      public Recycler.Builder hasFixedSize(boolean var1) {
         this.mRecycler.hasFixedSize = var1;
         return this;
      }

      public Recycler.Builder horizontalFadingEdgeEnabled(boolean var1) {
         this.mRecycler.horizontalFadingEdgeEnabled = var1;
         return this;
      }

      public Recycler.Builder itemAnimator(RecyclerView.ItemAnimator var1) {
         this.mRecycler.itemAnimator = var1;
         return this;
      }

      public Recycler.Builder itemDecoration(RecyclerView.ItemDecoration var1) {
         this.mRecycler.itemDecoration = var1;
         return this;
      }

      public Recycler.Builder leftPadding(int var1) {
         this.mRecycler.leftPadding = var1;
         return this;
      }

      public Recycler.Builder nestedScrollingEnabled(boolean var1) {
         this.mRecycler.nestedScrollingEnabled = var1;
         return this;
      }

      public Recycler.Builder onScrollListener(RecyclerView.OnScrollListener var1) {
         if(var1 == null) {
            return this;
         } else {
            if(this.mRecycler.onScrollListeners == null) {
               this.mRecycler.onScrollListeners = new ArrayList();
            }

            this.mRecycler.onScrollListeners.add(var1);
            return this;
         }
      }

      public Recycler.Builder onScrollListeners(List<RecyclerView.OnScrollListener> var1) {
         if(var1 == null) {
            return this;
         } else if(this.mRecycler.onScrollListeners != null && !this.mRecycler.onScrollListeners.isEmpty()) {
            this.mRecycler.onScrollListeners.addAll(var1);
            return this;
         } else {
            this.mRecycler.onScrollListeners = var1;
            return this;
         }
      }

      public Recycler.Builder overScrollMode(int var1) {
         this.mRecycler.overScrollMode = var1;
         return this;
      }

      public Recycler.Builder pTRRefreshEventHandler(EventHandler var1) {
         this.mRecycler.pTRRefreshEventHandler = var1;
         return this;
      }

      public Recycler.Builder pullToRefresh(boolean var1) {
         this.mRecycler.pullToRefresh = var1;
         return this;
      }

      public Recycler.Builder recyclerEventsController(RecyclerEventsController var1) {
         this.mRecycler.recyclerEventsController = var1;
         return this;
      }

      public Recycler.Builder recyclerViewId(@IdRes int var1) {
         this.mRecycler.recyclerViewId = var1;
         return this;
      }

      public Recycler.Builder refreshHandler(EventHandler var1) {
         this.mRecycler.refreshHandler = var1;
         return this;
      }

      public Recycler.Builder refreshProgressBarColor(@ColorInt int var1) {
         this.mRecycler.refreshProgressBarColor = var1;
         return this;
      }

      public Recycler.Builder refreshProgressBarColorAttr(@AttrRes int var1) {
         this.mRecycler.refreshProgressBarColor = this.mResourceResolver.resolveColorAttr(var1, 0);
         return this;
      }

      public Recycler.Builder refreshProgressBarColorAttr(@AttrRes int var1, @ColorRes int var2) {
         this.mRecycler.refreshProgressBarColor = this.mResourceResolver.resolveColorAttr(var1, var2);
         return this;
      }

      public Recycler.Builder refreshProgressBarColorRes(@ColorRes int var1) {
         this.mRecycler.refreshProgressBarColor = this.mResourceResolver.resolveColorRes(var1);
         return this;
      }

      protected void release() {
         super.release();
         this.mRecycler = null;
         this.mContext = null;
      }

      public Recycler.Builder rightPadding(int var1) {
         this.mRecycler.rightPadding = var1;
         return this;
      }

      public Recycler.Builder scrollBarStyle(int var1) {
         this.mRecycler.scrollBarStyle = var1;
         return this;
      }

      public Recycler.Builder snapHelper(SnapHelper var1) {
         this.mRecycler.snapHelper = var1;
         return this;
      }

      public Recycler.Builder topPadding(int var1) {
         this.mRecycler.topPadding = var1;
         return this;
      }

      public Recycler.Builder touchInterceptor(LithoRecylerView.TouchInterceptor var1) {
         this.mRecycler.touchInterceptor = var1;
         return this;
      }

      public Recycler.Builder verticalFadingEdgeEnabled(boolean var1) {
         this.mRecycler.verticalFadingEdgeEnabled = var1;
         return this;
      }
   }

   static class OnUpdateMeasureStateUpdate implements ComponentLifecycle.StateUpdate {

      private int mMeasureVer;


      OnUpdateMeasureStateUpdate(int var1) {
         this.mMeasureVer = var1;
      }

      public void updateState(StateContainer var1, Component var2) {
         Recycler.RecyclerStateContainer var4 = (Recycler.RecyclerStateContainer)var1;
         Recycler var5 = (Recycler)var2;
         StateValue var3 = new StateValue();
         var3.set(Integer.valueOf(var4.measureVersion));
         RecyclerSpec.onUpdateMeasure(this.mMeasureVer, var3);
         var5.mStateContainer.measureVersion = ((Integer)var3.get()).intValue();
      }
   }

   @VisibleForTesting(
      otherwise = 2
   )
   static class RecyclerStateContainer implements StateContainer {

      @Comparable(
         type = 3
      )
      @State
      int measureVersion;


   }
}
