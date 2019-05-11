package com.google.android.gms.common.internal.service;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.service.zaj;
import com.google.android.gms.common.internal.service.zal;

public final class zam extends com.google.android.gms.internal.base.zaa implements zal {

   zam(IBinder var1) {
      super(var1, "com.google.android.gms.common.internal.service.ICommonService");
   }

   public final void zaa(zaj var1) throws RemoteException {
      Parcel var2 = this.zaa();
      gb.a(var2, var1);
      this.zac(1, var2);
   }
}
