package com.facebook.infer.annotation;

import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public class Assertions {

   public static void assertCondition(boolean var0) {
      if(!var0) {
         throw new AssertionError();
      }
   }

   public static void assertCondition(boolean var0, String var1) {
      if(!var0) {
         throw new AssertionError(var1);
      }
   }

   public static <T extends Object> T assertNotNull(@Nullable T var0) {
      if(var0 == null) {
         throw new AssertionError();
      } else {
         return var0;
      }
   }

   public static <T extends Object> T assertNotNull(@Nullable T var0, String var1) {
      if(var0 == null) {
         throw new AssertionError(var1);
      } else {
         return var0;
      }
   }

   public static AssertionError assertUnreachable() {
      throw new AssertionError();
   }

   public static AssertionError assertUnreachable(Exception var0) {
      throw new AssertionError(var0);
   }

   public static AssertionError assertUnreachable(String var0) {
      throw new AssertionError(var0);
   }

   public static void assumeCondition(boolean var0) {}

   public static void assumeCondition(boolean var0, String var1) {}

   public static <T extends Object> T assumeNotNull(@Nullable T var0) {
      return var0;
   }

   public static <T extends Object> T assumeNotNull(@Nullable T var0, String var1) {
      return var0;
   }

   public static <T extends Object> T getAssertingNotNull(List<T> var0, int var1) {
      boolean var2;
      if(var1 >= 0 && var1 < var0.size()) {
         var2 = true;
      } else {
         var2 = false;
      }

      assertCondition(var2);
      return assertNotNull(var0.get(var1));
   }

   public static <K extends Object, V extends Object> V getAssertingNotNull(Map<K, V> var0, K var1) {
      assertCondition(var0.containsKey(var1));
      return assertNotNull(var0.get(var1));
   }

   public static <T extends Object> T getAssumingNotNull(List<T> var0, int var1) {
      return var0.get(var1);
   }

   public static <K extends Object, V extends Object> V getAssumingNotNull(Map<K, V> var0, K var1) {
      return var0.get(var1);
   }
}
