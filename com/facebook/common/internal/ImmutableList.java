package com.facebook.common.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImmutableList<E extends Object> extends ArrayList<E> {

   private ImmutableList(int var1) {
      super(var1);
   }

   private ImmutableList(List<E> var1) {
      super(var1);
   }

   public static <E extends Object> ImmutableList<E> copyOf(List<E> var0) {
      return new ImmutableList(var0);
   }

   public static <E extends Object> ImmutableList<E> of(E ... var0) {
      ImmutableList var1 = new ImmutableList(var0.length);
      Collections.addAll(var1, var0);
      return var1;
   }
}
