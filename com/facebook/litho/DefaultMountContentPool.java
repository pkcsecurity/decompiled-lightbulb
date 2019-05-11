package com.facebook.litho;

import android.content.Context;
import com.facebook.litho.ComponentLifecycle;
import com.facebook.litho.MountContentPool;
import com.facebook.litho.RecyclePool;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultMountContentPool extends RecyclePool implements MountContentPool {

   private final AtomicInteger mAllocationCount = new AtomicInteger(0);
   private final int mPoolSize;


   public DefaultMountContentPool(String var1, int var2, boolean var3) {
      super(var1, var2, var3);
      this.mPoolSize = var2;
   }

   public final Object acquire() {
      throw new UnsupportedOperationException("Call acquire(ComponentContext, ComponentLifecycle)");
   }

   public Object acquire(Context var1, ComponentLifecycle var2) {
      Object var3 = super.acquire();
      if(var3 != null) {
         return var3;
      } else {
         this.mAllocationCount.incrementAndGet();
         return var2.createMountContent(var1);
      }
   }

   public void maybePreallocateContent(Context var1, ComponentLifecycle var2) {
      if(!this.isFull() && this.mAllocationCount.getAndIncrement() < this.mPoolSize) {
         this.release(var2.createMountContent(var1));
      }

   }
}
