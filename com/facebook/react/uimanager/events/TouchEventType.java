package com.facebook.react.uimanager.events;


public enum TouchEventType {

   // $FF: synthetic field
   private static final TouchEventType[] $VALUES = new TouchEventType[]{START, END, MOVE, CANCEL};
   CANCEL("CANCEL", 3, "topTouchCancel"),
   END("END", 1, "topTouchEnd"),
   MOVE("MOVE", 2, "topTouchMove"),
   START("START", 0, "topTouchStart");
   private final String mJSEventName;


   private TouchEventType(String var1, int var2, String var3) {
      this.mJSEventName = var3;
   }

   public String getJSEventName() {
      return this.mJSEventName;
   }
}
