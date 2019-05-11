package com.google.android.gms.common.util;

import android.os.SystemClock;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.Clock;

@KeepForSdk
public class DefaultClock implements Clock {

   private static final DefaultClock zzgk = new DefaultClock();


   @KeepForSdk
   public static Clock getInstance() {
      return zzgk;
   }

   public long currentThreadTimeMillis() {
      return SystemClock.currentThreadTimeMillis();
   }

   public long currentTimeMillis() {
      return System.currentTimeMillis();
   }

   public long elapsedRealtime() {
      return SystemClock.elapsedRealtime();
   }

   public long nanoTime() {
      return System.nanoTime();
   }
}
