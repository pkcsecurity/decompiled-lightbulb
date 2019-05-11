package com.facebook.react.bridge;

import com.facebook.react.bridge.NativeModuleCallExceptionHandler;

public class DefaultNativeModuleCallExceptionHandler implements NativeModuleCallExceptionHandler {

   public void handleException(Exception var1) {
      if(var1 instanceof RuntimeException) {
         throw (RuntimeException)var1;
      } else {
         throw new RuntimeException(var1);
      }
   }
}
