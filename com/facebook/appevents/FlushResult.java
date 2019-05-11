package com.facebook.appevents;


public enum FlushResult {

   // $FF: synthetic field
   private static final FlushResult[] $VALUES = new FlushResult[]{SUCCESS, SERVER_ERROR, NO_CONNECTIVITY, UNKNOWN_ERROR};
   NO_CONNECTIVITY("NO_CONNECTIVITY", 2),
   SERVER_ERROR("SERVER_ERROR", 1),
   SUCCESS("SUCCESS", 0),
   UNKNOWN_ERROR("UNKNOWN_ERROR", 3);


   private FlushResult(String var1, int var2) {}
}
