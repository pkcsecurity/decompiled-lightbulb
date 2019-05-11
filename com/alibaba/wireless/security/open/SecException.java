package com.alibaba.wireless.security.open;

import java.io.PrintStream;
import java.io.PrintWriter;

public class SecException extends Exception {

   public static final int ERROR_NULL_CONTEXT = -100;
   private int a;


   public SecException(int var1) {
      this.a = var1;
   }

   public SecException(String var1, int var2) {
      super(var1);
      this.a = var2;
   }

   public SecException(String var1, Throwable var2, int var3) {
      super(var1, var2);
      this.a = var3;
   }

   public SecException(Throwable var1, int var2) {
      super(var1);
      this.a = var2;
   }

   public int getErrorCode() {
      return this.a;
   }

   public void printStackTrace(PrintStream var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append("ErrorCode = ");
      var2.append(this.getErrorCode());
      var1.println(var2.toString());
      super.printStackTrace(var1);
   }

   public void printStackTrace(PrintWriter var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append("ErrorCode = ");
      var2.append(this.getErrorCode());
      var1.println(var2.toString());
      super.printStackTrace(var1);
   }

   public void setErrorCode(int var1) {
      this.a = var1;
   }
}
