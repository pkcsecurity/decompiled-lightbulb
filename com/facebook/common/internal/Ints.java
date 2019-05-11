package com.facebook.common.internal;

import com.facebook.common.internal.Preconditions;

public class Ints {

   public static int max(int ... var0) {
      int var2 = var0.length;
      int var1 = 1;
      boolean var4;
      if(var2 > 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4);

      int var3;
      for(var2 = var0[0]; var1 < var0.length; var2 = var3) {
         var3 = var2;
         if(var0[var1] > var2) {
            var3 = var0[var1];
         }

         ++var1;
      }

      return var2;
   }
}
