package com.facebook.common.logging;


public interface LoggingDelegate {

   void d(String var1, String var2);

   void d(String var1, String var2, Throwable var3);

   void e(String var1, String var2);

   void e(String var1, String var2, Throwable var3);

   int getMinimumLoggingLevel();

   void i(String var1, String var2);

   void i(String var1, String var2, Throwable var3);

   boolean isLoggable(int var1);

   void log(int var1, String var2, String var3);

   void setMinimumLoggingLevel(int var1);

   void v(String var1, String var2);

   void v(String var1, String var2, Throwable var3);

   void w(String var1, String var2);

   void w(String var1, String var2, Throwable var3);

   void wtf(String var1, String var2);

   void wtf(String var1, String var2, Throwable var3);
}
