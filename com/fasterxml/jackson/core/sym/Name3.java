package com.fasterxml.jackson.core.sym;

import com.fasterxml.jackson.core.sym.Name;

public final class Name3 extends Name {

   final int mQuad1;
   final int mQuad2;
   final int mQuad3;


   Name3(String var1, int var2, int var3, int var4, int var5) {
      super(var1, var2);
      this.mQuad1 = var3;
      this.mQuad2 = var4;
      this.mQuad3 = var5;
   }

   public boolean equals(int var1) {
      return false;
   }

   public boolean equals(int var1, int var2) {
      return false;
   }

   public boolean equals(int[] var1, int var2) {
      return var2 == 3 && var1[0] == this.mQuad1 && var1[1] == this.mQuad2 && var1[2] == this.mQuad3;
   }
}
