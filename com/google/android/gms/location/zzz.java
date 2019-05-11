package com.google.android.gms.location;

import android.location.Location;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.location.zza;
import com.google.android.gms.location.zzx;

public final class zzz extends zza implements zzx {

   zzz(IBinder var1) {
      super(var1, "com.google.android.gms.location.ILocationListener");
   }

   public final void a(Location var1) throws RemoteException {
      Parcel var2 = this.a();
      hk.a(var2, var1);
      this.c(1, var2);
   }
}
