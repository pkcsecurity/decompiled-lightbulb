package com.facebook.common.util;


public class ExceptionWithNoStacktrace extends Exception {

   public ExceptionWithNoStacktrace(String var1) {
      super(var1);
   }

   public Throwable fillInStackTrace() {
      synchronized(this){}
      return this;
   }
}
