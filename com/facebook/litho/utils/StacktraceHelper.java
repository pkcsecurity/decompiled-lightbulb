package com.facebook.litho.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.annotation.Nullable;

public final class StacktraceHelper {

   @Nullable
   public static String formatStacktrace(Throwable var0) {
      StringWriter var1 = new StringWriter();
      PrintWriter var2 = new PrintWriter(var1);
      boolean var7 = false;

      try {
         var7 = true;
         var0.printStackTrace(var2);
         var7 = false;
      } finally {
         if(var7) {
            var2.close();

            try {
               var1.toString();
               var1.close();
            } catch (IOException var8) {
               ;
            }

         }
      }

      var2.close();

      String var12;
      try {
         var12 = var1.toString();
      } catch (IOException var10) {
         return null;
      }

      try {
         var1.close();
         return var12;
      } catch (IOException var9) {
         return var12;
      }
   }
}
