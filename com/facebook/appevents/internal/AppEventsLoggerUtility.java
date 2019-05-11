package com.facebook.appevents.internal;

import android.content.Context;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class AppEventsLoggerUtility {

   private static final Map<AppEventsLoggerUtility.GraphAPIActivityType, String> API_ACTIVITY_TYPE_TO_STRING = new HashMap() {
      {
         this.put(AppEventsLoggerUtility.GraphAPIActivityType.MOBILE_INSTALL_EVENT, "MOBILE_APP_INSTALL");
         this.put(AppEventsLoggerUtility.GraphAPIActivityType.CUSTOM_APP_EVENTS, "CUSTOM_APP_EVENTS");
      }
   };


   public static JSONObject getJSONObjectForGraphAPICall(AppEventsLoggerUtility.GraphAPIActivityType var0, AttributionIdentifiers var1, String var2, boolean var3, Context var4) throws JSONException {
      JSONObject var5 = new JSONObject();
      var5.put("event", API_ACTIVITY_TYPE_TO_STRING.get(var0));
      String var7 = AppEventsLogger.getUserID();
      if(var7 != null) {
         var5.put("app_user_id", var7);
      }

      var7 = AppEventsLogger.getUserData();
      if(var7 != null) {
         var5.put("ud", var7);
      }

      Utility.setAppEventAttributionParameters(var5, var1, var2, var3);

      try {
         Utility.setAppEventExtendedDeviceInfoParameters(var5, var4);
      } catch (Exception var6) {
         Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "Fetching extended device info parameters failed: \'%s\'", new Object[]{var6.toString()});
      }

      var5.put("application_package_name", var4.getPackageName());
      return var5;
   }

   public static enum GraphAPIActivityType {

      // $FF: synthetic field
      private static final AppEventsLoggerUtility.GraphAPIActivityType[] $VALUES = new AppEventsLoggerUtility.GraphAPIActivityType[]{MOBILE_INSTALL_EVENT, CUSTOM_APP_EVENTS};
      CUSTOM_APP_EVENTS("CUSTOM_APP_EVENTS", 1),
      MOBILE_INSTALL_EVENT("MOBILE_INSTALL_EVENT", 0);


      private GraphAPIActivityType(String var1, int var2) {}
   }
}
