package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.internal.zzbh;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

public abstract class zzbi extends com.google.android.gms.internal.maps.zzb implements zzbh {

   public zzbi() {
      super("com.google.android.gms.maps.internal.IOnStreetViewPanoramaCameraChangeListener");
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if(var1 == 1) {
         this.a((StreetViewPanoramaCamera)hs.a(var2, StreetViewPanoramaCamera.CREATOR));
         var3.writeNoException();
         return true;
      } else {
         return false;
      }
   }
}
