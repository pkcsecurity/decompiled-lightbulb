package com.google.android.gms.location;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.location.zzb;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.zzu;
import com.google.android.gms.location.zzw;

public abstract class zzv extends zzb implements zzu {

   public zzv() {
      super("com.google.android.gms.location.ILocationCallback");
   }

   public static zzu a(IBinder var0) {
      if(var0 == null) {
         return null;
      } else {
         IInterface var1 = var0.queryLocalInterface("com.google.android.gms.location.ILocationCallback");
         return (zzu)(var1 instanceof zzu?(zzu)var1:new zzw(var0));
      }
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      switch(var1) {
      case 1:
         this.a((LocationResult)hk.a(var2, LocationResult.CREATOR));
         break;
      case 2:
         this.a((LocationAvailability)hk.a(var2, LocationAvailability.CREATOR));
         break;
      default:
         return false;
      }

      return true;
   }
}
