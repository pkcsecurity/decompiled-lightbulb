package com.google.android.gms.common.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import java.util.concurrent.TimeUnit;

@KeepForSdk
public abstract class PendingResult<R extends Object & Result> {

   @KeepForSdk
   public void addStatusListener(@NonNull PendingResult.StatusListener var1) {
      throw new UnsupportedOperationException();
   }

   @NonNull
   public abstract R await();

   @NonNull
   public abstract R await(long var1, @NonNull TimeUnit var3);

   public abstract void cancel();

   public abstract boolean isCanceled();

   public abstract void setResultCallback(@NonNull ResultCallback<? super R> var1);

   public abstract void setResultCallback(@NonNull ResultCallback<? super R> var1, long var2, @NonNull TimeUnit var4);

   @NonNull
   public <S extends Object & Result> TransformedResult<S> then(@NonNull ResultTransform<? super R, ? extends S> var1) {
      throw new UnsupportedOperationException();
   }

   @Nullable
   public Integer zam() {
      throw new UnsupportedOperationException();
   }

   @KeepForSdk
   public interface StatusListener {

      @KeepForSdk
      void onComplete(Status var1);
   }
}
