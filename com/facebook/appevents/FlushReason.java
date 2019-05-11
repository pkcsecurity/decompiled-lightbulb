package com.facebook.appevents;


enum FlushReason {

   // $FF: synthetic field
   private static final FlushReason[] $VALUES = new FlushReason[]{EXPLICIT, TIMER, SESSION_CHANGE, PERSISTED_EVENTS, EVENT_THRESHOLD, EAGER_FLUSHING_EVENT};
   EAGER_FLUSHING_EVENT("EAGER_FLUSHING_EVENT", 5),
   EVENT_THRESHOLD("EVENT_THRESHOLD", 4),
   EXPLICIT("EXPLICIT", 0),
   PERSISTED_EVENTS("PERSISTED_EVENTS", 3),
   SESSION_CHANGE("SESSION_CHANGE", 2),
   TIMER("TIMER", 1);


   private FlushReason(String var1, int var2) {}
}
