package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zaak;
import com.google.android.gms.common.api.internal.zaal;
import com.google.android.gms.common.api.internal.zaar;

final class zaat implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

   // $FF: synthetic field
   private final zaak zagi;


   private zaat(zaak var1) {
      this.zagi = var1;
   }

   // $FF: synthetic method
   zaat(zaak var1, zaal var2) {
      this(var1);
   }

   public final void onConnected(Bundle var1) {
      zaak.zaf(this.zagi).a(new zaar(this.zagi));
   }

   public final void onConnectionFailed(@NonNull ConnectionResult var1) {
      zaak.zac(this.zagi).lock();

      try {
         if(zaak.zab(this.zagi, var1)) {
            zaak.zai(this.zagi);
            zaak.zaj(this.zagi);
         } else {
            zaak.zaa(this.zagi, var1);
         }
      } finally {
         zaak.zac(this.zagi).unlock();
      }

   }

   public final void onConnectionSuspended(int var1) {}
}
