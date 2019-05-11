package com.facebook.react.bridge;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.WritableNativeArray;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import javax.annotation.Nullable;

public final class JavaScriptModuleRegistry {

   private final HashMap<Class<? extends JavaScriptModule>, JavaScriptModule> mModuleInstances = new HashMap();


   public <T extends Object & JavaScriptModule> T getJavaScriptModule(CatalystInstance param1, Class<T> param2) {
      // $FF: Couldn't be decompiled
   }

   static class JavaScriptModuleInvocationHandler implements InvocationHandler {

      private final CatalystInstance mCatalystInstance;
      private final Class<? extends JavaScriptModule> mModuleInterface;
      @Nullable
      private String mName;


      public JavaScriptModuleInvocationHandler(CatalystInstance var1, Class<? extends JavaScriptModule> var2) {
         this.mCatalystInstance = var1;
         this.mModuleInterface = var2;
      }

      private String getJSModuleName() {
         if(this.mName == null) {
            String var3 = this.mModuleInterface.getSimpleName();
            int var1 = var3.lastIndexOf(36);
            String var2 = var3;
            if(var1 != -1) {
               var2 = var3.substring(var1 + 1);
            }

            this.mName = var2;
         }

         return this.mName;
      }

      @Nullable
      public Object invoke(Object var1, Method var2, @Nullable Object[] var3) throws Throwable {
         WritableNativeArray var4;
         if(var3 != null) {
            var4 = Arguments.fromJavaArgs(var3);
         } else {
            var4 = new WritableNativeArray();
         }

         this.mCatalystInstance.callFunction(this.getJSModuleName(), var2.getName(), var4);
         return null;
      }
   }
}
