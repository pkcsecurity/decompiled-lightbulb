package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzi;
import com.google.android.gms.dynamic.IObjectWrapper;

public final class zzk extends com.google.android.gms.internal.common.zza implements zzi {

   zzk(IBinder var1) {
      super(var1, "com.google.android.gms.common.internal.ICertData");
   }

   public final IObjectWrapper zzb() throws RemoteException {
      Parcel var1 = this.zza(1, this.zza());
      IObjectWrapper var2 = IObjectWrapper.Stub.a(var1.readStrongBinder());
      var1.recycle();
      return var2;
   }

   public final int zzc() throws RemoteException {
      Parcel var2 = this.zza(2, this.zza());
      int var1 = var2.readInt();
      var2.recycle();
      return var1;
   }
}
