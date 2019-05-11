package com.google.android.gms.common.api.internal;

import android.support.annotation.WorkerThread;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.zacm;

final class zacn implements Runnable {

   // $FF: synthetic field
   private final Result zaku;
   // $FF: synthetic field
   private final zacm zakv;


   zacn(zacm var1, Result var2) {
      this.zakv = var1;
      this.zaku = var2;
   }

   @WorkerThread
   public final void run() {
      boolean var5 = false;

      GoogleApiClient var8;
      label71: {
         try {
            var5 = true;
            BasePendingResult.zadm.set(Boolean.valueOf(true));
            PendingResult var1 = zacm.zac(this.zakv).onSuccess(this.zaku);
            zacm.zad(this.zakv).sendMessage(zacm.zad(this.zakv).obtainMessage(0, var1));
            var5 = false;
            break label71;
         } catch (RuntimeException var6) {
            zacm.zad(this.zakv).sendMessage(zacm.zad(this.zakv).obtainMessage(1, var6));
            var5 = false;
         } finally {
            if(var5) {
               BasePendingResult.zadm.set(Boolean.valueOf(false));
               zacm.zaa(this.zakv, this.zaku);
               GoogleApiClient var2 = (GoogleApiClient)zacm.zae(this.zakv).get();
               if(var2 != null) {
                  var2.zab(this.zakv);
               }

            }
         }

         BasePendingResult.zadm.set(Boolean.valueOf(false));
         zacm.zaa(this.zakv, this.zaku);
         var8 = (GoogleApiClient)zacm.zae(this.zakv).get();
         if(var8 != null) {
            var8.zab(this.zakv);
         }

         return;
      }

      BasePendingResult.zadm.set(Boolean.valueOf(false));
      zacm.zaa(this.zakv, this.zaku);
      var8 = (GoogleApiClient)zacm.zae(this.zakv).get();
      if(var8 != null) {
         var8.zab(this.zakv);
      }

   }
}
