package com.facebook.imagepipeline.core;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.cache.disk.DiskStorage;
import com.facebook.cache.disk.DynamicDefaultDiskStorage;
import com.facebook.imagepipeline.core.DiskStorageFactory;

public class DynamicDefaultDiskStorageFactory implements DiskStorageFactory {

   public DiskStorage get(DiskCacheConfig var1) {
      return new DynamicDefaultDiskStorage(var1.getVersion(), var1.getBaseDirectoryPathSupplier(), var1.getBaseDirectoryName(), var1.getCacheErrorLogger());
   }
}
