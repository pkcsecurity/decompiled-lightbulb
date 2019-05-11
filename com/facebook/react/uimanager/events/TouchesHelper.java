package com.facebook.react.uimanager.events;

import android.view.MotionEvent;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.uimanager.events.TouchEvent;
import com.facebook.react.uimanager.events.TouchEventType;

class TouchesHelper {

   private static final String LOCATION_X_KEY = "locationX";
   private static final String LOCATION_Y_KEY = "locationY";
   private static final String PAGE_X_KEY = "pageX";
   private static final String PAGE_Y_KEY = "pageY";
   private static final String POINTER_IDENTIFIER_KEY = "identifier";
   private static final String TARGET_KEY = "target";
   private static final String TIMESTAMP_KEY = "timestamp";


   private static WritableArray createsPointersArray(int var0, TouchEvent var1) {
      WritableArray var9 = Arguments.createArray();
      MotionEvent var10 = var1.getMotionEvent();
      float var2 = var10.getX();
      float var3 = var1.getViewX();
      float var4 = var10.getY();
      float var5 = var1.getViewY();

      for(int var8 = 0; var8 < var10.getPointerCount(); ++var8) {
         WritableMap var11 = Arguments.createMap();
         var11.putDouble("pageX", (double)PixelUtil.toDIPFromPixel(var10.getX(var8)));
         var11.putDouble("pageY", (double)PixelUtil.toDIPFromPixel(var10.getY(var8)));
         float var6 = var10.getX(var8);
         float var7 = var10.getY(var8);
         var11.putDouble("locationX", (double)PixelUtil.toDIPFromPixel(var6 - (var2 - var3)));
         var11.putDouble("locationY", (double)PixelUtil.toDIPFromPixel(var7 - (var4 - var5)));
         var11.putInt("target", var0);
         var11.putDouble("timestamp", (double)var1.getTimestampMs());
         var11.putDouble("identifier", (double)var10.getPointerId(var8));
         var9.pushMap(var11);
      }

      return var9;
   }

   public static void sendTouchEvent(RCTEventEmitter var0, TouchEventType var1, int var2, TouchEvent var3) {
      WritableArray var4 = createsPointersArray(var2, var3);
      MotionEvent var7 = var3.getMotionEvent();
      WritableArray var5 = Arguments.createArray();
      if(var1 != TouchEventType.MOVE && var1 != TouchEventType.CANCEL) {
         if(var1 != TouchEventType.START && var1 != TouchEventType.END) {
            StringBuilder var6 = new StringBuilder();
            var6.append("Unknown touch type: ");
            var6.append(var1);
            throw new RuntimeException(var6.toString());
         }

         var5.pushInt(var7.getActionIndex());
      } else {
         for(var2 = 0; var2 < var7.getPointerCount(); ++var2) {
            var5.pushInt(var2);
         }
      }

      var0.receiveTouches(var1.getJSEventName(), var4, var5);
   }
}
