package com.google.android.gms.common.api.internal;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.zacq;
import com.google.android.gms.common.api.internal.zacr;
import com.google.android.gms.common.api.internal.zacs;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public final class zacp {

   public static final Status zakw = new Status(8, "The connection to Google Play services was lost");
   private static final BasePendingResult<?>[] zakx = new BasePendingResult[0];
   private final Map<Api.AnyClientKey<?>, Api.Client> zagy;
   @VisibleForTesting
   final Set<BasePendingResult<?>> zaky = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));
   private final zacs zakz = new zacq(this);


   public zacp(Map<Api.AnyClientKey<?>, Api.Client> var1) {
      this.zagy = var1;
   }

   public final void release() {
      BasePendingResult[] var4 = (BasePendingResult[])this.zaky.toArray(zakx);
      int var2 = var4.length;

      for(int var1 = 0; var1 < var2; ++var1) {
         BasePendingResult var3 = var4[var1];
         var3.zaa((zacs)null);
         if(var3.zam() == null) {
            if(var3.zat()) {
               this.zaky.remove(var3);
            }
         } else {
            var3.setResultCallback((ResultCallback)null);
            IBinder var5 = ((Api.Client)this.zagy.get(((BaseImplementation.ApiMethodImpl)var3).getClientKey())).getServiceBrokerBinder();
            if(var3.isReady()) {
               var3.zaa((zacs)(new zacr(var3, (com.google.android.gms.common.api.zac)null, var5, (zacq)null)));
            } else {
               if(var5 == null || !var5.isBinderAlive()) {
                  var3.zaa((zacs)null);
                  var3.cancel();
                  var3.zam().intValue();
                  throw new NullPointerException();
               }

               zacr var6 = new zacr(var3, (com.google.android.gms.common.api.zac)null, var5, (zacq)null);
               var3.zaa((zacs)var6);

               try {
                  var5.linkToDeath(var6, 0);
               } catch (RemoteException var7) {
                  var3.cancel();
                  var3.zam().intValue();
                  throw new NullPointerException();
               }
            }

            this.zaky.remove(var3);
         }
      }

   }

   final void zab(BasePendingResult<? extends Result> var1) {
      this.zaky.add(var1);
      var1.zaa(this.zakz);
   }

   public final void zabx() {
      BasePendingResult[] var3 = (BasePendingResult[])this.zaky.toArray(zakx);
      int var2 = var3.length;

      for(int var1 = 0; var1 < var2; ++var1) {
         var3[var1].zab(zakw);
      }

   }
}
