package com.google.android.gms.common;

import android.content.Context;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import com.google.android.gms.common.zze;
import com.google.android.gms.common.zzm;
import javax.annotation.CheckReturnValue;

@CheckReturnValue
final class zzc {

   private static volatile com.google.android.gms.common.internal.zzm zzn;
   private static final Object zzo = new Object();
   private static Context zzp;


   static zzm zza(String var0, zze var1, boolean var2) {
      ThreadPolicy var3 = StrictMode.allowThreadDiskReads();

      zzm var6;
      try {
         var6 = zzb(var0, var1, var2);
      } finally {
         StrictMode.setThreadPolicy(var3);
      }

      return var6;
   }

   // $FF: synthetic method
   static final String zza(boolean var0, String var1, zze var2) throws Exception {
      boolean var3 = true;
      if(var0 || !zzb(var1, var2, true).zzac) {
         var3 = false;
      }

      return zzm.zza(var1, var2, var0, var3);
   }

   static void zza(Context param0) {
      // $FF: Couldn't be decompiled
   }

   private static zzm zzb(String param0, zze param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }
}
