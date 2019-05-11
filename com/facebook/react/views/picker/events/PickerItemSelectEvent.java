package com.facebook.react.views.picker.events;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class PickerItemSelectEvent extends Event<PickerItemSelectEvent> {

   public static final String EVENT_NAME = "topSelect";
   private final int mPosition;


   public PickerItemSelectEvent(int var1, int var2) {
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
      return "topSelect";
   }
}
