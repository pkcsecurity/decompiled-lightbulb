package com.alibaba.fastjson.util;

import java.lang.reflect.Type;

public class IdentityHashMap<V extends Object> {

   private final IdentityHashMap.Entry<V>[] buckets;
   private final int indexMask;


   public IdentityHashMap(int var1) {
      this.indexMask = var1 - 1;
      this.buckets = new IdentityHashMap.Entry[var1];
   }

   public Class findClass(String var1) {
      for(int var2 = 0; var2 < this.buckets.length; ++var2) {
         IdentityHashMap.Entry var4 = this.buckets[var2];
         if(var4 != null) {
            for(IdentityHashMap.Entry var3 = var4; var3 != null; var3 = var3.next) {
               Type var5 = var4.key;
               if(var5 instanceof Class) {
                  Class var6 = (Class)var5;
                  if(var6.getName().equals(var1)) {
                     return var6;
                  }
               }
            }
         }
      }

      return null;
   }

   public final V get(Type var1) {
      int var2 = System.identityHashCode(var1);
      int var3 = this.indexMask;

      for(IdentityHashMap.Entry var4 = this.buckets[var2 & var3]; var4 != null; var4 = var4.next) {
         if(var1 == var4.key) {
            return var4.value;
         }
      }

      return null;
   }

   public boolean put(Type var1, V var2) {
      int var3 = System.identityHashCode(var1);
      int var4 = this.indexMask & var3;

      for(IdentityHashMap.Entry var5 = this.buckets[var4]; var5 != null; var5 = var5.next) {
         if(var1 == var5.key) {
            var5.value = var2;
            return true;
         }
      }

      IdentityHashMap.Entry var6 = new IdentityHashMap.Entry(var1, var2, var3, this.buckets[var4]);
      this.buckets[var4] = var6;
      return false;
   }

   public static final class Entry<V extends Object> {

      public final int hashCode;
      public final Type key;
      public final IdentityHashMap.Entry<V> next;
      public V value;


      public Entry(Type var1, V var2, int var3, IdentityHashMap.Entry<V> var4) {
         this.key = var1;
         this.value = var2;
         this.next = var4;
         this.hashCode = var3;
      }
   }
}
