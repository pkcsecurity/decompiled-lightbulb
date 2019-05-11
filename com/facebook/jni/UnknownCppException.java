package com.facebook.jni;

import com.facebook.jni.CppException;
import com.facebook.proguard.annotations.DoNotStrip;

@DoNotStrip
public class UnknownCppException extends CppException {

   @DoNotStrip
   public UnknownCppException() {
      super("Unknown");
   }

   @DoNotStrip
   public UnknownCppException(String var1) {
      super(var1);
   }
}
