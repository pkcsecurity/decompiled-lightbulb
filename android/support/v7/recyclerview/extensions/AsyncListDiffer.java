package android.support.v7.recyclerview.extensions;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig;
import android.support.v7.util.AdapterListUpdateCallback;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

public class AsyncListDiffer<T extends Object> {

   private static final Executor sMainThreadExecutor = new AsyncListDiffer.MainThreadExecutor();
   final AsyncDifferConfig<T> mConfig;
   @Nullable
   private List<T> mList;
   final Executor mMainThreadExecutor;
   int mMaxScheduledGeneration;
   @NonNull
   private List<T> mReadOnlyList;
   private final ListUpdateCallback mUpdateCallback;


   public AsyncListDiffer(@NonNull ListUpdateCallback var1, @NonNull AsyncDifferConfig<T> var2) {
      this.mReadOnlyList = Collections.emptyList();
      this.mUpdateCallback = var1;
      this.mConfig = var2;
      if(var2.getMainThreadExecutor() != null) {
         this.mMainThreadExecutor = var2.getMainThreadExecutor();
      } else {
         this.mMainThreadExecutor = sMainThreadExecutor;
      }
   }

   public AsyncListDiffer(@NonNull RecyclerView.Adapter var1, @NonNull DiffUtil.ItemCallback<T> var2) {
      this((ListUpdateCallback)(new AdapterListUpdateCallback(var1)), (new AsyncDifferConfig.Builder(var2)).build());
   }

   @NonNull
   public List<T> getCurrentList() {
      return this.mReadOnlyList;
   }

   void latchList(@NonNull List<T> var1, @NonNull DiffUtil.DiffResult var2) {
      this.mList = var1;
      this.mReadOnlyList = Collections.unmodifiableList(var1);
      var2.dispatchUpdatesTo(this.mUpdateCallback);
   }

   public void submitList(@Nullable final List<T> var1) {
      final int var2 = this.mMaxScheduledGeneration + 1;
      this.mMaxScheduledGeneration = var2;
      if(var1 != this.mList) {
         if(var1 == null) {
            var2 = this.mList.size();
            this.mList = null;
            this.mReadOnlyList = Collections.emptyList();
            this.mUpdateCallback.onRemoved(0, var2);
         } else if(this.mList == null) {
            this.mList = var1;
            this.mReadOnlyList = Collections.unmodifiableList(var1);
            this.mUpdateCallback.onInserted(0, var1.size());
         } else {
            final List var3 = this.mList;
            this.mConfig.getBackgroundThreadExecutor().execute(new Runnable() {
               public void run() {
                  final DiffUtil.DiffResult var1x = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                     public boolean areContentsTheSame(int var1x, int var2) {
                        Object var3x = var3.get(var1x);
                        Object var4 = var1.get(var2);
                        if(var3x != null && var4 != null) {
                           return AsyncListDiffer.this.mConfig.getDiffCallback().areContentsTheSame(var3x, var4);
                        } else if(var3x == null && var4 == null) {
                           return true;
                        } else {
                           throw new AssertionError();
                        }
                     }
                     public boolean areItemsTheSame(int var1x, int var2) {
                        Object var3x = var3.get(var1x);
                        Object var4 = var1.get(var2);
                        return var3x != null && var4 != null?AsyncListDiffer.this.mConfig.getDiffCallback().areItemsTheSame(var3x, var4):var3x == null && var4 == null;
                     }
                     @Nullable
                     public Object getChangePayload(int var1x, int var2) {
                        Object var3x = var3.get(var1x);
                        Object var4 = var1.get(var2);
                        if(var3x != null && var4 != null) {
                           return AsyncListDiffer.this.mConfig.getDiffCallback().getChangePayload(var3x, var4);
                        } else {
                           throw new AssertionError();
                        }
                     }
                     public int getNewListSize() {
                        return var1.size();
                     }
                     public int getOldListSize() {
                        return var3.size();
                     }
                  });
                  AsyncListDiffer.this.mMainThreadExecutor.execute(new Runnable() {
                     public void run() {
                        if(AsyncListDiffer.this.mMaxScheduledGeneration == var2) {
                           AsyncListDiffer.thisx.latchList(var1, var1x);
                        }

                     }
                  });
               }
            });
         }
      }
   }

   static class MainThreadExecutor implements Executor {

      final Handler mHandler = new Handler(Looper.getMainLooper());


      public void execute(@NonNull Runnable var1) {
         this.mHandler.post(var1);
      }
   }
}
