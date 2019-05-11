package com.facebook.litho.sections.widget;

import android.support.annotation.IdRes;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.EventHandler;
import com.facebook.litho.StateValue;
import com.facebook.litho.TouchEvent;
import com.facebook.litho.Wrapper;
import com.facebook.litho.annotations.FromTrigger;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateInitialState;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.OnEvent;
import com.facebook.litho.annotations.OnTrigger;
import com.facebook.litho.annotations.OnUpdateState;
import com.facebook.litho.annotations.Param;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.annotations.State;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.sections.BaseLoadEventsHandler;
import com.facebook.litho.sections.LoadEventsHandler;
import com.facebook.litho.sections.Section;
import com.facebook.litho.sections.SectionContext;
import com.facebook.litho.sections.SectionTree;
import com.facebook.litho.sections.config.SectionsConfiguration;
import com.facebook.litho.sections.widget.ListRecyclerConfiguration;
import com.facebook.litho.sections.widget.RecyclerBinderConfiguration;
import com.facebook.litho.sections.widget.RecyclerCollectionComponent;
import com.facebook.litho.sections.widget.RecyclerCollectionEventsController;
import com.facebook.litho.sections.widget.RecyclerConfiguration;
import com.facebook.litho.sections.widget.ScrollEvent;
import com.facebook.litho.sections.widget.SectionBinderTarget;
import com.facebook.litho.widget.Binder;
import com.facebook.litho.widget.LithoRecylerView;
import com.facebook.litho.widget.PTRRefreshEvent;
import com.facebook.litho.widget.Recycler;
import com.facebook.litho.widget.RecyclerBinder;
import com.facebook.litho.widget.RecyclerEventsController;
import com.facebook.litho.widget.ViewportInfo;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaEdge;
import com.facebook.yoga.YogaPositionType;
import java.util.List;

@LayoutSpec(
   events = {PTRRefreshEvent.class}
)
public class RecyclerCollectionComponentSpec {

   private static final int MIN_SCROLL_FOR_PAGE = 20;
   @PropDefault
   protected static final boolean asyncPropUpdates = SectionsConfiguration.sectionComponentsAsyncPropUpdates;
   @PropDefault
   protected static final boolean asyncStateUpdates = SectionsConfiguration.sectionComponentsAsyncStateUpdates;
   @PropDefault
   public static final boolean clipChildren = true;
   @PropDefault
   public static final boolean clipToPadding = true;
   @PropDefault
   public static final RecyclerView.ItemAnimator itemAnimator = new RecyclerCollectionComponentSpec.NoUpdateItemAnimator();
   @PropDefault
   public static final boolean nestedScrollingEnabled = true;
   @PropDefault
   public static final int overScrollMode = 0;
   @PropDefault
   public static final RecyclerConfiguration recyclerConfiguration = new ListRecyclerConfiguration();
   @PropDefault
   public static final int recyclerViewId = -1;
   @PropDefault
   public static final int refreshProgressBarColor = -12425294;
   @PropDefault
   public static final int scrollBarStyle = 0;
   @PropDefault
   protected static final boolean setRootAsync = ComponentsConfiguration.setRootAsyncRecyclerCollectionComponent;


