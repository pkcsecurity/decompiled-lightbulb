package com.facebook.litho.dataflow;

import android.animation.TimeInterpolator;
import com.facebook.litho.dataflow.ValueNode;

public class InterpolatorNode extends ValueNode {

   private final TimeInterpolator mInterpolator;


   public InterpolatorNode(TimeInterpolator var1) {
      this.mInterpolator = var1;
   }

   protected float calculateValue(long var1) {
      float var3 = this.getInput("default_input").getValue();
      return this.mInterpolator.getInterpolation(var3);
   }
}
