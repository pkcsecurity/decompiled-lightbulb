package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.internal.zzz;

public abstract class zzaa extends com.google.android.gms.internal.maps.zzb implements zzz {

   public zzaa() {
      super("com.google.android.gms.maps.internal.IOnIndoorStateChangeListener");
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      switch(var1) {
      case 1:
         this.a();
         break;
      case 2:
         this.a(com.google.android.gms.internal.maps.zzo.a(var2.readStrongBinder()));
         break;
      default:
         return false;
      }

      var3.writeNoException();
      return true;
   }
}
