package com.facebook.react.devsupport;

import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import java.io.File;
import javax.annotation.Nullable;

@ReactModule(
   name = "JSCHeapCapture",
   needsEagerInit = true
)
public class JSCHeapCapture extends ReactContextBaseJavaModule {

   @Nullable
   private JSCHeapCapture.CaptureCallback mCaptureInProgress = null;


   public JSCHeapCapture(ReactApplicationContext var1) {
      super(var1);
   }

   @ReactMethod
   public void captureComplete(String param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   public void captureHeap(String param1, JSCHeapCapture.CaptureCallback param2) {
      // $FF: Couldn't be decompiled
   }

   public String getName() {
      return "JSCHeapCapture";
   }

   public static class CaptureException extends Exception {

      CaptureException(String var1) {
         super(var1);
      }

      CaptureException(String var1, Throwable var2) {
         super(var1, var2);
      }
   }

   public interface HeapCapture extends JavaScriptModule {

      void captureHeap(String var1);
   }

   public interface CaptureCallback {

      void onFailure(JSCHeapCapture.CaptureException var1);

      void onSuccess(File var1);
   }
}
