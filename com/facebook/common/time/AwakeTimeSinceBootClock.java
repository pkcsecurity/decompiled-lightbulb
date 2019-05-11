package com.facebook.common.time;

import com.facebook.common.internal.DoNotStrip;
import com.facebook.common.time.MonotonicClock;

@DoNotStrip
public class AwakeTimeSinceBootClock implements MonotonicClock {

   @DoNotStrip
   private static final AwakeTimeSinceBootClock INSTANCE = new AwakeTimeSinceBootClock();


   @DoNotStrip
   public static AwakeTimeSinceBootClock get() {
      return INSTANCE;
   }

   @DoNotStrip
   public long now() {
      return android.os.SystemClock.uptimeMillis();
   }
}
