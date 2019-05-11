package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.IInterface;
import com.google.android.gms.internal.maps.zzab;
import com.google.android.gms.internal.maps.zzb;
import com.google.android.gms.internal.maps.zzz;

public abstract class zzaa extends zzb implements zzz {

   public static zzz a(IBinder var0) {
      if(var0 == null) {
         return null;
      } else {
         IInterface var1 = var0.queryLocalInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
         return (zzz)(var1 instanceof zzz?(zzz)var1:new zzab(var0));
      }
   }
}
