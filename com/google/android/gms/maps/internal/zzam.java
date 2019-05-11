package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.internal.zzal;

public abstract class zzam extends com.google.android.gms.internal.maps.zzb implements zzal {

   public zzam() {
      super("com.google.android.gms.maps.internal.IOnMapLoadedCallback");
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
