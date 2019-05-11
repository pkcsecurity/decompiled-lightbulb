package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.PendingResultUtil;
import java.util.concurrent.TimeUnit;

final class zaj implements PendingResult.StatusListener {

   // $FF: synthetic field
   private final PendingResult zaou;
   // $FF: synthetic field
   private final li zaov;
   // $FF: synthetic field
   private final PendingResultUtil.ResultConverter zaow;
   // $FF: synthetic field
   private final PendingResultUtil.zaa zaox;


   zaj(PendingResult var1, li var2, PendingResultUtil.ResultConverter var3, PendingResultUtil.zaa var4) {
      this.zaou = var1;
      this.zaov = var2;
      this.zaow = var3;
      this.zaox = var4;
   }

   public final void onComplete(Status var1) {
      if(var1.isSuccess()) {
         Result var2 = this.zaou.await(0L, TimeUnit.MILLISECONDS);
         this.zaov.a(this.zaow.convert(var2));
      } else {
         this.zaov.a(this.zaox.zaf(var1));
      }
   }
}
