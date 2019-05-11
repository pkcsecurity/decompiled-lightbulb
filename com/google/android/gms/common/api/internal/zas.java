package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.SignInConnectionListener;
import com.google.android.gms.common.api.internal.zaaw;
import com.google.android.gms.common.api.internal.zabe;
import com.google.android.gms.common.api.internal.zabs;
import com.google.android.gms.common.api.internal.zaq;
import com.google.android.gms.common.api.internal.zat;
import com.google.android.gms.common.api.internal.zau;
import com.google.android.gms.common.api.internal.zav;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Preconditions;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

final class zas implements zabs {

   private final Context mContext;
   private final Looper zabj;
   private final zaaw zaed;
   private final zabe zaee;
   private final zabe zaef;
   private final Map<Api.AnyClientKey<?>, zabe> zaeg;
   private final Set<SignInConnectionListener> zaeh = Collections.newSetFromMap(new WeakHashMap());
   private final Api.Client zaei;
   private Bundle zaej;
   private ConnectionResult zaek = null;
   private ConnectionResult zael = null;
   private boolean zaem = false;
   private final Lock zaen;
   @GuardedBy
   private int zaeo = 0;


   private zas(Context var1, zaaw var2, Lock var3, Looper var4, GoogleApiAvailabilityLight var5, Map<Api.AnyClientKey<?>, Api.Client> var6, Map<Api.AnyClientKey<?>, Api.Client> var7, ClientSettings var8, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, kx> var9, Api.Client var10, ArrayList<zaq> var11, ArrayList<zaq> var12, Map<Api<?>, Boolean> var13, Map<Api<?>, Boolean> var14) {
      this.mContext = var1;
      this.zaed = var2;
      this.zaen = var3;
      this.zabj = var4;
      this.zaei = var10;
      this.zaee = new zabe(var1, this.zaed, var3, var4, var5, var7, (ClientSettings)null, var14, (Api.AbstractClientBuilder)null, var12, new zau(this, (zat)null));
      this.zaef = new zabe(var1, this.zaed, var3, var4, var5, var6, var8, var13, var9, var11, new zav(this, (zat)null));
      ArrayMap var15 = new ArrayMap();
      Iterator var16 = var7.keySet().iterator();

      while(var16.hasNext()) {
         var15.put((Api.AnyClientKey)var16.next(), this.zaee);
      }

      var16 = var6.keySet().iterator();

      while(var16.hasNext()) {
         var15.put((Api.AnyClientKey)var16.next(), this.zaef);
      }

      this.zaeg = Collections.unmodifiableMap(var15);
   }

   // $FF: synthetic method
   static ConnectionResult zaa(zas var0, ConnectionResult var1) {
      var0.zaek = var1;
      return var1;
   }

   public static zas zaa(Context var0, zaaw var1, Lock var2, Looper var3, GoogleApiAvailabilityLight var4, Map<Api.AnyClientKey<?>, Api.Client> var5, ClientSettings var6, Map<Api<?>, Boolean> var7, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, kx> var8, ArrayList<zaq> var9) {
      ArrayMap var13 = new ArrayMap();
      ArrayMap var14 = new ArrayMap();
      Iterator var15 = var5.entrySet().iterator();
      Api.Client var19 = null;

      while(var15.hasNext()) {
         Entry var16 = (Entry)var15.next();
         Api.Client var12 = (Api.Client)var16.getValue();
         if(var12.providesSignIn()) {
            var19 = var12;
         }

         if(var12.requiresSignIn()) {
            var13.put((Api.AnyClientKey)var16.getKey(), var12);
         } else {
            var14.put((Api.AnyClientKey)var16.getKey(), var12);
         }
      }

      Preconditions.checkState(var13.isEmpty() ^ true, "CompositeGoogleApiClient should not be used without any APIs that require sign-in.");
      ArrayMap var21 = new ArrayMap();
      ArrayMap var22 = new ArrayMap();
      Iterator var23 = var7.keySet().iterator();

      while(var23.hasNext()) {
         Api var17 = (Api)var23.next();
         Api.AnyClientKey var18 = var17.getClientKey();
         if(var13.containsKey(var18)) {
            var21.put(var17, (Boolean)var7.get(var17));
         } else {
            if(!var14.containsKey(var18)) {
               throw new IllegalStateException("Each API in the isOptionalMap must have a corresponding client in the clients map.");
            }

            var22.put(var17, (Boolean)var7.get(var17));
         }
      }

      ArrayList var20 = new ArrayList();
      ArrayList var24 = new ArrayList();
      var9 = (ArrayList)var9;
      int var11 = var9.size();
      int var10 = 0;

      while(var10 < var11) {
         Object var25 = var9.get(var10);
         ++var10;
         zaq var26 = (zaq)var25;
         if(var21.containsKey(var26.mApi)) {
            var20.add(var26);
         } else {
            if(!var22.containsKey(var26.mApi)) {
               throw new IllegalStateException("Each ClientCallbacks must have a corresponding API in the isOptionalMap");
            }

            var24.add(var26);
         }
      }

      return new zas(var0, var1, var2, var3, var4, var13, var14, var6, var8, var19, var20, var24, var21, var22);
   }

