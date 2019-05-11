package com.facebook.react.common;

import android.support.v4.util.Pools;

public class ClearableSynchronizedPool<T extends Object> implements Pools.Pool<T> {

   private final Object[] mPool;
   private int mSize = 0;


   public ClearableSynchronizedPool(int var1) {
      this.mPool = new Object[var1];
   }

   public T acquire() {
      // $FF: Couldn't be decompiled
   }

   public void clear() {
      synchronized(this){}
      int var1 = 0;

      while(true) {
         boolean var4 = false;

         try {
            var4 = true;
            if(var1 >= this.mSize) {
               this.mSize = 0;
               var4 = false;
               return;
            }

            this.mPool[var1] = null;
            var4 = false;
         } finally {
            if(var4) {
               ;
            }
         }

         ++var1;
      }
   }

   public boolean release(T param1) {
      // $FF: Couldn't be decompiled
   }
}
