package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.IGmsCallbacks;
import com.google.android.gms.common.internal.zzb;

public final class zzl extends com.google.android.gms.internal.common.zza implements IGmsCallbacks {

   zzl(IBinder var1) {
      super(var1, "com.google.android.gms.common.internal.IGmsCallbacks");
   }

   public final void onPostInitComplete(int var1, IBinder var2, Bundle var3) throws RemoteException {
      Parcel var4 = this.zza();
      var4.writeInt(var1);
      var4.writeStrongBinder(var2);
      gi.a(var4, var3);
      this.zzb(1, var4);
   }

   public final void zza(int var1, Bundle var2) throws RemoteException {
      Parcel var3 = this.zza();
      var3.writeInt(var1);
      gi.a(var3, var2);
      this.zzb(2, var3);
   }

   public final void zza(int var1, IBinder var2, zzb var3) throws RemoteException {
      Parcel var4 = this.zza();
      var4.writeInt(var1);
      var4.writeStrongBinder(var2);
      gi.a(var4, var3);
      this.zzb(3, var4);
   }
}
