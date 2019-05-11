package com.facebook.litho;

import android.support.annotation.Nullable;

public class CommonUtils {

   public static boolean equals(@Nullable Object var0, @Nullable Object var1) {
      return var0 == var1?true:(var0 != null && var1 != null?var0.equals(var1):false);
   }
}
