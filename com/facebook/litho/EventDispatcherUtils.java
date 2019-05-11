package com.facebook.litho;

import android.os.Bundle;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import com.facebook.litho.ClickEvent;
import com.facebook.litho.ComponentsSystrace;
import com.facebook.litho.DispatchPopulateAccessibilityEventEvent;
import com.facebook.litho.EventHandler;
import com.facebook.litho.FocusChangedEvent;
import com.facebook.litho.FocusedVisibleEvent;
import com.facebook.litho.FullImpressionVisibleEvent;
import com.facebook.litho.InterceptTouchEvent;
import com.facebook.litho.InvisibleEvent;
import com.facebook.litho.LongClickEvent;
import com.facebook.litho.OnInitializeAccessibilityEventEvent;
import com.facebook.litho.OnInitializeAccessibilityNodeInfoEvent;
import com.facebook.litho.OnPopulateAccessibilityEventEvent;
import com.facebook.litho.OnRequestSendAccessibilityEventEvent;
import com.facebook.litho.PerformAccessibilityActionEvent;
import com.facebook.litho.SendAccessibilityEventEvent;
import com.facebook.litho.SendAccessibilityEventUncheckedEvent;
import com.facebook.litho.ThreadUtils;
import com.facebook.litho.TouchEvent;
import com.facebook.litho.UnfocusedVisibleEvent;
import com.facebook.litho.VisibilityChangedEvent;
import com.facebook.litho.VisibleEvent;

class EventDispatcherUtils {

   private static ClickEvent sClickEvent;
   private static DispatchPopulateAccessibilityEventEvent sDispatchPopulateAccessibilityEventEvent;
   private static FocusChangedEvent sFocusChangedEvent;
   private static FocusedVisibleEvent sFocusedVisibleEvent;
   private static FullImpressionVisibleEvent sFullImpressionVisibleEvent;
   private static InterceptTouchEvent sInterceptTouchEvent;
   private static InvisibleEvent sInvisibleEvent;
   private static LongClickEvent sLongClickEvent;
   private static OnInitializeAccessibilityEventEvent sOnInitializeAccessibilityEventEvent;
   private static OnInitializeAccessibilityNodeInfoEvent sOnInitializeAccessibilityNodeInfoEvent;
   private static OnPopulateAccessibilityEventEvent sOnPopulateAccessibilityEventEvent;
   private static OnRequestSendAccessibilityEventEvent sOnRequestSendAccessibilityEventEvent;
   private static PerformAccessibilityActionEvent sPerformAccessibilityActionEvent;
   private static SendAccessibilityEventEvent sSendAccessibilityEventEvent;
   private static SendAccessibilityEventUncheckedEvent sSendAccessibilityEventUncheckedEvent;
   private static TouchEvent sTouchEvent;
   private static UnfocusedVisibleEvent sUnfocusedVisibleEvent;
   private static VisibleEvent sVisibleEvent;
   private static VisibilityChangedEvent sVisibleRectChangedEvent;


   static boolean dispatchDispatchPopulateAccessibilityEvent(EventHandler<DispatchPopulateAccessibilityEventEvent> var0, View var1, AccessibilityEvent var2, AccessibilityDelegateCompat var3) {
      ThreadUtils.assertMainThread();
      if(sDispatchPopulateAccessibilityEventEvent == null) {
         sDispatchPopulateAccessibilityEventEvent = new DispatchPopulateAccessibilityEventEvent();
      }

      sDispatchPopulateAccessibilityEventEvent.host = var1;
      sDispatchPopulateAccessibilityEventEvent.event = var2;
      sDispatchPopulateAccessibilityEventEvent.superDelegate = var3;
      boolean var4 = ((Boolean)var0.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(var0, sDispatchPopulateAccessibilityEventEvent)).booleanValue();
      sDispatchPopulateAccessibilityEventEvent.host = null;
      sDispatchPopulateAccessibilityEventEvent.event = null;
      sDispatchPopulateAccessibilityEventEvent.superDelegate = null;
      return var4;
   }

