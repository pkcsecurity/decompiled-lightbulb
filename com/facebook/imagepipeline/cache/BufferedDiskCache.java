package com.facebook.imagepipeline.cache;

import bolts.Task;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.WriterCallback;
import com.facebook.cache.disk.FileCache;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.common.memory.PooledByteStreams;
import com.facebook.imagepipeline.cache.ImageCacheStatsTracker;
import com.facebook.imagepipeline.cache.StagingArea;
import com.facebook.imagepipeline.image.EncodedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public class BufferedDiskCache {

   private static final Class<?> TAG = BufferedDiskCache.class;
   private final FileCache mFileCache;
   private final ImageCacheStatsTracker mImageCacheStatsTracker;
   private final PooledByteBufferFactory mPooledByteBufferFactory;
   private final PooledByteStreams mPooledByteStreams;
   private final Executor mReadExecutor;
   private final StagingArea mStagingArea;
   private final Executor mWriteExecutor;


   public BufferedDiskCache(FileCache var1, PooledByteBufferFactory var2, PooledByteStreams var3, Executor var4, Executor var5, ImageCacheStatsTracker var6) {
      this.mFileCache = var1;
      this.mPooledByteBufferFactory = var2;
      this.mPooledByteStreams = var3;
      this.mReadExecutor = var4;
      this.mWriteExecutor = var5;
      this.mImageCacheStatsTracker = var6;
      this.mStagingArea = StagingArea.getInstance();
   }

   // $FF: synthetic method
   static Class access$200() {
      return TAG;
   }

   // $FF: synthetic method
   static ImageCacheStatsTracker access$300(BufferedDiskCache var0) {
      return var0.mImageCacheStatsTracker;
   }

   // $FF: synthetic method
   static PooledByteBuffer access$400(BufferedDiskCache var0, CacheKey var1) throws IOException {
      return var0.readFromDiskCache(var1);
   }

   private boolean checkInStagingAreaAndFileCache(CacheKey var1) {
      EncodedImage var3 = this.mStagingArea.get(var1);
      if(var3 != null) {
         var3.close();
         FLog.v(TAG, "Found image for %s in staging area", (Object)var1.getUriString());
         this.mImageCacheStatsTracker.onStagingAreaHit(var1);
         return true;
      } else {
         FLog.v(TAG, "Did not find image for %s in staging area", (Object)var1.getUriString());
         this.mImageCacheStatsTracker.onStagingAreaMiss();

         try {
            boolean var2 = this.mFileCache.hasKey(var1);
            return var2;
         } catch (Exception var4) {
            return false;
         }
      }
   }

   private Task<Boolean> containsAsync(final CacheKey var1) {
      try {
         Task var2 = Task.a(new Callable() {
            public Boolean call() throws Exception {
               return Boolean.valueOf(BufferedDiskCache.this.checkInStagingAreaAndFileCache(var1));
            }
         }, this.mReadExecutor);
         return var2;
      } catch (Exception var3) {
         FLog.w(TAG, var3, "Failed to schedule disk-cache read for %s", new Object[]{var1.getUriString()});
         return Task.a(var3);
      }
   }

   private Task<EncodedImage> foundPinnedImage(CacheKey var1, EncodedImage var2) {
      FLog.v(TAG, "Found image for %s in staging area", (Object)var1.getUriString());
      this.mImageCacheStatsTracker.onStagingAreaHit(var1);
      return Task.a((Object)var2);
   }

   private Task<EncodedImage> getAsync(final CacheKey var1, final AtomicBoolean var2) {
      try {
         Task var4 = Task.a(new Callable() {
            public EncodedImage call() throws Exception {
               // $FF: Couldn't be decompiled
            }
         }, this.mReadExecutor);
         return var4;
      } catch (Exception var3) {
         FLog.w(TAG, var3, "Failed to schedule disk-cache read for %s", new Object[]{var1.getUriString()});
         return Task.a(var3);
      }
   }

   private PooledByteBuffer readFromDiskCache(CacheKey param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private void writeToDiskCache(CacheKey var1, final EncodedImage var2) {
      FLog.v(TAG, "About to write to disk-cache for key %s", (Object)var1.getUriString());

      try {
         this.mFileCache.insert(var1, new WriterCallback() {
            public void write(OutputStream var1) throws IOException {
               BufferedDiskCache.this.mPooledByteStreams.copy(var2.getInputStream(), var1);
            }
         });
         FLog.v(TAG, "Successful disk-cache write for key %s", (Object)var1.getUriString());
      } catch (IOException var3) {
         FLog.w(TAG, var3, "Failed to write to disk-cache for key %s", new Object[]{var1.getUriString()});
      }
   }

   public Task<Void> clearAll() {
      this.mStagingArea.clearAll();

      try {
         Task var1 = Task.a(new Callable() {
            public Void call() throws Exception {
               BufferedDiskCache.this.mStagingArea.clearAll();
               BufferedDiskCache.this.mFileCache.clearAll();
               return null;
            }
         }, this.mWriteExecutor);
         return var1;
      } catch (Exception var2) {
         FLog.w(TAG, var2, "Failed to schedule disk-cache clear", new Object[0]);
         return Task.a(var2);
      }
   }

   public Task<Boolean> contains(CacheKey var1) {
      return this.containsSync(var1)?Task.a((Object)Boolean.valueOf(true)):this.containsAsync(var1);
   }

   public boolean containsSync(CacheKey var1) {
      return this.mStagingArea.containsKey(var1) || this.mFileCache.hasKeySync(var1);
   }

   public boolean diskCheckSync(CacheKey var1) {
      return this.containsSync(var1)?true:this.checkInStagingAreaAndFileCache(var1);
   }

   public Task<EncodedImage> get(CacheKey var1, AtomicBoolean var2) {
      EncodedImage var3 = this.mStagingArea.get(var1);
      return var3 != null?this.foundPinnedImage(var1, var3):this.getAsync(var1, var2);
   }

   public void put(final CacheKey var1, EncodedImage var2) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkArgument(EncodedImage.isValid(var2));
      this.mStagingArea.put(var1, var2);
      var2.setEncodedCacheKey(var1);
      final EncodedImage var3 = EncodedImage.cloneOrNull(var2);

      try {
         this.mWriteExecutor.execute(new Runnable() {
            public void run() {
               try {
                  BufferedDiskCache.this.writeToDiskCache(var1, var3);
               } finally {
                  BufferedDiskCache.this.mStagingArea.remove(var1, var3);
                  EncodedImage.closeSafely(var3);
               }

            }
         });
      } catch (Exception var5) {
         FLog.w(TAG, var5, "Failed to schedule disk-cache write for %s", new Object[]{var1.getUriString()});
         this.mStagingArea.remove(var1, var2);
         EncodedImage.closeSafely(var3);
      }
   }

   public Task<Void> remove(final CacheKey var1) {
      Preconditions.checkNotNull(var1);
      this.mStagingArea.remove(var1);

      try {
         Task var2 = Task.a(new Callable() {
            public Void call() throws Exception {
               BufferedDiskCache.this.mStagingArea.remove(var1);
               BufferedDiskCache.this.mFileCache.remove(var1);
               return null;
            }
         }, this.mWriteExecutor);
         return var2;
      } catch (Exception var3) {
         FLog.w(TAG, var3, "Failed to schedule disk-cache remove for %s", new Object[]{var1.getUriString()});
         return Task.a(var3);
      }
   }
}
