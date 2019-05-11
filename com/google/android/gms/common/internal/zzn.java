package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import com.google.android.gms.common.internal.zzm;
import com.google.android.gms.common.internal.zzo;

public abstract class zzn extends com.google.android.gms.internal.common.zzb implements zzm {

   public static zzm zzc(IBinder var0) {
      if(var0 == null) {
         return null;
      } else {
         IInterface var1 = var0.queryLocalInterface("com.google.android.gms.common.internal.IGoogleCertificatesApi");
         return (zzm)(var1 instanceof zzm?(zzm)var1:new zzo(var0));
      }
   }
}
