package com.facebook.litho;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import com.facebook.litho.SizeSpec;

public class DoubleMeasureFixUtil {

   public static int correctWidthSpecForAndroidDoubleMeasureBug(Resources var0, int var1) {
      int var2 = SizeSpec.getMode(var1);
      if(var2 == 0) {
         return var1;
      } else {
         Configuration var5 = var0.getConfiguration();
         DisplayMetrics var6 = var0.getDisplayMetrics();
         int var3 = var6.widthPixels;
         int var4 = (int)(var6.density * (float)var5.screenWidthDp + 0.5F);
         return var3 != var4 && var4 == SizeSpec.getSize(var1)?SizeSpec.makeSizeSpec(var3, var2):var1;
      }
   }
}
