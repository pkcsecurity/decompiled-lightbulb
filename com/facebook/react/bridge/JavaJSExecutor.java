package com.facebook.react.bridge;

import com.facebook.proguard.annotations.DoNotStrip;

@DoNotStrip
public interface JavaJSExecutor {

   void close();

   @DoNotStrip
   String executeJSCall(String var1, String var2) throws JavaJSExecutor.ProxyExecutorException;

   @DoNotStrip
   void loadApplicationScript(String var1) throws JavaJSExecutor.ProxyExecutorException;

   @DoNotStrip
   void setGlobalVariable(String var1, String var2);

   public static class ProxyExecutorException extends Exception {

      public ProxyExecutorException(Throwable var1) {
         super(var1);
      }
   }

   public interface Factory {

      JavaJSExecutor create() throws Exception;
   }
}
