package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.zaaj;
import com.google.android.gms.common.api.internal.zabd;
import com.google.android.gms.common.api.internal.zabe;
import com.google.android.gms.common.api.internal.zabf;
import com.google.android.gms.common.api.internal.zacm;
import java.util.Iterator;

public final class zaah implements zabd {

   private final zabe zafs;
   private boolean zaft = false;


   public zaah(zabe var1) {
      this.zafs = var1;
   }

   // $FF: synthetic method
   static zabe zaa(zaah var0) {
      return var0.zafs;
   }

   public final void begin() {}

   public final void connect() {
      if(this.zaft) {
         this.zaft = false;
         this.zafs.zaa((zabf)(new zaaj(this, this)));
      }

   }

   public final boolean disconnect() {
      if(this.zaft) {
         return false;
      } else if(!this.zafs.zaed.zaax()) {
         this.zafs.zaf((ConnectionResult)null);
         return true;
      } else {
         this.zaft = true;
         Iterator var1 = this.zafs.zaed.zahd.iterator();

         while(var1.hasNext()) {
            ((zacm)var1.next()).zabv();
         }

         return false;
      }
   }

   public final <A extends Object & Api.AnyClient, R extends Object & Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(T var1) {
      return this.execute(var1);
   }

   public final <A extends Object & Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(T param1) {
      // $FF: Couldn't be decompiled
   }

   public final void onConnected(Bundle var1) {}

   public final void onConnectionSuspended(int var1) {
      this.zafs.zaf((ConnectionResult)null);
      this.zafs.zahs.zab(var1, this.zaft);
   }

   public final void zaa(ConnectionResult var1, Api<?> var2, boolean var3) {}

   final void zaam() {
      if(this.zaft) {
         this.zaft = false;
         this.zafs.zaed.zahe.release();
         this.disconnect();
      }

   }
}
