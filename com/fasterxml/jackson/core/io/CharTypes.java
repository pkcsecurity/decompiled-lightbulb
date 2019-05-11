package com.fasterxml.jackson.core.io;

import java.util.Arrays;

public final class CharTypes {

   private static final byte[] HEX_BYTES;
   private static final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();
   static final int[] sHexValues;
   static final int[] sInputCodes;
   static final int[] sInputCodesComment;
   static final int[] sInputCodesJsNames;
   static final int[] sInputCodesUtf8;
   static final int[] sInputCodesUtf8JsNames;
   static final int[] sOutputEscapes128;


   static {
      int var1 = HEX_CHARS.length;
      HEX_BYTES = new byte[var1];
      byte var2 = 0;

      int var0;
      for(var0 = 0; var0 < var1; ++var0) {
         HEX_BYTES[var0] = (byte)HEX_CHARS[var0];
      }

      int[] var3 = new int[256];

      for(var0 = 0; var0 < 32; ++var0) {
         var3[var0] = -1;
      }

      var3[34] = 1;
      var3[92] = 1;
      sInputCodes = var3;
      var3 = new int[sInputCodes.length];
      System.arraycopy(sInputCodes, 0, var3, 0, sInputCodes.length);

      for(var1 = 128; var1 < 256; ++var1) {
         byte var4;
         if((var1 & 224) == 192) {
            var4 = 2;
         } else if((var1 & 240) == 224) {
            var4 = 3;
         } else if((var1 & 248) == 240) {
            var4 = 4;
         } else {
            var4 = -1;
         }

         var3[var1] = var4;
      }

      sInputCodesUtf8 = var3;
      var3 = new int[256];
      Arrays.fill(var3, -1);

      for(var0 = 33; var0 < 256; ++var0) {
         if(Character.isJavaIdentifierPart((char)var0)) {
            var3[var0] = 0;
         }
      }

      var3[64] = 0;
      var3[35] = 0;
      var3[42] = 0;
      var3[45] = 0;
      var3[43] = 0;
      sInputCodesJsNames = var3;
      var3 = new int[256];
      System.arraycopy(sInputCodesJsNames, 0, var3, 0, sInputCodesJsNames.length);
      Arrays.fill(var3, 128, 128, 0);
      sInputCodesUtf8JsNames = var3;
      sInputCodesComment = new int[256];
      System.arraycopy(sInputCodesUtf8, 128, sInputCodesComment, 128, 128);
      Arrays.fill(sInputCodesComment, 0, 32, -1);
      sInputCodesComment[9] = 0;
      sInputCodesComment[10] = 10;
      sInputCodesComment[13] = 13;
      sInputCodesComment[42] = 42;
      var3 = new int[128];

      for(var0 = 0; var0 < 32; ++var0) {
         var3[var0] = -1;
      }

      var3[34] = 34;
      var3[92] = 92;
      var3[8] = 98;
      var3[9] = 116;
      var3[12] = 102;
      var3[10] = 110;
      var3[13] = 114;
      sOutputEscapes128 = var3;
      sHexValues = new int[128];
      Arrays.fill(sHexValues, -1);
      var1 = 0;

      while(true) {
         var0 = var2;
         if(var1 >= 10) {
            while(var0 < 6) {
               var3 = sHexValues;
               var1 = var0 + 10;
               var3[var0 + 97] = var1;
               sHexValues[var0 + 65] = var1;
               ++var0;
            }

            return;
         }

         sHexValues[var1 + 48] = var1++;
      }
   }

   public static void appendQuoted(StringBuilder var0, String var1) {
      int[] var7 = sOutputEscapes128;
      int var4 = var7.length;
      int var5 = var1.length();

      for(int var3 = 0; var3 < var5; ++var3) {
         char var2 = var1.charAt(var3);
         if(var2 < var4 && var7[var2] != 0) {
            var0.append('\\');
            int var6 = var7[var2];
            if(var6 < 0) {
               var0.append('u');
               var0.append('0');
               var0.append('0');
               var0.append(HEX_CHARS[var2 >> 4]);
               var0.append(HEX_CHARS[var2 & 15]);
            } else {
               var0.append((char)var6);
            }
         } else {
            var0.append(var2);
         }
      }

   }

   public static int charToHex(int var0) {
      return var0 > 127?-1:sHexValues[var0];
   }

   public static byte[] copyHexBytes() {
      return (byte[])HEX_BYTES.clone();
   }

   public static char[] copyHexChars() {
      return (char[])HEX_CHARS.clone();
   }

   public static int[] get7BitOutputEscapes() {
      return sOutputEscapes128;
   }

   public static int[] getInputCodeComment() {
      return sInputCodesComment;
   }

   public static int[] getInputCodeLatin1() {
      return sInputCodes;
   }

   public static int[] getInputCodeLatin1JsNames() {
      return sInputCodesJsNames;
   }

   public static int[] getInputCodeUtf8() {
      return sInputCodesUtf8;
   }

   public static int[] getInputCodeUtf8JsNames() {
      return sInputCodesUtf8JsNames;
   }
}
