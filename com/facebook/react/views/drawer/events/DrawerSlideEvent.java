package com.facebook.react.views.drawer.events;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class DrawerSlideEvent extends Event<DrawerSlideEvent> {

   public static final String EVENT_NAME = "topDrawerSlide";
   private final float mOffset;


   public DrawerSlideEvent(int var1, float var2) {
      super(var1);
      this.mOffset = var2;
   }

   private WritableMap serializeEventData() {
      WritableMap var1 = Arguments.createMap();
      var1.putDouble("offset", (double)this.getOffset());
      return var1;
   }

   public void dispatch(RCTEventEmitter var1) {
      var1.receiveEvent(this.getViewTag(), this.getEventName(), this.serializeEventData());
   }

   public short getCoalescingKey() {
      return (short)0;
   }

   public String getEventName() {
      return "topDrawerSlide";
   }

   public float getOffset() {
      return this.mOffset;
   }
}
