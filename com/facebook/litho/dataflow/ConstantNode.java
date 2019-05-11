package com.facebook.litho.dataflow;

import com.facebook.litho.dataflow.ValueNode;

public class ConstantNode extends ValueNode {

   private final float mValue;


   public ConstantNode(float var1) {
      this.mValue = var1;
   }

   public float calculateValue(long var1) {
      return this.mValue;
   }
}
