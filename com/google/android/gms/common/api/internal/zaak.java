package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.zaal;
import com.google.android.gms.common.api.internal.zaam;
import com.google.android.gms.common.api.internal.zaan;
import com.google.android.gms.common.api.internal.zaaq;
import com.google.android.gms.common.api.internal.zaat;
import com.google.android.gms.common.api.internal.zabd;
import com.google.android.gms.common.api.internal.zabe;
import com.google.android.gms.common.api.internal.zabh;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

public final class zaak implements zabd {

   private final Context mContext;
   private final Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, kx> zacd;
   private final Lock zaen;
   private final ClientSettings zaes;
   private final Map<Api<?>, Boolean> zaev;
   private final GoogleApiAvailabilityLight zaex;
   private ConnectionResult zafg;
   private final zabe zafs;
   private int zafv;
   private int zafw = 0;
   private int zafx;
   private final Bundle zafy = new Bundle();
   private final Set<Api.AnyClientKey> zafz = new HashSet();
   private com.google.android.gms.signin.zad zaga;
   private boolean zagb;
   private boolean zagc;
   private boolean zagd;
   private IAccountAccessor zage;
   private boolean zagf;
   private boolean zagg;
   private ArrayList<Future<?>> zagh = new ArrayList();


   public zaak(zabe var1, ClientSettings var2, Map<Api<?>, Boolean> var3, GoogleApiAvailabilityLight var4, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, kx> var5, Lock var6, Context var7) {
      this.zafs = var1;
      this.zaes = var2;
      this.zaev = var3;
      this.zaex = var4;
      this.zacd = var5;
      this.zaen = var6;
      this.mContext = var7;
   }

   // $FF: synthetic method
   static Context zaa(zaak var0) {
      return var0.mContext;
   }

   // $FF: synthetic method
   static void zaa(zaak var0, ConnectionResult var1) {
      var0.zae(var1);
   }

   // $FF: synthetic method
   static void zaa(zaak var0, ConnectionResult var1, Api var2, boolean var3) {
      var0.zab(var1, var2, var3);
   }

   // $FF: synthetic method
   static void zaa(zaak var0, com.google.android.gms.signin.internal.zaj var1) {
      var0.zaa(var1);
   }

   @GuardedBy
   private final void zaa(com.google.android.gms.signin.internal.zaj var1) {
      if(this.zac(0)) {
         ConnectionResult var2 = var1.a();
         if(var2.isSuccess()) {
            ResolveAccountResponse var5 = var1.b();
            ConnectionResult var4 = var5.getConnectionResult();
            if(!var4.isSuccess()) {
               String var6 = String.valueOf(var4);
               StringBuilder var3 = new StringBuilder(String.valueOf(var6).length() + 48);
               var3.append("Sign-in succeeded with resolve account failure: ");
               var3.append(var6);
               Log.wtf("GoogleApiClientConnecting", var3.toString(), new Exception());
               this.zae(var4);
            } else {
               this.zagd = true;
               this.zage = var5.getAccountAccessor();
               this.zagf = var5.getSaveDefaultAccount();
               this.zagg = var5.isFromCrossClientAuth();
               this.zaap();
            }
         } else if(this.zad(var2)) {
            this.zaar();
            this.zaap();
         } else {
            this.zae(var2);
         }
      }
   }

   // $FF: synthetic method
   static boolean zaa(zaak var0, int var1) {
      return var0.zac(0);
   }

   @GuardedBy
   private final boolean zaao() {
      --this.zafx;
      if(this.zafx > 0) {
         return false;
      } else if(this.zafx < 0) {
         Log.w("GoogleApiClientConnecting", this.zafs.zaed.zaay());
         Log.wtf("GoogleApiClientConnecting", "GoogleApiClient received too many callbacks for the given step. Clients may be in an unexpected state; GoogleApiClient will now disconnect.", new Exception());
         this.zae(new ConnectionResult(8, (PendingIntent)null));
         return false;
      } else if(this.zafg != null) {
         this.zafs.zahr = this.zafv;
         this.zae(this.zafg);
         return false;
      } else {
         return true;
      }
   }

   @GuardedBy
   private final void zaap() {
      if(this.zafx == 0) {
         if(!this.zagc || this.zagd) {
            ArrayList var1 = new ArrayList();
            this.zafw = 1;
            this.zafx = this.zafs.zagy.size();
            Iterator var2 = this.zafs.zagy.keySet().iterator();

            while(var2.hasNext()) {
               Api.AnyClientKey var3 = (Api.AnyClientKey)var2.next();
               if(this.zafs.zaho.containsKey(var3)) {
                  if(this.zaao()) {
                     this.zaaq();
                  }
               } else {
                  var1.add((Api.Client)this.zafs.zagy.get(var3));
               }
            }

            if(!var1.isEmpty()) {
               this.zagh.add(zabh.zabb().submit(new zaaq(this, var1)));
            }
         }

      }
   }

