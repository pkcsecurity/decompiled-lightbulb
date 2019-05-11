package com.facebook.react.views.textinput;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class ReactTextChangedEvent extends Event<ReactTextChangedEvent> {

   public static final String EVENT_NAME = "topChange";
   private int mEventCount;
   private String mText;


   public ReactTextChangedEvent(int var1, String var2, int var3) {
      super(var1);
      this.mText = var2;
      this.mEventCount = var3;
   }

   private WritableMap serializeEventData() {
      WritableMap var1 = Arguments.createMap();
      var1.putString("text", this.mText);
      var1.putInt("eventCount", this.mEventCount);
      var1.putInt("target", this.getViewTag());
      return var1;
   }

   public void dispatch(RCTEventEmitter var1) {
      var1.receiveEvent(this.getViewTag(), this.getEventName(), this.serializeEventData());
   }

   public String getEventName() {
      return "topChange";
   }
}
