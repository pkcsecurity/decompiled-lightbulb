package com.facebook.react.bridge;

import javax.annotation.Nullable;

public interface Promise {

   @Deprecated
   void reject(String var1);

   void reject(String var1, String var2);

   void reject(String var1, String var2, Throwable var3);

   void reject(String var1, Throwable var2);

   void reject(Throwable var1);

   void resolve(@Nullable Object var1);
}
