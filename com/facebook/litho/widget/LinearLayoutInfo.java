package com.facebook.litho.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.SizeSpec;
import com.facebook.litho.widget.ComponentTreeHolder;
import com.facebook.litho.widget.LayoutInfo;
import com.facebook.litho.widget.LayoutInfoUtils;
import com.facebook.litho.widget.RenderInfo;
import java.util.List;

public class LinearLayoutInfo implements LayoutInfo {

   private static final int MAX_SANE_RANGE = 10;
   private static final int MIN_SANE_RANGE = 2;
   private final LinearLayoutManager mLinearLayoutManager;


   public LinearLayoutInfo(Context var1, int var2, boolean var3) {
      this.mLinearLayoutManager = new LinearLayoutInfo.InternalLinearLayoutManager(var1, var2, var3);
      this.mLinearLayoutManager.setMeasurementCacheEnabled(false);
   }

   public LinearLayoutInfo(LinearLayoutManager var1) {
      this.mLinearLayoutManager = var1;
   }

   public LinearLayoutInfo(ComponentContext var1, int var2, boolean var3) {
      this(var1.getAndroidContext(), var2, var3);
   }

   public int approximateRangeSize(int var1, int var2, int var3, int var4) {
      if(this.mLinearLayoutManager.getOrientation() != 0) {
         var1 = (int)Math.ceil((double)((float)var4 / (float)var2));
      } else {
         var1 = (int)Math.ceil((double)((float)var3 / (float)var1));
      }

      if(var1 < 2) {
         return 2;
      } else {
         var2 = var1;
         if(var1 > 10) {
            var2 = 10;
         }

         return var2;
      }
   }

   public int computeWrappedHeight(int var1, List<ComponentTreeHolder> var2) {
      return LayoutInfoUtils.computeLinearLayoutWrappedHeight(this.mLinearLayoutManager, var1, var2);
   }

   public LinearLayoutInfo.ViewportFiller createViewportFiller(int var1, int var2) {
      return new LinearLayoutInfo.ViewportFiller(var1, var2, this.getScrollDirection());
   }

   public int findFirstFullyVisibleItemPosition() {
      return this.mLinearLayoutManager.findFirstCompletelyVisibleItemPosition();
   }

   public int findFirstVisibleItemPosition() {
      return this.mLinearLayoutManager.findFirstVisibleItemPosition();
   }

   public int findLastFullyVisibleItemPosition() {
      return this.mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
   }

   public int findLastVisibleItemPosition() {
      return this.mLinearLayoutManager.findLastVisibleItemPosition();
   }

   public int getChildHeightSpec(int var1, RenderInfo var2) {
      return this.mLinearLayoutManager.getOrientation() != 0?SizeSpec.makeSizeSpec(0, 0):var1;
   }

   public int getChildWidthSpec(int var1, RenderInfo var2) {
      return this.mLinearLayoutManager.getOrientation() != 0?var1:SizeSpec.makeSizeSpec(0, 0);
   }

   public int getItemCount() {
      return this.mLinearLayoutManager.getItemCount();
   }

   public RecyclerView.LayoutManager getLayoutManager() {
      return this.mLinearLayoutManager;
   }

   public int getScrollDirection() {
      return this.mLinearLayoutManager.getOrientation();
   }

   public void setRenderInfoCollection(LayoutInfo.RenderInfoCollection var1) {}

   static class InternalLinearLayoutManager extends LinearLayoutManager {

      InternalLinearLayoutManager(Context var1, int var2, boolean var3) {
         super(var1, var2, var3);
      }

      public RecyclerView.LayoutParams generateDefaultLayoutParams() {
         return this.getOrientation() == 1?new RecyclerView.LayoutParams(-1, -2):new RecyclerView.LayoutParams(-2, -1);
      }

      public boolean supportsPredictiveItemAnimations() {
         return this.getOrientation() == 0?false:super.supportsPredictiveItemAnimations();
      }
   }

   public static class ViewportFiller implements LayoutInfo.ViewportFiller {

      private int mFill;
      private final int mHeight;
      private final int mOrientation;
      private final int mWidth;


      public ViewportFiller(int var1, int var2, int var3) {
         this.mWidth = var1;
         this.mHeight = var2;
         this.mOrientation = var3;
      }

      public void add(RenderInfo var1, int var2, int var3) {
         int var4 = this.mFill;
         if(this.mOrientation == 1) {
            var2 = var3;
         }

         this.mFill = var4 + var2;
      }

      public int getFill() {
         return this.mFill;
      }

      public boolean wantsMore() {
         int var1;
         if(this.mOrientation == 1) {
            var1 = this.mHeight;
         } else {
            var1 = this.mWidth;
         }

         return this.mFill < var1;
      }
   }
}
