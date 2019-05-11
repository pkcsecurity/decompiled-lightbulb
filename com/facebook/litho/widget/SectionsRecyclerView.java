package com.facebook.litho.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentTree;
import com.facebook.litho.HasLithoViewChildren;
import com.facebook.litho.LithoView;
import java.util.List;
import javax.annotation.Nullable;

public class SectionsRecyclerView extends SwipeRefreshLayout implements HasLithoViewChildren {

   private boolean mHasBeenDetachedFromWindow = false;
   private final RecyclerView mRecyclerView;
   private final LithoView mStickyHeader;


   public SectionsRecyclerView(Context var1, RecyclerView var2) {
      super(var1);
      this.mRecyclerView = var2;
      this.mRecyclerView.setChildDrawingOrderCallback(new RecyclerView.ChildDrawingOrderCallback() {
         public int onGetChildDrawingOrder(int var1, int var2) {
            return var1 - 1 - var2;
         }
      });
      this.mRecyclerView.setItemViewCacheSize(0);
      this.addView(this.mRecyclerView);
      this.mStickyHeader = new LithoView(new ComponentContext(this.getContext()), (AttributeSet)null);
      this.mStickyHeader.setLayoutParams(new LayoutParams(-1, -2));
      this.addView(this.mStickyHeader);
   }

   @Nullable
   static SectionsRecyclerView getParentRecycler(RecyclerView var0) {
      return var0.getParent() instanceof SectionsRecyclerView?(SectionsRecyclerView)var0.getParent():null;
   }

   private void measureStickyHeader(int var1) {
      this.measureChild(this.mStickyHeader, MeasureSpec.makeMeasureSpec(var1, 1073741824), 0);
   }

   public RecyclerView getRecyclerView() {
      return this.mRecyclerView;
   }

   public LithoView getStickyHeader() {
      return this.mStickyHeader;
   }

   boolean hasBeenDetachedFromWindow() {
      return this.mHasBeenDetachedFromWindow;
   }

   public void hideStickyHeader() {
      this.mStickyHeader.unmountAllItems();
      this.mStickyHeader.setVisibility(8);
   }

   public boolean isLayoutRequested() {
      return this.getParent() != null?this.getParent().isLayoutRequested() || super.isLayoutRequested():super.isLayoutRequested();
   }

   public boolean isStickyHeaderHidden() {
      return this.mStickyHeader.getVisibility() == 8;
   }

   public void obtainLithoViewChildren(List<LithoView> var1) {
      int var3 = this.mRecyclerView.getChildCount();

      for(int var2 = 0; var2 < var3; ++var2) {
         View var4 = this.mRecyclerView.getChildAt(var2);
         if(var4 instanceof LithoView) {
            var1.add((LithoView)var4);
         }
      }

   }

   protected void onDetachedFromWindow() {
      super.onDetachedFromWindow();
      this.mHasBeenDetachedFromWindow = true;
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      super.onLayout(var1, var2, var3, var4, var5);
      if(this.mStickyHeader.getVisibility() != 8) {
         var2 = this.getPaddingLeft();
         var3 = this.getPaddingTop();
         this.mStickyHeader.layout(var2, var3, this.mStickyHeader.getMeasuredWidth() + var2, this.mStickyHeader.getMeasuredHeight() + var3);
      }
   }

   public void onMeasure(int var1, int var2) {
      super.onMeasure(var1, var2);
      this.measureStickyHeader(MeasureSpec.getSize(var1));
   }

   public void requestDisallowInterceptTouchEvent(boolean var1) {
      super.requestDisallowInterceptTouchEvent(var1);
      if(this.getParent() != null && !this.isNestedScrollingEnabled()) {
         this.getParent().requestDisallowInterceptTouchEvent(var1);
      }

   }

   void setHasBeenDetachedFromWindow(boolean var1) {
      this.mHasBeenDetachedFromWindow = var1;
   }

   public void setOnTouchListener(OnTouchListener var1) {
      this.mRecyclerView.setOnTouchListener(var1);
   }

   public void setStickyComponent(ComponentTree var1) {
      if(var1.getLithoView() != null) {
         var1.getLithoView().startTemporaryDetach();
      }

      this.mStickyHeader.setComponentTree(var1);
      this.measureStickyHeader(this.getWidth());
   }

   public void setStickyHeaderVerticalOffset(int var1) {
      this.mStickyHeader.setTranslationY((float)var1);
   }

   public void showStickyHeader() {
      this.mStickyHeader.setVisibility(0);
      this.mStickyHeader.performIncrementalMount();
   }
}
