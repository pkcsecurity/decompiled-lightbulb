package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.zacp;
import com.google.android.gms.common.api.internal.zacs;

final class zacq implements zacs {

   // $FF: synthetic field
   private final zacp zala;


   zacq(zacp var1) {
      this.zala = var1;
   }

   public final void zac(BasePendingResult<?> var1) {
      this.zala.zaky.remove(var1);
   }
}
