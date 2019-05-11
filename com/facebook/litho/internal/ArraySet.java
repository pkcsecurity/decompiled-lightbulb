package com.facebook.litho.internal;

import android.support.v4.util.SimpleArrayMap;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.Set;
import javax.annotation.Nullable;

@Deprecated
public class ArraySet<E extends Object> implements Set<E> {

   private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
   private static final Integer SENTINEL_MAP_VALUE = Integer.valueOf(0);
   private final SimpleArrayMap<E, Integer> mMap;


   public ArraySet() {
      this.mMap = new SimpleArrayMap();
   }

   public ArraySet(int var1) {
      this.mMap = new SimpleArrayMap(var1);
   }

   public ArraySet(@Nullable Collection<? extends E> var1) {
      this.mMap = new SimpleArrayMap();
      if(var1 != null) {
         this.addAll(var1);
      }

   }

   public boolean add(E var1) {
      return this.mMap.put(var1, SENTINEL_MAP_VALUE) == null;
   }

   public boolean addAll(Collection<? extends E> var1) {
      this.ensureCapacity(this.size() + var1.size());
      boolean var6 = var1 instanceof ArraySet;
      boolean var4 = false;
      boolean var5 = false;
      int var2 = 0;
      if(var6) {
         ArraySet var7 = (ArraySet)var1;
         var2 = this.size();
         this.mMap.putAll(var7.mMap);
         if(this.size() != var2) {
            return true;
         }
      } else {
         if(var1 instanceof List && var1 instanceof RandomAccess) {
            List var9 = (List)var1;
            int var3 = var9.size();

            for(var4 = false; var2 < var3; ++var2) {
               var4 |= this.add(var9.get(var2));
            }

            return var4;
         }

         Iterator var8 = var1.iterator();

         while(true) {
            var5 = var4;
            if(!var8.hasNext()) {
               break;
            }

            var4 |= this.add(var8.next());
         }
      }

      return var5;
   }

   public void clear() {
      this.mMap.clear();
   }

   public void clearAndAddAll(ArraySet<? extends E> var1) {
      this.clear();
      this.addAll(var1);
   }

   public boolean contains(Object var1) {
      return this.mMap.containsKey(var1);
   }

   public boolean containsAll(Collection<?> var1) {
      if(var1 instanceof List && var1 instanceof RandomAccess) {
         List var5 = (List)var1;
         int var3 = var5.size();

         for(int var2 = 0; var2 < var3; ++var2) {
            if(!this.contains(var5.get(var2))) {
               return false;
            }
         }
      } else {
         Iterator var4 = var1.iterator();

         while(var4.hasNext()) {
            if(!this.contains(var4.next())) {
               return false;
            }
         }
      }

      return true;
   }

   public void ensureCapacity(int var1) {
      this.mMap.ensureCapacity(var1);
   }

   public boolean equals(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public int hashCode() {
      int var4 = this.size();
      int var1 = 0;

      int var2;
      int var3;
      for(var2 = 0; var1 < var4; var2 = var3) {
         Object var5 = this.valueAt(var1);
         var3 = var2;
         if(var5 != null) {
            var3 = var2 + var5.hashCode();
         }

         ++var1;
      }

      return var2;
   }

   public int indexOf(E var1) {
      return this.mMap.indexOfKey(var1);
   }

   public boolean isEmpty() {
      return this.mMap.isEmpty();
   }

   public Iterator<E> iterator() {
      return new ArraySet.ArraySetIterator();
   }

   public boolean remove(Object var1) {
      int var2 = this.indexOf(var1);
      if(var2 >= 0) {
         this.removeAt(var2);
         return true;
      } else {
         return false;
      }
   }

   public boolean removeAll(Collection<?> var1) {
      boolean var4 = var1 instanceof List;
      int var2 = 0;
      boolean var5;
      if(var4 && var1 instanceof RandomAccess) {
         List var7 = (List)var1;
         int var3 = var7.size();
         var4 = false;

         while(true) {
            var5 = var4;
            if(var2 >= var3) {
               break;
            }

            var4 |= this.remove(var7.get(var2));
            ++var2;
         }
      } else {
         Iterator var6 = var1.iterator();
         var4 = false;

         while(true) {
            var5 = var4;
            if(!var6.hasNext()) {
               break;
            }

            var4 |= this.remove(var6.next());
         }
      }

      return var5;
   }

   public E removeAt(int var1) {
      Object var2 = this.mMap.keyAt(var1);
      this.mMap.removeAt(var1);
      return var2;
   }

   public boolean retainAll(Collection<?> var1) {
      int var2 = this.size() - 1;

      boolean var3;
      for(var3 = false; var2 >= 0; --var2) {
         if(!var1.contains(this.valueAt(var2))) {
            this.removeAt(var2);
            var3 = true;
         }
      }

      return var3;
   }

   public int size() {
      return this.mMap.size();
   }

   public Object[] toArray() {
      int var2 = this.mMap.size();
      if(var2 == 0) {
         return EMPTY_OBJECT_ARRAY;
      } else {
         Object[] var3 = new Object[var2];

         for(int var1 = 0; var1 < var2; ++var1) {
            var3[var1] = this.mMap.keyAt(var1);
         }

         return var3;
      }
   }

   public <T extends Object> T[] toArray(T[] var1) {
      int var3 = this.size();
      Object[] var4 = var1;
      if(var1.length < var3) {
         var4 = (Object[])Array.newInstance(var1.getClass().getComponentType(), var3);
      }

      for(int var2 = 0; var2 < var3; ++var2) {
         var4[var2] = this.valueAt(var2);
      }

      if(var4.length > var3) {
         var4[var3] = null;
      }

      return var4;
   }

   public String toString() {
      if(this.isEmpty()) {
         return "{}";
      } else {
         int var2 = this.size();
         StringBuilder var3 = new StringBuilder(var2 * 14);
         var3.append('{');

         for(int var1 = 0; var1 < var2; ++var1) {
            if(var1 > 0) {
               var3.append(", ");
            }

            Object var4 = this.valueAt(var1);
            if(var4 != this) {
               var3.append(var4);
            } else {
               var3.append("(this Set)");
            }
         }

         var3.append('}');
         return var3.toString();
      }
   }

   public E valueAt(int var1) {
      return this.mMap.keyAt(var1);
   }

   final class ArraySetIterator implements Iterator<E> {

      private int mIndex = -1;
      private boolean mRemoved;


      public boolean hasNext() {
         return this.mIndex + 1 < ArraySet.this.size();
      }

      public E next() {
         if(!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            this.mRemoved = false;
            ++this.mIndex;
            return ArraySet.this.valueAt(this.mIndex);
         }
      }

      public void remove() {
         if(this.mRemoved) {
            throw new IllegalStateException();
         } else {
            ArraySet.this.removeAt(this.mIndex);
            this.mRemoved = true;
            --this.mIndex;
         }
      }
   }
}
