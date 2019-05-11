package com.facebook;

import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;

public class FacebookServiceException extends FacebookException {

   private static final long serialVersionUID = 1L;
   private final FacebookRequestError error;


   public FacebookServiceException(FacebookRequestError var1, String var2) {
      super(var2);
      this.error = var1;
   }

   public final FacebookRequestError getRequestError() {
      return this.error;
   }

   public final String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("{FacebookServiceException: ");
      var1.append("httpResponseCode: ");
      var1.append(this.error.getRequestStatusCode());
      var1.append(", facebookErrorCode: ");
      var1.append(this.error.getErrorCode());
      var1.append(", facebookErrorType: ");
      var1.append(this.error.getErrorType());
      var1.append(", message: ");
      var1.append(this.error.getErrorMessage());
      var1.append("}");
      return var1.toString();
   }
}
