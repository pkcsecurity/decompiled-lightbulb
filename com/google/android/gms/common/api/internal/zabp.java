package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.zaag;
import com.google.android.gms.common.api.internal.zacm;

public final class zabp<O extends Object & Api.ApiOptions> extends zaag {

   private final GoogleApi<O> zajg;


   public zabp(GoogleApi<O> var1) {
      super("Method is not supported by connectionless client. APIs supporting connectionless client must not call this method.");
      this.zajg = var1;
   }

   public final <A extends Object & Api.AnyClient, R extends Object & Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(@NonNull T var1) {
      return this.zajg.doRead(var1);
   }

   public final <A extends Object & Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(@NonNull T var1) {
      return this.zajg.doWrite(var1);
   }

   public final Context getContext() {
      return this.zajg.getApplicationContext();
   }

   public final Looper getLooper() {
      return this.zajg.getLooper();
   }

   public final void zaa(zacm var1) {}

   public final void zab(zacm var1) {}
}
