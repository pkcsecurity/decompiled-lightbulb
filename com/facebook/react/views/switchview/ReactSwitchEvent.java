package com.facebook.react.views.switchview;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

class ReactSwitchEvent extends Event<ReactSwitchEvent> {

   public static final String EVENT_NAME = "topChange";
   private final boolean mIsChecked;


   public ReactSwitchEvent(int var1, boolean var2) {
      super(var1);
      this.mIsChecked = var2;
   }

   private WritableMap serializeEventData() {
      WritableMap var1 = Arguments.createMap();
      var1.putInt("target", this.getViewTag());
      var1.putBoolean("value", this.getIsChecked());
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

   public boolean getIsChecked() {
      return this.mIsChecked;
   }
}
