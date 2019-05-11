package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.zaab;
import com.google.android.gms.common.api.internal.zab;

public final class zae<A extends BaseImplementation.ApiMethodImpl<? extends Result, Api.AnyClient>> extends zab {

   private final A zacn;


   public zae(int var1, A var2) {
      super(var1);
      this.zacn = var2;
   }

   public final void zaa(@NonNull Status var1) {
      this.zacn.setFailedResult(var1);
   }

   public final void zaa(GoogleApiManager.zaa<?> var1) throws DeadObjectException {
      try {
         this.zacn.run(var1.zaab());
      } catch (RuntimeException var2) {
         this.zaa(var2);
      }
   }

   public final void zaa(@NonNull zaab var1, boolean var2) {
      var1.zaa((BasePendingResult)this.zacn, var2);
   }

   public final void zaa(@NonNull RuntimeException var1) {
      String var2 = var1.getClass().getSimpleName();
      String var4 = var1.getLocalizedMessage();
      StringBuilder var3 = new StringBuilder(String.valueOf(var2).length() + 2 + String.valueOf(var4).length());
      var3.append(var2);
      var3.append(": ");
      var3.append(var4);
      Status var5 = new Status(10, var3.toString());
      this.zacn.setFailedResult(var5);
   }
}
