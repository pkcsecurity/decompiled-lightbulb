package com.alibaba.wireless.security.framework;

import android.content.Context;

public class h {

   public static String a(ClassLoader var0, String var1) {
      String var2;
      if(var0 != null && var1 != null && !"".equals(var1)) {
         String var3 = a(var0, var1, true);
         var2 = var3;
         if(var3 == null) {
            return a(var0, var1, false);
         }
      } else {
         var2 = null;
      }

      return var2;
   }

   private static String a(ClassLoader param0, String param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   public static boolean a(Context param0) {
      // $FF: Couldn't be decompiled
   }
}
