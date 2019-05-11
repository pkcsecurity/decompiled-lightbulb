package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.zabd;
import com.google.android.gms.common.api.internal.zabe;
import java.util.Collections;
import java.util.Iterator;

public final class zaav implements zabd {

   private final zabe zafs;


   public zaav(zabe var1) {
      this.zafs = var1;
   }

   public final void begin() {
      Iterator var1 = this.zafs.zagy.values().iterator();

      while(var1.hasNext()) {
         ((Api.Client)var1.next()).disconnect();
      }

      this.zafs.zaed.zagz = Collections.emptySet();
   }

   public final void connect() {
      this.zafs.zaaz();
   }

   public final boolean disconnect() {
      return true;
   }

   public final <A extends Object & Api.AnyClient, R extends Object & Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(T var1) {
      this.zafs.zaed.zafb.add(var1);
      return var1;
   }

   public final <A extends Object & Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(T var1) {
      throw new IllegalStateException("GoogleApiClient is not connected yet.");
   }

   public final void onConnected(Bundle var1) {}

   public final void onConnectionSuspended(int var1) {}

   public final void zaa(ConnectionResult var1, Api<?> var2, boolean var3) {}
}
