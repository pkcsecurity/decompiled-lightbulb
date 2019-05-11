package com.facebook.litho.widget;

import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.facebook.litho.widget.LayoutInfo;
import com.facebook.litho.widget.ViewportInfo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
final class ViewportManager {

   private int mCurrentFirstFullyVisiblePosition;
   private int mCurrentFirstVisiblePosition;
   private int mCurrentLastFullyVisiblePosition;
   private int mCurrentLastVisiblePosition;
   private final LayoutInfo mLayoutInfo;
   private boolean mShouldUpdate;
   private int mTotalItemCount;
   @Nullable
   private List<ViewportInfo.ViewportChanged> mViewportChangedListeners;
   private final ViewportManager.ViewportScrollListener mViewportScrollListener = new ViewportManager.ViewportScrollListener(null);
   private int offsetTop;


   ViewportManager(int var1, int var2, LayoutInfo var3) {
      this.mCurrentFirstVisiblePosition = var1;
      this.mCurrentLastVisiblePosition = var2;
      this.mCurrentFirstFullyVisiblePosition = var3.findFirstFullyVisibleItemPosition();
      this.mCurrentLastFullyVisiblePosition = var3.findLastFullyVisibleItemPosition();
      this.mTotalItemCount = var3.getItemCount();
      this.mLayoutInfo = var3;
   }

   @UiThread
   void addViewportChangedListener(@Nullable ViewportInfo.ViewportChanged var1) {
      if(var1 != null) {
         if(this.mViewportChangedListeners == null) {
            this.mViewportChangedListeners = new ArrayList(2);
         }

         this.mViewportChangedListeners.add(var1);
      }
   }

   public int getOffsetTop() {
      return this.offsetTop;
   }

   @UiThread
   ViewportManager.ViewportScrollListener getScrollListener() {
      return this.mViewportScrollListener;
   }

   @UiThread
   boolean insertAffectsVisibleRange(int var1, int var2, int var3) {
      return !this.shouldUpdate()?(var3 == -1?true:var1 <= Math.max(this.mCurrentFirstVisiblePosition + var3 - 1, this.mCurrentLastVisiblePosition)):true;
   }

   @UiThread
   boolean moveAffectsVisibleRange(int var1, int var2, int var3) {
      boolean var5 = this.shouldUpdate();
      boolean var4 = true;
      if(!var5) {
         if(var3 == -1) {
            return true;
         } else {
            boolean var7;
            if(var2 >= this.mCurrentFirstVisiblePosition && var2 <= this.mCurrentFirstVisiblePosition + var3 - 1) {
               var7 = true;
            } else {
               var7 = false;
            }

            boolean var6;
            if(var1 >= this.mCurrentFirstVisiblePosition && var1 <= this.mCurrentFirstVisiblePosition + var3 - 1) {
               var6 = true;
            } else {
               var6 = false;
            }

            if(!var7) {
               if(var6) {
                  return true;
               }

               var4 = false;
            }

            return var4;
         }
      } else {
         return true;
      }
   }

   @UiThread
   void onViewportChanged(@ViewportInfo.State int var1) {
      int var3 = this.mLayoutInfo.findFirstVisibleItemPosition();
      int var4 = this.mLayoutInfo.findLastVisibleItemPosition();
      int var5 = this.mLayoutInfo.findFirstFullyVisibleItemPosition();
      int var6 = this.mLayoutInfo.findLastFullyVisibleItemPosition();
      int var2 = this.mLayoutInfo.getItemCount();
      if(var3 >= 0) {
         if(var4 >= 0) {
            if(var3 != this.mCurrentFirstVisiblePosition || var4 != this.mCurrentLastVisiblePosition || var5 != this.mCurrentFirstFullyVisiblePosition || var6 != this.mCurrentLastFullyVisiblePosition || var2 != this.mTotalItemCount || var1 == 1) {
               this.mCurrentFirstVisiblePosition = var3;
               this.mCurrentLastVisiblePosition = var4;
               this.mCurrentFirstFullyVisiblePosition = var5;
               this.mCurrentLastFullyVisiblePosition = var6;
               this.mTotalItemCount = var2;
               this.mShouldUpdate = false;
               if(this.mViewportChangedListeners != null) {
                  if(!this.mViewportChangedListeners.isEmpty()) {
                     int var7 = this.mViewportChangedListeners.size();

                     for(var2 = 0; var2 < var7; ++var2) {
                        ((ViewportInfo.ViewportChanged)this.mViewportChangedListeners.get(var2)).viewportChanged(var3, var4, var5, var6, var1);
                     }

                  }
               }
            }
         }
      }
   }

   @UiThread
   boolean removeAffectsVisibleRange(int var1, int var2) {
      return this.shouldUpdate()?true:var1 <= this.mCurrentLastVisiblePosition;
   }

   @UiThread
   void removeViewportChangedListener(@Nullable ViewportInfo.ViewportChanged var1) {
      if(var1 != null) {
         if(this.mViewportChangedListeners != null) {
            this.mViewportChangedListeners.remove(var1);
         }
      }
   }

   @UiThread
   void resetShouldUpdate() {
      this.mShouldUpdate = false;
   }

   @UiThread
   void setShouldUpdate(boolean var1) {
      if(!this.mShouldUpdate && !var1) {
         var1 = false;
      } else {
         var1 = true;
      }

      this.mShouldUpdate = var1;
   }

   @UiThread
   boolean shouldUpdate() {
      return this.mCurrentFirstVisiblePosition < 0 || this.mCurrentLastVisiblePosition < 0 || this.mShouldUpdate;
   }

   @UiThread
   boolean updateAffectsVisibleRange(int var1, int var2) {
      if(this.shouldUpdate()) {
         return true;
      } else {
         for(int var3 = var1; var3 < var1 + var2; ++var3) {
            if(this.mCurrentFirstVisiblePosition <= var3 && var3 <= this.mCurrentLastVisiblePosition) {
               return true;
            }
         }

         return false;
      }
   }

   class ViewportScrollListener extends RecyclerView.OnScrollListener {

      private ViewportScrollListener() {}

      // $FF: synthetic method
      ViewportScrollListener(Object var2) {
         this();
      }

      public void onScrolled(RecyclerView var1, int var2, int var3) {
         ViewportManager.this.onViewportChanged(0);
         if(var1.getChildAt(0) != null) {
            View var4 = var1.getChildAt(0);
            ViewportManager.this.offsetTop = var4.getTop();
         }

      }
   }
}
