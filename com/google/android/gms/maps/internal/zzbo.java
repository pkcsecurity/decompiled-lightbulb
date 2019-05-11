package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.internal.zzbn;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;

public abstract class zzbo extends com.google.android.gms.internal.maps.zzb implements zzbn {

   public zzbo() {
      super("com.google.android.gms.maps.internal.IOnStreetViewPanoramaLongClickListener");
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
