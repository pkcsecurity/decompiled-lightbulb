package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.StatusPendingResult;
import com.google.android.gms.common.api.internal.zaaw;
import java.util.concurrent.atomic.AtomicReference;

final class zaay implements GoogleApiClient.ConnectionCallbacks {

   // $FF: synthetic field
   private final zaaw zahg;
   // $FF: synthetic field
   private final AtomicReference zahh;
   // $FF: synthetic field
   private final StatusPendingResult zahi;


   zaay(zaaw var1, AtomicReference var2, StatusPendingResult var3) {
      this.zahg = var1;
      this.zahh = var2;
      this.zahi = var3;
   }

   public final void onConnected(Bundle var1) {
      zaaw.zaa(this.zahg, (GoogleApiClient)this.zahh.get(), this.zahi, true);
   }

   public final void onConnectionSuspended(int var1) {}
}
