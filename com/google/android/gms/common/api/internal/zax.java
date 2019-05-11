package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.SignInConnectionListener;
import com.google.android.gms.common.api.internal.zaaa;
import com.google.android.gms.common.api.internal.zaaw;
import com.google.android.gms.common.api.internal.zabs;
import com.google.android.gms.common.api.internal.zacs;
import com.google.android.gms.common.api.internal.zai;
import com.google.android.gms.common.api.internal.zaq;
import com.google.android.gms.common.api.internal.zaw;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.util.concurrent.HandlerExecutor;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

public final class zax implements zabs {

   private final Looper zabj;
   private final GoogleApiManager zabm;
   private final Lock zaen;
   private final ClientSettings zaes;
   private final Map<Api.AnyClientKey<?>, zaw<?>> zaet = new HashMap();
   private final Map<Api.AnyClientKey<?>, zaw<?>> zaeu = new HashMap();
   private final Map<Api<?>, Boolean> zaev;
   private final zaaw zaew;
   private final GoogleApiAvailabilityLight zaex;
   private final Condition zaey;
   private final boolean zaez;
   private final boolean zafa;
   private final Queue<BaseImplementation.ApiMethodImpl<?, ?>> zafb = new LinkedList();
   @GuardedBy
   private boolean zafc;
   @GuardedBy
   private Map<zai<?>, ConnectionResult> zafd;
   @GuardedBy
   private Map<zai<?>, ConnectionResult> zafe;
   @GuardedBy
   private zaaa zaff;
   @GuardedBy
   private ConnectionResult zafg;


   public zax(Context var1, Lock var2, Looper var3, GoogleApiAvailabilityLight var4, Map<Api.AnyClientKey<?>, Api.Client> var5, ClientSettings var6, Map<Api<?>, Boolean> var7, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, kx> var8, ArrayList<zaq> var9, zaaw var10, boolean var11) {
      this.zaen = var2;
      this.zabj = var3;
      this.zaey = var2.newCondition();
      this.zaex = var4;
      this.zaew = var10;
      this.zaev = var7;
      this.zaes = var6;
      this.zaez = var11;
      HashMap var16 = new HashMap();
      Iterator var17 = var7.keySet().iterator();

      while(var17.hasNext()) {
         Api var20 = (Api)var17.next();
         var16.put(var20.getClientKey(), var20);
      }

      HashMap var18 = new HashMap();
      ArrayList var21 = (ArrayList)var9;
      int var13 = var21.size();
      int var12 = 0;

      while(var12 < var13) {
         Object var23 = var21.get(var12);
         ++var12;
         zaq var24 = (zaq)var23;
         var18.put(var24.mApi, var24);
      }

      Iterator var19 = var5.entrySet().iterator();
      boolean var14 = false;
      boolean var29 = true;

      boolean var15;
      boolean var28;
      for(var28 = false; var19.hasNext(); var28 = var15) {
         Entry var22 = (Entry)var19.next();
         Api var26 = (Api)var16.get(var22.getKey());
         Api.Client var25 = (Api.Client)var22.getValue();
         if(var25.requiresGooglePlayServices()) {
            if(!((Boolean)this.zaev.get(var26)).booleanValue()) {
               var14 = true;
            } else {
               var14 = var28;
            }

            var28 = true;
         } else {
            var29 = var14;
            var14 = var28;
            var15 = false;
            var28 = var29;
            var29 = var15;
         }

         zaw var27 = new zaw(var1, var26, var3, var25, (zaq)var18.get(var26), var6, var8);
         this.zaet.put((Api.AnyClientKey)var22.getKey(), var27);
         if(var25.requiresSignIn()) {
            this.zaeu.put((Api.AnyClientKey)var22.getKey(), var27);
         }

         var15 = var14;
         var14 = var28;
      }

      if(var14 && !var29 && !var28) {
         var11 = true;
      } else {
         var11 = false;
      }

      this.zafa = var11;
      this.zabm = GoogleApiManager.zabc();
   }

   @Nullable
   private final ConnectionResult zaa(@NonNull Api.AnyClientKey<?> param1) {
      // $FF: Couldn't be decompiled
   }

   // $FF: synthetic method
   static ConnectionResult zaa(zax var0, ConnectionResult var1) {
      var0.zafg = var1;
      return var1;
   }

   // $FF: synthetic method
   static Map zaa(zax var0, Map var1) {
      var0.zafd = var1;
      return var1;
   }

   // $FF: synthetic method
   static Lock zaa(zax var0) {
      return var0.zaen;
   }

