package com.facebook.react.views.textinput;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

class ReactTextInputBlurEvent extends Event<ReactTextInputBlurEvent> {

   private static final String EVENT_NAME = "topBlur";


   public ReactTextInputBlurEvent(int var1) {
      super(var1);
   }

   private WritableMap serializeEventData() {
      WritableMap var1 = Arguments.createMap();
      var1.putInt("target", this.getViewTag());
      return var1;
   }

   public boolean canCoalesce() {
      return false;
   }

   public void dispatch(RCTEventEmitter var1) {
      var1.receiveEvent(this.getViewTag(), this.getEventName(), this.serializeEventData());
   }

   public String getEventName() {
      return "topBlur";
   }
}
