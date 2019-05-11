package com.facebook.litho.widget;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import com.facebook.litho.LithoView;
import com.facebook.litho.SizeSpec;
import com.facebook.litho.widget.ComponentTreeHolder;
import com.facebook.litho.widget.LayoutInfo;
import com.facebook.litho.widget.LayoutInfoUtils;
import com.facebook.litho.widget.RecyclerBinder;
import com.facebook.litho.widget.RenderInfo;
import com.facebook.litho.widget.StaggeredGridLayoutHelper;
import java.util.List;

public class StaggeredGridLayoutInfo implements LayoutInfo {

   public static final String OVERRIDE_SIZE = "OVERRIDE_SIZE";
   private final StaggeredGridLayoutManager mStaggeredGridLayoutManager;


   public StaggeredGridLayoutInfo(int var1, int var2, boolean var3, int var4) {
      this.mStaggeredGridLayoutManager = new StaggeredGridLayoutInfo.LithoStaggeredGridLayoutManager(var1, var2);
      this.mStaggeredGridLayoutManager.setReverseLayout(var3);
      this.mStaggeredGridLayoutManager.setGapStrategy(var4);
   }

   public int approximateRangeSize(int var1, int var2, int var3, int var4) {
      int var5 = this.mStaggeredGridLayoutManager.getSpanCount();
      return this.mStaggeredGridLayoutManager.getOrientation() != 0?(int)Math.ceil((double)var4 / (double)var2) * var5:(int)Math.ceil((double)var3 / (double)var1) * var5;
   }

   public int computeWrappedHeight(int var1, List<ComponentTreeHolder> var2) {
      int var6 = var2.size();
      int var7 = this.mStaggeredGridLayoutManager.getSpanCount();
      if(this.mStaggeredGridLayoutManager.getOrientation() != 1) {
         throw new IllegalStateException("This method should only be called when orientation is vertical");
      } else {
         int var3 = 0;

         int var4;
         int var5;
         for(var4 = 0; var3 < var6; var3 = var5) {
            var5 = var3 + var7;
            var4 = var4 + LayoutInfoUtils.getMaxHeightInRow(var3, var5, var2) + LayoutInfoUtils.getTopDecorationHeight(this.mStaggeredGridLayoutManager, var3) + LayoutInfoUtils.getBottomDecorationHeight(this.mStaggeredGridLayoutManager, var3);
            if(var4 > var1) {
               return var1;
            }
         }

         return var4;
      }
   }

   public StaggeredGridLayoutInfo.ViewportFiller createViewportFiller(int var1, int var2) {
      return new StaggeredGridLayoutInfo.ViewportFiller(var1, var2, this.getScrollDirection(), this.mStaggeredGridLayoutManager.getSpanCount());
   }

   public int findFirstFullyVisibleItemPosition() {
      return StaggeredGridLayoutHelper.findFirstFullyVisibleItemPosition(this.mStaggeredGridLayoutManager);
   }

   public int findFirstVisibleItemPosition() {
      return StaggeredGridLayoutHelper.findFirstVisibleItemPosition(this.mStaggeredGridLayoutManager);
   }

   public int findLastFullyVisibleItemPosition() {
      return StaggeredGridLayoutHelper.findLastFullyVisibleItemPosition(this.mStaggeredGridLayoutManager);
   }

   public int findLastVisibleItemPosition() {
      return StaggeredGridLayoutHelper.findLastVisibleItemPosition(this.mStaggeredGridLayoutManager);
   }

   public int getChildHeightSpec(int var1, RenderInfo var2) {
      if(this.mStaggeredGridLayoutManager.getOrientation() != 0) {
         return SizeSpec.makeSizeSpec(0, 0);
      } else {
         Integer var5 = (Integer)var2.getCustomAttribute("OVERRIDE_SIZE");
         if(var5 != null) {
            return SizeSpec.makeSizeSpec(var5.intValue(), 1073741824);
         } else {
            int var4 = this.mStaggeredGridLayoutManager.getSpanCount();
            int var3;
            if(var2.isFullSpan()) {
               var3 = this.mStaggeredGridLayoutManager.getSpanCount();
            } else {
               var3 = 1;
            }

            return SizeSpec.makeSizeSpec(var3 * (SizeSpec.getSize(var1) / var4), 1073741824);
         }
      }
   }

