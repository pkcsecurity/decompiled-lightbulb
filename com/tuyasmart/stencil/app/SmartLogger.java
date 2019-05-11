package com.tuyasmart.stencil.app;

import android.util.Log;
import com.tuya.smart.android.common.utils.L;
import com.tuya.smart.api.logger.ILogger;

public class SmartLogger implements ILogger {

   public void d(String var1, String var2) {
      L.d(var1, var2);
   }

   public void e(String var1, String var2) {
      L.e(var1, var2);
   }

   public void e(String var1, String var2, Throwable var3) {
      StringBuilder var4 = new StringBuilder();
      var4.append(var2);
      var4.append("\n");
      var4.append(Log.getStackTraceString(var3));
      L.e(var1, var4.toString());
   }

   public void i(String var1, String var2) {
      L.i(var1, var2);
   }

   public void v(String var1, String var2) {
      L.v(var1, var2);
   }

   public void w(String var1, String var2) {
      L.d(var1, var2);
   }

   public void w(String var1, String var2, Throwable var3) {
      StringBuilder var4 = new StringBuilder();
      var4.append(var2);
      var4.append("\n");
      var4.append(Log.getStackTraceString(var3));
      L.w(var1, var4.toString());
   }
}
