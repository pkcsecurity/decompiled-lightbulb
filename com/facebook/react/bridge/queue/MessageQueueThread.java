package com.facebook.react.bridge.queue;

import com.facebook.proguard.annotations.DoNotStrip;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

@DoNotStrip
public interface MessageQueueThread {

   @DoNotStrip
   void assertIsOnThread();

   @DoNotStrip
   void assertIsOnThread(String var1);

   @DoNotStrip
   <T extends Object> Future<T> callOnQueue(Callable<T> var1);

   @DoNotStrip
   boolean isOnThread();

   @DoNotStrip
   void quitSynchronous();

   @DoNotStrip
   void runOnQueue(Runnable var1);
}
