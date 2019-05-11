package com.google.android.gms.internal.location;

import android.location.Location;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.location.zza;
import com.google.android.gms.internal.location.zzao;
import com.google.android.gms.internal.location.zzaq;
import com.google.android.gms.internal.location.zzbf;
import com.google.android.gms.internal.location.zzo;
import com.google.android.gms.location.LocationSettingsRequest;

public final class zzap extends zza implements zzao {

   public zzap(IBinder var1) {
      super(var1, "com.google.android.gms.location.internal.IGoogleLocationManagerService");
   }

   public final Location a(String var1) throws RemoteException {
      Parcel var2 = this.a();
      var2.writeString(var1);
      Parcel var3 = this.a(21, var2);
      Location var4 = (Location)hk.a(var3, Location.CREATOR);
      var3.recycle();
      return var4;
   }

   public final void a(zzbf var1) throws RemoteException {
      Parcel var2 = this.a();
      hk.a(var2, var1);
      this.b(59, var2);
   }

   public final void a(zzo var1) throws RemoteException {
      Parcel var2 = this.a();
      hk.a(var2, var1);
      this.b(75, var2);
   }

   public final void a(LocationSettingsRequest var1, zzaq var2, String var3) throws RemoteException {
      Parcel var4 = this.a();
      hk.a(var4, var1);
      hk.a(var4, var2);
      var4.writeString(var3);
      this.b(63, var4);
   }

   public final void a(boolean var1) throws RemoteException {
      Parcel var2 = this.a();
      hk.a(var2, var1);
      this.b(12, var2);
   }
}
