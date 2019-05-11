package com.facebook.react.common;

import android.text.TextUtils;
import com.facebook.common.logging.FLog;
import javax.annotation.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

public class DebugServerException extends RuntimeException {

   private static final String GENERIC_ERROR_MESSAGE = "\n\nTry the following to fix the issue:\n• Ensure that the packager server is running\n• Ensure that your device/emulator is connected to your machine and has USB debugging enabled - run \'adb devices\' to see a list of connected devices\n• Ensure Airplane Mode is disabled\n• If you\'re on a physical device connected to the same machine, run \'adb reverse tcp:8081 tcp:8081\' to forward requests from your device\n• If your device is on the same Wi-Fi network, set \'Debug server host & port for device\' in \'Dev settings\' to your machine\'s IP address and the port of the local dev server - e.g. 10.0.1.1:8081\n\n";


   public DebugServerException(String var1) {
      super(var1);
   }

   private DebugServerException(String var1, String var2, int var3, int var4) {
      StringBuilder var5 = new StringBuilder();
      var5.append(var1);
      var5.append("\n  at ");
      var5.append(var2);
      var5.append(":");
      var5.append(var3);
      var5.append(":");
      var5.append(var4);
      super(var5.toString());
   }

   public DebugServerException(String var1, Throwable var2) {
      super(var1, var2);
   }

   public static DebugServerException makeGeneric(String var0, String var1, Throwable var2) {
      StringBuilder var3 = new StringBuilder();
      var3.append(var0);
      var3.append("\n\nTry the following to fix the issue:\n• Ensure that the packager server is running\n• Ensure that your device/emulator is connected to your machine and has USB debugging enabled - run \'adb devices\' to see a list of connected devices\n• Ensure Airplane Mode is disabled\n• If you\'re on a physical device connected to the same machine, run \'adb reverse tcp:8081 tcp:8081\' to forward requests from your device\n• If your device is on the same Wi-Fi network, set \'Debug server host & port for device\' in \'Dev settings\' to your machine\'s IP address and the port of the local dev server - e.g. 10.0.1.1:8081\n\n");
      var3.append(var1);
      return new DebugServerException(var3.toString(), var2);
   }

   public static DebugServerException makeGeneric(String var0, Throwable var1) {
      return makeGeneric(var0, "", var1);
   }

   @Nullable
   public static DebugServerException parse(String var0) {
      if(TextUtils.isEmpty(var0)) {
         return null;
      } else {
         try {
            JSONObject var1 = new JSONObject(var0);
            String var5 = var1.getString("filename");
            DebugServerException var4 = new DebugServerException(var1.getString("description"), shortenFileName(var5), var1.getInt("lineNumber"), var1.getInt("column"));
            return var4;
         } catch (JSONException var3) {
            StringBuilder var2 = new StringBuilder();
            var2.append("Could not parse DebugServerException from: ");
            var2.append(var0);
            FLog.w("ReactNative", var2.toString(), (Throwable)var3);
            return null;
         }
      }
   }

   private static String shortenFileName(String var0) {
      String[] var1 = var0.split("/");
      return var1[var1.length - 1];
   }
}
