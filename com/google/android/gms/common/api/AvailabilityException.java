package com.google.android.gms.common.api;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.zai;
import com.google.android.gms.common.internal.Preconditions;
import java.util.ArrayList;
import java.util.Iterator;

public class AvailabilityException extends Exception {

   private final ArrayMap<zai<?>, ConnectionResult> zaay;


   public AvailabilityException(ArrayMap<zai<?>, ConnectionResult> var1) {
      this.zaay = var1;
   }

   public ConnectionResult getConnectionResult(GoogleApi<? extends Api.ApiOptions> var1) {
      zai var3 = var1.zak();
      boolean var2;
      if(this.zaay.get(var3) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "The given API was not part of the availability request.");
      return (ConnectionResult)this.zaay.get(var3);
   }

   public String getMessage() {
      ArrayList var2 = new ArrayList();
      Iterator var3 = this.zaay.keySet().iterator();
      boolean var1 = true;

      while(var3.hasNext()) {
         zai var4 = (zai)var3.next();
         ConnectionResult var5 = (ConnectionResult)this.zaay.get(var4);
         if(var5.isSuccess()) {
            var1 = false;
         }

         String var8 = var4.zan();
         String var9 = String.valueOf(var5);
         StringBuilder var6 = new StringBuilder(String.valueOf(var8).length() + 2 + String.valueOf(var9).length());
         var6.append(var8);
         var6.append(": ");
         var6.append(var9);
         var2.add(var6.toString());
      }

      StringBuilder var7 = new StringBuilder();
      if(var1) {
         var7.append("None of the queried APIs are available. ");
      } else {
         var7.append("Some of the queried APIs are unavailable. ");
      }

      var7.append(TextUtils.join("; ", var2));
      return var7.toString();
   }

   public final ArrayMap<zai<?>, ConnectionResult> zaj() {
      return this.zaay;
   }
}
