package com.facebook;

import com.facebook.FacebookException;

public class FacebookDialogException extends FacebookException {

   static final long serialVersionUID = 1L;
   private int errorCode;
   private String failingUrl;


   public FacebookDialogException(String var1, int var2, String var3) {
      super(var1);
      this.errorCode = var2;
      this.failingUrl = var3;
   }

   public int getErrorCode() {
      return this.errorCode;
   }

   public String getFailingUrl() {
      return this.failingUrl;
   }

   public final String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("{FacebookDialogException: ");
      var1.append("errorCode: ");
      var1.append(this.getErrorCode());
      var1.append(", message: ");
      var1.append(this.getMessage());
      var1.append(", url: ");
      var1.append(this.getFailingUrl());
      var1.append("}");
      return var1.toString();
   }
}
