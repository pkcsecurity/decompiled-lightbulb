package com.facebook.litho.dataflow;

import com.facebook.litho.dataflow.ValueNode;

public class MappingNode extends ValueNode {

   public static final String END_INPUT = "end";
   public static final String INITIAL_INPUT = "initial";


   protected float calculateValue(long var1) {
      float var3 = this.getInput("initial").getValue();
      float var4 = this.getInput("end").getValue();
      return var3 + this.getInput("default_input").getValue() * (var4 - var3);
   }
}
