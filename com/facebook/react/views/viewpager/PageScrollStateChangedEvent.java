package com.facebook.react.views.viewpager;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

class PageScrollStateChangedEvent extends Event<PageScrollStateChangedEvent> {

   public static final String EVENT_NAME = "topPageScrollStateChanged";
   private final String mPageScrollState;


   protected PageScrollStateChangedEvent(int var1, String var2) {
      super(var1);
      this.mPageScrollState = var2;
   }

   private WritableMap serializeEventData() {
      WritableMap var1 = Arguments.createMap();
      var1.putString("pageScrollState", this.mPageScrollState);
      return var1;
   }

   public void dispatch(RCTEventEmitter var1) {
      var1.receiveEvent(this.getViewTag(), this.getEventName(), this.serializeEventData());
   }

   public String getEventName() {
      return "topPageScrollStateChanged";
   }
}
