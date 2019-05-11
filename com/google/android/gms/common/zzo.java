package com.google.android.gms.common;

import com.google.android.gms.common.zzm;
import com.google.android.gms.common.zzn;
import java.util.concurrent.Callable;

final class zzo extends zzm {

   private final Callable<String> zzae;


   private zzo(Callable<String> var1) {
      super(false, (String)null, (Throwable)null);
      this.zzae = var1;
   }

   // $FF: synthetic method
   zzo(Callable var1, zzn var2) {
      this(var1);
   }

   final String getErrorMessage() {
      try {
         String var1 = (String)this.zzae.call();
         return var1;
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }
   }
}
