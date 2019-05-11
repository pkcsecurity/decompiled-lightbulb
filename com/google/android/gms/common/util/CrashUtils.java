package com.google.android.gms.common.util;

import android.content.Context;
import android.os.DropBoxManager;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import javax.annotation.concurrent.GuardedBy;

@KeepForSdk
public final class CrashUtils {

   private static final String[] zzge = new String[]{"android.", "com.android.", "dalvik.", "java.", "javax."};
   private static DropBoxManager zzgf;
   private static boolean zzgg;
   private static int zzgh;
   @GuardedBy
   private static int zzgi;
   @GuardedBy
   private static int zzgj;


   @KeepForSdk
   public static boolean addDynamiteErrorToDropBox(Context var0, Throwable var1) {
      return zza(var0, var1, 536870912);
   }

   private static boolean zza(Context var0, Throwable var1, int var2) {
      try {
         Preconditions.checkNotNull(var0);
         Preconditions.checkNotNull(var1);
         return false;
      } catch (Exception var3) {
         Log.e("CrashUtils", "Error adding exception to DropBox!", var3);
         return false;
      }
   }
}
