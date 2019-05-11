package com.facebook.react.views.textinput;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

class ReactTextInputEndEditingEvent extends Event<ReactTextInputEndEditingEvent> {

   private static final String EVENT_NAME = "topEndEditing";
   private String mText;


   public ReactTextInputEndEditingEvent(int var1, String var2) {
      super(var1);
      this.mText = var2;
   }

   private WritableMap serializeEventData() {
      WritableMap var1 = Arguments.createMap();
      var1.putInt("target", this.getViewTag());
      var1.putString("text", this.mText);
      return var1;
   }

   public boolean canCoalesce() {
      return false;
   }

   public void dispatch(RCTEventEmitter var1) {
      var1.receiveEvent(this.getViewTag(), this.getEventName(), this.serializeEventData());
   }

   public String getEventName() {
      return "topEndEditing";
   }
}
