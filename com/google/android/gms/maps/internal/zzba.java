package com.google.android.gms.maps.internal;

import android.location.Location;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.internal.zzaz;

public abstract class zzba extends com.google.android.gms.internal.maps.zzb implements zzaz {

   public zzba() {
      super("com.google.android.gms.maps.internal.IOnMyLocationClickListener");
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if(var1 == 1) {
         this.a((Location)hs.a(var2, Location.CREATOR));
         var3.writeNoException();
         return true;
      } else {
         return false;
      }
   }
}
