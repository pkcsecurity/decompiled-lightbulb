package com.facebook.react.bridge.queue;

import com.facebook.react.bridge.queue.MessageQueueThread;
import javax.annotation.Nullable;

public interface ReactQueueConfiguration {

   void destroy();

   MessageQueueThread getJSQueueThread();

   MessageQueueThread getNativeModulesQueueThread();

   @Nullable
   MessageQueueThread getUIBackgroundQueueThread();

   MessageQueueThread getUIQueueThread();
}
