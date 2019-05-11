package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.StatusExceptionMapper;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.api.internal.zaab;
import com.google.android.gms.common.api.internal.zab;
import com.google.android.gms.common.api.internal.zac;

public final class zag<ResultT extends Object> extends zac {

   private final li<ResultT> zacm;
   private final TaskApiCall<Api.AnyClient, ResultT> zacq;
   private final StatusExceptionMapper zacr;


   public zag(int var1, TaskApiCall<Api.AnyClient, ResultT> var2, li<ResultT> var3, StatusExceptionMapper var4) {
      super(var1);
      this.zacm = var3;
      this.zacq = var2;
      this.zacr = var4;
   }

   public final void zaa(@NonNull Status var1) {
      this.zacm.b(this.zacr.getException(var1));
   }

   public final void zaa(GoogleApiManager.zaa<?> var1) throws DeadObjectException {
      try {
         this.zacq.doExecute(var1.zaab(), this.zacm);
      } catch (DeadObjectException var2) {
         throw var2;
      } catch (RemoteException var3) {
         this.zaa(zab.zab(var3));
      } catch (RuntimeException var4) {
         this.zaa(var4);
      }
   }

   public final void zaa(@NonNull zaab var1, boolean var2) {
      var1.zaa(this.zacm, var2);
   }

   public final void zaa(@NonNull RuntimeException var1) {
      this.zacm.b(var1);
   }

   @Nullable
   public final Feature[] zab(GoogleApiManager.zaa<?> var1) {
      return this.zacq.zabt();
   }

   public final boolean zac(GoogleApiManager.zaa<?> var1) {
      return this.zacq.shouldAutoResolveMissingFeatures();
   }
}
