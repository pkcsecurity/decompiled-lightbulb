package com.facebook.imagepipeline.animated.impl;

import android.os.SystemClock;

class RollingStat {

   private static final int WINDOWS = 60;
   private final short[] mStat = new short[60];


   int getSum(int var1) {
      long var8 = SystemClock.uptimeMillis() / 1000L;
      int var5 = (int)((var8 - (long)var1) % 60L);
      int var6 = (int)(var8 / 60L & 255L);
      int var2 = 0;

      int var3;
      int var4;
      for(var3 = 0; var2 < var1; var3 = var4) {
         short var7 = this.mStat[(var5 + var2) % 60];
         var4 = var3;
         if((var7 >> 8 & 255) == var6) {
            var4 = var3 + (var7 & 255);
         }

         ++var2;
      }

      return var3;
   }

   void incrementStats(int var1) {
      long var5 = SystemClock.uptimeMillis() / 1000L;
      int var2 = (int)(var5 % 60L);
      int var3 = (int)(var5 / 60L & 255L);
      short var4 = this.mStat[var2];
      if(var3 == (var4 >> 8 & 255)) {
         var1 += var4 & 255;
      }

      this.mStat[var2] = (short)(var1 | var3 << 8);
   }
}
