package com.facebook.react.uimanager;


public class ReactInvalidPropertyException extends RuntimeException {

   public ReactInvalidPropertyException(String var1, String var2, String var3) {
      StringBuilder var4 = new StringBuilder();
      var4.append("Invalid React property `");
      var4.append(var1);
      var4.append("` with value `");
      var4.append(var2);
      var4.append("`, expected ");
      var4.append(var3);
      super(var4.toString());
   }
}
