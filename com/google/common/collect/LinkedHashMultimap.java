package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible
public final class LinkedHashMultimap<K extends Object, V extends Object> extends qj<K, V> {

   @GwtIncompatible
   private static final long serialVersionUID = 1L;
   @VisibleForTesting
   transient int a;
   private transient LinkedHashMultimap.a<K, V> b;


   private static <K extends Object, V extends Object> void b(LinkedHashMultimap.ValueSetLink<K, V> var0) {
      b(var0.a(), var0.b());
   }

   private static <K extends Object, V extends Object> void b(LinkedHashMultimap.ValueSetLink<K, V> var0, LinkedHashMultimap.ValueSetLink<K, V> var1) {
      var0.b(var1);
      var1.a(var0);
   }

   private static <K extends Object, V extends Object> void b(LinkedHashMultimap.a<K, V> var0) {
      b(var0.c(), var0.d());
   }

   private static <K extends Object, V extends Object> void b(LinkedHashMultimap.a<K, V> var0, LinkedHashMultimap.a<K, V> var1) {
      var0.a(var1);
      var1.b(var0);
   }

   @GwtIncompatible
   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      byte var3 = 0;
      this.b = new LinkedHashMultimap.a((Object)null, (Object)null, 0, (LinkedHashMultimap.a)null);
      b(this.b, this.b);
      this.a = 2;
      int var4 = var1.readInt();
      LinkedHashMap var5 = new LinkedHashMap();

      int var2;
      Object var6;
      for(var2 = 0; var2 < var4; ++var2) {
         var6 = var1.readObject();
         var5.put(var6, this.a(var6));
      }

      var4 = var1.readInt();

      for(var2 = var3; var2 < var4; ++var2) {
         var6 = var1.readObject();
         Object var7 = var1.readObject();
         ((Collection)var5.get(var6)).add(var7);
      }

