package com.facebook.react.modules.location;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

public class PositionError {

   public static int PERMISSION_DENIED;
   public static int POSITION_UNAVAILABLE;
   public static int TIMEOUT;


   public static WritableMap buildError(int var0, String var1) {
      WritableMap var2 = Arguments.createMap();
      var2.putInt("code", var0);
      if(var1 != null) {
         var2.putString("message", var1);
      }

      return var2;
   }
}
