package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.internal.zzan;
import com.google.android.gms.maps.model.LatLng;

public abstract class zzao extends com.google.android.gms.internal.maps.zzb implements zzan {

   public zzao() {
      super("com.google.android.gms.maps.internal.IOnMapLongClickListener");
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if(var1 == 1) {
         this.a((LatLng)hs.a(var2, LatLng.CREATOR));
         var3.writeNoException();
         return true;
      } else {
         return false;
      }
   }
}
