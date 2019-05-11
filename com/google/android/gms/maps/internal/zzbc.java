package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.internal.zzbb;
import com.google.android.gms.maps.model.PointOfInterest;

public abstract class zzbc extends com.google.android.gms.internal.maps.zzb implements zzbb {

   public zzbc() {
      super("com.google.android.gms.maps.internal.IOnPoiClickListener");
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if(var1 == 1) {
         this.a((PointOfInterest)hs.a(var2, PointOfInterest.CREATOR));
         var3.writeNoException();
         return true;
      } else {
         return false;
      }
   }
}
