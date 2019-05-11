package com.facebook.litho;

import com.facebook.litho.LayoutHandler;
import com.facebook.litho.LayoutThreadPoolExecutor;
import com.facebook.litho.config.LayoutThreadPoolConfiguration;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolLayoutHandler implements LayoutHandler {

   private static ThreadPoolExecutor sLayoutThreadPoolExecutor;


   public ThreadPoolLayoutHandler(LayoutThreadPoolConfiguration var1) {
      if(sLayoutThreadPoolExecutor == null) {
         sLayoutThreadPoolExecutor = new LayoutThreadPoolExecutor(var1.getCorePoolSize(), var1.getMaxPoolSize(), var1.getThreadPriority());
      }

   }

   public boolean post(Runnable var1) {
      try {
         sLayoutThreadPoolExecutor.execute(var1);
         return true;
      } catch (RejectedExecutionException var3) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Cannot execute layout calculation task; ");
         var2.append(var3);
         throw new RuntimeException(var2.toString());
      }
   }

   public void removeCallbacks(Runnable var1) {
      sLayoutThreadPoolExecutor.remove(var1);
   }

   public void removeCallbacksAndMessages(Object var1) {
      throw new RuntimeException("Operation not supported");
   }
}
