package com.google.android.gms.location;

import android.location.Location;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.location.zzb;
import com.google.android.gms.location.zzx;
import com.google.android.gms.location.zzz;

public abstract class zzy extends zzb implements zzx {

   public zzy() {
      super("com.google.android.gms.location.ILocationListener");
   }

   public static zzx a(IBinder var0) {
      if(var0 == null) {
         return null;
      } else {
         IInterface var1 = var0.queryLocalInterface("com.google.android.gms.location.ILocationListener");
         return (zzx)(var1 instanceof zzx?(zzx)var1:new zzz(var0));
      }
   }

   protected final boolean a(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if(var1 == 1) {
         this.a((Location)hk.a(var2, Location.CREATOR));
         return true;
      } else {
         return false;
      }
   }
}
