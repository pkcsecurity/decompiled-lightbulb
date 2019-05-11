package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.internal.zzl;
import com.google.android.gms.maps.model.CameraPosition;

public abstract class zzm extends com.google.android.gms.internal.maps.zzb implements zzl {

   public zzm() {
      super("com.google.android.gms.maps.internal.IOnCameraChangeListener");
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if(var1 == 1) {
         this.a((CameraPosition)hs.a(var2, CameraPosition.CREATOR));
         var3.writeNoException();
         return true;
      } else {
         return false;
      }
   }
}
