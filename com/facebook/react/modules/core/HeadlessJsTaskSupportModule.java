package com.facebook.react.modules.core;

import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.jstasks.HeadlessJsTaskContext;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(
   name = "HeadlessJsTaskSupport"
)
public class HeadlessJsTaskSupportModule extends ReactContextBaseJavaModule {

   protected static final String MODULE_NAME = "HeadlessJsTaskSupport";


   public HeadlessJsTaskSupportModule(ReactApplicationContext var1) {
      super(var1);
   }

   public String getName() {
      return "HeadlessJsTaskSupport";
   }

   @ReactMethod
   public void notifyTaskFinished(int var1) {
      HeadlessJsTaskContext var2 = HeadlessJsTaskContext.getInstance(this.getReactApplicationContext());
      if(var2.isTaskRunning(var1)) {
         var2.finishTask(var1);
      } else {
         FLog.w(HeadlessJsTaskSupportModule.class, "Tried to finish non-active task with id %d. Did it time out?", new Object[]{Integer.valueOf(var1)});
      }
   }
}
