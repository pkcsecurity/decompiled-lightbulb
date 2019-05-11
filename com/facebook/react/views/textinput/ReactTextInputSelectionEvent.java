package com.facebook.react.views.textinput;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

class ReactTextInputSelectionEvent extends Event<ReactTextInputSelectionEvent> {

   private static final String EVENT_NAME = "topSelectionChange";
   private int mSelectionEnd;
   private int mSelectionStart;


   public ReactTextInputSelectionEvent(int var1, int var2, int var3) {
      super(var1);
      this.mSelectionStart = var2;
      this.mSelectionEnd = var3;
   }

   private WritableMap serializeEventData() {
      WritableMap var1 = Arguments.createMap();
      WritableMap var2 = Arguments.createMap();
      var2.putInt("end", this.mSelectionEnd);
      var2.putInt("start", this.mSelectionStart);
      var1.putMap("selection", var2);
      return var1;
   }

   public void dispatch(RCTEventEmitter var1) {
      var1.receiveEvent(this.getViewTag(), this.getEventName(), this.serializeEventData());
   }

   public String getEventName() {
      return "topSelectionChange";
   }
}
