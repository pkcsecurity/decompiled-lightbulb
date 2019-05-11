package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import java.util.concurrent.TimeUnit;

public final class zacd<R extends Object & Result> extends PendingResult<R> {

   private final Status mStatus;


   public zacd(Status var1) {
      Preconditions.checkNotNull(var1, "Status must not be null");
      Preconditions.checkArgument(var1.isSuccess() ^ true, "Status must not be success");
      this.mStatus = var1;
   }

   public final void addStatusListener(@NonNull PendingResult.StatusListener var1) {
      throw new UnsupportedOperationException("Operation not supported on PendingResults generated by ResultTransform.createFailedResult()");
   }

   @NonNull
   public final R await() {
      throw new UnsupportedOperationException("Operation not supported on PendingResults generated by ResultTransform.createFailedResult()");
   }

   @NonNull
   public final R await(long var1, @NonNull TimeUnit var3) {
      throw new UnsupportedOperationException("Operation not supported on PendingResults generated by ResultTransform.createFailedResult()");
   }

   public final void cancel() {
      throw new UnsupportedOperationException("Operation not supported on PendingResults generated by ResultTransform.createFailedResult()");
   }

   @NonNull
   final Status getStatus() {
      return this.mStatus;
   }

   public final boolean isCanceled() {
      throw new UnsupportedOperationException("Operation not supported on PendingResults generated by ResultTransform.createFailedResult()");
   }

   public final void setResultCallback(@NonNull ResultCallback<? super R> var1) {
      throw new UnsupportedOperationException("Operation not supported on PendingResults generated by ResultTransform.createFailedResult()");
   }

   public final void setResultCallback(@NonNull ResultCallback<? super R> var1, long var2, @NonNull TimeUnit var4) {
      throw new UnsupportedOperationException("Operation not supported on PendingResults generated by ResultTransform.createFailedResult()");
   }

   @NonNull
   @ShowFirstParty
   public final <S extends Object & Result> TransformedResult<S> then(@NonNull ResultTransform<? super R, ? extends S> var1) {
      throw new UnsupportedOperationException("Operation not supported on PendingResults generated by ResultTransform.createFailedResult()");
   }

   @Nullable
   public final Integer zam() {
      throw new UnsupportedOperationException("Operation not supported on PendingResults generated by ResultTransform.createFailedResult()");
   }
}
