package android.support.v4.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ContainerHelpers;

public class LongSparseArray<E extends Object> implements Cloneable {

   private static final Object DELETED = new Object();
   private boolean mGarbage;
   private long[] mKeys;
   private int mSize;
   private Object[] mValues;


   public LongSparseArray() {
      this(10);
   }

   public LongSparseArray(int var1) {
      this.mGarbage = false;
      if(var1 == 0) {
         this.mKeys = ContainerHelpers.EMPTY_LONGS;
         this.mValues = ContainerHelpers.EMPTY_OBJECTS;
      } else {
         var1 = ContainerHelpers.idealLongArraySize(var1);
         this.mKeys = new long[var1];
         this.mValues = new Object[var1];
      }

      this.mSize = 0;
   }

   private void gc() {
      int var4 = this.mSize;
      long[] var5 = this.mKeys;
      Object[] var6 = this.mValues;
      int var1 = 0;

      int var2;
      int var3;
      for(var2 = 0; var1 < var4; var2 = var3) {
         Object var7 = var6[var1];
         var3 = var2;
         if(var7 != DELETED) {
            if(var1 != var2) {
               var5[var2] = var5[var1];
               var6[var2] = var7;
               var6[var1] = null;
            }

            var3 = var2 + 1;
         }

         ++var1;
      }

      this.mGarbage = false;
      this.mSize = var2;
   }

   public void append(long var1, E var3) {
      if(this.mSize != 0 && var1 <= this.mKeys[this.mSize - 1]) {
         this.put(var1, var3);
      } else {
         if(this.mGarbage && this.mSize >= this.mKeys.length) {
            this.gc();
         }

         int var4 = this.mSize;
         if(var4 >= this.mKeys.length) {
            int var5 = ContainerHelpers.idealLongArraySize(var4 + 1);
            long[] var6 = new long[var5];
            Object[] var7 = new Object[var5];
            System.arraycopy(this.mKeys, 0, var6, 0, this.mKeys.length);
            System.arraycopy(this.mValues, 0, var7, 0, this.mValues.length);
            this.mKeys = var6;
            this.mValues = var7;
         }

         this.mKeys[var4] = var1;
         this.mValues[var4] = var3;
         this.mSize = var4 + 1;
      }
   }

   public void clear() {
      int var2 = this.mSize;
      Object[] var3 = this.mValues;

      for(int var1 = 0; var1 < var2; ++var1) {
         var3[var1] = null;
      }

      this.mSize = 0;
      this.mGarbage = false;
   }

   public LongSparseArray<E> clone() {
      try {
         LongSparseArray var1 = (LongSparseArray)super.clone();
         var1.mKeys = (long[])this.mKeys.clone();
         var1.mValues = (Object[])this.mValues.clone();
         return var1;
      } catch (CloneNotSupportedException var2) {
         throw new AssertionError(var2);
      }
   }

   public boolean containsKey(long var1) {
      return this.indexOfKey(var1) >= 0;
   }

   public boolean containsValue(E var1) {
      return this.indexOfValue(var1) >= 0;
   }

   public void delete(long var1) {
      int var3 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, var1);
      if(var3 >= 0 && this.mValues[var3] != DELETED) {
         this.mValues[var3] = DELETED;
         this.mGarbage = true;
      }

   }

   @Nullable
   public E get(long var1) {
      return this.get(var1, (Object)null);
   }

   public E get(long var1, E var3) {
      int var4 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, var1);
      return var4 >= 0?(this.mValues[var4] == DELETED?var3:this.mValues[var4]):var3;
   }

   public int indexOfKey(long var1) {
      if(this.mGarbage) {
         this.gc();
      }

      return ContainerHelpers.binarySearch(this.mKeys, this.mSize, var1);
   }

   public int indexOfValue(E var1) {
      if(this.mGarbage) {
         this.gc();
      }

      for(int var2 = 0; var2 < this.mSize; ++var2) {
         if(this.mValues[var2] == var1) {
            return var2;
         }
      }

      return -1;
   }

   public boolean isEmpty() {
      return this.size() == 0;
   }

   public long keyAt(int var1) {
      if(this.mGarbage) {
         this.gc();
      }

      return this.mKeys[var1];
   }

   public void put(long var1, E var3) {
      int var4 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, var1);
      if(var4 >= 0) {
         this.mValues[var4] = var3;
      } else {
         int var5 = ~var4;
         if(var5 < this.mSize && this.mValues[var5] == DELETED) {
            this.mKeys[var5] = var1;
            this.mValues[var5] = var3;
         } else {
            var4 = var5;
            if(this.mGarbage) {
               var4 = var5;
               if(this.mSize >= this.mKeys.length) {
                  this.gc();
                  var4 = ~ContainerHelpers.binarySearch(this.mKeys, this.mSize, var1);
               }
            }

            long[] var6;
            if(this.mSize >= this.mKeys.length) {
               var5 = ContainerHelpers.idealLongArraySize(this.mSize + 1);
               var6 = new long[var5];
               Object[] var7 = new Object[var5];
               System.arraycopy(this.mKeys, 0, var6, 0, this.mKeys.length);
               System.arraycopy(this.mValues, 0, var7, 0, this.mValues.length);
               this.mKeys = var6;
               this.mValues = var7;
            }

            if(this.mSize - var4 != 0) {
               var6 = this.mKeys;
               long[] var8 = this.mKeys;
               var5 = var4 + 1;
               System.arraycopy(var6, var4, var8, var5, this.mSize - var4);
               System.arraycopy(this.mValues, var4, this.mValues, var5, this.mSize - var4);
            }

            this.mKeys[var4] = var1;
            this.mValues[var4] = var3;
            ++this.mSize;
         }
      }
   }

   public void putAll(@NonNull LongSparseArray<? extends E> var1) {
      int var3 = var1.size();

      for(int var2 = 0; var2 < var3; ++var2) {
         this.put(var1.keyAt(var2), var1.valueAt(var2));
      }

   }

   public void remove(long var1) {
      this.delete(var1);
   }

   public void removeAt(int var1) {
      if(this.mValues[var1] != DELETED) {
         this.mValues[var1] = DELETED;
         this.mGarbage = true;
      }

   }

   public void setValueAt(int var1, E var2) {
      if(this.mGarbage) {
         this.gc();
      }

      this.mValues[var1] = var2;
   }

   public int size() {
      if(this.mGarbage) {
         this.gc();
      }

      return this.mSize;
   }

   public String toString() {
      if(this.size() <= 0) {
         return "{}";
      } else {
         StringBuilder var2 = new StringBuilder(this.mSize * 28);
         var2.append('{');

         for(int var1 = 0; var1 < this.mSize; ++var1) {
            if(var1 > 0) {
               var2.append(", ");
            }

            var2.append(this.keyAt(var1));
            var2.append('=');
            Object var3 = this.valueAt(var1);
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

   public E valueAt(int var1) {
      if(this.mGarbage) {
         this.gc();
      }

      return this.mValues[var1];
   }
}
