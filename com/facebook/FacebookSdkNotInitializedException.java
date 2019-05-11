package com.facebook;

import com.facebook.FacebookException;

public class FacebookSdkNotInitializedException extends FacebookException {

   static final long serialVersionUID = 1L;


   public FacebookSdkNotInitializedException() {}

   public FacebookSdkNotInitializedException(String var1) {
      super(var1);
   }

   public FacebookSdkNotInitializedException(String var1, Throwable var2) {
      super(var1, var2);
   }

   public FacebookSdkNotInitializedException(Throwable var1) {
      super(var1);
   }
}
