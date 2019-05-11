package com.facebook.litho;

import com.facebook.litho.config.ComponentsConfiguration;

public abstract class ThreadTracingRunnable implements Runnable {

   private static final String MESSAGE_PART_1 = "Runnable instantiated on thread id: ";
   private static final String MESSAGE_PART_2 = ", name: ";
   private final Throwable mTracingThrowable;


   public ThreadTracingRunnable() {
      Thread var1 = Thread.currentThread();
      StringBuilder var2 = new StringBuilder("Runnable instantiated on thread id: ");
      var2.append(var1.getId());
      var2.append(", name: ");
      var2.append(var1.getName());
      this.mTracingThrowable = new Throwable(var2.toString());
   }

   public ThreadTracingRunnable(Throwable var1) {
      this();
      this.mTracingThrowable.initCause(var1);
   }

   public void resetTrace() {
      this.mTracingThrowable.fillInStackTrace();
   }

   public final void run() {
      try {
         this.tracedRun(this.mTracingThrowable);
      } catch (Throwable var3) {
         if(ComponentsConfiguration.enableThreadTracingStacktrace) {
            Throwable var1;
            for(var1 = var3; var1.getCause() != null; var1 = var1.getCause()) {
               ;
            }

            var1.initCause(this.mTracingThrowable);
         }

         throw var3;
      }
   }

   public abstract void tracedRun(Throwable var1);
}
