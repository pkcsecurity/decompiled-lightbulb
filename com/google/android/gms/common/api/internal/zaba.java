package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.StatusPendingResult;
import com.google.android.gms.common.api.internal.zaaw;

final class zaba implements ResultCallback<Status> {

   // $FF: synthetic field
   private final zaaw zahg;
   // $FF: synthetic field
   private final StatusPendingResult zahi;
   // $FF: synthetic field
   private final boolean zahj;
   // $FF: synthetic field
   private final GoogleApiClient zahk;


   zaba(zaaw var1, StatusPendingResult var2, boolean var3, GoogleApiClient var4) {
      this.zahg = var1;
      this.zahi = var2;
      this.zahj = var3;
      this.zahk = var4;
   }

   // $FF: synthetic method
   public final void onResult(@NonNull Result var1) {
      Status var2 = (Status)var1;
      fg.a(zaaw.zac(this.zahg)).b();
      if(var2.isSuccess() && this.zahg.isConnected()) {
         this.zahg.reconnect();
      }

      this.zahi.setResult(var2);
      if(this.zahj) {
         this.zahk.disconnect();
      }

   }
}
