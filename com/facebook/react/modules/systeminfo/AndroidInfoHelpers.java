package com.facebook.react.modules.systeminfo;

import android.os.Build;
import android.os.Build.VERSION;
import java.util.Locale;

public class AndroidInfoHelpers {

   private static final int DEBUG_SERVER_HOST_PORT = 8081;
   public static final String DEVICE_LOCALHOST = "localhost";
   public static final String EMULATOR_LOCALHOST = "10.0.2.2";
   public static final String GENYMOTION_LOCALHOST = "10.0.3.2";
   private static final int INSPECTOR_PROXY_PORT = 8082;


   public static String getFriendlyDeviceName() {
      if(isRunningOnGenymotion()) {
         return Build.MODEL;
      } else {
         StringBuilder var0 = new StringBuilder();
         var0.append(Build.MODEL);
         var0.append(" - ");
         var0.append(VERSION.RELEASE);
         var0.append(" - API ");
         var0.append(VERSION.SDK_INT);
         return var0.toString();
      }
   }

   public static String getInspectorProxyHost() {
      return getServerIpAddress(8082);
   }

   public static String getServerHost() {
      return getServerIpAddress(8081);
   }

   private static String getServerIpAddress(int var0) {
      String var1;
      if(isRunningOnGenymotion()) {
         var1 = "10.0.3.2";
      } else if(isRunningOnStockEmulator()) {
         var1 = "10.0.2.2";
      } else {
         var1 = "localhost";
      }

      return String.format(Locale.US, "%s:%d", new Object[]{var1, Integer.valueOf(var0)});
   }

   private static boolean isRunningOnGenymotion() {
      return Build.FINGERPRINT.contains("vbox");
   }

   private static boolean isRunningOnStockEmulator() {
      return Build.FINGERPRINT.contains("generic");
   }
}
