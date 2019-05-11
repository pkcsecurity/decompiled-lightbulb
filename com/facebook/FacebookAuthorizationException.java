package com.facebook;

import com.facebook.FacebookException;

public class FacebookAuthorizationException extends FacebookException {

   static final long serialVersionUID = 1L;


   public FacebookAuthorizationException() {}

   public FacebookAuthorizationException(String var1) {
      super(var1);
   }

   public FacebookAuthorizationException(String var1, Throwable var2) {
      super(var1, var2);
   }

   public FacebookAuthorizationException(Throwable var1) {
      super(var1);
   }
}
