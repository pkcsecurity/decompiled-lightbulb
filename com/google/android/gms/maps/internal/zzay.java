package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.internal.zzax;

public abstract class zzay extends com.google.android.gms.internal.maps.zzb implements zzax {

   public zzay() {
      super("com.google.android.gms.maps.internal.IOnMyLocationChangeListener");
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if(var1 == 1) {
         this.a(IObjectWrapper.Stub.a(var2.readStrongBinder()));
         var3.writeNoException();
         return true;
      } else {
         return false;
      }
   }
}
