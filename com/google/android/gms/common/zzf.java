package com.google.android.gms.common;

import com.google.android.gms.common.zze;
import java.util.Arrays;

final class zzf extends zze {

   private final byte[] zzu;


   zzf(byte[] var1) {
      super(Arrays.copyOfRange(var1, 0, 25));
      this.zzu = var1;
   }

   final byte[] getBytes() {
      return this.zzu;
   }
}
