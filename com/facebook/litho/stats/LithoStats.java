package com.facebook.litho.stats;

import java.util.concurrent.atomic.AtomicLong;

public final class LithoStats {

   private static final AtomicLong sStateUpdates = new AtomicLong(0L);
   private static final AtomicLong sStateUpdatesSync = new AtomicLong(0L);


   public static long getStateUpdates() {
      return sStateUpdates.get();
   }

   public static long getStateUpdatesSync() {
      return sStateUpdatesSync.get();
   }

   public static long incStateUpdate(long var0) {
      return sStateUpdates.addAndGet(var0);
   }

   public static long incStateUpdateSync(long var0) {
      return sStateUpdatesSync.addAndGet(var0);
   }
}
