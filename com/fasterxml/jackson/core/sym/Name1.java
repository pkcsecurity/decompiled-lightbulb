package com.fasterxml.jackson.core.sym;

import com.fasterxml.jackson.core.sym.Name;

public final class Name1 extends Name {

   static final Name1 sEmptyName = new Name1("", 0, 0);
   final int mQuad;


   Name1(String var1, int var2, int var3) {
      super(var1, var2);
      this.mQuad = var3;
   }

   static Name1 getEmptyName() {
      return sEmptyName;
   }

   public boolean equals(int var1) {
      return var1 == this.mQuad;
   }

   public boolean equals(int var1, int var2) {
      return var1 == this.mQuad && var2 == 0;
   }

   public boolean equals(int[] var1, int var2) {
      boolean var4 = false;
      boolean var3 = var4;
      if(var2 == 1) {
         var3 = var4;
         if(var1[0] == this.mQuad) {
            var3 = true;
         }
      }

      return var3;
   }
}
