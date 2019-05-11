package com.facebook.litho.widget;

import android.annotation.TargetApi;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.facebook.litho.ComponentTree;
import com.facebook.litho.LithoView;
import com.facebook.litho.widget.HasStickyHeader;
import com.facebook.litho.widget.SectionsRecyclerView;

@TargetApi(14)
class StickyHeaderController extends RecyclerView.OnScrollListener {

   static final String LAYOUTMANAGER_NOT_INITIALIZED = "LayoutManager of RecyclerView is not initialized yet.";
   static final String RECYCLER_ALREADY_INITIALIZED = "SectionsRecyclerView has already been initialized but never reset.";
   static final String RECYCLER_ARGUMENT_NULL = "Cannot initialize with null SectionsRecyclerView.";
   static final String RECYCLER_NOT_INITIALIZED = "SectionsRecyclerView has not been set yet.";
   private View lastTranslatedView;
   private final HasStickyHeader mHasStickyHeader;
   private RecyclerView.LayoutManager mLayoutManager;
   private SectionsRecyclerView mSectionsRecyclerView;
   private int previousStickyHeaderPosition = -1;


   StickyHeaderController(HasStickyHeader var1) {
      this.mHasStickyHeader = var1;
   }

   private static void detachLithoViewIfNeeded(LithoView var0) {
      if(var0 != null) {
         boolean var1;
         if(var0.getWindowToken() != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         if(var1) {
            var0.onStartTemporaryDetach();
         }

      }
   }

   private void initStickyHeader(int var1) {
      ComponentTree var2 = this.mHasStickyHeader.getComponentForStickyHeaderAt(var1);
      detachLithoViewIfNeeded(var2.getLithoView());
      this.mSectionsRecyclerView.setStickyComponent(var2);
   }

   @VisibleForTesting(
      otherwise = 2
   )
   int findStickyHeaderPosition(int var1) {
      while(var1 >= 0) {
         if(this.mHasStickyHeader.isSticky(var1)) {
            return var1;
         }

         --var1;
      }

      return -1;
   }

   void init(SectionsRecyclerView var1) {
      if(var1 == null) {
         throw new RuntimeException("Cannot initialize with null SectionsRecyclerView.");
      } else if(this.mSectionsRecyclerView != null) {
         throw new RuntimeException("SectionsRecyclerView has already been initialized but never reset.");
      } else {
         this.mSectionsRecyclerView = var1;
         this.mSectionsRecyclerView.hideStickyHeader();
         this.mLayoutManager = var1.getRecyclerView().getLayoutManager();
         if(this.mLayoutManager == null) {
            throw new RuntimeException("LayoutManager of RecyclerView is not initialized yet.");
         } else {
            this.mSectionsRecyclerView.getRecyclerView().addOnScrollListener(this);
         }
      }
   }

   public void onScrolled(RecyclerView var1, int var2, int var3) {
      var2 = this.mHasStickyHeader.findFirstVisibleItemPosition();
      if(var2 != -1) {
         int var4 = this.findStickyHeaderPosition(var2);
         ComponentTree var7 = this.mHasStickyHeader.getComponentForStickyHeaderAt(var2);
         if(this.lastTranslatedView != null && var7 != null && this.lastTranslatedView != var7.getLithoView()) {
            this.lastTranslatedView.setTranslationY(0.0F);
            this.lastTranslatedView = null;
         }

         if(var4 != -1 && var7 != null) {
            if(var2 == var4) {
               LithoView var8 = var7.getLithoView();
               HasStickyHeader var6 = this.mHasStickyHeader;
               var2 = var4 + 1;
               if(!var6.isValidPosition(var2) || !this.mHasStickyHeader.isSticky(var2)) {
                  var8.setTranslationY((float)(-var8.getTop()));
               }

               this.lastTranslatedView = var8;
               this.mSectionsRecyclerView.hideStickyHeader();
               this.previousStickyHeaderPosition = -1;
            } else {
               if(this.mSectionsRecyclerView.isStickyHeaderHidden() || var4 != this.previousStickyHeaderPosition) {
                  this.initStickyHeader(var4);
                  this.mSectionsRecyclerView.showStickyHeader();
               }

               int var5 = this.mHasStickyHeader.findLastVisibleItemPosition();

               while(true) {
                  var3 = 0;
                  if(var2 > var5) {
                     break;
                  }

                  if(this.mHasStickyHeader.isSticky(var2)) {
                     var3 = Math.min(this.mLayoutManager.findViewByPosition(var2).getTop() - this.mSectionsRecyclerView.getStickyHeader().getBottom() + this.mSectionsRecyclerView.getPaddingTop(), 0);
                     break;
                  }

                  ++var2;
               }

               this.mSectionsRecyclerView.setStickyHeaderVerticalOffset(var3);
               this.previousStickyHeaderPosition = var4;
            }
         } else {
            this.mSectionsRecyclerView.hideStickyHeader();
            this.previousStickyHeaderPosition = -1;
         }
      }
   }

   void reset() {
      if(this.mSectionsRecyclerView == null) {
         throw new IllegalStateException("SectionsRecyclerView has not been set yet.");
      } else {
         this.mSectionsRecyclerView.getRecyclerView().removeOnScrollListener(this);
         this.mLayoutManager = null;
         this.mSectionsRecyclerView = null;
      }
   }
}
