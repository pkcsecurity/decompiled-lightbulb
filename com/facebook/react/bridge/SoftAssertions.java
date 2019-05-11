package com.facebook.react.bridge;

import com.facebook.react.bridge.AssertionException;
import javax.annotation.Nullable;

public class SoftAssertions {

   public static void assertCondition(boolean var0, String var1) {
      if(!var0) {
         throw new AssertionException(var1);
      }
   }

   public static <T extends Object> T assertNotNull(@Nullable T var0) {
      if(var0 == null) {
         throw new AssertionException("Expected object to not be null!");
      } else {
         return var0;
      }
   }

   public static void assertUnreachable(String var0) {
      throw new AssertionException(var0);
   }
}
