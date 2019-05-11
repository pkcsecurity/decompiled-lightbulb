package com.tuya.smart.android.common.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import com.tuya.smart.android.common.utils.FileUtil;
import com.tuya.smart.android.common.utils.L.ILogJsonProxy;
import java.io.File;
import java.io.FileWriter;

public final class L {

   private static String TUYA_TAG;
   private static boolean isLogOn;
   private static boolean isSendLogOn;
   private static ILogJsonProxy mILogJsonProxy;
   private static StringBuffer sLogData = new StringBuffer();
   public static String sLogPath;


   public static void d(String var0, String var1) {
      if(var0 != null && var1 != null && isLogOn) {
         String var2 = TUYA_TAG;
         StringBuilder var3 = new StringBuilder();
         var3.append(var0);
         var3.append(" ");
         var3.append(var1);
         Log.d(var2, var3.toString());
      }

   }

   public static void e(String var0, String var1) {
      if(var0 != null && var1 != null && isLogOn) {
         String var2 = TUYA_TAG;
         StringBuilder var3 = new StringBuilder();
         var3.append(var0);
         var3.append(" ");
         var3.append(var1);
         Log.e(var2, var3.toString());
      }

   }

   public static boolean getLogStatus() {
      return isLogOn;
   }

   public static boolean getSendLogStatus() {
      return isSendLogOn;
   }

   public static void i(String var0, String var1) {
      if(var0 != null && var1 != null && isLogOn) {
         String var2 = TUYA_TAG;
         StringBuilder var3 = new StringBuilder();
         var3.append(var0);
         var3.append(" ");
         var3.append(var1);
         Log.i(var2, var3.toString());
      }

   }

   public static void init() {}

   public static void log2(String var0, String var1) {
      if(var0 != null && var1 != null && isLogOn) {
         StringBuilder var2 = new StringBuilder();
         var2.append(TUYA_TAG);
         var2.append("2");
         String var4 = var2.toString();
         StringBuilder var3 = new StringBuilder();
         var3.append(var0);
         var3.append(" ");
         var3.append(var1);
         Log.d(var4, var3.toString());
      }

   }

   public static void logInLocal(String var0) {
      logInLocal("", var0);
   }

   public static void logInLocal(String var0, String var1) {
      if(isLogOn) {
         d(var0, var1);
      }
   }

   public static void logJson(String var0, String var1) {
      if(var0 != null && var1 != null && isLogOn) {
         if(mILogJsonProxy != null) {
            mILogJsonProxy.log(var0, var1);
            return;
         }

         d(var0, var1);
      }

   }

   public static void logServer(String var0) {
      if(Environment.getExternalStorageState().equals("mounted") && !TextUtils.isEmpty(sLogPath)) {
         try {
            File var1 = new File(sLogPath);
            if(!var1.exists()) {
               var1.getParentFile().mkdirs();
               var1.createNewFile();
            } else if(var1.length() > 5242880L) {
               FileUtil.deleteFileSafely(var1);
            }

            FileWriter var4 = new FileWriter(sLogPath, true);
            StringBuilder var2 = new StringBuilder();
            var2.append(var0);
            var2.append("\r\n");
            var4.write(var2.toString());
            var4.flush();
            var4.close();
         } catch (Exception var3) {
            return;
         }
      }

   }

   public static void logServer(String var0, Object var1) {
      if(var0 != null && var1 != null) {
         d(var0, var1.toString());
      }

   }

   @Deprecated
   public static void logToServer(String var0, String var1) {
      if(var0 != null && var1 != null) {
         d(var0, var1);
      }

   }

   public static void mqtt(String var0, String var1) {
      if(isLogOn) {
         StringBuilder var2 = new StringBuilder();
         var2.append("mqtt: ");
         var2.append(var0);
         d(var2.toString(), var1);
      }
   }

   public static void setJsonAdapter(ILogJsonProxy var0) {
      mILogJsonProxy = var0;
   }

   public static void setLogSwitcher(boolean var0) {
      isLogOn = var0;
   }

   public static void setSendLogOn(boolean var0) {
      isSendLogOn = var0;
   }

   public static void setsLogPath(String var0) {
      sLogPath = var0;
   }

   public static void v(String var0, String var1) {
      if(var0 != null && var1 != null && isLogOn) {
         String var2 = TUYA_TAG;
         StringBuilder var3 = new StringBuilder();
         var3.append(var0);
         var3.append(" ");
         var3.append(var1);
         Log.v(var2, var3.toString());
      }

   }

   public static void w(String var0, String var1) {
      if(var0 != null && var1 != null && isLogOn) {
         String var2 = TUYA_TAG;
         StringBuilder var3 = new StringBuilder();
         var3.append(var0);
         var3.append(" ");
         var3.append(var1);
         Log.w(var2, var3.toString());
      }

   }
}
