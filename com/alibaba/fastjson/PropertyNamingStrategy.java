package com.alibaba.fastjson;


public enum PropertyNamingStrategy {

   // $FF: synthetic field
   private static final PropertyNamingStrategy[] $VALUES = new PropertyNamingStrategy[]{CamelCase, PascalCase, SnakeCase, KebabCase};
   CamelCase("CamelCase", 0),
   KebabCase("KebabCase", 3),
   PascalCase("PascalCase", 1),
   SnakeCase("SnakeCase", 2);


   private PropertyNamingStrategy(String var1, int var2) {}

   public String translate(String var1) {
      int var5 = null.$SwitchMap$com$alibaba$fastjson$PropertyNamingStrategy[this.ordinal()];
      byte var4 = 0;
      int var3 = 0;
      char var2;
      StringBuilder var6;
      char[] var7;
      char var8;
      switch(var5) {
      case 1:
         var6 = new StringBuilder();
         var3 = var4;

         for(; var3 < var1.length(); ++var3) {
            var2 = var1.charAt(var3);
            if(var2 >= 65 && var2 <= 90) {
               var2 = (char)(var2 + 32);
               if(var3 > 0) {
                  var6.append('_');
               }

               var6.append(var2);
            } else {
               var6.append(var2);
            }
         }

         return var6.toString();
      case 2:
         for(var6 = new StringBuilder(); var3 < var1.length(); ++var3) {
            var2 = var1.charAt(var3);
            if(var2 >= 65 && var2 <= 90) {
               var2 = (char)(var2 + 32);
               if(var3 > 0) {
                  var6.append('-');
               }

               var6.append(var2);
            } else {
               var6.append(var2);
            }
         }

         return var6.toString();
      case 3:
         var8 = var1.charAt(0);
         if(var8 >= 97 && var8 <= 122) {
            var7 = var1.toCharArray();
            var7[0] = (char)(var7[0] - 32);
            return new String(var7);
         }

         return var1;
      case 4:
         var8 = var1.charAt(0);
         if(var8 >= 65 && var8 <= 90) {
            var7 = var1.toCharArray();
            var7[0] = (char)(var7[0] + 32);
            return new String(var7);
         }

         return var1;
      default:
         return var1;
      }
   }
}
