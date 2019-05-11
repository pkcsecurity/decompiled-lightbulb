package com.google.android.gms.common.logging;

import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.GmsLogger;
import java.util.Locale;

@KeepForSdk
public class Logger {

   private final String mTag;
   private final String zzei;
   private final GmsLogger zzew;
   private final int zzex;


   private Logger(String var1, String var2) {
      this.zzei = var2;
      this.mTag = var1;
      this.zzew = new GmsLogger(var1);

      int var3;
      for(var3 = 2; 7 >= var3 && !Log.isLoggable(this.mTag, var3); ++var3) {
         ;
      }

      this.zzex = var3;
   }

   @KeepForSdk
   public Logger(String var1, String ... var2) {
      String var7;
      if(var2.length == 0) {
         var7 = "";
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append('[');
         int var4 = var2.length;

         for(int var3 = 0; var3 < var4; ++var3) {
            String var6 = var2[var3];
            if(var5.length() > 1) {
               var5.append(",");
            }

            var5.append(var6);
         }

         var5.append(']');
         var5.append(' ');
         var7 = var5.toString();
      }

      this(var1, var7);
   }

   private final String format(String var1, @Nullable Object ... var2) {
      String var3 = var1;
      if(var2 != null) {
         var3 = var1;
         if(var2.length > 0) {
            var3 = String.format(Locale.US, var1, var2);
         }
      }

      return this.zzei.concat(var3);
   }

   @KeepForSdk
   public void d(String var1, @Nullable Object ... var2) {
      if(this.isLoggable(3)) {
         Log.d(this.mTag, this.format(var1, var2));
      }

   }

   @KeepForSdk
   public void e(String var1, Throwable var2, @Nullable Object ... var3) {
      Log.e(this.mTag, this.format(var1, var3), var2);
   }

   @KeepForSdk
   public void e(String var1, @Nullable Object ... var2) {
      Log.e(this.mTag, this.format(var1, var2));
   }

   @KeepForSdk
   public void i(String var1, @Nullable Object ... var2) {
      Log.i(this.mTag, this.format(var1, var2));
   }

   @KeepForSdk
   public boolean isLoggable(int var1) {
      return this.zzex <= var1;
   }

   @KeepForSdk
   public void v(String var1, @Nullable Object ... var2) {
      if(this.isLoggable(2)) {
         Log.v(this.mTag, this.format(var1, var2));
      }

   }

   @KeepForSdk
   public void w(String var1, @Nullable Object ... var2) {
      Log.w(this.mTag, this.format(var1, var2));
   }

   @KeepForSdk
   public void wtf(String var1, Throwable var2, @Nullable Object ... var3) {
      Log.wtf(this.mTag, this.format(var1, var3), var2);
   }

   @KeepForSdk
   public void wtf(Throwable var1) {
      Log.wtf(this.mTag, var1);
   }
}
