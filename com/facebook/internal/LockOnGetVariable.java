package com.facebook.internal;

import com.facebook.FacebookSdk;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;

public class LockOnGetVariable<T extends Object> {

   private CountDownLatch initLatch;
   private T value;


   public LockOnGetVariable(T var1) {
      this.value = var1;
   }

   public LockOnGetVariable(final Callable<T> var1) {
      this.initLatch = new CountDownLatch(1);
      FacebookSdk.getExecutor().execute(new FutureTask(new Callable() {
         public Void call() throws Exception {
            try {
               LockOnGetVariable.this.value = var1.call();
            } finally {
               LockOnGetVariable.this.initLatch.countDown();
            }

            return null;
         }
      }));
   }

   private void waitOnInit() {
      if(this.initLatch != null) {
         try {
            this.initLatch.await();
         } catch (InterruptedException var2) {
            ;
         }
      }
   }

   public T getValue() {
      this.waitOnInit();
      return this.value;
   }
}
