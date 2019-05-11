package com.google.android.gms.maps.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate;
import com.google.android.gms.maps.internal.zzbp;
import com.google.android.gms.maps.internal.zzbu;

public abstract class zzbq extends com.google.android.gms.internal.maps.zzb implements zzbp {

   public zzbq() {
      super("com.google.android.gms.maps.internal.IOnStreetViewPanoramaReadyCallback");
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if(var1 == 1) {
         IBinder var6 = var2.readStrongBinder();
         Object var7;
         if(var6 == null) {
            var7 = null;
         } else {
            IInterface var5 = var6.queryLocalInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
            if(var5 instanceof IStreetViewPanoramaDelegate) {
               var7 = (IStreetViewPanoramaDelegate)var5;
            } else {
               var7 = new zzbu(var6);
            }
         }

         this.a((IStreetViewPanoramaDelegate)var7);
         var3.writeNoException();
         return true;
      } else {
         return false;
      }
   }
}
