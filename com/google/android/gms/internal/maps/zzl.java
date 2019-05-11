package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.IInterface;
import com.google.android.gms.internal.maps.zzb;
import com.google.android.gms.internal.maps.zzk;
import com.google.android.gms.internal.maps.zzm;

public abstract class zzl extends zzb implements zzk {

   public static zzk a(IBinder var0) {
      if(var0 == null) {
         return null;
      } else {
         IInterface var1 = var0.queryLocalInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
         return (zzk)(var1 instanceof zzk?(zzk)var1:new zzm(var0));
      }
   }
}
