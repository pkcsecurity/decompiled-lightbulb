package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.zaab;

final class zaac implements PendingResult.StatusListener {

   // $FF: synthetic field
   private final BasePendingResult zafl;
   // $FF: synthetic field
   private final zaab zafm;


   zaac(zaab var1, BasePendingResult var2) {
      this.zafm = var1;
      this.zafl = var2;
   }

   public final void onComplete(Status var1) {
      zaab.zaa(this.zafm).remove(this.zafl);
   }
}
