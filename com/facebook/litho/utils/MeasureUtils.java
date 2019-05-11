package com.facebook.litho.utils;

import android.view.View.MeasureSpec;
import com.facebook.litho.Size;
import com.facebook.litho.SizeSpec;

public final class MeasureUtils {

   private static final String TAG = "MeasureUtils";


   private static int getResultSizePxWithSpecAndDesiredPx(int var0, int var1) {
      int var2 = SizeSpec.getMode(var0);
      if(var2 != Integer.MIN_VALUE) {
         if(var2 != 0) {
            if(var2 != 1073741824) {
               throw new IllegalStateException("Unexpected size spec mode");
            } else {
               return SizeSpec.getSize(var0);
            }
         } else {
            return var1;
         }
      } else {
         return Math.min(SizeSpec.getSize(var0), var1);
      }
   }

   public static int getViewMeasureSpec(int var0) {
      int var1 = SizeSpec.getMode(var0);
      if(var1 != Integer.MIN_VALUE) {
         if(var1 != 0) {
            if(var1 != 1073741824) {
               throw new IllegalStateException("Unexpected size spec mode");
            } else {
               return MeasureSpec.makeMeasureSpec(SizeSpec.getSize(var0), 1073741824);
            }
         } else {
            return MeasureSpec.makeMeasureSpec(SizeSpec.getSize(var0), 0);
         }
      } else {
         return MeasureSpec.makeMeasureSpec(SizeSpec.getSize(var0), Integer.MIN_VALUE);
      }
   }

   public static void measureWithAspectRatio(int var0, int var1, float var2, Size var3) {
      int var4 = SizeSpec.getMode(var0);
      var0 = SizeSpec.getSize(var0);
      int var5 = SizeSpec.getMode(var1);
      var1 = SizeSpec.getSize(var1);
      int var6 = (int)Math.ceil((double)((float)var0 / var2));
      int var7 = (int)Math.ceil((double)((float)var1 * var2));
      if(var4 == 0 && var5 == 0) {
         var3.width = 0;
         var3.height = 0;
      } else if(var4 == Integer.MIN_VALUE && var5 == Integer.MIN_VALUE) {
         if(var6 > var1) {
            var3.width = var7;
            var3.height = var1;
         } else {
            var3.width = var0;
            var3.height = var6;
         }
      } else if(var4 == 1073741824) {
         var3.width = var0;
         if(var5 != 0 && var6 > var1) {
            var3.height = var1;
         } else {
            var3.height = var6;
         }
      } else if(var5 == 1073741824) {
         var3.height = var1;
         if(var4 != 0 && var7 > var0) {
            var3.width = var0;
         } else {
            var3.width = var7;
         }
      } else if(var4 == Integer.MIN_VALUE) {
         var3.width = var0;
         var3.height = var6;
      } else {
         if(var5 == Integer.MIN_VALUE) {
            var3.width = var7;
            var3.height = var1;
         }

      }
   }

   public static void measureWithAspectRatio(int var0, int var1, int var2, int var3, float var4, Size var5) {
      int var6 = var0;
      if(SizeSpec.getMode(var0) == Integer.MIN_VALUE) {
         var6 = var0;
         if(SizeSpec.getSize(var0) > var2) {
            var6 = SizeSpec.makeSizeSpec(var2, Integer.MIN_VALUE);
         }
      }

      var0 = var1;
      if(SizeSpec.getMode(var1) == Integer.MIN_VALUE) {
         var0 = var1;
         if(SizeSpec.getSize(var1) > var3) {
            var0 = SizeSpec.makeSizeSpec(var3, Integer.MIN_VALUE);
         }
      }

      measureWithAspectRatio(var6, var0, var4, var5);
   }

   public static void measureWithDesiredPx(int var0, int var1, int var2, int var3, Size var4) {
      var4.width = getResultSizePxWithSpecAndDesiredPx(var0, var2);
      var4.height = getResultSizePxWithSpecAndDesiredPx(var1, var3);
   }

   public static void measureWithEqualDimens(int var0, int var1, Size var2) {
      int var3 = SizeSpec.getMode(var0);
      var0 = SizeSpec.getSize(var0);
      int var4 = SizeSpec.getMode(var1);
      var1 = SizeSpec.getSize(var1);
      if(var3 == 0 && var4 == 0) {
         var2.width = 0;
         var2.height = 0;
      } else {
         if(var3 == 1073741824) {
            var2.width = var0;
            if(var4 == Integer.MIN_VALUE) {
               var2.height = Math.min(var0, var1);
               return;
            }

            if(var4 == 0) {
               var2.height = var0;
               return;
            }

            if(var4 == 1073741824) {
               var2.height = var1;
               return;
            }
         } else if(var3 == Integer.MIN_VALUE) {
            if(var4 == Integer.MIN_VALUE) {
               var0 = Math.min(var0, var1);
               var2.width = var0;
               var2.height = var0;
               return;
            }

            if(var4 == 0) {
               var2.width = var0;
               var2.height = var0;
               return;
            }

            if(var4 == 1073741824) {
               var2.height = var1;
               var2.width = Math.min(var0, var1);
               return;
            }
         }

         var2.height = var1;
         var2.width = var1;
      }
   }
}
