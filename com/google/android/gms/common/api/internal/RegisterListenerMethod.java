package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import android.support.annotation.Nullable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.ListenerHolder;

@KeepForSdk
public abstract class RegisterListenerMethod<A extends Object & Api.AnyClient, L extends Object> {

   private final ListenerHolder<L> zajt;
   private final Feature[] zaju;
   private final boolean zajv;


   @KeepForSdk
   protected RegisterListenerMethod(ListenerHolder<L> var1) {
      this.zajt = var1;
      this.zaju = null;
      this.zajv = false;
   }

   @KeepForSdk
   protected RegisterListenerMethod(ListenerHolder<L> var1, Feature[] var2, boolean var3) {
      this.zajt = var1;
      this.zaju = var2;
      this.zajv = var3;
   }

   @KeepForSdk
   public void clearListener() {
      this.zajt.clear();
   }

   @KeepForSdk
   public ListenerHolder.ListenerKey<L> getListenerKey() {
      return this.zajt.getListenerKey();
   }

   @Nullable
   @KeepForSdk
   public Feature[] getRequiredFeatures() {
      return this.zaju;
   }

   @KeepForSdk
   public abstract void registerListener(A var1, li<Void> var2) throws RemoteException;

   public final boolean shouldAutoResolveMissingFeatures() {
      return this.zajv;
   }
}
