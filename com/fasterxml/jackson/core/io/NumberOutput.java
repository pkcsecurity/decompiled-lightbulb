package com.fasterxml.jackson.core.io;


public final class NumberOutput {

   private static int BILLION;
   static final char[] FULL_TRIPLETS = new char[4000];
   static final byte[] FULL_TRIPLETS_B;
   static final char[] LEADING_TRIPLETS = new char[4000];
   private static long MAX_INT_AS_LONG;
   private static int MILLION;
   private static long MIN_INT_AS_LONG;
   private static final char NULL_CHAR = '\u0000';
   static final String SMALLEST_LONG = String.valueOf(Long.MIN_VALUE);
   private static long TEN_BILLION_L;
   private static long THOUSAND_L;
   static final String[] sSmallIntStrs;
   static final String[] sSmallIntStrs2;


   static {
      int var6 = 0;

      int var5;
      for(var5 = 0; var6 < 10; ++var6) {
         char var2 = (char)(var6 + 48);
         char var0;
         if(var6 == 0) {
            var0 = 0;
         } else {
            var0 = var2;
         }

         for(int var7 = 0; var7 < 10; ++var7) {
            char var3 = (char)(var7 + 48);
            char var1;
            if(var6 == 0 && var7 == 0) {
               var1 = 0;
            } else {
               var1 = var3;
            }

            for(int var8 = 0; var8 < 10; ++var8) {
               char var4 = (char)(var8 + 48);
               LEADING_TRIPLETS[var5] = var0;
               char[] var11 = LEADING_TRIPLETS;
               int var9 = var5 + 1;
               var11[var9] = var1;
               var11 = LEADING_TRIPLETS;
               int var10 = var5 + 2;
               var11[var10] = var4;
               FULL_TRIPLETS[var5] = var2;
               FULL_TRIPLETS[var9] = var3;
               FULL_TRIPLETS[var10] = var4;
               var5 += 4;
            }
         }
      }

      FULL_TRIPLETS_B = new byte[4000];

      for(var5 = 0; var5 < 4000; ++var5) {
         FULL_TRIPLETS_B[var5] = (byte)FULL_TRIPLETS[var5];
      }

      sSmallIntStrs = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
      sSmallIntStrs2 = new String[]{"-1", "-2", "-3", "-4", "-5", "-6", "-7", "-8", "-9", "-10"};
   }

   private static int calcLongStrLength(long var0) {
      long var3 = TEN_BILLION_L;

      int var2;
      for(var2 = 10; var0 >= var3; var3 = (var3 << 3) + (var3 << 1)) {
         if(var2 == 19) {
            return var2;
         }

         ++var2;
      }

      return var2;
   }

   private static int outputFullTriplet(int var0, byte[] var1, int var2) {
      int var4 = var0 << 2;
      var0 = var2 + 1;
      byte[] var5 = FULL_TRIPLETS_B;
      int var3 = var4 + 1;
      var1[var2] = var5[var4];
      var2 = var0 + 1;
      var1[var0] = FULL_TRIPLETS_B[var3];
      var1[var2] = FULL_TRIPLETS_B[var3 + 1];
      return var2 + 1;
   }

   private static int outputFullTriplet(int var0, char[] var1, int var2) {
      int var4 = var0 << 2;
      var0 = var2 + 1;
      char[] var5 = FULL_TRIPLETS;
      int var3 = var4 + 1;
      var1[var2] = var5[var4];
      var2 = var0 + 1;
      var1[var0] = FULL_TRIPLETS[var3];
      var1[var2] = FULL_TRIPLETS[var3 + 1];
      return var2 + 1;
   }

   public static int outputInt(int var0, byte[] var1, int var2) {
      int var4 = var0;
      int var3 = var2;
      if(var0 < 0) {
         if(var0 == Integer.MIN_VALUE) {
            return outputLong((long)var0, var1, var2);
         }

         var1[var2] = 45;
         var4 = -var0;
         var3 = var2 + 1;
      }

      if(var4 < MILLION) {
         if(var4 < 1000) {
            if(var4 < 10) {
               var1[var3] = (byte)(var4 + 48);
               return var3 + 1;
            } else {
               return outputLeadingTriplet(var4, var1, var3);
            }
         } else {
            var0 = var4 / 1000;
            return outputFullTriplet(var4 - var0 * 1000, var1, outputLeadingTriplet(var0, var1, var3));
         }
      } else {
         boolean var6;
         if(var4 >= BILLION) {
            var6 = true;
         } else {
            var6 = false;
         }

         if(var6) {
            var4 -= BILLION;
            if(var4 >= BILLION) {
               var4 -= BILLION;
               var0 = var3 + 1;
               var1[var3] = 50;
            } else {
               var0 = var3 + 1;
               var1[var3] = 49;
            }
         } else {
            var0 = var3;
         }

         var3 = var4 / 1000;
         int var5 = var3 / 1000;
         if(var6) {
            var0 = outputFullTriplet(var5, var1, var0);
         } else {
            var0 = outputLeadingTriplet(var5, var1, var0);
         }

         return outputFullTriplet(var4 - var3 * 1000, var1, outputFullTriplet(var3 - var5 * 1000, var1, var0));
      }
   }

