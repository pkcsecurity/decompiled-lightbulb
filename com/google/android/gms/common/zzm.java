package com.google.android.gms.common;

import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.zze;
import com.google.android.gms.common.zzn;
import com.google.android.gms.common.zzo;
import com.google.android.gms.common.util.AndroidUtilsLight;
import com.google.android.gms.common.util.Hex;
import java.util.concurrent.Callable;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@CheckReturnValue
class zzm {

   private static final zzm zzab = new zzm(true, (String)null, (Throwable)null);
   private final Throwable cause;
   final boolean zzac;
   private final String zzad;


   zzm(boolean var1, @Nullable String var2, @Nullable Throwable var3) {
      this.zzac = var1;
      this.zzad = var2;
      this.cause = var3;
   }

   static zzm zza(@NonNull String var0, @NonNull Throwable var1) {
      return new zzm(false, var0, var1);
   }

   static zzm zza(Callable<String> var0) {
      return new zzo(var0, (zzn)null);
   }

   static String zza(String var0, zze var1, boolean var2, boolean var3) {
      String var4;
      if(var3) {
         var4 = "debug cert rejected";
      } else {
         var4 = "not whitelisted";
      }

      return String.format("%s: pkg=%s, sha1=%s, atk=%s, ver=%s", new Object[]{var4, var0, Hex.zza(AndroidUtilsLight.zzi("SHA-1").digest(var1.getBytes())), Boolean.valueOf(var2), "12451009.false"});
   }

   static zzm zzb(@NonNull String var0) {
      return new zzm(false, var0, (Throwable)null);
   }

   static zzm zze() {
      return zzab;
   }

   @Nullable
   String getErrorMessage() {
      return this.zzad;
   }

   final void zzf() {
      if(!this.zzac && Log.isLoggable("GoogleCertificatesRslt", 3)) {
         if(this.cause != null) {
            Log.d("GoogleCertificatesRslt", this.getErrorMessage(), this.cause);
            return;
         }

         Log.d("GoogleCertificatesRslt", this.getErrorMessage());
      }

   }
}
