package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.zabn;
import com.google.android.gms.common.internal.BaseGmsClient;

final class zabm implements BaseGmsClient.SignOutCallbacks {

   // $FF: synthetic field
   final GoogleApiManager.zaa zaix;


   zabm(GoogleApiManager.zaa var1) {
      this.zaix = var1;
   }

   public final void onSignOutComplete() {
      GoogleApiManager.zaa(this.zaix.zail).post(new zabn(this));
   }
}
