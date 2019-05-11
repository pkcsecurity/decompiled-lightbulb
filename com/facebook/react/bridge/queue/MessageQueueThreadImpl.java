package com.facebook.react.bridge.queue;

import android.os.Looper;
import android.os.Process;
import com.facebook.common.logging.FLog;
import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.SoftAssertions;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.queue.MessageQueueThread;
import com.facebook.react.bridge.queue.MessageQueueThreadHandler;
import com.facebook.react.bridge.queue.MessageQueueThreadSpec;
import com.facebook.react.bridge.queue.QueueThreadExceptionHandler;
import com.facebook.react.common.futures.SimpleSettableFuture;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

@DoNotStrip
public class MessageQueueThreadImpl implements MessageQueueThread {

   private final String mAssertionErrorMessage;
   private final MessageQueueThreadHandler mHandler;
   private volatile boolean mIsFinished = false;
   private final Looper mLooper;
   private final String mName;


   private MessageQueueThreadImpl(String var1, Looper var2, QueueThreadExceptionHandler var3) {
      this.mName = var1;
      this.mLooper = var2;
      this.mHandler = new MessageQueueThreadHandler(var2, var3);
      StringBuilder var4 = new StringBuilder();
      var4.append("Expected to be called from the \'");
      var4.append(this.getName());
      var4.append("\' thread!");
      this.mAssertionErrorMessage = var4.toString();
   }

   public static MessageQueueThreadImpl create(MessageQueueThreadSpec var0, QueueThreadExceptionHandler var1) {
      switch(null.$SwitchMap$com$facebook$react$bridge$queue$MessageQueueThreadSpec$ThreadType[var0.getThreadType().ordinal()]) {
      case 1:
         return createForMainThread(var0.getName(), var1);
      case 2:
         return startNewBackgroundThread(var0.getName(), var0.getStackSize(), var1);
      default:
         StringBuilder var2 = new StringBuilder();
         var2.append("Unknown thread type: ");
         var2.append(var0.getThreadType());
         throw new RuntimeException(var2.toString());
      }
   }

   private static MessageQueueThreadImpl createForMainThread(String var0, QueueThreadExceptionHandler var1) {
      MessageQueueThreadImpl var2 = new MessageQueueThreadImpl(var0, Looper.getMainLooper(), var1);
      if(UiThreadUtil.isOnUiThread()) {
         Process.setThreadPriority(-4);
         return var2;
      } else {
         UiThreadUtil.runOnUiThread(new Runnable() {
            public void run() {
               Process.setThreadPriority(-4);
            }
         });
         return var2;
      }
   }

   private static MessageQueueThreadImpl startNewBackgroundThread(String var0, long var1, QueueThreadExceptionHandler var3) {
      final SimpleSettableFuture var4 = new SimpleSettableFuture();
      Runnable var5 = new Runnable() {
         public void run() {
            Process.setThreadPriority(-4);
            Looper.prepare();
            var4.set(Looper.myLooper());
            Looper.loop();
         }
      };
      StringBuilder var6 = new StringBuilder();
      var6.append("mqt_");
      var6.append(var0);
      (new Thread((ThreadGroup)null, var5, var6.toString(), var1)).start();
      return new MessageQueueThreadImpl(var0, (Looper)var4.getOrThrow(), var3);
   }

   @DoNotStrip
   public void assertIsOnThread() {
      SoftAssertions.assertCondition(this.isOnThread(), this.mAssertionErrorMessage);
   }

   @DoNotStrip
   public void assertIsOnThread(String var1) {
      boolean var2 = this.isOnThread();
      StringBuilder var3 = new StringBuilder();
      var3.append(this.mAssertionErrorMessage);
      var3.append(" ");
      var3.append(var1);
      SoftAssertions.assertCondition(var2, var3.toString());
   }

   @DoNotStrip
   public <T extends Object> Future<T> callOnQueue(final Callable<T> var1) {
      final SimpleSettableFuture var2 = new SimpleSettableFuture();
      this.runOnQueue(new Runnable() {
         public void run() {
            try {
               var2.set(var1.call());
            } catch (Exception var2x) {
               var2.setException(var2x);
            }
         }
      });
      return var2;
   }

   public Looper getLooper() {
      return this.mLooper;
   }

   public String getName() {
      return this.mName;
   }

   @DoNotStrip
   public boolean isOnThread() {
      return this.mLooper.getThread() == Thread.currentThread();
   }

   @DoNotStrip
   public void quitSynchronous() {
      this.mIsFinished = true;
      this.mLooper.quit();
      if(this.mLooper.getThread() != Thread.currentThread()) {
         try {
            this.mLooper.getThread().join();
         } catch (InterruptedException var2) {
            StringBuilder var1 = new StringBuilder();
            var1.append("Got interrupted waiting to join thread ");
            var1.append(this.mName);
            throw new RuntimeException(var1.toString());
         }
      }
   }

   @DoNotStrip
   public void runOnQueue(Runnable var1) {
      if(this.mIsFinished) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Tried to enqueue runnable on already finished thread: \'");
         var2.append(this.getName());
         var2.append("... dropping Runnable.");
         FLog.w("ReactNative", var2.toString());
      }

      this.mHandler.post(var1);
   }
}
