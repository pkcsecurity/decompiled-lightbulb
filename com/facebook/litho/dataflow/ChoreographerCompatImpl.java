package com.facebook.litho.dataflow;

import android.annotation.TargetApi;
import android.os.Handler;
import android.os.Looper;
import android.os.Build.VERSION;
import android.support.annotation.VisibleForTesting;
import android.view.Choreographer;
import android.view.Choreographer.FrameCallback;
import com.facebook.litho.dataflow.ChoreographerCompat;

public class ChoreographerCompatImpl implements ChoreographerCompat {

   private static final boolean IS_JELLYBEAN_OR_HIGHER;
   private static final long ONE_FRAME_MILLIS = 17L;
   private static ChoreographerCompat sInstance;
   private Choreographer mChoreographer;
   private Handler mHandler;


   static {
      boolean var0;
      if(VERSION.SDK_INT >= 16) {
         var0 = true;
      } else {
         var0 = false;
      }

      IS_JELLYBEAN_OR_HIGHER = var0;
   }

   private ChoreographerCompatImpl() {
      if(IS_JELLYBEAN_OR_HIGHER) {
         this.mChoreographer = this.getChoreographer();
      } else {
         this.mHandler = new Handler(Looper.getMainLooper());
      }
   }

   @TargetApi(16)
   private void choreographerPostFrameCallback(FrameCallback var1) {
      this.mChoreographer.postFrameCallback(var1);
   }

   @TargetApi(16)
   private void choreographerPostFrameCallbackDelayed(FrameCallback var1, long var2) {
      this.mChoreographer.postFrameCallbackDelayed(var1, var2);
   }

   @TargetApi(16)
   private void choreographerRemoveFrameCallback(FrameCallback var1) {
      this.mChoreographer.removeFrameCallback(var1);
   }

   @TargetApi(16)
   private Choreographer getChoreographer() {
      return Choreographer.getInstance();
   }

   public static ChoreographerCompat getInstance() {
      if(sInstance == null) {
         sInstance = new ChoreographerCompatImpl();
      }

      return sInstance;
   }

   @VisibleForTesting
   public static void setInstance(ChoreographerCompat var0) {
      sInstance = var0;
   }

   public void postFrameCallback(ChoreographerCompat.FrameCallback var1) {
      if(IS_JELLYBEAN_OR_HIGHER) {
         this.choreographerPostFrameCallback(var1.getFrameCallback());
      } else {
         this.mHandler.postDelayed(var1.getRunnable(), 0L);
      }
   }

   public void postFrameCallbackDelayed(ChoreographerCompat.FrameCallback var1, long var2) {
      if(IS_JELLYBEAN_OR_HIGHER) {
         this.choreographerPostFrameCallbackDelayed(var1.getFrameCallback(), var2);
      } else {
         this.mHandler.postDelayed(var1.getRunnable(), var2 + 17L);
      }
   }

   public void removeFrameCallback(ChoreographerCompat.FrameCallback var1) {
      if(IS_JELLYBEAN_OR_HIGHER) {
         this.choreographerRemoveFrameCallback(var1.getFrameCallback());
      } else {
         this.mHandler.removeCallbacks(var1.getRunnable());
      }
   }
}
