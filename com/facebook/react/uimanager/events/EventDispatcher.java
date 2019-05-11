package com.facebook.react.uimanager.events;

import android.util.LongSparseArray;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.modules.core.ChoreographerCompat;
import com.facebook.react.modules.core.ReactChoreographer;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.EventDispatcherListener;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.systrace.Systrace;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;

public class EventDispatcher implements LifecycleEventListener {

   private static final Comparator<Event> EVENT_COMPARATOR = new Comparator() {
      public int compare(Event var1, Event var2) {
         if(var1 == null && var2 == null) {
            return 0;
         } else if(var1 == null) {
            return -1;
         } else if(var2 == null) {
            return 1;
         } else {
            long var3 = var1.getTimestampMs() - var2.getTimestampMs();
            return var3 == 0L?0:(var3 < 0L?-1:1);
         }
      }
   };
   private final EventDispatcher.ScheduleDispatchFrameCallback mCurrentFrameCallback = new EventDispatcher.ScheduleDispatchFrameCallback(null);
   private final EventDispatcher.DispatchEventsRunnable mDispatchEventsRunnable = new EventDispatcher.DispatchEventsRunnable(null);
   private final LongSparseArray<Integer> mEventCookieToLastEventIdx = new LongSparseArray();
   private final Map<String, Short> mEventNameToEventId = MapBuilder.newHashMap();
   private final ArrayList<Event> mEventStaging = new ArrayList();
   private final Object mEventsStagingLock = new Object();
   private Event[] mEventsToDispatch = new Event[16];
   private final Object mEventsToDispatchLock = new Object();
   private int mEventsToDispatchSize = 0;
   private volatile boolean mHasDispatchScheduled = false;
   private final AtomicInteger mHasDispatchScheduledCount = new AtomicInteger();
   private final ArrayList<EventDispatcherListener> mListeners = new ArrayList();
   private short mNextEventTypeId = 0;
   @Nullable
   private volatile RCTEventEmitter mRCTEventEmitter;
   private final ReactApplicationContext mReactContext;


   public EventDispatcher(ReactApplicationContext var1) {
      this.mReactContext = var1;
      this.mReactContext.addLifecycleEventListener(this);
   }

   // $FF: synthetic method
   static RCTEventEmitter access$1000(EventDispatcher var0) {
      return var0.mRCTEventEmitter;
   }

   // $FF: synthetic method
   static Object access$1100(EventDispatcher var0) {
      return var0.mEventsToDispatchLock;
   }

   // $FF: synthetic method
   static Event[] access$1200(EventDispatcher var0) {
      return var0.mEventsToDispatch;
   }

   // $FF: synthetic method
   static Comparator access$1300() {
      return EVENT_COMPARATOR;
   }

   // $FF: synthetic method
   static void access$1400(EventDispatcher var0) {
      var0.clearEventsToDispatch();
   }

   // $FF: synthetic method
   static LongSparseArray access$1500(EventDispatcher var0) {
      return var0.mEventCookieToLastEventIdx;
   }

   private void addEventToEventsToDispatch(Event var1) {
      if(this.mEventsToDispatchSize == this.mEventsToDispatch.length) {
         this.mEventsToDispatch = (Event[])Arrays.copyOf(this.mEventsToDispatch, this.mEventsToDispatch.length * 2);
      }

      Event[] var3 = this.mEventsToDispatch;
      int var2 = this.mEventsToDispatchSize;
      this.mEventsToDispatchSize = var2 + 1;
      var3[var2] = var1;
   }

   private void clearEventsToDispatch() {
      Arrays.fill(this.mEventsToDispatch, 0, this.mEventsToDispatchSize, (Object)null);
      this.mEventsToDispatchSize = 0;
   }

   private long getEventCookie(int var1, String var2, short var3) {
      Short var5 = (Short)this.mEventNameToEventId.get(var2);
      short var4;
      if(var5 != null) {
         var4 = var5.shortValue();
      } else {
         var4 = this.mNextEventTypeId;
         this.mNextEventTypeId = (short)(var4 + 1);
         this.mEventNameToEventId.put(var2, Short.valueOf(var4));
      }

      return getEventCookie(var1, var4, var3);
   }

