package com.google.android.gms.common.internal;

import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;

@KeepForSdk
public final class GmsLogger {

   private static final int zzef = 15;
   private static final String zzeg;
   private final String zzeh;
   private final String zzei;


   public GmsLogger(String var1) {
      this(var1, (String)null);
   }

   public GmsLogger(String var1, String var2) {
      Preconditions.checkNotNull(var1, "log tag cannot be null");
      boolean var3;
      if(var1.length() <= 23) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "tag \"%s\" is longer than the %d character maximum", new Object[]{var1, Integer.valueOf(23)});
      this.zzeh = var1;
      if(var2 != null && var2.length() > 0) {
         this.zzei = var2;
      } else {
         this.zzei = null;
      }
   }

   private final String zza(String var1, Object ... var2) {
      var1 = String.format(var1, var2);
      return this.zzei == null?var1:this.zzei.concat(var1);
   }

   private final String zzh(String var1) {
      return this.zzei == null?var1:this.zzei.concat(var1);
   }

   @KeepForSdk
   public final boolean canLog(int var1) {
      return Log.isLoggable(this.zzeh, var1);
   }

   @KeepForSdk
   public final boolean canLogPii() {
      return false;
   }

   @KeepForSdk
   public final void d(String var1, String var2) {
      if(this.canLog(3)) {
         Log.d(var1, this.zzh(var2));
      }

   }

   @KeepForSdk
   public final void d(String var1, String var2, Throwable var3) {
      if(this.canLog(3)) {
         Log.d(var1, this.zzh(var2), var3);
      }

   }

   @KeepForSdk
   public final void e(String var1, String var2) {
      if(this.canLog(6)) {
         Log.e(var1, this.zzh(var2));
      }

   }

   @KeepForSdk
   public final void e(String var1, String var2, Throwable var3) {
      if(this.canLog(6)) {
         Log.e(var1, this.zzh(var2), var3);
      }

   }

   @KeepForSdk
   public final void efmt(String var1, String var2, Object ... var3) {
      if(this.canLog(6)) {
         Log.e(var1, this.zza(var2, var3));
      }

   }

   @KeepForSdk
   public final void i(String var1, String var2) {
      if(this.canLog(4)) {
         Log.i(var1, this.zzh(var2));
      }

   }

   @KeepForSdk
   public final void i(String var1, String var2, Throwable var3) {
      if(this.canLog(4)) {
         Log.i(var1, this.zzh(var2), var3);
      }

   }

   @KeepForSdk
   public final void pii(String var1, String var2) {
      if(this.canLogPii()) {
         var1 = String.valueOf(var1);
         String var3 = String.valueOf(" PII_LOG");
         if(var3.length() != 0) {
            var1 = var1.concat(var3);
         } else {
            var1 = new String(var1);
         }

         Log.i(var1, this.zzh(var2));
      }

   }

   @KeepForSdk
   public final void pii(String var1, String var2, Throwable var3) {
      if(this.canLogPii()) {
         var1 = String.valueOf(var1);
         String var4 = String.valueOf(" PII_LOG");
         if(var4.length() != 0) {
            var1 = var1.concat(var4);
         } else {
            var1 = new String(var1);
         }

         Log.i(var1, this.zzh(var2), var3);
      }

   }

   @KeepForSdk
   public final void v(String var1, String var2) {
      if(this.canLog(2)) {
         Log.v(var1, this.zzh(var2));
      }

   }

   @KeepForSdk
   public final void v(String var1, String var2, Throwable var3) {
      if(this.canLog(2)) {
         Log.v(var1, this.zzh(var2), var3);
      }

   }

   @KeepForSdk
   public final void w(String var1, String var2) {
      if(this.canLog(5)) {
         Log.w(var1, this.zzh(var2));
      }

   }

   @KeepForSdk
   public final void w(String var1, String var2, Throwable var3) {
      if(this.canLog(5)) {
         Log.w(var1, this.zzh(var2), var3);
      }

   }

   @KeepForSdk
   public final void wfmt(String var1, String var2, Object ... var3) {
      if(this.canLog(5)) {
         Log.w(this.zzeh, this.zza(var2, var3));
      }

   }

   @KeepForSdk
   public final void wtf(String var1, String var2, Throwable var3) {
      if(this.canLog(7)) {
         Log.e(var1, this.zzh(var2), var3);
         Log.wtf(var1, this.zzh(var2), var3);
      }

   }
}
