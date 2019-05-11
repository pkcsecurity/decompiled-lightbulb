package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.internal.zzr;

public abstract class zzs extends com.google.android.gms.internal.maps.zzb implements zzr {

   public zzs() {
      super("com.google.android.gms.maps.internal.IOnCameraMoveListener");
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if(var1 == 1) {
         this.a();
         var3.writeNoException();
         return true;
      } else {
         return false;
      }
   }
}
