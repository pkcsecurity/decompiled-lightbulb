package com.facebook.imagepipeline.cache;

import com.android.internal.util.Predicate;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.imagepipeline.cache.ValueDescriptor;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class CountingLruMap<K extends Object, V extends Object> {

   @GuardedBy
   private final LinkedHashMap<K, V> mMap = new LinkedHashMap();
   @GuardedBy
   private int mSizeInBytes = 0;
   private final ValueDescriptor<V> mValueDescriptor;


   public CountingLruMap(ValueDescriptor<V> var1) {
      this.mValueDescriptor = var1;
   }

   private int getValueSizeInBytes(V var1) {
      return var1 == null?0:this.mValueDescriptor.getSizeInBytes(var1);
   }

   public ArrayList<V> clear() {
      synchronized(this){}

      ArrayList var1;
      try {
         var1 = new ArrayList(this.mMap.values());
         this.mMap.clear();
         this.mSizeInBytes = 0;
      } finally {
         ;
      }

      return var1;
   }

   public boolean contains(K var1) {
      synchronized(this){}

      boolean var2;
      try {
         var2 = this.mMap.containsKey(var1);
      } finally {
         ;
      }

      return var2;
   }

   @Nullable
   public V get(K var1) {
      synchronized(this){}

      try {
         var1 = this.mMap.get(var1);
      } finally {
         ;
      }

      return var1;
   }

   public int getCount() {
      synchronized(this){}

      int var1;
      try {
         var1 = this.mMap.size();
      } finally {
         ;
      }

      return var1;
   }

   @Nullable
   public K getFirstKey() {
      synchronized(this){}
      boolean var3 = false;

      Object var1;
      try {
         var3 = true;
         if(!this.mMap.isEmpty()) {
            var1 = this.mMap.keySet().iterator().next();
            var3 = false;
            return var1;
         }

         var3 = false;
      } finally {
         if(var3) {
            ;
         }
      }

      var1 = null;
      return var1;
   }

   @VisibleForTesting
   ArrayList<K> getKeys() {
      synchronized(this){}

      ArrayList var1;
      try {
         var1 = new ArrayList(this.mMap.keySet());
      } finally {
         ;
      }

      return var1;
   }

   public ArrayList<Entry<K, V>> getMatchingEntries(@Nullable Predicate<K> param1) {
      // $FF: Couldn't be decompiled
   }

   public int getSizeInBytes() {
      synchronized(this){}

      int var1;
      try {
         var1 = this.mSizeInBytes;
      } finally {
         ;
      }

      return var1;
   }

   @VisibleForTesting
   ArrayList<V> getValues() {
      synchronized(this){}

      ArrayList var1;
      try {
         var1 = new ArrayList(this.mMap.values());
      } finally {
         ;
      }

      return var1;
   }

   @Nullable
   public V put(K var1, V var2) {
      synchronized(this){}

      Object var3;
      try {
         var3 = this.mMap.remove(var1);
         this.mSizeInBytes -= this.getValueSizeInBytes(var3);
         this.mMap.put(var1, var2);
         this.mSizeInBytes += this.getValueSizeInBytes(var2);
      } finally {
         ;
      }

      return var3;
   }

   @Nullable
   public V remove(K var1) {
      synchronized(this){}

      try {
         var1 = this.mMap.remove(var1);
         this.mSizeInBytes -= this.getValueSizeInBytes(var1);
      } finally {
         ;
      }

      return var1;
   }

   public ArrayList<V> removeAll(@Nullable Predicate<K> param1) {
      // $FF: Couldn't be decompiled
   }
}
