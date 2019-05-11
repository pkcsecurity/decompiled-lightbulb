package com.facebook.react.views.viewpager;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

class PageSelectedEvent extends Event<PageSelectedEvent> {

   public static final String EVENT_NAME = "topPageSelected";
   private final int mPosition;


   protected PageSelectedEvent(int var1, int var2) {
      super(var1);
      this.mPosition = var2;
   }

   private WritableMap serializeEventData() {
      WritableMap var1 = Arguments.createMap();
      var1.putInt("position", this.mPosition);
      return var1;
   }

   public void dispatch(RCTEventEmitter var1) {
      var1.receiveEvent(this.getViewTag(), this.getEventName(), this.serializeEventData());
   }

   public String getEventName() {
      return "topPageSelected";
   }
}
