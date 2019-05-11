package com.google.android.gms.common.internal;

import android.content.Context;
import javax.annotation.concurrent.GuardedBy;

public final class zzp {

   private static Object sLock = new Object();
   @GuardedBy
   private static boolean zzeo;
   private static String zzep;
   private static int zzeq;


   public static String zzc(Context var0) {
      zze(var0);
      return zzep;
   }

   public static int zzd(Context var0) {
      zze(var0);
      return zzeq;
   }

   private static void zze(Context param0) {
      // $FF: Couldn't be decompiled
   }
}
