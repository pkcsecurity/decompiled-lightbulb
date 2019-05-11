package com.facebook.litho.widget;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.Diff;
import com.facebook.litho.EventHandler;
import com.facebook.litho.Output;
import com.facebook.litho.Size;
import com.facebook.litho.StateValue;
import com.facebook.litho.annotations.MountSpec;
import com.facebook.litho.annotations.OnBind;
import com.facebook.litho.annotations.OnBoundsDefined;
import com.facebook.litho.annotations.OnCreateInitialState;
import com.facebook.litho.annotations.OnCreateMountContent;
import com.facebook.litho.annotations.OnEvent;
import com.facebook.litho.annotations.OnMeasure;
import com.facebook.litho.annotations.OnMount;
import com.facebook.litho.annotations.OnPrepare;
import com.facebook.litho.annotations.OnUnbind;
import com.facebook.litho.annotations.OnUnmount;
import com.facebook.litho.annotations.OnUpdateState;
import com.facebook.litho.annotations.Param;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.annotations.ShouldAlwaysRemeasure;
import com.facebook.litho.annotations.ShouldUpdate;
import com.facebook.litho.annotations.State;
import com.facebook.litho.widget.Binder;
import com.facebook.litho.widget.LithoRecylerView;
import com.facebook.litho.widget.PTRRefreshEvent;
import com.facebook.litho.widget.ReMeasureEvent;
import com.facebook.litho.widget.Recycler;
import com.facebook.litho.widget.RecyclerEventsController;
import com.facebook.litho.widget.SectionsRecyclerView;
import java.util.Iterator;
import java.util.List;

@MountSpec(
   events = {PTRRefreshEvent.class},
   hasChildLithoViews = true,
   isPureRender = true
)
class RecyclerSpec {

   @PropDefault
   static final int bottomPadding = 0;
   @PropDefault
   static final boolean clipChildren = true;
   @PropDefault
   static final boolean clipToPadding = true;
   @PropDefault
   static final boolean hasFixedSize = true;
   @PropDefault
   static final RecyclerView.ItemAnimator itemAnimator = new RecyclerSpec.NoUpdateItemAnimator();
   @PropDefault
   static final int leftPadding = 0;
   @PropDefault
   static final boolean nestedScrollingEnabled = true;
   @PropDefault
   static final int overScrollMode = 0;
   @PropDefault
   static final boolean pullToRefresh = true;
   @PropDefault
   static final int recyclerViewId = -1;
   @PropDefault
   static final int refreshProgressBarColor = -16777216;
   @PropDefault
   static final int rightPadding = 0;
   @PropDefault
   static final int scrollBarStyle = 0;
   @PropDefault
   static final int topPadding = 0;


   @OnBind
   protected static void onBind(ComponentContext var0, SectionsRecyclerView var1, 
      @Prop(
         optional = true
      ) RecyclerView.ItemAnimator var2, @Prop Binder<RecyclerView> var3, 
      @Prop(
         optional = true
      ) RecyclerEventsController var4, 
      @Prop(
         optional = true,
         varArg = "onScrollListener"
      ) List<RecyclerView.OnScrollListener> var5, 
      @Prop(
         optional = true
      ) SnapHelper var6, 
      @Prop(
         optional = true
      ) boolean var7, 
      @Prop(
         optional = true
      ) LithoRecylerView.TouchInterceptor var8, SwipeRefreshLayout.OnRefreshListener var9, Output<RecyclerView.ItemAnimator> var10) {
      var1.setContentDescription((CharSequence)null);
      if(var7 && var9 != null) {
         var7 = true;
      } else {
         var7 = false;
      }

      var1.setEnabled(var7);
      var1.setOnRefreshListener(var9);
      LithoRecylerView var11 = (LithoRecylerView)var1.getRecyclerView();
      if(var11 == null) {
         throw new IllegalStateException("RecyclerView not found, it should not be removed from SwipeRefreshLayout before unmounting");
      } else {
         var10.set(var11.getItemAnimator());
         if(var2 != itemAnimator) {
            var11.setItemAnimator(var2);
         } else {
            var11.setItemAnimator(new RecyclerSpec.NoUpdateItemAnimator());
         }

         if(var5 != null) {
            Iterator var12 = var5.iterator();

            while(var12.hasNext()) {
               var11.addOnScrollListener((RecyclerView.OnScrollListener)var12.next());
            }
         }

         if(var8 != null) {
            var11.setTouchInterceptor(var8);
         }

         if(var6 != null && var11.getOnFlingListener() == null) {
            var6.attachToRecyclerView(var11);
         }

         var3.bind(var11);
         if(var4 != null) {
            var4.setSectionsRecyclerView(var1);
         }

         if(var1.hasBeenDetachedFromWindow()) {
            var11.requestLayout();
            var1.setHasBeenDetachedFromWindow(false);
         }

      }
   }

