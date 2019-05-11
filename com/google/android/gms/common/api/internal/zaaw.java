package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.LifecycleActivity;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.common.api.internal.SignInConnectionListener;
import com.google.android.gms.common.api.internal.StatusPendingResult;
import com.google.android.gms.common.api.internal.zaax;
import com.google.android.gms.common.api.internal.zaay;
import com.google.android.gms.common.api.internal.zaaz;
import com.google.android.gms.common.api.internal.zaba;
import com.google.android.gms.common.api.internal.zabb;
import com.google.android.gms.common.api.internal.zabc;
import com.google.android.gms.common.api.internal.zabe;
import com.google.android.gms.common.api.internal.zabq;
import com.google.android.gms.common.api.internal.zabr;
import com.google.android.gms.common.api.internal.zabs;
import com.google.android.gms.common.api.internal.zabt;
import com.google.android.gms.common.api.internal.zacm;
import com.google.android.gms.common.api.internal.zacp;
import com.google.android.gms.common.api.internal.zaj;
import com.google.android.gms.common.api.internal.zaq;
import com.google.android.gms.common.api.internal.zas;
import com.google.android.gms.common.api.internal.zax;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClientEventManager;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.common.util.ClientLibraryUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

public final class zaaw extends GoogleApiClient implements zabt {

   private final Context mContext;
   private final Looper zabj;
   private final int zaca;
   private final GoogleApiAvailability zacc;
   private final Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, kx> zacd;
   private boolean zacg;
   private final Lock zaen;
   private final ClientSettings zaes;
   private final Map<Api<?>, Boolean> zaev;
   @VisibleForTesting
   final Queue<BaseImplementation.ApiMethodImpl<?, ?>> zafb = new LinkedList();
   private final GmsClientEventManager zagr;
   private zabs zags = null;
   private volatile boolean zagt;
   private long zagu;
   private long zagv;
   private final zabb zagw;
   @VisibleForTesting
   private zabq zagx;
   final Map<Api.AnyClientKey<?>, Api.Client> zagy;
   Set<Scope> zagz;
   private final ListenerHolders zaha;
   private final ArrayList<zaq> zahb;
   private Integer zahc;
   Set<zacm> zahd;
   final zacp zahe;
   private final GmsClientEventManager.GmsClientEventState zahf;


   public zaaw(Context var1, Lock var2, Looper var3, ClientSettings var4, GoogleApiAvailability var5, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, kx> var6, Map<Api<?>, Boolean> var7, List<GoogleApiClient.ConnectionCallbacks> var8, List<GoogleApiClient.OnConnectionFailedListener> var9, Map<Api.AnyClientKey<?>, Api.Client> var10, int var11, int var12, ArrayList<zaq> var13, boolean var14) {
      long var15;
      if(ClientLibraryUtils.isPackageSide()) {
         var15 = 10000L;
      } else {
         var15 = 120000L;
      }

      this.zagu = var15;
      this.zagv = 5000L;
      this.zagz = new HashSet();
      this.zaha = new ListenerHolders();
      this.zahc = null;
      this.zahd = null;
      this.zahf = new zaax(this);
      this.mContext = var1;
      this.zaen = var2;
      this.zacg = false;
      this.zagr = new GmsClientEventManager(var3, this.zahf);
      this.zabj = var3;
      this.zagw = new zabb(this, var3);
      this.zacc = var5;
      this.zaca = var11;
      if(this.zaca >= 0) {
         this.zahc = Integer.valueOf(var12);
      }

      this.zaev = var7;
      this.zagy = var10;
      this.zahb = var13;
      this.zahe = new zacp(this.zagy);
      Iterator var17 = var8.iterator();

      while(var17.hasNext()) {
         GoogleApiClient.ConnectionCallbacks var18 = (GoogleApiClient.ConnectionCallbacks)var17.next();
         this.zagr.registerConnectionCallbacks(var18);
      }

      var17 = var9.iterator();

      while(var17.hasNext()) {
         GoogleApiClient.OnConnectionFailedListener var19 = (GoogleApiClient.OnConnectionFailedListener)var17.next();
         this.zagr.registerConnectionFailedListener(var19);
      }

      this.zaes = var4;
      this.zacd = var6;
   }

