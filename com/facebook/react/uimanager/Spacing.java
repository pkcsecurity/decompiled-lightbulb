package com.facebook.react.uimanager;

import com.facebook.react.uimanager.FloatUtil;
import com.facebook.yoga.YogaConstants;
import java.util.Arrays;

public class Spacing {

   public static final int ALL = 8;
   public static final int BOTTOM = 3;
   public static final int END = 5;
   public static final int HORIZONTAL = 6;
   public static final int LEFT = 0;
   public static final int RIGHT = 2;
   public static final int START = 4;
   public static final int TOP = 1;
   public static final int VERTICAL = 7;
   private static final int[] sFlagsMap = new int[]{1, 2, 4, 8, 16, 32, 64, 128, 256};
   private float mDefaultValue;
   private boolean mHasAliasesSet;
   private final float[] mSpacing;
   private int mValueFlags;


   public Spacing() {
      this(0.0F);
   }

   public Spacing(float var1) {
      this.mSpacing = newFullSpacingArray();
      this.mValueFlags = 0;
      this.mDefaultValue = var1;
   }

   private static float[] newFullSpacingArray() {
      return new float[]{Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN};
   }

   public float get(int var1) {
      float var2;
      if(var1 != 4 && var1 != 5) {
         var2 = this.mDefaultValue;
      } else {
         var2 = Float.NaN;
      }

      if(this.mValueFlags == 0) {
         return var2;
      } else if((this.mValueFlags & sFlagsMap[var1]) != 0) {
         return this.mSpacing[var1];
      } else {
         if(this.mHasAliasesSet) {
            byte var3;
            if(var1 != 1 && var1 != 3) {
               var3 = 6;
            } else {
               var3 = 7;
            }

            if((this.mValueFlags & sFlagsMap[var3]) != 0) {
               return this.mSpacing[var3];
            }

            if((this.mValueFlags & sFlagsMap[8]) != 0) {
               return this.mSpacing[8];
            }
         }

         return var2;
      }
   }

   public float getRaw(int var1) {
      return this.mSpacing[var1];
   }

   float getWithFallback(int var1, int var2) {
      return (this.mValueFlags & sFlagsMap[var1]) != 0?this.mSpacing[var1]:this.get(var2);
   }

   public void reset() {
      Arrays.fill(this.mSpacing, Float.NaN);
      this.mHasAliasesSet = false;
      this.mValueFlags = 0;
   }

   public boolean set(int var1, float var2) {
      boolean var5 = FloatUtil.floatsEqual(this.mSpacing[var1], var2);
      boolean var4 = false;
      if(!var5) {
         this.mSpacing[var1] = var2;
         int var3;
         if(YogaConstants.isUndefined(var2)) {
            var3 = this.mValueFlags;
            this.mValueFlags = ~sFlagsMap[var1] & var3;
         } else {
            var3 = this.mValueFlags;
            this.mValueFlags = sFlagsMap[var1] | var3;
         }

         if((this.mValueFlags & sFlagsMap[8]) != 0 || (this.mValueFlags & sFlagsMap[7]) != 0 || (this.mValueFlags & sFlagsMap[6]) != 0) {
            var4 = true;
         }

         this.mHasAliasesSet = var4;
         return true;
      } else {
         return false;
      }
   }
}
