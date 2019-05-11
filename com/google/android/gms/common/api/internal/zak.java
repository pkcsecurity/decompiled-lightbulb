package com.google.android.gms.common.api.internal;

import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.AvailabilityException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.zai;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class zak {

   private final ArrayMap<zai<?>, ConnectionResult> zaay = new ArrayMap();
   private final ArrayMap<zai<?>, String> zada = new ArrayMap();
   private final li<Map<zai<?>, String>> zadb = new li();
   private int zadc;
   private boolean zadd = false;


   public zak(Iterable<? extends GoogleApi<?>> var1) {
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         GoogleApi var2 = (GoogleApi)var3.next();
         this.zaay.put(var2.zak(), (Object)null);
      }

      this.zadc = this.zaay.keySet().size();
   }

   public final lh<Map<zai<?>, String>> getTask() {
      return this.zadb.a();
   }

   public final void zaa(zai<?> var1, ConnectionResult var2, @Nullable String var3) {
      this.zaay.put(var1, var2);
      this.zada.put(var1, var3);
      --this.zadc;
      if(!var2.isSuccess()) {
         this.zadd = true;
      }

      if(this.zadc == 0) {
         if(this.zadd) {
            AvailabilityException var4 = new AvailabilityException(this.zaay);
            this.zadb.a(var4);
            return;
         }

         this.zadb.a(this.zada);
      }

   }

   public final Set<zai<?>> zap() {
      return this.zaay.keySet();
   }
}