   @GuardedBy
   private final void zaaq() {
      this.zafs.zaba();
      zabh.zabb().execute(new zaal(this));
      if(this.zaga != null) {
         if(this.zagf) {
            this.zaga.a(this.zage, this.zagg);
         }

         this.zab(false);
      }

      Iterator var1 = this.zafs.zaho.keySet().iterator();

      while(var1.hasNext()) {
         Api.AnyClientKey var2 = (Api.AnyClientKey)var1.next();
         ((Api.Client)this.zafs.zagy.get(var2)).disconnect();
      }

      Bundle var3;
      if(this.zafy.isEmpty()) {
         var3 = null;
      } else {
         var3 = this.zafy;
      }

      this.zafs.zahs.zab(var3);
   }

   @GuardedBy
   private final void zaar() {
      this.zagc = false;
      this.zafs.zaed.zagz = Collections.emptySet();
      Iterator var1 = this.zafz.iterator();

      while(var1.hasNext()) {
         Api.AnyClientKey var2 = (Api.AnyClientKey)var1.next();
         if(!this.zafs.zaho.containsKey(var2)) {
            this.zafs.zaho.put(var2, new ConnectionResult(17, (PendingIntent)null));
         }
      }

   }

   private final void zaas() {
      ArrayList var3 = (ArrayList)this.zagh;
      int var2 = var3.size();
      int var1 = 0;

      while(var1 < var2) {
         Object var4 = var3.get(var1);
         ++var1;
         ((Future)var4).cancel(true);
      }

      this.zagh.clear();
   }

   private final Set<Scope> zaat() {
      if(this.zaes == null) {
         return Collections.emptySet();
      } else {
         HashSet var1 = new HashSet(this.zaes.getRequiredScopes());
         Map var2 = this.zaes.getOptionalApiSettings();
         Iterator var3 = var2.keySet().iterator();

         while(var3.hasNext()) {
            Api var4 = (Api)var3.next();
            if(!this.zafs.zaho.containsKey(var4.getClientKey())) {
               var1.addAll(((ClientSettings.OptionalApiSettings)var2.get(var4)).mScopes);
            }
         }

         return var1;
      }
   }

   // $FF: synthetic method
   static GoogleApiAvailabilityLight zab(zaak var0) {
      return var0.zaex;
   }

   @GuardedBy
   private final void zab(ConnectionResult var1, Api<?> var2, boolean var3) {
      boolean var5;
      int var7;
      label34: {
         var7 = var2.zah().getPriority();
         boolean var6 = false;
         if(var3) {
            boolean var4;
            if(!var1.hasResolution() && this.zaex.getErrorResolutionIntent(var1.getErrorCode()) == null) {
               var4 = false;
            } else {
               var4 = true;
            }

            var5 = var6;
            if(!var4) {
               break label34;
            }
         }

         if(this.zafg != null) {
            var5 = var6;
            if(var7 >= this.zafv) {
               break label34;
            }
         }

         var5 = true;
      }

      if(var5) {
         this.zafg = var1;
         this.zafv = var7;
      }

      this.zafs.zaho.put(var2.getClientKey(), var1);
   }

   private final void zab(boolean var1) {
      if(this.zaga != null) {
         if(this.zaga.isConnected() && var1) {
            this.zaga.a();
         }

         this.zaga.disconnect();
         this.zage = null;
      }

   }

   // $FF: synthetic method
   static boolean zab(zaak var0, ConnectionResult var1) {
      return var0.zad(var1);
   }

   // $FF: synthetic method
   static Lock zac(zaak var0) {
      return var0.zaen;
   }

   @GuardedBy
   private final boolean zac(int var1) {
      if(this.zafw != var1) {
         Log.w("GoogleApiClientConnecting", this.zafs.zaed.zaay());
         String var3 = String.valueOf(this);
         StringBuilder var4 = new StringBuilder(String.valueOf(var3).length() + 23);
         var4.append("Unexpected callback in ");
         var4.append(var3);
         Log.w("GoogleApiClientConnecting", var4.toString());
         int var2 = this.zafx;
         StringBuilder var6 = new StringBuilder(33);
         var6.append("mRemainingConnections=");
         var6.append(var2);
         Log.w("GoogleApiClientConnecting", var6.toString());
         var3 = zad(this.zafw);
         String var7 = zad(var1);
         StringBuilder var5 = new StringBuilder(String.valueOf(var3).length() + 70 + String.valueOf(var7).length());
         var5.append("GoogleApiClient connecting is in step ");
         var5.append(var3);
         var5.append(" but received callback for step ");
         var5.append(var7);
         Log.wtf("GoogleApiClientConnecting", var5.toString(), new Exception());
         this.zae(new ConnectionResult(8, (PendingIntent)null));
         return false;
      } else {
         return true;
      }
   }