   private final void resume() {
      this.zaen.lock();

      try {
         if(this.zagt) {
            this.zaau();
         }
      } finally {
         this.zaen.unlock();
      }

   }

   public static int zaa(Iterable<Api.Client> var0, boolean var1) {
      Iterator var6 = var0.iterator();
      boolean var3 = false;
      boolean var2 = false;

      while(var6.hasNext()) {
         Api.Client var5 = (Api.Client)var6.next();
         boolean var4 = var3;
         if(var5.requiresSignIn()) {
            var4 = true;
         }

         var3 = var4;
         if(var5.providesSignIn()) {
            var2 = true;
            var3 = var4;
         }
      }

      if(var3) {
         if(var2 && var1) {
            return 2;
         } else {
            return 1;
         }
      } else {
         return 3;
      }
   }

   private final void zaa(GoogleApiClient var1, StatusPendingResult var2, boolean var3) {
      Common.zaph.zaa(var1).setResultCallback(new zaba(this, var2, var3, var1));
   }

   // $FF: synthetic method
   static void zaa(zaaw var0) {
      var0.resume();
   }

   // $FF: synthetic method
   static void zaa(zaaw var0, GoogleApiClient var1, StatusPendingResult var2, boolean var3) {
      var0.zaa(var1, var2, true);
   }

   @GuardedBy
   private final void zaau() {
      this.zagr.enableCallbacks();
      this.zags.connect();
   }

   private final void zaav() {
      this.zaen.lock();

      try {
         if(this.zaaw()) {
            this.zaau();
         }
      } finally {
         this.zaen.unlock();
      }

   }

   // $FF: synthetic method
   static void zab(zaaw var0) {
      var0.zaav();
   }

   // $FF: synthetic method
   static Context zac(zaaw var0) {
      return var0.mContext;
   }