   static void dispatchOnClick(EventHandler<ClickEvent> var0, View var1) {
      ThreadUtils.assertMainThread();
      if(sClickEvent == null) {
         sClickEvent = new ClickEvent();
      }

      sClickEvent.view = var1;
      var0.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(var0, sClickEvent);
      sClickEvent.view = null;
   }

   static void dispatchOnFocusChanged(EventHandler<FocusChangedEvent> var0, View var1, boolean var2) {
      ThreadUtils.assertMainThread();
      if(sFocusChangedEvent == null) {
         sFocusChangedEvent = new FocusChangedEvent();
      }

      sFocusChangedEvent.view = var1;
      sFocusChangedEvent.hasFocus = var2;
      var0.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(var0, sFocusChangedEvent);
      sFocusChangedEvent.view = null;
   }

   static void dispatchOnFocused(EventHandler<FocusedVisibleEvent> var0) {
      ThreadUtils.assertMainThread();
      if(sFocusedVisibleEvent == null) {
         sFocusedVisibleEvent = new FocusedVisibleEvent();
      }

      var0.dispatchEvent(sFocusedVisibleEvent);
   }

   static void dispatchOnFullImpression(EventHandler<FullImpressionVisibleEvent> var0) {
      ThreadUtils.assertMainThread();
      if(sFullImpressionVisibleEvent == null) {
         sFullImpressionVisibleEvent = new FullImpressionVisibleEvent();
      }

      var0.dispatchEvent(sFullImpressionVisibleEvent);
   }

   static void dispatchOnInitializeAccessibilityEvent(EventHandler<OnInitializeAccessibilityEventEvent> var0, View var1, AccessibilityEvent var2, AccessibilityDelegateCompat var3) {
      ThreadUtils.assertMainThread();
      if(sOnInitializeAccessibilityEventEvent == null) {
         sOnInitializeAccessibilityEventEvent = new OnInitializeAccessibilityEventEvent();
      }

      sOnInitializeAccessibilityEventEvent.host = var1;
      sOnInitializeAccessibilityEventEvent.event = var2;
      sOnInitializeAccessibilityEventEvent.superDelegate = var3;
      var0.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(var0, sOnInitializeAccessibilityEventEvent);
      sOnInitializeAccessibilityEventEvent.host = null;
      sOnInitializeAccessibilityEventEvent.event = null;
      sOnInitializeAccessibilityEventEvent.superDelegate = null;
   }

   static void dispatchOnInitializeAccessibilityNodeInfoEvent(EventHandler<OnInitializeAccessibilityNodeInfoEvent> var0, View var1, AccessibilityNodeInfoCompat var2, AccessibilityDelegateCompat var3) {
      ThreadUtils.assertMainThread();
      if(sOnInitializeAccessibilityNodeInfoEvent == null) {
         sOnInitializeAccessibilityNodeInfoEvent = new OnInitializeAccessibilityNodeInfoEvent();
      }

      sOnInitializeAccessibilityNodeInfoEvent.host = var1;
      sOnInitializeAccessibilityNodeInfoEvent.info = var2;
      sOnInitializeAccessibilityNodeInfoEvent.superDelegate = var3;
      var0.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(var0, sOnInitializeAccessibilityNodeInfoEvent);
      sOnInitializeAccessibilityNodeInfoEvent.host = null;
      sOnInitializeAccessibilityNodeInfoEvent.info = null;
      sOnInitializeAccessibilityNodeInfoEvent.superDelegate = null;
   }

   static boolean dispatchOnInterceptTouch(EventHandler<InterceptTouchEvent> var0, View var1, MotionEvent var2) {
      ThreadUtils.assertMainThread();
      if(sInterceptTouchEvent == null) {
         sInterceptTouchEvent = new InterceptTouchEvent();
      }

      sInterceptTouchEvent.motionEvent = var2;
      sInterceptTouchEvent.view = var1;
      boolean var3 = ((Boolean)var0.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(var0, sInterceptTouchEvent)).booleanValue();
      sInterceptTouchEvent.motionEvent = null;
      sInterceptTouchEvent.view = null;
      return var3;
   }

