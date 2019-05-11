package com.facebook.litho.sections.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import com.facebook.litho.sections.SectionTree;
import com.facebook.litho.widget.RecyclerEventsController;
import com.facebook.litho.widget.SmoothScrollAlignmentType;
import com.facebook.litho.widget.SnapUtil;
import com.facebook.litho.widget.StaggeredGridLayoutHelper;
import java.lang.ref.WeakReference;

public class RecyclerCollectionEventsController extends RecyclerEventsController {

   private int mFirstCompletelyVisibleItemPosition = 0;
   private int mLastCompletelyVisibleItemPosition = 0;
   private WeakReference<SectionTree> mSectionTree;
   private int mSnapMode = Integer.MIN_VALUE;


   private static int getFirstCompletelyVisibleItemPosition(RecyclerView.LayoutManager var0) {
      return var0 instanceof StaggeredGridLayoutManager?StaggeredGridLayoutHelper.findFirstFullyVisibleItemPosition((StaggeredGridLayoutManager)var0):((LinearLayoutManager)var0).findFirstCompletelyVisibleItemPosition();
   }

   private static int getLastCompletelyVisibleItemPosition(RecyclerView.LayoutManager var0) {
      return var0 instanceof StaggeredGridLayoutManager?StaggeredGridLayoutHelper.findLastFullyVisibleItemPosition((StaggeredGridLayoutManager)var0):((LinearLayoutManager)var0).findLastCompletelyVisibleItemPosition();
   }

   private static SmoothScrollAlignmentType getSmoothScrollAlignmentTypeFrom(int var0) {
      if(var0 != -1) {
         if(var0 != 1) {
            switch(var0) {
            case 2147483646:
            case Integer.MAX_VALUE:
               return SmoothScrollAlignmentType.SNAP_TO_CENTER;
            default:
               return SmoothScrollAlignmentType.DEFAULT;
            }
         } else {
            return SmoothScrollAlignmentType.SNAP_TO_END;
         }
      } else {
         return SmoothScrollAlignmentType.SNAP_TO_START;
      }
   }

   private int getSmoothScrollTarget(boolean var1, int var2) {
      int var3 = this.mSnapMode;
      if(var3 != Integer.MIN_VALUE) {
         if(var3 != -1) {
            if(var3 != 1) {
               switch(var3) {
               case 2147483646:
               case Integer.MAX_VALUE:
                  RecyclerView var5 = this.getRecyclerView();
                  if(var5 == null) {
                     return var2;
                  } else {
                     var3 = var5.getWidth() / 2;
                     int var4 = var5.getHeight() / 2;
                     View var6 = var5.findChildViewUnder((float)var3, (float)var4);
                     if(var6 == null) {
                        return var2;
                     }

                     var2 = var5.getChildAdapterPosition(var6);
                     if(var1) {
                        ++var2;
                     } else {
                        --var2;
                     }

                     return Math.max(0, var2);
                  }
               default:
                  return var2;
               }
            } else {
               if(var1) {
                  var2 = this.mLastCompletelyVisibleItemPosition + 1;
               } else {
                  var2 = this.mLastCompletelyVisibleItemPosition - 1;
               }

               return Math.max(0, var2);
            }
         } else {
            if(var1) {
               var2 = this.mFirstCompletelyVisibleItemPosition + 1;
            } else {
               var2 = this.mFirstCompletelyVisibleItemPosition - 1;
            }

            return Math.max(0, var2);
         }
      } else {
         return var2;
      }
   }

   private void requestScrollToPositionInner(boolean var1, int var2, int var3) {
      this.requestScrollToPositionInner(var1, var2, var3, (RecyclerView.SmoothScroller)null);
   }

   private void requestScrollToPositionInner(boolean var1, int var2, int var3, RecyclerView.SmoothScroller var4) {
      RecyclerView var7 = this.getRecyclerView();
      if(var7 != null) {
         RecyclerView.LayoutManager var6 = var7.getLayoutManager();
         if(var6 != null) {
            if(!var7.isLayoutFrozen()) {
               if(!var1) {
                  this.requestScrollToPosition(var2, false);
               } else if(var4 == null && this.mSnapMode == Integer.MIN_VALUE) {
                  this.requestScrollToPosition(var2, true);
               } else {
                  RecyclerView.SmoothScroller var5 = var4;
                  if(var4 == null) {
                     var5 = SnapUtil.getSmoothScrollerWithOffset(var7.getContext(), 0, getSmoothScrollAlignmentTypeFrom(this.mSnapMode));
                  }

                  var5.setTargetPosition(var3);
                  var6.startSmoothScroll(var5);
               }
            }
         }
      }
   }

   public void requestRefresh() {
      this.requestRefresh(false);
   }

   public void requestRefresh(boolean var1) {
      if(this.mSectionTree != null && this.mSectionTree.get() != null) {
         if(var1) {
            this.showRefreshing();
         }

         ((SectionTree)this.mSectionTree.get()).refresh();
      }

   }

   public void requestScrollToNextPosition(boolean var1) {
      int var2 = Math.max(0, this.mLastCompletelyVisibleItemPosition + 1);
      this.requestScrollToPositionInner(var1, var2, this.getSmoothScrollTarget(true, var2));
   }

   public void requestScrollToPositionWithSnap(int var1) {
      this.requestScrollToPositionInner(true, var1, var1);
   }

   public void requestScrollToPositionWithSnap(int var1, RecyclerView.SmoothScroller var2) {
      this.requestScrollToPositionInner(true, var1, var1, var2);
   }

   public void requestScrollToPreviousPosition(boolean var1) {
      int var2 = Math.max(0, this.mFirstCompletelyVisibleItemPosition - 1);
      this.requestScrollToPositionInner(var1, var2, this.getSmoothScrollTarget(false, var2));
   }

   void setSectionTree(SectionTree var1) {
      this.mSectionTree = new WeakReference(var1);
   }

   void setSnapMode(int var1) {
      this.mSnapMode = var1;
   }

   public void updateFirstLastFullyVisibleItemPositions(RecyclerView.LayoutManager var1) {
      int var2 = getFirstCompletelyVisibleItemPosition(var1);
      if(var2 != -1) {
         this.mFirstCompletelyVisibleItemPosition = var2;
      }

      var2 = getLastCompletelyVisibleItemPosition(var1);
      if(var2 != -1) {
         this.mLastCompletelyVisibleItemPosition = var2;
      }

   }
}
