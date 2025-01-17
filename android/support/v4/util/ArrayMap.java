package android.support.v4.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.MapCollections;
import android.support.v4.util.SimpleArrayMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class ArrayMap<K extends Object, V extends Object> extends SimpleArrayMap<K, V> implements Map<K, V> {

   @Nullable
   MapCollections<K, V> mCollections;


   public ArrayMap() {}

   public ArrayMap(int var1) {
      super(var1);
   }

   public ArrayMap(SimpleArrayMap var1) {
      super(var1);
   }

   private MapCollections<K, V> getCollection() {
      if(this.mCollections == null) {
         this.mCollections = new MapCollections() {
            protected void colClear() {
               ArrayMap.this.clear();
            }
            protected Object colGetEntry(int var1, int var2) {
               return ArrayMap.this.mArray[(var1 << 1) + var2];
            }
            protected Map<K, V> colGetMap() {
               return ArrayMap.this;
            }
            protected int colGetSize() {
               return ArrayMap.this.mSize;
            }
            protected int colIndexOfKey(Object var1) {
               return ArrayMap.this.indexOfKey(var1);
            }
            protected int colIndexOfValue(Object var1) {
               return ArrayMap.this.indexOfValue(var1);
            }
            protected void colPut(K var1, V var2) {
               ArrayMap.this.put(var1, var2);
            }
            protected void colRemoveAt(int var1) {
               ArrayMap.this.removeAt(var1);
            }
            protected V colSetValue(int var1, V var2) {
               return ArrayMap.this.setValueAt(var1, var2);
            }
         };
      }

      return this.mCollections;
   }

   public boolean containsAll(@NonNull Collection<?> var1) {
      return MapCollections.containsAllHelper(this, var1);
   }

   public Set<Entry<K, V>> entrySet() {
      return this.getCollection().getEntrySet();
   }

   public Set<K> keySet() {
      return this.getCollection().getKeySet();
   }

   public void putAll(Map<? extends K, ? extends V> var1) {
      this.ensureCapacity(this.mSize + var1.size());
      Iterator var3 = var1.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var2 = (Entry)var3.next();
         this.put(var2.getKey(), var2.getValue());
      }

   }

   public boolean removeAll(@NonNull Collection<?> var1) {
      return MapCollections.removeAllHelper(this, var1);
   }

   public boolean retainAll(@NonNull Collection<?> var1) {
      return MapCollections.retainAllHelper(this, var1);
   }

   public Collection<V> values() {
      return this.getCollection().getValues();
   }
}
