package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.IInterface;
import com.google.android.gms.internal.maps.zzb;
import com.google.android.gms.internal.maps.zzt;
import com.google.android.gms.internal.maps.zzv;

public abstract class zzu extends zzb implements zzt {

   public static zzt a(IBinder var0) {
      if(var0 == null) {
         return null;
      } else {
         IInterface var1 = var0.queryLocalInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
         return (zzt)(var1 instanceof zzt?(zzt)var1:new zzv(var0));
      }
   }
}
