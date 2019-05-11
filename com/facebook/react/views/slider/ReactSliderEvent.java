package com.facebook.react.views.slider;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class ReactSliderEvent extends Event<ReactSliderEvent> {

   public static final String EVENT_NAME = "topChange";
   private final boolean mFromUser;
   private final double mValue;


   public ReactSliderEvent(int var1, double var2, boolean var4) {
      super(var1);
      this.mValue = var2;
      this.mFromUser = var4;
   }

   private WritableMap serializeEventData() {
      WritableMap var1 = Arguments.createMap();
      var1.putInt("target", this.getViewTag());
      var1.putDouble("value", this.getValue());
      var1.putBoolean("fromUser", this.isFromUser());
      return var1;
   }

   public void dispatch(RCTEventEmitter var1) {
      var1.receiveEvent(this.getViewTag(), this.getEventName(), this.serializeEventData());
   }

   public short getCoalescingKey() {
      return (short)0;
   }

   public String getEventName() {
      return "topChange";
   }

   public double getValue() {
      return this.mValue;
   }

   public boolean isFromUser() {
      return this.mFromUser;
   }
}