      this.a((Map)var5);
   }

   @GwtIncompatible
   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      var1.writeInt(this.i().size());
      Iterator var2 = this.i().iterator();

      while(var2.hasNext()) {
         var1.writeObject(var2.next());
      }

      var1.writeInt(this.b());
      var2 = this.l().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         var1.writeObject(var3.getKey());
         var1.writeObject(var3.getValue());
      }

   }

   // $FF: synthetic method
   Collection a() {
      return this.k();
   }

   Collection<V> a(K var1) {
      return new LinkedHashMultimap.b(var1, this.a);
   }

   public void c() {
      super.c();
      b(this.b, this.b);
   }

   // $FF: synthetic method
   public Collection e() {
      return this.l();
   }

   Iterator<Entry<K, V>> f() {
      return new Iterator() {

         LinkedHashMultimap.a<K, V> a;
         LinkedHashMultimap.a<K, V> b;

         {
            this.a = LinkedHashMultimap.this.b.h;
         }
         public Entry<K, V> a() {
            if(!this.hasNext()) {
               throw new NoSuchElementException();
            } else {
               LinkedHashMultimap.a var1 = this.a;
               this.b = var1;
               this.a = this.a.h;
               return var1;
            }
         }
         public boolean hasNext() {
            return this.a != LinkedHashMultimap.this.b;
         }
         // $FF: synthetic method
         public Object next() {
            return this.a();
         }
         public void remove() {
            boolean var1;
            if(this.b != null) {
               var1 = true;
            } else {
               var1 = false;
            }

            ql.a(var1);
            LinkedHashMultimap.this.b(this.b.getKey(), this.b.getValue());
            this.b = null;
         }
      };
   }

   Set<V> k() {
      return new LinkedHashSet(this.a);
   }

   public Set<Entry<K, V>> l() {
      return super.l();
   }

   interface ValueSetLink<K extends Object, V extends Object> {

      LinkedHashMultimap.ValueSetLink<K, V> a();

      void a(LinkedHashMultimap.ValueSetLink<K, V> var1);

      LinkedHashMultimap.ValueSetLink<K, V> b();

      void b(LinkedHashMultimap.ValueSetLink<K, V> var1);
   }

   @VisibleForTesting
   final class b extends rz.a<V> implements LinkedHashMultimap.ValueSetLink<K, V> {

      @VisibleForTesting
      LinkedHashMultimap.a<K, V>[] a;
      private final K c;
      private int d = 0;
      private int e = 0;
      private LinkedHashMultimap.ValueSetLink<K, V> f;
      private LinkedHashMultimap.ValueSetLink<K, V> g;


      b(Object var2, int var3) {
         this.c = var2;
         this.f = this;
         this.g = this;
         this.a = new LinkedHashMultimap.a[qt.a(var3, 1.0D)];
      }

      private int c() {
         return this.a.length - 1;
      }

      private void d() {
         if(qt.a(this.d, this.a.length, 1.0D)) {
            LinkedHashMultimap.a[] var4 = new LinkedHashMultimap.a[this.a.length * 2];
            this.a = var4;
            int var1 = var4.length;

            for(LinkedHashMultimap.ValueSetLink var3 = this.f; var3 != this; var3 = var3.b()) {
               LinkedHashMultimap.a var5 = (LinkedHashMultimap.a)var3;
               int var2 = var5.c & var1 - 1;
               var5.d = var4[var2];
               var4[var2] = var5;
            }
         }

      }

      public LinkedHashMultimap.ValueSetLink<K, V> a() {
         return this.g;
      }

      public void a(LinkedHashMultimap.ValueSetLink<K, V> var1) {
         this.g = var1;
      }

      public boolean add(@Nullable V var1) {
         int var2 = qt.a(var1);
         int var3 = this.c() & var2;
         LinkedHashMultimap.a var5 = this.a[var3];

         for(LinkedHashMultimap.a var4 = var5; var4 != null; var4 = var4.d) {
            if(var4.a(var1, var2)) {
               return false;
            }
         }

         LinkedHashMultimap.a var6 = new LinkedHashMultimap.a(this.c, var1, var2, var5);
         LinkedHashMultimap.b(this.g, (LinkedHashMultimap.ValueSetLink)var6);
         LinkedHashMultimap.b((LinkedHashMultimap.ValueSetLink)var6, (LinkedHashMultimap.ValueSetLink)this);
         LinkedHashMultimap.b(LinkedHashMultimap.this.b.c(), var6);
         LinkedHashMultimap.b(var6, LinkedHashMultimap.this.b);
         this.a[var3] = var6;
         ++this.d;
         ++this.e;
         this.d();
         return true;
      }

      public LinkedHashMultimap.ValueSetLink<K, V> b() {
         return this.f;
      }

      public void b(LinkedHashMultimap.ValueSetLink<K, V> var1) {
         this.f = var1;
      }

      public void clear() {
         Arrays.fill(this.a, (Object)null);
         this.d = 0;

         for(LinkedHashMultimap.ValueSetLink var1 = this.f; var1 != this; var1 = var1.b()) {
            LinkedHashMultimap.b((LinkedHashMultimap.a)var1);
         }

         LinkedHashMultimap.b((LinkedHashMultimap.ValueSetLink)this, (LinkedHashMultimap.ValueSetLink)this);
         ++this.e;
      }

      public boolean contains(@Nullable Object var1) {
         int var2 = qt.a(var1);

         for(LinkedHashMultimap.a var3 = this.a[this.c() & var2]; var3 != null; var3 = var3.d) {
            if(var3.a(var1, var2)) {
               return true;
            }
         }

         return false;
      }

      public Iterator<V> iterator() {
         return new Iterator() {

            LinkedHashMultimap.ValueSetLink<K, V> a;
            LinkedHashMultimap.a<K, V> b;
            int c;

            {
               this.a = b.this.f;
               this.c = b.this.e;
            }
            private void a() {
               if(b.this.e != this.c) {
                  throw new ConcurrentModificationException();
               }
            }
            public boolean hasNext() {
               this.a();
               return this.a != b.this;
            }
            public V next() {
               if(!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  LinkedHashMultimap.a var1 = (LinkedHashMultimap.a)this.a;
                  Object var2 = var1.getValue();
                  this.b = var1;
                  this.a = var1.b();
                  return var2;
               }
            }
            public void remove() {
               this.a();
               boolean var1;
               if(this.b != null) {
                  var1 = true;
               } else {
                  var1 = false;
               }

               ql.a(var1);
               b.this.remove(this.b.getValue());
               this.c = b.this.e;
               this.b = null;
            }
         };
      }

      public boolean remove(@Nullable Object var1) {
         int var2 = qt.a(var1);
         int var3 = this.c() & var2;
         LinkedHashMultimap.a var4 = this.a[var3];
         LinkedHashMultimap.a var5 = null;

         while(true) {
            LinkedHashMultimap.a var6 = var5;
            var5 = var4;
            if(var4 == null) {
               return false;
            }

            if(var4.a(var1, var2)) {
               if(var6 == null) {
                  this.a[var3] = var4.d;
               } else {
                  var6.d = var4.d;
               }

               LinkedHashMultimap.b((LinkedHashMultimap.ValueSetLink)var4);
               LinkedHashMultimap.b(var4);
               --this.d;
               ++this.e;
               return true;
            }

            var4 = var4.d;
         }
      }

      public int size() {
         return this.d;
      }
   }

   @VisibleForTesting
   static final class a<K extends Object, V extends Object> extends qx<K, V> implements LinkedHashMultimap.ValueSetLink<K, V> {

      final int c;
      @Nullable
      LinkedHashMultimap.a<K, V> d;
      LinkedHashMultimap.ValueSetLink<K, V> e;
      LinkedHashMultimap.ValueSetLink<K, V> f;
      LinkedHashMultimap.a<K, V> g;
      LinkedHashMultimap.a<K, V> h;


      a(@Nullable K var1, @Nullable V var2, int var3, @Nullable LinkedHashMultimap.a<K, V> var4) {
         super(var1, var2);
         this.c = var3;
         this.d = var4;
      }

      public LinkedHashMultimap.ValueSetLink<K, V> a() {
         return this.e;
      }

      public void a(LinkedHashMultimap.ValueSetLink<K, V> var1) {
         this.e = var1;
      }

      public void a(LinkedHashMultimap.a<K, V> var1) {
         this.h = var1;
      }

      boolean a(@Nullable Object var1, int var2) {
         return this.c == var2 && pt.a(this.getValue(), var1);
      }

      public LinkedHashMultimap.ValueSetLink<K, V> b() {
         return this.f;
      }

      public void b(LinkedHashMultimap.ValueSetLink<K, V> var1) {
         this.f = var1;
      }

      public void b(LinkedHashMultimap.a<K, V> var1) {
         this.g = var1;
      }

      public LinkedHashMultimap.a<K, V> c() {
         return this.g;
      }

      public LinkedHashMultimap.a<K, V> d() {
         return this.h;
      }
   }
}
