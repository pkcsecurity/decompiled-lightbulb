package com.facebook.litho.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.facebook.litho.LithoView;
import com.facebook.litho.SizeSpec;
import com.facebook.litho.widget.ComponentTreeHolder;
import com.facebook.litho.widget.LayoutInfo;
import com.facebook.litho.widget.LayoutInfoUtils;
import com.facebook.litho.widget.RecyclerBinder;
import com.facebook.litho.widget.RenderInfo;
import java.util.List;

public class GridLayoutInfo implements LayoutInfo {

   public static final String OVERRIDE_SIZE = "OVERRIDE_SIZE";
   private final boolean mAllowGridMeasureOverride;
   private final GridLayoutManager mGridLayoutManager;
   private final GridLayoutInfo.GridSpanSizeLookup mGridSpanSizeLookup;
   private LayoutInfo.RenderInfoCollection mRenderInfoCollection;


   public GridLayoutInfo(Context var1, int var2) {
      this(var1, var2, 1, false);
   }

   public GridLayoutInfo(Context var1, int var2, int var3, boolean var4) {
      this(var1, var2, var3, var4, false);
   }

   public GridLayoutInfo(Context var1, int var2, int var3, boolean var4, boolean var5) {
      this.mAllowGridMeasureOverride = var5;
      Object var6;
      if(this.mAllowGridMeasureOverride) {
         var6 = new GridLayoutManager(var1, var2, var3, var4);
      } else {
         var6 = new GridLayoutInfo.LithoGridLayoutManager(var1, var2, var3, var4);
      }

      this.mGridLayoutManager = (GridLayoutManager)var6;
      this.mGridSpanSizeLookup = new GridLayoutInfo.GridSpanSizeLookup(null);
      this.mGridLayoutManager.setSpanSizeLookup(this.mGridSpanSizeLookup);
   }

   public int approximateRangeSize(int var1, int var2, int var3, int var4) {
      int var5 = this.mGridLayoutManager.getSpanCount();
      return this.mGridLayoutManager.getOrientation() != 0?(int)Math.ceil((double)var4 / (double)var2) * var5:(int)Math.ceil((double)var3 / (double)var1) * var5;
   }

   public int computeWrappedHeight(int var1, List<ComponentTreeHolder> var2) {
      int var5 = var2.size();
      int var6 = this.mGridLayoutManager.getSpanCount();
      if(this.mGridLayoutManager.getOrientation() != 1) {
         throw new IllegalStateException("This method should only be called when orientation is vertical");
      } else {
         int var3 = 0;

         int var4;
         for(var4 = 0; var3 < var5; var3 += var6) {
            var4 = var4 + ((ComponentTreeHolder)var2.get(var3)).getMeasuredHeight() + LayoutInfoUtils.getTopDecorationHeight(this.mGridLayoutManager, var3) + LayoutInfoUtils.getBottomDecorationHeight(this.mGridLayoutManager, var3);
            if(var4 > var1) {
               return var1;
            }
         }

         return var4;
      }
   }

   public GridLayoutInfo.ViewportFiller createViewportFiller(int var1, int var2) {
      return new GridLayoutInfo.ViewportFiller(var1, var2, this.getScrollDirection(), this.mGridLayoutManager.getSpanCount());
   }

   public int findFirstFullyVisibleItemPosition() {
      return this.mGridLayoutManager.findFirstCompletelyVisibleItemPosition();
   }

   public int findFirstVisibleItemPosition() {
      return this.mGridLayoutManager.findFirstVisibleItemPosition();
   }

   public int findLastFullyVisibleItemPosition() {
      return this.mGridLayoutManager.findLastCompletelyVisibleItemPosition();
   }

   public int findLastVisibleItemPosition() {
      return this.mGridLayoutManager.findLastVisibleItemPosition();
   }

