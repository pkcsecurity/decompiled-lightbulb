package android.arch.core.internal;

import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import java.util.Iterator;
import java.util.WeakHashMap;
import java.util.Map.Entry;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class SafeIterableMap<K extends Object, V extends Object> implements Iterable<Entry<K, V>> {

   private SafeIterableMap.c<K, V> a;
   private SafeIterableMap.c<K, V> b;
   private WeakHashMap<SafeIterableMap.SupportRemove<K, V>, Boolean> c = new WeakHashMap();
   private int d = 0;


   public int a() {
      return this.d;
   }

   public SafeIterableMap.c<K, V> a(K var1) {
      SafeIterableMap.c var2;
      for(var2 = this.a; var2 != null; var2 = var2.c) {
         if(var2.a.equals(var1)) {
            return var2;
         }
      }

      return var2;
   }

   public V a(@NonNull K var1, @NonNull V var2) {
      SafeIterableMap.c var3 = this.a(var1);
      if(var3 != null) {
         return var3.b;
      } else {
         this.b(var1, var2);
         return null;
      }
   }

   protected SafeIterableMap.c<K, V> b(@NonNull K var1, @NonNull V var2) {
      SafeIterableMap.c var3 = new SafeIterableMap.c(var1, var2);
      ++this.d;
      if(this.b == null) {
         this.a = var3;
         this.b = this.a;
         return var3;
      } else {
         this.b.c = var3;
         var3.d = this.b;
         this.b = var3;
         return var3;
      }
   }

   public V b(@NonNull K var1) {
      SafeIterableMap.c var3 = this.a(var1);
      if(var3 == null) {
         return null;
      } else {
         --this.d;
         if(!this.c.isEmpty()) {
            Iterator var2 = this.c.keySet().iterator();

            while(var2.hasNext()) {
               ((SafeIterableMap.SupportRemove)var2.next()).a_(var3);
            }
         }

         if(var3.d != null) {
            var3.d.c = var3.c;
         } else {
            this.a = var3.c;
         }

         if(var3.c != null) {
            var3.c.d = var3.d;
         } else {
            this.b = var3.d;
         }

         var3.c = null;
         var3.d = null;
         return var3.b;
      }
   }

   public Iterator<Entry<K, V>> b() {
      SafeIterableMap.b var1 = new SafeIterableMap.b(this.b, this.a);
      this.c.put(var1, Boolean.valueOf(false));
      return var1;
   }

   public SafeIterableMap.d c() {
      SafeIterableMap.d var1 = new SafeIterableMap.d(null);
      this.c.put(var1, Boolean.valueOf(false));
      return var1;
   }

   public Entry<K, V> d() {
      return this.a;
   }

   public Entry<K, V> e() {
      return this.b;
   }

   public boolean equals(Object var1) {
      if(var1 == this) {
         return true;
      } else if(!(var1 instanceof SafeIterableMap)) {
         return false;
      } else {
         SafeIterableMap var2 = (SafeIterableMap)var1;
         if(this.a() != var2.a()) {
            return false;
         } else {
            Iterator var5 = this.iterator();
            Iterator var6 = var2.iterator();

            while(true) {
               if(var5.hasNext() && var6.hasNext()) {
                  Entry var3 = (Entry)var5.next();
                  Object var4 = var6.next();
                  if((var3 != null || var4 == null) && (var3 == null || var3.equals(var4))) {
                     continue;
                  }

                  return false;
               }

               if(!var5.hasNext() && !var6.hasNext()) {
                  return true;
               }

               return false;
            }
         }
      }
   }

   @NonNull
   public Iterator<Entry<K, V>> iterator() {
      SafeIterableMap.a var1 = new SafeIterableMap.a(this.a, this.b);
      this.c.put(var1, Boolean.valueOf(false));
      return var1;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("[");
      Iterator var2 = this.iterator();

      while(var2.hasNext()) {
         var1.append(((Entry)var2.next()).toString());
         if(var2.hasNext()) {
            var1.append(", ");
         }
      }

      var1.append("]");
      return var1.toString();
   }

   static class a<K extends Object, V extends Object> extends SafeIterableMap.e<K, V> {

      a(SafeIterableMap.c<K, V> var1, SafeIterableMap.c<K, V> var2) {
         super(var1, var2);
      }

      SafeIterableMap.c<K, V> a(SafeIterableMap.c<K, V> var1) {
         return var1.c;
      }

      SafeIterableMap.c<K, V> b(SafeIterableMap.c<K, V> var1) {
         return var1.d;
      }
   }

   interface SupportRemove<K extends Object, V extends Object> {

      void a_(@NonNull SafeIterableMap.c<K, V> var1);
   }

   public class d implements SafeIterableMap.SupportRemove<K, V>, Iterator<Entry<K, V>> {

      private SafeIterableMap.c<K, V> b;
      private boolean c;


      private d() {
         this.c = true;
      }

      // $FF: synthetic method
      d(Object var2) {
         this();
      }

      public Entry<K, V> a() {
         if(this.c) {
            this.c = false;
            this.b = SafeIterableMap.this.a;
         } else {
            SafeIterableMap.c var1;
            if(this.b != null) {
               var1 = this.b.c;
            } else {
               var1 = null;
            }

            this.b = var1;
         }

         return this.b;
      }

      public void a_(@NonNull SafeIterableMap.c<K, V> var1) {
         if(var1 == this.b) {
            this.b = this.b.d;
            boolean var2;
            if(this.b == null) {
               var2 = true;
            } else {
               var2 = false;
            }

            this.c = var2;
         }

      }

      public boolean hasNext() {
         boolean var3 = this.c;
         boolean var2 = false;
         boolean var1 = false;
         if(var3) {
            if(SafeIterableMap.this.a != null) {
               var1 = true;
            }

            return var1;
         } else {
            var1 = var2;
            if(this.b != null) {
               var1 = var2;
               if(this.b.c != null) {
                  var1 = true;
               }
            }

            return var1;
         }
      }

      // $FF: synthetic method
      public Object next() {
         return this.a();
      }
   }

   abstract static class e<K extends Object, V extends Object> implements SafeIterableMap.SupportRemove<K, V>, Iterator<Entry<K, V>> {

      SafeIterableMap.c<K, V> a;
      SafeIterableMap.c<K, V> b;


      e(SafeIterableMap.c<K, V> var1, SafeIterableMap.c<K, V> var2) {
         this.a = var2;
         this.b = var1;
      }

      private SafeIterableMap.c<K, V> b() {
         return this.b != this.a && this.a != null?this.a(this.b):null;
      }

      abstract SafeIterableMap.c<K, V> a(SafeIterableMap.c<K, V> var1);

      public Entry<K, V> a() {
         SafeIterableMap.c var1 = this.b;
         this.b = this.b();
         return var1;
      }

      public void a_(@NonNull SafeIterableMap.c<K, V> var1) {
         if(this.a == var1 && var1 == this.b) {
            this.b = null;
            this.a = null;
         }

         if(this.a == var1) {
            this.a = this.b(this.a);
         }

         if(this.b == var1) {
            this.b = this.b();
         }

      }

      abstract SafeIterableMap.c<K, V> b(SafeIterableMap.c<K, V> var1);

      public boolean hasNext() {
         return this.b != null;
      }

      // $FF: synthetic method
      public Object next() {
         return this.a();
      }
   }

   static class b<K extends Object, V extends Object> extends SafeIterableMap.e<K, V> {

      b(SafeIterableMap.c<K, V> var1, SafeIterableMap.c<K, V> var2) {
         super(var1, var2);
      }

      SafeIterableMap.c<K, V> a(SafeIterableMap.c<K, V> var1) {
         return var1.d;
      }

      SafeIterableMap.c<K, V> b(SafeIterableMap.c<K, V> var1) {
         return var1.c;
      }
   }

   public static class c<K extends Object, V extends Object> implements Entry<K, V> {

      @NonNull
      final K a;
      @NonNull
      public final V b;
      SafeIterableMap.c<K, V> c;
      public SafeIterableMap.c<K, V> d;


      c(@NonNull K var1, @NonNull V var2) {
         this.a = var1;
         this.b = var2;
      }

      public boolean equals(Object var1) {
         if(var1 == this) {
            return true;
         } else if(!(var1 instanceof SafeIterableMap.c)) {
            return false;
         } else {
            SafeIterableMap.c var2 = (SafeIterableMap.c)var1;
            return this.a.equals(var2.a) && this.b.equals(var2.b);
         }
      }

      @NonNull
      public K getKey() {
         return this.a;
      }

      @NonNull
      public V getValue() {
         return this.b;
      }

      public V setValue(V var1) {
         throw new UnsupportedOperationException("An entry modification is not supported");
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append(this.a);
         var1.append("=");
         var1.append(this.b);
         return var1.toString();
      }
   }
}
