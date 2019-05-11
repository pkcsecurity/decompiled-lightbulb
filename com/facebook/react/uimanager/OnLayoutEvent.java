package com.facebook.react.uimanager;

import android.support.v4.util.Pools;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class OnLayoutEvent extends Event<OnLayoutEvent> {

   private static final Pools.SynchronizedPool<OnLayoutEvent> EVENTS_POOL = new Pools.SynchronizedPool(20);
   private int mHeight;
   private int mWidth;
   private int mX;
   private int mY;


   public static OnLayoutEvent obtain(int var0, int var1, int var2, int var3, int var4) {
      OnLayoutEvent var6 = (OnLayoutEvent)EVENTS_POOL.acquire();
      OnLayoutEvent var5 = var6;
      if(var6 == null) {
         var5 = new OnLayoutEvent();
      }

      var5.init(var0, var1, var2, var3, var4);
      return var5;
   }

   public void dispatch(RCTEventEmitter var1) {
      WritableMap var2 = Arguments.createMap();
      var2.putDouble("x", (double)PixelUtil.toDIPFromPixel((float)this.mX));
      var2.putDouble("y", (double)PixelUtil.toDIPFromPixel((float)this.mY));
      var2.putDouble("width", (double)PixelUtil.toDIPFromPixel((float)this.mWidth));
      var2.putDouble("height", (double)PixelUtil.toDIPFromPixel((float)this.mHeight));
      WritableMap var3 = Arguments.createMap();
      var3.putMap("layout", var2);
      var3.putInt("target", this.getViewTag());
      var1.receiveEvent(this.getViewTag(), this.getEventName(), var3);
   }

   public String getEventName() {
      return "topLayout";
   }

   protected void init(int var1, int var2, int var3, int var4, int var5) {
      super.init(var1);
      this.mX = var2;
      this.mY = var3;
      this.mWidth = var4;
      this.mHeight = var5;
   }

   public void onDispose() {
      EVENTS_POOL.release(this);
   }
}
