package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.zace;
import com.google.android.gms.common.api.internal.zaq;
import com.google.android.gms.common.internal.ClientSettings;

public final class zaw<O extends Object & Api.ApiOptions> extends GoogleApi<O> {

   private final Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, kx> zacd;
   private final Api.Client zaeq;
   private final zaq zaer;
   private final ClientSettings zaes;


   public zaw(@NonNull Context var1, Api<O> var2, Looper var3, @NonNull Api.Client var4, @NonNull zaq var5, ClientSettings var6, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, kx> var7) {
      super(var1, var2, var3);
      this.zaeq = var4;
      this.zaer = var5;
      this.zaes = var6;
      this.zacd = var7;
      this.zabm.zaa((GoogleApi)this);
   }

   public final Api.Client zaa(Looper var1, GoogleApiManager.zaa<O> var2) {
      this.zaer.zaa(var2);
      return this.zaeq;
   }

   public final zace zaa(Context var1, Handler var2) {
      return new zace(var1, var2, this.zaes, this.zacd);
   }

   public final Api.Client zaab() {
      return this.zaeq;
   }
}
