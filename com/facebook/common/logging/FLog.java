package com.facebook.common.logging;

import com.facebook.common.logging.FLogDefaultLoggingDelegate;
import com.facebook.common.logging.LoggingDelegate;
import java.util.Locale;

public class FLog {

   public static final int ASSERT = 7;
   public static final int DEBUG = 3;
   public static final int ERROR = 6;
   public static final int INFO = 4;
   public static final int VERBOSE = 2;
   public static final int WARN = 5;
   private static LoggingDelegate sHandler = FLogDefaultLoggingDelegate.getInstance();


   public static void d(Class<?> var0, String var1) {
      if(sHandler.isLoggable(3)) {
         sHandler.d(getTag(var0), var1);
      }

   }

   public static void d(Class<?> var0, String var1, Object var2) {
      if(sHandler.isLoggable(3)) {
         sHandler.d(getTag(var0), formatString(var1, new Object[]{var2}));
      }

   }

   public static void d(Class<?> var0, String var1, Object var2, Object var3) {
      if(sHandler.isLoggable(3)) {
         sHandler.d(getTag(var0), formatString(var1, new Object[]{var2, var3}));
      }

   }

   public static void d(Class<?> var0, String var1, Object var2, Object var3, Object var4) {
      if(sHandler.isLoggable(3)) {
         sHandler.d(getTag(var0), formatString(var1, new Object[]{var2, var3, var4}));
      }

   }

   public static void d(Class<?> var0, String var1, Object var2, Object var3, Object var4, Object var5) {
      if(sHandler.isLoggable(3)) {
         sHandler.d(getTag(var0), formatString(var1, new Object[]{var2, var3, var4, var5}));
      }

   }

   public static void d(Class<?> var0, String var1, Throwable var2) {
      if(sHandler.isLoggable(3)) {
         sHandler.d(getTag(var0), var1, var2);
      }

   }

   public static void d(Class<?> var0, String var1, Object ... var2) {
      if(sHandler.isLoggable(3)) {
         sHandler.d(getTag(var0), formatString(var1, var2));
      }

   }

   public static void d(Class<?> var0, Throwable var1, String var2, Object ... var3) {
      if(sHandler.isLoggable(3)) {
         sHandler.d(getTag(var0), formatString(var2, var3), var1);
      }

   }

   public static void d(String var0, String var1) {
      if(sHandler.isLoggable(3)) {
         sHandler.d(var0, var1);
      }

   }

   public static void d(String var0, String var1, Object var2) {
      if(sHandler.isLoggable(3)) {
         sHandler.d(var0, formatString(var1, new Object[]{var2}));
      }

   }

   public static void d(String var0, String var1, Object var2, Object var3) {
      if(sHandler.isLoggable(3)) {
         sHandler.d(var0, formatString(var1, new Object[]{var2, var3}));
      }

   }

   public static void d(String var0, String var1, Object var2, Object var3, Object var4) {
      if(sHandler.isLoggable(3)) {
         sHandler.d(var0, formatString(var1, new Object[]{var2, var3, var4}));
      }

   }

   public static void d(String var0, String var1, Object var2, Object var3, Object var4, Object var5) {
      if(sHandler.isLoggable(3)) {
         sHandler.d(var0, formatString(var1, new Object[]{var2, var3, var4, var5}));
      }

   }

   public static void d(String var0, String var1, Throwable var2) {
      if(sHandler.isLoggable(3)) {
         sHandler.d(var0, var1, var2);
      }

   }

   public static void d(String var0, String var1, Object ... var2) {
      if(sHandler.isLoggable(3)) {
         d(var0, formatString(var1, var2));
      }

   }

   public static void d(String var0, Throwable var1, String var2, Object ... var3) {
      if(sHandler.isLoggable(3)) {
         d(var0, formatString(var2, var3), var1);
      }

   }

   public static void e(Class<?> var0, String var1) {
      if(sHandler.isLoggable(6)) {
         sHandler.e(getTag(var0), var1);
      }

   }

   public static void e(Class<?> var0, String var1, Throwable var2) {
      if(sHandler.isLoggable(6)) {
         sHandler.e(getTag(var0), var1, var2);
      }

   }

   public static void e(Class<?> var0, String var1, Object ... var2) {
      if(sHandler.isLoggable(6)) {
         sHandler.e(getTag(var0), formatString(var1, var2));
      }

   }

   public static void e(Class<?> var0, Throwable var1, String var2, Object ... var3) {
      if(sHandler.isLoggable(6)) {
         sHandler.e(getTag(var0), formatString(var2, var3), var1);
      }

   }

   public static void e(String var0, String var1) {
      if(sHandler.isLoggable(6)) {
         sHandler.e(var0, var1);
      }

   }

   public static void e(String var0, String var1, Throwable var2) {
      if(sHandler.isLoggable(6)) {
         sHandler.e(var0, var1, var2);
      }

   }

   public static void e(String var0, String var1, Object ... var2) {
      if(sHandler.isLoggable(6)) {
         sHandler.e(var0, formatString(var1, var2));
      }

   }

