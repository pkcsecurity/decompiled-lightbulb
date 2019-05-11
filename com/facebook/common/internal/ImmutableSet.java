package com.facebook.common.internal;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ImmutableSet<E extends Object> extends HashSet<E> {

   private ImmutableSet(Set<E> var1) {
      super(var1);
   }

   public static <E extends Object> ImmutableSet<E> copyOf(Set<E> var0) {
      return new ImmutableSet(var0);
   }

   public static <E extends Object> ImmutableSet<E> of(E ... var0) {
      HashSet var1 = new HashSet();
      Collections.addAll(var1, var0);
      return new ImmutableSet(var1);
   }
}
