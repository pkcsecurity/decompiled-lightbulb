package com.tuya.smart.android.base.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.tuya.smart.android.base.TuyaSmartSdk;

public class StorageHelper {

   private static Context sContext;


   public static boolean getBooleanValue(String var0) {
      return getBooleanValue(var0, false);
   }

   public static boolean getBooleanValue(String var0, boolean var1) {
      SharedPreferences var2 = getCommonSharedPreferences();
      return var2 != null && var2.contains(var0)?var2.getBoolean(var0, var1):var1;
   }

   private static SharedPreferences getCommonSharedPreferences() {
      synchronized(StorageHelper.class){}

      SharedPreferences var0;
      try {
         if(sContext == null) {
            sContext = TuyaSmartSdk.getApplication();
         }

         var0 = sContext.getSharedPreferences("common_storage", 4);
      } finally {
         ;
      }

      return var0;
   }

   public static int getIntValue(String var0, int var1) {
      SharedPreferences var2 = getCommonSharedPreferences();
      return var2 != null && var2.contains(var0)?var2.getInt(var0, var1):var1;
   }

   public static long getLongValue(String var0, long var1) {
      SharedPreferences var3 = getCommonSharedPreferences();
      return var3 != null && var3.contains(var0)?var3.getLong(var0, var1):var1;
   }

   public static String getStringValue(String var0, String var1) {
      SharedPreferences var2 = getCommonSharedPreferences();
      return var2 != null && var2.contains(var0)?var2.getString(var0, var1):var1;
   }

   public static void remove(String var0) {
      getCommonSharedPreferences().edit().remove(var0).commit();
   }

   public static void setBooleanValue(String var0, boolean var1) {
      Editor var2 = getCommonSharedPreferences().edit();
      var2.putBoolean(var0, var1);
      var2.apply();
   }

   public static void setIntValue(String var0, Integer var1) {
      Editor var2 = getCommonSharedPreferences().edit();
      var2.putInt(var0, var1.intValue());
      var2.apply();
   }

   public static void setLongValue(String var0, Long var1) {
      Editor var2 = getCommonSharedPreferences().edit();
      var2.putLong(var0, var1.longValue());
      var2.apply();
   }

   public static void setStringValue(String var0, String var1) {
      Editor var2 = getCommonSharedPreferences().edit();
      var2.putString(var0, var1);
      var2.apply();
   }
}