   // $FF: synthetic method
   static Lock zaa(zas var0) {
      return var0.zaen;
   }

   @GuardedBy
   private final void zaa(int var1, boolean var2) {
      this.zaed.zab(var1, var2);
      this.zael = null;
      this.zaek = null;
   }

   private final void zaa(Bundle var1) {
      if(this.zaej == null) {
         this.zaej = var1;
      } else {
         if(var1 != null) {
            this.zaej.putAll(var1);
         }

      }
   }

   @GuardedBy
   private final void zaa(ConnectionResult var1) {
      switch(this.zaeo) {
      case 2:
         this.zaed.zac(var1);
      case 1:
         this.zay();
         break;
      default:
         Log.wtf("CompositeGAC", "Attempted to call failure callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new Exception());
      }

      this.zaeo = 0;
   }

   // $FF: synthetic method
   static void zaa(zas var0, int var1, boolean var2) {
      var0.zaa(var1, var2);
   }

   // $FF: synthetic method
   static void zaa(zas var0, Bundle var1) {
      var0.zaa(var1);
   }

   private final boolean zaa(BaseImplementation.ApiMethodImpl<? extends Result, ? extends Api.AnyClient> var1) {
      Api.AnyClientKey var2 = var1.getClientKey();
      Preconditions.checkArgument(this.zaeg.containsKey(var2), "GoogleApiClient is not configured to use the API required for this call.");
      return ((zabe)this.zaeg.get(var2)).equals(this.zaef);
   }

   // $FF: synthetic method
   static boolean zaa(zas var0, boolean var1) {
      var0.zaem = var1;
      return var1;
   }

   @Nullable
   private final PendingIntent zaaa() {
      return this.zaei == null?null:PendingIntent.getActivity(this.mContext, System.identityHashCode(this.zaed), this.zaei.getSignInIntent(), 134217728);
   }

   // $FF: synthetic method
   static ConnectionResult zab(zas var0, ConnectionResult var1) {
      var0.zael = var1;
      return var1;
   }

   // $FF: synthetic method
   static void zab(zas var0) {
      var0.zax();
   }

   private static boolean zab(ConnectionResult var0) {
      return var0 != null && var0.isSuccess();
   }

   // $FF: synthetic method
   static boolean zac(zas var0) {
      return var0.zaem;
   }

   // $FF: synthetic method
   static ConnectionResult zad(zas var0) {
      return var0.zael;
   }

   // $FF: synthetic method
   static zabe zae(zas var0) {
      return var0.zaef;
   }

   // $FF: synthetic method
   static zabe zaf(zas var0) {
      return var0.zaee;
   }

   @GuardedBy
   private final void zax() {
      if(zab(this.zaek)) {
         if(zab(this.zael) || this.zaz()) {
            switch(this.zaeo) {
            case 2:
               this.zaed.zab(this.zaej);
            case 1:
               this.zay();
               break;
            default:
               Log.wtf("CompositeGAC", "Attempted to call success callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new AssertionError());
            }

            this.zaeo = 0;
            return;
         }

         if(this.zael != null) {
            if(this.zaeo == 1) {
               this.zay();
               return;
            }

            this.zaa(this.zael);
            this.zaee.disconnect();
            return;
         }
      } else {
         if(this.zaek != null && zab(this.zael)) {
            this.zaef.disconnect();
            this.zaa(this.zaek);
            return;
         }

         if(this.zaek != null && this.zael != null) {
            ConnectionResult var1 = this.zaek;
            if(this.zaef.zahr < this.zaee.zahr) {
               var1 = this.zael;
            }

            this.zaa(var1);
         }
      }

   }

   @GuardedBy
   private final void zay() {
      Iterator var1 = this.zaeh.iterator();

      while(var1.hasNext()) {
         ((SignInConnectionListener)var1.next()).onComplete();
      }

      this.zaeh.clear();
   }

   @GuardedBy
   private final boolean zaz() {
      return this.zael != null && this.zael.getErrorCode() == 4;
   }

   @GuardedBy
   public final ConnectionResult blockingConnect() {
      throw new UnsupportedOperationException();
   }

   @GuardedBy
   public final ConnectionResult blockingConnect(long var1, @NonNull TimeUnit var3) {
      throw new UnsupportedOperationException();
   }

   @GuardedBy
   public final void connect() {
      this.zaeo = 2;
      this.zaem = false;
      this.zael = null;
      this.zaek = null;
      this.zaee.connect();
      this.zaef.connect();
   }

   @GuardedBy
   public final void disconnect() {
      this.zael = null;
      this.zaek = null;
      this.zaeo = 0;
      this.zaee.disconnect();
      this.zaef.disconnect();
      this.zay();
   }

   public final void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {
      var3.append(var1).append("authClient").println(":");
      this.zaef.dump(String.valueOf(var1).concat("  "), var2, var3, var4);
      var3.append(var1).append("anonClient").println(":");
      this.zaee.dump(String.valueOf(var1).concat("  "), var2, var3, var4);
   }

   @GuardedBy
   public final <A extends Object & Api.AnyClient, R extends Object & Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(@NonNull T var1) {
      if(this.zaa(var1)) {
         if(this.zaz()) {
            var1.setFailedResult(new Status(4, (String)null, this.zaaa()));
            return var1;
         } else {
            return this.zaef.enqueue(var1);
         }
      } else {
         return this.zaee.enqueue(var1);
      }
   }

   @GuardedBy
   public final <A extends Object & Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(@NonNull T var1) {
      if(this.zaa(var1)) {
         if(this.zaz()) {
            var1.setFailedResult(new Status(4, (String)null, this.zaaa()));
            return var1;
         } else {
            return this.zaef.execute(var1);
         }
      } else {
         return this.zaee.execute(var1);
      }
   }

   @Nullable
   @GuardedBy
   public final ConnectionResult getConnectionResult(@NonNull Api<?> var1) {
      return ((zabe)this.zaeg.get(var1.getClientKey())).equals(this.zaef)?(this.zaz()?new ConnectionResult(4, this.zaaa()):this.zaef.getConnectionResult(var1)):this.zaee.getConnectionResult(var1);
   }

   public final boolean isConnected() {
      // $FF: Couldn't be decompiled
   }

   public final boolean isConnecting() {
      this.zaen.lock();
      boolean var5 = false;

      int var1;
      try {
         var5 = true;
         var1 = this.zaeo;
         var5 = false;
      } finally {
         if(var5) {
            this.zaen.unlock();
         }
      }

      boolean var2;
      if(var1 == 2) {
         var2 = true;
      } else {
         var2 = false;
      }

      this.zaen.unlock();
      return var2;
   }

   public final boolean maybeSignIn(SignInConnectionListener var1) {
      this.zaen.lock();

      try {
         if(!this.isConnecting() && !this.isConnected() || this.zaef.isConnected()) {
            return false;
         }

         this.zaeh.add(var1);
         if(this.zaeo == 0) {
            this.zaeo = 1;
         }

         this.zael = null;
         this.zaef.connect();
      } finally {
         this.zaen.unlock();
      }

      return true;
   }

   public final void maybeSignOut() {
      // $FF: Couldn't be decompiled
   }

   @GuardedBy
   public final void zaw() {
      this.zaee.zaw();
      this.zaef.zaw();
   }
}
