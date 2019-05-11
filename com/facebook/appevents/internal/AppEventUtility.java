package com.facebook.appevents.internal;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;
import com.facebook.FacebookSdk;
import com.facebook.internal.Utility;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppEventUtility {

   private static final String regex = "[-+]*\\d+([\\,\\.]\\d+)*([\\.\\,]\\d+)?";


   public static void assertIsMainThread() {}

   public static void assertIsNotMainThread() {}

   public static String bytesToHex(byte[] var0) {
      StringBuffer var3 = new StringBuffer();
      int var2 = var0.length;

      for(int var1 = 0; var1 < var2; ++var1) {
         var3.append(String.format("%02x", new Object[]{Byte.valueOf(var0[var1])}));
      }

      return var3.toString();
   }

   public static String getAppVersion() {
      Context var0 = FacebookSdk.getApplicationContext();

      try {
         String var2 = var0.getPackageManager().getPackageInfo(var0.getPackageName(), 0).versionName;
         return var2;
      } catch (NameNotFoundException var1) {
         return "";
      }
   }

   public static boolean isEmulator() {
      return Build.FINGERPRINT.startsWith("generic") || Build.FINGERPRINT.startsWith("unknown") || Build.MODEL.contains("google_sdk") || Build.MODEL.contains("Emulator") || Build.MODEL.contains("Android SDK built for x86") || Build.MANUFACTURER.contains("Genymotion") || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic") || "google_sdk".equals(Build.PRODUCT);
   }

   private static boolean isMainThread() {
      return Looper.myLooper() == Looper.getMainLooper();
   }

   public static double normalizePrice(String var0) {
      try {
         Matcher var4 = Pattern.compile("[-+]*\\d+([\\,\\.]\\d+)*([\\.\\,]\\d+)?", 8).matcher(var0);
         if(var4.find()) {
            var0 = var4.group(0);
            double var1 = NumberFormat.getNumberInstance(Utility.getCurrentLocale()).parse(var0).doubleValue();
            return var1;
         } else {
            return 0.0D;
         }
      } catch (ParseException var3) {
         return 0.0D;
      }
   }
}
