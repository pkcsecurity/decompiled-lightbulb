package android.support.v7.widget;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

final class GapWorker implements Runnable {

   static final ThreadLocal<GapWorker> sGapWorker = new ThreadLocal();
   static Comparator<GapWorker.Task> sTaskComparator = new Comparator() {
      public int compare(GapWorker.Task var1, GapWorker.Task var2) {
         RecyclerView var6 = var1.view;
         byte var5 = 1;
         boolean var3;
         if(var6 == null) {
            var3 = true;
         } else {
            var3 = false;
         }

         boolean var4;
         if(var2.view == null) {
            var4 = true;
         } else {
            var4 = false;
         }

         if(var3 != var4) {
            return var1.view == null?1:-1;
         } else if(var1.immediate != var2.immediate) {
            byte var8 = var5;
            if(var1.immediate) {
               var8 = -1;
            }

            return var8;
         } else {
            int var7 = var2.viewVelocity - var1.viewVelocity;
            if(var7 != 0) {
               return var7;
            } else {
               var7 = var1.distanceToItem - var2.distanceToItem;
               return var7 != 0?var7:0;
            }
         }
      }
   };
   long mFrameIntervalNs;
   long mPostTimeNs;
   ArrayList<RecyclerView> mRecyclerViews = new ArrayList();
   private ArrayList<GapWorker.Task> mTasks = new ArrayList();


   private void buildTaskList() {
      int var4 = this.mRecyclerViews.size();
      int var1 = 0;

      int var2;
      int var3;
      for(var2 = 0; var1 < var4; var2 = var3) {
         RecyclerView var8 = (RecyclerView)this.mRecyclerViews.get(var1);
         var3 = var2;
         if(var8.getWindowVisibility() == 0) {
            var8.mPrefetchRegistry.collectPrefetchPositionsFromView(var8, false);
            var3 = var2 + var8.mPrefetchRegistry.mCount;
         }

         ++var1;
      }

      this.mTasks.ensureCapacity(var2);
      var2 = 0;

      for(var1 = 0; var2 < var4; ++var2) {
         RecyclerView var9 = (RecyclerView)this.mRecyclerViews.get(var2);
         if(var9.getWindowVisibility() == 0) {
            GapWorker.LayoutPrefetchRegistryImpl var10 = var9.mPrefetchRegistry;
            int var5 = Math.abs(var10.mPrefetchDx) + Math.abs(var10.mPrefetchDy);

            for(var3 = 0; var3 < var10.mCount * 2; var3 += 2) {
               GapWorker.Task var11;
               if(var1 >= this.mTasks.size()) {
                  var11 = new GapWorker.Task();
                  this.mTasks.add(var11);
               } else {
                  var11 = (GapWorker.Task)this.mTasks.get(var1);
               }

               int var6 = var10.mPrefetchArray[var3 + 1];
               boolean var7;
               if(var6 <= var5) {
                  var7 = true;
               } else {
                  var7 = false;
               }

               var11.immediate = var7;
               var11.viewVelocity = var5;
               var11.distanceToItem = var6;
               var11.view = var9;
               var11.position = var10.mPrefetchArray[var3];
               ++var1;
            }
         }
      }

      Collections.sort(this.mTasks, sTaskComparator);
   }

   private void flushTaskWithDeadline(GapWorker.Task var1, long var2) {
      long var4;
      if(var1.immediate) {
         var4 = Long.MAX_VALUE;
      } else {
         var4 = var2;
      }

      RecyclerView.ViewHolder var6 = this.prefetchPositionWithDeadline(var1.view, var1.position, var4);
      if(var6 != null && var6.mNestedRecyclerView != null && var6.isBound() && !var6.isInvalid()) {
         this.prefetchInnerRecyclerViewWithDeadline((RecyclerView)var6.mNestedRecyclerView.get(), var2);
      }

   }

   private void flushTasksWithDeadline(long var1) {
      for(int var3 = 0; var3 < this.mTasks.size(); ++var3) {
         GapWorker.Task var4 = (GapWorker.Task)this.mTasks.get(var3);
         if(var4.view == null) {
            return;
         }

         this.flushTaskWithDeadline(var4, var1);
         var4.clear();
      }

   }