   public static void e(String var0, Throwable var1, String var2, Object ... var3) {
      if(sHandler.isLoggable(6)) {
         sHandler.e(var0, formatString(var2, var3), var1);
      }

   }

   private static String formatString(String var0, Object ... var1) {
      return String.format((Locale)null, var0, var1);
   }

   public static int getMinimumLoggingLevel() {
      return sHandler.getMinimumLoggingLevel();
   }

   private static String getTag(Class<?> var0) {
      return var0.getSimpleName();
   }

   public static void i(Class<?> var0, String var1) {
      if(sHandler.isLoggable(4)) {
         sHandler.i(getTag(var0), var1);
      }

   }

   public static void i(Class<?> var0, String var1, Object var2) {
      if(sHandler.isLoggable(4)) {
         sHandler.i(getTag(var0), formatString(var1, new Object[]{var2}));
      }

   }

   public static void i(Class<?> var0, String var1, Object var2, Object var3) {
      if(sHandler.isLoggable(4)) {
         sHandler.i(getTag(var0), formatString(var1, new Object[]{var2, var3}));
      }

   }

   public static void i(Class<?> var0, String var1, Object var2, Object var3, Object var4) {
      if(sHandler.isLoggable(4)) {
         sHandler.i(getTag(var0), formatString(var1, new Object[]{var2, var3, var4}));
      }

   }

   public static void i(Class<?> var0, String var1, Object var2, Object var3, Object var4, Object var5) {
      if(sHandler.isLoggable(4)) {
         sHandler.i(getTag(var0), formatString(var1, new Object[]{var2, var3, var4, var5}));
      }

   }

   public static void i(Class<?> var0, String var1, Throwable var2) {
      if(sHandler.isLoggable(4)) {
         sHandler.i(getTag(var0), var1, var2);
      }

   }

   public static void i(Class<?> var0, String var1, Object ... var2) {
      if(sHandler.isLoggable(4)) {
         sHandler.i(getTag(var0), formatString(var1, var2));
      }

   }

   public static void i(Class<?> var0, Throwable var1, String var2, Object ... var3) {
      if(isLoggable(4)) {
         sHandler.i(getTag(var0), formatString(var2, var3), var1);
      }

   }

   public static void i(String var0, String var1) {
      if(sHandler.isLoggable(4)) {
         sHandler.i(var0, var1);
      }

   }

   public static void i(String var0, String var1, Object var2) {
      if(sHandler.isLoggable(4)) {
         sHandler.i(var0, formatString(var1, new Object[]{var2}));
      }

   }

   public static void i(String var0, String var1, Object var2, Object var3) {
      if(sHandler.isLoggable(4)) {
         sHandler.i(var0, formatString(var1, new Object[]{var2, var3}));
      }

   }

   public static void i(String var0, String var1, Object var2, Object var3, Object var4) {
      if(sHandler.isLoggable(4)) {
         sHandler.i(var0, formatString(var1, new Object[]{var2, var3, var4}));
      }

   }

   public static void i(String var0, String var1, Object var2, Object var3, Object var4, Object var5) {
      if(sHandler.isLoggable(4)) {
         sHandler.i(var0, formatString(var1, new Object[]{var2, var3, var4, var5}));
      }

   }

   public static void i(String var0, String var1, Throwable var2) {
      if(sHandler.isLoggable(4)) {
         sHandler.i(var0, var1, var2);
      }

   }

   public static void i(String var0, String var1, Object ... var2) {
      if(sHandler.isLoggable(4)) {
         sHandler.i(var0, formatString(var1, var2));
      }

   }

   public static void i(String var0, Throwable var1, String var2, Object ... var3) {
      if(sHandler.isLoggable(4)) {
         sHandler.i(var0, formatString(var2, var3), var1);
      }

   }

   public static boolean isLoggable(int var0) {
      return sHandler.isLoggable(var0);
   }

   public static void setLoggingDelegate(LoggingDelegate var0) {
      if(var0 == null) {
         throw new IllegalArgumentException();
      } else {
         sHandler = var0;
      }
   }

   public static void setMinimumLoggingLevel(int var0) {
      sHandler.setMinimumLoggingLevel(var0);
   }

   public static void v(Class<?> var0, String var1) {
      if(sHandler.isLoggable(2)) {
         sHandler.v(getTag(var0), var1);
      }

   }

   public static void v(Class<?> var0, String var1, Object var2) {
      if(sHandler.isLoggable(2)) {
         sHandler.v(getTag(var0), formatString(var1, new Object[]{var2}));
      }

   }

   public static void v(Class<?> var0, String var1, Object var2, Object var3) {
      if(sHandler.isLoggable(2)) {
         sHandler.v(getTag(var0), formatString(var1, new Object[]{var2, var3}));
      }

   }

   public static void v(Class<?> var0, String var1, Object var2, Object var3, Object var4) {
      if(isLoggable(2)) {
         v(var0, formatString(var1, new Object[]{var2, var3, var4}));
      }

   }

   public static void v(Class<?> var0, String var1, Object var2, Object var3, Object var4, Object var5) {
      if(sHandler.isLoggable(2)) {
         sHandler.v(getTag(var0), formatString(var1, new Object[]{var2, var3, var4, var5}));
      }

   }

