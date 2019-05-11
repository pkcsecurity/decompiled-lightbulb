package com.facebook.litho.dataflow;

import com.facebook.litho.dataflow.ValueNode;

public class SimpleNode extends ValueNode {

   public float calculateValue(long var1) {
      return this.getInput().getValue();
   }
}
