package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzi;
import com.google.android.gms.common.internal.zzk;
import com.google.android.gms.dynamic.IObjectWrapper;

public abstract class zzj extends com.google.android.gms.internal.common.zzb implements zzi {

   public zzj() {
      super("com.google.android.gms.common.internal.ICertData");
   }

   public static zzi zzb(IBinder var0) {
      if(var0 == null) {
         return null;
      } else {
         IInterface var1 = var0.queryLocalInterface("com.google.android.gms.common.internal.ICertData");
         return (zzi)(var1 instanceof zzi?(zzi)var1:new zzk(var0));
      }
   }

   protected final boolean zza(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      switch(var1) {
      case 1:
         IObjectWrapper var5 = this.zzb();
         var3.writeNoException();
         gi.a(var3, var5);
         break;
      case 2:
         var1 = this.zzc();
         var3.writeNoException();
         var3.writeInt(var1);
         break;
      default:
         return false;
      }

      return true;
   }
}
