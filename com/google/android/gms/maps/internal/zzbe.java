package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.internal.zzbd;

public abstract class zzbe extends com.google.android.gms.internal.maps.zzb implements zzbd {

   public zzbe() {
      super("com.google.android.gms.maps.internal.IOnPolygonClickListener");
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if(var1 == 1) {
         this.a(com.google.android.gms.internal.maps.zzx.a(var2.readStrongBinder()));
         var3.writeNoException();
         return true;
      } else {
         return false;
      }
   }
}
