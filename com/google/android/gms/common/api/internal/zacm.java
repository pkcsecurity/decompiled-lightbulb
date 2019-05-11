package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.api.internal.zaco;
import com.google.android.gms.common.internal.Preconditions;
import java.lang.ref.WeakReference;
import javax.annotation.concurrent.GuardedBy;

public final class zacm<R extends Object & Result> extends TransformedResult<R> implements ResultCallback<R> {

   private final Object zadn = new Object();
   private final WeakReference<GoogleApiClient> zadp;
   private ResultTransform<? super R, ? extends Result> zakn = null;
   private zacm<? extends Result> zako = null;
   private volatile ResultCallbacks<? super R> zakp = null;
   private PendingResult<R> zakq = null;
   private Status zakr = null;
   private final zaco zaks;
   private boolean zakt = false;


   public zacm(WeakReference<GoogleApiClient> var1) {
      Preconditions.checkNotNull(var1, "GoogleApiClient reference must not be null");
      this.zadp = var1;
      GoogleApiClient var2 = (GoogleApiClient)this.zadp.get();
      Looper var3;
      if(var2 != null) {
         var3 = var2.getLooper();
      } else {
         var3 = Looper.getMainLooper();
      }

      this.zaks = new zaco(this, var3);
   }

   // $FF: synthetic method
   static void zaa(zacm var0, Result var1) {
      zab(var1);
   }

   // $FF: synthetic method
   static void zaa(zacm var0, Status var1) {
      var0.zad(var1);
   }

   private static void zab(Result var0) {
      if(var0 instanceof Releasable) {
         try {
            ((Releasable)var0).release();
            return;
         } catch (RuntimeException var3) {
            String var4 = String.valueOf(var0);
            StringBuilder var2 = new StringBuilder(String.valueOf(var4).length() + 18);
            var2.append("Unable to release ");
            var2.append(var4);
            Log.w("TransformedResultImpl", var2.toString(), var3);
         }
      }

   }

   @GuardedBy
   private final void zabu() {
      if(this.zakn != null || this.zakp != null) {
         GoogleApiClient var1 = (GoogleApiClient)this.zadp.get();
         if(!this.zakt && this.zakn != null && var1 != null) {
            var1.zaa(this);
            this.zakt = true;
         }

         if(this.zakr != null) {
            this.zae(this.zakr);
         } else {
            if(this.zakq != null) {
               this.zakq.setResultCallback(this);
            }

         }
      }
   }

   @GuardedBy
   private final boolean zabw() {
      GoogleApiClient var1 = (GoogleApiClient)this.zadp.get();
      return this.zakp != null && var1 != null;
   }

   // $FF: synthetic method
   static ResultTransform zac(zacm var0) {
      return var0.zakn;
   }

   // $FF: synthetic method
   static zaco zad(zacm var0) {
      return var0.zaks;
   }

   private final void zad(Status param1) {
      // $FF: Couldn't be decompiled
   }

   // $FF: synthetic method
   static WeakReference zae(zacm var0) {
      return var0.zadp;
   }

   private final void zae(Status param1) {
      // $FF: Couldn't be decompiled
   }

   // $FF: synthetic method
   static Object zaf(zacm var0) {
      return var0.zadn;
   }

   // $FF: synthetic method
   static zacm zag(zacm var0) {
      return var0.zako;
   }

   public final void andFinally(@NonNull ResultCallbacks<? super R> param1) {
      // $FF: Couldn't be decompiled
   }

   public final void onResult(R param1) {
      // $FF: Couldn't be decompiled
   }

   @NonNull
   public final <S extends Object & Result> TransformedResult<S> then(@NonNull ResultTransform<? super R, ? extends S> param1) {
      // $FF: Couldn't be decompiled
   }

   public final void zaa(PendingResult<?> param1) {
      // $FF: Couldn't be decompiled
   }

   final void zabv() {
      this.zakp = null;
   }
}
