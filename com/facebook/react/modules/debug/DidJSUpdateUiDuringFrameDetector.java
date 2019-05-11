package com.facebook.react.modules.debug;

import com.facebook.react.bridge.NotThreadSafeBridgeIdleDebugListener;
import com.facebook.react.common.LongArray;
import com.facebook.react.uimanager.debug.NotThreadSafeViewHierarchyUpdateDebugListener;

public class DidJSUpdateUiDuringFrameDetector implements NotThreadSafeBridgeIdleDebugListener, NotThreadSafeViewHierarchyUpdateDebugListener {

   private final LongArray mTransitionToBusyEvents = LongArray.createWithInitialCapacity(20);
   private final LongArray mTransitionToIdleEvents = LongArray.createWithInitialCapacity(20);
   private final LongArray mViewHierarchyUpdateEnqueuedEvents = LongArray.createWithInitialCapacity(20);
   private final LongArray mViewHierarchyUpdateFinishedEvents = LongArray.createWithInitialCapacity(20);
   private volatile boolean mWasIdleAtEndOfLastFrame = true;


   private static void cleanUp(LongArray var0, long var1) {
      int var7 = var0.size();
      byte var6 = 0;
      int var4 = 0;

      int var3;
      int var5;
      for(var3 = 0; var4 < var7; var3 = var5) {
         var5 = var3;
         if(var0.get(var4) < var1) {
            var5 = var3 + 1;
         }

         ++var4;
      }

      if(var3 > 0) {
         for(var4 = var6; var4 < var7 - var3; ++var4) {
            var0.set(var4, var0.get(var4 + var3));
         }

         var0.dropTail(var3);
      }

   }

   private boolean didEndFrameIdle(long var1, long var3) {
      long var5 = getLastEventBetweenTimestamps(this.mTransitionToIdleEvents, var1, var3);
      var1 = getLastEventBetweenTimestamps(this.mTransitionToBusyEvents, var1, var3);
      return var5 == -1L && var1 == -1L?this.mWasIdleAtEndOfLastFrame:var5 > var1;
   }

   private static long getLastEventBetweenTimestamps(LongArray var0, long var1, long var3) {
      long var6 = -1L;

      long var8;
      for(int var5 = 0; var5 < var0.size(); var6 = var8) {
         long var10 = var0.get(var5);
         if(var10 >= var1 && var10 < var3) {
            var8 = var10;
         } else {
            var8 = var6;
            if(var10 >= var3) {
               return var6;
            }
         }

         ++var5;
      }

      return var6;
   }

   private static boolean hasEventBetweenTimestamps(LongArray var0, long var1, long var3) {
      for(int var5 = 0; var5 < var0.size(); ++var5) {
         long var6 = var0.get(var5);
         if(var6 >= var1 && var6 < var3) {
            return true;
         }
      }

      return false;
   }

   public boolean getDidJSHitFrameAndCleanup(long param1, long param3) {
      // $FF: Couldn't be decompiled
   }

   public void onTransitionToBridgeBusy() {
      synchronized(this){}

      try {
         this.mTransitionToBusyEvents.add(System.nanoTime());
      } finally {
         ;
      }

   }

   public void onTransitionToBridgeIdle() {
      synchronized(this){}

      try {
         this.mTransitionToIdleEvents.add(System.nanoTime());
      } finally {
         ;
      }

   }

   public void onViewHierarchyUpdateEnqueued() {
      synchronized(this){}

      try {
         this.mViewHierarchyUpdateEnqueuedEvents.add(System.nanoTime());
      } finally {
         ;
      }

   }

   public void onViewHierarchyUpdateFinished() {
      synchronized(this){}

      try {
         this.mViewHierarchyUpdateFinishedEvents.add(System.nanoTime());
      } finally {
         ;
      }

   }
}
