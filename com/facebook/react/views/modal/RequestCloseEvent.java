package com.facebook.react.views.modal;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

class RequestCloseEvent extends Event<RequestCloseEvent> {

   public static final String EVENT_NAME = "topRequestClose";


   protected RequestCloseEvent(int var1) {
      super(var1);
   }

   public void dispatch(RCTEventEmitter var1) {
      var1.receiveEvent(this.getViewTag(), this.getEventName(), (WritableMap)null);
   }

   public String getEventName() {
      return "topRequestClose";
   }
}