   @OnBoundsDefined
   static void onBoundsDefined(ComponentContext var0, ComponentLayout var1, @Prop Binder<RecyclerView> var2) {
      var2.setSize(var1.getWidth(), var1.getHeight());
   }

   @OnCreateInitialState
   protected static void onCreateInitialState(ComponentContext var0, StateValue<Integer> var1) {
      var1.set(Integer.valueOf(0));
   }

   @OnCreateMountContent
   static SectionsRecyclerView onCreateMountContent(Context var0) {
      return new SectionsRecyclerView(var0, new LithoRecylerView(var0));
   }

   @OnMeasure
   static void onMeasure(ComponentContext var0, ComponentLayout var1, int var2, int var3, Size var4, @Prop Binder<RecyclerView> var5) {
      EventHandler var6;
      if(!var5.canMeasure() && !var5.isWrapContent()) {
         var6 = null;
      } else {
         var6 = Recycler.onRemeasure(var0);
      }

      var5.measure(var4, var2, var3, var6);
   }

   @OnMount
   static void onMount(ComponentContext var0, SectionsRecyclerView var1, @Prop Binder<RecyclerView> var2, 
      @Prop(
         optional = true
      ) boolean var3, 
      @Prop(
         optional = true
      ) boolean var4, 
      @Prop(
         optional = true
      ) int var5, 
      @Prop(
         optional = true
      ) int var6, 
      @Prop(
         optional = true
      ) int var7, 
      @Prop(
         optional = true
      ) int var8, 
      @Prop(
         optional = true
      ) boolean var9, 
      @Prop(
         optional = true
      ) boolean var10, 
      @Prop(
         optional = true
      ) int var11, 
      @Prop(
         optional = true
      ) RecyclerView.ItemDecoration var12, 
      @Prop(
         optional = true,
         resType = ResType.COLOR
      ) int var13, 
      @Prop(
         optional = true
      ) boolean var14, 
      @Prop(
         optional = true
      ) boolean var15, 
      @Prop(
         optional = true,
         resType = ResType.DIMEN_SIZE
      ) int var16, 
      @Prop(
         optional = true
      ) @IdRes int var17, 
      @Prop(
         optional = true
      ) int var18, 
      @Prop(
         isCommonProp = true,
         optional = true
      ) CharSequence var19) {
      RecyclerView var20 = var1.getRecyclerView();
      if(var20 == null) {
         throw new IllegalStateException("RecyclerView not found, it should not be removed from SwipeRefreshLayout");
      } else {
         var20.setContentDescription(var19);
         var1.setColorSchemeColors(new int[]{var13});
         var20.setHasFixedSize(var3);
         var20.setClipToPadding(var4);
         var1.setClipToPadding(var4);
         var20.setPadding(var5, var7, var6, var8);
         var20.setClipChildren(var9);
         var1.setClipChildren(var9);
         var20.setNestedScrollingEnabled(var10);
         var1.setNestedScrollingEnabled(var10);
         var20.setScrollBarStyle(var11);
         var20.setHorizontalFadingEdgeEnabled(var14);
         var20.setVerticalFadingEdgeEnabled(var15);
         var20.setFadingEdgeLength(var16);
         var20.setId(var17);
         var20.setOverScrollMode(var18);
         if(var12 != null) {
            var20.addItemDecoration(var12);
         }

         var2.mount(var20);
      }
   }

