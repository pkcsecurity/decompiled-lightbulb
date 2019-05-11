package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.zabt;
import com.google.android.gms.common.api.internal.zas;
import com.google.android.gms.common.api.internal.zat;

final class zau implements zabt {

   // $FF: synthetic field
   private final zas zaep;


   private zau(zas var1) {
      this.zaep = var1;
   }

   // $FF: synthetic method
   zau(zas var1, zat var2) {
      this(var1);
   }

   public final void zab(int var1, boolean var2) {
      zas.zaa(this.zaep).lock();

      try {
         if(!zas.zac(this.zaep) && zas.zad(this.zaep) != null && zas.zad(this.zaep).isSuccess()) {
            zas.zaa(this.zaep, true);
            zas.zae(this.zaep).onConnectionSuspended(var1);
            return;
         }

         zas.zaa(this.zaep, false);
         zas.zaa(this.zaep, var1, var2);
      } finally {
         zas.zaa(this.zaep).unlock();
      }

   }

   public final void zab(@Nullable Bundle var1) {
      zas.zaa(this.zaep).lock();

      try {
         zas.zaa(this.zaep, var1);
         zas.zaa(this.zaep, ConnectionResult.RESULT_SUCCESS);
         zas.zab(this.zaep);
      } finally {
         zas.zaa(this.zaep).unlock();
      }

   }

   public final void zac(@NonNull ConnectionResult var1) {
      zas.zaa(this.zaep).lock();

      try {
         zas.zaa(this.zaep, var1);
         zas.zab(this.zaep);
      } finally {
         zas.zaa(this.zaep).unlock();
      }

   }
}
