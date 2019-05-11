package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.internal.IAccountAccessor;
import java.util.Collections;

final class zabo implements Runnable {

   // $FF: synthetic field
   private final ConnectionResult zaiy;
   // $FF: synthetic field
   private final GoogleApiManager.zac zajf;


   zabo(GoogleApiManager.zac var1, ConnectionResult var2) {
      this.zajf = var1;
      this.zaiy = var2;
   }

   public final void run() {
      if(this.zaiy.isSuccess()) {
         GoogleApiManager.zac.zaa(this.zajf, true);
         if(GoogleApiManager.zac.zaa(this.zajf).requiresSignIn()) {
            GoogleApiManager.zac.zab(this.zajf);
         } else {
            try {
               GoogleApiManager.zac.zaa(this.zajf).getRemoteService((IAccountAccessor)null, Collections.emptySet());
            } catch (SecurityException var2) {
               ((GoogleApiManager.zaa)GoogleApiManager.zaj(this.zajf.zail).get(GoogleApiManager.zac.zac(this.zajf))).onConnectionFailed(new ConnectionResult(10));
            }
         }
      } else {
         ((GoogleApiManager.zaa)GoogleApiManager.zaj(this.zajf.zail).get(GoogleApiManager.zac.zac(this.zajf))).onConnectionFailed(this.zaiy);
      }
   }
}
