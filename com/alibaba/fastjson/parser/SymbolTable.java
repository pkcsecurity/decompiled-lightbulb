package com.alibaba.fastjson.parser;


public class SymbolTable {

   private final int indexMask;
   private final SymbolTable.Entry[] symbols;


   public SymbolTable(int var1) {
      this.indexMask = var1 - 1;
      this.symbols = new SymbolTable.Entry[var1];
      this.addSymbol("$ref", 0, 4, "$ref".hashCode());
      this.addSymbol("@type", 0, "@type".length(), "@type".hashCode());
   }

   private static String subString(String var0, int var1, int var2) {
      char[] var3 = new char[var2];
      var0.getChars(var1, var2 + var1, var3, 0);
      return new String(var3);
   }

   public String addSymbol(String var1, int var2, int var3, int var4) {
      int var5 = this.indexMask & var4;
      SymbolTable.Entry var6 = this.symbols[var5];
      if(var6 != null) {
         return var4 == var6.hashCode && var3 == var6.chars.length && var1.regionMatches(var2, var6.value, 0, var3)?var6.value:subString(var1, var2, var3);
      } else {
         if(var3 != var1.length()) {
            var1 = subString(var1, var2, var3);
         }

         var1 = var1.intern();
         this.symbols[var5] = new SymbolTable.Entry(var1, var4);
         return var1;
      }
   }

   public String addSymbol(char[] var1, int var2, int var3, int var4) {
      int var5 = this.indexMask & var4;
      SymbolTable.Entry var8 = this.symbols[var5];
      if(var8 == null) {
         String var9 = (new String(var1, var2, var3)).intern();
         var8 = new SymbolTable.Entry(var9, var4);
         this.symbols[var5] = var8;
         return var9;
      } else {
         int var7 = var8.hashCode;
         boolean var6 = false;
         boolean var10 = var6;
         if(var4 == var7) {
            var10 = var6;
            if(var3 == var8.chars.length) {
               var4 = 0;

               while(true) {
                  if(var4 >= var3) {
                     var10 = true;
                     break;
                  }

                  if(var1[var2 + var4] != var8.chars[var4]) {
                     var10 = var6;
                     break;
                  }

                  ++var4;
               }
            }
         }

         return var10?var8.value:new String(var1, var2, var3);
      }
   }

   static class Entry {

      final char[] chars;
      final int hashCode;
      final String value;


      Entry(String var1, int var2) {
         this.value = var1;
         this.chars = var1.toCharArray();
         this.hashCode = var2;
      }
   }
}
