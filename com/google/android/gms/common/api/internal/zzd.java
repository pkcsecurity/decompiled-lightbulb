package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.zzc;

final class zzd implements Runnable {

   // $FF: synthetic field
   private final LifecycleCallback zzbh;
   // $FF: synthetic field
   private final String zzbi;
   // $FF: synthetic field
   private final zzc zzbk;


   zzd(zzc var1, LifecycleCallback var2, String var3) {
      this.zzbk = var1;
      this.zzbh = var2;
      this.zzbi = var3;
   }

   public final void run() {
      if(zzc.zza(this.zzbk) > 0) {
         LifecycleCallback var2 = this.zzbh;
         Bundle var1;
         if(zzc.zzb(this.zzbk) != null) {
            var1 = zzc.zzb(this.zzbk).getBundle(this.zzbi);
         } else {
            var1 = null;
         }

         var2.onCreate(var1);
      }

      if(zzc.zza(this.zzbk) >= 2) {
         this.zzbh.onStart();
      }

      if(zzc.zza(this.zzbk) >= 3) {
         this.zzbh.onResume();
      }

      if(zzc.zza(this.zzbk) >= 4) {
         this.zzbh.onStop();
      }

      if(zzc.zza(this.zzbk) >= 5) {
         this.zzbh.onDestroy();
      }

   }
}
