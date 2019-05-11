package com.facebook.litho;

import android.support.v4.util.Pools;
import com.facebook.infer.annotation.ThreadSafe;
import com.facebook.litho.PoolWithDebugInfo;

@ThreadSafe(
   enableChecks = false
)
public class RecyclePool<T extends Object> implements PoolWithDebugInfo {

   private int mCurrentSize = 0;
   private final boolean mIsSync;
   private final int mMaxSize;
   private final String mName;
   private final Pools.Pool<T> mPool;


   public RecyclePool(String var1, int var2, boolean var3) {
      this.mIsSync = var3;
      this.mName = var1;
      this.mMaxSize = var2;
      Object var4;
      if(var3) {
         var4 = new Pools.SynchronizedPool(var2);
      } else {
         var4 = new Pools.SimplePool(var2);
      }

      this.mPool = (Pools.Pool)var4;
   }

   public T acquire() {
      // $FF: Couldn't be decompiled
   }

   public void clear() {
      // $FF: Couldn't be decompiled
   }

   public int getCurrentSize() {
      return this.mCurrentSize;
   }

   public int getMaxSize() {
      return this.mMaxSize;
   }

   public String getName() {
      return this.mName;
   }

   public boolean isFull() {
      return this.mCurrentSize >= this.mMaxSize;
   }

   public void release(T param1) {
      // $FF: Couldn't be decompiled
   }
}