   public int getChildWidthSpec(int var1, RenderInfo var2) {
      if(this.mStaggeredGridLayoutManager.getOrientation() != 0) {
         Integer var5 = (Integer)var2.getCustomAttribute("OVERRIDE_SIZE");
         if(var5 != null) {
            return SizeSpec.makeSizeSpec(var5.intValue(), 1073741824);
         } else {
            int var4 = this.mStaggeredGridLayoutManager.getSpanCount();
            int var3;
            if(var2.isFullSpan()) {
               var3 = this.mStaggeredGridLayoutManager.getSpanCount();
            } else {
               var3 = 1;
            }

            return SizeSpec.makeSizeSpec(var3 * (SizeSpec.getSize(var1) / var4), 1073741824);
         }
      } else {
         return SizeSpec.makeSizeSpec(0, 0);
      }
   }

   public int getItemCount() {
      return this.mStaggeredGridLayoutManager.getItemCount();
   }

   public RecyclerView.LayoutManager getLayoutManager() {
      return this.mStaggeredGridLayoutManager;
   }

   public int getScrollDirection() {
      return this.mStaggeredGridLayoutManager.getOrientation();
   }

   public void setRenderInfoCollection(LayoutInfo.RenderInfoCollection var1) {}

   static class ViewportFiller implements LayoutInfo.ViewportFiller {

      private int[] mFills;
      private final int mHeight;
      private int mIndexOfSpan;
      private int mMaxFill;
      private final int mOrientation;
      private final int mSpanCount;
      private final int mWidth;


      public ViewportFiller(int var1, int var2, int var3, int var4) {
         this.mWidth = var1;
         this.mHeight = var2;
         this.mOrientation = var3;
         this.mSpanCount = var4;
         this.mFills = new int[var4];
      }

      public void add(RenderInfo var1, int var2, int var3) {
         int[] var6 = this.mFills;
         int var4 = this.mIndexOfSpan;
         int var5 = var6[var4];
         if(this.mOrientation == 1) {
            var2 = var3;
         }

         var6[var4] = var5 + var2;
         this.mMaxFill = Math.max(this.mMaxFill, this.mFills[this.mIndexOfSpan]);
         var2 = this.mIndexOfSpan + 1;
         this.mIndexOfSpan = var2;
         if(var2 == this.mSpanCount) {
            this.mIndexOfSpan = 0;
         }

      }

      public int getFill() {
         return this.mMaxFill;
      }

      public boolean wantsMore() {
         int var1;
         if(this.mOrientation == 1) {
            var1 = this.mHeight;
         } else {
            var1 = this.mWidth;
         }

         return this.mMaxFill < var1;
      }
   }

   static class LithoStaggeredGridLayoutManager extends StaggeredGridLayoutManager {

      public LithoStaggeredGridLayoutManager(int var1, int var2) {
         super(var1, var2);
      }

      public RecyclerView.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams var1) {
         return (RecyclerView.LayoutParams)(var1 instanceof RecyclerBinder.RecyclerViewLayoutManagerOverrideParams?new StaggeredGridLayoutInfo.LayoutParams((RecyclerBinder.RecyclerViewLayoutManagerOverrideParams)var1):super.generateLayoutParams(var1));
      }
   }

   public static class LayoutParams extends StaggeredGridLayoutManager.LayoutParams implements LithoView.LayoutManagerOverrideParams {

      private final int mOverrideHeightMeasureSpec;
      private final int mOverrideWidthMeasureSpec;


      public LayoutParams(RecyclerBinder.RecyclerViewLayoutManagerOverrideParams var1) {
         super((RecyclerView.LayoutParams)var1);
         this.setFullSpan(var1.isFullSpan());
         this.mOverrideWidthMeasureSpec = var1.getWidthMeasureSpec();
         this.mOverrideHeightMeasureSpec = var1.getHeightMeasureSpec();
      }

      public int getHeightMeasureSpec() {
         return this.mOverrideHeightMeasureSpec;
      }

      public int getWidthMeasureSpec() {
         return this.mOverrideWidthMeasureSpec;
      }

      public boolean hasValidAdapterPosition() {
         return false;
      }
   }
}
