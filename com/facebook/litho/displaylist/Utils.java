package com.facebook.litho.displaylist;

import com.facebook.litho.displaylist.DisplayListException;
import java.lang.reflect.Method;

class Utils {

   static Object safeInvoke(Method var0, Object var1, Object ... var2) throws DisplayListException {
      try {
         Object var4 = var0.invoke(var1, var2);
         return var4;
      } catch (Exception var3) {
         throw new DisplayListException(var3);
      }
   }
}
