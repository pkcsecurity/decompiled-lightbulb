package com.google.android.gms.common.internal.service;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.service.zaa;

final class zaf extends zaa {

   private final BaseImplementation.ResultHolder<Status> mResultHolder;


   public zaf(BaseImplementation.ResultHolder<Status> var1) {
      this.mResultHolder = var1;
   }

   public final void zaj(int var1) throws RemoteException {
      this.mResultHolder.setResult(new Status(var1));
   }
}
