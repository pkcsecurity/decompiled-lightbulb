package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zar;
import com.google.android.gms.common.internal.Preconditions;

public final class zaq implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

   public final Api<?> mApi;
   private final boolean zaeb;
   private zar zaec;


   public zaq(Api<?> var1, boolean var2) {
      this.mApi = var1;
      this.zaeb = var2;
   }

   private final void zav() {
      Preconditions.checkNotNull(this.zaec, "Callbacks must be attached to a ClientConnectionHelper instance before connecting the client.");
   }

   public final void onConnected(@Nullable Bundle var1) {
      this.zav();
      this.zaec.onConnected(var1);
   }

   public final void onConnectionFailed(@NonNull ConnectionResult var1) {
      this.zav();
      this.zaec.zaa(var1, this.mApi, this.zaeb);
   }

   public final void onConnectionSuspended(int var1) {
      this.zav();
      this.zaec.onConnectionSuspended(var1);
   }

   public final void zaa(zar var1) {
      this.zaec = var1;
   }
}