   private final boolean zaa(zaw<?> var1, ConnectionResult var2) {
      return !var2.isSuccess() && !var2.hasResolution() && ((Boolean)this.zaev.get(var1.getApi())).booleanValue() && var1.zaab().requiresGooglePlayServices() && this.zaex.isUserResolvableError(var2.getErrorCode());
   }

   // $FF: synthetic method
   static boolean zaa(zax var0, zaw var1, ConnectionResult var2) {
      return var0.zaa(var1, var2);
   }

   // $FF: synthetic method
   static boolean zaa(zax var0, boolean var1) {
      var0.zafc = false;
      return false;
   }

   private final boolean zaac() {
      // $FF: Couldn't be decompiled
   }

   @GuardedBy
   private final void zaad() {
      if(this.zaes == null) {
         this.zaew.zagz = Collections.emptySet();
      } else {
         HashSet var1 = new HashSet(this.zaes.getRequiredScopes());
         Map var2 = this.zaes.getOptionalApiSettings();
         Iterator var3 = var2.keySet().iterator();

         while(var3.hasNext()) {
            Api var4 = (Api)var3.next();
            ConnectionResult var5 = this.getConnectionResult(var4);
            if(var5 != null && var5.isSuccess()) {
               var1.addAll(((ClientSettings.OptionalApiSettings)var2.get(var4)).mScopes);
            }
         }

         this.zaew.zagz = var1;
      }
   }

   @GuardedBy
   private final void zaae() {
      while(!this.zafb.isEmpty()) {
         this.execute((BaseImplementation.ApiMethodImpl)this.zafb.remove());
      }

      this.zaew.zab((Bundle)null);
   }

   @Nullable
   @GuardedBy
   private final ConnectionResult zaaf() {
      Iterator var7 = this.zaet.values().iterator();
      ConnectionResult var4 = null;
      ConnectionResult var5 = null;
      int var2 = 0;
      int var1 = 0;

      while(var7.hasNext()) {
         zaw var6 = (zaw)var7.next();
         Api var8 = var6.getApi();
         zai var9 = var6.zak();
         ConnectionResult var10 = (ConnectionResult)this.zafd.get(var9);
         if(!var10.isSuccess() && (!((Boolean)this.zaev.get(var8)).booleanValue() || var10.hasResolution() || this.zaex.isUserResolvableError(var10.getErrorCode()))) {
            int var3;
            if(var10.getErrorCode() == 4 && this.zaez) {
               var3 = var8.zah().getPriority();
               if(var5 == null || var1 > var3) {
                  var5 = var10;
                  var1 = var3;
               }
            } else {
               var3 = var8.zah().getPriority();
               if(var4 == null || var2 > var3) {
                  var4 = var10;
                  var2 = var3;
               }
            }
         }
      }

      if(var4 != null && var5 != null && var2 > var1) {
         return var5;
      } else {
         return var4;
      }
   }

   // $FF: synthetic method
   static Map zab(zax var0, Map var1) {
      var0.zafe = var1;
      return var1;
   }

   private final <T extends BaseImplementation.ApiMethodImpl<? extends Result, ? extends Api.AnyClient>> boolean zab(@NonNull T var1) {
      Api.AnyClientKey var2 = var1.getClientKey();
      ConnectionResult var3 = this.zaa(var2);
      if(var3 != null && var3.getErrorCode() == 4) {
         var1.setFailedResult(new Status(4, (String)null, this.zabm.zaa(((zaw)this.zaet.get(var2)).zak(), System.identityHashCode(this.zaew))));
         return true;
      } else {
         return false;
      }
   }

   // $FF: synthetic method
   static boolean zab(zax var0) {
      return var0.zafc;
   }

   // $FF: synthetic method
   static Map zac(zax var0) {
      return var0.zaet;
   }

   // $FF: synthetic method
   static Map zad(zax var0) {
      return var0.zafd;
   }

   // $FF: synthetic method
   static boolean zae(zax var0) {
      return var0.zafa;
   }

   // $FF: synthetic method
   static ConnectionResult zaf(zax var0) {
      return var0.zaaf();
   }

   // $FF: synthetic method
   static Map zag(zax var0) {
      return var0.zafe;
   }

   // $FF: synthetic method
   static ConnectionResult zah(zax var0) {
      return var0.zafg;
   }

   // $FF: synthetic method
   static void zai(zax var0) {
      var0.zaad();
   }

   // $FF: synthetic method
   static void zaj(zax var0) {
      var0.zaae();
   }

   // $FF: synthetic method
   static zaaw zak(zax var0) {
      return var0.zaew;
   }

   // $FF: synthetic method
   static Condition zal(zax var0) {
      return var0.zaey;
   }

