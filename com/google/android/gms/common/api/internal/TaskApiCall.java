package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import android.support.annotation.Nullable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.common.api.internal.zaci;
import com.google.android.gms.common.api.internal.zacj;
import com.google.android.gms.common.api.internal.zack;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.BiConsumer;

@KeepForSdk
public abstract class TaskApiCall<A extends Object & Api.AnyClient, ResultT extends Object> {

   private final Feature[] zakd;
   private final boolean zakk;


   @Deprecated
   @KeepForSdk
   public TaskApiCall() {
      this.zakd = null;
      this.zakk = false;
   }

   @KeepForSdk
   private TaskApiCall(Feature[] var1, boolean var2) {
      this.zakd = var1;
      this.zakk = var2;
   }

   // $FF: synthetic method
   TaskApiCall(Feature[] var1, boolean var2, zaci var3) {
      this(var1, var2);
   }

   @KeepForSdk
   public static <A extends Object & Api.AnyClient, ResultT extends Object> TaskApiCall.Builder<A, ResultT> builder() {
      return new TaskApiCall.Builder((zaci)null);
   }

   @KeepForSdk
   protected abstract void doExecute(A var1, li<ResultT> var2) throws RemoteException;

   @KeepForSdk
   public boolean shouldAutoResolveMissingFeatures() {
      return this.zakk;
   }

   @Nullable
   public final Feature[] zabt() {
      return this.zakd;
   }

   @KeepForSdk
   public static class Builder<A extends Object & Api.AnyClient, ResultT extends Object> {

      private Feature[] zakd;
      private boolean zakk;
      private RemoteCall<A, li<ResultT>> zakl;


      private Builder() {
         this.zakk = true;
      }

      // $FF: synthetic method
      Builder(zaci var1) {
         this();
      }

      // $FF: synthetic method
      static RemoteCall zaa(TaskApiCall.Builder var0) {
         return var0.zakl;
      }

      @KeepForSdk
      public TaskApiCall<A, ResultT> build() {
         boolean var1;
         if(this.zakl != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         Preconditions.checkArgument(var1, "execute parameter required");
         return new zack(this, this.zakd, this.zakk);
      }

      @Deprecated
      @KeepForSdk
      public TaskApiCall.Builder<A, ResultT> execute(BiConsumer<A, li<ResultT>> var1) {
         this.zakl = new zacj(var1);
         return this;
      }

      @KeepForSdk
      public TaskApiCall.Builder<A, ResultT> run(RemoteCall<A, li<ResultT>> var1) {
         this.zakl = var1;
         return this;
      }

      @KeepForSdk
      public TaskApiCall.Builder<A, ResultT> setAutoResolveMissingFeatures(boolean var1) {
         this.zakk = var1;
         return this;
      }

      @KeepForSdk
      public TaskApiCall.Builder<A, ResultT> setFeatures(Feature ... var1) {
         this.zakd = var1;
         return this;
      }
   }
}
