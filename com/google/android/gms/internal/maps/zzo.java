package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.IInterface;
import com.google.android.gms.internal.maps.zzb;
import com.google.android.gms.internal.maps.zzn;
import com.google.android.gms.internal.maps.zzp;

public abstract class zzo extends zzb implements zzn {

   public static zzn a(IBinder var0) {
      if(var0 == null) {
         return null;
      } else {
         IInterface var1 = var0.queryLocalInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
         return (zzn)(var1 instanceof zzn?(zzn)var1:new zzp(var0));
      }
   }
}
