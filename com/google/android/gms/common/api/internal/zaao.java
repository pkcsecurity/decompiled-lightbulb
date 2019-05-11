package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.zaak;
import com.google.android.gms.common.api.internal.zaan;
import com.google.android.gms.common.api.internal.zabd;
import com.google.android.gms.common.api.internal.zabf;
import javax.annotation.concurrent.GuardedBy;

final class zaao extends zabf {

   // $FF: synthetic field
   private final ConnectionResult zagl;
   // $FF: synthetic field
   private final zaan zagm;


   zaao(zaan var1, zabd var2, ConnectionResult var3) {
      super(var2);
      this.zagm = var1;
      this.zagl = var3;
   }

   @GuardedBy
   public final void zaan() {
      zaak.zaa(this.zagm.zagi, this.zagl);
   }
}
