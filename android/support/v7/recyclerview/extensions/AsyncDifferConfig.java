package android.support.v7.recyclerview.extensions;

import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.v7.util.DiffUtil;
import java.util.concurrent.Executor;

public final class AsyncDifferConfig<T extends Object> {

   @NonNull
   private final Executor mBackgroundThreadExecutor;
   @NonNull
   private final DiffUtil.ItemCallback<T> mDiffCallback;
   @NonNull
   private final Executor mMainThreadExecutor;


   AsyncDifferConfig(@NonNull Executor var1, @NonNull Executor var2, @NonNull DiffUtil.ItemCallback<T> var3) {
      this.mMainThreadExecutor = var1;
      this.mBackgroundThreadExecutor = var2;
      this.mDiffCallback = var3;
   }

   @NonNull
   public Executor getBackgroundThreadExecutor() {
      return this.mBackgroundThreadExecutor;
   }

   @NonNull
   public DiffUtil.ItemCallback<T> getDiffCallback() {
      return this.mDiffCallback;
   }

   @NonNull
   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   public Executor getMainThreadExecutor() {
      return this.mMainThreadExecutor;
   }

   public static final class Builder<T extends Object> {

      private static Executor sDiffExecutor;
      private static final Object sExecutorLock = new Object();
      private Executor mBackgroundThreadExecutor;
      private final DiffUtil.ItemCallback<T> mDiffCallback;
      private Executor mMainThreadExecutor;


      public Builder(@NonNull DiffUtil.ItemCallback<T> var1) {
         this.mDiffCallback = var1;
      }

      @NonNull
      public AsyncDifferConfig<T> build() {
         // $FF: Couldn't be decompiled
      }

      @NonNull
      public AsyncDifferConfig.Builder<T> setBackgroundThreadExecutor(Executor var1) {
         this.mBackgroundThreadExecutor = var1;
         return this;
      }

      @NonNull
      @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
      public AsyncDifferConfig.Builder<T> setMainThreadExecutor(Executor var1) {
         this.mMainThreadExecutor = var1;
         return this;
      }
   }
}
