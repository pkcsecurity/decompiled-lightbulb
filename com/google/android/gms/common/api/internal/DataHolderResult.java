package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;

@KeepForSdk
public class DataHolderResult implements Releasable, Result {

   @KeepForSdk
   protected final DataHolder mDataHolder;
   @KeepForSdk
   protected final Status mStatus;


   @KeepForSdk
   protected DataHolderResult(DataHolder var1) {
      this(var1, new Status(var1.getStatusCode()));
   }

   @KeepForSdk
   protected DataHolderResult(DataHolder var1, Status var2) {
      this.mStatus = var2;
      this.mDataHolder = var1;
   }

   @KeepForSdk
   public Status getStatus() {
      return this.mStatus;
   }

   @KeepForSdk
   public void release() {
      if(this.mDataHolder != null) {
         this.mDataHolder.close();
      }

   }
}
