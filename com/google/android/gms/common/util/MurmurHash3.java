package com.google.android.gms.common.util;

import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
public class MurmurHash3 {

   @KeepForSdk
   public static int murmurhash3_x86_32(byte[] var0, int var1, int var2, int var3) {
      int var4;
      int var5;
      for(var5 = (var2 & -4) + var1; var1 < var5; var1 += 4) {
         var4 = (var0[var1] & 255 | (var0[var1 + 1] & 255) << 8 | (var0[var1 + 2] & 255) << 16 | var0[var1 + 3] << 24) * -862048943;
         var3 ^= (var4 << 15 | var4 >>> 17) * 461845907;
         var3 = (var3 >>> 19 | var3 << 13) * 5 - 430675100;
      }

      var4 = 0;
      var1 = 0;
      switch(var2 & 3) {
      case 3:
         var1 = (var0[var5 + 2] & 255) << 16;
      case 2:
         var4 = var1 | (var0[var5 + 1] & 255) << 8;
      case 1:
         var1 = (var0[var5] & 255 | var4) * -862048943;
         var3 ^= (var1 >>> 17 | var1 << 15) * 461845907;
      default:
         var1 = var3 ^ var2;
         var1 = (var1 ^ var1 >>> 16) * -2048144789;
         var1 = (var1 ^ var1 >>> 13) * -1028477387;
         return var1 ^ var1 >>> 16;
      }
   }
}
