package com.facebook.react.bridge;

import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.JavaJSExecutor;
import com.facebook.react.bridge.JavaScriptExecutor;
import com.facebook.react.bridge.JavaScriptExecutorFactory;
import com.facebook.react.bridge.ReactBridge;
import javax.annotation.Nullable;

@DoNotStrip
public class ProxyJavaScriptExecutor extends JavaScriptExecutor {

   @Nullable
   private JavaJSExecutor mJavaJSExecutor;


   static {
      ReactBridge.staticInit();
   }

   public ProxyJavaScriptExecutor(JavaJSExecutor var1) {
      super(initHybrid(var1));
      this.mJavaJSExecutor = var1;
   }

   private static native HybridData initHybrid(JavaJSExecutor var0);

   public void close() {
      if(this.mJavaJSExecutor != null) {
         this.mJavaJSExecutor.close();
         this.mJavaJSExecutor = null;
      }

   }

   public static class Factory implements JavaScriptExecutorFactory {

      private final JavaJSExecutor.Factory mJavaJSExecutorFactory;


      public Factory(JavaJSExecutor.Factory var1) {
         this.mJavaJSExecutorFactory = var1;
      }

      public JavaScriptExecutor create() throws Exception {
         return new ProxyJavaScriptExecutor(this.mJavaJSExecutorFactory.create());
      }
   }
}