   private static long getEventCookie(int var0, short var1, short var2) {
      return (long)var0 | ((long)var1 & 65535L) << 32 | ((long)var2 & 65535L) << 48;
   }

   private void moveStagedEventsToDispatchQueue() {
      // $FF: Couldn't be decompiled
   }

   private void stopFrameCallback() {
      UiThreadUtil.assertOnUiThread();
      this.mCurrentFrameCallback.stop();
   }

   public void addListener(EventDispatcherListener var1) {
      this.mListeners.add(var1);
   }

   public void dispatchEvent(Event param1) {
      // $FF: Couldn't be decompiled
   }

   public void onCatalystInstanceDestroyed() {
      UiThreadUtil.runOnUiThread(new Runnable() {
         public void run() {
            EventDispatcher.this.stopFrameCallback();
         }
      });
   }

   public void onHostDestroy() {
      this.stopFrameCallback();
   }

   public void onHostPause() {
      this.stopFrameCallback();
   }

   public void onHostResume() {
      if(this.mRCTEventEmitter == null) {
         this.mRCTEventEmitter = (RCTEventEmitter)this.mReactContext.getJSModule(RCTEventEmitter.class);
      }

      this.mCurrentFrameCallback.maybePostFromNonUI();
   }

   public void removeListener(EventDispatcherListener var1) {
      this.mListeners.remove(var1);
   }

   class DispatchEventsRunnable implements Runnable {

      private DispatchEventsRunnable() {}

      // $FF: synthetic method
      DispatchEventsRunnable(Object var2) {
         this();
      }

      public void run() {
         // $FF: Couldn't be decompiled
      }
   }

   class ScheduleDispatchFrameCallback extends ChoreographerCompat.FrameCallback {

      private volatile boolean mIsPosted;
      private boolean mShouldStop;


      private ScheduleDispatchFrameCallback() {
         this.mIsPosted = false;
         this.mShouldStop = false;
      }

      // $FF: synthetic method
      ScheduleDispatchFrameCallback(Object var2) {
         this();
      }

      private void post() {
         ReactChoreographer.getInstance().postFrameCallback(ReactChoreographer.CallbackType.TIMERS_EVENTS, EventDispatcher.this.mCurrentFrameCallback);
      }

      public void doFrame(long var1) {
         UiThreadUtil.assertOnUiThread();
         if(this.mShouldStop) {
            this.mIsPosted = false;
         } else {
            this.post();
         }

         Systrace.beginSection(0L, "ScheduleDispatchFrameCallback");

         try {
            EventDispatcher.this.moveStagedEventsToDispatchQueue();
            if(EventDispatcher.this.mEventsToDispatchSize > 0 && !EventDispatcher.this.mHasDispatchScheduled) {
               EventDispatcher.this.mHasDispatchScheduled = true;
               Systrace.startAsyncFlow(0L, "ScheduleDispatchFrameCallback", EventDispatcher.this.mHasDispatchScheduledCount.get());
               EventDispatcher.this.mReactContext.runOnJSQueueThread(EventDispatcher.this.mDispatchEventsRunnable);
            }
         } finally {
            Systrace.endSection(0L);
         }

      }

      public void maybePost() {
         if(!this.mIsPosted) {
            this.mIsPosted = true;
            this.post();
         }

      }

      public void maybePostFromNonUI() {
         if(!this.mIsPosted) {
            if(EventDispatcher.this.mReactContext.isOnUiQueueThread()) {
               this.maybePost();
            } else {
               EventDispatcher.this.mReactContext.runOnUiQueueThread(new Runnable() {
                  public void run() {
                     ScheduleDispatchFrameCallback.this.maybePost();
                  }
               });
            }
         }
      }

      public void stop() {
         this.mShouldStop = true;
      }
   }
}
