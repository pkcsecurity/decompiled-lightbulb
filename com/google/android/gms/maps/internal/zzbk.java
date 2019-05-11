package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.internal.zzbj;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

public abstract class zzbk extends com.google.android.gms.internal.maps.zzb implements zzbj {

   public zzbk() {
      super("com.google.android.gms.maps.internal.IOnStreetViewPanoramaChangeListener");
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if(var1 == 1) {
         this.a((StreetViewPanoramaLocation)hs.a(var2, StreetViewPanoramaLocation.CREATOR));
         var3.writeNoException();
         return true;
      } else {
         return false;
      }
   }
}
