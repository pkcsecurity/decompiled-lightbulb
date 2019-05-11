package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.BaseGmsClient;

final class zaf implements BaseGmsClient.BaseConnectionCallbacks {

   // $FF: synthetic field
   private final GoogleApiClient.ConnectionCallbacks zaoi;


   zaf(GoogleApiClient.ConnectionCallbacks var1) {
      this.zaoi = var1;
   }

   public final void onConnected(@Nullable Bundle var1) {
      this.zaoi.onConnected(var1);
   }

   public final void onConnectionSuspended(int var1) {
      this.zaoi.onConnectionSuspended(var1);
   }
}
