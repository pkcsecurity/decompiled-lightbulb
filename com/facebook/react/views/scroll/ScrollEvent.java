package com.facebook.react.views.scroll;

import android.support.v4.util.Pools;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.scroll.ScrollEventType;
import javax.annotation.Nullable;

public class ScrollEvent extends Event<ScrollEvent> {

   private static final Pools.SynchronizedPool<ScrollEvent> EVENTS_POOL = new Pools.SynchronizedPool(3);
   private int mContentHeight;
   private int mContentWidth;
   @Nullable
   private ScrollEventType mScrollEventType;
   private int mScrollViewHeight;
   private int mScrollViewWidth;
   private int mScrollX;
   private int mScrollY;
   private double mXVelocity;
   private double mYVelocity;


   private void init(int var1, ScrollEventType var2, int var3, int var4, float var5, float var6, int var7, int var8, int var9, int var10) {
      super.init(var1);
      this.mScrollEventType = var2;
      this.mScrollX = var3;
      this.mScrollY = var4;
      this.mXVelocity = (double)var5;
      this.mYVelocity = (double)var6;
      this.mContentWidth = var7;
      this.mContentHeight = var8;
      this.mScrollViewWidth = var9;
      this.mScrollViewHeight = var10;
   }

   public static ScrollEvent obtain(int var0, ScrollEventType var1, int var2, int var3, float var4, float var5, int var6, int var7, int var8, int var9) {
      ScrollEvent var11 = (ScrollEvent)EVENTS_POOL.acquire();
      ScrollEvent var10 = var11;
      if(var11 == null) {
         var10 = new ScrollEvent();
      }

      var10.init(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
      return var10;
   }

   private WritableMap serializeEventData() {
      WritableMap var1 = Arguments.createMap();
      var1.putDouble("top", 0.0D);
      var1.putDouble("bottom", 0.0D);
      var1.putDouble("left", 0.0D);
      var1.putDouble("right", 0.0D);
      WritableMap var2 = Arguments.createMap();
      var2.putDouble("x", (double)PixelUtil.toDIPFromPixel((float)this.mScrollX));
      var2.putDouble("y", (double)PixelUtil.toDIPFromPixel((float)this.mScrollY));
      WritableMap var3 = Arguments.createMap();
      var3.putDouble("width", (double)PixelUtil.toDIPFromPixel((float)this.mContentWidth));
      var3.putDouble("height", (double)PixelUtil.toDIPFromPixel((float)this.mContentHeight));
      WritableMap var4 = Arguments.createMap();
      var4.putDouble("width", (double)PixelUtil.toDIPFromPixel((float)this.mScrollViewWidth));
      var4.putDouble("height", (double)PixelUtil.toDIPFromPixel((float)this.mScrollViewHeight));
      WritableMap var5 = Arguments.createMap();
      var5.putDouble("x", this.mXVelocity);
      var5.putDouble("y", this.mYVelocity);
      WritableMap var6 = Arguments.createMap();
      var6.putMap("contentInset", var1);
      var6.putMap("contentOffset", var2);
      var6.putMap("contentSize", var3);
      var6.putMap("layoutMeasurement", var4);
      var6.putMap("velocity", var5);
      var6.putInt("target", this.getViewTag());
      var6.putBoolean("responderIgnoreScroll", true);
      return var6;
   }

   public boolean canCoalesce() {
      return this.mScrollEventType == ScrollEventType.SCROLL;
   }

   public void dispatch(RCTEventEmitter var1) {
      var1.receiveEvent(this.getViewTag(), this.getEventName(), this.serializeEventData());
   }

   public short getCoalescingKey() {
      return (short)0;
   }

   public String getEventName() {
      return ((ScrollEventType)Assertions.assertNotNull(this.mScrollEventType)).getJSEventName();
   }

   public void onDispose() {
      EVENTS_POOL.release(this);
   }
}
