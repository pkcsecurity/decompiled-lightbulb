package com.google.android.gms.common.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zacd;

public abstract class ResultTransform<R extends Object & Result, S extends Object & Result> {

   @NonNull
   public final PendingResult<S> createFailedResult(@NonNull Status var1) {
      return new zacd(var1);
   }

   @NonNull
   public Status onFailure(@NonNull Status var1) {
      return var1;
   }

   @Nullable
   @WorkerThread
   public abstract PendingResult<S> onSuccess(@NonNull R var1);
}
