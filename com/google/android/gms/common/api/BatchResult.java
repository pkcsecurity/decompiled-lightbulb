package com.google.android.gms.common.api;

import com.google.android.gms.common.api.BatchResultToken;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.TimeUnit;

public final class BatchResult implements Result {

   private final Status mStatus;
   private final PendingResult<?>[] zabc;


   BatchResult(Status var1, PendingResult<?>[] var2) {
      this.mStatus = var1;
      this.zabc = var2;
   }

   public final Status getStatus() {
      return this.mStatus;
   }

   public final <R extends Object & Result> R take(BatchResultToken<R> var1) {
      boolean var2;
      if(var1.mId < this.zabc.length) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "The result token does not belong to this batch");
      return this.zabc[var1.mId].await(0L, TimeUnit.MILLISECONDS);
   }
}
