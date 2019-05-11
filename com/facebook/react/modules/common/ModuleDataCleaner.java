package com.facebook.react.modules.common;

import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.NativeModule;
import java.util.Iterator;

public class ModuleDataCleaner {

   public static void cleanDataFromModules(CatalystInstance var0) {
      Iterator var3 = var0.getNativeModules().iterator();

      while(var3.hasNext()) {
         NativeModule var1 = (NativeModule)var3.next();
         if(var1 instanceof ModuleDataCleaner.Cleanable) {
            StringBuilder var2 = new StringBuilder();
            var2.append("Cleaning data from ");
            var2.append(var1.getName());
            FLog.d("ReactNative", var2.toString());
            ((ModuleDataCleaner.Cleanable)var1).clearSensitiveData();
         }
      }

   }

   public interface Cleanable {

      void clearSensitiveData();
   }
}