   // $FF: synthetic method
   static zabe zad(zaak var0) {
      return var0.zafs;
   }

   private static String zad(int var0) {
      switch(var0) {
      case 0:
         return "STEP_SERVICE_BINDINGS_AND_SIGN_IN";
      case 1:
         return "STEP_GETTING_REMOTE_SERVICE";
      default:
         return "UNKNOWN";
      }
   }

   @GuardedBy
   private final boolean zad(ConnectionResult var1) {
      return this.zagb && !var1.hasResolution();
   }

   @GuardedBy
   private final void zae(ConnectionResult var1) {
      this.zaas();
      this.zab(var1.hasResolution() ^ true);
      this.zafs.zaf(var1);
      this.zafs.zahs.zac(var1);
   }

   // $FF: synthetic method
   static boolean zae(zaak var0) {
      return var0.zagc;
   }

   // $FF: synthetic method
   static com.google.android.gms.signin.zad zaf(zaak var0) {
      return var0.zaga;
   }

   // $FF: synthetic method
   static Set zag(zaak var0) {
      return var0.zaat();
   }

   // $FF: synthetic method
   static IAccountAccessor zah(zaak var0) {
      return var0.zage;
   }

   // $FF: synthetic method
   static void zai(zaak var0) {
      var0.zaar();
   }

   // $FF: synthetic method
   static void zaj(zaak var0) {
      var0.zaap();
   }

   // $FF: synthetic method
   static boolean zak(zaak var0) {
      return var0.zaao();
   }

   public final void begin() {
      this.zafs.zaho.clear();
      this.zagc = false;
      this.zafg = null;
      this.zafw = 0;
      this.zagb = true;
      this.zagd = false;
      this.zagf = false;
      HashMap var4 = new HashMap();
      Iterator var5 = this.zaev.keySet().iterator();

      boolean var1;
      boolean var3;
      Api var6;
      Api.Client var7;
      for(var1 = false; var5.hasNext(); var4.put(var7, new zaam(this, var6, var3))) {
         var6 = (Api)var5.next();
         var7 = (Api.Client)this.zafs.zagy.get(var6.getClientKey());
         boolean var2;
         if(var6.zah().getPriority() == 1) {
            var2 = true;
         } else {
            var2 = false;
         }

         var1 |= var2;
         var3 = ((Boolean)this.zaev.get(var6)).booleanValue();
         if(var7.requiresSignIn()) {
            this.zagc = true;
            if(var3) {
               this.zafz.add(var6.getClientKey());
            } else {
               this.zagb = false;
            }
         }
      }

      if(var1) {
         this.zagc = false;
      }

      if(this.zagc) {
         this.zaes.setClientSessionId(Integer.valueOf(System.identityHashCode(this.zafs.zaed)));
         zaat var8 = new zaat(this, (zaal)null);
         this.zaga = (com.google.android.gms.signin.zad)this.zacd.buildClient(this.mContext, this.zafs.zaed.getLooper(), this.zaes, this.zaes.getSignInOptions(), var8, var8);
      }

      this.zafx = this.zafs.zagy.size();
      this.zagh.add(zabh.zabb().submit(new zaan(this, var4)));
   }

   public final void connect() {}

   public final boolean disconnect() {
      this.zaas();
      this.zab(true);
      this.zafs.zaf((ConnectionResult)null);
      return true;
   }

   public final <A extends Object & Api.AnyClient, R extends Object & Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(T var1) {
      this.zafs.zaed.zafb.add(var1);
      return var1;
   }

   public final <A extends Object & Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(T var1) {
      throw new IllegalStateException("GoogleApiClient is not connected yet.");
   }

   @GuardedBy
   public final void onConnected(Bundle var1) {
      if(this.zac(1)) {
         if(var1 != null) {
            this.zafy.putAll(var1);
         }

         if(this.zaao()) {
            this.zaaq();
         }

      }
   }

   @GuardedBy
   public final void onConnectionSuspended(int var1) {
      this.zae(new ConnectionResult(8, (PendingIntent)null));
   }

   @GuardedBy
   public final void zaa(ConnectionResult var1, Api<?> var2, boolean var3) {
      if(this.zac(1)) {
         this.zab(var1, var2, var3);
         if(this.zaao()) {
            this.zaaq();
         }

      }
   }
}