   @OnCreateInitialState
   static void createInitialState(ComponentContext var0, @Prop Section var1, 
      @Prop(
         optional = true
      ) RecyclerConfiguration var2, 
      @Prop(
         optional = true
      ) RecyclerCollectionEventsController var3, 
      @Prop(
         optional = true
      ) boolean var4, 
      @Prop(
         optional = true
      ) boolean var5, 
      @Prop(
         optional = true
      ) boolean var6, 
      @Prop(
         optional = true
      ) boolean var7, 
      @Prop(
         optional = true
      ) String var8, 
      @Prop(
         optional = true
      ) boolean var9, StateValue<SnapHelper> var10, StateValue<SectionTree> var11, StateValue<RecyclerCollectionComponentSpec.RecyclerCollectionLoadEventsHandler> var12, StateValue<Binder<RecyclerView>> var13, StateValue<RecyclerCollectionComponentSpec.LoadingState> var14, StateValue<RecyclerCollectionEventsController> var15) {
      String var20;
      SectionTree.Builder var21;
      SectionBinderTarget var22;
      label20: {
         RecyclerBinderConfiguration var16 = var2.getRecyclerBinderConfiguration();
         var22 = new SectionBinderTarget((new RecyclerBinder.Builder()).layoutInfo(var2.getLayoutInfo(var0)).rangeRatio(var16.getRangeRatio()).layoutHandlerFactory(var16.getLayoutHandlerFactory()).wrapContent(var16.isWrapContent()).enableStableIds(var16.getEnableStableIds()).invalidStateLogParamsList(var16.getInvalidStateLogParamsList()).useSharedLayoutStateFuture(var16.getUseSharedLayoutStateFuture()).threadPoolConfig(var16.getThreadPoolConfiguration()).asyncInitRange(var16.getAsyncInitRange()).hscrollAsyncMode(var16.getHScrollAsyncMode()).canPrefetchDisplayLists(var16.canPrefetchDisplayLists()).isCircular(var16.isCircular()).hasDynamicItemHeight(var16.hasDynamicItemHeight()).splitLayoutTag(var16.getSplitLayoutTag()).build(var0), var16.getUseBackgroundChangeSets());
         SectionContext var17 = new SectionContext(var0);
         var13.set(var22);
         var10.set(var2.getSnapHelper());
         var21 = SectionTree.create(var17, var22);
         if(var8 != null) {
            var20 = var8;
            if(!var8.equals("")) {
               break label20;
            }
         }

         var20 = var1.getSimpleName();
      }

      final SectionTree var19 = var21.tag(var20).asyncPropUpdates(var4).asyncStateUpdates(var5).forceSyncStateUpdates(var6).build();
      var11.set(var19);
      if(var3 == null) {
         var3 = new RecyclerCollectionEventsController();
      }

      var3.setSectionTree(var19);
      var3.setSnapMode(var2.getSnapMode());
      var15.set(var3);
      RecyclerCollectionComponentSpec.RecyclerCollectionLoadEventsHandler var18 = new RecyclerCollectionComponentSpec.RecyclerCollectionLoadEventsHandler(var0, var3, var7, null);
      var12.set(var18);
      var19.setLoadEventsHandler(var18);
      var22.setViewportChangedListener(new ViewportInfo.ViewportChanged() {
         public void viewportChanged(int var1, int var2, int var3, int var4, int var5) {
            var19.viewPortChanged(var1, var2, var3, var4, var5);
         }
      });
      var22.setCanMeasure(var9);
      if(var7) {
         var14.set(RecyclerCollectionComponentSpec.LoadingState.LOADED);
      } else {
         var14.set(RecyclerCollectionComponentSpec.LoadingState.LOADING);
      }
   }

