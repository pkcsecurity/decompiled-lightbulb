package com.facebook.react.bridge.queue;

import android.os.Looper;
import com.facebook.react.bridge.queue.MessageQueueThread;
import com.facebook.react.bridge.queue.MessageQueueThreadImpl;
import com.facebook.react.bridge.queue.MessageQueueThreadSpec;
import com.facebook.react.bridge.queue.QueueThreadExceptionHandler;
import com.facebook.react.bridge.queue.ReactQueueConfiguration;
import com.facebook.react.bridge.queue.ReactQueueConfigurationSpec;
import com.facebook.react.common.MapBuilder;
import java.util.HashMap;
import javax.annotation.Nullable;

public class ReactQueueConfigurationImpl implements ReactQueueConfiguration {

   private final MessageQueueThreadImpl mJSQueueThread;
   private final MessageQueueThreadImpl mNativeModulesQueueThread;
   @Nullable
   private final MessageQueueThreadImpl mUIBackgroundQueueThread;
   private final MessageQueueThreadImpl mUIQueueThread;


   private ReactQueueConfigurationImpl(MessageQueueThreadImpl var1, @Nullable MessageQueueThreadImpl var2, MessageQueueThreadImpl var3, MessageQueueThreadImpl var4) {
      this.mUIQueueThread = var1;
      this.mUIBackgroundQueueThread = var2;
      this.mNativeModulesQueueThread = var3;
      this.mJSQueueThread = var4;
   }

   public static ReactQueueConfigurationImpl create(ReactQueueConfigurationSpec var0, QueueThreadExceptionHandler var1) {
      HashMap var5 = MapBuilder.newHashMap();
      MessageQueueThreadSpec var2 = MessageQueueThreadSpec.mainThreadSpec();
      MessageQueueThreadImpl var6 = MessageQueueThreadImpl.create(var2, var1);
      var5.put(var2, var6);
      MessageQueueThreadImpl var3 = (MessageQueueThreadImpl)var5.get(var0.getJSQueueThreadSpec());
      MessageQueueThreadImpl var8 = var3;
      if(var3 == null) {
         var8 = MessageQueueThreadImpl.create(var0.getJSQueueThreadSpec(), var1);
      }

      MessageQueueThreadImpl var4 = (MessageQueueThreadImpl)var5.get(var0.getNativeModulesQueueThreadSpec());
      var3 = var4;
      if(var4 == null) {
         var3 = MessageQueueThreadImpl.create(var0.getNativeModulesQueueThreadSpec(), var1);
      }

      MessageQueueThreadImpl var7 = (MessageQueueThreadImpl)var5.get(var0.getUIBackgroundQueueThreadSpec());
      var4 = var7;
      if(var7 == null) {
         var4 = var7;
         if(var0.getUIBackgroundQueueThreadSpec() != null) {
            var4 = MessageQueueThreadImpl.create(var0.getUIBackgroundQueueThreadSpec(), var1);
         }
      }

      return new ReactQueueConfigurationImpl(var6, var4, var3, var8);
   }

   public void destroy() {
      if(this.mUIBackgroundQueueThread != null && this.mUIBackgroundQueueThread.getLooper() != Looper.getMainLooper()) {
         this.mUIBackgroundQueueThread.quitSynchronous();
      }

      if(this.mNativeModulesQueueThread.getLooper() != Looper.getMainLooper()) {
         this.mNativeModulesQueueThread.quitSynchronous();
      }

      if(this.mJSQueueThread.getLooper() != Looper.getMainLooper()) {
         this.mJSQueueThread.quitSynchronous();
      }

   }

   public MessageQueueThread getJSQueueThread() {
      return this.mJSQueueThread;
   }

   public MessageQueueThread getNativeModulesQueueThread() {
      return this.mNativeModulesQueueThread;
   }

   @Nullable
   public MessageQueueThread getUIBackgroundQueueThread() {
      return this.mUIBackgroundQueueThread;
   }

   public MessageQueueThread getUIQueueThread() {
      return this.mUIQueueThread;
   }
}
