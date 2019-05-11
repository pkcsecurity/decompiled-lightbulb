package com.facebook.litho;

import android.os.Looper;
import android.os.Process;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

class LayoutThreadFactory implements ThreadFactory {

   private static final AtomicInteger threadPoolId = new AtomicInteger(1);
   private final int mThreadPoolId;
   private final int mThreadPriority;
   private final AtomicInteger threadNumber = new AtomicInteger(1);


   public LayoutThreadFactory(int var1) {
      this.mThreadPriority = var1;
      this.mThreadPoolId = threadPoolId.getAndIncrement();
   }

   public Thread newThread(final Runnable var1) {
      var1 = new Runnable() {
         public void run() {
            if(Looper.myLooper() == null) {
               Looper.prepare();
            }

            try {
               Process.setThreadPriority(LayoutThreadFactory.this.mThreadPriority);
            } catch (SecurityException var2) {
               Process.setThreadPriority(LayoutThreadFactory.this.mThreadPriority + 1);
            }

            var1.run();
         }
      };
      StringBuilder var2 = new StringBuilder();
      var2.append("ComponentLayoutThread");
      var2.append(this.mThreadPoolId);
      var2.append("-");
      var2.append(this.threadNumber.getAndIncrement());
      Thread var3 = new Thread(var1, var2.toString());
      var3.setPriority(10);
      return var3;
   }
}
