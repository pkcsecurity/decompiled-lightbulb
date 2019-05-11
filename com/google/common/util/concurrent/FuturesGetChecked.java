package com.google.common.util.concurrent;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import java.lang.reflect.Constructor;
import java.util.Arrays;

final class FuturesGetChecked {

   private static final rp<Constructor<?>> a = rp.b().a(new Function() {
      public Boolean a(Constructor<?> var1) {
         return Boolean.valueOf(Arrays.asList(var1.getParameterTypes()).contains(String.class));
      }
      // $FF: synthetic method
      public Object apply(Object var1) {
         return this.a((Constructor)var1);
      }
   }).a();



   @VisibleForTesting
   interface GetCheckedTypeValidator {
   }
}
