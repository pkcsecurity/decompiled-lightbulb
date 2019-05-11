package com.facebook.litho.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.util.Pools;
import android.view.ViewTreeObserver;
import android.view.View.MeasureSpec;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.HorizontalScrollView;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.ComponentTree;
import com.facebook.litho.LithoView;
import com.facebook.litho.Output;
import com.facebook.litho.Size;
import com.facebook.litho.SizeSpec;
import com.facebook.litho.StateValue;
import com.facebook.litho.annotations.FromMeasure;
import com.facebook.litho.annotations.MountSpec;
import com.facebook.litho.annotations.OnBoundsDefined;
import com.facebook.litho.annotations.OnCreateInitialState;
import com.facebook.litho.annotations.OnCreateMountContent;
import com.facebook.litho.annotations.OnMeasure;
import com.facebook.litho.annotations.OnMount;
import com.facebook.litho.annotations.OnUnmount;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropDefault;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.annotations.State;
import com.facebook.litho.widget.HorizontalScrollEventsController;
import com.facebook.yoga.YogaDirection;

@MountSpec
class HorizontalScrollSpec {

   private static final int LAST_SCROLL_POSITION_UNSET = -1;
   private static final Pools.SynchronizedPool<Size> sSizePool = new Pools.SynchronizedPool(2);
   @PropDefault
   static final boolean scrollbarEnabled = true;


   private static Size acquireSize() {
      Size var1 = (Size)sSizePool.acquire();
      Size var0 = var1;
      if(var1 == null) {
         var0 = new Size();
      }

      return var0;
   }

   @OnBoundsDefined
   static void onBoundsDefined(ComponentContext var0, ComponentLayout var1, @Prop Component var2, @FromMeasure Integer var3, @FromMeasure Integer var4, Output<Integer> var5, Output<Integer> var6, Output<YogaDirection> var7) {
      if(var3 != null && var4 != null) {
         var5.set(var3);
         var6.set(var4);
      } else {
         Size var10 = acquireSize();
         var2.measure(var0, SizeSpec.makeSizeSpec(0, 0), SizeSpec.makeSizeSpec(var1.getHeight(), 1073741824), var10);
         int var8 = var10.width;
         int var9 = var10.height;
         releaseSize(var10);
         var5.set(Integer.valueOf(var8));
         var6.set(Integer.valueOf(var9));
      }

      var7.set(var1.getResolvedLayoutDirection());
   }

   @OnCreateInitialState
   static void onCreateInitialState(ComponentContext var0, StateValue<HorizontalScrollSpec.ScrollPosition> var1, 
      @Prop(
         optional = true
      ) Integer var2) {
      int var3;
      if(var2 == null) {
         var3 = -1;
      } else {
         var3 = var2.intValue();
      }

      var1.set(new HorizontalScrollSpec.ScrollPosition(var3));
   }

   @OnCreateMountContent
   static HorizontalScrollSpec.HorizontalScrollLithoView onCreateMountContent(Context var0) {
      return new HorizontalScrollSpec.HorizontalScrollLithoView(var0);
   }

   static void onLoadStyle(ComponentContext var0, Output<Boolean> var1) {
      TypedArray var6 = var0.obtainStyledAttributes(com.facebook.litho.R.HorizontalScroll, 0);
      int var3 = var6.getIndexCount();

      for(int var2 = 0; var2 < var3; ++var2) {
         int var4 = var6.getIndex(var2);
         if(var4 == com.facebook.litho.R.HorizontalScroll_android_scrollbars) {
            boolean var5;
            if(var6.getInt(var4, 0) != 0) {
               var5 = true;
            } else {
               var5 = false;
            }

            var1.set(Boolean.valueOf(var5));
         }
      }

      var6.recycle();
   }

   @OnMeasure
   static void onMeasure(ComponentContext var0, ComponentLayout var1, int var2, int var3, Size var4, @Prop Component var5, Output<Integer> var6, Output<Integer> var7) {
      Size var9 = acquireSize();
      var5.measure(var0, SizeSpec.makeSizeSpec(0, 0), var3, var9);
      var3 = var9.width;
      int var8 = var9.height;
      releaseSize(var9);
      var6.set(Integer.valueOf(var3));
      var7.set(Integer.valueOf(var8));
      if(SizeSpec.getMode(var2) == 0) {
         var2 = var3;
      } else {
         var2 = SizeSpec.getSize(var2);
      }

      var4.width = var2;
      var4.height = var8;
   }

   @OnMount
   static void onMount(ComponentContext var0, final HorizontalScrollSpec.HorizontalScrollLithoView var1, @Prop Component var2, 
      @Prop(
         optional = true,
         resType = ResType.BOOL
      ) boolean var3, 
      @Prop(
         optional = true
      ) HorizontalScrollEventsController var4, @State final HorizontalScrollSpec.ScrollPosition var5, int var6, int var7, final YogaDirection var8) {
      var1.setHorizontalScrollBarEnabled(var3);
      var1.mount(var2, var6, var7);
      ViewTreeObserver var9 = var1.getViewTreeObserver();
      var9.addOnPreDrawListener(new OnPreDrawListener() {
         public boolean onPreDraw() {
            var1.getViewTreeObserver().removeOnPreDrawListener(this);
            if(var5.x == -1) {
               if(var8 == YogaDirection.RTL) {
                  var1.fullScroll(66);
               }

               var5.x = var1.getScrollX();
            } else {
               var1.setScrollX(var5.x);
            }

            return true;
         }
      });
      var9.addOnScrollChangedListener(new OnScrollChangedListener() {
         public void onScrollChanged() {
            var5.x = var1.getScrollX();
         }
      });
      if(var4 != null) {
         var4.setScrollableView(var1);
      }

   }

   @OnUnmount
   static void onUnmount(ComponentContext var0, HorizontalScrollSpec.HorizontalScrollLithoView var1, 
      @Prop(
         optional = true
      ) HorizontalScrollEventsController var2) {
      var1.unmount();
      if(var2 != null) {
         var2.setScrollableView((HorizontalScrollSpec.HorizontalScrollLithoView)null);
      }

   }

   private static void releaseSize(Size var0) {
      sSizePool.release(var0);
   }

   static class HorizontalScrollLithoView extends HorizontalScrollView {

      private int mComponentHeight;
      private int mComponentWidth;
      private final LithoView mLithoView;


      public HorizontalScrollLithoView(Context var1) {
         super(var1);
         this.mLithoView = new LithoView(var1);
         this.addView(this.mLithoView);
      }

      void mount(Component var1, int var2, int var3) {
         if(this.mLithoView.getComponentTree() == null) {
            this.mLithoView.setComponentTree(ComponentTree.create(this.mLithoView.getComponentContext(), var1).incrementalMount(false).build());
         } else {
            this.mLithoView.setComponent(var1);
         }

         this.mComponentWidth = var2;
         this.mComponentHeight = var3;
      }

      protected void onMeasure(int var1, int var2) {
         this.mLithoView.measure(MeasureSpec.makeMeasureSpec(this.mComponentWidth, 1073741824), MeasureSpec.makeMeasureSpec(this.mComponentHeight, 1073741824));
         this.setMeasuredDimension(MeasureSpec.getSize(var1), MeasureSpec.getSize(var2));
      }

      void unmount() {
         this.mLithoView.unbind();
         this.mComponentWidth = 0;
         this.mComponentHeight = 0;
      }
   }

   static class ScrollPosition {

      int x;


      ScrollPosition(int var1) {
         this.x = var1;
      }
   }
}
