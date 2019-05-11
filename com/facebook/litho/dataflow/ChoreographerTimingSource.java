package com.facebook.litho.dataflow;

import com.facebook.litho.dataflow.ChoreographerCompat;
import com.facebook.litho.dataflow.ChoreographerCompatImpl;
import com.facebook.litho.dataflow.DataFlowGraph;
import com.facebook.litho.dataflow.TimingSource;

public class ChoreographerTimingSource implements TimingSource {

   private final ChoreographerCompat mChoreographerCompat = ChoreographerCompatImpl.getInstance();
   private DataFlowGraph mDataFlowGraph;
   private final ChoreographerCompat.FrameCallback mFrameCallback = new ChoreographerCompat.FrameCallback() {
      public void doFrame(long var1) {
         ChoreographerTimingSource.this.doFrame(var1);
      }
   };
   private boolean mHasPostedFrameCallback = false;
   private boolean mIsRunning = false;
   private long mLastFrameTime = Long.MIN_VALUE;


   private void doFrame(long var1) {
      this.mHasPostedFrameCallback = false;
      if(this.mIsRunning) {
         if(this.mLastFrameTime != var1) {
            this.mDataFlowGraph.doFrame(var1);
            this.mLastFrameTime = var1;
         }

         if(this.mIsRunning) {
            this.postFrameCallback();
         }

      }
   }

   private void postFrameCallback() {
      if(!this.mHasPostedFrameCallback) {
         this.mChoreographerCompat.postFrameCallback(this.mFrameCallback);
         this.mHasPostedFrameCallback = true;
      }
   }

   private void stopFrameCallback() {
      this.mChoreographerCompat.removeFrameCallback(this.mFrameCallback);
      this.mHasPostedFrameCallback = false;
   }

   public void setDataFlowGraph(DataFlowGraph var1) {
      this.mDataFlowGraph = var1;
   }

   public void start() {
      if(this.mDataFlowGraph == null) {
         throw new RuntimeException("Must set a binding graph first.");
      } else if(this.mIsRunning) {
         throw new RuntimeException("Tried to start but was already running.");
      } else {
         this.mIsRunning = true;
         this.postFrameCallback();
      }
   }

   public void stop() {
      if(!this.mIsRunning) {
         throw new RuntimeException("Tried to stop but wasn\'t running.");
      } else {
         this.mIsRunning = false;
         this.stopFrameCallback();
      }
   }
}
