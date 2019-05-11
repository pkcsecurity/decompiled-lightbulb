package com.facebook.react.views.modal;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

class ShowEvent extends Event<ShowEvent> {

   public static final String EVENT_NAME = "topShow";


   protected ShowEvent(int var1) {
      super(var1);
   }

   public void dispatch(RCTEventEmitter var1) {
      var1.receiveEvent(this.getViewTag(), this.getEventName(), (WritableMap)null);
   }

   public String getEventName() {
      return "topShow";
   }
}
