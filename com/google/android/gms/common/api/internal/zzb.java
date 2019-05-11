package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.zza;

final class zzb implements Runnable {

   // $FF: synthetic field
   private final LifecycleCallback zzbh;
   // $FF: synthetic field
   private final String zzbi;
   // $FF: synthetic field
   private final zza zzbj;


   zzb(zza var1, LifecycleCallback var2, String var3) {
      this.zzbj = var1;
      this.zzbh = var2;
      this.zzbi = var3;
   }

   public final void run() {
      if(zza.zza(this.zzbj) > 0) {
         LifecycleCallback var2 = this.zzbh;
         Bundle var1;
         if(zza.zzb(this.zzbj) != null) {
            var1 = zza.zzb(this.zzbj).getBundle(this.zzbi);
         } else {
            var1 = null;
         }

         var2.onCreate(var1);
      }

      if(zza.zza(this.zzbj) >= 2) {
         this.zzbh.onStart();
      }

      if(zza.zza(this.zzbj) >= 3) {
         this.zzbh.onResume();
      }

      if(zza.zza(this.zzbj) >= 4) {
         this.zzbh.onStop();
      }

      if(zza.zza(this.zzbj) >= 5) {
         this.zzbh.onDestroy();
      }

   }
}
