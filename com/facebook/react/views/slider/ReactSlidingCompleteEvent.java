package com.facebook.react.views.slider;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class ReactSlidingCompleteEvent extends Event<ReactSlidingCompleteEvent> {

   public static final String EVENT_NAME = "topSlidingComplete";
   private final double mValue;


   public ReactSlidingCompleteEvent(int var1, double var2) {
      super(var1);
      this.mValue = var2;
   }

   private WritableMap serializeEventData() {
      WritableMap var1 = Arguments.createMap();
      var1.putInt("target", this.getViewTag());
      var1.putDouble("value", this.getValue());
      return var1;
   }

   public boolean canCoalesce() {
      return false;
   }

   public void dispatch(RCTEventEmitter var1) {
      var1.receiveEvent(this.getViewTag(), this.getEventName(), this.serializeEventData());
   }

   public short getCoalescingKey() {
      return (short)0;
   }

   public String getEventName() {
      return "topSlidingComplete";
   }

   public double getValue() {
      return this.mValue;
   }
}
