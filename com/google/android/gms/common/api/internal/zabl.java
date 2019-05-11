package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.GoogleApiManager;

final class zabl implements Runnable {

   // $FF: synthetic field
   private final GoogleApiManager.zaa zaix;
   // $FF: synthetic field
   private final ConnectionResult zaiy;


   zabl(GoogleApiManager.zaa var1, ConnectionResult var2) {
      this.zaix = var1;
      this.zaiy = var2;
   }

   public final void run() {
      this.zaix.onConnectionFailed(this.zaiy);
   }
}
