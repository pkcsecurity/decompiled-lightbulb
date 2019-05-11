package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.internal.zzc;

public abstract class zzd extends com.google.android.gms.internal.maps.zzb implements zzc {

   public zzd() {
      super("com.google.android.gms.maps.internal.ICancelableCallback");
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      switch(var1) {
      case 1:
         this.a();
         break;
      case 2:
         this.b();
         break;
      default:
         return false;
      }

      var3.writeNoException();
      return true;
   }
}
