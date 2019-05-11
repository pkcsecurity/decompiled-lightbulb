package com.facebook.react.modules.debug;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.ChoreographerCompat;
import com.facebook.react.modules.debug.DidJSUpdateUiDuringFrameDetector;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.debug.NotThreadSafeViewHierarchyUpdateDebugListener;
import java.util.TreeMap;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class FpsDebugFrameCallback extends ChoreographerCompat.FrameCallback {

   private static final double EXPECTED_FRAME_TIME = 16.9D;
   private int m4PlusFrameStutters = 0;
   private final ChoreographerCompat mChoreographer;
   private final DidJSUpdateUiDuringFrameDetector mDidJSUpdateUiDuringFrameDetector;
   private int mExpectedNumFramesPrev = 0;
   private long mFirstFrameTime = -1L;
   private boolean mIsRecordingFpsInfoAtEachFrame = false;
   private long mLastFrameTime = -1L;
   private int mNumFrameCallbacks = 0;
   private int mNumFrameCallbacksWithBatchDispatches = 0;
   private final ReactContext mReactContext;
   private boolean mShouldStop = false;
   @Nullable
   private TreeMap<Long, FpsDebugFrameCallback.FpsInfo> mTimeToFps;
   private final UIManagerModule mUIManagerModule;


   public FpsDebugFrameCallback(ChoreographerCompat var1, ReactContext var2) {
      this.mChoreographer = var1;
      this.mReactContext = var2;
      this.mUIManagerModule = (UIManagerModule)var2.getNativeModule(UIManagerModule.class);
      this.mDidJSUpdateUiDuringFrameDetector = new DidJSUpdateUiDuringFrameDetector();
   }

   public void doFrame(long var1) {
      if(!this.mShouldStop) {
         if(this.mFirstFrameTime == -1L) {
            this.mFirstFrameTime = var1;
         }

         long var4 = this.mLastFrameTime;
         this.mLastFrameTime = var1;
         if(this.mDidJSUpdateUiDuringFrameDetector.getDidJSHitFrameAndCleanup(var4, var1)) {
            ++this.mNumFrameCallbacksWithBatchDispatches;
         }

         ++this.mNumFrameCallbacks;
         int var3 = this.getExpectedNumFrames();
         if(var3 - this.mExpectedNumFramesPrev - 1 >= 4) {
            ++this.m4PlusFrameStutters;
         }

         if(this.mIsRecordingFpsInfoAtEachFrame) {
            Assertions.assertNotNull(this.mTimeToFps);
            FpsDebugFrameCallback.FpsInfo var6 = new FpsDebugFrameCallback.FpsInfo(this.getNumFrames(), this.getNumJSFrames(), var3, this.m4PlusFrameStutters, this.getFPS(), this.getJSFPS(), this.getTotalTimeMS());
            this.mTimeToFps.put(Long.valueOf(System.currentTimeMillis()), var6);
         }

         this.mExpectedNumFramesPrev = var3;
         this.mChoreographer.postFrameCallback(this);
      }
   }

   public int get4PlusFrameStutters() {
      return this.m4PlusFrameStutters;
   }

   public int getExpectedNumFrames() {
      return (int)((double)this.getTotalTimeMS() / 16.9D + 1.0D);
   }

   public double getFPS() {
      return this.mLastFrameTime == this.mFirstFrameTime?0.0D:(double)this.getNumFrames() * 1.0E9D / (double)(this.mLastFrameTime - this.mFirstFrameTime);
   }

   @Nullable
   public FpsDebugFrameCallback.FpsInfo getFpsInfo(long var1) {
      Assertions.assertNotNull(this.mTimeToFps, "FPS was not recorded at each frame!");
      Entry var3 = this.mTimeToFps.floorEntry(Long.valueOf(var1));
      return var3 == null?null:(FpsDebugFrameCallback.FpsInfo)var3.getValue();
   }

   public double getJSFPS() {
      return this.mLastFrameTime == this.mFirstFrameTime?0.0D:(double)this.getNumJSFrames() * 1.0E9D / (double)(this.mLastFrameTime - this.mFirstFrameTime);
   }

   public int getNumFrames() {
      return this.mNumFrameCallbacks - 1;
   }

   public int getNumJSFrames() {
      return this.mNumFrameCallbacksWithBatchDispatches - 1;
   }

   public int getTotalTimeMS() {
      return (int)((double)this.mLastFrameTime - (double)this.mFirstFrameTime) / 1000000;
   }

   public void reset() {
      this.mFirstFrameTime = -1L;
      this.mLastFrameTime = -1L;
      this.mNumFrameCallbacks = 0;
      this.m4PlusFrameStutters = 0;
      this.mNumFrameCallbacksWithBatchDispatches = 0;
      this.mIsRecordingFpsInfoAtEachFrame = false;
      this.mTimeToFps = null;
   }

   public void start() {
      this.mShouldStop = false;
      this.mReactContext.getCatalystInstance().addBridgeIdleDebugListener(this.mDidJSUpdateUiDuringFrameDetector);
      this.mUIManagerModule.setViewHierarchyUpdateDebugListener(this.mDidJSUpdateUiDuringFrameDetector);
      this.mChoreographer.postFrameCallback(this);
   }

   public void startAndRecordFpsAtEachFrame() {
      this.mTimeToFps = new TreeMap();
      this.mIsRecordingFpsInfoAtEachFrame = true;
      this.start();
   }

   public void stop() {
      this.mShouldStop = true;
      this.mReactContext.getCatalystInstance().removeBridgeIdleDebugListener(this.mDidJSUpdateUiDuringFrameDetector);
      this.mUIManagerModule.setViewHierarchyUpdateDebugListener((NotThreadSafeViewHierarchyUpdateDebugListener)null);
   }

   public static class FpsInfo {

      public final double fps;
      public final double jsFps;
      public final int total4PlusFrameStutters;
      public final int totalExpectedFrames;
      public final int totalFrames;
      public final int totalJsFrames;
      public final int totalTimeMs;


      public FpsInfo(int var1, int var2, int var3, int var4, double var5, double var7, int var9) {
         this.totalFrames = var1;
         this.totalJsFrames = var2;
         this.totalExpectedFrames = var3;
         this.total4PlusFrameStutters = var4;
         this.fps = var5;
         this.jsFps = var7;
         this.totalTimeMs = var9;
      }
   }
}
