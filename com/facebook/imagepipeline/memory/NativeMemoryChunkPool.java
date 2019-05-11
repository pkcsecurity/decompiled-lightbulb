package com.facebook.imagepipeline.memory;

import android.util.SparseIntArray;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.imagepipeline.memory.BasePool;
import com.facebook.imagepipeline.memory.NativeMemoryChunk;
import com.facebook.imagepipeline.memory.PoolParams;
import com.facebook.imagepipeline.memory.PoolStatsTracker;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class NativeMemoryChunkPool extends BasePool<NativeMemoryChunk> {

   private final int[] mBucketSizes;


   public NativeMemoryChunkPool(MemoryTrimmableRegistry var1, PoolParams var2, PoolStatsTracker var3) {
      super(var1, var2, var3);
      SparseIntArray var5 = var2.bucketSizes;
      this.mBucketSizes = new int[var5.size()];

      for(int var4 = 0; var4 < this.mBucketSizes.length; ++var4) {
         this.mBucketSizes[var4] = var5.keyAt(var4);
      }

      this.initialize();
   }

   protected NativeMemoryChunk alloc(int var1) {
      return new NativeMemoryChunk(var1);
   }

   protected void free(NativeMemoryChunk var1) {
      Preconditions.checkNotNull(var1);
      var1.close();
   }

   protected int getBucketedSize(int var1) {
      if(var1 <= 0) {
         throw new BasePool.InvalidSizeException(Integer.valueOf(var1));
      } else {
         int[] var5 = this.mBucketSizes;
         int var3 = var5.length;

         for(int var2 = 0; var2 < var3; ++var2) {
            int var4 = var5[var2];
            if(var4 >= var1) {
               return var4;
            }
         }

         return var1;
      }
   }

   protected int getBucketedSizeForValue(NativeMemoryChunk var1) {
      Preconditions.checkNotNull(var1);
      return var1.getSize();
   }

   public int getMinBufferSize() {
      return this.mBucketSizes[0];
   }

   protected int getSizeInBytes(int var1) {
      return var1;
   }

   protected boolean isReusable(NativeMemoryChunk var1) {
      Preconditions.checkNotNull(var1);
      return var1.isClosed() ^ true;
   }
}
