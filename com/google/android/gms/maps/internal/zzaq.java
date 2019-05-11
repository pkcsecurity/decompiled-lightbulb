package com.google.android.gms.maps.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.zzap;
import com.google.android.gms.maps.internal.zzg;

public abstract class zzaq extends com.google.android.gms.internal.maps.zzb implements zzap {

   public zzaq() {
      super("com.google.android.gms.maps.internal.IOnMapReadyCallback");
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if(var1 == 1) {
         IBinder var6 = var2.readStrongBinder();
         Object var7;
         if(var6 == null) {
            var7 = null;
         } else {
            IInterface var5 = var6.queryLocalInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
            if(var5 instanceof IGoogleMapDelegate) {
               var7 = (IGoogleMapDelegate)var5;
            } else {
               var7 = new zzg(var6);
            }
         }

         this.a((IGoogleMapDelegate)var7);
         var3.writeNoException();
         return true;
      } else {
         return false;
      }
   }
}
