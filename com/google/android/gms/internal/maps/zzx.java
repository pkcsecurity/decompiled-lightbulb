package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.IInterface;
import com.google.android.gms.internal.maps.zzb;
import com.google.android.gms.internal.maps.zzw;
import com.google.android.gms.internal.maps.zzy;

public abstract class zzx extends zzb implements zzw {

   public static zzw a(IBinder var0) {
      if(var0 == null) {
         return null;
      } else {
         IInterface var1 = var0.queryLocalInterface("com.google.android.gms.maps.model.internal.IPolygonDelegate");
         return (zzw)(var1 instanceof zzw?(zzw)var1:new zzy(var0));
      }
   }
}
