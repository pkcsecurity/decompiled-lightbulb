package com.facebook.react.modules.systeminfo;

import android.os.Build.VERSION;
import com.facebook.react.bridge.BaseJavaModule;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.systeminfo.AndroidInfoHelpers;
import com.facebook.react.modules.systeminfo.ReactNativeVersion;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

@ReactModule(
   name = "PlatformConstants"
)
public class AndroidInfoModule extends BaseJavaModule {

   private static final String IS_TESTING = "IS_TESTING";


   @Nullable
   public Map<String, Object> getConstants() {
      HashMap var1 = new HashMap();
      var1.put("Version", Integer.valueOf(VERSION.SDK_INT));
      var1.put("ServerHost", AndroidInfoHelpers.getServerHost());
      var1.put("isTesting", Boolean.valueOf("true".equals(System.getProperty("IS_TESTING"))));
      var1.put("reactNativeVersion", ReactNativeVersion.VERSION);
      return var1;
   }

   public String getName() {
      return "PlatformConstants";
   }
}
