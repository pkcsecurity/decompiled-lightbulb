package com.google.android.gms.location;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.location.zzb;
import com.google.android.gms.location.zzr;
import com.google.android.gms.location.zzt;

public class zzs extends zzb implements zzr {

   public static zzr a(IBinder var0) {
      if(var0 == null) {
         return null;
      } else {
         IInterface var1 = var0.queryLocalInterface("com.google.android.gms.location.IDeviceOrientationListener");
         return (zzr)(var1 instanceof zzr?(zzr)var1:new zzt(var0));
      }
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      throw new NoSuchMethodError();
   }
}
