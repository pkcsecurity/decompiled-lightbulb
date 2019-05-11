package com.google.android.gms.location;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.location.zza;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.zzu;

public final class zzw extends zza implements zzu {

   zzw(IBinder var1) {
      super(var1, "com.google.android.gms.location.ILocationCallback");
   }

   public final void a(LocationAvailability var1) throws RemoteException {
      Parcel var2 = this.a();
      hk.a(var2, var1);
      this.c(2, var2);
   }

   public final void a(LocationResult var1) throws RemoteException {
      Parcel var2 = this.a();
      hk.a(var2, var1);
      this.c(1, var2);
   }
}
