package com.facebook.common.util;


public class Hex {

   private static final byte[] DIGITS;
   private static final char[] FIRST_CHAR = new char[256];
   private static final char[] HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
   private static final char[] SECOND_CHAR = new char[256];


   static {
      byte var2 = 0;

      int var1;
      for(var1 = 0; var1 < 256; ++var1) {
         FIRST_CHAR[var1] = HEX_DIGITS[var1 >> 4 & 15];
         SECOND_CHAR[var1] = HEX_DIGITS[var1 & 15];
      }

      DIGITS = new byte[103];

      for(var1 = 0; var1 <= 70; ++var1) {
         DIGITS[var1] = -1;
      }

      byte var0 = 0;

      while(true) {
         byte var4 = var2;
         if(var0 >= 10) {
            while(var4 < 6) {
               byte[] var3 = DIGITS;
               var0 = (byte)(var4 + 10);
               var3[var4 + 65] = var0;
               DIGITS[var4 + 97] = var0;
               ++var4;
            }

            return;
         }

         DIGITS[var0 + 48] = var0++;
      }
   }

   public static String byte2Hex(int var0) {
      if(var0 <= 255 && var0 >= 0) {
         StringBuilder var1 = new StringBuilder();
         var1.append(String.valueOf(FIRST_CHAR[var0]));
         var1.append(String.valueOf(SECOND_CHAR[var0]));
         return var1.toString();
      } else {
         throw new IllegalArgumentException("The int converting to hex should be in range 0~255");
      }
   }

   public static byte[] decodeHex(String var0) {
      int var4 = var0.length();
      if((var4 & 1) != 0) {
         throw new IllegalArgumentException("Odd number of characters.");
      } else {
         byte[] var7 = new byte[var4 >> 1];
         int var2 = 0;
         int var1 = 0;

         boolean var8;
         while(true) {
            boolean var3 = true;
            if(var2 >= var4) {
               var8 = false;
               break;
            }

            int var5 = var2 + 1;
            char var9 = var0.charAt(var2);
            if(var9 > 102) {
               var8 = var3;
               break;
            }

            byte var10 = DIGITS[var9];
            if(var10 == -1) {
               var8 = var3;
               break;
            }

            char var6 = var0.charAt(var5);
            if(var6 > 102) {
               var8 = var3;
               break;
            }

            byte var11 = DIGITS[var6];
            if(var11 == -1) {
               var8 = var3;
               break;
            }

            var7[var1] = (byte)(var10 << 4 | var11);
            ++var1;
            var2 = var5 + 1;
         }

         if(var8) {
            StringBuilder var12 = new StringBuilder();
            var12.append("Invalid hexadecimal digit: ");
            var12.append(var0);
            throw new IllegalArgumentException(var12.toString());
         } else {
            return var7;
         }
      }
   }

   public static String encodeHex(byte[] var0, boolean var1) {
      char[] var6 = new char[var0.length * 2];
      int var2 = 0;

      int var3;
      for(var3 = 0; var2 < var0.length; ++var2) {
         int var4 = var0[var2] & 255;
         if(var4 == 0 && var1) {
            break;
         }

         int var5 = var3 + 1;
         var6[var3] = FIRST_CHAR[var4];
         var3 = var5 + 1;
         var6[var5] = SECOND_CHAR[var4];
      }

      return new String(var6, 0, var3);
   }

   public static byte[] hexStringToByteArray(String var0) {
      return decodeHex(var0.replaceAll(" ", ""));
   }
}
