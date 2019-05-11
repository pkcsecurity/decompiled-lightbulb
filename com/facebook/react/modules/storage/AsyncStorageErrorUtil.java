package com.facebook.react.modules.storage;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import javax.annotation.Nullable;

public class AsyncStorageErrorUtil {

   static WritableMap getDBError(@Nullable String var0) {
      return getError(var0, "Database Error");
   }

   static WritableMap getError(@Nullable String var0, String var1) {
      WritableMap var2 = Arguments.createMap();
      var2.putString("message", var1);
      if(var0 != null) {
         var2.putString("key", var0);
      }

      return var2;
   }

   static WritableMap getInvalidKeyError(@Nullable String var0) {
      return getError(var0, "Invalid key");
   }

   static WritableMap getInvalidValueError(@Nullable String var0) {
      return getError(var0, "Invalid Value");
   }
}
