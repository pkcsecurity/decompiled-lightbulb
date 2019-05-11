package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.ListenerHolder;

@KeepForSdk
public abstract class UnregisterListenerMethod<A extends Object & Api.AnyClient, L extends Object> {

   private final ListenerHolder.ListenerKey<L> zajk;


   @KeepForSdk
   protected UnregisterListenerMethod(ListenerHolder.ListenerKey<L> var1) {
      this.zajk = var1;
   }

   @KeepForSdk
   public ListenerHolder.ListenerKey<L> getListenerKey() {
      return this.zajk;
   }

   @KeepForSdk
   public abstract void unregisterListener(A var1, li<Boolean> var2) throws RemoteException;
}
