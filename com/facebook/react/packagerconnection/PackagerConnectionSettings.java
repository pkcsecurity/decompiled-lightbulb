package com.facebook.react.packagerconnection;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.facebook.common.logging.FLog;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.modules.systeminfo.AndroidInfoHelpers;
import javax.annotation.Nullable;

public class PackagerConnectionSettings {

   private static final String PREFS_DEBUG_SERVER_HOST_KEY = "debug_http_host";
   private static final String TAG = "PackagerConnectionSettings";
   private final String mPackageName;
   private final SharedPreferences mPreferences;


   public PackagerConnectionSettings(Context var1) {
      this.mPreferences = PreferenceManager.getDefaultSharedPreferences(var1);
      this.mPackageName = var1.getPackageName();
   }

   public String getDebugServerHost() {
      String var1 = this.mPreferences.getString("debug_http_host", (String)null);
      if(!TextUtils.isEmpty(var1)) {
         return (String)Assertions.assertNotNull(var1);
      } else {
         var1 = AndroidInfoHelpers.getServerHost();
         if(var1.equals("localhost")) {
            FLog.w(TAG, "You seem to be running on device. Run \'adb reverse tcp:8081 tcp:8081\' to forward the debug server\'s port to the device.");
         }

         return var1;
      }
   }

   public String getInspectorServerHost() {
      return AndroidInfoHelpers.getInspectorProxyHost();
   }

   @Nullable
   public String getPackageName() {
      return this.mPackageName;
   }
}
