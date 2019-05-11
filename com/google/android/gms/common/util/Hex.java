package com.google.android.gms.common.util;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ShowFirstParty;

@KeepForSdk
@ShowFirstParty
public class Hex {

   private static final char[] zzgw = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
   private static final char[] zzgx = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


   @KeepForSdk
   public static String bytesToStringUppercase(byte[] var0) {
      return bytesToStringUppercase(var0, false);
   }

   @KeepForSdk
   public static String bytesToStringUppercase(byte[] var0, boolean var1) {
      int var3 = var0.length;
      StringBuilder var4 = new StringBuilder(var3 << 1);

      for(int var2 = 0; var2 < var3 && (!var1 || var2 != var3 - 1 || (var0[var2] & 255) != 0); ++var2) {
         var4.append(zzgw[(var0[var2] & 240) >>> 4]);
         var4.append(zzgw[var0[var2] & 15]);
      }

      return var4.toString();
   }

   @KeepForSdk
   public static byte[] stringToBytes(String var0) throws IllegalArgumentException {
      int var3 = var0.length();
      if(var3 % 2 != 0) {
         throw new IllegalArgumentException("Hex string has odd number of characters");
      } else {
         byte[] var5 = new byte[var3 / 2];

         int var2;
         for(int var1 = 0; var1 < var3; var1 = var2) {
            int var4 = var1 / 2;
            var2 = var1 + 2;
            var5[var4] = (byte)Integer.parseInt(var0.substring(var1, var2), 16);
         }

         return var5;
      }
   }

   public static String zza(byte[] var0) {
      char[] var5 = new char[var0.length << 1];
      int var1 = 0;

      for(int var2 = 0; var1 < var0.length; ++var1) {
         int var3 = var0[var1] & 255;
         int var4 = var2 + 1;
         var5[var2] = zzgx[var3 >>> 4];
         var2 = var4 + 1;
         var5[var4] = zzgx[var3 & 15];
      }

      return new String(var5);
   }
}
