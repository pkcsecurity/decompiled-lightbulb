package com.google.android.gms.common.internal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.SparseIntArray;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.Preconditions;

public class GoogleApiAvailabilityCache {

   private final SparseIntArray zaor;
   private GoogleApiAvailabilityLight zaos;


   public GoogleApiAvailabilityCache() {
      this(GoogleApiAvailability.getInstance());
   }

   public GoogleApiAvailabilityCache(@NonNull GoogleApiAvailabilityLight var1) {
      this.zaor = new SparseIntArray();
      Preconditions.checkNotNull(var1);
      this.zaos = var1;
   }

   public void flush() {
      this.zaor.clear();
   }

   public int getClientAvailability(@NonNull Context var1, @NonNull Api.Client var2) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var2);
      if(!var2.requiresGooglePlayServices()) {
         return 0;
      } else {
         int var6 = var2.getMinApkVersion();
         int var5 = this.zaor.get(var6, -1);
         if(var5 != -1) {
            return var5;
         } else {
            int var4 = 0;

            int var3;
            while(true) {
               var3 = var5;
               if(var4 >= this.zaor.size()) {
                  break;
               }

               var3 = this.zaor.keyAt(var4);
               if(var3 > var6 && this.zaor.get(var3) == 0) {
                  var3 = 0;
                  break;
               }

               ++var4;
            }

            var4 = var3;
            if(var3 == -1) {
               var4 = this.zaos.isGooglePlayServicesAvailable(var1, var6);
            }

            this.zaor.put(var6, var4);
            return var4;
         }
      }
   }
}
