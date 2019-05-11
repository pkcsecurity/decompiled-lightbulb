package com.facebook.litho.dataflow;

import com.facebook.litho.dataflow.NodeCanFinish;
import com.facebook.litho.dataflow.ValueNode;

public class TimingNode extends ValueNode implements NodeCanFinish {

   private static final float END_VALUE = 1.0F;
   private static final float INITIAL_VALUE = 0.0F;
   private static final int MS_IN_NANOS = 1000000;
   private final long mDurationMs;
   private long mExpectedEndTimeNs = Long.MIN_VALUE;
   private long mLastValueTimeNs = Long.MIN_VALUE;
   private long mStartTimeNs = Long.MIN_VALUE;


   public TimingNode(int var1) {
      this.mDurationMs = (long)var1;
   }

   public float calculateValue(long var1) {
      if(this.mLastValueTimeNs == Long.MIN_VALUE) {
         this.mStartTimeNs = var1;
         this.mLastValueTimeNs = var1;
         this.mExpectedEndTimeNs = this.mStartTimeNs + this.mDurationMs * 1000000L;
         return 0.0F;
      } else if(var1 >= this.mExpectedEndTimeNs) {
         this.mLastValueTimeNs = var1;
         return 1.0F;
      } else {
         this.mLastValueTimeNs = var1;
         return (float)(var1 - this.mStartTimeNs) / (float)(this.mExpectedEndTimeNs - this.mStartTimeNs);
      }
   }

   public boolean isFinished() {
      return this.mLastValueTimeNs >= this.mExpectedEndTimeNs;
   }
}
