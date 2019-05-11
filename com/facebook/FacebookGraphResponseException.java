package com.facebook;

import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.GraphResponse;

public class FacebookGraphResponseException extends FacebookException {

   private final GraphResponse graphResponse;


   public FacebookGraphResponseException(GraphResponse var1, String var2) {
      super(var2);
      this.graphResponse = var1;
   }

   public final GraphResponse getGraphResponse() {
      return this.graphResponse;
   }

   public final String toString() {
      FacebookRequestError var1;
      if(this.graphResponse != null) {
         var1 = this.graphResponse.getError();
      } else {
         var1 = null;
      }

      StringBuilder var2 = new StringBuilder();
      var2.append("{FacebookGraphResponseException: ");
      String var3 = this.getMessage();
      if(var3 != null) {
         var2.append(var3);
         var2.append(" ");
      }

      if(var1 != null) {
         var2.append("httpResponseCode: ");
         var2.append(var1.getRequestStatusCode());
         var2.append(", facebookErrorCode: ");
         var2.append(var1.getErrorCode());
         var2.append(", facebookErrorType: ");
         var2.append(var1.getErrorType());
         var2.append(", message: ");
         var2.append(var1.getErrorMessage());
         var2.append("}");
      }

      return var2.toString();
   }
}
