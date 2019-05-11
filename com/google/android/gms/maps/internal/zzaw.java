package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.internal.zzav;

public abstract class zzaw extends com.google.android.gms.internal.maps.zzb implements zzav {

   public zzaw() {
      super("com.google.android.gms.maps.internal.IOnMyLocationButtonClickListener");
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if(var1 == 1) {
         boolean var5 = this.a();
         var3.writeNoException();
         hs.a(var3, var5);
         return true;
      } else {
         return false;
      }
   }
}
