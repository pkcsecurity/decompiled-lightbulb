package com.facebook.react.uimanager;


public class FloatUtil {

   private static final float EPSILON = 1.0E-5F;


   public static boolean floatsEqual(float var0, float var1) {
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
}