   public static int outputInt(int var0, char[] var1, int var2) {
      int var4 = var0;
      int var3 = var2;
      if(var0 < 0) {
         if(var0 == Integer.MIN_VALUE) {
            return outputLong((long)var0, var1, var2);
         }

         var1[var2] = 45;
         var4 = -var0;
         var3 = var2 + 1;
      }

      if(var4 < MILLION) {
         if(var4 < 1000) {
            if(var4 < 10) {
               var1[var3] = (char)(var4 + 48);
               return var3 + 1;
            } else {
               return outputLeadingTriplet(var4, var1, var3);
            }
         } else {
            var0 = var4 / 1000;
            return outputFullTriplet(var4 - var0 * 1000, var1, outputLeadingTriplet(var0, var1, var3));
         }
      } else {
         boolean var6;
         if(var4 >= BILLION) {
            var6 = true;
         } else {
            var6 = false;
         }

         if(var6) {
            var4 -= BILLION;
            if(var4 >= BILLION) {
               var4 -= BILLION;
               var0 = var3 + 1;
               var1[var3] = 50;
            } else {
               var0 = var3 + 1;
               var1[var3] = 49;
            }
         } else {
            var0 = var3;
         }

         var3 = var4 / 1000;
         int var5 = var3 / 1000;
         if(var6) {
            var0 = outputFullTriplet(var5, var1, var0);
         } else {
            var0 = outputLeadingTriplet(var5, var1, var0);
         }

         return outputFullTriplet(var4 - var3 * 1000, var1, outputFullTriplet(var3 - var5 * 1000, var1, var0));
      }
   }

   private static int outputLeadingTriplet(int var0, byte[] var1, int var2) {
      var0 <<= 2;
      char[] var5 = LEADING_TRIPLETS;
      int var3 = var0 + 1;
      char var4 = var5[var0];
      var0 = var2;
      if(var4 != 0) {
         var1[var2] = (byte)var4;
         var0 = var2 + 1;
      }

      var4 = LEADING_TRIPLETS[var3];
      var2 = var0;
      if(var4 != 0) {
         var1[var0] = (byte)var4;
         var2 = var0 + 1;
      }

      var1[var2] = (byte)LEADING_TRIPLETS[var3 + 1];
      return var2 + 1;
   }

   private static int outputLeadingTriplet(int var0, char[] var1, int var2) {
      var0 <<= 2;
      char[] var5 = LEADING_TRIPLETS;
      int var4 = var0 + 1;
      char var3 = var5[var0];
      var0 = var2;
      if(var3 != 0) {
         var1[var2] = var3;
         var0 = var2 + 1;
      }

      var3 = LEADING_TRIPLETS[var4];
      var2 = var0;
      if(var3 != 0) {
         var1[var0] = var3;
         var2 = var0 + 1;
      }

      var1[var2] = LEADING_TRIPLETS[var4 + 1];
      return var2 + 1;
   }

   public static int outputLong(long var0, byte[] var2, int var3) {
      int var4;
      int var5;
      long var8;
      if(var0 < 0L) {
         if(var0 > MIN_INT_AS_LONG) {
            return outputInt((int)var0, var2, var3);
         }

         if(var0 == Long.MIN_VALUE) {
            var5 = SMALLEST_LONG.length();

            for(var4 = 0; var4 < var5; ++var3) {
               var2[var3] = (byte)SMALLEST_LONG.charAt(var4);
               ++var4;
            }

            return var3;
         }

         var2[var3] = 45;
         var8 = -var0;
         var4 = var3 + 1;
      } else {
         var8 = var0;
         var4 = var3;
         if(var0 <= MAX_INT_AS_LONG) {
            return outputInt((int)var0, var2, var3);
         }
      }

      int var6 = calcLongStrLength(var8) + var4;

      for(var3 = var6; var8 > MAX_INT_AS_LONG; var8 = var0) {
         var3 -= 3;
         var0 = var8 / THOUSAND_L;
         outputFullTriplet((int)(var8 - THOUSAND_L * var0), var2, var3);
      }

      int var7;
      for(var5 = (int)var8; var5 >= 1000; var5 = var7) {
         var3 -= 3;
         var7 = var5 / 1000;
         outputFullTriplet(var5 - var7 * 1000, var2, var3);
      }

      outputLeadingTriplet(var5, var2, var4);
      return var6;
   }

   public static int outputLong(long var0, char[] var2, int var3) {
      int var4;
      long var8;
      if(var0 < 0L) {
         if(var0 > MIN_INT_AS_LONG) {
            return outputInt((int)var0, var2, var3);
         }

         if(var0 == Long.MIN_VALUE) {
            var4 = SMALLEST_LONG.length();
            SMALLEST_LONG.getChars(0, var4, var2, var3);
            return var3 + var4;
         }

         var2[var3] = 45;
         var8 = -var0;
         var4 = var3 + 1;
      } else {
         var8 = var0;
         var4 = var3;
         if(var0 <= MAX_INT_AS_LONG) {
            return outputInt((int)var0, var2, var3);
         }
      }

      int var6 = calcLongStrLength(var8) + var4;

      for(var3 = var6; var8 > MAX_INT_AS_LONG; var8 = var0) {
         var3 -= 3;
         var0 = var8 / THOUSAND_L;
         outputFullTriplet((int)(var8 - THOUSAND_L * var0), var2, var3);
      }

      int var5;
      int var7;
      for(var5 = (int)var8; var5 >= 1000; var5 = var7) {
         var3 -= 3;
         var7 = var5 / 1000;
         outputFullTriplet(var5 - var7 * 1000, var2, var3);
      }

      outputLeadingTriplet(var5, var2, var4);
      return var6;
   }

   public static String toString(double var0) {
      return Double.toString(var0);
   }

   public static String toString(int var0) {
      if(var0 < sSmallIntStrs.length) {
         if(var0 >= 0) {
            return sSmallIntStrs[var0];
         }

         int var1 = -var0 - 1;
         if(var1 < sSmallIntStrs2.length) {
            return sSmallIntStrs2[var1];
         }
      }

      return Integer.toString(var0);
   }

   public static String toString(long var0) {
      return var0 <= 2147483647L && var0 >= -2147483648L?toString((int)var0):Long.toString(var0);
   }
}
