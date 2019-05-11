package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.StatusPendingResult;
import com.google.android.gms.common.api.internal.zaaw;

final class zaaz implements GoogleApiClient.OnConnectionFailedListener {

   // $FF: synthetic field
   private final StatusPendingResult zahi;


   zaaz(zaaw var1, StatusPendingResult var2) {
      this.zahi = var2;
   }

   public final void onConnectionFailed(@NonNull ConnectionResult var1) {
      this.zahi.setResult(new Status(8));
   }
}
