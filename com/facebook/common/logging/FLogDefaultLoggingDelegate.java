package com.facebook.common.logging;

import android.util.Log;
import com.facebook.common.logging.LoggingDelegate;
import java.io.PrintWriter;
import java.io.StringWriter;

public class FLogDefaultLoggingDelegate implements LoggingDelegate {

   public static final FLogDefaultLoggingDelegate sInstance = new FLogDefaultLoggingDelegate();
   private String mApplicationTag = "unknown";
   private int mMinimumLoggingLevel = 5;


   public static FLogDefaultLoggingDelegate getInstance() {
      return sInstance;
   }

   private static String getMsg(String var0, Throwable var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(var0);
      var2.append('\n');
      var2.append(getStackTraceString(var1));
      return var2.toString();
   }

   private static String getStackTraceString(Throwable var0) {
      if(var0 == null) {
         return "";
      } else {
         StringWriter var1 = new StringWriter();
         var0.printStackTrace(new PrintWriter(var1));
         return var1.toString();
      }
   }

   private String prefixTag(String var1) {
      if(this.mApplicationTag != null) {
         StringBuilder var2 = new StringBuilder();
         var2.append(this.mApplicationTag);
         var2.append(":");
         var2.append(var1);
         return var2.toString();
      } else {
         return var1;
      }
   }

   private void println(int var1, String var2, String var3) {
      Log.println(var1, this.prefixTag(var2), var3);
   }

   private void println(int var1, String var2, String var3, Throwable var4) {
      Log.println(var1, this.prefixTag(var2), getMsg(var3, var4));
   }

   public void d(String var1, String var2) {
      this.println(3, var1, var2);
   }

   public void d(String var1, String var2, Throwable var3) {
      this.println(3, var1, var2, var3);
   }

   public void e(String var1, String var2) {
      this.println(6, var1, var2);
   }

   public void e(String var1, String var2, Throwable var3) {
      this.println(6, var1, var2, var3);
   }

   public int getMinimumLoggingLevel() {
      return this.mMinimumLoggingLevel;
   }

   public void i(String var1, String var2) {
      this.println(4, var1, var2);
   }

   public void i(String var1, String var2, Throwable var3) {
      this.println(4, var1, var2, var3);
   }

   public boolean isLoggable(int var1) {
      return this.mMinimumLoggingLevel <= var1;
   }

   public void log(int var1, String var2, String var3) {
      this.println(var1, var2, var3);
   }

   public void setApplicationTag(String var1) {
      this.mApplicationTag = var1;
   }

   public void setMinimumLoggingLevel(int var1) {
      this.mMinimumLoggingLevel = var1;
   }

   public void v(String var1, String var2) {
      this.println(2, var1, var2);
   }

   public void v(String var1, String var2, Throwable var3) {
      this.println(2, var1, var2, var3);
   }

   public void w(String var1, String var2) {
      this.println(5, var1, var2);
   }

   public void w(String var1, String var2, Throwable var3) {
      this.println(5, var1, var2, var3);
   }

   public void wtf(String var1, String var2) {
      this.println(6, var1, var2);
   }

   public void wtf(String var1, String var2, Throwable var3) {
      this.println(6, var1, var2, var3);
   }
}
