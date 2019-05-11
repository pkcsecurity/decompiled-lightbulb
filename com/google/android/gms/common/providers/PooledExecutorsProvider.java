package com.google.android.gms.common.providers;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.providers.zza;
import java.util.concurrent.ScheduledExecutorService;

@KeepForSdk
public class PooledExecutorsProvider {

   private static PooledExecutorsProvider.PooledExecutorFactory zzey;


   @KeepForSdk
   public static PooledExecutorsProvider.PooledExecutorFactory getInstance() {
      synchronized(PooledExecutorsProvider.class){}

      PooledExecutorsProvider.PooledExecutorFactory var0;
      try {
         if(zzey == null) {
            zzey = new zza();
         }

         var0 = zzey;
      } finally {
         ;
      }

      return var0;
   }

   public interface PooledExecutorFactory {

      @KeepForSdk
      ScheduledExecutorService newSingleThreadScheduledExecutor();
   }
}
