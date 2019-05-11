package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.Preconditions;

final class zam {

   private final int zadg;
   private final ConnectionResult zadh;


   zam(ConnectionResult var1, int var2) {
      Preconditions.checkNotNull(var1);
      this.zadh = var1;
      this.zadg = var2;
   }

   final ConnectionResult getConnectionResult() {
      return this.zadh;
   }

   final int zar() {
      return this.zadg;
   }
}
