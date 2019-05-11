package com.facebook.litho.dataflow;

import com.facebook.litho.dataflow.DataFlowGraph;

public interface TimingSource {

   void setDataFlowGraph(DataFlowGraph var1);

   void start();

   void stop();
}
