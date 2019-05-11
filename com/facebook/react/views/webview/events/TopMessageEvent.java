package com.facebook.react.views.webview.events;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class TopMessageEvent extends Event<TopMessageEvent> {

   public static final String EVENT_NAME = "topMessage";
   private final String mData;


   public TopMessageEvent(int var1, String var2) {
      super(var1);
      this.mData = var2;
   }

   public boolean canCoalesce() {
      return false;
   }

   public void dispatch(RCTEventEmitter var1) {
      WritableMap var2 = Arguments.createMap();
      var2.putString("data", this.mData);
      var1.receiveEvent(this.getViewTag(), "topMessage", var2);
   }

   public short getCoalescingKey() {
      return (short)0;
   }

   public String getEventName() {
      return "topMessage";
   }
}
