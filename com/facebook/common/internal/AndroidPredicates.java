package com.facebook.common.internal;

import com.android.internal.util.Predicate;

public class AndroidPredicates {

   public static <T extends Object> Predicate<T> False() {
      return new Predicate() {
         public boolean apply(T var1) {
            return false;
         }
      };
   }

   public static <T extends Object> Predicate<T> True() {
      return new Predicate() {
         public boolean apply(T var1) {
            return true;
         }
      };
   }
}
