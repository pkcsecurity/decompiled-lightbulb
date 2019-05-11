package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.zabm;

final class zabn implements Runnable {

   // $FF: synthetic field
   private final zabm zaiz;


   zabn(zabm var1) {
      this.zaiz = var1;
   }

   public final void run() {
      GoogleApiManager.zaa.zag(this.zaiz.zaix).disconnect();
   }
}
