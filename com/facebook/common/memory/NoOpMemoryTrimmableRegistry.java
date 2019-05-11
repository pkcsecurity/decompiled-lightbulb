package com.facebook.common.memory;

import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.MemoryTrimmableRegistry;

public class NoOpMemoryTrimmableRegistry implements MemoryTrimmableRegistry {

   private static NoOpMemoryTrimmableRegistry sInstance;


   public static NoOpMemoryTrimmableRegistry getInstance() {
      synchronized(NoOpMemoryTrimmableRegistry.class){}

      NoOpMemoryTrimmableRegistry var0;
      try {
         if(sInstance == null) {
            sInstance = new NoOpMemoryTrimmableRegistry();
         }

         var0 = sInstance;
      } finally {
         ;
      }

      return var0;
   }

   public void registerMemoryTrimmable(MemoryTrimmable var1) {}

   public void unregisterMemoryTrimmable(MemoryTrimmable var1) {}
}
