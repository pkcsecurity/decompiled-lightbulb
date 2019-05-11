package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.common.api.internal.UnregisterListenerMethod;
import com.google.android.gms.common.api.internal.zabx;
import com.google.android.gms.common.api.internal.zaby;
import com.google.android.gms.common.api.internal.zabz;
import com.google.android.gms.common.api.internal.zaca;
import com.google.android.gms.common.api.internal.zacb;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.BiConsumer;

@KeepForSdk
public class RegistrationMethods<A extends Object & Api.AnyClient, L extends Object> {

   public final RegisterListenerMethod<A, L> zajy;
   public final UnregisterListenerMethod<A, L> zajz;


   private RegistrationMethods(RegisterListenerMethod<A, L> var1, UnregisterListenerMethod<A, L> var2) {
      this.zajy = var1;
      this.zajz = var2;
   }

   // $FF: synthetic method
   RegistrationMethods(RegisterListenerMethod var1, UnregisterListenerMethod var2, zabx var3) {
      this(var1, var2);
   }

   @KeepForSdk
   public static <A extends Object & Api.AnyClient, L extends Object> RegistrationMethods.Builder<A, L> builder() {
      return new RegistrationMethods.Builder((zabx)null);
   }

   @KeepForSdk
   public static class Builder<A extends Object & Api.AnyClient, L extends Object> {

      private boolean zajv;
      private RemoteCall<A, li<Void>> zaka;
      private RemoteCall<A, li<Boolean>> zakb;
      private ListenerHolder<L> zakc;
      private Feature[] zakd;


      private Builder() {
         this.zajv = true;
      }

      // $FF: synthetic method
      Builder(zabx var1) {
         this();
      }

      // $FF: synthetic method
      static RemoteCall zaa(RegistrationMethods.Builder var0) {
         return var0.zaka;
      }

      // $FF: synthetic method
      static RemoteCall zab(RegistrationMethods.Builder var0) {
         return var0.zakb;
      }

      @KeepForSdk
      public RegistrationMethods<A, L> build() {
         RemoteCall var3 = this.zaka;
         boolean var2 = false;
         boolean var1;
         if(var3 != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         Preconditions.checkArgument(var1, "Must set register function");
         if(this.zakb != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         Preconditions.checkArgument(var1, "Must set unregister function");
         var1 = var2;
         if(this.zakc != null) {
            var1 = true;
         }

         Preconditions.checkArgument(var1, "Must set holder");
         return new RegistrationMethods(new zaca(this, this.zakc, this.zakd, this.zajv), new zacb(this, this.zakc.getListenerKey()), (zabx)null);
      }

      @KeepForSdk
      public RegistrationMethods.Builder<A, L> register(RemoteCall<A, li<Void>> var1) {
         this.zaka = var1;
         return this;
      }

      @Deprecated
      @KeepForSdk
      public RegistrationMethods.Builder<A, L> register(BiConsumer<A, li<Void>> var1) {
         this.zaka = new zaby(var1);
         return this;
      }

      @KeepForSdk
      public RegistrationMethods.Builder<A, L> setAutoResolveMissingFeatures(boolean var1) {
         this.zajv = var1;
         return this;
      }

      @KeepForSdk
      public RegistrationMethods.Builder<A, L> setFeatures(Feature[] var1) {
         this.zakd = var1;
         return this;
      }

      @KeepForSdk
      public RegistrationMethods.Builder<A, L> unregister(RemoteCall<A, li<Boolean>> var1) {
         this.zakb = var1;
         return this;
      }

      @Deprecated
      @KeepForSdk
      public RegistrationMethods.Builder<A, L> unregister(BiConsumer<A, li<Boolean>> var1) {
         this.zaka = new zabz(this);
         return this;
      }

      @KeepForSdk
      public RegistrationMethods.Builder<A, L> withHolder(ListenerHolder<L> var1) {
         this.zakc = var1;
         return this;
      }

      // $FF: synthetic method
      final void zaa(Api.AnyClient var1, li var2) throws RemoteException {
         this.zaka.accept(var1, var2);
      }
   }
}
