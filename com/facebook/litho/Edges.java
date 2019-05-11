package com.facebook.litho;

import com.facebook.yoga.YogaConstants;
import com.facebook.yoga.YogaEdge;
import java.util.Arrays;

public class Edges {

   private static final int[] sFlagsMap = new int[]{1, 2, 4, 8, 16, 32, 64, 128, 256};
   private final float mDefaultValue = 0.0F;
   private final float[] mEdges = new float[]{Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN};
   private boolean mHasAliasesSet;
   private int mValueFlags = 0;


   private static boolean floatsEqual(float var0, float var1) {
      boolean var4 = Float.isNaN(var0);
      boolean var3 = false;
      boolean var2 = false;
      if(!var4 && !Float.isNaN(var1)) {
         if(Math.abs(var1 - var0) < 1.0E-5F) {
            var2 = true;
         }

         return var2;
      } else {
         var2 = var3;
         if(Float.isNaN(var0)) {
            var2 = var3;
            if(Float.isNaN(var1)) {
               var2 = true;
            }
         }

         return var2;
      }
   }

   public float get(YogaEdge var1) {
      float var2;
      if(var1 != YogaEdge.START && var1 != YogaEdge.END) {
         var2 = 0.0F;
      } else {
         var2 = Float.NaN;
      }

      if(this.mValueFlags == 0) {
         return var2;
      } else if((this.mValueFlags & sFlagsMap[var1.intValue()]) != 0) {
         return this.mEdges[var1.intValue()];
      } else {
         if(this.mHasAliasesSet) {
            if(var1 != YogaEdge.TOP && var1 != YogaEdge.BOTTOM) {
               var1 = YogaEdge.HORIZONTAL;
            } else {
               var1 = YogaEdge.VERTICAL;
            }

            if((this.mValueFlags & sFlagsMap[var1.intValue()]) != 0) {
               return this.mEdges[var1.intValue()];
            }

            if((this.mValueFlags & sFlagsMap[YogaEdge.ALL.intValue()]) != 0) {
               return this.mEdges[YogaEdge.ALL.intValue()];
            }
         }

         return var2;
      }
   }

   public float getRaw(YogaEdge var1) {
      return this.mEdges[var1.intValue()];
   }

   public void reset() {
      Arrays.fill(this.mEdges, Float.NaN);
      this.mHasAliasesSet = false;
      this.mValueFlags = 0;
   }

   public boolean set(YogaEdge var1, float var2) {
      boolean var5 = floatsEqual(this.mEdges[var1.intValue()], var2);
      boolean var4 = false;
      if(!var5) {
         this.mEdges[var1.intValue()] = var2;
         int var3;
         if(YogaConstants.isUndefined(var2)) {
            var3 = this.mValueFlags;
            this.mValueFlags = ~sFlagsMap[var1.intValue()] & var3;
         } else {
            var3 = this.mValueFlags;
            this.mValueFlags = sFlagsMap[var1.intValue()] | var3;
         }

         if((this.mValueFlags & sFlagsMap[YogaEdge.ALL.intValue()]) != 0 || (this.mValueFlags & sFlagsMap[YogaEdge.VERTICAL.intValue()]) != 0 || (this.mValueFlags & sFlagsMap[YogaEdge.HORIZONTAL.intValue()]) != 0) {
            var4 = true;
         }

         this.mHasAliasesSet = var4;
         return true;
      } else {
         return false;
      }
   }
}
