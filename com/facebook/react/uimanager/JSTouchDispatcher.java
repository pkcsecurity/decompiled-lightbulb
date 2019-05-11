package com.facebook.react.uimanager;

import android.view.MotionEvent;
import android.view.ViewGroup;
import com.facebook.common.logging.FLog;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.uimanager.TouchTargetHelper;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.uimanager.events.TouchEvent;
import com.facebook.react.uimanager.events.TouchEventCoalescingKeyHelper;
import com.facebook.react.uimanager.events.TouchEventType;

public class JSTouchDispatcher {

   private boolean mChildIsHandlingNativeGesture = false;
   private long mGestureStartTime = Long.MIN_VALUE;
   private final ViewGroup mRootViewGroup;
   private final float[] mTargetCoordinates = new float[2];
   private int mTargetTag = -1;
   private final TouchEventCoalescingKeyHelper mTouchEventCoalescingKeyHelper = new TouchEventCoalescingKeyHelper();


   public JSTouchDispatcher(ViewGroup var1) {
      this.mRootViewGroup = var1;
   }

   private void dispatchCancelEvent(MotionEvent var1, EventDispatcher var2) {
      if(this.mTargetTag == -1) {
         FLog.w("ReactNative", "Can\'t cancel already finished gesture. Is a child View trying to start a gesture from an UP/CANCEL event?");
      } else {
         Assertions.assertCondition(this.mChildIsHandlingNativeGesture ^ true, "Expected to not have already sent a cancel for this gesture");
         ((EventDispatcher)Assertions.assertNotNull(var2)).dispatchEvent(TouchEvent.obtain(this.mTargetTag, TouchEventType.CANCEL, var1, this.mGestureStartTime, this.mTargetCoordinates[0], this.mTargetCoordinates[1], this.mTouchEventCoalescingKeyHelper));
      }
   }

   private int findTargetTagAndSetCoordinates(MotionEvent var1) {
      return TouchTargetHelper.findTargetTagAndCoordinatesForTouch(var1.getX(), var1.getY(), this.mRootViewGroup, this.mTargetCoordinates, (int[])null);
   }

   public void handleTouchEvent(MotionEvent var1, EventDispatcher var2) {
      int var3 = var1.getAction() & 255;
      if(var3 == 0) {
         if(this.mTargetTag != -1) {
            FLog.e("ReactNative", "Got DOWN touch before receiving UP or CANCEL from last gesture");
         }

         this.mChildIsHandlingNativeGesture = false;
         this.mGestureStartTime = var1.getEventTime();
         this.mTargetTag = this.findTargetTagAndSetCoordinates(var1);
         var2.dispatchEvent(TouchEvent.obtain(this.mTargetTag, TouchEventType.START, var1, this.mGestureStartTime, this.mTargetCoordinates[0], this.mTargetCoordinates[1], this.mTouchEventCoalescingKeyHelper));
      } else if(!this.mChildIsHandlingNativeGesture) {
         if(this.mTargetTag == -1) {
            FLog.e("ReactNative", "Unexpected state: received touch event but didn\'t get starting ACTION_DOWN for this gesture before");
         } else if(var3 == 1) {
            this.findTargetTagAndSetCoordinates(var1);
            var2.dispatchEvent(TouchEvent.obtain(this.mTargetTag, TouchEventType.END, var1, this.mGestureStartTime, this.mTargetCoordinates[0], this.mTargetCoordinates[1], this.mTouchEventCoalescingKeyHelper));
            this.mTargetTag = -1;
            this.mGestureStartTime = Long.MIN_VALUE;
         } else if(var3 == 2) {
            this.findTargetTagAndSetCoordinates(var1);
            var2.dispatchEvent(TouchEvent.obtain(this.mTargetTag, TouchEventType.MOVE, var1, this.mGestureStartTime, this.mTargetCoordinates[0], this.mTargetCoordinates[1], this.mTouchEventCoalescingKeyHelper));
         } else if(var3 == 5) {
            var2.dispatchEvent(TouchEvent.obtain(this.mTargetTag, TouchEventType.START, var1, this.mGestureStartTime, this.mTargetCoordinates[0], this.mTargetCoordinates[1], this.mTouchEventCoalescingKeyHelper));
         } else if(var3 == 6) {
            var2.dispatchEvent(TouchEvent.obtain(this.mTargetTag, TouchEventType.END, var1, this.mGestureStartTime, this.mTargetCoordinates[0], this.mTargetCoordinates[1], this.mTouchEventCoalescingKeyHelper));
         } else if(var3 == 3) {
            if(this.mTouchEventCoalescingKeyHelper.hasCoalescingKey(var1.getDownTime())) {
               this.dispatchCancelEvent(var1, var2);
            } else {
               FLog.e("ReactNative", "Received an ACTION_CANCEL touch event for which we have no corresponding ACTION_DOWN");
            }

            this.mTargetTag = -1;
            this.mGestureStartTime = Long.MIN_VALUE;
         } else {
            StringBuilder var4 = new StringBuilder();
            var4.append("Warning : touch event was ignored. Action=");
            var4.append(var3);
            var4.append(" Target=");
            var4.append(this.mTargetTag);
            FLog.w("ReactNative", var4.toString());
         }
      }
   }

   public void onChildStartedNativeGesture(MotionEvent var1, EventDispatcher var2) {
      if(!this.mChildIsHandlingNativeGesture) {
         this.dispatchCancelEvent(var1, var2);
         this.mChildIsHandlingNativeGesture = true;
         this.mTargetTag = -1;
      }
   }
}
