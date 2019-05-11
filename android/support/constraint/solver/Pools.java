package android.support.constraint.solver;


final class Pools {

   private static final boolean DEBUG = false;



   interface Pool<T extends Object> {

      T acquire();

      boolean release(T var1);

      void releaseAll(T[] var1, int var2);
   }

   static class SimplePool<T extends Object> implements Pools.Pool<T> {

      private final Object[] mPool;
      private int mPoolSize;


      SimplePool(int var1) {
         if(var1 <= 0) {
            throw new IllegalArgumentException("The max pool size must be > 0");
         } else {
            this.mPool = new Object[var1];
         }
      }

      private boolean isInPool(T var1) {
         for(int var2 = 0; var2 < this.mPoolSize; ++var2) {
            if(this.mPool[var2] == var1) {
               return true;
            }
         }

         return false;
      }

      public T acquire() {
         if(this.mPoolSize > 0) {
            int var1 = this.mPoolSize - 1;
            Object var2 = this.mPool[var1];
            this.mPool[var1] = null;
            --this.mPoolSize;
            return var2;
         } else {
            return null;
         }
      }

      public boolean release(T var1) {
         if(this.mPoolSize < this.mPool.length) {
            this.mPool[this.mPoolSize] = var1;
            ++this.mPoolSize;
            return true;
         } else {
            return false;
         }
      }

      public void releaseAll(T[] var1, int var2) {
         int var3 = var2;
         if(var2 > var1.length) {
            var3 = var1.length;
         }

         for(var2 = 0; var2 < var3; ++var2) {
            Object var4 = var1[var2];
            if(this.mPoolSize < this.mPool.length) {
               this.mPool[this.mPoolSize] = var4;
               ++this.mPoolSize;
            }
         }

      }
   }
}