   private final void zae(int var1) {
      if(this.zahc == null) {
         this.zahc = Integer.valueOf(var1);
      } else if(this.zahc.intValue() != var1) {
         String var8 = zaf(var1);
         String var9 = zaf(this.zahc.intValue());
         StringBuilder var6 = new StringBuilder(String.valueOf(var8).length() + 51 + String.valueOf(var9).length());
         var6.append("Cannot use sign-in mode: ");
         var6.append(var8);
         var6.append(". Mode was already set to ");
         var6.append(var9);
         throw new IllegalStateException(var6.toString());
      }

      if(this.zags == null) {
         Iterator var4 = this.zagy.values().iterator();
         boolean var2 = false;
         boolean var7 = false;

         while(var4.hasNext()) {
            Api.Client var5 = (Api.Client)var4.next();
            boolean var3 = var2;
            if(var5.requiresSignIn()) {
               var3 = true;
            }

            var2 = var3;
            if(var5.providesSignIn()) {
               var7 = true;
               var2 = var3;
            }
         }

         switch(this.zahc.intValue()) {
         case 1:
            if(!var2) {
               throw new IllegalStateException("SIGN_IN_MODE_REQUIRED cannot be used on a GoogleApiClient that does not contain any authenticated APIs. Use connect() instead.");
            }

            if(var7) {
               throw new IllegalStateException("Cannot use SIGN_IN_MODE_REQUIRED with GOOGLE_SIGN_IN_API. Use connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            break;
         case 2:
            if(var2) {
               if(this.zacg) {
                  this.zags = new zax(this.mContext, this.zaen, this.zabj, this.zacc, this.zagy, this.zaes, this.zaev, this.zacd, this.zahb, this, true);
                  return;
               }

               this.zags = zas.zaa(this.mContext, this, this.zaen, this.zabj, this.zacc, this.zagy, this.zaes, this.zaev, this.zacd, this.zahb);
               return;
            }
         case 3:
         }

         if(this.zacg && !var7) {
            this.zags = new zax(this.mContext, this.zaen, this.zabj, this.zacc, this.zagy, this.zaes, this.zaev, this.zacd, this.zahb, this, false);
         } else {
            this.zags = new zabe(this.mContext, this, this.zaen, this.zabj, this.zacc, this.zagy, this.zaes, this.zaev, this.zacd, this.zahb, this);
         }
      }
   }

   private static String zaf(int var0) {
      switch(var0) {
      case 1:
         return "SIGN_IN_MODE_REQUIRED";
      case 2:
         return "SIGN_IN_MODE_OPTIONAL";
      case 3:
         return "SIGN_IN_MODE_NONE";
      default:
         return "UNKNOWN";
      }
   }

   public final ConnectionResult blockingConnect() {
      // $FF: Couldn't be decompiled
   }

   public final ConnectionResult blockingConnect(long var1, @NonNull TimeUnit var3) {
      boolean var4;
      if(Looper.myLooper() != Looper.getMainLooper()) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkState(var4, "blockingConnect must not be called on the UI thread");
      Preconditions.checkNotNull(var3, "TimeUnit must not be null");
      this.zaen.lock();

      ConnectionResult var7;
      try {
         if(this.zahc == null) {
            this.zahc = Integer.valueOf(zaa(this.zagy.values(), false));
         } else if(this.zahc.intValue() == 2) {
            throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
         }

         this.zae(this.zahc.intValue());
         this.zagr.enableCallbacks();
         var7 = this.zags.blockingConnect(var1, var3);
      } finally {
         this.zaen.unlock();
      }

      return var7;
   }

   public final PendingResult<Status> clearDefaultAccountAndReconnect() {
      Preconditions.checkState(this.isConnected(), "GoogleApiClient is not connected yet.");
      boolean var1;
      if(this.zahc.intValue() != 2) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkState(var1, "Cannot use clearDefaultAccountAndReconnect with GOOGLE_SIGN_IN_API");
      StatusPendingResult var2 = new StatusPendingResult(this);
      if(this.zagy.containsKey(Common.CLIENT_KEY)) {
         this.zaa(this, var2, false);
         return var2;
      } else {
         AtomicReference var3 = new AtomicReference();
         zaay var4 = new zaay(this, var3, var2);
         zaaz var5 = new zaaz(this, var2);
         GoogleApiClient var6 = (new GoogleApiClient.Builder(this.mContext)).addApi(Common.API).addConnectionCallbacks(var4).addOnConnectionFailedListener(var5).setHandler(this.zagw).build();
         var3.set(var6);
         var6.connect();
         return var2;
      }
   }

   public final void connect() {
      // $FF: Couldn't be decompiled
   }

   public final void connect(int var1) {
      this.zaen.lock();
      boolean var3 = true;
      boolean var2 = var3;
      if(var1 != 3) {
         var2 = var3;
         if(var1 != 1) {
            if(var1 == 2) {
               var2 = var3;
            } else {
               var2 = false;
            }
         }
      }

      try {
         StringBuilder var4 = new StringBuilder(33);
         var4.append("Illegal sign-in mode: ");
         var4.append(var1);
         Preconditions.checkArgument(var2, var4.toString());
         this.zae(var1);
         this.zaau();
      } finally {
         this.zaen.unlock();
      }

   }

   public final void disconnect() {
      // $FF: Couldn't be decompiled
   }

   public final void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {
      var3.append(var1).append("mContext=").println(this.mContext);
      var3.append(var1).append("mResuming=").print(this.zagt);
      var3.append(" mWorkQueue.size()=").print(this.zafb.size());
      zacp var5 = this.zahe;
      var3.append(" mUnconsumedApiCalls.size()=").println(var5.zaky.size());
      if(this.zags != null) {
         this.zags.dump(var1, var2, var3, var4);
      }

   }

   public final <A extends Object & Api.AnyClient, R extends Object & Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(@NonNull T var1) {
      boolean var2;
      if(var1.getClientKey() != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "This task can not be enqueued (it\'s probably a Batch or malformed)");
      var2 = this.zagy.containsKey(var1.getClientKey());
      String var3;
      if(var1.getApi() != null) {
         var3 = var1.getApi().getName();
      } else {
         var3 = "the API";
      }

      StringBuilder var4 = new StringBuilder(String.valueOf(var3).length() + 65);
      var4.append("GoogleApiClient is not configured to use ");
      var4.append(var3);
      var4.append(" required for this call.");
      Preconditions.checkArgument(var2, var4.toString());
      this.zaen.lock();

      try {
         if(this.zags == null) {
            this.zafb.add(var1);
            return var1;
         }

         var1 = this.zags.enqueue(var1);
      } finally {
         this.zaen.unlock();
      }

      return var1;
   }

   public final <A extends Object & Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(@NonNull T var1) {
      boolean var2;
      if(var1.getClientKey() != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "This task can not be executed (it\'s probably a Batch or malformed)");
      var2 = this.zagy.containsKey(var1.getClientKey());
      String var3;
      if(var1.getApi() != null) {
         var3 = var1.getApi().getName();
      } else {
         var3 = "the API";
      }

      StringBuilder var4 = new StringBuilder(String.valueOf(var3).length() + 65);
      var4.append("GoogleApiClient is not configured to use ");
      var4.append(var3);
      var4.append(" required for this call.");
      Preconditions.checkArgument(var2, var4.toString());
      this.zaen.lock();

      try {
         if(this.zags == null) {
            throw new IllegalStateException("GoogleApiClient is not connected yet.");
         }

         if(!this.zagt) {
            var1 = this.zags.execute(var1);
            return var1;
         }

         this.zafb.add(var1);

         while(!this.zafb.isEmpty()) {
            BaseImplementation.ApiMethodImpl var7 = (BaseImplementation.ApiMethodImpl)this.zafb.remove();
            this.zahe.zab(var7);
            var7.setFailedResult(Status.RESULT_INTERNAL_ERROR);
         }
      } finally {
         this.zaen.unlock();
      }

      return var1;
   }

   @NonNull
   public final <C extends Object & Api.Client> C getClient(@NonNull Api.AnyClientKey<C> var1) {
      Api.Client var2 = (Api.Client)this.zagy.get(var1);
      Preconditions.checkNotNull(var2, "Appropriate Api was not requested.");
      return var2;
   }

   @NonNull
   public final ConnectionResult getConnectionResult(@NonNull Api<?> param1) {
      // $FF: Couldn't be decompiled
   }

   public final Context getContext() {
      return this.mContext;
   }

   public final Looper getLooper() {
      return this.zabj;
   }

   public final boolean hasApi(@NonNull Api<?> var1) {
      return this.zagy.containsKey(var1.getClientKey());
   }

   public final boolean hasConnectedApi(@NonNull Api<?> var1) {
      if(!this.isConnected()) {
         return false;
      } else {
         Api.Client var2 = (Api.Client)this.zagy.get(var1.getClientKey());
         return var2 != null && var2.isConnected();
      }
   }

   public final boolean isConnected() {
      return this.zags != null && this.zags.isConnected();
   }

   public final boolean isConnecting() {
      return this.zags != null && this.zags.isConnecting();
   }

   public final boolean isConnectionCallbacksRegistered(@NonNull GoogleApiClient.ConnectionCallbacks var1) {
      return this.zagr.isConnectionCallbacksRegistered(var1);
   }

   public final boolean isConnectionFailedListenerRegistered(@NonNull GoogleApiClient.OnConnectionFailedListener var1) {
      return this.zagr.isConnectionFailedListenerRegistered(var1);
   }

   public final boolean maybeSignIn(SignInConnectionListener var1) {
      return this.zags != null && this.zags.maybeSignIn(var1);
   }

   public final void maybeSignOut() {
      if(this.zags != null) {
         this.zags.maybeSignOut();
      }

   }

   public final void reconnect() {
      this.disconnect();
      this.connect();
   }

   public final void registerConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks var1) {
      this.zagr.registerConnectionCallbacks(var1);
   }

   public final void registerConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener var1) {
      this.zagr.registerConnectionFailedListener(var1);
   }

