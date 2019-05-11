package com.facebook.jni;

import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.soloader.SoLoader;

@DoNotStrip
public class ThreadScopeSupport {

   static {
      SoLoader.loadLibrary("fb");
   }

   @DoNotStrip
   private static void runStdFunction(long var0) {
      runStdFunctionImpl(var0);
   }

   private static native void runStdFunctionImpl(long var0);
}