   static boolean isPrefetchPositionAttached(RecyclerView var0, int var1) {
      int var3 = var0.mChildHelper.getUnfilteredChildCount();

      for(int var2 = 0; var2 < var3; ++var2) {
         RecyclerView.ViewHolder var4 = RecyclerView.getChildViewHolderInt(var0.mChildHelper.getUnfilteredChildAt(var2));
         if(var4.mPosition == var1 && !var4.isInvalid()) {
            return true;
         }
      }

      return false;
   }

   private void prefetchInnerRecyclerViewWithDeadline(@Nullable RecyclerView param1, long param2) {
      // $FF: Couldn't be decompiled
   }

   private RecyclerView.ViewHolder prefetchPositionWithDeadline(RecyclerView param1, int param2, long param3) {
      // $FF: Couldn't be decompiled
   }

   public void add(RecyclerView var1) {
      this.mRecyclerViews.add(var1);
   }

   void postFromTraversal(RecyclerView var1, int var2, int var3) {
      if(var1.isAttachedToWindow() && this.mPostTimeNs == 0L) {
         this.mPostTimeNs = var1.getNanoTime();
         var1.post(this);
      }

      var1.mPrefetchRegistry.setPrefetchVector(var2, var3);
   }

   void prefetch(long var1) {
      this.buildTaskList();
      this.flushTasksWithDeadline(var1);
   }

   public void remove(RecyclerView var1) {
      this.mRecyclerViews.remove(var1);
   }

   public void run() {
      // $FF: Couldn't be decompiled
   }

   static class Task {

      public int distanceToItem;
      public boolean immediate;
      public int position;
      public RecyclerView view;
      public int viewVelocity;


      public void clear() {
         this.immediate = false;
         this.viewVelocity = 0;
         this.distanceToItem = 0;
         this.view = null;
         this.position = 0;
      }
   }

   static class LayoutPrefetchRegistryImpl implements RecyclerView.LayoutPrefetchRegistry {

      int mCount;
      int[] mPrefetchArray;
      int mPrefetchDx;
      int mPrefetchDy;


      public void addPosition(int var1, int var2) {
         if(var1 < 0) {
            throw new IllegalArgumentException("Layout positions must be non-negative");
         } else if(var2 < 0) {
            throw new IllegalArgumentException("Pixel distance must be non-negative");
         } else {
            int var3 = this.mCount * 2;
            if(this.mPrefetchArray == null) {
               this.mPrefetchArray = new int[4];
               Arrays.fill(this.mPrefetchArray, -1);
            } else if(var3 >= this.mPrefetchArray.length) {
               int[] var4 = this.mPrefetchArray;
               this.mPrefetchArray = new int[var3 * 2];
               System.arraycopy(var4, 0, this.mPrefetchArray, 0, var4.length);
            }

            this.mPrefetchArray[var3] = var1;
            this.mPrefetchArray[var3 + 1] = var2;
            ++this.mCount;
         }
      }

      void clearPrefetchPositions() {
         if(this.mPrefetchArray != null) {
            Arrays.fill(this.mPrefetchArray, -1);
         }

         this.mCount = 0;
      }

      void collectPrefetchPositionsFromView(RecyclerView var1, boolean var2) {
         this.mCount = 0;
         if(this.mPrefetchArray != null) {
            Arrays.fill(this.mPrefetchArray, -1);
         }

         RecyclerView.LayoutManager var3 = var1.mLayout;
         if(var1.mAdapter != null && var3 != null && var3.isItemPrefetchEnabled()) {
            if(var2) {
               if(!var1.mAdapterHelper.hasPendingUpdates()) {
                  var3.collectInitialPrefetchPositions(var1.mAdapter.getItemCount(), this);
               }
            } else if(!var1.hasPendingAdapterUpdates()) {
               var3.collectAdjacentPrefetchPositions(this.mPrefetchDx, this.mPrefetchDy, var1.mState, this);
            }

            if(this.mCount > var3.mPrefetchMaxCountObserved) {
               var3.mPrefetchMaxCountObserved = this.mCount;
               var3.mPrefetchMaxObservedInInitialPrefetch = var2;
               var1.mRecycler.updateViewCacheSize();
            }
         }

      }

      boolean lastPrefetchIncludedPosition(int var1) {
         if(this.mPrefetchArray != null) {
            int var3 = this.mCount;

            for(int var2 = 0; var2 < var3 * 2; var2 += 2) {
               if(this.mPrefetchArray[var2] == var1) {
                  return true;
               }
            }
         }

         return false;
      }

      void setPrefetchVector(int var1, int var2) {
         this.mPrefetchDx = var1;
         this.mPrefetchDy = var2;
      }
   }
}
