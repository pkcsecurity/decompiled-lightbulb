package com.facebook.appevents.internal;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.internal.SourceApplicationInfo;
import java.util.UUID;

class SessionInfo {

   private static final String INTERRUPTION_COUNT_KEY = "com.facebook.appevents.SessionInfo.interruptionCount";
   private static final String LAST_SESSION_INFO_END_KEY = "com.facebook.appevents.SessionInfo.sessionEndTime";
   private static final String LAST_SESSION_INFO_START_KEY = "com.facebook.appevents.SessionInfo.sessionStartTime";
   private static final String SESSION_ID_KEY = "com.facebook.appevents.SessionInfo.sessionId";
   private Long diskRestoreTime;
   private int interruptionCount;
   private UUID sessionId;
   private Long sessionLastEventTime;
   private Long sessionStartTime;
   private SourceApplicationInfo sourceApplicationInfo;


   public SessionInfo(Long var1, Long var2) {
      this(var1, var2, UUID.randomUUID());
   }

   public SessionInfo(Long var1, Long var2, UUID var3) {
      this.sessionStartTime = var1;
      this.sessionLastEventTime = var2;
      this.sessionId = var3;
   }

   public static void clearSavedSessionFromDisk() {
      Editor var0 = PreferenceManager.getDefaultSharedPreferences(FacebookSdk.getApplicationContext()).edit();
      var0.remove("com.facebook.appevents.SessionInfo.sessionStartTime");
      var0.remove("com.facebook.appevents.SessionInfo.sessionEndTime");
      var0.remove("com.facebook.appevents.SessionInfo.interruptionCount");
      var0.remove("com.facebook.appevents.SessionInfo.sessionId");
      var0.apply();
      SourceApplicationInfo.clearSavedSourceApplicationInfoFromDisk();
   }

   public static SessionInfo getStoredSessionInfo() {
      SharedPreferences var4 = PreferenceManager.getDefaultSharedPreferences(FacebookSdk.getApplicationContext());
      long var0 = var4.getLong("com.facebook.appevents.SessionInfo.sessionStartTime", 0L);
      long var2 = var4.getLong("com.facebook.appevents.SessionInfo.sessionEndTime", 0L);
      String var5 = var4.getString("com.facebook.appevents.SessionInfo.sessionId", (String)null);
      if(var0 != 0L && var2 != 0L) {
         if(var5 == null) {
            return null;
         } else {
            SessionInfo var6 = new SessionInfo(Long.valueOf(var0), Long.valueOf(var2));
            var6.interruptionCount = var4.getInt("com.facebook.appevents.SessionInfo.interruptionCount", 0);
            var6.sourceApplicationInfo = SourceApplicationInfo.getStoredSourceApplicatioInfo();
            var6.diskRestoreTime = Long.valueOf(System.currentTimeMillis());
            var6.sessionId = UUID.fromString(var5);
            return var6;
         }
      } else {
         return null;
      }
   }

   public long getDiskRestoreTime() {
      return this.diskRestoreTime == null?0L:this.diskRestoreTime.longValue();
   }

   public int getInterruptionCount() {
      return this.interruptionCount;
   }

   public UUID getSessionId() {
      return this.sessionId;
   }

   public Long getSessionLastEventTime() {
      return this.sessionLastEventTime;
   }

   public long getSessionLength() {
      return this.sessionStartTime != null && this.sessionLastEventTime != null?this.sessionLastEventTime.longValue() - this.sessionStartTime.longValue():0L;
   }

   public Long getSessionStartTime() {
      return this.sessionStartTime;
   }

   public SourceApplicationInfo getSourceApplicationInfo() {
      return this.sourceApplicationInfo;
   }

   public void incrementInterruptionCount() {
      ++this.interruptionCount;
   }

   public void setSessionLastEventTime(Long var1) {
      this.sessionLastEventTime = var1;
   }

   public void setSourceApplicationInfo(SourceApplicationInfo var1) {
      this.sourceApplicationInfo = var1;
   }

   public void writeSessionToDisk() {
      Editor var1 = PreferenceManager.getDefaultSharedPreferences(FacebookSdk.getApplicationContext()).edit();
      var1.putLong("com.facebook.appevents.SessionInfo.sessionStartTime", this.sessionStartTime.longValue());
      var1.putLong("com.facebook.appevents.SessionInfo.sessionEndTime", this.sessionLastEventTime.longValue());
      var1.putInt("com.facebook.appevents.SessionInfo.interruptionCount", this.interruptionCount);
      var1.putString("com.facebook.appevents.SessionInfo.sessionId", this.sessionId.toString());
      var1.apply();
      if(this.sourceApplicationInfo != null) {
         this.sourceApplicationInfo.writeSourceApplicationInfoToDisk();
      }

   }
}
