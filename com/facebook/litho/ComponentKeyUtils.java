package com.facebook.litho;


public class ComponentKeyUtils {

   public static String getKeyForChildPosition(String var0, int var1) {
      StringBuilder var2 = new StringBuilder(var0.length() + 4);
      var2.append(var0);
      var2.append('!');
      var2.append(var1);
      return var2.toString();
   }

   public static String getKeyWithSeparator(String var0, String var1) {
      StringBuilder var2 = new StringBuilder(var0.length() + var1.length() + 1);
      var2.append(var0);
      var2.append(',');
      var2.append(var1);
      return var2.toString();
   }

   public static String getKeyWithSeparator(Object ... var0) {
      StringBuilder var2 = new StringBuilder();
      var2.append(var0[0]);

      for(int var1 = 1; var1 < var0.length; ++var1) {
         var2.append(',');
         var2.append(var0[var1]);
      }

      return var2.toString();
   }
}
