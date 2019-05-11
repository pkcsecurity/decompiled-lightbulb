package com.facebook;

import com.facebook.FacebookException;

public class FacebookOperationCanceledException extends FacebookException {

   static final long serialVersionUID = 1L;


   public FacebookOperationCanceledException() {}

   public FacebookOperationCanceledException(String var1) {
      super(var1);
   }

   public FacebookOperationCanceledException(String var1, Throwable var2) {
      super(var1, var2);
   }

   public FacebookOperationCanceledException(Throwable var1) {
      super(var1);
   }
}
