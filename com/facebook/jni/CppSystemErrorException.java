package com.facebook.jni;

import com.facebook.jni.CppException;
import com.facebook.proguard.annotations.DoNotStrip;

@DoNotStrip
public class CppSystemErrorException extends CppException {

   int errorCode;


   @DoNotStrip
   public CppSystemErrorException(String var1, int var2) {
      super(var1);
      this.errorCode = var2;
   }

   public int getErrorCode() {
      return this.errorCode;
   }
}