   @OnCreateLayout
   static Component onCreateLayout(ComponentContext var0, @Prop Section var1, 
      @Prop(
         optional = true
      ) Component var2, 
      @Prop(
         optional = true
      ) Component var3, 
      @Prop(
         optional = true
      ) Component var4, 
      @Prop(
         optional = true,
         varArg = "onScrollListener"
      ) List<RecyclerView.OnScrollListener> var5, 
      @Prop(
         optional = true
      ) LoadEventsHandler var6, 
      @Prop(
         optional = true
      ) boolean var7, 
      @Prop(
         optional = true
      ) boolean var8, 
      @Prop(
         optional = true
      ) boolean var9, 
      @Prop(
         optional = true
      ) int var10, 
      @Prop(
         optional = true
      ) RecyclerView.ItemDecoration var11, 
      @Prop(
         optional = true
      ) RecyclerView.ItemAnimator var12, 
      @Prop(
         optional = true
      ) @IdRes int var13, 
      @Prop(
         optional = true
      ) int var14, 
      @Prop(
         optional = true,
         resType = ResType.DIMEN_SIZE
      ) int var15, 
      @Prop(
         optional = true,
         resType = ResType.DIMEN_SIZE
      ) int var16, 
      @Prop(
         optional = true,
         resType = ResType.DIMEN_SIZE
      ) int var17, 
      @Prop(
         optional = true,
         resType = ResType.DIMEN_SIZE
      ) int var18, 
      @Prop(
         optional = true
      ) EventHandler<TouchEvent> var19, 
      @Prop(
         optional = true
      ) boolean var20, 
      @Prop(
         optional = true
      ) boolean var21, 
      @Prop(
         optional = true,
         resType = ResType.DIMEN_SIZE
      ) int var22, 
      @Prop(
         optional = true,
         resType = ResType.COLOR
      ) int var23, 
      @Prop(
         optional = true
      ) LithoRecylerView.TouchInterceptor var24, 
      @Prop(
         optional = true
      ) boolean var25, 
      @Prop(
         optional = true
      ) boolean var26, 
      @Prop(
         optional = true
      ) RecyclerConfiguration var27, 
      @State(
         canUpdateLazily = true
      ) boolean var28, @State RecyclerCollectionEventsController var29, @State RecyclerCollectionComponentSpec.LoadingState var30, @State Binder<RecyclerView> var31, @State SectionTree var32, @State RecyclerCollectionComponentSpec.RecyclerCollectionLoadEventsHandler var33, @State SnapHelper var34) {
      var33.setLoadEventsHandler(var6);
      boolean var37 = true;
      if(var28 && var25) {
         var32.setRootAsync(var1);
      } else {
         RecyclerCollectionComponent.lazyUpdateHasSetSectionTreeRoot(var0, true);
         var32.setRoot(var1);
      }

      boolean var35;
      if(var30 == RecyclerCollectionComponentSpec.LoadingState.ERROR && var4 == null) {
         var35 = true;
      } else {
         var35 = false;
      }

      boolean var36;
      if(var30 == RecyclerCollectionComponentSpec.LoadingState.EMPTY && var3 == null) {
         var36 = true;
      } else {
         var36 = false;
      }

      if(!var36 && !var35) {
         var35 = false;
      } else {
         var35 = true;
      }

      if(var35) {
         return null;
      } else {
         if(var27.getOrientation() != 0 && !var26) {
            var25 = var37;
         } else {
            var25 = false;
         }

         Recycler.Builder var41 = Recycler.create(var0).clipToPadding(var7).leftPadding(var15).rightPadding(var16).topPadding(var17).bottomPadding(var18).clipChildren(var8).nestedScrollingEnabled(var9).scrollBarStyle(var10).recyclerViewId(var13).overScrollMode(var14).recyclerEventsController(var29);
         EventHandler var38;
         if(!var25) {
            var38 = null;
         } else {
            var38 = RecyclerCollectionComponent.onRefresh(var0, var32);
         }

         Recycler.Builder var39 = var41.refreshHandler(var38).pullToRefresh(var25).itemDecoration(var11).horizontalFadingEdgeEnabled(var20).verticalFadingEdgeEnabled(var21).fadingEdgeLengthDip((float)var22).onScrollListener(new RecyclerCollectionComponentSpec.RecyclerCollectionOnScrollListener(var29, null)).onScrollListeners(var5).refreshProgressBarColor(var23).snapHelper(var34).touchInterceptor(var24).binder(var31);
         if(itemAnimator == var12) {
            var12 = new RecyclerCollectionComponentSpec.NoUpdateItemAnimator();
         }

         var39 = (Recycler.Builder)((Recycler.Builder)var39.itemAnimator((RecyclerView.ItemAnimator)var12).flexShrink(0.0F)).touchHandler(var19);
         if(!var31.canMeasure() && !var27.getRecyclerBinderConfiguration().isWrapContent()) {
            ((Recycler.Builder)var39.positionType(YogaPositionType.ABSOLUTE)).positionPx(YogaEdge.ALL, 0);
         }

         Column.Builder var40 = ((Column.Builder)Column.create(var0).flexShrink(0.0F)).alignContent(YogaAlign.FLEX_START).child((Component.Builder)var39);
         if(var30 == RecyclerCollectionComponentSpec.LoadingState.LOADING && var2 != null) {
            var40.child(((Wrapper.Builder)((Wrapper.Builder)Wrapper.create(var0).delegate(var2).flexShrink(0.0F)).positionType(YogaPositionType.ABSOLUTE)).positionPx(YogaEdge.ALL, 0));
         } else if(var30 == RecyclerCollectionComponentSpec.LoadingState.EMPTY) {
            var40.child(((Wrapper.Builder)((Wrapper.Builder)Wrapper.create(var0).delegate(var3).flexShrink(0.0F)).positionType(YogaPositionType.ABSOLUTE)).positionPx(YogaEdge.ALL, 0));
         } else if(var30 == RecyclerCollectionComponentSpec.LoadingState.ERROR) {
            var40.child(((Wrapper.Builder)((Wrapper.Builder)Wrapper.create(var0).delegate(var4).flexShrink(0.0F)).positionType(YogaPositionType.ABSOLUTE)).positionPx(YogaEdge.ALL, 0));
         }

         return var40.build();
      }
   }

   @OnEvent(PTRRefreshEvent.class)
   protected static boolean onRefresh(ComponentContext var0, @Param SectionTree var1, 
      @Prop(
         optional = true
      ) boolean var2) {
      EventHandler var3 = RecyclerCollectionComponent.getPTRRefreshEventHandler(var0);
      if(var2 && var3 != null) {
         if(!RecyclerCollectionComponent.dispatchPTRRefreshEvent(var3).booleanValue()) {
            var1.refresh();
         }

         return true;
      } else {
         var1.refresh();
         return true;
      }
   }

   @OnTrigger(ScrollEvent.class)
   static void onScroll(ComponentContext var0, @FromTrigger int var1, @FromTrigger boolean var2, @State SectionTree var3) {
      var3.requestFocusOnRoot(var1);
   }

