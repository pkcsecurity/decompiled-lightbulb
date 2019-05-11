package com.facebook.react.bridge;

import javax.annotation.Nullable;

public class JSApplicationCausedNativeException extends RuntimeException {

   public JSApplicationCausedNativeException(String var1) {
      super(var1);
   }

   public JSApplicationCausedNativeException(@Nullable String var1, @Nullable Throwable var2) {
      super(var1, var2);
   }
}
