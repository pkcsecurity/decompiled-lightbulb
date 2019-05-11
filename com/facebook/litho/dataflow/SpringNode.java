package com.facebook.litho.dataflow;

import com.facebook.litho.dataflow.NodeCanFinish;
import com.facebook.litho.dataflow.ValueNode;
import com.facebook.litho.dataflow.springs.Spring;
import com.facebook.litho.dataflow.springs.SpringConfig;

public class SpringNode extends ValueNode implements NodeCanFinish {

   public static final String END_INPUT = "end";
   public static final String INITIAL_INPUT = "initial";
   public static final double NS_PER_SECOND = 1.0E9D;
   private long mLastFrameTimeNs;
   private final Spring mSpring;


   public SpringNode() {
      this((SpringConfig)null);
   }

   public SpringNode(SpringConfig var1) {
      this.mLastFrameTimeNs = Long.MIN_VALUE;
      this.mSpring = new Spring();
      if(var1 != null) {
         this.mSpring.setSpringConfig(var1);
      }

   }

   public float calculateValue(long var1) {
      float var5;
      if(this.mLastFrameTimeNs == Long.MIN_VALUE) {
         this.mLastFrameTimeNs = var1;
         var5 = this.getInput("initial").getValue();
         float var6 = this.getInput("end").getValue();
         this.mSpring.setCurrentValue((double)var5);
         this.mSpring.setEndValue((double)var6);
         return var5;
      } else {
         var5 = this.getInput("end").getValue();
         this.mSpring.setEndValue((double)var5);
         if(this.isFinished()) {
            return var5;
         } else {
            double var3 = (double)(var1 - this.mLastFrameTimeNs) / 1.0E9D;
            this.mSpring.advance(var3);
            this.mLastFrameTimeNs = var1;
            return (float)this.mSpring.getCurrentValue();
         }
      }
   }

   public boolean isFinished() {
      return this.mSpring.isAtRest();
   }
}