   public final <L extends Object> ListenerHolder<L> registerListener(@NonNull L var1) {
      this.zaen.lock();

      ListenerHolder var4;
      try {
         var4 = this.zaha.zaa(var1, this.zabj, "NO_TYPE");
      } finally {
         this.zaen.unlock();
      }

      return var4;
   }

   public final void stopAutoManage(@NonNull FragmentActivity var1) {
      LifecycleActivity var2 = new LifecycleActivity(var1);
      if(this.zaca >= 0) {
         zaj.zaa(var2).zaa(this.zaca);
      } else {
         throw new IllegalStateException("Called stopAutoManage but automatic lifecycle management is not enabled.");
      }
   }

   public final void unregisterConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks var1) {
      this.zagr.unregisterConnectionCallbacks(var1);
   }

   public final void unregisterConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener var1) {
      this.zagr.unregisterConnectionFailedListener(var1);
   }

   public final void zaa(zacm var1) {
      this.zaen.lock();

      try {
         if(this.zahd == null) {
            this.zahd = new HashSet();
         }

         this.zahd.add(var1);
      } finally {
         this.zaen.unlock();
      }

   }

   @GuardedBy
   final boolean zaaw() {
      if(!this.zagt) {
         return false;
      } else {
         this.zagt = false;
         this.zagw.removeMessages(2);
         this.zagw.removeMessages(1);
         if(this.zagx != null) {
            this.zagx.unregister();
            this.zagx = null;
         }

         return true;
      }
   }

   final boolean zaax() {
      // $FF: Couldn't be decompiled
   }

   final String zaay() {
      StringWriter var1 = new StringWriter();
      this.dump("", (FileDescriptor)null, new PrintWriter(var1), (String[])null);
      return var1.toString();
   }

   @GuardedBy
   public final void zab(int var1, boolean var2) {
      if(var1 == 1 && !var2 && !this.zagt) {
         this.zagt = true;
         if(this.zagx == null && !ClientLibraryUtils.isPackageSide()) {
            this.zagx = this.zacc.zaa(this.mContext.getApplicationContext(), (zabr)(new zabc(this)));
         }

         this.zagw.sendMessageDelayed(this.zagw.obtainMessage(1), this.zagu);
         this.zagw.sendMessageDelayed(this.zagw.obtainMessage(2), this.zagv);
      }

      this.zahe.zabx();
      this.zagr.onUnintentionalDisconnection(var1);
      this.zagr.disableCallbacks();
      if(var1 == 2) {
         this.zaau();
      }

   }

   @GuardedBy
   public final void zab(Bundle var1) {
      while(!this.zafb.isEmpty()) {
         this.execute((BaseImplementation.ApiMethodImpl)this.zafb.remove());
      }

      this.zagr.onConnectionSuccess(var1);
   }

   public final void zab(zacm var1) {
      this.zaen.lock();

      try {
         if(this.zahd == null) {
            Log.wtf("GoogleApiClientImpl", "Attempted to remove pending transform when no transforms are registered.", new Exception());
         } else if(!this.zahd.remove(var1)) {
            Log.wtf("GoogleApiClientImpl", "Failed to remove pending transform - this may lead to memory leaks!", new Exception());
         } else if(!this.zaax()) {
            this.zags.zaw();
         }
      } finally {
         this.zaen.unlock();
      }

   }

   @GuardedBy
   public final void zac(ConnectionResult var1) {
      if(!this.zacc.isPlayServicesPossiblyUpdating(this.mContext, var1.getErrorCode())) {
         this.zaaw();
      }

      if(!this.zagt) {
         this.zagr.onConnectionFailure(var1);
         this.zagr.disableCallbacks();
      }

   }
}
