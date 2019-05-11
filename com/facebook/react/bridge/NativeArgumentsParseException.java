package com.facebook.react.bridge;

import com.facebook.react.bridge.JSApplicationCausedNativeException;
import javax.annotation.Nullable;

public class NativeArgumentsParseException extends JSApplicationCausedNativeException {

   public NativeArgumentsParseException(String var1) {
      super(var1);
   }

   public NativeArgumentsParseException(@Nullable String var1, @Nullable Throwable var2) {
      super(var1, var2);
   }
}