   // $FF: synthetic method
   static Map zam(zax var0) {
      return var0.zaeu;
   }

   @GuardedBy
   public final ConnectionResult blockingConnect() {
      this.connect();

      while(this.isConnecting()) {
         try {
            this.zaey.await();
         } catch (InterruptedException var2) {
            Thread.currentThread().interrupt();
            return new ConnectionResult(15, (PendingIntent)null);
         }
      }

      return this.isConnected()?ConnectionResult.RESULT_SUCCESS:(this.zafg != null?this.zafg:new ConnectionResult(13, (PendingIntent)null));
   }

   @GuardedBy
   public final ConnectionResult blockingConnect(long param1, TimeUnit param3) {
      // $FF: Couldn't be decompiled
   }

   public final void connect() {
      // $FF: Couldn't be decompiled
   }

   public final void disconnect() {
      this.zaen.lock();

      try {
         this.zafc = false;
         this.zafd = null;
         this.zafe = null;
         if(this.zaff != null) {
            this.zaff.cancel();
            this.zaff = null;
         }

         this.zafg = null;

         while(!this.zafb.isEmpty()) {
            BaseImplementation.ApiMethodImpl var1 = (BaseImplementation.ApiMethodImpl)this.zafb.remove();
            var1.zaa((zacs)null);
            var1.cancel();
         }

         this.zaey.signalAll();
      } finally {
         this.zaen.unlock();
      }
   }

   public final void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {}

   public final <A extends Object & Api.AnyClient, R extends Object & Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(@NonNull T var1) {
      if(this.zaez && this.zab(var1)) {
         return var1;
      } else if(!this.isConnected()) {
         this.zafb.add(var1);
         return var1;
      } else {
         this.zaew.zahe.zab(var1);
         return ((zaw)this.zaet.get(var1.getClientKey())).doRead(var1);
      }
   }

   public final <A extends Object & Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(@NonNull T var1) {
      Api.AnyClientKey var2 = var1.getClientKey();
      if(this.zaez && this.zab(var1)) {
         return var1;
      } else {
         this.zaew.zahe.zab(var1);
         return ((zaw)this.zaet.get(var2)).doWrite(var1);
      }
   }

   @Nullable
   public final ConnectionResult getConnectionResult(@NonNull Api<?> var1) {
      return this.zaa(var1.getClientKey());
   }

   public final boolean isConnected() {
      this.zaen.lock();
      boolean var4 = false;

      boolean var1;
      label46: {
         label45: {
            ConnectionResult var2;
            try {
               var4 = true;
               if(this.zafd == null) {
                  var4 = false;
                  break label45;
               }

               var2 = this.zafg;
               var4 = false;
            } finally {
               if(var4) {
                  this.zaen.unlock();
               }
            }

            if(var2 == null) {
               var1 = true;
               break label46;
            }
         }

         var1 = false;
      }

      this.zaen.unlock();
      return var1;
   }

   public final boolean isConnecting() {
      this.zaen.lock();
      boolean var4 = false;

      boolean var1;
      label46: {
         label45: {
            try {
               var4 = true;
               if(this.zafd != null) {
                  var4 = false;
                  break label45;
               }

               var1 = this.zafc;
               var4 = false;
            } finally {
               if(var4) {
                  this.zaen.unlock();
               }
            }

            if(var1) {
               var1 = true;
               break label46;
            }
         }

         var1 = false;
      }

      this.zaen.unlock();
      return var1;
   }

   public final boolean maybeSignIn(SignInConnectionListener var1) {
      this.zaen.lock();

      try {
         if(this.zafc && !this.zaac()) {
            this.zabm.zao();
            this.zaff = new zaaa(this, var1);
            this.zabm.zaa((Iterable)this.zaeu.values()).a(new HandlerExecutor(this.zabj), this.zaff);
            return true;
         }
      } finally {
         this.zaen.unlock();
      }

      return false;
   }

   public final void maybeSignOut() {
      this.zaen.lock();

      try {
         this.zabm.maybeSignOut();
         if(this.zaff != null) {
            this.zaff.cancel();
            this.zaff = null;
         }

         if(this.zafe == null) {
            this.zafe = new ArrayMap(this.zaeu.size());
         }

         ConnectionResult var1 = new ConnectionResult(4);
         Iterator var2 = this.zaeu.values().iterator();

         while(var2.hasNext()) {
            zaw var3 = (zaw)var2.next();
            this.zafe.put(var3.zak(), var1);
         }

         if(this.zafd != null) {
            this.zafd.putAll(this.zafe);
         }
      } finally {
         this.zaen.unlock();
      }

   }

   public final void zaw() {}
}