   static void dispatchOnInvisible(EventHandler<InvisibleEvent> var0) {
      ThreadUtils.assertMainThread();
      if(sInvisibleEvent == null) {
         sInvisibleEvent = new InvisibleEvent();
      }

      var0.dispatchEvent(sInvisibleEvent);
   }

   static boolean dispatchOnLongClick(EventHandler<LongClickEvent> var0, View var1) {
      ThreadUtils.assertMainThread();
      if(sLongClickEvent == null) {
         sLongClickEvent = new LongClickEvent();
      }

      sLongClickEvent.view = var1;
      boolean var2 = ((Boolean)var0.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(var0, sLongClickEvent)).booleanValue();
      sLongClickEvent.view = null;
      return var2;
   }

   static void dispatchOnPopulateAccessibilityEvent(EventHandler<OnPopulateAccessibilityEventEvent> var0, View var1, AccessibilityEvent var2, AccessibilityDelegateCompat var3) {
      ThreadUtils.assertMainThread();
      if(sOnPopulateAccessibilityEventEvent == null) {
         sOnPopulateAccessibilityEventEvent = new OnPopulateAccessibilityEventEvent();
      }

      sOnPopulateAccessibilityEventEvent.host = var1;
      sOnPopulateAccessibilityEventEvent.event = var2;
      sOnPopulateAccessibilityEventEvent.superDelegate = var3;
      var0.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(var0, sOnPopulateAccessibilityEventEvent);
      sOnPopulateAccessibilityEventEvent.host = null;
      sOnPopulateAccessibilityEventEvent.event = null;
      sOnPopulateAccessibilityEventEvent.superDelegate = null;
   }

   static boolean dispatchOnRequestSendAccessibilityEvent(EventHandler<OnRequestSendAccessibilityEventEvent> var0, ViewGroup var1, View var2, AccessibilityEvent var3, AccessibilityDelegateCompat var4) {
      ThreadUtils.assertMainThread();
      if(sOnRequestSendAccessibilityEventEvent == null) {
         sOnRequestSendAccessibilityEventEvent = new OnRequestSendAccessibilityEventEvent();
      }

      sOnRequestSendAccessibilityEventEvent.host = var1;
      sOnRequestSendAccessibilityEventEvent.child = var2;
      sOnRequestSendAccessibilityEventEvent.event = var3;
      sOnRequestSendAccessibilityEventEvent.superDelegate = var4;
      boolean var5 = ((Boolean)var0.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(var0, sOnRequestSendAccessibilityEventEvent)).booleanValue();
      sOnRequestSendAccessibilityEventEvent.host = null;
      sOnRequestSendAccessibilityEventEvent.child = null;
      sOnRequestSendAccessibilityEventEvent.event = null;
      sOnRequestSendAccessibilityEventEvent.superDelegate = null;
      return var5;
   }

   static boolean dispatchOnTouch(EventHandler<TouchEvent> var0, View var1, MotionEvent var2) {
      ThreadUtils.assertMainThread();
      if(sTouchEvent == null) {
         sTouchEvent = new TouchEvent();
      }

      sTouchEvent.view = var1;
      sTouchEvent.motionEvent = var2;
      boolean var3 = ((Boolean)var0.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(var0, sTouchEvent)).booleanValue();
      sTouchEvent.view = null;
      sTouchEvent.motionEvent = null;
      return var3;
   }

   static void dispatchOnUnfocused(EventHandler<UnfocusedVisibleEvent> var0) {
      ThreadUtils.assertMainThread();
      if(sUnfocusedVisibleEvent == null) {
         sUnfocusedVisibleEvent = new UnfocusedVisibleEvent();
      }

      var0.dispatchEvent(sUnfocusedVisibleEvent);
   }

