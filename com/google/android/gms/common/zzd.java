package com.google.android.gms.common;

import com.google.android.gms.common.zzc;
import com.google.android.gms.common.zze;
import java.util.concurrent.Callable;

// $FF: synthetic class
final class zzd implements Callable {

   private final boolean zzq;
   private final String zzr;
   private final zze zzs;


   zzd(boolean var1, String var2, zze var3) {
      this.zzq = var1;
      this.zzr = var2;
      this.zzs = var3;
   }

   public final Object call() {
      return zzc.zza(this.zzq, this.zzr, this.zzs);
   }
}