   public static void v(Class<?> var0, String var1, Throwable var2) {
      if(sHandler.isLoggable(2)) {
         sHandler.v(getTag(var0), var1, var2);
      }

   }

   public static void v(Class<?> var0, String var1, Object ... var2) {
      if(sHandler.isLoggable(2)) {
         sHandler.v(getTag(var0), formatString(var1, var2));
      }

   }

   public static void v(Class<?> var0, Throwable var1, String var2, Object ... var3) {
      if(sHandler.isLoggable(2)) {
         sHandler.v(getTag(var0), formatString(var2, var3), var1);
      }

   }

   public static void v(String var0, String var1) {
      if(sHandler.isLoggable(2)) {
         sHandler.v(var0, var1);
      }

   }

   public static void v(String var0, String var1, Object var2) {
      if(sHandler.isLoggable(2)) {
         sHandler.v(var0, formatString(var1, new Object[]{var2}));
      }

   }

   public static void v(String var0, String var1, Object var2, Object var3) {
      if(sHandler.isLoggable(2)) {
         sHandler.v(var0, formatString(var1, new Object[]{var2, var3}));
      }

   }

   public static void v(String var0, String var1, Object var2, Object var3, Object var4) {
      if(sHandler.isLoggable(2)) {
         sHandler.v(var0, formatString(var1, new Object[]{var2, var3, var4}));
      }

   }

   public static void v(String var0, String var1, Object var2, Object var3, Object var4, Object var5) {
      if(sHandler.isLoggable(2)) {
         sHandler.v(var0, formatString(var1, new Object[]{var2, var3, var4, var5}));
      }

   }

   public static void v(String var0, String var1, Throwable var2) {
      if(sHandler.isLoggable(2)) {
         sHandler.v(var0, var1, var2);
      }

   }

   public static void v(String var0, String var1, Object ... var2) {
      if(sHandler.isLoggable(2)) {
         sHandler.v(var0, formatString(var1, var2));
      }

   }

   public static void v(String var0, Throwable var1, String var2, Object ... var3) {
      if(sHandler.isLoggable(2)) {
         sHandler.v(var0, formatString(var2, var3), var1);
      }

   }

   public static void w(Class<?> var0, String var1) {
      if(sHandler.isLoggable(5)) {
         sHandler.w(getTag(var0), var1);
      }

   }

   public static void w(Class<?> var0, String var1, Throwable var2) {
      if(sHandler.isLoggable(5)) {
         sHandler.w(getTag(var0), var1, var2);
      }

   }

   public static void w(Class<?> var0, String var1, Object ... var2) {
      if(sHandler.isLoggable(5)) {
         sHandler.w(getTag(var0), formatString(var1, var2));
      }

   }

   public static void w(Class<?> var0, Throwable var1, String var2, Object ... var3) {
      if(isLoggable(5)) {
         w(var0, formatString(var2, var3), var1);
      }

   }

   public static void w(String var0, String var1) {
      if(sHandler.isLoggable(5)) {
         sHandler.w(var0, var1);
      }

   }

   public static void w(String var0, String var1, Throwable var2) {
      if(sHandler.isLoggable(5)) {
         sHandler.w(var0, var1, var2);
      }

   }

   public static void w(String var0, String var1, Object ... var2) {
      if(sHandler.isLoggable(5)) {
         sHandler.w(var0, formatString(var1, var2));
      }

   }

   public static void w(String var0, Throwable var1, String var2, Object ... var3) {
      if(sHandler.isLoggable(5)) {
         sHandler.w(var0, formatString(var2, var3), var1);
      }

   }

   public static void wtf(Class<?> var0, String var1) {
      if(sHandler.isLoggable(6)) {
         sHandler.wtf(getTag(var0), var1);
      }

   }

   public static void wtf(Class<?> var0, String var1, Throwable var2) {
      if(sHandler.isLoggable(6)) {
         sHandler.wtf(getTag(var0), var1, var2);
      }

   }

   public static void wtf(Class<?> var0, String var1, Object ... var2) {
      if(sHandler.isLoggable(6)) {
         sHandler.wtf(getTag(var0), formatString(var1, var2));
      }

   }

   public static void wtf(Class<?> var0, Throwable var1, String var2, Object ... var3) {
      if(sHandler.isLoggable(6)) {
         sHandler.wtf(getTag(var0), formatString(var2, var3), var1);
      }

   }

   public static void wtf(String var0, String var1) {
      if(sHandler.isLoggable(6)) {
         sHandler.wtf(var0, var1);
      }

   }

   public static void wtf(String var0, String var1, Throwable var2) {
      if(sHandler.isLoggable(6)) {
         sHandler.wtf(var0, var1, var2);
      }

   }

   public static void wtf(String var0, String var1, Object ... var2) {
      if(sHandler.isLoggable(6)) {
         sHandler.wtf(var0, formatString(var1, var2));
      }

   }

   public static void wtf(String var0, Throwable var1, String var2, Object ... var3) {
      if(sHandler.isLoggable(6)) {
         sHandler.wtf(var0, formatString(var2, var3), var1);
      }

   }
}
