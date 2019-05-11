package com.facebook.imagepipeline.memory;

import com.facebook.imagepipeline.memory.BasePool;
import com.facebook.imagepipeline.memory.PoolStatsTracker;

public class NoOpPoolStatsTracker implements PoolStatsTracker {

   private static NoOpPoolStatsTracker sInstance;


   public static NoOpPoolStatsTracker getInstance() {
      synchronized(NoOpPoolStatsTracker.class){}

      NoOpPoolStatsTracker var0;
      try {
         if(sInstance == null) {
            sInstance = new NoOpPoolStatsTracker();
         }

         var0 = sInstance;
      } finally {
         ;
      }

      return var0;
   }

   public void onAlloc(int var1) {}

   public void onFree(int var1) {}

   public void onHardCapReached() {}

   public void onSoftCapReached() {}

   public void onValueRelease(int var1) {}

   public void onValueReuse(int var1) {}

   public void setBasePool(BasePool var1) {}
}
