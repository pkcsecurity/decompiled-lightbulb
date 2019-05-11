package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.zaak;
import com.google.android.gms.common.api.internal.zaal;
import com.google.android.gms.common.api.internal.zaam;
import com.google.android.gms.common.api.internal.zaao;
import com.google.android.gms.common.api.internal.zaap;
import com.google.android.gms.common.api.internal.zaau;
import com.google.android.gms.common.api.internal.zabf;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.GoogleApiAvailabilityCache;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

final class zaan extends zaau {

   // $FF: synthetic field
   final zaak zagi;
   private final Map<Api.Client, zaam> zagk;


   public zaan(zaak var1, Map var2) {
      super(var1, (zaal)null);
      this.zagi = var1;
      this.zagk = var2;
   }

   @WorkerThread
   public final void zaan() {
      GoogleApiAvailabilityCache var6 = new GoogleApiAvailabilityCache(zaak.zab(this.zagi));
      ArrayList var7 = new ArrayList();
      ArrayList var8 = new ArrayList();
      Iterator var9 = this.zagk.keySet().iterator();

      while(var9.hasNext()) {
         Api.Client var10 = (Api.Client)var9.next();
         if(var10.requiresGooglePlayServices() && !zaam.zaa((zaam)this.zagk.get(var10))) {
            var7.add(var10);
         } else {
            var8.add(var10);
         }
      }

      int var1 = -1;
      boolean var5 = var7.isEmpty();
      byte var3 = 0;
      int var2 = 0;
      int var4;
      int var11;
      Object var14;
      Api.Client var15;
      if(var5) {
         var7 = (ArrayList)var8;
         var4 = var7.size();

         while(var2 < var4) {
            var14 = var7.get(var2);
            ++var2;
            var15 = (Api.Client)var14;
            var11 = var6.getClientAvailability(zaak.zaa(this.zagi), var15);
            var1 = var11;
            if(var11 == 0) {
               var1 = var11;
               break;
            }
         }
      } else {
         var7 = (ArrayList)var7;
         var4 = var7.size();
         var2 = var3;

         while(var2 < var4) {
            var14 = var7.get(var2);
            ++var2;
            var15 = (Api.Client)var14;
            var11 = var6.getClientAvailability(zaak.zaa(this.zagi), var15);
            var1 = var11;
            if(var11 != 0) {
               var1 = var11;
               break;
            }
         }
      }

      if(var1 != 0) {
         ConnectionResult var12 = new ConnectionResult(var1, (PendingIntent)null);
         zaak.zad(this.zagi).zaa((zabf)(new zaao(this, this.zagi, var12)));
      } else {
         if(zaak.zae(this.zagi)) {
            zaak.zaf(this.zagi).b();
         }

         Iterator var13 = this.zagk.keySet().iterator();

         while(var13.hasNext()) {
            var15 = (Api.Client)var13.next();
            BaseGmsClient.ConnectionProgressReportCallbacks var16 = (BaseGmsClient.ConnectionProgressReportCallbacks)this.zagk.get(var15);
            if(var15.requiresGooglePlayServices() && var6.getClientAvailability(zaak.zaa(this.zagi), var15) != 0) {
               zaak.zad(this.zagi).zaa((zabf)(new zaap(this, this.zagi, var16)));
            } else {
               var15.connect(var16);
            }
         }

      }
   }
}
