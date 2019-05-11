package com.facebook.imagepipeline.memory;

import android.annotation.SuppressLint;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Sets;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.logging.FLog;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.common.memory.Pool;
import com.facebook.imagepipeline.memory.Bucket;
import com.facebook.imagepipeline.memory.PoolParams;
import com.facebook.imagepipeline.memory.PoolStatsTracker;
import java.util.Map;
import java.util.Set;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.NotThreadSafe;

public abstract class BasePool<V extends Object> implements Pool<V> {

   private final Class<?> TAG = this.getClass();
   private boolean mAllowNewBuckets;
   @VisibleForTesting
   final SparseArray<Bucket<V>> mBuckets;
   @VisibleForTesting
   @GuardedBy
   final BasePool.Counter mFree;
   @VisibleForTesting
   final Set<V> mInUseValues;
   final MemoryTrimmableRegistry mMemoryTrimmableRegistry;
   final PoolParams mPoolParams;
   private final PoolStatsTracker mPoolStatsTracker;
   @VisibleForTesting
   @GuardedBy
   final BasePool.Counter mUsed;


   public BasePool(MemoryTrimmableRegistry var1, PoolParams var2, PoolStatsTracker var3) {
      this.mMemoryTrimmableRegistry = (MemoryTrimmableRegistry)Preconditions.checkNotNull(var1);
      this.mPoolParams = (PoolParams)Preconditions.checkNotNull(var2);
      this.mPoolStatsTracker = (PoolStatsTracker)Preconditions.checkNotNull(var3);
      this.mBuckets = new SparseArray();
      this.initBuckets(new SparseIntArray(0));
      this.mInUseValues = Sets.newIdentityHashSet();
      this.mFree = new BasePool.Counter();
      this.mUsed = new BasePool.Counter();
   }

   private void ensurePoolSizeInvariant() {
      // $FF: Couldn't be decompiled
   }

   private void initBuckets(SparseIntArray param1) {
      // $FF: Couldn't be decompiled
   }

   @SuppressLint({"InvalidAccessToGuardedField"})
   private void logStats() {
      if(FLog.isLoggable(2)) {
         FLog.v(this.TAG, "Used = (%d, %d); Free = (%d, %d)", Integer.valueOf(this.mUsed.mCount), Integer.valueOf(this.mUsed.mNumBytes), Integer.valueOf(this.mFree.mCount), Integer.valueOf(this.mFree.mNumBytes));
      }

   }

   protected abstract V alloc(int var1);

   @VisibleForTesting
   boolean canAllocate(int var1) {
      synchronized(this){}

      try {
         int var2 = this.mPoolParams.maxSizeHardCap;
         if(var1 > var2 - this.mUsed.mNumBytes) {
            this.mPoolStatsTracker.onHardCapReached();
            return false;
         }

         int var3 = this.mPoolParams.maxSizeSoftCap;
         if(var1 > var3 - (this.mUsed.mNumBytes + this.mFree.mNumBytes)) {
            this.trimToSize(var3 - var1);
         }

         if(var1 <= var2 - (this.mUsed.mNumBytes + this.mFree.mNumBytes)) {
            return true;
         }

         this.mPoolStatsTracker.onHardCapReached();
      } finally {
         ;
      }

      return false;
   }

   @VisibleForTesting
   protected abstract void free(V var1);

   public V get(int param1) {
      // $FF: Couldn't be decompiled
   }

   @VisibleForTesting
   Bucket<V> getBucket(int param1) {
      // $FF: Couldn't be decompiled
   }

   protected abstract int getBucketedSize(int var1);

   protected abstract int getBucketedSizeForValue(V var1);

   protected abstract int getSizeInBytes(int var1);

   public Map<String, Integer> getStats() {
      // $FF: Couldn't be decompiled
   }

   protected void initialize() {
      this.mMemoryTrimmableRegistry.registerMemoryTrimmable(this);
      this.mPoolStatsTracker.setBasePool(this);
   }

   @VisibleForTesting
   boolean isMaxSizeSoftCapExceeded() {
      // $FF: Couldn't be decompiled
   }

   protected boolean isReusable(V var1) {
      Preconditions.checkNotNull(var1);
      return true;
   }

   Bucket<V> newBucket(int var1) {
      return new Bucket(this.getSizeInBytes(var1), Integer.MAX_VALUE, 0);
   }

   protected void onParamsChanged() {}

   public void release(V param1) {
      // $FF: Couldn't be decompiled
   }

   public void trim(MemoryTrimType var1) {
      this.trimToNothing();
   }

   @VisibleForTesting
   void trimToNothing() {
      // $FF: Couldn't be decompiled
   }

   @VisibleForTesting
   void trimToSize(int param1) {
      // $FF: Couldn't be decompiled
   }

   @VisibleForTesting
   void trimToSoftCap() {
      synchronized(this){}

      try {
         if(this.isMaxSizeSoftCapExceeded()) {
            this.trimToSize(this.mPoolParams.maxSizeSoftCap);
         }
      } finally {
         ;
      }

   }

   @VisibleForTesting
   @NotThreadSafe
   static class Counter {

      private static final String TAG = "com.facebook.imagepipeline.memory.BasePool.Counter";
      int mCount;
      int mNumBytes;


      public void decrement(int var1) {
         if(this.mNumBytes >= var1 && this.mCount > 0) {
            --this.mCount;
            this.mNumBytes -= var1;
         } else {
            FLog.wtf("com.facebook.imagepipeline.memory.BasePool.Counter", "Unexpected decrement of %d. Current numBytes = %d, count = %d", new Object[]{Integer.valueOf(var1), Integer.valueOf(this.mNumBytes), Integer.valueOf(this.mCount)});
         }
      }

      public void increment(int var1) {
         ++this.mCount;
         this.mNumBytes += var1;
      }

      public void reset() {
         this.mCount = 0;
         this.mNumBytes = 0;
      }
   }

   public static class SizeTooLargeException extends BasePool.InvalidSizeException {

      public SizeTooLargeException(Object var1) {
         super(var1);
      }
   }

   public static class InvalidSizeException extends RuntimeException {

      public InvalidSizeException(Object var1) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Invalid size: ");
         var2.append(var1.toString());
         super(var2.toString());
      }
   }

   public static class InvalidValueException extends RuntimeException {

      public InvalidValueException(Object var1) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Invalid value: ");
         var2.append(var1.toString());
         super(var2.toString());
      }
   }

   public static class PoolSizeViolationException extends RuntimeException {

      public PoolSizeViolationException(int var1, int var2, int var3, int var4) {
         StringBuilder var5 = new StringBuilder();
         var5.append("Pool hard cap violation? Hard cap = ");
         var5.append(var1);
         var5.append(" Used size = ");
         var5.append(var2);
         var5.append(" Free size = ");
         var5.append(var3);
         var5.append(" Request size = ");
         var5.append(var4);
         super(var5.toString());
      }
   }
}
