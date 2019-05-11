package com.facebook.react.devsupport;

import com.facebook.proguard.annotations.DoNotStrip;

@DoNotStrip
public class JSException extends Exception {

   private final String mStack;


   public JSException(String var1, String var2) {
      super(var1);
      this.mStack = var2;
   }

   @DoNotStrip
   public JSException(String var1, String var2, Throwable var3) {
      super(var1, var3);
      this.mStack = var2;
   }

   public String getStack() {
      return this.mStack;
   }
}
