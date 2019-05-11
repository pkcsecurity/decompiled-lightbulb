package android.support.v4.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ContainerHelpers;
import java.util.ConcurrentModificationException;

public class SimpleArrayMap<K extends Object, V extends Object> {

   private static final int BASE_SIZE = 4;
   private static final int CACHE_SIZE = 10;
   private static final boolean CONCURRENT_MODIFICATION_EXCEPTIONS = true;
   private static final boolean DEBUG = false;
   private static final String TAG = "ArrayMap";
   @Nullable
   static Object[] mBaseCache;
   static int mBaseCacheSize;
   @Nullable
   static Object[] mTwiceBaseCache;
   static int mTwiceBaseCacheSize;
   Object[] mArray;
   int[] mHashes;
   int mSize;


   public SimpleArrayMap() {
      this.mHashes = ContainerHelpers.EMPTY_INTS;
      this.mArray = ContainerHelpers.EMPTY_OBJECTS;
      this.mSize = 0;
   }

   public SimpleArrayMap(int var1) {
      if(var1 == 0) {
         this.mHashes = ContainerHelpers.EMPTY_INTS;
         this.mArray = ContainerHelpers.EMPTY_OBJECTS;
      } else {
         this.allocArrays(var1);
      }

      this.mSize = 0;
   }

   public SimpleArrayMap(SimpleArrayMap<K, V> var1) {
      this();
      if(var1 != null) {
         this.putAll(var1);
      }

   }

   private void allocArrays(int param1) {
      // $FF: Couldn't be decompiled
   }

   private static int binarySearchHashes(int[] var0, int var1, int var2) {
      try {
         var1 = ContainerHelpers.binarySearch(var0, var1, var2);
         return var1;
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new ConcurrentModificationException();
      }
   }

