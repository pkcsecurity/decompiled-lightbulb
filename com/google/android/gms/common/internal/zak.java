package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.Response;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.PendingResultUtil;

final class zak implements PendingResultUtil.ResultConverter<R, T> {

   // $FF: synthetic field
   private final Response zaoy;


   zak(Response var1) {
      this.zaoy = var1;
   }

   // $FF: synthetic method
   public final Object convert(Result var1) {
      this.zaoy.setResult(var1);
      return this.zaoy;
   }
}
