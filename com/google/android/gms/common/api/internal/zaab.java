package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.zaac;
import com.google.android.gms.common.api.internal.zaad;
import com.google.android.gms.common.api.internal.zacp;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public final class zaab {

   private final Map<BasePendingResult<?>, Boolean> zafj = Collections.synchronizedMap(new WeakHashMap());
   private final Map<li<?>, Boolean> zafk = Collections.synchronizedMap(new WeakHashMap());


   // $FF: synthetic method
   static Map zaa(zaab var0) {
      return var0.zafj;
   }

   private final void zaa(boolean param1, Status param2) {
      // $FF: Couldn't be decompiled
   }

   // $FF: synthetic method
   static Map zab(zaab var0) {
      return var0.zafk;
   }

   final void zaa(BasePendingResult<? extends Result> var1, boolean var2) {
      this.zafj.put(var1, Boolean.valueOf(var2));
      var1.addStatusListener(new zaac(this, var1));
   }

   final <TResult extends Object> void zaa(li<TResult> var1, boolean var2) {
      this.zafk.put(var1, Boolean.valueOf(var2));
      var1.a().a(new zaad(this, var1));
   }

   final boolean zaag() {
      return !this.zafj.isEmpty() || !this.zafk.isEmpty();
   }

   public final void zaah() {
      this.zaa(false, GoogleApiManager.zahw);
   }

   public final void zaai() {
      this.zaa(true, zacp.zakw);
   }
}
