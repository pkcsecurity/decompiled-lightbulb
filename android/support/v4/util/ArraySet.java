package android.support.v4.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.util.ContainerHelpers;
import android.support.v4.util.MapCollections;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class ArraySet<E extends Object> implements Collection<E>, Set<E> {

   private static final int BASE_SIZE = 4;
   private static final int CACHE_SIZE = 10;
   private static final boolean DEBUG = false;
   private static final int[] INT = new int[0];
   private static final Object[] OBJECT = new Object[0];
   private static final String TAG = "ArraySet";
   @Nullable
   private static Object[] sBaseCache;
   private static int sBaseCacheSize;
   @Nullable
   private static Object[] sTwiceBaseCache;
   private static int sTwiceBaseCacheSize;
   Object[] mArray;
   private MapCollections<E, E> mCollections;
   private int[] mHashes;
   int mSize;


   public ArraySet() {
      this(0);
   }

   public ArraySet(int var1) {
      if(var1 == 0) {
         this.mHashes = INT;
         this.mArray = OBJECT;
      } else {
         this.allocArrays(var1);
      }

      this.mSize = 0;
   }

   public ArraySet(@Nullable ArraySet<E> var1) {
      this();
      if(var1 != null) {
         this.addAll(var1);
      }

   }

   public ArraySet(@Nullable Collection<E> var1) {
      this();
      if(var1 != null) {
         this.addAll(var1);
      }

   }

   private void allocArrays(int param1) {
      // $FF: Couldn't be decompiled
   }

   private static void freeArrays(int[] param0, Object[] param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   private MapCollections<E, E> getCollection() {
      if(this.mCollections == null) {
         this.mCollections = new MapCollections() {
            protected void colClear() {
               ArraySet.this.clear();
            }
            protected Object colGetEntry(int var1, int var2) {
               return ArraySet.this.mArray[var1];
            }
            protected Map<E, E> colGetMap() {
               throw new UnsupportedOperationException("not a map");
            }
            protected int colGetSize() {
               return ArraySet.this.mSize;
            }
            protected int colIndexOfKey(Object var1) {
               return ArraySet.this.indexOf(var1);
            }
            protected int colIndexOfValue(Object var1) {
               return ArraySet.this.indexOf(var1);
            }
            protected void colPut(E var1, E var2) {
               ArraySet.this.add(var1);
            }
            protected void colRemoveAt(int var1) {
               ArraySet.this.removeAt(var1);
            }
            protected E colSetValue(int var1, E var2) {
               throw new UnsupportedOperationException("not a map");
            }
         };
      }

      return this.mCollections;
   }

   private int indexOf(Object var1, int var2) {
      int var4 = this.mSize;
      if(var4 == 0) {
         return -1;
      } else {
         int var5 = ContainerHelpers.binarySearch(this.mHashes, var4, var2);
         if(var5 < 0) {
            return var5;
         } else if(var1.equals(this.mArray[var5])) {
            return var5;
         } else {
            int var3;
            for(var3 = var5 + 1; var3 < var4 && this.mHashes[var3] == var2; ++var3) {
               if(var1.equals(this.mArray[var3])) {
                  return var3;
               }
            }

            for(var4 = var5 - 1; var4 >= 0 && this.mHashes[var4] == var2; --var4) {
               if(var1.equals(this.mArray[var4])) {
                  return var4;
               }
            }

            return ~var3;
         }
      }
   }

   private int indexOfNull() {
      int var2 = this.mSize;
      if(var2 == 0) {
         return -1;
      } else {
         int var3 = ContainerHelpers.binarySearch(this.mHashes, var2, 0);
         if(var3 < 0) {
            return var3;
         } else if(this.mArray[var3] == null) {
            return var3;
         } else {
            int var1;
            for(var1 = var3 + 1; var1 < var2 && this.mHashes[var1] == 0; ++var1) {
               if(this.mArray[var1] == null) {
                  return var1;
               }
            }

            for(var2 = var3 - 1; var2 >= 0 && this.mHashes[var2] == 0; --var2) {
               if(this.mArray[var2] == null) {
                  return var2;
               }
            }

            return ~var1;
         }
      }
   }

   public boolean add(@Nullable E var1) {
      int var2;
      int var3;
      if(var1 == null) {
         var2 = this.indexOfNull();
         var3 = 0;
      } else {
         var3 = var1.hashCode();
         var2 = this.indexOf(var1, var3);
      }

      if(var2 >= 0) {
         return false;
      } else {
         int var4 = ~var2;
         int[] var6;
         if(this.mSize >= this.mHashes.length) {
            int var5 = this.mSize;
            var2 = 4;
            if(var5 >= 8) {
               var2 = this.mSize;
               var2 += this.mSize >> 1;
            } else if(this.mSize >= 4) {
               var2 = 8;
            }

            var6 = this.mHashes;
            Object[] var7 = this.mArray;
            this.allocArrays(var2);
            if(this.mHashes.length > 0) {
               System.arraycopy(var6, 0, this.mHashes, 0, var6.length);
               System.arraycopy(var7, 0, this.mArray, 0, var7.length);
            }

            freeArrays(var6, var7, this.mSize);
         }

         if(var4 < this.mSize) {
            var6 = this.mHashes;
            int[] var8 = this.mHashes;
            var2 = var4 + 1;
            System.arraycopy(var6, var4, var8, var2, this.mSize - var4);
            System.arraycopy(this.mArray, var4, this.mArray, var2, this.mSize - var4);
         }

         this.mHashes[var4] = var3;
         this.mArray[var4] = var1;
         ++this.mSize;
         return true;
      }
   }

   public void addAll(@NonNull ArraySet<? extends E> var1) {
      int var3 = var1.mSize;
      this.ensureCapacity(this.mSize + var3);
      int var4 = this.mSize;
      int var2 = 0;
      if(var4 == 0) {
         if(var3 > 0) {
            System.arraycopy(var1.mHashes, 0, this.mHashes, 0, var3);
            System.arraycopy(var1.mArray, 0, this.mArray, 0, var3);
            this.mSize = var3;
            return;
         }
      } else {
         while(var2 < var3) {
            this.add(var1.valueAt(var2));
            ++var2;
         }
      }

   }

   public boolean addAll(@NonNull Collection<? extends E> var1) {
      this.ensureCapacity(this.mSize + var1.size());
      Iterator var3 = var1.iterator();

      boolean var2;
      for(var2 = false; var3.hasNext(); var2 |= this.add(var3.next())) {
         ;
      }

      return var2;
   }

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   public void append(E var1) {
      int var3 = this.mSize;
      int var2;
      if(var1 == null) {
         var2 = 0;
      } else {
         var2 = var1.hashCode();
      }

      if(var3 >= this.mHashes.length) {
         throw new IllegalStateException("Array is full");
      } else if(var3 > 0 && this.mHashes[var3 - 1] > var2) {
         this.add(var1);
      } else {
         this.mSize = var3 + 1;
         this.mHashes[var3] = var2;
         this.mArray[var3] = var1;
      }
   }

   public void clear() {
      if(this.mSize != 0) {
         freeArrays(this.mHashes, this.mArray, this.mSize);
         this.mHashes = INT;
         this.mArray = OBJECT;
         this.mSize = 0;
      }

   }

   public boolean contains(@Nullable Object var1) {
      return this.indexOf(var1) >= 0;
   }

   public boolean containsAll(@NonNull Collection<?> var1) {
      Iterator var2 = var1.iterator();

      do {
         if(!var2.hasNext()) {
            return true;
         }
      } while(this.contains(var2.next()));

      return false;
   }

   public void ensureCapacity(int var1) {
      if(this.mHashes.length < var1) {
         int[] var2 = this.mHashes;
         Object[] var3 = this.mArray;
         this.allocArrays(var1);
         if(this.mSize > 0) {
            System.arraycopy(var2, 0, this.mHashes, 0, this.mSize);
            System.arraycopy(var3, 0, this.mArray, 0, this.mSize);
         }

         freeArrays(var2, var3, this.mSize);
      }

   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof Set)) {
         return false;
      } else {
         Set var6 = (Set)var1;
         if(this.size() != var6.size()) {
            return false;
         } else {
            int var2 = 0;

            while(true) {
               boolean var3;
               try {
                  if(var2 >= this.mSize) {
                     return true;
                  }

                  var3 = var6.contains(this.valueAt(var2));
               } catch (NullPointerException var4) {
                  return false;
               } catch (ClassCastException var5) {
                  return false;
               }

               if(!var3) {
                  return false;
               }

               ++var2;
            }
         }
      }
   }

   public int hashCode() {
      int[] var4 = this.mHashes;
      int var3 = this.mSize;
      int var1 = 0;

      int var2;
      for(var2 = 0; var1 < var3; ++var1) {
         var2 += var4[var1];
      }

      return var2;
   }

   public int indexOf(@Nullable Object var1) {
      return var1 == null?this.indexOfNull():this.indexOf(var1, var1.hashCode());
   }

   public boolean isEmpty() {
      return this.mSize <= 0;
   }

   public Iterator<E> iterator() {
      return this.getCollection().getKeySet().iterator();
   }

   public boolean remove(@Nullable Object var1) {
      int var2 = this.indexOf(var1);
      if(var2 >= 0) {
         this.removeAt(var2);
         return true;
      } else {
         return false;
      }
   }

   public boolean removeAll(@NonNull ArraySet<? extends E> var1) {
      int var3 = var1.mSize;
      int var4 = this.mSize;
      boolean var5 = false;

      for(int var2 = 0; var2 < var3; ++var2) {
         this.remove(var1.valueAt(var2));
      }

      if(var4 != this.mSize) {
         var5 = true;
      }

      return var5;
   }

   public boolean removeAll(@NonNull Collection<?> var1) {
      Iterator var3 = var1.iterator();

      boolean var2;
      for(var2 = false; var3.hasNext(); var2 |= this.remove(var3.next())) {
         ;
      }

      return var2;
   }

   public E removeAt(int var1) {
      Object var4 = this.mArray[var1];
      if(this.mSize <= 1) {
         freeArrays(this.mHashes, this.mArray, this.mSize);
         this.mHashes = INT;
         this.mArray = OBJECT;
         this.mSize = 0;
         return var4;
      } else {
         int var3 = this.mHashes.length;
         int var2 = 8;
         int[] var5;
         if(var3 > 8 && this.mSize < this.mHashes.length / 3) {
            if(this.mSize > 8) {
               var2 = this.mSize;
               var2 += this.mSize >> 1;
            }

            var5 = this.mHashes;
            Object[] var6 = this.mArray;
            this.allocArrays(var2);
            --this.mSize;
            if(var1 > 0) {
               System.arraycopy(var5, 0, this.mHashes, 0, var1);
               System.arraycopy(var6, 0, this.mArray, 0, var1);
            }

            if(var1 < this.mSize) {
               var2 = var1 + 1;
               System.arraycopy(var5, var2, this.mHashes, var1, this.mSize - var1);
               System.arraycopy(var6, var2, this.mArray, var1, this.mSize - var1);
               return var4;
            }
         } else {
            --this.mSize;
            if(var1 < this.mSize) {
               var5 = this.mHashes;
               var2 = var1 + 1;
               System.arraycopy(var5, var2, this.mHashes, var1, this.mSize - var1);
               System.arraycopy(this.mArray, var2, this.mArray, var1, this.mSize - var1);
            }

            this.mArray[this.mSize] = null;
         }

         return var4;
      }
   }

   public boolean retainAll(@NonNull Collection<?> var1) {
      int var2 = this.mSize - 1;

      boolean var3;
      for(var3 = false; var2 >= 0; --var2) {
         if(!var1.contains(this.mArray[var2])) {
            this.removeAt(var2);
            var3 = true;
         }
      }

      return var3;
   }

   public int size() {
      return this.mSize;
   }

   @NonNull
   public Object[] toArray() {
      Object[] var1 = new Object[this.mSize];
      System.arraycopy(this.mArray, 0, var1, 0, this.mSize);
      return var1;
   }

   @NonNull
   public <T extends Object> T[] toArray(@NonNull T[] var1) {
      Object[] var2 = var1;
      if(var1.length < this.mSize) {
         var2 = (Object[])Array.newInstance(var1.getClass().getComponentType(), this.mSize);
      }

      System.arraycopy(this.mArray, 0, var2, 0, this.mSize);
      if(var2.length > this.mSize) {
         var2[this.mSize] = null;
      }

      return var2;
   }

   public String toString() {
      if(this.isEmpty()) {
         return "{}";
      } else {
         StringBuilder var2 = new StringBuilder(this.mSize * 14);
         var2.append('{');

         for(int var1 = 0; var1 < this.mSize; ++var1) {
            if(var1 > 0) {
               var2.append(", ");
            }

            Object var3 = this.valueAt(var1);
            if(var3 != this) {
               var2.append(var3);
            } else {
               var2.append("(this Set)");
            }
         }

         var2.append('}');
         return var2.toString();
      }
   }

   @Nullable
   public E valueAt(int var1) {
      return this.mArray[var1];
   }
}
