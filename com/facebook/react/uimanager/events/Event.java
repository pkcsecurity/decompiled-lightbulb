package com.facebook.react.uimanager.events;

import com.facebook.react.common.SystemClock;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public abstract class Event<T extends Event> {

   private static int sUniqueID;
   private boolean mInitialized;
   private long mTimestampMs;
   private int mUniqueID;
   private int mViewTag;


   protected Event() {
      int var1 = sUniqueID;
      sUniqueID = var1 + 1;
      this.mUniqueID = var1;
   }

   protected Event(int var1) {
      int var2 = sUniqueID;
      sUniqueID = var2 + 1;
      this.mUniqueID = var2;
      this.init(var1);
   }

   public boolean canCoalesce() {
      return true;
   }

   public T coalesce(T var1) {
      Event var2 = var1;
      if(this.getTimestampMs() >= var1.getTimestampMs()) {
         var2 = this;
      }

      return var2;
   }

   public abstract void dispatch(RCTEventEmitter var1);

   final void dispose() {
      this.mInitialized = false;
      this.onDispose();
   }

   public short getCoalescingKey() {
      return (short)0;
   }

   public abstract String getEventName();

   public final long getTimestampMs() {
      return this.mTimestampMs;
   }

   public int getUniqueID() {
      return this.mUniqueID;
   }

   public final int getViewTag() {
      return this.mViewTag;
   }

   protected void init(int var1) {
      this.mViewTag = var1;
      this.mTimestampMs = SystemClock.uptimeMillis();
      this.mInitialized = true;
   }

   boolean isInitialized() {
      return this.mInitialized;
   }

   public void onDispose() {}
}
