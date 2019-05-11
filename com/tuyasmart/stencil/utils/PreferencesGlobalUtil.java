package com.tuyasmart.stencil.utils;

import com.tuya.smart.android.base.mmkv.manager.MMKVManager;
import com.tuya.smart.android.base.mmkv.util.GlobalMMKVManager;
import com.tuya.smart.android.common.utils.L;
import java.util.HashSet;

public class PreferencesGlobalUtil {

   public static final String BLE_DEVICE_VERSION_SUFFIX = "ble_v";
   public static final String CAMERA_MONITOR_DOMAIN = "camera_monitor_domain";
   public static final String CONF_IS_LAUNCHER_PANEL_OPEN = "conf_is_launcher_panel_open";
   public static final String ERROR_TIP_USERS = "error_tip_users";
   public static final String GCM_PUSH_TOKEN = "gcm_push_token";
   public static final String GLOBAL_ENV = "GLOBAL_ENV";
   public static final String GUIDE_FIRST_IN = "is_first_in";
   public static final String GUIDE_SCORE_JSON = "guide_score_json";
   public static final String HAS_CHOOSE_PROHIBIT_BLE_LOCATE = "HAS_CHOOSE_PROHIBIT_BLE_LOCATE";
   public static final String HOME_CENTER_TAB_HAS_NEW = "home_center_tab_has_new";
   public static final String MESSAGE_CENTER_LIST_HAS_NEW = "message_has_new";
   public static final String MESSAGE_USER_TIME_STAMP = "message_user_time_stamp";
   public static final String PUSH_CHANEL_SELECT = "push_chanel_select";
   private static MMKVManager sMMKVManager;


   public static Boolean getBoolean(String var0) {
      return Boolean.valueOf(getMMKVManager().getBoolean(var0, false));
   }

   public static float getFloat(String var0) {
      return getMMKVManager().getFloat(var0, 0.0F);
   }

   public static int getInt(String var0) {
      return getMMKVManager().getInt(var0, 0);
   }

   public static long getLong(String var0) {
      return getMMKVManager().getLong(var0, 0L);
   }

   private static MMKVManager getMMKVManager() {
      return GlobalMMKVManager.getMMKVManager();
   }

   public static String getString(String var0) {
      return getMMKVManager().getString(var0, "");
   }

   public static HashSet<String> getStringSet(String var0) {
      return (HashSet)getMMKVManager().getStringSet(var0, new HashSet());
   }

   public static void remove(String var0) {
      getMMKVManager().remove(var0);
   }

   public static void set(String var0, float var1) {
      getMMKVManager().putFloat(var0, var1);
   }

   public static void set(String var0, int var1) {
      getMMKVManager().putInt(var0, var1);
   }

   public static void set(String var0, long var1) {
      getMMKVManager().putLong(var0, var1);
   }

   public static void set(String var0, String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append("key: ");
      var2.append(var0);
      var2.append(" b: ");
      var2.append(var1);
      L.d("StencilApp", var2.toString());
      getMMKVManager().putString(var0, var1);
   }

   public static void set(String var0, HashSet<String> var1) {
      getMMKVManager().putStringSet(var0, var1);
   }

   public static void set(String var0, boolean var1) {
      getMMKVManager().putBoolean(var0, var1);
   }
}
