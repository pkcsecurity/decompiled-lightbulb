package com.facebook.litho;

import com.facebook.litho.SizeSpec;

public class MeasureComparisonUtils {

   private static final float DELTA = 0.5F;


   public static boolean isMeasureSpecCompatible(int var0, int var1, int var2) {
      int var4 = SizeSpec.getMode(var1);
      int var5 = SizeSpec.getSize(var1);
      int var6 = SizeSpec.getMode(var0);
      int var7 = SizeSpec.getSize(var0);
      if(var0 != var1 && (var6 != 0 || var4 != 0)) {
         float var3 = (float)var2;
         if(!newSizeIsExactAndMatchesOldMeasuredSize(var4, var5, var3) && !oldSizeIsUnspecifiedAndStillFits(var6, var4, var5, var3) && !newMeasureSizeIsStricterAndStillValid(var6, var4, var7, var5, var3)) {
            return false;
         }
      }

      return true;
   }

   private static boolean newMeasureSizeIsStricterAndStillValid(int var0, int var1, int var2, int var3, float var4) {
      return var0 == Integer.MIN_VALUE && var1 == Integer.MIN_VALUE && var2 > var3 && var4 <= (float)var3;
   }

   private static boolean newSizeIsExactAndMatchesOldMeasuredSize(int var0, int var1, float var2) {
      return var0 == 1073741824 && Math.abs((float)var1 - var2) < 0.5F;
   }

   private static boolean oldSizeIsUnspecifiedAndStillFits(int var0, int var1, int var2, float var3) {
      return var1 == Integer.MIN_VALUE && var0 == 0 && (float)var2 >= var3;
   }
}
