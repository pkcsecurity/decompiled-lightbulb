package com.facebook;


public class FacebookException extends RuntimeException {

   static final long serialVersionUID = 1L;


   public FacebookException() {}

   public FacebookException(String var1) {
      super(var1);
   }

   public FacebookException(String var1, Throwable var2) {
      super(var1, var2);
   }

   public FacebookException(String var1, Object ... var2) {
      this(String.format(var1, var2));
   }

   public FacebookException(Throwable var1) {
      super(var1);
   }

   public String toString() {
      return this.getMessage();
   }
}
