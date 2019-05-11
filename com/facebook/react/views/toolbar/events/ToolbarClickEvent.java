package com.facebook.react.views.toolbar.events;

import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class ToolbarClickEvent extends Event<ToolbarClickEvent> {

   private static final String EVENT_NAME = "topSelect";
   private final int position;


   public ToolbarClickEvent(int var1, int var2) {
      super(var1);
      this.position = var2;
   }

   public boolean canCoalesce() {
      return false;
   }

   public void dispatch(RCTEventEmitter var1) {
      WritableNativeMap var2 = new WritableNativeMap();
      var2.putInt("position", this.getPosition());
      var1.receiveEvent(this.getViewTag(), this.getEventName(), var2);
   }

   public String getEventName() {
      return "topSelect";
   }

   public int getPosition() {
      return this.position;
   }
}
