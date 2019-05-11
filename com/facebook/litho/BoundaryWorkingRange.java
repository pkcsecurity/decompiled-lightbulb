package com.facebook.litho;

import com.facebook.litho.WorkingRange;

public class BoundaryWorkingRange implements WorkingRange {

   private static final int OFFSET = 1;
   private final int mOffset;


   public BoundaryWorkingRange() {
      this(1);
   }

   public BoundaryWorkingRange(int var1) {
      this.mOffset = var1;
   }

   private static boolean isInRange(int var0, int var1, int var2, int var3) {
      return var0 >= var1 - var3 && var0 <= var2 + var3;
   }

   public boolean shouldEnterRange(int var1, int var2, int var3, int var4, int var5) {
      return isInRange(var1, var2, var3, this.mOffset);
   }

   public boolean shouldExitRange(int var1, int var2, int var3, int var4, int var5) {
      return isInRange(var1, var2, var3, this.mOffset) ^ true;
   }
}
