package com.google.android.gms.common.util;

import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
public final class HexDumpUtils {

   @KeepForSdk
   public static String dump(byte[] var0, int var1, int var2, boolean var3) {
      if(var0 != null && var0.length != 0 && var1 >= 0 && var2 > 0 && var1 + var2 <= var0.length) {
         byte var5 = 57;
         if(var3) {
            var5 = 75;
         }

         StringBuilder var10 = new StringBuilder(var5 * ((var2 + 16 - 1) / 16));
         int var11 = var2;
         int var7 = 0;

         int var9;
         for(int var8 = 0; var11 > 0; var11 = var9) {
            int var6;
            if(var7 == 0) {
               if(var2 < 65536) {
                  var10.append(String.format("%04X:", new Object[]{Integer.valueOf(var1)}));
               } else {
                  var10.append(String.format("%08X:", new Object[]{Integer.valueOf(var1)}));
               }

               var6 = var1;
            } else {
               var6 = var8;
               if(var7 == 8) {
                  var10.append(" -");
                  var6 = var8;
               }
            }

            var10.append(String.format(" %02X", new Object[]{Integer.valueOf(var0[var1] & 255)}));
            var9 = var11 - 1;
            ++var7;
            if(var3 && (var7 == 16 || var9 == 0)) {
               var8 = 16 - var7;
               if(var8 > 0) {
                  for(var11 = 0; var11 < var8; ++var11) {
                     var10.append("   ");
                  }
               }

               if(var8 >= 8) {
                  var10.append("  ");
               }

               var10.append("  ");

               for(var11 = 0; var11 < var7; ++var11) {
                  char var4 = (char)var0[var6 + var11];
                  if(var4 < 32 || var4 > 126) {
                     var4 = 46;
                  }

                  var10.append(var4);
               }
            }

            label80: {
               if(var7 != 16) {
                  var11 = var7;
                  if(var9 != 0) {
                     break label80;
                  }
               }

               var10.append('\n');
               var11 = 0;
            }

            ++var1;
            var7 = var11;
            var8 = var6;
         }

         return var10.toString();
      } else {
         return null;
      }
   }
}
