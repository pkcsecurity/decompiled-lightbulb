package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.internal.zzbl;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;

public abstract class zzbm extends com.google.android.gms.internal.maps.zzb implements zzbl {

   public zzbm() {
      super("com.google.android.gms.maps.internal.IOnStreetViewPanoramaClickListener");
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if(var1 == 1) {
         this.a((StreetViewPanoramaOrientation)hs.a(var2, StreetViewPanoramaOrientation.CREATOR));
         var3.writeNoException();
         return true;
      } else {
         return false;
      }
   }
}
