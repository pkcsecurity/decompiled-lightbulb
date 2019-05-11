package com.google.android.gms.common.util.concurrent;

import android.os.Process;

final class zza implements Runnable {

   private final int priority;
   private final Runnable zzhs;


   public zza(Runnable var1, int var2) {
      this.zzhs = var1;
      this.priority = var2;
   }

   public final void run() {
      Process.setThreadPriority(this.priority);
      this.zzhs.run();
   }
}
