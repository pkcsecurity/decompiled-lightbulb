package com.facebook.jni;

import java.lang.Thread.UncaughtExceptionHandler;

public class JniTerminateHandler {

   public static void handleTerminate(Throwable var0) throws Throwable {
      UncaughtExceptionHandler var1 = Thread.getDefaultUncaughtExceptionHandler();
      if(var1 != null) {
         var1.uncaughtException(Thread.currentThread(), var0);
      }
   }
}
