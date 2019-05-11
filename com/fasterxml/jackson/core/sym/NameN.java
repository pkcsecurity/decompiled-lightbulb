package com.fasterxml.jackson.core.sym;

import com.fasterxml.jackson.core.sym.Name;

public final class NameN extends Name {

   final int mQuadLen;
   final int[] mQuads;


   NameN(String var1, int var2, int[] var3, int var4) {
      super(var1, var2);
      if(var4 < 3) {
         throw new IllegalArgumentException("Qlen must >= 3");
      } else {
         this.mQuads = var3;
         this.mQuadLen = var4;
      }
   }

   public boolean equals(int var1) {
      return false;
   }

   public boolean equals(int var1, int var2) {
      return false;
   }

   public boolean equals(int[] var1, int var2) {
      if(var2 != this.mQuadLen) {
         return false;
      } else {
         for(int var3 = 0; var3 < var2; ++var3) {
            if(var1[var3] != this.mQuads[var3]) {
               return false;
            }
         }

         return true;
      }
   }
}
