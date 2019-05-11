package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.internal.zzx;

public abstract class zzy extends com.google.android.gms.internal.maps.zzb implements zzx {

   public zzy() {
      super("com.google.android.gms.maps.internal.IOnGroundOverlayClickListener");
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if(var1 == 1) {
         this.a(com.google.android.gms.internal.maps.zzl.a(var2.readStrongBinder()));
         var3.writeNoException();
         return true;
      } else {
         return false;
      }
   }
}
