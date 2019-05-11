package com.google.android.gms.common.api.internal;

import android.support.annotation.BinderThread;
import com.google.android.gms.common.api.internal.zaak;
import com.google.android.gms.common.api.internal.zaas;
import com.google.android.gms.common.api.internal.zabf;
import java.lang.ref.WeakReference;

final class zaar extends com.google.android.gms.signin.internal.zac {

   private final WeakReference<zaak> zagj;


   zaar(zaak var1) {
      this.zagj = new WeakReference(var1);
   }

   @BinderThread
   public final void zab(com.google.android.gms.signin.internal.zaj var1) {
      zaak var2 = (zaak)this.zagj.get();
      if(var2 != null) {
         zaak.zad(var2).zaa((zabf)(new zaas(this, var2, var2, var1)));
      }
   }
}
