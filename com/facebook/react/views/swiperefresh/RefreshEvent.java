package com.facebook.react.views.swiperefresh;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class RefreshEvent extends Event<RefreshEvent> {

   protected RefreshEvent(int var1) {
      super(var1);
   }

   public void dispatch(RCTEventEmitter var1) {
      var1.receiveEvent(this.getViewTag(), this.getEventName(), (WritableMap)null);
   }

   public String getEventName() {
      return "topRefresh";
   }
}