   public int getChildHeightSpec(int var1, RenderInfo var2) {
      if(this.mGridLayoutManager.getOrientation() != 0) {
         return SizeSpec.makeSizeSpec(0, 0);
      } else {
         Integer var4 = (Integer)var2.getCustomAttribute("OVERRIDE_SIZE");
         if(var4 != null) {
            return SizeSpec.makeSizeSpec(var4.intValue(), 1073741824);
         } else if(var2.isFullSpan()) {
            return SizeSpec.makeSizeSpec(SizeSpec.getSize(var1), 1073741824);
         } else {
            int var3 = this.mGridLayoutManager.getSpanCount();
            return SizeSpec.makeSizeSpec(var2.getSpanSize() * (SizeSpec.getSize(var1) / var3), 1073741824);
         }
      }
   }

   public int getChildWidthSpec(int var1, RenderInfo var2) {
      if(this.mGridLayoutManager.getOrientation() != 0) {
         Integer var4 = (Integer)var2.getCustomAttribute("OVERRIDE_SIZE");
         if(var4 != null) {
            return SizeSpec.makeSizeSpec(var4.intValue(), 1073741824);
         } else if(var2.isFullSpan()) {
            return SizeSpec.makeSizeSpec(SizeSpec.getSize(var1), 1073741824);
         } else {
            int var3 = this.mGridLayoutManager.getSpanCount();
            return SizeSpec.makeSizeSpec(var2.getSpanSize() * (SizeSpec.getSize(var1) / var3), 1073741824);
         }
      } else {
         return SizeSpec.makeSizeSpec(0, 0);
      }
   }

   public int getItemCount() {
      return this.mGridLayoutManager.getItemCount();
   }

   public RecyclerView.LayoutManager getLayoutManager() {
      return this.mGridLayoutManager;
   }

   public int getScrollDirection() {
      return this.mGridLayoutManager.getOrientation();
   }

   public void setRenderInfoCollection(LayoutInfo.RenderInfoCollection var1) {
      this.mRenderInfoCollection = var1;
   }

   static class LithoGridLayoutManager extends GridLayoutManager {

      public LithoGridLayoutManager(Context var1, int var2, int var3, boolean var4) {
         super(var1, var2, var3, var4);
      }

      public RecyclerView.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams var1) {
         return (RecyclerView.LayoutParams)(var1 instanceof RecyclerBinder.RecyclerViewLayoutManagerOverrideParams?new GridLayoutInfo.LayoutParams((RecyclerBinder.RecyclerViewLayoutManagerOverrideParams)var1):super.generateLayoutParams(var1));
      }
   }

   static class ViewportFiller implements LayoutInfo.ViewportFiller {

      private int mFill;
      private final int mHeight;
      private int mIndexOfSpan;
      private final int mOrientation;
      private final int mSpanCount;
      private final int mWidth;


      public ViewportFiller(int var1, int var2, int var3, int var4) {
         this.mWidth = var1;
         this.mHeight = var2;
         this.mOrientation = var3;
         this.mSpanCount = var4;
      }

      public void add(RenderInfo var1, int var2, int var3) {
         if(this.mIndexOfSpan == 0) {
            int var4 = this.mFill;
            if(this.mOrientation == 1) {
               var2 = var3;
            }

            this.mFill = var4 + var2;
         }

         if(var1.isFullSpan()) {
            this.mIndexOfSpan = 0;
         } else {
            this.mIndexOfSpan += var1.getSpanSize();
            if(this.mIndexOfSpan == this.mSpanCount) {
               this.mIndexOfSpan = 0;
            }

         }
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

   class GridSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

      private GridSpanSizeLookup() {}

      // $FF: synthetic method
      GridSpanSizeLookup(Object var2) {
         this();
      }

      public int getSpanSize(int var1) {
         if(GridLayoutInfo.this.mRenderInfoCollection == null) {
            return 1;
         } else {
            RenderInfo var2 = GridLayoutInfo.this.mRenderInfoCollection.getRenderInfoAt(var1);
            return var2.isFullSpan()?GridLayoutInfo.this.mGridLayoutManager.getSpanCount():var2.getSpanSize();
         }
      }
   }

   public static class LayoutParams extends GridLayoutManager.LayoutParams implements LithoView.LayoutManagerOverrideParams {

      private final int mOverrideHeightMeasureSpec;
      private final int mOverrideWidthMeasureSpec;


      public LayoutParams(RecyclerBinder.RecyclerViewLayoutManagerOverrideParams var1) {
         super((RecyclerView.LayoutParams)var1);
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
