package com.facebook.litho;

import com.facebook.litho.LayoutThreadFactory;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LayoutThreadPoolExecutor extends ThreadPoolExecutor {

   public LayoutThreadPoolExecutor(int var1, int var2, int var3) {
      super(var1, var2, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue(), new LayoutThreadFactory(var3));
   }
}
