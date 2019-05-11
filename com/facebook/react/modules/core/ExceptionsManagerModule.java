package com.facebook.react.modules.core;

import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.BaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.JavascriptException;
import com.facebook.react.devsupport.interfaces.DevSupportManager;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.util.JSStackTrace;

@ReactModule(
   name = "ExceptionsManager"
)
public class ExceptionsManagerModule extends BaseJavaModule {

   protected static final String NAME = "ExceptionsManager";
   private final DevSupportManager mDevSupportManager;


   public ExceptionsManagerModule(DevSupportManager var1) {
      this.mDevSupportManager = var1;
   }

   private void showOrThrowError(String var1, ReadableArray var2, int var3) {
      if(this.mDevSupportManager.getDevSupportEnabled()) {
         this.mDevSupportManager.showNewJSError(var1, var2, var3);
      } else {
         throw new JavascriptException(JSStackTrace.format(var1, var2));
      }
   }

   @ReactMethod
   public void dismissRedbox() {
      if(this.mDevSupportManager.getDevSupportEnabled()) {
         this.mDevSupportManager.hideRedboxDialog();
      }

   }

   public String getName() {
      return "ExceptionsManager";
   }

   @ReactMethod
   public void reportFatalException(String var1, ReadableArray var2, int var3) {
      this.showOrThrowError(var1, var2, var3);
   }

   @ReactMethod
   public void reportSoftException(String var1, ReadableArray var2, int var3) {
      if(this.mDevSupportManager.getDevSupportEnabled()) {
         this.mDevSupportManager.showNewJSError(var1, var2, var3);
      } else {
         FLog.e("ReactNative", JSStackTrace.format(var1, var2));
      }
   }

   @ReactMethod
   public void updateExceptionMessage(String var1, ReadableArray var2, int var3) {
      if(this.mDevSupportManager.getDevSupportEnabled()) {
         this.mDevSupportManager.updateJSError(var1, var2, var3);
      }

   }
}
