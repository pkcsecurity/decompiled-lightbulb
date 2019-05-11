package com.facebook.imagepipeline.core;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.cache.disk.DiskStorage;
import com.facebook.cache.disk.DiskStorageCache;
import com.facebook.cache.disk.FileCache;
import com.facebook.imagepipeline.core.DiskStorageFactory;
import com.facebook.imagepipeline.core.FileCacheFactory;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DiskStorageCacheFactory implements FileCacheFactory {

   private DiskStorageFactory mDiskStorageFactory;


   public DiskStorageCacheFactory(DiskStorageFactory var1) {
      this.mDiskStorageFactory = var1;
   }

   public static DiskStorageCache buildDiskStorageCache(DiskCacheConfig var0, DiskStorage var1) {
      return buildDiskStorageCache(var0, var1, Executors.newSingleThreadExecutor());
   }

   public static DiskStorageCache buildDiskStorageCache(DiskCacheConfig var0, DiskStorage var1, Executor var2) {
      DiskStorageCache.Params var3 = new DiskStorageCache.Params(var0.getMinimumSizeLimit(), var0.getLowDiskSpaceSizeLimit(), var0.getDefaultSizeLimit());
      return new DiskStorageCache(var1, var0.getEntryEvictionComparatorSupplier(), var3, var0.getCacheEventListener(), var0.getCacheErrorLogger(), var0.getDiskTrimmableRegistry(), var0.getContext(), var2, var0.getIndexPopulateAtStartupEnabled());
   }

   public FileCache get(DiskCacheConfig var1) {
      return buildDiskStorageCache(var1, this.mDiskStorageFactory.get(var1));
   }
}
