package com.google.android.gms.common.data;

import com.google.android.gms.common.data.Freezable;
import java.util.ArrayList;
import java.util.Iterator;

public final class FreezableUtils {

   public static <T extends Object, E extends Object & Freezable<T>> ArrayList<T> freeze(ArrayList<E> var0) {
      ArrayList var3 = new ArrayList(var0.size());
      int var2 = var0.size();

      for(int var1 = 0; var1 < var2; ++var1) {
         var3.add(((Freezable)var0.get(var1)).freeze());
      }

      return var3;
   }

   public static <T extends Object, E extends Object & Freezable<T>> ArrayList<T> freeze(E[] var0) {
      ArrayList var2 = new ArrayList(var0.length);

      for(int var1 = 0; var1 < var0.length; ++var1) {
         var2.add(var0[var1].freeze());
      }

      return var2;
   }

   public static <T extends Object, E extends Object & Freezable<T>> ArrayList<T> freezeIterable(Iterable<E> var0) {
      ArrayList var1 = new ArrayList();
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         var1.add(((Freezable)var2.next()).freeze());
      }

      return var1;
   }
}
