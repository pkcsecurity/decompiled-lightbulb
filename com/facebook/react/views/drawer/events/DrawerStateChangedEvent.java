package com.facebook.react.views.drawer.events;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class DrawerStateChangedEvent extends Event<DrawerStateChangedEvent> {

   public static final String EVENT_NAME = "topDrawerStateChanged";
   private final int mDrawerState;


   public DrawerStateChangedEvent(int var1, int var2) {
      super(var1);
      this.mDrawerState = var2;
   }

   private WritableMap serializeEventData() {
      WritableMap var1 = Arguments.createMap();
      var1.putDouble("drawerState", (double)this.getDrawerState());
      return var1;
   }

   public void dispatch(RCTEventEmitter var1) {
      var1.receiveEvent(this.getViewTag(), this.getEventName(), this.serializeEventData());
   }

   public short getCoalescingKey() {
      return (short)0;
   }

   public int getDrawerState() {
      return this.mDrawerState;
   }

   public String getEventName() {
      return "topDrawerStateChanged";
   }
}