   static void dispatchOnVisibilityChanged(EventHandler<VisibilityChangedEvent> var0, int var1, int var2, float var3, float var4) {
      ThreadUtils.assertMainThread();
      if(sVisibleRectChangedEvent == null) {
         sVisibleRectChangedEvent = new VisibilityChangedEvent();
      }

      sVisibleRectChangedEvent.visibleHeight = var2;
      sVisibleRectChangedEvent.visibleWidth = var1;
      sVisibleRectChangedEvent.percentVisibleHeight = var4;
      sVisibleRectChangedEvent.percentVisibleWidth = var3;
      var0.dispatchEvent(sVisibleRectChangedEvent);
   }

   static void dispatchOnVisible(EventHandler<VisibleEvent> var0) {
      ThreadUtils.assertMainThread();
      boolean var1 = ComponentsSystrace.isTracing();
      if(var1) {
         ComponentsSystrace.beginSection("EventDispatcherUtils.dispatchOnVisible");
      }

      if(sVisibleEvent == null) {
         sVisibleEvent = new VisibleEvent();
      }

      var0.dispatchEvent(sVisibleEvent);
      if(var1) {
         ComponentsSystrace.endSection();
      }

   }

   static boolean dispatchPerformAccessibilityActionEvent(EventHandler<PerformAccessibilityActionEvent> var0, View var1, int var2, Bundle var3, AccessibilityDelegateCompat var4) {
      ThreadUtils.assertMainThread();
      if(sPerformAccessibilityActionEvent == null) {
         sPerformAccessibilityActionEvent = new PerformAccessibilityActionEvent();
      }

      sPerformAccessibilityActionEvent.host = var1;
      sPerformAccessibilityActionEvent.action = var2;
      sPerformAccessibilityActionEvent.args = var3;
      sPerformAccessibilityActionEvent.superDelegate = var4;
      boolean var5 = ((Boolean)var0.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(var0, sPerformAccessibilityActionEvent)).booleanValue();
      sPerformAccessibilityActionEvent.host = null;
      sPerformAccessibilityActionEvent.action = 0;
      sPerformAccessibilityActionEvent.args = null;
      sPerformAccessibilityActionEvent.superDelegate = null;
      return var5;
   }

   static void dispatchSendAccessibilityEvent(EventHandler<SendAccessibilityEventEvent> var0, View var1, int var2, AccessibilityDelegateCompat var3) {
      ThreadUtils.assertMainThread();
      if(sSendAccessibilityEventEvent == null) {
         sSendAccessibilityEventEvent = new SendAccessibilityEventEvent();
      }

      sSendAccessibilityEventEvent.host = var1;
      sSendAccessibilityEventEvent.eventType = var2;
      sSendAccessibilityEventEvent.superDelegate = var3;
      var0.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(var0, sSendAccessibilityEventEvent);
      sSendAccessibilityEventEvent.host = null;
      sSendAccessibilityEventEvent.eventType = 0;
      sSendAccessibilityEventEvent.superDelegate = null;
   }

   static void dispatchSendAccessibilityEventUnchecked(EventHandler<SendAccessibilityEventUncheckedEvent> var0, View var1, AccessibilityEvent var2, AccessibilityDelegateCompat var3) {
      ThreadUtils.assertMainThread();
      if(sSendAccessibilityEventUncheckedEvent == null) {
         sSendAccessibilityEventUncheckedEvent = new SendAccessibilityEventUncheckedEvent();
      }

      sSendAccessibilityEventUncheckedEvent.host = var1;
      sSendAccessibilityEventUncheckedEvent.event = var2;
      sSendAccessibilityEventUncheckedEvent.superDelegate = var3;
      var0.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(var0, sSendAccessibilityEventUncheckedEvent);
      sSendAccessibilityEventUncheckedEvent.host = null;
      sSendAccessibilityEventUncheckedEvent.event = null;
      sSendAccessibilityEventUncheckedEvent.superDelegate = null;
   }
}
