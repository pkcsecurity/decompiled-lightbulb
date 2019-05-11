package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.IInterface;
import com.google.android.gms.internal.maps.zzb;
import com.google.android.gms.internal.maps.zze;
import com.google.android.gms.internal.maps.zzg;

public abstract class zzf extends zzb implements zze {

   public static zze a(IBinder var0) {
      if(var0 == null) {
         return null;
      } else {
         IInterface var1 = var0.queryLocalInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
         return (zze)(var1 instanceof zze?(zze)var1:new zzg(var0));
      }
   }
}
