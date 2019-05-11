package com.facebook.react.views.viewpager;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

class PageScrollEvent extends Event<PageScrollEvent> {

   public static final String EVENT_NAME = "topPageScroll";
   private final float mOffset;
   private final int mPosition;


   protected PageScrollEvent(int var1, int var2, float var3) {
      float var4;
      label11: {
         super(var1);
         this.mPosition = var2;
         if(!Float.isInfinite(var3)) {
            var4 = var3;
            if(!Float.isNaN(var3)) {
               break label11;
            }
         }

         var4 = 0.0F;
      }

      this.mOffset = var4;
   }

   private WritableMap serializeEventData() {
      WritableMap var1 = Arguments.createMap();
      var1.putInt("position", this.mPosition);
      var1.putDouble("offset", (double)this.mOffset);
      return var1;
   }

   public void dispatch(RCTEventEmitter var1) {
      var1.receiveEvent(this.getViewTag(), this.getEventName(), this.serializeEventData());
   }

   public String getEventName() {
      return "topPageScroll";
   }
}
