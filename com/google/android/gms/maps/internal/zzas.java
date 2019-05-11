package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.internal.zzar;

public abstract class zzas extends com.google.android.gms.internal.maps.zzb implements zzar {

   public zzas() {
      super("com.google.android.gms.maps.internal.IOnMarkerClickListener");
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if(var1 == 1) {
         boolean var5 = this.a(com.google.android.gms.internal.maps.zzu.a(var2.readStrongBinder()));
         var3.writeNoException();
         hs.a(var3, var5);
         return true;
      } else {
         return false;
      }
   }
}
