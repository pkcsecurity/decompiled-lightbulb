package com.facebook.react.views.webview.events;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class TopLoadingFinishEvent extends Event<TopLoadingFinishEvent> {

   public static final String EVENT_NAME = "topLoadingFinish";
   private WritableMap mEventData;


   public TopLoadingFinishEvent(int var1, WritableMap var2) {
      super(var1);
      this.mEventData = var2;
   }

   public boolean canCoalesce() {
      return false;
   }

   public void dispatch(RCTEventEmitter var1) {
      var1.receiveEvent(this.getViewTag(), this.getEventName(), this.mEventData);
   }

   public short getCoalescingKey() {
      return (short)0;
   }

   public String getEventName() {
      return "topLoadingFinish";
   }
}
