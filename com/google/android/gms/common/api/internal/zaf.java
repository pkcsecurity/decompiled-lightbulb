package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import android.support.annotation.Nullable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.common.api.internal.UnregisterListenerMethod;
import com.google.android.gms.common.api.internal.zabw;
import com.google.android.gms.common.api.internal.zad;

public final class zaf extends zad<Void> {

   private final RegisterListenerMethod<Api.AnyClient, ?> zaco;
   private final UnregisterListenerMethod<Api.AnyClient, ?> zacp;


   public zaf(zabw var1, li<Void> var2) {
      super(3, var2);
      this.zaco = var1.zajw;
      this.zacp = var1.zajx;
   }

   @Nullable
   public final Feature[] zab(GoogleApiManager.zaa<?> var1) {
      return this.zaco.getRequiredFeatures();
   }

   public final boolean zac(GoogleApiManager.zaa<?> var1) {
      return this.zaco.shouldAutoResolveMissingFeatures();
   }

   public final void zad(GoogleApiManager.zaa<?> var1) throws RemoteException {
      this.zaco.registerListener(var1.zaab(), this.zacm);
      if(this.zaco.getListenerKey() != null) {
         var1.zabk().put(this.zaco.getListenerKey(), new zabw(this.zaco, this.zacp));
      }

   }
}
