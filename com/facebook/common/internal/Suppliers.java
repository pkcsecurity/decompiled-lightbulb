package com.facebook.common.internal;

import com.facebook.common.internal.Supplier;

public class Suppliers {

   public static <T extends Object> Supplier<T> of(final T var0) {
      return new Supplier() {
         public T get() {
            return var0;
         }
      };
   }
}
