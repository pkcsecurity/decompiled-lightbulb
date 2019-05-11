package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.IStatusCallback;

@KeepForSdk
public class StatusCallback extends IStatusCallback.Stub {

   @KeepForSdk
   private final BaseImplementation.ResultHolder<Status> mResultHolder;


   @KeepForSdk
   public StatusCallback(BaseImplementation.ResultHolder<Status> var1) {
      this.mResultHolder = var1;
   }

   @KeepForSdk
   public void onResult(Status var1) {
      this.mResultHolder.setResult(var1);
   }
}
