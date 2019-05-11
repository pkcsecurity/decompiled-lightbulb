package com.facebook.common.disk;

import com.facebook.common.disk.DiskTrimmable;
import com.facebook.common.disk.DiskTrimmableRegistry;

public class NoOpDiskTrimmableRegistry implements DiskTrimmableRegistry {

   private static NoOpDiskTrimmableRegistry sInstance;


   public static NoOpDiskTrimmableRegistry getInstance() {
      synchronized(NoOpDiskTrimmableRegistry.class){}

      NoOpDiskTrimmableRegistry var0;
      try {
         if(sInstance == null) {
            sInstance = new NoOpDiskTrimmableRegistry();
         }

         var0 = sInstance;
      } finally {
         ;
      }

      return var0;
   }

   public void registerDiskTrimmable(DiskTrimmable var1) {}

   public void unregisterDiskTrimmable(DiskTrimmable var1) {}
}
