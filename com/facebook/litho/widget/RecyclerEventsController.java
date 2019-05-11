package com.facebook.litho.widget;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import com.facebook.litho.ThreadUtils;
import com.facebook.litho.widget.SectionsRecyclerView;

public class RecyclerEventsController {

   private final Runnable mClearRefreshRunnable = new Runnable() {
      public void run() {
         if(RecyclerEventsController.this.mSectionsRecyclerView != null && RecyclerEventsController.this.mSectionsRecyclerView.isRefreshing()) {
            RecyclerEventsController.this.mSectionsRecyclerView.setRefreshing(false);
         }

      }
   };
   private SectionsRecyclerView mSectionsRecyclerView;


   public void clearRefreshing() {
      if(this.mSectionsRecyclerView != null) {
         if(this.mSectionsRecyclerView.isRefreshing()) {
            if(ThreadUtils.isMainThread()) {
               this.mSectionsRecyclerView.setRefreshing(false);
            } else {
               this.mSectionsRecyclerView.removeCallbacks(this.mClearRefreshRunnable);
               this.mSectionsRecyclerView.post(this.mClearRefreshRunnable);
            }
         }
      }
   }

   @Nullable
   public RecyclerView getRecyclerView() {
      return this.mSectionsRecyclerView == null?null:this.mSectionsRecyclerView.getRecyclerView();
   }

   public void requestScrollToPosition(int var1, boolean var2) {
      if(this.mSectionsRecyclerView != null) {
         if(var2) {
            this.mSectionsRecyclerView.getRecyclerView().smoothScrollToPosition(var1);
         } else {
            this.mSectionsRecyclerView.getRecyclerView().scrollToPosition(var1);
         }
      }
   }

   public void requestScrollToTop(boolean var1) {
      this.requestScrollToPosition(0, var1);
   }

   void setSectionsRecyclerView(SectionsRecyclerView var1) {
      this.mSectionsRecyclerView = var1;
   }

   public void showRefreshing() {
      if(this.mSectionsRecyclerView != null) {
         if(!this.mSectionsRecyclerView.isRefreshing()) {
            ThreadUtils.assertMainThread();
            this.mSectionsRecyclerView.setRefreshing(true);
         }
      }
   }
}
