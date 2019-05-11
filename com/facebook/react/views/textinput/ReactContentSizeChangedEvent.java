package com.facebook.react.views.textinput;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.textinput.ReactTextChangedEvent;

public class ReactContentSizeChangedEvent extends Event<ReactTextChangedEvent> {

   public static final String EVENT_NAME = "topContentSizeChange";
   private float mContentHeight;
   private float mContentWidth;


   public ReactContentSizeChangedEvent(int var1, float var2, float var3) {
      super(var1);
      this.mContentWidth = var2;
      this.mContentHeight = var3;
   }

   private WritableMap serializeEventData() {
      WritableMap var1 = Arguments.createMap();
      WritableMap var2 = Arguments.createMap();
      var2.putDouble("width", (double)this.mContentWidth);
      var2.putDouble("height", (double)this.mContentHeight);
      var1.putMap("contentSize", var2);
      var1.putInt("target", this.getViewTag());
      return var1;
   }

   public void dispatch(RCTEventEmitter var1) {
      var1.receiveEvent(this.getViewTag(), this.getEventName(), this.serializeEventData());
   }

   public String getEventName() {
      return "topContentSizeChange";
   }
}
