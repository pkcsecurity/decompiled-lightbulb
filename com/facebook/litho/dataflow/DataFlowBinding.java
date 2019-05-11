package com.facebook.litho.dataflow;

import com.facebook.litho.dataflow.BindingListener;

public interface DataFlowBinding {

   void activate();

   void deactivate();

   boolean isActive();

   void setListener(BindingListener var1);
}
