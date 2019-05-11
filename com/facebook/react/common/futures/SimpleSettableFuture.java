package com.facebook.react.common.futures;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.Nullable;

public class SimpleSettableFuture<T extends Object> implements Future<T> {

   @Nullable
   private Exception mException;
   private final CountDownLatch mReadyLatch = new CountDownLatch(1);
   @Nullable
   private T mResult;


   private void checkNotSet() {
      if(this.mReadyLatch.getCount() == 0L) {
         throw new RuntimeException("Result has already been set!");
      }
   }

   public boolean cancel(boolean var1) {
      throw new UnsupportedOperationException();
   }

   @Nullable
   public T get() throws InterruptedException, ExecutionException {
      this.mReadyLatch.await();
      if(this.mException != null) {
         throw new ExecutionException(this.mException);
      } else {
         return this.mResult;
      }
   }

   @Nullable
   public T get(long var1, TimeUnit var3) throws InterruptedException, ExecutionException, TimeoutException {
      if(!this.mReadyLatch.await(var1, var3)) {
         throw new TimeoutException("Timed out waiting for result");
      } else if(this.mException != null) {
         throw new ExecutionException(this.mException);
      } else {
         return this.mResult;
      }
   }

   @Nullable
   public T getOrThrow() {
      try {
         Object var1 = this.get();
         return var1;
      } catch (ExecutionException var2) {
         throw new RuntimeException(var2);
      }
   }

   @Nullable
   public T getOrThrow(long var1, TimeUnit var3) {
      try {
         Object var5 = this.get(var1, var3);
         return var5;
      } catch (TimeoutException var4) {
         throw new RuntimeException(var4);
      }
   }

   public boolean isCancelled() {
      return false;
   }

   public boolean isDone() {
      return this.mReadyLatch.getCount() == 0L;
   }

   public void set(@Nullable T var1) {
      this.checkNotSet();
      this.mResult = var1;
      this.mReadyLatch.countDown();
   }

   public void setException(Exception var1) {
      this.checkNotSet();
      this.mException = var1;
      this.mReadyLatch.countDown();
   }
}
