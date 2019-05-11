package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.zas;

final class zat implements Runnable {

   // $FF: synthetic field
   private final zas zaep;


   zat(zas var1) {
      this.zaep = var1;
   }

   public final void run() {
      zas.zaa(this.zaep).lock();

      try {
         zas.zab(this.zaep);
      } finally {
         zas.zaa(this.zaep).unlock();
      }

   }
}
