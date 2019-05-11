package com.google.android.gms.internal.location;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.location.zzad;
import com.google.android.gms.internal.location.zzaj;
import com.google.android.gms.internal.location.zzb;

public abstract class zzak extends zzb implements zzaj {

   public zzak() {
      super("com.google.android.gms.location.internal.IFusedLocationProviderCallback");
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if(var1 == 1) {
         this.a((zzad)hk.a(var2, zzad.CREATOR));
         return true;
      } else {
         return false;
      }
   }
}