   private static void freeArrays(int[] param0, Object[] param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public void clear() {
      if(this.mSize > 0) {
         int[] var2 = this.mHashes;
         Object[] var3 = this.mArray;
         int var1 = this.mSize;
         this.mHashes = ContainerHelpers.EMPTY_INTS;
         this.mArray = ContainerHelpers.EMPTY_OBJECTS;
         this.mSize = 0;
         freeArrays(var2, var3, var1);
      }

      if(this.mSize > 0) {
         throw new ConcurrentModificationException();
      }
   }

   public boolean containsKey(@Nullable Object var1) {
      return this.indexOfKey(var1) >= 0;
   }

   public boolean containsValue(Object var1) {
      return this.indexOfValue(var1) >= 0;
   }

   public void ensureCapacity(int var1) {
      int var2 = this.mSize;
      if(this.mHashes.length < var1) {
         int[] var3 = this.mHashes;
         Object[] var4 = this.mArray;
         this.allocArrays(var1);
         if(this.mSize > 0) {
            System.arraycopy(var3, 0, this.mHashes, 0, var2);
            System.arraycopy(var4, 0, this.mArray, 0, var2 << 1);
         }

         freeArrays(var3, var4, var2);
      }

      if(this.mSize != var2) {
         throw new ConcurrentModificationException();
      }
   }

   public boolean equals(Object param1) {
      // $FF: Couldn't be decompiled
   }

   @Nullable
   public V get(Object var1) {
      int var2 = this.indexOfKey(var1);
      return var2 >= 0?this.mArray[(var2 << 1) + 1]:null;
   }

   public int hashCode() {
      int[] var7 = this.mHashes;
      Object[] var8 = this.mArray;
      int var5 = this.mSize;
      int var2 = 0;
      int var1 = 1;

      int var3;
      for(var3 = 0; var2 < var5; var1 += 2) {
         Object var9 = var8[var1];
         int var6 = var7[var2];
         int var4;
         if(var9 == null) {
            var4 = 0;
         } else {
            var4 = var9.hashCode();
         }

         var3 += var4 ^ var6;
         ++var2;
      }

      return var3;
   }

   int indexOf(Object var1, int var2) {
      int var4 = this.mSize;
      if(var4 == 0) {
         return -1;
      } else {
         int var5 = binarySearchHashes(this.mHashes, var4, var2);
         if(var5 < 0) {
            return var5;
         } else if(var1.equals(this.mArray[var5 << 1])) {
            return var5;
         } else {
            int var3;
            for(var3 = var5 + 1; var3 < var4 && this.mHashes[var3] == var2; ++var3) {
               if(var1.equals(this.mArray[var3 << 1])) {
                  return var3;
               }
            }

            for(var4 = var5 - 1; var4 >= 0 && this.mHashes[var4] == var2; --var4) {
               if(var1.equals(this.mArray[var4 << 1])) {
                  return var4;
               }
            }

            return ~var3;
         }
      }
   }

   public int indexOfKey(@Nullable Object var1) {
      return var1 == null?this.indexOfNull():this.indexOf(var1, var1.hashCode());
   }

   int indexOfNull() {
      int var2 = this.mSize;
      if(var2 == 0) {
         return -1;
      } else {
         int var3 = binarySearchHashes(this.mHashes, var2, 0);
         if(var3 < 0) {
            return var3;
         } else if(this.mArray[var3 << 1] == null) {
            return var3;
         } else {
            int var1;
            for(var1 = var3 + 1; var1 < var2 && this.mHashes[var1] == 0; ++var1) {
               if(this.mArray[var1 << 1] == null) {
                  return var1;
               }
            }

            for(var2 = var3 - 1; var2 >= 0 && this.mHashes[var2] == 0; --var2) {
               if(this.mArray[var2 << 1] == null) {
                  return var2;
               }
            }

            return ~var1;
         }
      }
   }

   int indexOfValue(Object var1) {
      int var3 = this.mSize * 2;
      Object[] var4 = this.mArray;
      int var2;
      if(var1 == null) {
         for(var2 = 1; var2 < var3; var2 += 2) {
            if(var4[var2] == null) {
               return var2 >> 1;
            }
         }
      } else {
         for(var2 = 1; var2 < var3; var2 += 2) {
            if(var1.equals(var4[var2])) {
               return var2 >> 1;
            }
         }
      }

      return -1;
   }

   public boolean isEmpty() {
      return this.mSize <= 0;
   }

   public K keyAt(int var1) {
      return this.mArray[var1 << 1];
   }

   @Nullable
   public V put(K var1, V var2) {
      int var5 = this.mSize;
      int var3;
      int var4;
      if(var1 == null) {
         var3 = this.indexOfNull();
         var4 = 0;
      } else {
         var4 = var1.hashCode();
         var3 = this.indexOf(var1, var4);
      }

      if(var3 >= 0) {
         var3 = (var3 << 1) + 1;
         var1 = this.mArray[var3];
         this.mArray[var3] = var2;
         return var1;
      } else {
         int var6 = ~var3;
         int[] var7;
         if(var5 >= this.mHashes.length) {
            var3 = 4;
            if(var5 >= 8) {
               var3 = (var5 >> 1) + var5;
            } else if(var5 >= 4) {
               var3 = 8;
            }

            var7 = this.mHashes;
            Object[] var8 = this.mArray;
            this.allocArrays(var3);
            if(var5 != this.mSize) {
               throw new ConcurrentModificationException();
            }

            if(this.mHashes.length > 0) {
               System.arraycopy(var7, 0, this.mHashes, 0, var7.length);
               System.arraycopy(var8, 0, this.mArray, 0, var8.length);
            }

            freeArrays(var7, var8, var5);
         }

         if(var6 < var5) {
            var7 = this.mHashes;
            int[] var10 = this.mHashes;
            var3 = var6 + 1;
            System.arraycopy(var7, var6, var10, var3, var5 - var6);
            System.arraycopy(this.mArray, var6 << 1, this.mArray, var3 << 1, this.mSize - var6 << 1);
         }

         if(var5 == this.mSize && var6 < this.mHashes.length) {
            this.mHashes[var6] = var4;
            Object[] var9 = this.mArray;
            var3 = var6 << 1;
            var9[var3] = var1;
            this.mArray[var3 + 1] = var2;
            ++this.mSize;
            return null;
         } else {
            throw new ConcurrentModificationException();
         }
      }
   }

   public void putAll(@NonNull SimpleArrayMap<? extends K, ? extends V> var1) {
      int var3 = var1.mSize;
      this.ensureCapacity(this.mSize + var3);
      int var4 = this.mSize;
      int var2 = 0;
      if(var4 == 0) {
         if(var3 > 0) {
            System.arraycopy(var1.mHashes, 0, this.mHashes, 0, var3);
            System.arraycopy(var1.mArray, 0, this.mArray, 0, var3 << 1);
            this.mSize = var3;
            return;
         }
      } else {
         while(var2 < var3) {
            this.put(var1.keyAt(var2), var1.valueAt(var2));
            ++var2;
         }
      }

   }

   @Nullable
   public V remove(Object var1) {
      int var2 = this.indexOfKey(var1);
      return var2 >= 0?this.removeAt(var2):null;
   }

   public V removeAt(int var1) {
      Object[] var7 = this.mArray;
      int var5 = var1 << 1;
      Object var12 = var7[var5 + 1];
      int var4 = this.mSize;
      byte var2 = 0;
      if(var4 <= 1) {
         freeArrays(this.mHashes, this.mArray, var4);
         this.mHashes = ContainerHelpers.EMPTY_INTS;
         this.mArray = ContainerHelpers.EMPTY_OBJECTS;
         var1 = var2;
      } else {
         int var3 = var4 - 1;
         int var6 = this.mHashes.length;
         int var11 = 8;
         int[] var8;
         if(var6 > 8 && this.mSize < this.mHashes.length / 3) {
            if(var4 > 8) {
               var11 = var4 + (var4 >> 1);
            }

            var8 = this.mHashes;
            Object[] var14 = this.mArray;
            this.allocArrays(var11);
            if(var4 != this.mSize) {
               throw new ConcurrentModificationException();
            }

            if(var1 > 0) {
               System.arraycopy(var8, 0, this.mHashes, 0, var1);
               System.arraycopy(var14, 0, this.mArray, 0, var5);
            }

            if(var1 < var3) {
               var11 = var1 + 1;
               int[] var10 = this.mHashes;
               var6 = var3 - var1;
               System.arraycopy(var8, var11, var10, var1, var6);
               System.arraycopy(var14, var11 << 1, this.mArray, var5, var6 << 1);
            }
         } else {
            if(var1 < var3) {
               var8 = this.mHashes;
               var11 = var1 + 1;
               int[] var9 = this.mHashes;
               var6 = var3 - var1;
               System.arraycopy(var8, var11, var9, var1, var6);
               System.arraycopy(this.mArray, var11 << 1, this.mArray, var5, var6 << 1);
            }

            Object[] var13 = this.mArray;
            var1 = var3 << 1;
            var13[var1] = null;
            this.mArray[var1 + 1] = null;
         }

         var1 = var3;
      }

      if(var4 != this.mSize) {
         throw new ConcurrentModificationException();
      } else {
         this.mSize = var1;
         return var12;
      }
   }

   public V setValueAt(int var1, V var2) {
      var1 = (var1 << 1) + 1;
      Object var3 = this.mArray[var1];
      this.mArray[var1] = var2;
      return var3;
   }

   public int size() {
      return this.mSize;
   }

   public String toString() {
      if(this.isEmpty()) {
         return "{}";
      } else {
         StringBuilder var2 = new StringBuilder(this.mSize * 28);
         var2.append('{');

         for(int var1 = 0; var1 < this.mSize; ++var1) {
            if(var1 > 0) {
               var2.append(", ");
            }

            Object var3 = this.keyAt(var1);
            if(var3 != this) {
               var2.append(var3);
            } else {
               var2.append("(this Map)");
            }

            var2.append('=');
            var3 = this.valueAt(var1);
            if(var3 != this) {
               var2.append(var3);
            } else {
               var2.append("(this Map)");
            }
         }

         var2.append('}');
         return var2.toString();
      }
   }

   public V valueAt(int var1) {
      return this.mArray[(var1 << 1) + 1];
   }
}
