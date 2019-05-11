package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzm;
import com.google.android.gms.dynamic.IObjectWrapper;

public final class zzo extends com.google.android.gms.internal.common.zza implements zzm {

   zzo(IBinder var1) {
      super(var1, "com.google.android.gms.common.internal.IGoogleCertificatesApi");
   }

   public final boolean zza(com.google.android.gms.common.zzk var1, IObjectWrapper var2) throws RemoteException {
      Parcel var4 = this.zza();
      gi.a(var4, var1);
      gi.a(var4, var2);
      Parcel var5 = this.zza(5, var4);
      boolean var3 = gi.a(var5);
      var5.recycle();
      return var3;
   }
}
