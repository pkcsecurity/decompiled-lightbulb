package com.facebook.react.bridge.queue;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.facebook.react.bridge.queue.QueueThreadExceptionHandler;

public class MessageQueueThreadHandler extends Handler {

   private final QueueThreadExceptionHandler mExceptionHandler;


   public MessageQueueThreadHandler(Looper var1, QueueThreadExceptionHandler var2) {
      super(var1);
      this.mExceptionHandler = var2;
   }

   public void dispatchMessage(Message var1) {
      try {
         super.dispatchMessage(var1);
      } catch (Exception var2) {
         this.mExceptionHandler.handleException(var2);
      }
   }
}
