package com.facebook.cache.disk;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.cache.common.CacheErrorLogger;
import com.facebook.cache.disk.DefaultDiskStorage;
import com.facebook.cache.disk.DiskStorage;
import com.facebook.common.file.FileTree;
import com.facebook.common.file.FileUtils;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.logging.FLog;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import javax.annotation.Nullable;

public class DynamicDefaultDiskStorage implements DiskStorage {

   private static final Class<?> TAG = DynamicDefaultDiskStorage.class;
   private final String mBaseDirectoryName;
   private final Supplier<File> mBaseDirectoryPathSupplier;
   private final CacheErrorLogger mCacheErrorLogger;
   @VisibleForTesting
   volatile DynamicDefaultDiskStorage.State mCurrentState;
   private final int mVersion;


   public DynamicDefaultDiskStorage(int var1, Supplier<File> var2, String var3, CacheErrorLogger var4) {
      this.mVersion = var1;
      this.mCacheErrorLogger = var4;
      this.mBaseDirectoryPathSupplier = var2;
      this.mBaseDirectoryName = var3;
      this.mCurrentState = new DynamicDefaultDiskStorage.State((File)null, (DiskStorage)null);
   }

   private void createStorage() throws IOException {
      File var1 = new File((File)this.mBaseDirectoryPathSupplier.get(), this.mBaseDirectoryName);
      this.createRootDirectoryIfNecessary(var1);
      this.mCurrentState = new DynamicDefaultDiskStorage.State(var1, new DefaultDiskStorage(var1, this.mVersion, this.mCacheErrorLogger));
   }

   private boolean shouldCreateNewStorage() {
      DynamicDefaultDiskStorage.State var1 = this.mCurrentState;
      return var1.delegate == null || var1.rootDirectory == null || !var1.rootDirectory.exists();
   }

   public void clearAll() throws IOException {
      this.get().clearAll();
   }

   public boolean contains(String var1, Object var2) throws IOException {
      return this.get().contains(var1, var2);
   }

   @VisibleForTesting
   void createRootDirectoryIfNecessary(File var1) throws IOException {
      try {
         FileUtils.mkdirs(var1);
      } catch (FileUtils.CreateDirectoryException var2) {
         this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.WRITE_CREATE_DIR, TAG, "createRootDirectoryIfNecessary", var2);
         throw var2;
      }

      FLog.d(TAG, "Created cache directory %s", (Object)var1.getAbsolutePath());
   }

   @VisibleForTesting
   void deleteOldStorageIfNecessary() {
      if(this.mCurrentState.delegate != null && this.mCurrentState.rootDirectory != null) {
         FileTree.deleteRecursively(this.mCurrentState.rootDirectory);
      }

   }

   @VisibleForTesting
   DiskStorage get() throws IOException {
      synchronized(this){}

      DiskStorage var1;
      try {
         if(this.shouldCreateNewStorage()) {
            this.deleteOldStorageIfNecessary();
            this.createStorage();
         }

         var1 = (DiskStorage)Preconditions.checkNotNull(this.mCurrentState.delegate);
      } finally {
         ;
      }

      return var1;
   }

   public DiskStorage.DiskDumpInfo getDumpInfo() throws IOException {
      return this.get().getDumpInfo();
   }

   public Collection<DiskStorage.Entry> getEntries() throws IOException {
      return this.get().getEntries();
   }

   public BinaryResource getResource(String var1, Object var2) throws IOException {
      return this.get().getResource(var1, var2);
   }

   public String getStorageName() {
      try {
         String var1 = this.get().getStorageName();
         return var1;
      } catch (IOException var2) {
         return "";
      }
   }

   public DiskStorage.Inserter insert(String var1, Object var2) throws IOException {
      return this.get().insert(var1, var2);
   }

   public boolean isEnabled() {
      try {
         boolean var1 = this.get().isEnabled();
         return var1;
      } catch (IOException var3) {
         return false;
      }
   }

   public boolean isExternal() {
      try {
         boolean var1 = this.get().isExternal();
         return var1;
      } catch (IOException var3) {
         return false;
      }
   }

   public void purgeUnexpectedResources() {
      try {
         this.get().purgeUnexpectedResources();
      } catch (IOException var2) {
         FLog.e(TAG, "purgeUnexpectedResources", (Throwable)var2);
      }
   }

   public long remove(DiskStorage.Entry var1) throws IOException {
      return this.get().remove(var1);
   }

   public long remove(String var1) throws IOException {
      return this.get().remove(var1);
   }

   public boolean touch(String var1, Object var2) throws IOException {
      return this.get().touch(var1, var2);
   }

   @VisibleForTesting
   static class State {

      @Nullable
      public final DiskStorage delegate;
      @Nullable
      public final File rootDirectory;


      @VisibleForTesting
      State(@Nullable File var1, @Nullable DiskStorage var2) {
         this.delegate = var2;
         this.rootDirectory = var1;
      }
   }
}
