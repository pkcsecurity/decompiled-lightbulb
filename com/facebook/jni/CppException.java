package com.facebook.jni;

import com.facebook.proguard.annotations.DoNotStrip;

@DoNotStrip
public class CppException extends RuntimeException {

   @DoNotStrip
   public CppException(String var1) {
      super(var1);
   }
}
