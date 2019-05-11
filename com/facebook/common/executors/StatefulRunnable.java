package com.facebook.common.executors;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class StatefulRunnable<T extends Object> implements Runnable {

   protected static final int STATE_CANCELLED = 2;
   protected static final int STATE_CREATED = 0;
   protected static final int STATE_FAILED = 4;
   protected static final int STATE_FINISHED = 3;
   protected static final int STATE_STARTED = 1;
   protected final AtomicInteger mState = new AtomicInteger(0);


   public void cancel() {
      if(this.mState.compareAndSet(0, 2)) {
         this.onCancellation();
      }

   }

   public void disposeResult(T var1) {}

   public abstract T getResult() throws Exception;

   public void onCancellation() {}

   public void onFailure(Exception var1) {}

   public void onSuccess(T var1) {}

   public final void run() {
      if(this.mState.compareAndSet(0, 1)) {
         Object var1;
         try {
            var1 = this.getResult();
         } catch (Exception var6) {
            this.mState.set(4);
            this.onFailure(var6);
            return;
         }

         this.mState.set(3);

         try {
            this.onSuccess(var1);
         } finally {
            this.disposeResult(var1);
         }

      }
   }
}
