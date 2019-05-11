package com.google.android.gms.internal.location;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.location.zza;
import com.google.android.gms.internal.location.zzad;
import com.google.android.gms.internal.location.zzaj;

public final class zzal extends zza implements zzaj {

   zzal(IBinder var1) {
      super(var1, "com.google.android.gms.location.internal.IFusedLocationProviderCallback");
   }

   public final void a(zzad var1) throws RemoteException {
      Parcel var2 = this.a();
      hk.a(var2, var1);
      this.c(1, var2);
   }
}
