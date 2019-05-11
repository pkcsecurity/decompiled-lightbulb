package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.api.internal.BasePendingResult;
import java.util.concurrent.TimeUnit;

@KeepForSdk
public final class OptionalPendingResultImpl<R extends Object & Result> extends OptionalPendingResult<R> {

   private final BasePendingResult<R> zajp;


   public OptionalPendingResultImpl(PendingResult<R> var1) {
      this.zajp = (BasePendingResult)var1;
   }

   public final void addStatusListener(PendingResult.StatusListener var1) {
      this.zajp.addStatusListener(var1);
   }

   public final R await() {
      return this.zajp.await();
   }

   public final R await(long var1, TimeUnit var3) {
      return this.zajp.await(var1, var3);
   }

   public final void cancel() {
      this.zajp.cancel();
   }

   public final R get() {
      if(this.isDone()) {
         return this.await(0L, TimeUnit.MILLISECONDS);
      } else {
         throw new IllegalStateException("Result is not available. Check that isDone() returns true before calling get().");
      }
   }

   public final boolean isCanceled() {
      return this.zajp.isCanceled();
   }

   public final boolean isDone() {
      return this.zajp.isReady();
   }

   public final void setResultCallback(ResultCallback<? super R> var1) {
      this.zajp.setResultCallback(var1);
   }

   public final void setResultCallback(ResultCallback<? super R> var1, long var2, TimeUnit var4) {
      this.zajp.setResultCallback(var1, var2, var4);
   }

   @NonNull
   public final <S extends Object & Result> TransformedResult<S> then(@NonNull ResultTransform<? super R, ? extends S> var1) {
      return this.zajp.then(var1);
   }

   public final Integer zam() {
      return this.zajp.zam();
   }
}
