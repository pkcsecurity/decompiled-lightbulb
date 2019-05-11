package com.facebook.imagepipeline.memory;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Throwables;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.references.OOMSoftReference;
import com.facebook.common.references.ResourceReleaser;
import com.facebook.imagepipeline.memory.PoolParams;
import java.util.concurrent.Semaphore;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class SharedByteArray implements MemoryTrimmable {

   @VisibleForTesting
   final OOMSoftReference<byte[]> mByteArraySoftRef;
   @VisibleForTesting
   final int mMaxByteArraySize;
   @VisibleForTesting
   final int mMinByteArraySize;
   private final ResourceReleaser<byte[]> mResourceReleaser;
   @VisibleForTesting
   final Semaphore mSemaphore;


   public SharedByteArray(MemoryTrimmableRegistry var1, PoolParams var2) {
      Preconditions.checkNotNull(var1);
      int var3 = var2.minBucketSize;
      boolean var5 = false;
      boolean var4;
      if(var3 > 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4);
      var4 = var5;
      if(var2.maxBucketSize >= var2.minBucketSize) {
         var4 = true;
      }

      Preconditions.checkArgument(var4);
      this.mMaxByteArraySize = var2.maxBucketSize;
      this.mMinByteArraySize = var2.minBucketSize;
      this.mByteArraySoftRef = new OOMSoftReference();
      this.mSemaphore = new Semaphore(1);
      this.mResourceReleaser = new ResourceReleaser() {
         public void release(byte[] var1) {
            SharedByteArray.this.mSemaphore.release();
         }
      };
      var1.registerMemoryTrimmable(this);
   }

   private byte[] allocateByteArray(int var1) {
      synchronized(this){}

      byte[] var2;
      try {
         this.mByteArraySoftRef.clear();
         var2 = new byte[var1];
         this.mByteArraySoftRef.set(var2);
      } finally {
         ;
      }

      return var2;
   }

   private byte[] getByteArray(int var1) {
      var1 = this.getBucketedSize(var1);
      byte[] var3 = (byte[])this.mByteArraySoftRef.get();
      byte[] var2;
      if(var3 != null) {
         var2 = var3;
         if(var3.length >= var1) {
            return var2;
         }
      }

      var2 = this.allocateByteArray(var1);
      return var2;
   }

   public CloseableReference<byte[]> get(int var1) {
      boolean var3 = false;
      boolean var2;
      if(var1 > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "Size must be greater than zero");
      var2 = var3;
      if(var1 <= this.mMaxByteArraySize) {
         var2 = true;
      }

      Preconditions.checkArgument(var2, "Requested size is too big");
      this.mSemaphore.acquireUninterruptibly();

      try {
         CloseableReference var4 = CloseableReference.of(this.getByteArray(var1), this.mResourceReleaser);
         return var4;
      } catch (Throwable var5) {
         this.mSemaphore.release();
         throw Throwables.propagate(var5);
      }
   }

   @VisibleForTesting
   int getBucketedSize(int var1) {
      return Integer.highestOneBit(Math.max(var1, this.mMinByteArraySize) - 1) * 2;
   }

   public void trim(MemoryTrimType var1) {
      if(this.mSemaphore.tryAcquire()) {
         try {
            this.mByteArraySoftRef.clear();
         } finally {
            this.mSemaphore.release();
         }

      }
   }
}
