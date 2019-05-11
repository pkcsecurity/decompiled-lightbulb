package com.facebook.appevents.internal;

import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.appevents.internal.InternalAppEventsLogger;
import com.facebook.appevents.internal.SessionInfo;
import com.facebook.appevents.internal.SourceApplicationInfo;
import com.facebook.internal.Logger;
import java.util.Locale;

class SessionLogger {

   private static final long[] INACTIVE_SECONDS_QUANTA = new long[]{300000L, 900000L, 1800000L, 3600000L, 21600000L, 43200000L, 86400000L, 172800000L, 259200000L, 604800000L, 1209600000L, 1814400000L, 2419200000L, 5184000000L, 7776000000L, 10368000000L, 12960000000L, 15552000000L, 31536000000L};
   private static final String TAG = SessionLogger.class.getCanonicalName();


   private static int getQuantaIndex(long var0) {
      int var2;
      for(var2 = 0; var2 < INACTIVE_SECONDS_QUANTA.length && INACTIVE_SECONDS_QUANTA[var2] < var0; ++var2) {
         ;
      }

      return var2;
   }

   public static void logActivateApp(String var0, SourceApplicationInfo var1, String var2) {
      String var5;
      if(var1 != null) {
         var5 = var1.toString();
      } else {
         var5 = "Unclassified";
      }

      Bundle var3 = new Bundle();
      var3.putString("fb_mobile_launch_source", var5);
      InternalAppEventsLogger var4 = new InternalAppEventsLogger(var0, var2, (AccessToken)null);
      var4.logEvent("fb_mobile_activate_app", var3);
      if(AppEventsLogger.getFlushBehavior() != AppEventsLogger.FlushBehavior.EXPLICIT_ONLY) {
         var4.flush();
      }

   }

   private static void logClockSkewEvent() {
      Logger.log(LoggingBehavior.APP_EVENTS, TAG, "Clock skew detected");
   }

   public static void logDeactivateApp(String var0, SessionInfo var1, String var2) {
      Long var3 = Long.valueOf(var1.getDiskRestoreTime() - var1.getSessionLastEventTime().longValue());
      Long var4 = var3;
      if(var3.longValue() < 0L) {
         var4 = Long.valueOf(0L);
         logClockSkewEvent();
      }

      Long var5 = Long.valueOf(var1.getSessionLength());
      var3 = var5;
      if(var5.longValue() < 0L) {
         logClockSkewEvent();
         var3 = Long.valueOf(0L);
      }

      Bundle var7 = new Bundle();
      var7.putInt("fb_mobile_app_interruptions", var1.getInterruptionCount());
      var7.putString("fb_mobile_time_between_sessions", String.format(Locale.ROOT, "session_quanta_%d", new Object[]{Integer.valueOf(getQuantaIndex(var4.longValue()))}));
      SourceApplicationInfo var6 = var1.getSourceApplicationInfo();
      String var8;
      if(var6 != null) {
         var8 = var6.toString();
      } else {
         var8 = "Unclassified";
      }

      var7.putString("fb_mobile_launch_source", var8);
      var7.putLong("_logTime", var1.getSessionLastEventTime().longValue() / 1000L);
      (new InternalAppEventsLogger(var0, var2, (AccessToken)null)).logEvent("fb_mobile_deactivate_app", (double)(var3.longValue() / 1000L), var7);
   }
}
