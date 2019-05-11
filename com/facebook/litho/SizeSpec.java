package com.facebook.litho;

import android.view.View.MeasureSpec;
import com.facebook.litho.FastMath;
import com.facebook.yoga.YogaMeasureMode;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SizeSpec {

   public static final int AT_MOST = Integer.MIN_VALUE;
   public static final int EXACTLY = 1073741824;
   public static final int UNSPECIFIED = 0;


   public static int getMode(int var0) {
      return MeasureSpec.getMode(var0);
   }

   public static int getSize(int var0) {
      return MeasureSpec.getSize(var0);
   }

   public static int makeSizeSpec(int var0, int var1) {
      return MeasureSpec.makeMeasureSpec(var0, var1);
   }

   public static int makeSizeSpecFromCssSpec(float var0, YogaMeasureMode var1) {
      switch(null.$SwitchMap$com$facebook$yoga$YogaMeasureMode[var1.ordinal()]) {
      case 1:
         return makeSizeSpec(FastMath.round(var0), 1073741824);
      case 2:
         return makeSizeSpec(0, 0);
      case 3:
         return makeSizeSpec(FastMath.round(var0), Integer.MIN_VALUE);
      default:
         StringBuilder var2 = new StringBuilder();
         var2.append("Unexpected YogaMeasureMode: ");
         var2.append(var1);
         throw new IllegalArgumentException(var2.toString());
      }
   }

   public static int resolveSize(int var0, int var1) {
      int var2 = getMode(var0);
      if(var2 != Integer.MIN_VALUE) {
         if(var2 != 0) {
            if(var2 != 1073741824) {
               StringBuilder var3 = new StringBuilder();
               var3.append("Unexpected size mode: ");
               var3.append(getMode(var0));
               throw new IllegalStateException(var3.toString());
            } else {
               return getSize(var0);
            }
         } else {
            return var1;
         }
      } else {
         return Math.min(getSize(var0), var1);
      }
   }

   public static String toString(int var0) {
      return MeasureSpec.toString(var0);
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface MeasureSpecMode {
   }
}
