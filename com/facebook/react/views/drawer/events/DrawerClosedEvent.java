package com.facebook.react.views.drawer.events;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class DrawerClosedEvent extends Event<DrawerClosedEvent> {

   public static final String EVENT_NAME = "topDrawerClosed";


   public DrawerClosedEvent(int var1) {
      super(var1);
   }

   public void dispatch(RCTEventEmitter var1) {
      var1.receiveEvent(this.getViewTag(), this.getEventName(), Arguments.createMap());
   }

   public short getCoalescingKey() {
      return (short)0;
   }

   public String getEventName() {
      return "topDrawerClosed";
   }
}
