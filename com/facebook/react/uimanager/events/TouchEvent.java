package com.facebook.react.uimanager.events;

import android.support.v4.util.Pools;
import android.view.MotionEvent;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.SoftAssertions;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.uimanager.events.TouchEventCoalescingKeyHelper;
import com.facebook.react.uimanager.events.TouchEventType;
import com.facebook.react.uimanager.events.TouchesHelper;
import javax.annotation.Nullable;

public class TouchEvent extends Event<TouchEvent> {

   private static final Pools.SynchronizedPool<TouchEvent> EVENTS_POOL = new Pools.SynchronizedPool(3);
   private static final int TOUCH_EVENTS_POOL_SIZE = 3;
   public static final long UNSET = Long.MIN_VALUE;
   private short mCoalescingKey;
   @Nullable
   private MotionEvent mMotionEvent;
   @Nullable
   private TouchEventType mTouchEventType;
   private float mViewX;
   private float mViewY;


   private void init(int var1, TouchEventType var2, MotionEvent var3, long var4, float var6, float var7, TouchEventCoalescingKeyHelper var8) {
      super.init(var1);
      short var9 = 0;
      boolean var10;
      if(var4 != Long.MIN_VALUE) {
         var10 = true;
      } else {
         var10 = false;
      }

      SoftAssertions.assertCondition(var10, "Gesture start time must be initialized");
      var1 = var3.getAction() & 255;
      switch(var1) {
      case 0:
         var8.addCoalescingKey(var4);
         break;
      case 1:
         var8.removeCoalescingKey(var4);
         break;
      case 2:
         var9 = var8.getCoalescingKey(var4);
         break;
      case 3:
         var8.removeCoalescingKey(var4);
         break;
      case 4:
      default:
         StringBuilder var11 = new StringBuilder();
         var11.append("Unhandled MotionEvent action: ");
         var11.append(var1);
         throw new RuntimeException(var11.toString());
      case 5:
      case 6:
         var8.incrementCoalescingKey(var4);
      }

      this.mTouchEventType = var2;
      this.mMotionEvent = MotionEvent.obtain(var3);
      this.mCoalescingKey = var9;
      this.mViewX = var6;
      this.mViewY = var7;
   }

   public static TouchEvent obtain(int var0, TouchEventType var1, MotionEvent var2, long var3, float var5, float var6, TouchEventCoalescingKeyHelper var7) {
      TouchEvent var9 = (TouchEvent)EVENTS_POOL.acquire();
      TouchEvent var8 = var9;
      if(var9 == null) {
         var8 = new TouchEvent();
      }

      var8.init(var0, var1, var2, var3, var5, var6, var7);
      return var8;
   }

   public boolean canCoalesce() {
      switch(null.$SwitchMap$com$facebook$react$uimanager$events$TouchEventType[((TouchEventType)Assertions.assertNotNull(this.mTouchEventType)).ordinal()]) {
      case 1:
      case 2:
      case 3:
         return false;
      case 4:
         return true;
      default:
         StringBuilder var1 = new StringBuilder();
         var1.append("Unknown touch event type: ");
         var1.append(this.mTouchEventType);
         throw new RuntimeException(var1.toString());
      }
   }

   public void dispatch(RCTEventEmitter var1) {
      TouchesHelper.sendTouchEvent(var1, (TouchEventType)Assertions.assertNotNull(this.mTouchEventType), this.getViewTag(), this);
   }

   public short getCoalescingKey() {
      return this.mCoalescingKey;
   }

   public String getEventName() {
      return ((TouchEventType)Assertions.assertNotNull(this.mTouchEventType)).getJSEventName();
   }

   public MotionEvent getMotionEvent() {
      Assertions.assertNotNull(this.mMotionEvent);
      return this.mMotionEvent;
   }

   public float getViewX() {
      return this.mViewX;
   }

   public float getViewY() {
      return this.mViewY;
   }

   public void onDispose() {
      ((MotionEvent)Assertions.assertNotNull(this.mMotionEvent)).recycle();
      this.mMotionEvent = null;
      EVENTS_POOL.release(this);
   }
}
