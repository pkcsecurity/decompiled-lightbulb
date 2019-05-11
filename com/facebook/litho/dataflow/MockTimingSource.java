package com.facebook.litho.dataflow;

import android.support.v4.util.Pair;
import com.facebook.litho.dataflow.ChoreographerCompat;
import com.facebook.litho.dataflow.DataFlowGraph;
import com.facebook.litho.dataflow.TimingSource;
import java.util.ArrayList;

public class MockTimingSource implements ChoreographerCompat, TimingSource {

   public static int FRAME_TIME_MS;
   private static final long FRAME_TIME_NANOS = (long)((double)FRAME_TIME_MS * 1000000.0D);
   private final ArrayList<Pair<ChoreographerCompat.FrameCallback, Long>> mChoreographerCallbacksToStartTimes = new ArrayList();
   private long mCurrentTimeNanos = 0L;
   private DataFlowGraph mDataFlowGraph;
   private boolean mIsRunning = false;


   private void fireChoreographerCallbacks() {
      int var2;
      for(int var1 = 0; var1 < this.mChoreographerCallbacksToStartTimes.size(); var1 = var2 + 1) {
         Pair var3 = (Pair)this.mChoreographerCallbacksToStartTimes.get(var1);
         var2 = var1;
         if(((Long)var3.second).longValue() <= this.mCurrentTimeNanos) {
            ((ChoreographerCompat.FrameCallback)var3.first).doFrame(this.mCurrentTimeNanos);
            this.mChoreographerCallbacksToStartTimes.remove(var1);
            var2 = var1 - 1;
         }
      }

   }

   public void postFrameCallback(ChoreographerCompat.FrameCallback var1) {
      this.postFrameCallbackDelayed(var1, 0L);
   }

   public void postFrameCallbackDelayed(ChoreographerCompat.FrameCallback var1, long var2) {
      this.mChoreographerCallbacksToStartTimes.add(new Pair(var1, Long.valueOf((long)((double)this.mCurrentTimeNanos + (double)var2 * 1000000.0D))));
   }

   public void removeFrameCallback(ChoreographerCompat.FrameCallback var1) {
      for(int var2 = this.mChoreographerCallbacksToStartTimes.size() - 1; var2 >= 0; --var2) {
         if(((Pair)this.mChoreographerCallbacksToStartTimes.get(var2)).first == var1) {
            this.mChoreographerCallbacksToStartTimes.remove(var2);
         }
      }

   }

   public void setDataFlowGraph(DataFlowGraph var1) {
      this.mDataFlowGraph = var1;
   }

   public void start() {
      this.mIsRunning = true;
   }

   public void step(int var1) {
      for(int var2 = 0; var2 < var1; ++var2) {
         if(!this.mIsRunning) {
            return;
         }

         this.mCurrentTimeNanos += FRAME_TIME_NANOS;
         this.mDataFlowGraph.doFrame(this.mCurrentTimeNanos);
         this.fireChoreographerCallbacks();
      }

   }

   public void stop() {
      this.mIsRunning = false;
   }
}