   @OnPrepare
   static void onPrepare(ComponentContext var0, 
      @Prop(
         optional = true
      ) final EventHandler var1, Output<SwipeRefreshLayout.OnRefreshListener> var2) {
      if(var1 != null) {
         var2.set(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
               Recycler.dispatchPTRRefreshEvent(var1);
            }
         });
      }

   }

   @OnEvent(ReMeasureEvent.class)
   protected static void onRemeasure(ComponentContext var0, @State int var1) {
      Recycler.onUpdateMeasureAsync(var0, var1 + 1);
   }

   @OnUnbind
   static void onUnbind(ComponentContext var0, SectionsRecyclerView var1, @Prop Binder<RecyclerView> var2, 
      @Prop(
         optional = true
      ) RecyclerEventsController var3, 
      @Prop(
         optional = true,
         varArg = "onScrollListener"
      ) List<RecyclerView.OnScrollListener> var4, RecyclerView.ItemAnimator var5) {
      LithoRecylerView var6 = (LithoRecylerView)var1.getRecyclerView();
      if(var6 == null) {
         throw new IllegalStateException("RecyclerView not found, it should not be removed from SwipeRefreshLayout before unmounting");
      } else {
         var6.setItemAnimator(var5);
         var2.unbind(var6);
         if(var3 != null) {
            var3.setSectionsRecyclerView((SectionsRecyclerView)null);
         }

         if(var4 != null) {
            Iterator var7 = var4.iterator();

            while(var7.hasNext()) {
               var6.removeOnScrollListener((RecyclerView.OnScrollListener)var7.next());
            }
         }

         var6.setTouchInterceptor((LithoRecylerView.TouchInterceptor)null);
         var1.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener)null);
      }
   }

   @OnUnmount
   static void onUnmount(ComponentContext var0, SectionsRecyclerView var1, @Prop Binder<RecyclerView> var2, 
      @Prop(
         optional = true
      ) RecyclerView.ItemDecoration var3, 
      @Prop(
         optional = true
      ) SnapHelper var4) {
      RecyclerView var5 = var1.getRecyclerView();
      if(var5 == null) {
         throw new IllegalStateException("RecyclerView not found, it should not be removed from SwipeRefreshLayout before unmounting");
      } else {
         var5.setId(-1);
         if(var3 != null) {
            var5.removeItemDecoration(var3);
         }

         var2.unmount(var5);
         if(var4 != null) {
            var4.attachToRecyclerView((RecyclerView)null);
         }

      }
   }

   @OnUpdateState
   protected static void onUpdateMeasure(@Param int var0, StateValue<Integer> var1) {
      var1.set(Integer.valueOf(var0));
   }

   @ShouldAlwaysRemeasure
   protected static boolean shouldAlwaysRemeasure(@Prop Binder<RecyclerView> var0) {
      return var0.isWrapContent();
   }

   @ShouldUpdate(
      onMount = true
   )
   protected static boolean shouldUpdate(@Prop Diff<Binder<RecyclerView>> var0, 
      @Prop(
         optional = true
      ) Diff<Boolean> var1, 
      @Prop(
         optional = true
      ) Diff<Boolean> var2, 
      @Prop(
         optional = true
      ) Diff<Integer> var3, 
      @Prop(
         optional = true
      ) Diff<Integer> var4, 
      @Prop(
         optional = true
      ) Diff<Integer> var5, 
      @Prop(
         optional = true
      ) Diff<Integer> var6, 
      @Prop(
         optional = true
      ) Diff<Boolean> var7, 
      @Prop(
         optional = true
      ) Diff<Integer> var8, 
      @Prop(
         optional = true
      ) Diff<RecyclerView.ItemDecoration> var9, 
      @Prop(
         optional = true
      ) Diff<Boolean> var10, 
      @Prop(
         optional = true
      ) Diff<Boolean> var11, 
      @Prop(
         optional = true,
         resType = ResType.DIMEN_SIZE
      ) Diff<Integer> var12, @State Diff<Integer> var13) {
      if(((Integer)var13.getPrevious()).intValue() != ((Integer)var13.getNext()).intValue()) {
         return true;
      } else if(var0.getPrevious() != var0.getNext()) {
         return true;
      } else if(!((Boolean)var1.getPrevious()).equals(var1.getNext())) {
         return true;
      } else if(!((Boolean)var2.getPrevious()).equals(var2.getNext())) {
         return true;
      } else if(!((Integer)var3.getPrevious()).equals(var3.getNext())) {
         return true;
      } else if(!((Integer)var4.getPrevious()).equals(var4.getNext())) {
         return true;
      } else if(!((Integer)var5.getPrevious()).equals(var5.getNext())) {
         return true;
      } else if(!((Integer)var6.getPrevious()).equals(var6.getNext())) {
         return true;
      } else if(!((Boolean)var7.getPrevious()).equals(var7.getNext())) {
         return true;
      } else if(!((Integer)var8.getPrevious()).equals(var8.getNext())) {
         return true;
      } else if(!((Boolean)var10.getPrevious()).equals(var10.getNext())) {
         return true;
      } else if(!((Boolean)var11.getPrevious()).equals(var11.getNext())) {
         return true;
      } else if(!((Integer)var12.getPrevious()).equals(var12.getNext())) {
         return true;
      } else {
         RecyclerView.ItemDecoration var15 = (RecyclerView.ItemDecoration)var9.getPrevious();
         RecyclerView.ItemDecoration var16 = (RecyclerView.ItemDecoration)var9.getNext();
         boolean var14;
         if(var15 == null) {
            if(var16 == null) {
               var14 = true;
            } else {
               var14 = false;
            }
         } else {
            var14 = var15.equals(var16);
         }

         return !var14;
      }
   }

   public static class NoUpdateItemAnimator extends DefaultItemAnimator {

      public NoUpdateItemAnimator() {
         this.setSupportsChangeAnimations(false);
      }
   }
}
