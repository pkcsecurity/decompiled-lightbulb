package com.facebook.react.bridge;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableNativeMap;
import javax.annotation.Nullable;

public class PromiseImpl implements Promise {

   private static final String DEFAULT_ERROR = "EUNSPECIFIED";
   @Nullable
   private Callback mReject;
   @Nullable
   private Callback mResolve;


   public PromiseImpl(@Nullable Callback var1, @Nullable Callback var2) {
      this.mResolve = var1;
      this.mReject = var2;
   }

   @Deprecated
   public void reject(String var1) {
      this.reject("EUNSPECIFIED", var1, (Throwable)null);
   }

   public void reject(String var1, String var2) {
      this.reject(var1, var2, (Throwable)null);
   }

   public void reject(String var1, String var2, @Nullable Throwable var3) {
      if(this.mReject != null) {
         String var5 = var1;
         if(var1 == null) {
            var5 = "EUNSPECIFIED";
         }

         WritableNativeMap var4 = new WritableNativeMap();
         var4.putString("code", var5);
         var4.putString("message", var2);
         this.mReject.invoke(new Object[]{var4});
      }

   }

   public void reject(String var1, Throwable var2) {
      this.reject(var1, var2.getMessage(), var2);
   }

   public void reject(Throwable var1) {
      this.reject("EUNSPECIFIED", var1.getMessage(), var1);
   }

   public void resolve(Object var1) {
      if(this.mResolve != null) {
         this.mResolve.invoke(new Object[]{var1});
      }

   }
}
