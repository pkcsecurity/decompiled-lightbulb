package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.internal.zzn;

public abstract class zzo extends com.google.android.gms.internal.maps.zzb implements zzn {

   public zzo() {
      super("com.google.android.gms.maps.internal.IOnCameraIdleListener");
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