   @OnUpdateState
   static void updateLoadingState(StateValue<RecyclerCollectionComponentSpec.LoadingState> var0, @Param RecyclerCollectionComponentSpec.LoadingState var1) {
      var0.set(var1);
   }

   static class RecyclerCollectionLoadEventsHandler extends BaseLoadEventsHandler {

      private final ComponentContext mComponentContext;
      private LoadEventsHandler mDelegate;
      private final boolean mIgnoreLoadingUpdates;
      private RecyclerCollectionComponentSpec.LoadingState mLastState;
      private final RecyclerEventsController mRecyclerEventsController;


      private RecyclerCollectionLoadEventsHandler(ComponentContext var1, RecyclerEventsController var2, boolean var3) {
         this.mLastState = RecyclerCollectionComponentSpec.LoadingState.LOADING;
         this.mComponentContext = var1;
         this.mRecyclerEventsController = var2;
         this.mIgnoreLoadingUpdates = var3;
      }

      // $FF: synthetic method
      RecyclerCollectionLoadEventsHandler(ComponentContext var1, RecyclerEventsController var2, boolean var3, Object var4) {
         this(var1, var2, var3);
      }

      private void updateState(RecyclerCollectionComponentSpec.LoadingState param1) {
         // $FF: Couldn't be decompiled
      }

      public void onInitialLoad() {
         LoadEventsHandler var1 = this.mDelegate;
         if(var1 != null) {
            var1.onInitialLoad();
         }

      }

      public void onLoadFailed(boolean var1) {
         RecyclerCollectionComponentSpec.LoadingState var2;
         if(var1) {
            var2 = RecyclerCollectionComponentSpec.LoadingState.ERROR;
         } else {
            var2 = RecyclerCollectionComponentSpec.LoadingState.LOADED;
         }

         this.updateState(var2);
         this.mRecyclerEventsController.clearRefreshing();
         LoadEventsHandler var3 = this.mDelegate;
         if(var3 != null) {
            var3.onLoadFailed(var1);
         }

      }

      public void onLoadStarted(boolean var1) {
         RecyclerCollectionComponentSpec.LoadingState var2;
         if(var1) {
            var2 = RecyclerCollectionComponentSpec.LoadingState.LOADING;
         } else {
            var2 = RecyclerCollectionComponentSpec.LoadingState.LOADED;
         }

         this.updateState(var2);
         LoadEventsHandler var3 = this.mDelegate;
         if(var3 != null) {
            var3.onLoadStarted(var1);
         }

      }

      public void onLoadSucceeded(boolean var1) {
         RecyclerCollectionComponentSpec.LoadingState var2;
         if(var1) {
            var2 = RecyclerCollectionComponentSpec.LoadingState.EMPTY;
         } else {
            var2 = RecyclerCollectionComponentSpec.LoadingState.LOADED;
         }

         this.updateState(var2);
         this.mRecyclerEventsController.clearRefreshing();
         LoadEventsHandler var3 = this.mDelegate;
         if(var3 != null) {
            var3.onLoadSucceeded(var1);
         }

      }

      public void setLoadEventsHandler(LoadEventsHandler var1) {
         this.mDelegate = var1;
      }
   }

   public static class NoUpdateItemAnimator extends DefaultItemAnimator {

      public NoUpdateItemAnimator() {
         this.setSupportsChangeAnimations(false);
      }
   }

   @VisibleForTesting
   public static enum LoadingState {

      // $FF: synthetic field
      private static final RecyclerCollectionComponentSpec.LoadingState[] $VALUES = new RecyclerCollectionComponentSpec.LoadingState[]{LOADING, LOADED, EMPTY, ERROR};
      EMPTY("EMPTY", 2),
      ERROR("ERROR", 3),
      LOADED("LOADED", 1),
      LOADING("LOADING", 0);


      private LoadingState(String var1, int var2) {}
   }

   static class RecyclerCollectionOnScrollListener extends RecyclerView.OnScrollListener {

      private final RecyclerCollectionEventsController mEventsController;


      private RecyclerCollectionOnScrollListener(RecyclerCollectionEventsController var1) {
         this.mEventsController = var1;
      }

      // $FF: synthetic method
      RecyclerCollectionOnScrollListener(RecyclerCollectionEventsController var1, Object var2) {
         this(var1);
      }

      public void onScrolled(RecyclerView var1, int var2, int var3) {
         super.onScrolled(var1, var2, var3);
         this.mEventsController.updateFirstLastFullyVisibleItemPositions(var1.getLayoutManager());
      }
   }
}
