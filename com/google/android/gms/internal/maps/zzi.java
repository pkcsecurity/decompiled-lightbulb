package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.IInterface;
import com.google.android.gms.internal.maps.zzb;
import com.google.android.gms.internal.maps.zzh;
import com.google.android.gms.internal.maps.zzj;

public abstract class zzi extends zzb implements zzh {

   public static zzh a(IBinder var0) {
      if(var0 == null) {
         return null;
      } else {
         IInterface var1 = var0.queryLocalInterface("com.google.android.gms.maps.model.internal.ICircleDelegate");
         return (zzh)(var1 instanceof zzh?(zzh)var1:new zzj(var0));
      }
   }
}
