package com.tuya.smart.api.logger;


public interface ILogger {

   void d(String var1, String var2);

   void e(String var1, String var2);

   void e(String var1, String var2, Throwable var3);

   void i(String var1, String var2);

   void w(String var1, String var2);

   void w(String var1, String var2, Throwable var3);
}
