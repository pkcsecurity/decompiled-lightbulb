package com.facebook.yoga;


public class YogaMeasureOutput {

   public static float getHeight(long var0) {
      return Float.intBitsToFloat((int)(var0 & -1L));
   }

   public static float getWidth(long var0) {
      return Float.intBitsToFloat((int)(var0 >> 32 & -1L));
   }

   public static long make(float var0, float var1) {
      int var2 = Float.floatToRawIntBits(var0);
      int var3 = Float.floatToRawIntBits(var1);
      return (long)var2 << 32 | (long)var3;
   }

   public static long make(int var0, int var1) {
      return make((float)var0, (float)var1);
   }
}
