package com.fasterxml.jackson.core.sym;

import com.fasterxml.jackson.core.sym.Name;

public final class Name2 extends Name {

   final int mQuad1;
   final int mQuad2;


   Name2(String var1, int var2, int var3, int var4) {
      super(var1, var2);
      this.mQuad1 = var3;
      this.mQuad2 = var4;
   }

   public boolean equals(int var1) {
      return false;
   }

   public boolean equals(int var1, int var2) {
      return var1 == this.mQuad1 && var2 == this.mQuad2;
   }

   public boolean equals(int[] var1, int var2) {
      return var2 == 2 && var1[0] == this.mQuad1 && var1[1] == this.mQuad2;
   }
}
