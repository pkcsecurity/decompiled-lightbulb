package com.google.android.gms.common.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.BaseGmsClient;

final class zag implements BaseGmsClient.BaseOnConnectionFailedListener {

   // $FF: synthetic field
   private final GoogleApiClient.OnConnectionFailedListener zaoj;


   zag(GoogleApiClient.OnConnectionFailedListener var1) {
      this.zaoj = var1;
   }

   public final void onConnectionFailed(@NonNull ConnectionResult var1) {
      this.zaoj.onConnectionFailed(var1);
   }
}
