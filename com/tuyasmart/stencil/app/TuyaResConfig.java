package com.tuyasmart.stencil.app;

import java.util.Map;

public class TuyaResConfig {

   public static String PUSH_ICON_RES_ID;
   public static String THEME_ID;
   private static Map<String, Integer> resIdMap;


   public static int getPushIconResId() {
      return getResId(PUSH_ICON_RES_ID);
   }

   public static int getResId(String var0) {
      if(resIdMap != null) {
         Integer var1 = (Integer)resIdMap.get(var0);
         if(var1 != null) {
            return var1.intValue();
         }
      }

      return 0;
   }

   public static int getThemeId() {
      return getResId(THEME_ID);
   }

   public static void init(Map<String, Integer> var0) {
      resIdMap = var0;
   }
}
