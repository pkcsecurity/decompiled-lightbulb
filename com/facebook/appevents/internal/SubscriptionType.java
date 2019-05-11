package com.facebook.appevents.internal;


public enum SubscriptionType {

   // $FF: synthetic field
   private static final SubscriptionType[] $VALUES = new SubscriptionType[]{NEW, HEARTBEAT, EXPIRE, CANCEL, RESTORE, DUPLICATED, UNKNOWN};
   CANCEL("CANCEL", 3),
   DUPLICATED("DUPLICATED", 5),
   EXPIRE("EXPIRE", 2),
   HEARTBEAT("HEARTBEAT", 1),
   NEW("NEW", 0),
   RESTORE("RESTORE", 4),
   UNKNOWN("UNKNOWN", 6);


   private SubscriptionType(String var1, int var2) {}
}
