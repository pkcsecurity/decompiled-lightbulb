package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.BinderThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.internal.zacf;
import com.google.android.gms.common.api.internal.zacg;
import com.google.android.gms.common.api.internal.zach;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import java.util.Set;

public final class zace extends com.google.android.gms.signin.internal.zac implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

   private static Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, kx> zakh = lc.a;
   private final Context mContext;
   private final Handler mHandler;
   private Set<Scope> mScopes;
   private final Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, kx> zaau;
   private ClientSettings zaes;
   private com.google.android.gms.signin.zad zaga;
   private zach zaki;


   @WorkerThread
   public zace(Context var1, Handler var2, @NonNull ClientSettings var3) {
      this(var1, var2, var3, zakh);
   }

   @WorkerThread
   public zace(Context var1, Handler var2, @NonNull ClientSettings var3, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, kx> var4) {
      this.mContext = var1;
      this.mHandler = var2;
      this.zaes = (ClientSettings)Preconditions.checkNotNull(var3, "ClientSettings must not be null");
      this.mScopes = var3.getRequiredScopes();
      this.zaau = var4;
   }

   // $FF: synthetic method
   static zach zaa(zace var0) {
      return var0.zaki;
   }

   // $FF: synthetic method
   static void zaa(zace var0, com.google.android.gms.signin.internal.zaj var1) {
      var0.zac(var1);
   }

   @WorkerThread
   private final void zac(com.google.android.gms.signin.internal.zaj var1) {
      ConnectionResult var2 = var1.a();
      if(var2.isSuccess()) {
         ResolveAccountResponse var5 = var1.b();
         ConnectionResult var4 = var5.getConnectionResult();
         if(!var4.isSuccess()) {
            String var6 = String.valueOf(var4);
            StringBuilder var3 = new StringBuilder(String.valueOf(var6).length() + 48);
            var3.append("Sign-in succeeded with resolve account failure: ");
            var3.append(var6);
            Log.wtf("SignInCoordinator", var3.toString(), new Exception());
            this.zaki.zag(var4);
            this.zaga.disconnect();
            return;
         }

         this.zaki.zaa(var5.getAccountAccessor(), this.mScopes);
      } else {
         this.zaki.zag(var2);
      }

      this.zaga.disconnect();
   }

   @WorkerThread
   public final void onConnected(@Nullable Bundle var1) {
      this.zaga.a(this);
   }

   @WorkerThread
   public final void onConnectionFailed(@NonNull ConnectionResult var1) {
      this.zaki.zag(var1);
   }

   @WorkerThread
   public final void onConnectionSuspended(int var1) {
      this.zaga.disconnect();
   }

   @WorkerThread
   public final void zaa(zach var1) {
      if(this.zaga != null) {
         this.zaga.disconnect();
      }

      this.zaes.setClientSessionId(Integer.valueOf(System.identityHashCode(this)));
      this.zaga = (com.google.android.gms.signin.zad)this.zaau.buildClient(this.mContext, this.mHandler.getLooper(), this.zaes, this.zaes.getSignInOptions(), this, this);
      this.zaki = var1;
      if(this.mScopes != null && !this.mScopes.isEmpty()) {
         this.zaga.b();
      } else {
         this.mHandler.post(new zacf(this));
      }
   }

   @BinderThread
   public final void zab(com.google.android.gms.signin.internal.zaj var1) {
      this.mHandler.post(new zacg(this, var1));
   }

   public final com.google.android.gms.signin.zad zabq() {
      return this.zaga;
   }

   public final void zabs() {
      if(this.zaga != null) {
         this.zaga.disconnect();
      }

   }
}
