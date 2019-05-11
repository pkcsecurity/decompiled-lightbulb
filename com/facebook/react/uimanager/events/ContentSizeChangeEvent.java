package com.facebook.react.uimanager.events;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class ContentSizeChangeEvent extends Event<ContentSizeChangeEvent> {

   public static final String EVENT_NAME = "topContentSizeChange";
   private final int mHeight;
   private final int mWidth;


   public ContentSizeChangeEvent(int var1, int var2, int var3) {
      super(var1);
      this.mWidth = var2;
      this.mHeight = var3;
   }

   public void dispatch(RCTEventEmitter var1) {
      WritableMap var2 = Arguments.createMap();
      var2.putDouble("width", (double)PixelUtil.toDIPFromPixel((float)this.mWidth));
      var2.putDouble("height", (double)PixelUtil.toDIPFromPixel((float)this.mHeight));
      var1.receiveEvent(this.getViewTag(), "topContentSizeChange", var2);
   }

   public String getEventName() {
      return "topContentSizeChange";
   }
}
