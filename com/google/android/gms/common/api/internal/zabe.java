package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.SignInConnectionListener;
import com.google.android.gms.common.api.internal.zaah;
import com.google.android.gms.common.api.internal.zaak;
import com.google.android.gms.common.api.internal.zaav;
import com.google.android.gms.common.api.internal.zaaw;
import com.google.android.gms.common.api.internal.zabd;
import com.google.android.gms.common.api.internal.zabf;
import com.google.android.gms.common.api.internal.zabg;
import com.google.android.gms.common.api.internal.zabs;
import com.google.android.gms.common.api.internal.zabt;
import com.google.android.gms.common.api.internal.zaq;
import com.google.android.gms.common.api.internal.zar;
import com.google.android.gms.common.internal.ClientSettings;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

public final class zabe implements zabs, zar {

   private final Context mContext;
   private final Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, kx> zacd;
   final zaaw zaed;
   private final Lock zaen;
   private final ClientSettings zaes;
   private final Map<Api<?>, Boolean> zaev;
   private final GoogleApiAvailabilityLight zaex;
   final Map<Api.AnyClientKey<?>, Api.Client> zagy;
   private final Condition zahm;
   private final zabg zahn;
   final Map<Api.AnyClientKey<?>, ConnectionResult> zaho = new HashMap();
   private volatile zabd zahp;
   private ConnectionResult zahq = null;
   int zahr;
   final zabt zahs;


   public zabe(Context var1, zaaw var2, Lock var3, Looper var4, GoogleApiAvailabilityLight var5, Map<Api.AnyClientKey<?>, Api.Client> var6, ClientSettings var7, Map<Api<?>, Boolean> var8, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, kx> var9, ArrayList<zaq> var10, zabt var11) {
      this.mContext = var1;
      this.zaen = var3;
      this.zaex = var5;
      this.zagy = var6;
      this.zaes = var7;
      this.zaev = var8;
      this.zacd = var9;
      this.zaed = var2;
      this.zahs = var11;
      ArrayList var14 = (ArrayList)var10;
      int var13 = var14.size();
      int var12 = 0;

      while(var12 < var13) {
         Object var15 = var14.get(var12);
         ++var12;
         ((zaq)var15).zaa(this);
      }

      this.zahn = new zabg(this, var4);
      this.zahm = var3.newCondition();
      this.zahp = new zaav(this);
   }

   // $FF: synthetic method
   static Lock zaa(zabe var0) {
      return var0.zaen;
   }

   // $FF: synthetic method
   static zabd zab(zabe var0) {
      return var0.zahp;
   }

   @GuardedBy
   public final ConnectionResult blockingConnect() {
      this.connect();

      while(this.isConnecting()) {
         try {
            this.zahm.await();
         } catch (InterruptedException var2) {
            Thread.currentThread().interrupt();
            return new ConnectionResult(15, (PendingIntent)null);
         }
      }

      return this.isConnected()?ConnectionResult.RESULT_SUCCESS:(this.zahq != null?this.zahq:new ConnectionResult(13, (PendingIntent)null));
   }

   @GuardedBy
   public final ConnectionResult blockingConnect(long param1, TimeUnit param3) {
      // $FF: Couldn't be decompiled
   }

   @GuardedBy
   public final void connect() {
      this.zahp.connect();
   }

   @GuardedBy
   public final void disconnect() {
      if(this.zahp.disconnect()) {
         this.zaho.clear();
      }

   }

   public final void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {
      String var5 = String.valueOf(var1).concat("  ");
      var3.append(var1).append("mState=").println(this.zahp);
      Iterator var6 = this.zaev.keySet().iterator();

      while(var6.hasNext()) {
         Api var7 = (Api)var6.next();
         var3.append(var1).append(var7.getName()).println(":");
         ((Api.Client)this.zagy.get(var7.getClientKey())).dump(var5, var2, var3, var4);
      }

   }

   @GuardedBy
   public final <A extends Object & Api.AnyClient, R extends Object & Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(@NonNull T var1) {
      var1.zau();
      return this.zahp.enqueue(var1);
   }

   @GuardedBy
   public final <A extends Object & Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(@NonNull T var1) {
      var1.zau();
      return this.zahp.execute(var1);
   }

   @Nullable
   @GuardedBy
   public final ConnectionResult getConnectionResult(@NonNull Api<?> var1) {
      Api.AnyClientKey var2 = var1.getClientKey();
      if(this.zagy.containsKey(var2)) {
         if(((Api.Client)this.zagy.get(var2)).isConnected()) {
            return ConnectionResult.RESULT_SUCCESS;
         }

         if(this.zaho.containsKey(var2)) {
            return (ConnectionResult)this.zaho.get(var2);
         }
      }

      return null;
   }

   public final boolean isConnected() {
      return this.zahp instanceof zaah;
   }

   public final boolean isConnecting() {
      return this.zahp instanceof zaak;
   }

   public final boolean maybeSignIn(SignInConnectionListener var1) {
      return false;
   }

   public final void maybeSignOut() {}

   public final void onConnected(@Nullable Bundle var1) {
      this.zaen.lock();

      try {
         this.zahp.onConnected(var1);
      } finally {
         this.zaen.unlock();
      }

   }

   public final void onConnectionSuspended(int var1) {
      this.zaen.lock();

      try {
         this.zahp.onConnectionSuspended(var1);
      } finally {
         this.zaen.unlock();
      }

   }

   public final void zaa(@NonNull ConnectionResult var1, @NonNull Api<?> var2, boolean var3) {
      this.zaen.lock();

      try {
         this.zahp.zaa(var1, var2, var3);
      } finally {
         this.zaen.unlock();
      }

   }

   final void zaa(zabf var1) {
      Message var2 = this.zahn.obtainMessage(1, var1);
      this.zahn.sendMessage(var2);
   }

   final void zaaz() {
      this.zaen.lock();

      try {
         this.zahp = new zaak(this, this.zaes, this.zaev, this.zaex, this.zacd, this.zaen, this.mContext);
         this.zahp.begin();
         this.zahm.signalAll();
      } finally {
         this.zaen.unlock();
      }

   }

   final void zab(RuntimeException var1) {
      Message var2 = this.zahn.obtainMessage(2, var1);
      this.zahn.sendMessage(var2);
   }

   final void zaba() {
      this.zaen.lock();

      try {
         this.zaed.zaaw();
         this.zahp = new zaah(this);
         this.zahp.begin();
         this.zahm.signalAll();
      } finally {
         this.zaen.unlock();
      }

   }

   final void zaf(ConnectionResult var1) {
      this.zaen.lock();

      try {
         this.zahq = var1;
         this.zahp = new zaav(this);
         this.zahp.begin();
         this.zahm.signalAll();
      } finally {
         this.zaen.unlock();
      }

   }

   @GuardedBy
   public final void zaw() {
      if(this.isConnected()) {
         ((zaah)this.zahp).zaam();
      }

   }
}
