package com.facebook.imagepipeline.producers;

import com.facebook.common.logging.FLog;
import com.facebook.imagepipeline.producers.Consumer;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public abstract class BaseConsumer<T extends Object> implements Consumer<T> {

   private boolean mIsFinished = false;


   public void onCancellation() {
      // $FF: Couldn't be decompiled
   }

   public abstract void onCancellationImpl();

   public void onFailure(Throwable param1) {
      // $FF: Couldn't be decompiled
   }

   public abstract void onFailureImpl(Throwable var1);

   public void onNewResult(@Nullable T param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   public abstract void onNewResultImpl(T var1, boolean var2);

   public void onProgressUpdate(float param1) {
      // $FF: Couldn't be decompiled
   }

   public void onProgressUpdateImpl(float var1) {}

   protected void onUnhandledException(Exception var1) {
      FLog.wtf(this.getClass(), "unhandled exception", (Throwable)var1);
   }
}
