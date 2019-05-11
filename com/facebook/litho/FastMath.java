package com.facebook.litho;


public class FastMath {

   public static int round(float var0) {
      return var0 > 0.0F?(int)((double)var0 + 0.5D):(int)((double)var0 - 0.5D);
   }
}
