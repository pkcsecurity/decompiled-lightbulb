package com.google.android.gms.common.api.internal;

import android.support.annotation.WorkerThread;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.zaak;
import com.google.android.gms.common.api.internal.zaal;
import com.google.android.gms.common.api.internal.zaau;
import java.util.ArrayList;

final class zaaq extends zaau {

   // $FF: synthetic field
   private final zaak zagi;
   private final ArrayList<Api.Client> zago;


   public zaaq(zaak var1, ArrayList var2) {
      super(var1, (zaal)null);
      this.zagi = var1;
      this.zago = var2;
   }

   @WorkerThread
   public final void zaan() {
      zaak.zad(this.zagi).zaed.zagz = zaak.zag(this.zagi);
      ArrayList var3 = (ArrayList)this.zago;
      int var2 = var3.size();
      int var1 = 0;

      while(var1 < var2) {
         Object var4 = var3.get(var1);
         ++var1;
         ((Api.Client)var4).getRemoteService(zaak.zah(this.zagi), zaak.zad(this.zagi).zaed.zagz);
      }

   }
}
