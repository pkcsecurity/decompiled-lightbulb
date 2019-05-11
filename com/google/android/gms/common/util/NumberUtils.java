package com.google.android.gms.common.util;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.VisibleForTesting;

@KeepForSdk
@VisibleForTesting
public class NumberUtils {

   @KeepForSdk
   public static long parseHexLong(String var0) {
      if(var0.length() > 16) {
         StringBuilder var1 = new StringBuilder(String.valueOf(var0).length() + 37);
         var1.append("Invalid input: ");
         var1.append(var0);
         var1.append(" exceeds 16 characters");
         throw new NumberFormatException(var1.toString());
      } else {
         return var0.length() == 16?Long.parseLong(var0.substring(8), 16) | Long.parseLong(var0.substring(0, 8), 16) << 32:Long.parseLong(var0, 16);
      }
   }
}
